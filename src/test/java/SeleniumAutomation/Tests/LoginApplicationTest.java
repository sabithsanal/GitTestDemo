package SeleniumAutomation.Tests;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.sun.org.apache.bcel.internal.classfile.Method;

import SeleniumAutomation.PageObjectRepository.LoginPageRepository;
import SeleniumAutomation.PageObjectRepository.ProductCatalogueRepository;
import SeleniumAutomation.TestComponents.BaseTest;

public class LoginApplicationTest extends BaseTest {
	
	
	@Test(dataProvider="specificData",alwaysRun=true)
	public void loginApplication(String data1,String data2,String data3) throws IOException, AWTException, InterruptedException {
		
		launchURL();
		LoginPageRepository loginPageProcess = new LoginPageRepository(driver);
		loginPageProcess.loginApplication(data1, data2);
		System.out.println(data3);
		
	}
		

	
}
