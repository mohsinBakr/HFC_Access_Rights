package Pages;

import Runner.UtilityMethods;
import com.codoid.products.exception.FilloException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.IOException;

public class Login extends UtilityMethods {
    ExcelDB ExcelDb;

    public Login(WebDriver driver) {
        this.driver = driver;
    }

    public  boolean FileNetLogin(String UN, String PW, int TCNumber) throws InterruptedException, FilloException, IOException {
        WebDriverWait wait5 = new WebDriverWait(driver,5);
        WebDriverWait wait10 = new WebDriverWait(driver,50);
        WebDriverWait wait20 = new WebDriverWait(driver,80);
        ExcelDb = new ExcelDB(driver);

        try {

            WebElement UserName = driver.findElement(By.xpath("//input[@id = 'ecm_widget_layout_NavigatorMainLayout_0_LoginPane_username']"));
            WebElement Password = driver.findElement(By.xpath("//input[@id = 'ecm_widget_layout_NavigatorMainLayout_0_LoginPane_password']"));
            WebElement LoginBtn = driver.findElement(By.xpath("//span[@id = 'ecm_widget_layout_NavigatorMainLayout_0_LoginPane_LoginButton']"));

            wait10.until(ExpectedConditions.visibilityOf(UserName)).sendKeys(UN);
            wait5.until(ExpectedConditions.visibilityOf(Password)).sendKeys(PW);
            Thread.sleep(1000);
            wait5.until(ExpectedConditions.visibilityOf(LoginBtn)).click();
            wait5.until(ExpectedConditions.invisibilityOf(LoginBtn));
        WebElement NavigatorBtn = driver.findElement(By.xpath("//*[contains(@id,'dijit_form_DropDownButton_')][text()='Home']"));
        wait20.until(ExpectedConditions.visibilityOf(NavigatorBtn));
            ExcelDb.SetStatus("Login_Status","Can",TCNumber);
            return true;
        }catch (Exception e){
            ExcelDb.SetStatus("Login_Status","Cannot",TCNumber);
            ExcelDb.SetStatus("Search_Status","Cannot",TCNumber);
            ExcelDb.SetStatus("View_Status","Cannot",TCNumber);
            ExcelDb.SetStatus("Edit_Status","Cannot",TCNumber);
            ExcelDb.SetStatus("Delete_Status","Cannot",TCNumber);

            return false;

        }
    }


}
