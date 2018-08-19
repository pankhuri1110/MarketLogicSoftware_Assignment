package com.marketlogic.genericlib;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This is the base class to instantiate Browser and containing other customised Selenium methods and other
 * method which can be used acroos different project of the organization.
 * 
 * @since July 2018
 * @author pankhurisharma
 *
 */
public class SolventSelenium {

	public static WebDriver driver;

	/**
	 * 
	 * This method is used to instantiate Firefox Browser on Different Operating Systems.
	 * This method can be extended to use Chrome and other browsers.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public static void launchBrowser() {	
		Path currentRelativePath = Paths.get("");
		String PATH = currentRelativePath.toAbsolutePath().toString();
		String geckoDriver = null;

		String osType = System.getProperty("os.name").toLowerCase();
		if(osType.indexOf("mac") >= 0)
			geckoDriver = PATH + "/lib/" + "geckodriver_mac";
		else if(osType.indexOf("lin") >= 0)
			geckoDriver = PATH + "/lib/" + "geckodriver_linux";
		else 
			geckoDriver = PATH + "\\lib\\" + "geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", geckoDriver);

		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.google.com");
		waitForPageToLoad();	
	}

	/**
	 * 
	 * Types of Locators to Find Element.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public enum LocatorType { 
		ID("ID"), XPATH("XPATH"), NAME("NAME"), CLASSNAME("CLASSNAME"); 

		private String type; 
		private LocatorType(String type) { 
			this.type = type; 
		} 

		@Override 
		public String toString(){ 
			return type; 
		} 
	}

	/**
	 * This method is used to find element if PageFactory class is not used. 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public WebElement getElement(String locator, LocatorType type) {

		try {
		WebElement element = null;

		switch (type.toString()) {
		case "ID":
			element = driver.findElement(By.id(locator));	
			break;
		case "XPATH":
			element= driver.findElement(By.xpath(locator));	
			break;
		case "NAME":
			element= driver.findElement(By.name(locator));	
			break;
		case "CLASSNAME":
			element= driver.findElement(By.className(locator));	
			break;
		default:
			break;
		}
		return element;
		}
		catch (NoSuchElementException e) {
			System.out.println("No Element found with Locator : " + locator + " and Locator type : " + type);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method is used to find list of elements if PageFactory class is not used. 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public List<WebElement> getElements(String locator, LocatorType type) {

		List<WebElement> elements = null;

		switch (type.toString()) {
		case "ID":
			System.out.println("Finding Elements with Id: " + locator);
			elements= driver.findElements(By.id(locator));	
			break;
		case "XPATH":
			System.out.println("Finding Elements with XPath: " + locator);
			elements= driver.findElements(By.xpath(locator));	
			break;
		case "NAME":
			System.out.println("Finding Elements with Name: " + locator);
			elements= driver.findElements(By.name(locator));	
			break;
		case "CLASSNAME":
			System.out.println("Finding Elements with Name: " + locator);
			elements= driver.findElements(By.className(locator));	
			break;	
		default:
			break;
		}
		return elements;
	}

	/**
	 * This method is wait for page to load completely. 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public static void waitForPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver wdriver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState"
						).equals("complete");
			}
		});		
	}

	/**
	 * This method is wait for element to appear in DOM. 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public static void waitForElementToPresent(WebElement element){	
		FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(200))
				.ignoring(NoSuchElementException.class);
		fluentWait.until(ExpectedConditions.visibilityOf(element));	
	}
	
	/**
	 * This method is wait for element to appear in DOM. 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public static void waitForElementToBeClickable(WebElement element){	
		FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(200))
				.ignoring(NoSuchElementException.class);
		fluentWait.until(ExpectedConditions.elementToBeClickable(element));	
	}
}