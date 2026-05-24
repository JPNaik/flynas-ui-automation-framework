package com.flynas.pages;

import com.flynas.utils.SeleniumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SearchPage{
    private WebDriver driver;
    private SeleniumUtils utils;
    private WebDriverWait wait;
    private static Logger log;

    public SearchPage(WebDriver driver){
        this.driver = driver;
        utils = new SeleniumUtils(driver);
        log = LogManager.getLogger(SearchPage.class);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By acceptCookies = By.xpath("//button[contains(@id,'AllowAll')]");
    private By noThanks = By.xpath("//button[contains(@id,'deny')]");
    private By tripTypes = By.xpath("//label[contains(@class,'radio-inline')]");
    private By from = By.xpath("//input[contains(@id,'input-0')]");
    private By to = By.xpath("//input[contains(@id,'input-1')]");
    private By cityDropdown = By.xpath("//span[contains(@class,'code')]");
    private By calender = By.xpath("//span[contains(@class,'month')]");
    private By calenderDropdown = By.xpath("//div[contains(@class,'dp-content')]");
    private By availableFare = By.xpath("//span[contains(@class,'fare-currency')]");

    public void acceptCookies(){
        utils.waitForPageToLoad();
        utils.waitForElementToBeClickabe(acceptCookies,5);
        //wait.until(ExpectedConditions.elementToBeClickable(acceptCookies)).click();
        driver.findElement(acceptCookies).click();
        utils.waitForInvisibilityOfLoader();
        handleNoThanks();
    }
    private void handleNoThanks(){
        try {
            utils.waitForFrameToBeSwitch("webpush-onsite", 20);
//            wait.until(ExpectedConditions.elementToBeClickable(noThanks)).click();
            log.info("clicking no thanks");
            driver.findElement(noThanks).click();
            driver.switchTo().defaultContent();
        }
        catch (Exception e){
            System.out.println("No thanks didn't appear");
            driver.switchTo().defaultContent();
        }
    }
    public void selectTripType(String tripType){
        List<WebElement> tripTypesList = driver.findElements(tripTypes);
        for(int i=0;i<tripTypesList.size();i++)
        {
            if(tripTypesList.get(i).getText().trim().equalsIgnoreCase(tripType))
            {
                utils.scrollToElement(tripTypesList.get(i));
                wait.until(ExpectedConditions.elementToBeClickable(tripTypesList.get(i))).click();
                //tripTypesList.get(i).click();
            }
        }
    }
    public void selectOneWayRoute(String route)
    {
        String origin=route.split("-")[0];
        String destination=route.split("-")[1];
        log.info("clicking origin");
        //driver.findElement(from).click();
        //wait.until(ExpectedConditions.elementToBeClickable(from)).click();
        utils.clickUsingJS(from);
        log.info("origin clicked successfully");
        utils.sendKeysUsingActions(driver,from,origin);
        //driver.findElement(from).sendKeys(origin);
        log.info("origin city typed successfully");
        utils.waitForVisiblityOfElement(cityDropdown,5);
        utils.selectFromDropDown(cityDropdown,origin);
        log.info("selecting destination");
        driver.findElement(to).sendKeys(destination);
        utils.waitForVisiblityOfElement(cityDropdown,5);
        utils.selectFromDropDown(cityDropdown,destination);
    }
    public void selectDate(String day) throws Exception{
        log.info("Selecting date");
        if(!driver.findElement(calenderDropdown).isDisplayed()){
            wait.until(ExpectedConditions.elementToBeClickable(calenderDropdown)).click();
            //driver.findElement(calender).click();
            utils.waitForVisiblityOfElement(calenderDropdown,5);
        }
        else{
            utils.waitForVisiblityOfElement(calenderDropdown,5);
            utils.waitForVisiblityOfElement(availableFare,5);
            List<WebElement> availablefares = driver.findElements(availableFare);
            log.info("clicking date");
            utils.clickUsingJS(availablefares.get(Integer.parseInt(day)));
            //wait.until(ExpectedConditions.elementToBeClickable(availablefares.get(Integer.parseInt(day)))).click();
            //availablefares.get(Integer.parseInt(day)).click();
        }
    }
}
