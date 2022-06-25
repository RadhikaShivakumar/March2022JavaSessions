package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.exceptions.FrameworkException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	//5 threads in parallel tests -> it will create one local copy of thread and execute tests
	// dead lock condition
	
	
	//reporting is fine, it never interrupts other tests and get accurate results
	
	/**
	 * This method is used to initialize the webdriver on the basis of given browser name
	 * @param browserName
	 * @return it returns driver
	 */
	public WebDriver init_driver(Properties prop) {
		
		//String browserName = prop.getProperty("browser").trim();
		
		//for jenkins trigger
		// mvn clean install -Denv="qa" -Dbrowser="chrome"
		String browserName = System.getProperty("browser");
		
		optionsManager= new OptionsManager(prop);
		
		System.out.println("Browser name is : "+ browserName);
		if(browserName.equalsIgnoreCase("chrome")) {
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote execution on docker/Cloud
				init_remoteDriver("chrome");
			} else {
				//local execution:
				WebDriverManager.chromedriver().setup();
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		
			}
			
	}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
	
		} 
		else if(browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		} 
		else {
			System.out.println("Please pass the right browser: "+ browserName);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
	
		return getDriver();
	}
	
	/** For remote execution
	 * 
	 * @param string
	 */
	private void init_remoteDriver(String browserName) {
		System.out.println("Runnin testcases on remote grid server: "+browserName );

		if(browserName.equalsIgnoreCase("chrome")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} 
		else if(browserName.equalsIgnoreCase("firefox")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}	
	}

	//	getDriver().findElement(By.linkText("Login")).click();
	/**
	 * get the thread local copy of driver
	 * @return
	 */
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	
	/**
	 * This method is used to initialize the properties
	 * @return 
	 */
	
	// ./ --> refers to go the root of the project and from there go to src/test/resources/config/config.properties
	// difference between the all diff environments is url,browser,username and pwd
	// and any other settings.
	public Properties init_prop()  {
		
		prop=new Properties();
		FileInputStream ip = null;
		
		
		// mvn clean install -Denv="qa"
		String envName = System.getProperty("env");
		System.out.println("Running tests on environment: "+ envName);
		
		if(envName == null) {
			System.out.println("No env is given..Hence running it on QA environment");
			try {
				ip = new  FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
			  switch(envName.toLowerCase()) {
			  case "qa":
				  System.out.println("Running it on QA environment");
				  ip = new  FileInputStream("./src/test/resources/config/qa.config.properties");
				  break;
			  case "stage":
				  System.out.println("Running it on stage environment");
				  ip = new  FileInputStream("./src/test/resources/config/stage.config.properties");
				  break;
			  case "dev":
				  System.out.println("Running it on dev environment");
				  ip = new  FileInputStream("./src/test/resources/config/dev.config.properties");
				  break;  
			  case "uat":
				  System.out.println("Running it on uat environment");
				  ip = new  FileInputStream("./src/test/resources/config/uat.config.properties");
				  break;  
			  case "prod":
				  System.out.println("Running it on prod environment");
				  ip = new  FileInputStream("./src/test/resources/config/config.properties");
				  break;   
				  
			  default:
				  System.out.println("Please pass the right environent.."+envName);
				  throw new FrameworkException("NoEnvironmentFoundException ---> No env is found exception...."); 	
				 // break;
			  		}
			
		} catch (Exception e) {
		}
		}
	
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
		
	}
	
	/**
	 * Take a screenshot
	 * TakesScreenshot ->interface
	 * @return file path
	 */
	public String getScreenshot() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = "./"+"screenshot/"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	
	
	
}
