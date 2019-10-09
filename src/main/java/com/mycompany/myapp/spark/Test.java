package com.mycompany.myapp.spark;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String file = "/home/bji/Documents/sparkBase.csv";
		SVMPostInterest.init(file);
		SVMPostInterest.buildModel();
		String newP = "Besoin d'un développeur web pour créer mon site internet pour centraliser des comptes sur les réseaux sociaux";
		System.out.println(SVMPostInterest.predict(newP));
	}

}
