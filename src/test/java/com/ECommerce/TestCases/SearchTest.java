package com.ECommerce.TestCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ECommerce.QA.Base.Base;
import com.ECommerce.QA.Pages.HomePage;
import com.ECommerce.QA.Pages.SearchPage;

public class SearchTest extends Base {
	
	public SearchTest(){
		super();
	}
	
	public WebDriver driver;
	SearchPage searchPage;
	HomePage homePage;
	
	@BeforeMethod
	public void setup() {
		driver=initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		homePage=new HomePage(driver);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority = 1)
	public void verifySearchWithValidProduct() {
		
		searchPage=homePage.searchForAProduct(dataProp.getProperty("validProduct"));
		Assert.assertTrue(searchPage.displayStatusOfvalidHPProduct(),"valid product HP is not displayed in the search result");
		
	}
	
	@Test(priority = 2)
	public void verifySearchWithInvalidProduct() {
		
		searchPage=homePage.searchForAProduct(dataProp.getProperty("invalidProduct"));
//		Assert.assertEquals(searchPage.retrieveNoProductMessageText(),dataProp.getProperty("noProductTextInSearchResults"),"Search product message is not displayed");
		Assert.assertEquals(searchPage.retrieveNoProductMessageText(),"abcd","Search product message is not displayed");
		
	}
	
	@Test(priority = 3, dependsOnMethods = {"verifySearchWithValidProduct","verifySearchWithInvalidProduct"})
	public void verifySearchWithoutAnyProduct() {
		
		searchPage=homePage.clickOnSearchButton();
		Assert.assertEquals(searchPage.retrieveNoProductMessageText(),dataProp.getProperty("noProductTextInSearchResults"),"Search product message is not displayed");	}

}
