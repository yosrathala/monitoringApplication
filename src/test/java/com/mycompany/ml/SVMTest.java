package com.mycompany.ml;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.myapp.spark.SVMPostInterest;


public class SVMTest 
{
	private String file="/home/bji/Documents/sparkBase.csv";
	
	@Before
    public void setup()
	{
		SVMPostInterest.init(file);
		SVMPostInterest.buildModel();
	}
	
	@Test

	public void test1()
	{
		boolean result = SVMPostInterest.predict("Traducteur Français Portugais pour mission freelance en entreprise");
		System.out.println("Predicted SVM : "+ result);
		assertEquals(false, result);
	}
	
	@Test
	public void test2()
	{
		boolean result = SVMPostInterest.predict("Besoin d’un bon développeur web pour créer mon site internet pour centraliser des comptes sur les réseaux sociaux");
		System.out.println("Predicted SVM : "+ result);
		assertEquals(true, result);
	}
	
	@After
	public void teardown() {
		SVMPostInterest.getSc().stop();
    	SVMPostInterest.getSc().close();
	}

}
