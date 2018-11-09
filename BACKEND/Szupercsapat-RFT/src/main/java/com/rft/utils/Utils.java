package com.rft.utils;

import java.util.Random;

import com.rft.exceptions.BadYearException;

public class Utils {
	
	public static String randomString(int n)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<n;i++)
		{
			sb.append(randomChar());
		}
			
		return sb.toString();
	}
	
	public static char randomChar()
	{
		String from = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
		Random rand = new Random();
		int n=rand.nextInt(from.length());
		
		return from.charAt(n);
	}
	
	//https://stackoverflow.com/a/1306751
	public static void checkTheNumberPositive4DigitLong(int n)
	{
		if(n<0)
			throw new BadYearException("The year cannot be less than zero");
		int length = (int)(Math.log10(n)+1);
		if(length !=4)
			throw new BadYearException("The given year is not 4 digit long!");
	}
	
}
