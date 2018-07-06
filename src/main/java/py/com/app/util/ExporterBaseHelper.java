package py.com.app.util;

import java.io.IOException;

import py.com.app.util.CalendarHelper;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;

/**
 * 
 * @author Aprodherv2
 * 
 */
public abstract class ExporterBaseHelper
{
    protected String title = null;
    protected Boolean rotate;

    private final static Font FONT_TITLE = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);

    private final static Font COURIER_10 = new Font(Font.COURIER, 10, Font.NORMAL);
    
//    private final static Font SMALL_BOLD = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);

    // private static Font FONT_NORMAL_RED = new Font(Font.TIMES_ROMAN, 12,
    // Font.NORMAL, Color.RED);
    
    protected final float marginLeft = 50;
    protected final float marginRight = 10;
    protected final float marginTop = 20;
    protected final float marginBottom = 20;
    
    
    

    protected abstract String getUserInfo();
    protected abstract String getLogoImage();
    protected abstract String getWaterMarkImage();

    public void updateParam(String title, Boolean rotate){
        this.title = title;
        this.rotate = rotate;
    }
    
    private void addEmptyLine(Paragraph paragraph, int number)
    {
        for (int i = 0; i < number; i++)
        {
            paragraph.add(new Paragraph(" "));
        }
    }

    protected void addBasicSetup(Document document)
    {
        document.setPageSize(PageSize.A4);
        document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
    }

    protected void addMetaData(Document document)
    {
        document.addAuthor("Aprodher Team");
        document.addCreator("Aprodher Team");
        document.addSubject("Auto Generated Basic Report");
        document.addTitle(title);
        document.addKeywords("Java, PDF, iText, Auto Generated Report, SGPTI");
    }

    protected void addTitlePage(Document document) throws DocumentException
    {
        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_CENTER);

        addEmptyLine(preface, 1);

        preface.add(new Paragraph(title, FONT_TITLE));

        addEmptyLine(preface, 1);
        
        
        document.add(preface);
    }

    
    protected void addHeader(Document document) throws DocumentException, IOException
    {
//        BaseFont bf_symbol = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false);
        
        String line = " ";
        HeaderFooter header = new HeaderFooter(new Phrase(line, FONT_TITLE), false);
        header.setAlignment(Element.ALIGN_LEFT);
        
        document.setHeader(header);
    }

    
    /**
     * 
     * @param document
     * @throws DocumentException
     * @throws IOException
     */
    protected void addFooterPage(Document document) throws DocumentException, IOException
    {
        String line = "Reporte generado por: " + getUserInfo() + ", " + CalendarHelper.getNow() + "       ";
        
        HeaderFooter footer = new HeaderFooter(new Phrase(line, COURIER_10), true);
        footer.setBorder(Rectangle.TOP);
        footer.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        
        document.setFooter(footer);
    }

    
    /**
     * 
     * @param document
     * @param image
     * @param alt 
     * @throws DocumentException 
     */
    protected void addWaterMarkImage(Document document, Image image, String alt) throws DocumentException
    {
        // FIXME transparent values set not working
        
        int[] trans01 = {60, 60};
//        int[] trans02 = new int[]{ 0x00, 0x10 };
//        int[] trans03 = new int[]{ 0xF0, 0xFF }; 
        image.setTransparency(trans01);
        image.setAlt(alt);
        image.setAbsolutePosition((document.getPageSize().getWidth())/6,780);
             /*   (document.getPageSize().getWidth() - image.getScaledWidth()) / 2,
                (document.getPageSize().getHeight() - image.getScaledHeight()) / 2
        );*/
        image.scalePercent(40f);
        document.add(image);
    }
    
    public void updateTitle(String title)
    {
        this.title = title;
    }
    
    

	public Boolean getRotate() {
		return rotate;
	}

	public void setRotate(Boolean rotate) {
		this.rotate = rotate;
	}
}
