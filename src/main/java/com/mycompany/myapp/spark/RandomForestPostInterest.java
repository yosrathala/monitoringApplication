package com.mycompany.myapp.spark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.classification.RandomForestClassificationModel;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.IDF;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.feature.IndexToString;
import org.apache.spark.ml.feature.StopWordsRemover;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.feature.VectorIndexer;
import org.apache.spark.ml.feature.VectorIndexerModel;
import org.apache.spark.ml.feature.Word2Vec;
import org.apache.spark.ml.feature.Word2VecModel;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

@Service
@Scope("singleton")
public class RandomForestPostInterest implements PostInterestManager
{
	private  SparkSession spark;
	private  Dataset<Row> textData;
	//private static PipelineModel model;
	private  RandomForestClassificationModel rfModel;
	
	
	public  void init(String file) {
		// TODO Auto-generated method stub
		 spark = SparkSession.builder().appName("RandomForestPostInterest").master("local[5]").getOrCreate();
		textData = spark.read().format("com.databricks.spark.csv").option("header", "false").option("inferSchema", "true").option("delimiter", "\t").load(file);
	}
	
	public  void buildModel() 
	{
		Tokenizer tokenizer = new Tokenizer()
				  .setInputCol("_c1")
				  .setOutputCol("words");
		Dataset<Row> wordsData = tokenizer.transform(textData);
		String[] stopword = StopWordsRemover.loadDefaultStopWords("french");
		
		StopWordsRemover remover = new StopWordsRemover()
				  .setStopWords(stopword)
				  .setInputCol("words")
				  .setOutputCol("filtered");
		Dataset<Row> realWords = remover.transform(wordsData);
		Word2Vec word2vec = new Word2Vec().setInputCol("filtered").setOutputCol("features").setVectorSize(15).setMinCount(0);
		Word2VecModel word2vecmodel = word2vec.fit(realWords);
		Dataset<Row> result = word2vecmodel.transform(realWords);
		Dataset<Row> dataset = result.select("_c4", "features", "_c1");
		StringIndexerModel labelIndexer = new StringIndexer()
				  .setInputCol("_c4")
				  .setOutputCol("indexedLabel")
				  .fit(dataset);
		/*VectorIndexerModel featureIndexer = new VectorIndexer()
				  .setInputCol("features")
				  .setOutputCol("indexedFeatures")
				  .setMaxCategories(4)
				  .fit(dataset);*/
		
		RandomForestClassifier rf = new RandomForestClassifier()
				.setLabelCol("indexedLabel")
				.setFeaturesCol("features")
				.setNumTrees(1000);
		IndexToString labelConverter = new IndexToString()
				  .setInputCol("prediction")
				  .setOutputCol("predictedLabel")
				  .setLabels(labelIndexer.labels());
		Pipeline pipeline = new Pipeline()
				  .setStages(new PipelineStage[] {labelIndexer, rf, labelConverter});
		
		PipelineModel model = pipeline.fit(dataset);
		rfModel = (RandomForestClassificationModel)(model.stages()[1]);
	}
	
	public  boolean predict(String newPosts) {
		// TODO Auto-generated method stub
		Tokenizer tokenizer = new Tokenizer()
				  .setInputCol("text")
				  .setOutputCol("wordsP");
		 StructType schema = new StructType(new StructField[]{
			      new StructField("text", DataTypes.StringType, false, Metadata.empty())
			    });
		List<Row> rows = new ArrayList<>();
        rows.add(RowFactory.create(newPosts));
        Dataset<Row> listNewPosts = spark.createDataFrame(rows, schema);
		Dataset<Row> wordsData = tokenizer.transform(listNewPosts);
		String[] stopword = StopWordsRemover.loadDefaultStopWords("french");
		StopWordsRemover remover = new StopWordsRemover()
				  .setStopWords(stopword)
				  .setInputCol(tokenizer.getOutputCol())
				  .setOutputCol("filteredP");
		Dataset<Row> realWords = remover.transform(wordsData);
		Word2Vec word2vec = new Word2Vec().setInputCol(remover.getOutputCol()).setOutputCol("features").setVectorSize(15).setMinCount(0);
		Word2VecModel word2vecmodel = word2vec.fit(realWords);
		Dataset<Row> result = word2vecmodel.transform(realWords);
		Dataset<Row> dataset = result.select("features");
		Dataset<Row> predictions = rfModel.transform(dataset);
		Dataset<Row> v_pred = predictions.selectExpr("cast(prediction as int) prediction");
		int i = v_pred.first().getInt(0);
		return i==1 ? true : false;
	}
	
    public  void filterGoodPosts(ResultatRecherche resultatRecherche ) {

    	Set<ResultatItem> filtredPost = new HashSet<>();
        for (ResultatItem newResult : resultatRecherche.getResultatItems()) {
       	        
        	boolean result = false;
        	result = predict(newResult.getContenu());	
        	System.err.println("-----------------------Post Text ------------------------------");
        	System.err.println(newResult.getContenu());
        	System.err.println("-------------------------------- ------------------------------");				
			System.err.println("======================> PREDICTION result : " + result);
			if(result) {  
				filtredPost.add(newResult);      		
        	}
			resultatRecherche.setResultatItems(filtredPost);;

        }
   
    }
	public  boolean isModelSet() {
		return rfModel != null;
	}
}
