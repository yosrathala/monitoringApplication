package com.mycompany.myapp.spark;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

@Service
@Scope("singleton")
public class SVMPostInterest implements PostInterestManager
{
	private  SparkConf conf = new SparkConf().setAppName("SVM Classifier Example").setMaster("local[5]");
	private  JavaSparkContext sc = new JavaSparkContext(conf);
	private  JavaRDD<String> textData;
	private  SVMModel model;
	
	public  void init(String file) {
		// TODO Auto-generated method stub
		textData = sc.textFile(file);
	}

	public  void buildModel() {
		// TODO Auto-generated method stub
		
		final HashingTF tf = new HashingTF();
		JavaRDD<LabeledPoint> data = textData.map(s -> new LabeledPoint(Double.parseDouble(s.split("\t")[4]), tf.transform(Arrays.asList(s.split("\t")[1].split(" ")))));
		data.cache();
		
		int iterations = 100;
        model = SVMWithSGD.train(data.rdd(), iterations);
	}

	public  boolean predict(String newPosts) 
	{
		// TODO Auto-generated method stub
		final HashingTF tf = new HashingTF();
		
		int i = (int)model.predict(tf.transform(Arrays.asList(newPosts.split(" "))));
		return i == 1 ? true : false ;
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
        	getSc().stop();
        	getSc().close();
        }
   
    }
    
    
	public  JavaSparkContext getSc() {
		return sc;
	}

	public  boolean isModelSet() {
		return model != null;
	}

}

