package com.marketlogic.pagelib;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.marketlogic.genericlib.SolventSelenium;

/**
 * This is the Page Object class for Home Page of Google.com
 * 
 * @author pankhurisharma
 *
 */
public class GoogleHomePage extends SolventSelenium {
	
	@FindBy(xpath = "//input[@type='text' and @title = 'Search']")
	public WebElement googleSearchTextBox;
	
	@FindBy(xpath = "//image[@alt='Google']")
	public WebElement googleTitle;
	
	@FindBy(xpath = "//input[@value ='Google Search']")
	public WebElement googleSearchButton;
	
	@FindBy(xpath = "//input[contains(@value, 'Feeling Lucky')]")
	public WebElement iAmFeelingLuckyButton;
	
	@FindBy(xpath = "//div[@class='rc']/h3[@class='r']/a")
	public List<WebElement> googleSearchResults;
	
	@FindBy(linkText = "English")
	public WebElement changeLanguageToEnglish;
	
	@FindBy(xpath = "//div[@class='med card-section']/p")
	public WebElement noSearchResults;
	
	/**
	 * Initializing Page Factory elements in the constructor 
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public GoogleHomePage() {
		PageFactory.initElements(driver, this);
		if(changeLanguageToEnglish.isDisplayed())
			changeLanguageToEnglish.click();
	}
	
	/**
	 * This method is used to enter the text in search text box
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public void enterSearchText(String searchText) {
		googleSearchTextBox.sendKeys(searchText);
	}
	
	/**
	 * This method is used to click on Search Button
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public void clickOnSearchButton() {
		waitForElementToPresent(googleSearchButton);
		googleSearchButton.click();
	}
	
	/**
	 * This method is used to perform search 
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public void performSearch(String searchText) {
		clearSearch();	
		enterSearchText(searchText);
		clickOnSearchButton();
		waitForPageToLoad();
	}
	
	/**
	 * This method is used to get all the search results.
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public ArrayList<String> getAllSearchResults() {
		ArrayList<String> resultNames = new ArrayList<String>();
		for (WebElement result : googleSearchResults) {
			resultNames.add(result.getText());
		}
		return resultNames;
	}
	
	/**
	 * This method is used to verify search results with a searched string 
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public void verifySearchResultsForSingleString(String searchedString) {
		ArrayList<String> searchResults = getAllSearchResults();
		for (String result : searchResults) {
			Assert.assertTrue(result.toLowerCase().contains(searchedString.toLowerCase()), "Search failed!! Search result : " + result + " does not contains searched string : " + searchedString);
		}
	}
	
	/**
	 * This method is used to verify no search result
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public String verifyNoSearchResults() {
		return noSearchResults.getText();
	}
	
	/**
	 * This method is used to clear search 
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public void clearSearch() {
		googleSearchTextBox.clear();
	}
	
	/**
	 * This method is used to click on I'm feeling lucky button to bypass the search result
	 *
	 * @since August 2018
	 * @author pankhurisharma
	 */
	public void clickOnFeelingLuckyButton() {
		iAmFeelingLuckyButton.click();
	}

}
