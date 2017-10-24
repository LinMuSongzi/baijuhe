package com.mr.huang.framwork.commande;

public interface Calculate {

	String TAG = "CalculateImp";

	double sum(float one, float two);

	double subtraction(float one, float two);
	
	double multiplication(float one, float two);
	
	double division(float one, float two);
	
}
