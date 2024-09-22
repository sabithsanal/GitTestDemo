import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class checkTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File(
				System.getProperty("user.dir") + "\\src\\test\\java\\SeleniumAutomation\\TestData\\TestData.xlsx");
		FileInputStream fis = new FileInputStream(file);
		/*--Open Excel Workbook--*/
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		
		/*--assign the variable--*/
		XSSFSheet specificSheet;
		XSSFSheet checkSheet = null;
		String specificData = null;
		Object getData[][] = null;
		DataFormatter formatter = new DataFormatter();

		/*-- Retireve data for the specific sheet of the WorkBook --*/
		int totalSheets = workBook.getNumberOfSheets();
		for (int i = 0; i < totalSheets; i++) {
			checkSheet = workBook.getSheetAt(i);
			if (checkSheet.getSheetName().equalsIgnoreCase("LoginApplicationTest")) {
				System.out.println("the specific sheet is found");
				break;
			}
		}

		specificSheet = checkSheet;
		XSSFRow firstRow = specificSheet.getRow(0);
		int totalRows = specificSheet.getPhysicalNumberOfRows();
		System.out.println("the totalRows is " + totalRows);
		int getColumnIndexofCell = 0;
		int columnIndex = 0;
		XSSFRow nextRow = null;
		int totalColumn = 0;
		int k;
		int j;
		Iterator<Cell> cell = firstRow.cellIterator();
		while (cell.hasNext()) {
			Cell cellValue = cell.next();
			if (cellValue.getStringCellValue().equalsIgnoreCase("testCase")) {
				getColumnIndexofCell = cellValue.getColumnIndex();
				System.out.println(getColumnIndexofCell);
			}
		}
		columnIndex = getColumnIndexofCell;
		System.out.println("The assigned value is " + columnIndex);
		
		for ( k = 0; k < totalRows-1; k++) {
			nextRow = specificSheet.getRow(k+1);
			specificData = formatter.formatCellValue(nextRow.getCell(columnIndex)).toString();
			System.out.println("The data is " + specificData);
			System.out.println(k);
			if (specificData.equalsIgnoreCase("checkJava")) {
				totalColumn = nextRow.getLastCellNum();
				getData = new Object[1][totalColumn-1];
				System.out.println(totalColumn);
				for ( j = 0; j <totalColumn-1; j++) {
					XSSFCell cellData = nextRow.getCell(j+1);
					String dataValue = formatter.formatCellValue(cellData);
					System.out.println("the cell data is : " + dataValue);	
					getData[0][j] = dataValue;			
				}		
			}
		}
		System.out.println(getData[0][0]);	
		System.out.println(getData[0][1]);	
		System.out.println(getData[0][2]);	
	}
}
