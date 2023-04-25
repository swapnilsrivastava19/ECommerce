package com.ECommerce.TestCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ECommerce.QA.Base.Base;
import com.ECommerce.QA.Pages.AccountSuccessPage;
import com.ECommerce.QA.Pages.HomePage;
import com.ECommerce.QA.Pages.RegisterPage;
import com.ECommerce.QA.Utils.Utility;

public class RegisterTest extends Base{

	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;

	public RegisterTest() {
		super();
	}

	public WebDriver driver;

	@AfterMethod
	public void tearDown() {

		driver.quit();
	}

	@BeforeMethod
	public void setup() {

		driver=initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		HomePage homePage=new HomePage(driver);
		registerPage=homePage.navigateToRegisterPage();
	}

	@Test(priority = 1)
	public void verifyRegisteringAccountWithMandatoryFields() {

		accountSuccessPage=registerPage.registerWithMandatoryFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), Utility.generateEmailWithTimeStamp(), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(), dataProp.getProperty("accountSuccessfullyCreateHeading"),"Account is not created");


	}

	@Test(priority = 2)
	public void verifyRegisteringAccountWithAllFields() {

		accountSuccessPage=registerPage.registerWithAllFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), Utility.generateEmailWithTimeStamp(), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(), dataProp.getProperty("accountSuccessfullyCreateHeading"),"Account is not created");

	}

	@Test(priority = 3)
	public void verifyRegisteringAccountWithExistingEmail() {

		registerPage.registerWithAllFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), prop.getProperty("validEmail"), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		Assert.assertTrue(registerPage.retrieveDuplicateEmailAddressWarning().contains(dataProp.getProperty("duplicateEmailWarning")), "Warning message regarding duplicate email is not displayed");


	}

	@Test(priority = 4)
	public void verifyRegisteringAccountWithoutProvidingAnyDetails() {

		registerPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.displayStatusOfWarningMessage(dataProp.getProperty("privacyPolicyWarning"), dataProp.getProperty("firstNameWarning"), dataProp.getProperty("lastNameWarning"), dataProp.getProperty("emailWarning"), dataProp.getProperty("telephoneNumberWarning"), dataProp.getProperty("passwordWarning")),"Some Warning messages are not displayed");
	}

}
