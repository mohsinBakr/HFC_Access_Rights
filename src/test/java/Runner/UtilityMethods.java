package Runner;

import Pages.ExcelDB;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


import java.util.concurrent.TimeUnit;


public class UtilityMethods {
    public static String currentOs = System.getProperty("os.name").toLowerCase();
    public static String stChromeLinux = System.getProperty("user.dir")+"/drivers/linux/chromedriver";
    public static String stGeckoLinux = System.getProperty("user.dir")+"/drivers/linux/geckodriver";
    public static String stChromeWin = System.getProperty("user.dir")+"/drivers/windows/chromedriver.exe";
    public static String stGeckoWin = System.getProperty("user.dir")+"/drivers/windows/geckodriver.exe";
    public static WebDriver driver;
    boolean HeadLessFirefox = false;
    boolean HeadLessChrome = false;


    //public UtilityMethods(WebDriver driver) {
    //    this.driver = driver;
    //}


    public void init(BrowserEnum Browser){
        SetDriver(Browser);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://10.1.40.36:9080/navigator/?desktop=UserON");
    }

    public void TearDown(){
        driver.quit();
    }


    public void SetDriver(BrowserEnum browser)
    {
        switch(browser) {
            case Chrome:
                if (currentOs.toLowerCase().contains("linux")|| currentOs.toLowerCase().contains("mac os x"))
                {
                    System.setProperty("webdriver.chrome.driver", stChromeLinux);
                    if (!HeadLessChrome) {
                        driver = new ChromeDriver();
                    } else {
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
                        driver = new ChromeDriver(options);
                    }
                }
                else if (currentOs.toLowerCase().contains("windows"))
                {
                    System.setProperty("webdriver.chrome.driver",stChromeWin);
                    driver = new ChromeDriver();
                }
                break;
            case Firefox:
                if (currentOs.toLowerCase().contains("linux")|| currentOs.toLowerCase().contains("mac os x"))
                {

                    System.setProperty("webdriver.gecko.driver",stGeckoLinux);
                    FirefoxBinary firefoxBinary = new FirefoxBinary();
                    FirefoxOptions options = new FirefoxOptions();
                    options.setBinary(firefoxBinary);
                    options.setHeadless(HeadLessFirefox);
                    driver = new FirefoxDriver(options);
                }
                else if (currentOs.toLowerCase().contains("windows"))
                {
                    System.setProperty("webdriver.gecko.driver",stGeckoWin);
                    driver = new FirefoxDriver();
                }
                break;
        }

    }
    public enum BrowserEnum {

        Chrome,
        Firefox

    }




}

