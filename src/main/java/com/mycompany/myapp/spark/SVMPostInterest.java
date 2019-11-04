package com.mycompany.myapp.spark;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.regression.LabeledPoint;

public class SVMPostInterest implements PostInterestManager
{
	private static SparkConf conf = new SparkConf().setAppName("SVM Classifier Example").setMaster("local[5]");
	private static JavaSparkContext sc = new JavaSparkContext(conf);
	private static JavaRDD<String> textData;
	private static SVMModel model;
	
	public static void init(String file) {
		// TODO Auto-generated method stub
		textData = sc.textFile(file);
	}

	public static void buildModel() {
		// TODO Auto-generated method stub
		
		final HashingTF tf = new HashingTF();
		JavaRDD<LabeledPoint> data = textData.map(s -> new LabeledPoint(Double.parseDouble(s.split("\t")[4]), tf.transform(Arrays.asList(s.split("\t")[1].split(" ")))));
		data.cache();
		
		int iterations = 100;
        model = SVMWithSGD.train(data.rdd(), iterations);
	}

	public static boolean predict(String newPosts) 
	{
		// TODO Auto-generated method stub
		final HashingTF tf = new HashingTF();
		
		int i = (int)model.predict(tf.transform(Arrays.asList(newPosts.split(" "))));
		return i == 1 ? true : false ;
	}

	public static JavaSparkContext getSc() {
		return sc;
	}

}

