package com.qa.opencart.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.utils.ExcelUtil;
/*
 The main difference between a page title and an h1 tag is that the page title is shown in the browser window
 and search results snippet while the h1 tag is only shown on the page itself. 
 The page title is defined in the HTML <head> section while the H1 tag is part of the <body> of a page.
 
 They can be same too at times.
 
 Page Title:
 <head>
 <title>Product comparison</title>
 
 Header: Product Comparison
 <div id="content" class="col-sm-12">==$0
 <h1>Product Comparison</h1>
 
 */

import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic("Epic-200: This epic is for Accounts page of open cart application")
@Story("LOGIN-201: Design Accounts page with various features")


public class AccountsPageTest extends BaseTest{

	@BeforeClass
	public void  accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		//accPage = new AccountsPage(driver);
	}
	
	
	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccoutsPageTitle();
		System.out.println("Accounts Page Title: "+ actTitle);
		Assert.assertEquals(actTitle, Constants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void accPageURLTest() {
		String actURL = accPage.getAccoutsPageURL();
		System.out.println("Accounts Page URL: "+ actURL);
		Assert.assertTrue(actURL.contains(Constants.ACCOUNTS_PAGE_URL_FRACTION));
	}
	
	@Test(enabled=false)
	public void accPageHeaderTest() {
		Assert.assertEquals(accPage.getAccPageHeader(), Constants.ACCOUNTS_PAGE_HEADER);
	}
	
	@Test
	public void accPageSectionsTest() {
		List<String> actAccSecList = accPage.getAccountsPageSectionsList();
		System.out.println("Actual Account Page Section Header List: " +actAccSecList);
		Assert.assertEquals(actAccSecList, Constants.EXPECTED_ACCOUNTS_SECTION_LIST);
	}
	
	//@Test
	public void logoutLinkTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void SearchExistTest() {
		Assert.assertTrue(accPage.isSearchExist());
	}
	
	//@Test
	public void logoutTest() {
		Assert.assertEquals(accPage.clickOnLogout().getLogoutSuccessMessg(), Constants.LOGOUT_SUCCESS_MESSG);
	}
	
	@DataProvider
	public Object[][] getSearchKey() {
		return new Object[][] {
			{"MacBook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"}
		};
	}
	
	@Test(dataProvider="getSearchKey")
	public void searchTest(String searchKey) {
		searchResPage = accPage.doSearch(searchKey);
		Assert.assertTrue(searchResPage.getSearchResultCount()>0);
	}
	
	
	@DataProvider
	public Object[][] getProductName() {
		return new Object[][] {
			{"MacBook","MacBook Pro"},
			{"MacBook","MacBook Air"},
			{"MacBook","MacBook"},
			{"iMac", "iMac"},
			{"Apple", "Apple Cinema 30\""},
			{"Samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@DataProvider
	public Object[][] getProductNameViaExcel(){
		return ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
	}

	@Test(dataProvider = "getProductNameViaExcel")
	public void selectProductTest(String searchKey, String productName) {
		
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(productName);
		String productHeader = productInfoPage.getProductHeaderName();
		System.out.println("product header: " + productHeader);
		Assert.assertEquals(productHeader, productName);
	}
	

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 4},
			{"Samsung", "Samsung SyncMaster 941BW", 1},
			{"Samsung","Samsung Galaxy Tab 10.1",7}
		};
	}
	
	@Test(dataProvider="getProductData")
	public void productImageTest(String searchKey,String productName,int productImageCount) {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage  = searchResPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagescount(), productImageCount);
		}
	

	@Test
	public void productInfoDescriptionTest() {
		searchResPage = accPage.doSearch("Macbook");
		productInfoPage  = searchResPage.selectProduct("MacBook Pro");
		softAssert.assertTrue(productInfoPage.getProductInfoPageInnerText().contains("Latest Intel mobile architecture"));
		softAssert.assertTrue(productInfoPage.getProductInfoPageInnerText().contains("new Core 2 Duo MacBook Pro is over 50%"));
		softAssert.assertTrue(productInfoPage.getProductInfoPageInnerText().contains("Connect. Create. Communicate."));
		softAssert.assertAll();
	}
	
	//where to store all these hard coded values.

	
	@DataProvider
	public Object[][] getaddToCartData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", "1"},
			{"Samsung", "Samsung SyncMaster 941BW", "1"},
			{"Samsung","Samsung Galaxy Tab 10.1","1"}
		};
	}
	
	@Test(dataProvider="getaddToCartData")
	public void addToCartTest(String searchKey,String productName,String qty) {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage  = searchResPage.selectProduct(productName);
		String actSuccessMsg = productInfoPage.enterQty(qty)
												.clickOnAddToCart()
														.getCartSuccessMsg();
		System.out.println("cart msg: "+actSuccessMsg );
		softAssert.assertTrue(actSuccessMsg.contains(productName));
		String actCartItemText = productInfoPage.getCartItemText();
		softAssert.assertTrue(actCartItemText.contains(qty+" item(s)"));
	}
	
	
	@DataProvider
	public Object[][] getProductInfoData(){
		return new Object[][] {
			{"Macbook","MacBook Pro","MacBook Pro","Apple","In Stock","$2,000.00","Product 18","Ex Tax: $2,000.00","800"}
			
		};
	}
	
	@DataProvider
	public Object[][] getProductInfoDataViaExcel(){
		return ExcelUtil.getTestData(Constants.PRODUCTINFO_SHEET_NAME);
	}
	
	
	@Test(dataProvider="getProductInfoDataViaExcel")
	public void productInfoTest(String searchKey,String productName, String name,String brand,String availability,String price,String productCode,String exTaxPrice,String rewardPoints) throws InterruptedException {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(productName);
		Map<String, String> actProductInfoMap = productInfoPage.getProductInformation();
		softAssert.assertEquals(actProductInfoMap.get("name"),name);
		softAssert.assertEquals(actProductInfoMap.get("Brand"), brand);
		softAssert.assertEquals(actProductInfoMap.get("Availability"), availability);
		System.out.println(actProductInfoMap.get("price"));
		softAssert.assertEquals(actProductInfoMap.get("price"),price);
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), productCode);
		softAssert.assertEquals(actProductInfoMap.get("extaxprice"), exTaxPrice);
		System.out.println(actProductInfoMap.get("Reward Points"));
		softAssert.assertEquals(actProductInfoMap.get("Reward Points"), rewardPoints);
		softAssert.assertAll();
	}
	
	/*
  How to maintain a common code when products have different set of data:
  macbook -> Brand,Availibility,product code,Rewards Point
  Samsung -> Availibility,product code,Rewards Point	
	 */
	
	
	

	
}

