package com.qa.opencart.utils;

import java.net.URL;

// Ctrl +shift+o for importing

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserUtil {

	//use driver in other methods like launchUrl,closeBrowser,quitBrowser
	// why private because if it is public then we can access the driver instance from BrowserUtil class and default 
	//value of the driver is null, and get nullPointerException unnecessarily.
	
	private WebDriver driver; //encapsulation, private variable in public method
	
	public WebDriver init_driver(String browserName) {
		
		System.out.println("browser name is: "+browserName);
		
		if(browserName.equals("chrome")) {
			//System.setProperty("webdriver.chrome.driver","C:\\Drivers\\chromedriver_win32\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(); //blank browser will be opened with the constructor

		}
		else if(browserName.equals("firefox")) {
			//System.setProperty("webdriver.gecko.driver", "C:\\Drivers\\geckodriver-v0.30.0-win64\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(); //blank browser will be opened with the constructor
		} 
		else if(browserName.equals("edge")) {
			//System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(); //blank browser will be opened with the constructor
		} 
		else if(browserName.equals("edge")) {
			driver = new SafariDriver(); //blank browser will be opened with the constructor
		} 
		else
		{
			System.out.println("Please pass the right browser..."+browserName);
		}
		return driver; //returning driver instance.
	}
	
	
	//overloaded method - launchUrl with String url, and URL
	public void launchUrl(URL url) {
		driver.navigate().to(url);
	}
	
	
	public void launchUrl(String url) {
		// if URL is null
		if(url==null)
		{
			System.out.println("url is null...");
			return;
		}
		
		//to handle : InvalidArgumentException when not provide http or https
		if(url.indexOf("http") ==-1 || url.indexOf("https")==-1) 
		{
			System.out.println("http or https is missing in the url..");
		
			try {
			throw new Exception("HttpMissingException");
			}
			catch(Exception e)
			{
				
				System.out.println("http (s) is missing...");
			}
		}	
		driver.get(url);
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	public String getPageUrl() {
		return driver.getCurrentUrl();
	}
	
	public void closeBrowser() {
		 driver.close();
	}
	
	public void quitBrowser() {
		 driver.quit();
	}
}
