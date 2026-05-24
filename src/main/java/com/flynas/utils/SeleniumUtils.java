package com.flynas.utils;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SeleniumUtils{

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log = LogManager.getLogger(SeleniumUtils.class);


    public SeleniumUtils(WebDriver driver){
        this.driver = driver;
    }



    public void waitForInvisibilityOfLoader(){
        By loader = By.xpath("//div[@class='loader']");
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }
    public void waitForVisiblityOfElement(By locator,int timeout){
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitForElementToBeClickabe(By locator,int timeout){
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
    }
    @Step("Waiting for web page to completely load")
    public void waitForPageToLoad(){
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(WebDriver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete"));
            waitForInvisibilityOfLoader();
        }
        catch (Exception e){
            System.out.println("Loader was not present or already gone.");
        }
    }
    public void waitForFrameToBeSwitch(String framenameOrId,int timeout){
        wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framenameOrId));
    }
    public void scrollToElement(By locator)
    {
        waitForVisiblityOfElement(locator,10);
        scrollToElement(driver.findElement(locator));
    }

    @Step("scrolling the page to the locator")
    public void scrollToElement(WebElement locator)
    {
        try{
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center',})",locator);
        }
        catch(Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locator);
        }
    }
    @Step("select option from dropdown")
    public void selectFromDropDown(By locator, String option)
    {
        waitForVisiblityOfElement(locator,5);
        List<WebElement> dropdownList = driver.findElements(locator);
        for(WebElement optn:dropdownList){
            if(optn.getText().trim().equalsIgnoreCase(option))
            {
                scrollToElement(optn);
                log.info("Selected option: "+optn.getText().trim());
                optn.click();
                break;
            }
        }
    }
    public void clickUsingJS(By locator) {
        org.openqa.selenium.WebElement element = driver.findElement(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    public void clickUsingJS(WebElement locator) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", locator);
    }
    public void typeUsingJS(By locator) {
        org.openqa.selenium.WebElement element = driver.findElement(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].sendKeys();", element);
    }
    public void sendKeysUsingActions(WebDriver driver, By locator, String textToType) {
        try {
            // 1. Explicitly wait until the field is ready to receive input
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

            // 2. Instantiate the Actions pipeline builder
            Actions actions = new Actions(driver);

            // 3. Chain execution actions together for simulated physical typing
            actions.moveToElement(element)       // Hover over the element safely
                    .click(element)               // Physically click to gain focus
                    .doubleClick(element)         // Highlight existing text (if any)
                    .sendKeys(org.openqa.selenium.Keys.BACK_SPACE) // Clear existing text safely
                    .sendKeys(element, textToType) // Type character-by-character
                    .build()                      // Compile the sequence
                    .perform();                   // Fire the actions sequence in the browser

        } catch (Exception e) {
            throw new RuntimeException("Actions Class typing sequence failed on locator: " + locator, e);
        }
    }

}
