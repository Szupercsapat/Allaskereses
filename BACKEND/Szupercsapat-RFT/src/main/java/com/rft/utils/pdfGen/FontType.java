package com.rft.utils.pdfGen;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public class FontType {
	private Font font;
	
	public static Font getMainTitleFont()
	{
		return FontFactory.getFont(FontFactory.HELVETICA_BOLD, 40, Font.NORMAL, new BaseColor(20, 30, 40));
	}
	
	public static Font getCategoryTitleFont()
	{
		return FontFactory.getFont(FontFactory.TIMES_ROMAN, 25, Font.BOLD, new BaseColor(66, 206, 244));
	}
	
	public static Font getCategoryYearFont()
	{
		return FontFactory.getFont(FontFactory.COURIER, 19, new BaseColor(0, 0, 0));
	}
	
	public static Font getCategoryDescriptionFont()
	{
		return FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, new BaseColor(0, 0, 0));
	}
	
}
