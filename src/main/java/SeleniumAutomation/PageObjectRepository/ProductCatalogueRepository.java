package SeleniumAutomation.PageObjectRepository;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumAutomation.ResuableComponents.ResuableComponent;

public class ProductCatalogueRepository extends ResuableComponent {

	WebDriver driver;

	public ProductCatalogueRepository(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".card-body")
	List<WebElement> products;
	
	@FindBy(css = ".ng-animating")
	WebElement spinner;
	
	By addtoCartButton = By.cssSelector(".card-body button:last-of-type");
	By viewButton = By.cssSelector(".card-body button:first-of-type");
	By confirmationMessage = By.cssSelector("#toast-container");
	By productList = By.cssSelector(".card-body");

	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productList);
		return products;
	}
	
	
	public WebElement getProductName(String requiredProductName) {
		WebElement productName = getProductList().stream().filter(product->product.findElement
				(By.cssSelector("b")).getText().equalsIgnoreCase(requiredProductName)).findFirst().orElse(null);
		return productName;
	}
	
	public void addProductToCart(String requiredProductName) throws InterruptedException {
		
		WebElement productToAdd = getProductName(requiredProductName);
		productToAdd.findElement(addtoCartButton).click();
		waitForElementToAppear(confirmationMessage);
		try {
			waitForElementToDisappear(spinner);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
