package com.flynas.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class BaseUtils {
    private final ThreadLocal<WebDriver> driverContainer = new ThreadLocal<>();

    public WebDriver getDriver(){
        return driverContainer.get();
    }
    @BeforeMethod
    @Parameters({"browser","executionPlatform"})
    public void launchURL(@Optional String xmlBrowser,@Optional String executionPlatform) throws MalformedURLException {
        String targetBrowser = (xmlBrowser!=null)?xmlBrowser.toLowerCase().trim():ConfigReader.getProperty("browser").toLowerCase().trim();
        String platform = (executionPlatform != null && !executionPlatform.isEmpty())
                ? executionPlatform.toLowerCase().trim()
                : ConfigReader.getProperty("execution.platform").toLowerCase().trim();
        WebDriver driver;
        String isHeadless = ConfigReader.getProperty("headless");
        if (platform.equalsIgnoreCase("grid")) {
            URL gridUrl = URI.create(ConfigReader.getProperty("selenium.grid.url")).toURL();

            switch (targetBrowser) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless.equalsIgnoreCase("true")) firefoxOptions.addArguments("-headless");
                    driver = new RemoteWebDriver(gridUrl, firefoxOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (isHeadless.equalsIgnoreCase("true")) edgeOptions.addArguments("--headless=new");
                    driver = new RemoteWebDriver(gridUrl, edgeOptions);
                    break;

                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless.equalsIgnoreCase("true")) chromeOptions.addArguments("--headless=new");
                    driver = new RemoteWebDriver(gridUrl, chromeOptions);
                    break;
            }
        }else{
        switch (targetBrowser) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                ChromeOptions options = new ChromeOptions();
                if (ConfigReader.getProperty("headless").equalsIgnoreCase("true")) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                }
                driver = new ChromeDriver(options);
                break;
        }
        }
        int implicitWaitTimeOut  = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTimeOut));
        driver.manage().window().maximize();
        driverContainer.set(driver);
        getDriver().get(ConfigReader.getProperty("flynas.url"));
    }
    @AfterMethod
    public void quitBrowser(ITestResult result){
        if(getDriver()!=null) {
            getDriver().quit();
        }
        driverContainer.remove();
    }

}
