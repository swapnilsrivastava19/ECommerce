package com.ECommerce.TestCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ECommerce.QA.Base.Base;
import com.ECommerce.QA.Pages.AccountPage;
import com.ECommerce.QA.Pages.HomePage;
import com.ECommerce.QA.Pages.LoginPage;
import com.ECommerce.QA.Utils.Utility;

public class LoginTest  extends Base{
	
	LoginPage loginPage;
	
	public LoginTest() {
		super(); 
	}

	public WebDriver driver;

	@BeforeMethod
	public void setup() {
		
		driver=initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		HomePage homePage=new HomePage(driver);
		loginPage=homePage.navigateToLoginPage();

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1 , dataProvider = "validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email, String password) {

		AccountPage accountPage = loginPage.login(email, password);
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption() ,"Edit Your account information message is not displayed");
		
	}
	
	@DataProvider(name="validCredentialsSupplier")
	public Object[][] supplyTestData() {
		
		Object[][] data= Utility.getTestDataFromExcel("Login");
		return data;
	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidCredentials() {

		loginPage.login(Utility.generateEmailWithTimeStamp(), dataProp.getProperty("invalidPassword"));
		Assert.assertTrue(loginPage.retriveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected waring message is not displayed");

	}

	@Test(priority = 3)
	public void verifyLoginWithInvalidEmailValidPassword() {

		loginPage.login(Utility.generateEmailWithTimeStamp(), prop.getProperty("validPassword"));
		Assert.assertTrue(loginPage.retriveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected waring message is not displayed");

	}

	@Test(priority = 4)
	public void verifyLoginWithValidEmailInvalidPassword() {

		loginPage.login(prop.getProperty("validEmail"), dataProp.getProperty("invalidPassword"));
		Assert.assertTrue(loginPage.retriveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected waring message is not displayed");

	}


	@Test(priority = 5)
	public void verifyLoginWithoutEmailAndPassword() {

		loginPage.clickOnLoginButton();
		Assert.assertTrue(loginPage.retriveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected waring message is not displayed");

	}





}
