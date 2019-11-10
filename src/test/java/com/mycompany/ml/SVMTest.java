package com.mycompany.ml;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.myapp.spark.SVMPostInterest;


public class SVMTest 
{
	private String file="/home/bji/Documents/sparkBase.csv";
	SVMPostInterest svmPostInterest = new SVMPostInterest();
	@Before
    public void setup()
	{
		svmPostInterest.init(file);
		svmPostInterest.buildModel();
	}
	
	@Test

	public void test1()
	{
		boolean result = svmPostInterest.predict("Traducteur Français Portugais pour mission freelance en entreprise");
		System.out.println("Predicted SVM : "+ result);
		assertEquals(false, result);
	}
	
	@Test
	public void test2()
	{
		boolean result = svmPostInterest.predict("Besoin d’un bon développeur web pour créer mon site internet pour centraliser des comptes sur les réseaux sociaux");
		System.out.println("Predicted SVM : "+ result);
		assertEquals(true, result);
	}
	
	@After
	public void teardown() {
		svmPostInterest.getSc().stop();
		svmPostInterest.getSc().close();
	}

}
