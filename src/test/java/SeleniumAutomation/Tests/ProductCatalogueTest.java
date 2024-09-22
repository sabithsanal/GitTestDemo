package SeleniumAutomation.Tests;

import org.apache.logging.log4j.plugins.Factory;
import org.testng.annotations.Test;

import SeleniumAutomation.PageObjectRepository.ProductCatalogueRepository;
import SeleniumAutomation.TestComponents.BaseTest;


public class ProductCatalogueTest extends LoginApplicationTest{

	
	@Test(dataProvider="specificData")
	public void addToCart(String data1) throws InterruptedException
	{
		ProductCatalogueRepository getProducts = new ProductCatalogueRepository(driver);
		getProducts.addProductToCart(data1);
		driver.close();
	}
	
	
}
