package com.rft.utils.pdfGen;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rft.entities.JobSeeker;
import com.rft.entities.School;
import com.rft.entities.TableRowBase;
import com.rft.entities.WorkPlace;
import com.rft.exceptions.CvCreationException;

public class GeneratePDF {

	private void setCellToDefault(PdfPCell cell)
	{
		cell.setBorderColor(BaseColor.WHITE);
		cell.setPaddingLeft(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	}
	
	private PdfPTable generate2ColTable(String title, List<TableRowBase> rows)
	{	
		PdfPTable table = new PdfPTable(2); 
		
		 float[] columnWidths = {0.9f, 1.1f};
	        try {
				table.setWidths(columnWidths);
			} catch (DocumentException e) {
				throw new CvCreationException("Error when creating the CV!"); 
			}
		
        table.setWidthPercentage(100); 
        table.setSpacingBefore(20); 

        PdfPCell titleCell = new PdfPCell();
        setCellToDefault(titleCell);
        titleCell.addElement(new Paragraph(title,FontType.getCategoryTitleFont()));
        titleCell.setColspan(2);
        table.addCell(titleCell);
 
        for(TableRowBase row : rows)
        {
        	PdfPCell yearCell = new PdfPCell();
            setCellToDefault(yearCell);
            yearCell.addElement(new Paragraph(row.getFromYear() +" - "+row.getToYear(),FontType.getCategoryYearFont()));
            
            PdfPCell descCell = new PdfPCell();
            setCellToDefault(descCell);
            descCell.addElement(new Paragraph(row.getName(),FontType.getCategoryDescriptionFont()));
            
            table.addCell(yearCell);
            table.addCell(descCell);
        }
		 return table;
	}
	
	private PdfPTable generateHeaderTable(String profileName,byte[] imageArray) throws BadElementException,MalformedURLException, IOException
	{
		final int IMAGE_SIZE=100;
		
		PdfPTable table = new PdfPTable(3); 
        table.setWidthPercentage(100);
 
        PdfPCell profileImageCell = new PdfPCell();
      
        Image profileImage = Image.getInstance(imageArray);
        
        profileImage.scaleAbsolute(IMAGE_SIZE,IMAGE_SIZE);
        profileImageCell.addElement(profileImage);
        setCellToDefault(profileImageCell);
 
        PdfPCell profileNameCell = new PdfPCell();
        setCellToDefault(profileNameCell);
        profileNameCell.addElement(new Paragraph(profileName,FontType.getMainTitleFont()));
        profileNameCell.setColspan(2);

        table.addCell(profileImageCell);
        table.addCell(profileNameCell);
        
        return(table);
	}
	
	public static byte[] getPDF(JobSeeker jobSeeker, byte[] defulatProfileImage)
	{
		Document document = new Document();
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    try
	    {
	    	GeneratePDF genPDF = new GeneratePDF();
	    	
	        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
	        document.open();
	        
	        byte[] profileImage = jobSeeker.getProfileImage() == null ? defulatProfileImage : jobSeeker.getProfileImage();
	        
	        try {
	        	document.add(genPDF.generateHeaderTable(
	        			jobSeeker.getFirstName() + " "+ jobSeeker.getLastName() ,profileImage));		        
	        }
	        catch(MalformedURLException ex) //TODO: logolni a hibakat
	        {
	        	throw new CvCreationException("Error when creating the CV!");
	        }
	        catch(IOException ex)
	        {
	        	throw new CvCreationException("Error when creating the CV!");
	        }
	        
	        List<TableRowBase> schools = new ArrayList<TableRowBase>();
	        List<TableRowBase> workPlaces = new ArrayList<TableRowBase>();
	        
	        schools.addAll(jobSeeker.getSchools());
	        workPlaces.addAll(jobSeeker.getWorkPlaces());
	        
	        document.add(genPDF.generate2ColTable("Iskola",schools));
	        document.add(genPDF.generate2ColTable("Korábbi munkahelyek",workPlaces));
	        
	        document.close();
	        writer.close();
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    
	    return byteArrayOutputStream.toByteArray();
	}
}





































