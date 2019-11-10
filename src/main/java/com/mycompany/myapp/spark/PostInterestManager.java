package com.mycompany.myapp.spark;

import org.apache.spark.mllib.classification.SVMModel;

import com.mycompany.myapp.domain.ResultatRecherche;

public interface PostInterestManager 
{
	public  void init(String file) ;
	public  void buildModel() ;
	public  boolean predict(String newPosts);
	public  boolean isModelSet();
	public  void filterGoodPosts(ResultatRecherche resultatRecherche ) ;
}
