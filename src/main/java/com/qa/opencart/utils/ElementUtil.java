package com.qa.opencart.utils;

import java.time.Duration;
/*
 * To search functions, ctrl+O
 */
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {

	private WebDriver driver; //with public reference, anybody can create object of ElementUtil and the driver value is null.
	
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}
	


	public  By getBy(String locatorType,String locatorValue) {
		By locator = null;
		switch(locatorType.toLowerCase()) {
		
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
			
		case "css":
			locator = By.cssSelector(locatorValue);
			break;
		
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;
			
		default:
				break;
		}
		return locator;
	}
	
	public  String getAttributeValue(By locator,String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	
	//if element is displayed
	public  boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	//if Element is displayed or not
	//Sometimes isDisplayed method may not give proper values. then can use this function
	
	public  boolean isElementDisplayed(By locator) {
		WebElement ele = getElement(locator);
		List<WebElement> eleList = getElements(locator);
		if(eleList.size() > 0) {
			if(ele.isDisplayed()) {
				return true;
			}
		}
		return false;
	}
	
	public  boolean isElementDisplayedwithSize(By locator) {
		WebElement ele = getElement(locator);
		List<WebElement> eleList = getElements(locator);
		if(eleList.size() > 0) {
		
			return true;
		}
		return false;
	}
	
	//element is enabled
	public  boolean doIsEnabled(By locator) {
		return getElement(locator).isEnabled();
	}
	
	public  String doGetText(By locator) {
		return getElement(locator).getText();
	}
	
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public  void doSendKeys(By locator,String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}
	
	public  void doSendKeys(String locatorType,String locatorValue,String value) {
		getElement(getBy(locatorType,locatorValue)).sendKeys(value);
	}
	
	public  WebElement getElement(By locator) {
		return driver.findElement(locator);
	}
	
	public  List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	public  int getElementCount(By locator) {
		return getElements(locator).size();
	}
	
	public  List<String> getElementsAttributeList(By locator,String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<String>();
		for(WebElement e: eleList) {
			String attrVal = e.getAttribute(attrName);
			System.out.println(attrVal);
			eleAttrList.add(attrVal);
		}
		return eleAttrList;
	}
	
	public  List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for(WebElement e: eleList) {
			String eleText = e.getText();
			System.out.println(eleText);
			eleTextList.add(eleText);
		}
		return eleTextList;
	}

	// *****************************************Drop down Utils *******************//
	
	public  void selectDropDownByIndex(By locator,int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	public  void selectDropDownByValue(By locator,String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	public  void selectDropDownByVisibleText(By locator,String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	
	public int getDropDownValuesCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}
	
	public  void dropDownValueUsingSelectGetOptions(By locator,String value) {
		Select sel_country = new Select(getElement(locator));
		List<WebElement> optionList = sel_country.getOptions();
		
		for(WebElement e: optionList) {
			String text= e.getText();
			System.out.println(text);
			if(text.equals(value)) {
				e.click();
				break;
			}
		}
	}
	
	public  void dropDownValueUsingLocator(By locator,String value) {
		List<WebElement> list = getElements(locator);
        for(WebElement e: list) {
			String text= e.getText();
			  if(text.equals(value)) {
				e.click();
				break;
			  }
			}
	}
	
	public  List<String> getDropdownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> dropdownOptionList = select.getOptions();
		List<String> stringListOfOptions = new ArrayList<String>();
		for(WebElement e: dropdownOptionList ) {
			String text = e.getText();
			stringListOfOptions.add(text);
		}
		return stringListOfOptions;
	}
	
	//***************** Multi level dropdown util *************************//
	public  void multiLevelMenuHandling(By parentMenu,String l1,String l2,String l3,String l4) throws InterruptedException {

		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenu)).perform();
		Thread.sleep(2000);
		
		act.moveToElement(driver.findElement(By.linkText(l1))).perform();
		Thread.sleep(2000);
		
		act.moveToElement(driver.findElement(By.linkText(l2))).perform();
		Thread.sleep(2000);
		
		act.moveToElement(driver.findElement(By.linkText(l3))).perform();
		Thread.sleep(2000);
		
		driver.findElement(By.linkText(l4)).click();
		
	}
	
	//*************************** context click utils ************************************//
	public  void selectRightClickMenu(By rightClickElementLocator,By rightMenuItems) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(rightClickElementLocator)).perform();
		getElement(rightMenuItems).click();
	}
	
	public  List<String> getRightClickMenuList(By rightClickElementLocator,By rightMenuItems) {

		List<String> rightMenuList = new ArrayList<String>();
		WebElement rightClick = getElement(rightClickElementLocator);

		Actions act = new Actions(driver);
		act.contextClick(rightClick).perform();
		

		List<WebElement> menuItems = driver.findElements(rightMenuItems);
		
		for(WebElement e: menuItems) {
			String text = e.getText();
			rightMenuList.add(text);
		}	
		return rightMenuList;
	}
	
	//*************************  Mouse Operations Utils ********************//
	public  void dragAndDropAction(By source,By target) {
		Actions act = new Actions(driver);
		act.clickAndHold(getElement(source)).moveToElement(getElement(target)).build().perform();
	}
	
	// ******************* Action sendkeys and click ************************//
	public  void doActionSendkeys(By locator,String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator),value).perform();
	}
	
	public  void doActionClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
	
	//*********************************** Wait Utils **********************//
	/**
	 * An expectation for checking that an element is present on the DOM of a page. This does notnecessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  WebElement waitForElementPresence(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10)); //selenium 4.x
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.Visibility means that the element is not only displayed but also has a height and width that isgreater than 0.
	 * Parameters:locator used to find the elementReturns:the WebElement once it is located and visible
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  WebElement waitForElementVisibility(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10)); //selenium 4.x
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible. 
	 * Visibility means that the elements are not only displayed but also have a 
	 * height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  List<WebElement> waitForElementsVisible(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  List<WebElement> waitForElementsPresence(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * Default polling time = 500ms
	 * @return
	 */
	public  List<WebElement> waitForElementsPresence(By locator,int timeOut,long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut),Duration.ofMillis(pollingTime));
		return	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	
	/**
	 * An expectation for checking that the title contains a case-sensitive substring
	 * @param titleFraction
	 * @param timeOut
	 * @return
	 */

	public  String waitForTitleContains(String titleFraction,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		}
		return null;
	}
	
	public  void waitForFrameByLocator(By frameLocator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public  void waitForFrameByIndex(int frameIndex,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public  void waitForFrameByWebElement(WebElement frameElement,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public  void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	// click element  when ready with polling interval
	public  void waitForElementToBeClickableWithPolling(By locator,int timeOut,long pollingTime) {
		WebDriverWait wait  = new WebDriverWait(driver,Duration.ofSeconds(timeOut),Duration.ofMillis(pollingTime));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	/**
	 * An expectation for checking the title of a page.
	 * @param title
	 * @param timeOut
	 * @return
	 */
	public  String waitForTitleIs(String title,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleIs(title))) {
			return driver.getTitle();
		}
		return null;
	}
	
	// wait for url contains
	public  String waitForUrlContains(String urlFraction,int timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		if(wait.until(ExpectedConditions.urlContains(urlFraction))) {
			return driver.getCurrentUrl();
		}
		return null;
	}
	
	// wait for url to be
	public  String waitForUrlIs(String urlFraction,int timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		if(wait.until(ExpectedConditions.urlToBe(urlFraction))) {
			return driver.getCurrentUrl();
		}
		return null;
	}
	
	
	// custom wait for Page loading
	public  void waitForPageLoad(int timeOut) {
		
		long endTime = System.currentTimeMillis() + timeOut;
		
		while(System.currentTimeMillis() < endTime) {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			String state = js.executeScript("return document.readyState").toString();
			System.out.println("Page is : " +state);
			if(state.equals("complete")) {
				System.out.println("Page is fully loaded now...");
				break;
			}
		}
	}
	
	public List<WebElement> waitForElementsPresence(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(pollingTime));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public void waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime, String message) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime))
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class).withMessage(message);

		wait.until(ExpectedConditions.presenceOfElementLocated(locator)).click();
	}

	///
	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {

		WebElement element = null;

		int attempts = 0;
		while (attempts < timeOut) {
			try {
				element = driver.findElement(locator);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found in attempt : " + attempts + " for : " + locator);
				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}

		if (element == null) {
			try {
				throw new Exception("ElementNotFoundException");
			} catch (Exception e) {
				System.out.println("element is not found exception....tried for : " + timeOut + " secs"
						+ " with the interval of : " + pollingTime + " ms ");
			}
		}

		return element;

	}

	

	


	
	//************************* Alert Methods *********************************//
	
	/**
	 * Methods to getText,sendkeys,dismiss,accept alert
	 * @param timeout
	 * @return
	 */
	public  Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public String doGetAlertText(int timeOut) {
		return waitForAlert(timeOut).getText();
	}
	public void acceptAlert(int timeOut) {
		waitForAlert(timeOut).accept();
	}
	public void dismissAlert(int timeOut) {
		waitForAlert(timeOut).dismiss();
	}
	public void alertSendKeys(int timeOut,String value) {
		waitForAlert(timeOut).sendKeys(value);
	}
}
