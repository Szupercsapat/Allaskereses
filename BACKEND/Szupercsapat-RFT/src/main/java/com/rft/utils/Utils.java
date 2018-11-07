package com.rft.utils;

import java.util.Random;

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
	
}
