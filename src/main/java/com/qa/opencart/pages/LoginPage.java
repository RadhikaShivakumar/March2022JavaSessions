package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private By locators: Object Repository
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By logoutSuccessMsg = By.cssSelector("div#common-success h1"); 
	
	
	
	//2. Page constructor...
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	//3. Page actions:
	@Step("Getting login Page title of open cart app..")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIs(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("Getting login Page URL of open cart app..")
	public String getLoginPageUrl() {
		return eleUtil.waitForUrlContains(Constants.LOGIN_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("user is able to login with username: {0} and password: {1} open cart app..")
	public AccountsPage doLogin(String username,String pwd) {
		System.out.println("Login credentials are: "+ username + " : " + pwd );
		eleUtil.waitForElementVisible(emailId, Constants.DEFAULT_ELEMENT_TIME_OUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}

	@Step("isForgotPwdLinkExist..")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwdLink);
	}
	
	@Step("isRegisterLinkExist..")
	public boolean isRegisterLinkExist() {
		return eleUtil.doIsDisplayed(registerLink);
	}
	
	@Step("fetching success message for logout..")
	public String getLogoutSuccessMessg() {
		return eleUtil.waitForElementVisible(logoutSuccessMsg, Constants.DEFAULT_ELEMENT_TIME_OUT).getText();
	}
	
	@Step("Navigating to register page after clicking on registration link..")
	public RegisterPage goToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
	//page chaining: two levels from loginPage to RegisterPage
	
}
