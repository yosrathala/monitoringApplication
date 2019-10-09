package com.mycompany.myapp.spark;

import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.IDF;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.feature.IndexToString;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.feature.VectorIndexer;
import org.apache.spark.ml.feature.VectorIndexerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import java.util.ArrayList;
import java.util.List;
import org.apache.spark.sql.RowFactory;

public class RandomForestPostInterest implements PostInterestManager
{
	private static SparkSession spark;
	private static Dataset<Row> textData;
	private static PipelineModel model;

	public static void init(String file) {
		// TODO Auto-generated method stub
		 spark = SparkSession.builder().appName("RandomForest").master("local[4]").getOrCreate();
		textData = spark.read().format("com.databricks.spark.csv").option("header", "false").option("inferSchema", "true").option("delimiter", "\t").load(file);
	}

	
	public static void buildModel() {
		// TODO Auto-generated method stub
		Tokenizer tokenizer = new Tokenizer()
				  .setInputCol("_c1")
				  .setOutputCol("words");
		Dataset<Row> wordsData = tokenizer.transform(textData);
		
		HashingTF hashingTF = new HashingTF()
				  .setNumFeatures(1000)
				  .setInputCol(tokenizer.getOutputCol())
				  .setOutputCol("rawFeatures");
		
		Dataset<Row> tf = hashingTF.transform(wordsData);
		
		IDF idf = new IDF()
				.setInputCol("rawFeatures")
				.setOutputCol("features");
		
		IDFModel tfidf = idf.fit(tf);
		
		Dataset<Row> d = tfidf.transform(tf);
		Dataset<Row> dataset = d.select("_c4", "features", "_c1");
		
		StringIndexerModel labelIndexer = new StringIndexer()
				  .setInputCol("_c4")
				  .setOutputCol("indexedLabel")
				  .fit(dataset);
		VectorIndexerModel featureIndexer = new VectorIndexer()
				  .setInputCol("features")
				  .setOutputCol("indexedFeatures")
				  .setMaxCategories(4)
				  .fit(dataset);
		/*Dataset<Row>[] splits = dataset.randomSplit(new double[] {0.7, 0.3});
		Dataset<Row> trainingData = splits[0];
		Dataset<Row> testData = splits[1];
		//trainingData.show();*/
		RandomForestClassifier rf = new RandomForestClassifier()
				.setLabelCol("indexedLabel")
				.setFeaturesCol("indexedFeatures")
				.setNumTrees(10000);
		
		IndexToString labelConverter = new IndexToString()
				  .setInputCol("prediction")
				  .setOutputCol("predictedLabel")
				  .setLabels(labelIndexer.labels());
		
		Pipeline pipeline = new Pipeline()
				  .setStages(new PipelineStage[] {labelIndexer, featureIndexer, rf, labelConverter});
		
		model = pipeline.fit(dataset);
	}

	
	public static int predict(String newPosts) {
		// TODO Auto-generated method stub
		Tokenizer tokenizer = new Tokenizer()
				  .setInputCol("_c0")
				  .setOutputCol("words");
		 StructType schema = new StructType(new StructField[]{
			      new StructField("words", DataTypes.StringType, false, Metadata.empty())
			    });
		List<Row> rows = new ArrayList<>();
        rows.add(RowFactory.create(newPosts));
        Dataset<Row> listNewPosts = spark.createDataFrame(rows, schema);
		Dataset<Row> wordsData = tokenizer.transform(listNewPosts);
		
		HashingTF hashingTF = new HashingTF()
				  .setNumFeatures(1000)
				  .setInputCol(tokenizer.getOutputCol())
				  .setOutputCol("rawFeatures");
		
		Dataset<Row> tf = hashingTF.transform(wordsData);
		IDF idf = new IDF()
				.setInputCol("rawFeatures")
				.setOutputCol("features");
		
		IDFModel tfidf = idf.fit(tf);
		Dataset<Row> d = tfidf.transform(tf);
		
		VectorIndexerModel featureIndexer = new VectorIndexer()
				  .setInputCol("features")
				  .setOutputCol("indexedFeatures")
				  .setMaxCategories(4)
				  .fit(d);
		Dataset<Row> dd = featureIndexer.transform(d);
		Dataset<Row> dataset = dd.select("features");
		Dataset<Row> predictions = model.transform(dataset);
		//predictions.show();
		//predictions.foreach((ForeachFunction<Row>) row -> System.out.println(row));
		return 1;
	}

	

}
