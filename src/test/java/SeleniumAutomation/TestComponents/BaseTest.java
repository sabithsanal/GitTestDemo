package SeleniumAutomation.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {

	public WebDriver driver;

	public WebDriver initializeDriver() throws IOException {
		// properties class

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\java\\SeleniumAutomation\\TestData\\GlobalValue.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browsertype");

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\BrowserDriver\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\BrowserDriver\\geckodriver.exe");
			driver = new FirefoxDriver();
			// Firefox
		} else if (browserName.equalsIgnoreCase("edge")) {
			// Edge
			System.setProperty("webdriver.edge.driver",
					System.getProperty("user.dir") + "\\BrowserDriver\\msedgedriver.exe");
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;

	}

	public void launchURL() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\java\\SeleniumAutomation\\TestData\\GlobalValue.properties");
		prop.load(fis);
		String URL = prop.getProperty("URLname");
		driver = initializeDriver();
		driver.get(URL);
	}

	public void tearDown() {
		driver.close();
	}

	/*-- To read data from excel sheet --*/
	@DataProvider(name = "dataExcel")
	public Object[][] getAllDataFromExcel() throws InvalidFormatException, IOException {
		File file = new File(
				System.getProperty("user.dir") + "\\src\\test\\java\\SeleniumAutomation\\TestData\\TestData.xlsx");
		FileInputStream fis = new FileInputStream(file);

		/*--Data Formatter - to format the values retrieved from cell to string --*/
		DataFormatter formatter = new DataFormatter();
		/*--Open Excel Workbook--*/
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		/*--assign the variable--*/
		XSSFSheet specificSheet;
		XSSFSheet checkSheet = null;

		/*-- Retireve data for the specific sheet of the WorkBook --*/
		int totalSheets = workBook.getNumberOfSheets();
		System.out.println(this.getClass().getSimpleName());
		for (int i = 0; i < totalSheets; i++) {
			checkSheet = workBook.getSheetAt(i);
			if (checkSheet.getSheetName().equalsIgnoreCase(this.getClass().getSimpleName())) {
				System.out.println("the specific sheet is found");
				break;
			}
		}

		specificSheet = checkSheet;
		/*--Total Rows in the excel sheet--*/
		int totalRows = specificSheet.getPhysicalNumberOfRows();
		/*--First Row of the Sheet--*/
		XSSFRow firstRow = specificSheet.getRow(0);
		/*--Get the Last cell of the row - indicates the total column--*/
		int lastColumn = firstRow.getLastCellNum();
		/*-- Create an MultiDimensionalArray to store the data --*/

		Object dataPara[][] = new Object[totalRows - 1][lastColumn];
		/*--Iterate for each row and column to retrieve data--*/
		int k = 0;
		int j = 0;

		for (k = 0; k < totalRows - 1; k++) {
			XSSFRow row = specificSheet.getRow(k + 1);
			for (j = 0; j < lastColumn; j++) {
				XSSFCell getCell = row.getCell(j);
				String value = formatter.formatCellValue(getCell);
				dataPara[k][j] = value;
			}
		}
		return dataPara;
	}

	@DataProvider(name = "specificData")
	public Object[][] getSpecificFieldDataExcel(Method m) throws IOException {
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
			if (checkSheet.getSheetName().equalsIgnoreCase(this.getClass().getSimpleName())) {
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

		for (k = 0; k < totalRows - 1; k++) {
			nextRow = specificSheet.getRow(k + 1);
			specificData = formatter.formatCellValue(nextRow.getCell(columnIndex)).toString();
			;
			if (specificData.equalsIgnoreCase(m.getName())) {
				totalColumn = nextRow.getLastCellNum();
				getData = new Object[1][totalColumn - 1];
				for (j = 0; j < totalColumn - 1; j++) {
					XSSFCell cellData = nextRow.getCell(j + 1);
					String dataValue = formatter.formatCellValue(cellData);
					getData[0][j] = dataValue;
				}
			}
		}
		return getData;
	}

	/*-- To get screenshot --*/
	public String takeScreenshot(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot capture = (TakesScreenshot) driver;
		File sourceFile = capture.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + "\\.png");
		FileUtils.copyFile(sourceFile, destinationFile);
		String returnScreenshotPath = System.getProperty("user.dir") + "\\reports\\" + testCaseName + "\\.png";
		return returnScreenshotPath;

	}

	/*-- To create extent report --*/

	public ExtentReports extentReports() {

		String pathValue = System.getProperty("user.dir") + "\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(pathValue);
		reporter.config().setReportName("AutomationTestResults");
		reporter.config().setDocumentTitle("ResultSummary");

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Author:SabithSanal", "AutomationTester");
		return extent;
	}



}