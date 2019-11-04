package com.mycompany.myapp.spark;

public interface PostInterestManager 
{
	public static void init(String file) {};
	public static void buildModel() {};
	public static boolean predict(String newPosts) {return false;};
}
