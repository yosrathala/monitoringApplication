package com.mycompany.ml;

import org.junit.Before;
import org.junit.Test;

import com.mycompany.myapp.spark.RandomForestPostInterest;
import com.mycompany.myapp.spark.SVMPostInterest;

public class RandomForestTest 
{
	private String file="/home/bji/Documents/sparkBase.csv";
	
	RandomForestPostInterest randomForestPostInterest = new RandomForestPostInterest();
	
	@Before
    public void setup()
	{
		randomForestPostInterest.init(file);
		randomForestPostInterest.buildModel();
	}
	
	
	@Test
	public void test1()
	{
		boolean result = randomForestPostInterest.predict("Traducteur Français Portugais pour mission freelance en entreprise");
		System.out.println("Predicted RandomForest : "+ result);
	}
	
	@Test
	public void test2()
	{
		boolean result = randomForestPostInterest.predict("Besoin d'un développeur web expert en Shopify pour finaliser mon site e-commerce");
		System.out.println("Predicted RandomForest : "+ result);
	}

}
