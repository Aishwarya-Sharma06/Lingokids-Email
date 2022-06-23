import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteToExcel {
    public static  void main(String args[]) throws IOException {
        
    	//Create an object of File class to open xlsx file
        File file =    new File("C:\\TestData\\TestData.xls");
        
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        
        //creating workbook instance that refers to .xls file
        HSSFWorkbook wb=new HSSFWorkbook(inputStream);
        
        //creating a Sheet object using the sheet Name
        HSSFSheet sheet=wb.getSheet("STUDENT_DATA");
        
        //Create a row object to retrieve row at index 3
        HSSFRow row2=sheet.createRow(3);
        
        //create a cell object to enter value in it using cell Index
        row2.createCell(0).setCellValue("Diana");
        row2.createCell(1).setCellValue("Jane");
        row2.createCell(2).setCellValue("djanes@gmail.com");
        row2.createCell(3).setCellValue("Female");
        row2.createCell(4).setCellValue("8786858432");
        row2.createCell(5).setCellValue("Park Lane, Flat C1 , New Jersey");
        
        //write the data in excel using output stream
        FileOutputStream outputStream = new FileOutputStream("C:\\TestData\\TestData.xls");
        wb.write(outputStream);
        wb.close();

    }
}