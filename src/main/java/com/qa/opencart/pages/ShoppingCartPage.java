package com.qa.opencart.pages;

import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class ShoppingCartPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	
}
