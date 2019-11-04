package com.mycompany.myapp.spark;

public class RandomForestTest 
{
	private String file="/home/bji/Documents/sparkBase.csv";
	
	public RandomForestTest()
	{
		RandomForestPostInterest.init(file);
		RandomForestPostInterest.buildModel();
	}
	
	

	public void test1()
	{
		boolean result = RandomForestPostInterest.predict("Traducteur Français Portugais pour mission freelance en entreprise");
		System.out.println("Predicted RandomForest : "+ result);
	}
	
	public void test2()
	{
		boolean result = RandomForestPostInterest.predict("Besoin d'un développeur web expert en Shopify pour finaliser mon site e-commerce");
		System.out.println("Predicted RandomForest : "+ result);
	}
	public static void main( String[] args)
    {
		RandomForestTest rf = new RandomForestTest();
		System.out.println("============RandomForest Test 1==============");
		rf.test1();
		System.out.println("============RandomForest Test 2==============");
		rf.test2();
    }

}
