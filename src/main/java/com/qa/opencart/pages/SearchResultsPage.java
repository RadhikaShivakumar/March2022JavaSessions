package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productNameLink;
	private By searchResults = By.cssSelector("div.product-layout.product-grid");
	 
	
	
	public SearchResultsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	public int getSearchResultCount() {
		return eleUtil.waitForElementsVisible(searchResults, Constants.DEFAULT_ELEMENT_TIME_OUT).size();
	}
	
	public productInfoPage selectProduct(String productName) {
		productNameLink = By.linkText(productName);
		eleUtil.doClick(productNameLink);
		return new productInfoPage(driver);
	}
	
	
	
	
}
