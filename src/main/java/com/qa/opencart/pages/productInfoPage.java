package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

public class productInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	Map<String,String> productInfoMap;
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By prouductPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	
	
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMsg = By.cssSelector("div.alert.alert-success");
	private By cart = By.cssSelector("div#cart button.dropdown-toggle");
	private By viewCartLink = By.xpath("//strong[normalize-space()='View Cart']");
	
	
	public productInfoPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	public String getProductHeaderName() {
		return eleUtil.waitForElementVisible(productHeader, Constants.DEFAULT_ELEMENT_TIME_OUT).getText();
	}
	
	public int getProductImagescount() {
		return eleUtil.waitForElementsVisible(productImages, Constants.DEFAULT_ELEMENT_TIME_OUT).size();
	}
	

	private void getProductMetaData() {
		//meta data:
		List<WebElement> metaDataList = eleUtil.getElements(productMetaData);
		System.out.println("Total product meta data: "+metaDataList.size() );
	
		for(WebElement e: metaDataList) {
			String meta[] = e.getText().split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productInfoMap.put(metaKey, metaValue);
			
		}
	}
	
	private void getProductPriceData() {
		//price
		List<WebElement> priceList = eleUtil.getElements(prouductPriceData);
		String price = priceList.get(0).getText().trim();
		String ExTaxPrice = priceList.get(1).getText().trim();
		
		productInfoMap.put("price", price);
		productInfoMap.put("extaxprice", ExTaxPrice);
	}

	
	public Map<String, String> getProductInformation() {
		productInfoMap = new HashMap<String,String>();
		productInfoMap.put("name", getProductHeaderName());
		getProductMetaData();
		getProductPriceData();
		productInfoMap.forEach((k,v)-> System.out.println(k+ " : "+v));
		return productInfoMap;
	}

	

	public String getProductInfoPageInnerText() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String pageInnerText = js.executeScript("return document.documentElement.innerText").toString();
		System.out.println("=======================================\n"+pageInnerText +"\n========================================");
		return pageInnerText;
	}
	
	public productInfoPage enterQty(String qty) {
		eleUtil.doSendKeys(quantity, qty);
		return this;
	}
	
	public productInfoPage clickOnAddToCart() {
		eleUtil.doClick(addToCartBtn);
		return this;
	}
	
	public String getCartSuccessMsg() {
		return eleUtil.waitForElementVisible(cartSuccessMsg, Constants.DEFAULT_ELEMENT_TIME_OUT).getText();
	}
	
	public String getCartItemText() {
		return eleUtil.doGetText(cart);
	}
	
	public ShoppingCartPage clickOnCart() {
		eleUtil.waitForElementVisible(cart, Constants.DEFAULT_ELEMENT_TIME_OUT);
		eleUtil.waitForElementVisible(viewCartLink, Constants.DEFAULT_ELEMENT_TIME_OUT);
		return new ShoppingCartPage(driver);
	}
}

//-quntity
//add to cart
// success is coming or not
// click on shopping cart
//verify landing on shopping cart
//verify products are displayed correctly or not and also quantity
