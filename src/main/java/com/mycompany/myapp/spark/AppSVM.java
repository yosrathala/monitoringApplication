package com.mycompany.myapp.spark;

import com.mycompany.myapp.spark.SVMPostInterest;
/**
 * Hello world!
 *
 */
public class AppSVM 
{
    public static void main( String[] args )
    {
        String file = "C:\\sparkBase.csv";
        SVMPostInterest svm = new SVMPostInterest();
        svm.init(file);
        svm.buildModel();
        String newPost = "Besoin d’un bon développeur web pour créer mon site internet pour centraliser des comptes sur les réseaux sociaux";
        System.out.println("Predicted SVM value : "+svm.predict(newPost));
    }
}
