package com.qa.opencart.tests;

/*
 ChromeOptions -> to run tests in headless mode, incongnito mode,remote machine
	in a specific browser.
	
	//incongnito mode: private
	
	Advantage:
	Headless mode -> no browser launch
	Testing is behind the scene, block the display property of the browser
	faster than the normal browsers
	straight forward application and for sanity testcases
	
	Disadvantage:
	Websites with multiple level dropdowns and complex flow will not be executed
	
	
	Not a industry standard, so not recommended 
	
	
	Single responsibility principle -> one class one resposibility
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HeadlessConcept {

	public static void main(String a[]) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions co = new ChromeOptions();
	//	co.setHeadless(true);
		co.addArguments("--headless");
		WebDriver driver = new ChromeDriver(co);
		driver.get("https://www.google.com");
		System.out.println(driver.getTitle());
		driver.quit();
	}
	//sout -> print shortcut in intelleij
}
