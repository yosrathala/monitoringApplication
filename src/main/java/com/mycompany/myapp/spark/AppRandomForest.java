package com.mycompany.myapp.spark;

public class AppRandomForest {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String file = "C:\\sparkBase.csv";
		RandomForestPostInterest rfpi = new RandomForestPostInterest();
		rfpi.init(file);
		rfpi.buildModel();
		String newPost="Besoin d'un développeur web expert en Shopify pour finaliser mon site e-commerce";
		System.out.println("Predicted Random Forest value : ");
		//rfpi.predict(newPost);
	}

}
