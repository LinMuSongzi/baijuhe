package com.lin.app.common;

public class CalculateImp implements Calculate{
	
	public double sum(float one, float two) {
		return one + two;
	}

	public double subtraction(float one, float two) {
		return one - two;
	}

	public double multiplication(float one, float two) {
		return one * two;
	}

	public double division(float one, float two) {
		return one / two;
	}
	
}
