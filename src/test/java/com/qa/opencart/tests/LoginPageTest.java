package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic-100:This epic is for login page of open cart application")
@Story("LOGIN-101: Design login page with various features")


public class LoginPageTest extends BaseTest {

	@Description("Login page title test....")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=1)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		System.out.println("login Page title: " + actualTitle);
		Assert.assertEquals(actualTitle, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("Login page url test....")
	@Severity(SeverityLevel.NORMAL)
	
	@Test(priority=2)
	public void loginPageURLTest() {
		String actualURL = loginPage.getLoginPageUrl();
		System.out.println("login Page title: " + actualURL);
		Assert.assertTrue(actualURL.contains(Constants.LOGIN_PAGE_URL_FRACTION) );
	}
	
	@Description("Forgot password link test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3)
	public void forgotPwdLinkExistsTest() {
		Assert.assertTrue( loginPage.isForgotPwdLinkExist()) ;
	}
	
	@Description("Register link exist test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=4)
	public void registerLinkExistsTest() {
		Assert.assertTrue( loginPage.isRegisterLinkExist()) ;
	}

	@Description("User is able to login to open cart application....")
	@Severity(SeverityLevel.BLOCKER)
	
	@Test(priority = 5)
	public void loginTest() {
		Assert.assertTrue(loginPage
				.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim())
					.isLogoutLinkExist());
	}
	
}
