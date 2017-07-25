/**
 * 
 */
package py.com.app.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import py.com.app.util.ExporterBaseHelper;
import py.com.app.util.Credentials;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * @author RSGPTIv2
 * 
 */
@ManagedBean
@RequestScoped
public class ExporterHelper extends ExporterBaseHelper
{
    private transient Logger logger;
    
    @Inject
    Credentials crendencials;
    
    @Override
    protected String getLogoImage()
    {
        return AppHelper.getServletContext().getRealPath("/resources/images/img/logoAprodher3.jpg");
    }

    @Override
    protected String getWaterMarkImage()
    {
        return AppHelper.getServletContext().getRealPath("/resources/images/img/logoAprodher3.jpg");
    }
    
    @Override
    protected String getUserInfo()
    {
        return crendencials.getUserInfo();
    }
    
    
    
    
    
    
    /**
     * 
     * @param document
     */
    public void preProcessXLS(Object document)
    {
        // TODO implement logo add to sheets ;-)
    }
    
    /**
     * adds a Grey 25% color to header background
     * 
     * @param document
     */
    public void postProcessXLS(Object document)
    {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++)
        {
            header.getCell(i).setCellStyle(cellStyle);
        }
    }

    /**
     * adds the logo 
     * 
     * @param document
     * @throws IOException
     * @throws BadElementException
     * @throws DocumentException
     */
    public void preProcessPDF(Object document)
    {
    	super.title = MessageUtil.retrieveMessage(title);
        
        Document pdf = (Document) document;
        
        super.addBasicSetup(pdf);
        super.addMetaData(pdf);

        try
        {
            super.addHeader(pdf);
            super.addFooterPage(pdf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MessageUtil.addFacesMessageError("error.report.generator.building");
        }
        
        if (this.rotate != null && this.rotate)
        	pdf.setPageSize(PageSize.A4.rotate());
        else
        	pdf.setPageSize(PageSize.A4);
        
        pdf.open();
        
        try
        {
            String alt = MessageUtil.retrieveMessage("report.watermark.confidential.spaced");
            Image image = Image.getInstance(getWaterMarkImage());

            super.addWaterMarkImage(pdf, image, alt);
            super.addTitlePage(pdf);
            
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
            logger.severe(e.getMessage());
            MessageUtil.addFacesMessageError("error.report.generator.building");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            logger.severe(e.getMessage());
            MessageUtil.addFacesMessageError("error.report.generator.building");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            logger.severe(e.getMessage());
            MessageUtil.addFacesMessageError("error.report.generator.building");
        }
        
    }

    
}
