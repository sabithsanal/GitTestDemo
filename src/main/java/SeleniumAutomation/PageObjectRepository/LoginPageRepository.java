package SeleniumAutomation.PageObjectRepository;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.text.Document;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import SeleniumAutomation.ResuableComponents.ResuableComponent;

public class LoginPageRepository extends ResuableComponent{

	WebDriver driver;
	LoginPageRepository loginPageProcess = null;
	
	public LoginPageRepository(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/*--Initialize the Web Elements in Login Page using Page Factory--*/

	@FindBy(css = "div.login-section-wrapper")
	WebElement loginPanel;

	@FindBy(xpath = "//input[@id='userEmail']")
	WebElement emailField;

	@FindBy(css = "input[id='userPassword']")
	WebElement passwordField;

	@FindBy(id = "login")
	WebElement buttonLoginIn;
	
	@FindBy(xpath="//div[contains(@class,'left mt')]/h3")
	WebElement HomePanel;

	public void loginApplication(String username, String password) throws AWTException, InterruptedException {
	
		if (loginPanel.isDisplayed()) {
			Assert.assertTrue(true, "Login page is displayed");
		} else {
			Assert.assertTrue(false, "Login page is not displayed");
		}
		emailField.sendKeys(username);
		passwordField.sendKeys(password);
		buttonLoginIn.click();
		Thread.sleep(2000);
		
		if(HomePanel.isDisplayed()) {
			Assert.assertTrue(true,"Home page is displayed");
			System.out.println("Home page is shown");
		}
		else
		{
			Assert.assertTrue(false, "Home page is not displayed");
		}
		
	}
}
