package Pages;

import Runner.UtilityMethods;
import com.codoid.products.exception.FilloException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.io.FileUtils;


public class AccessRights extends UtilityMethods {
    ExcelDB ExcelDb;

    public AccessRights(WebDriver driver) {
        this.driver = driver;
    }

    public boolean SearchForDocGroup(String SearchBy, String FileNetGroupID, int TCNumber) throws FilloException, InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver,2);
        WebDriverWait wait5 = new WebDriverWait(driver,7);
        WebDriverWait wait10 = new WebDriverWait(driver,10);
        WebDriverWait wait20 = new WebDriverWait(driver,20);
        WebDriverWait wait300 = new WebDriverWait(driver,300);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        ExcelDb = new ExcelDB(driver);
        String DocType;

        try {
            WebElement NavigatorBtn = driver.findElement(By.xpath("//*[contains(@id,'dijit_form_DropDownButton_')][text()='Home']"));
            wait10.until(ExpectedConditions.visibilityOf(NavigatorBtn));
            Thread.sleep(1000);
            wait10.until(ExpectedConditions.visibilityOf(NavigatorBtn)).click();

            WebElement SelectSearchBtn = driver.findElement(By.xpath("//td[contains(@id,'dijit_MenuItem_')][text()='Search']"));
            wait5.until(ExpectedConditions.elementToBeClickable(SelectSearchBtn)).click();

            WebElement NewSearchBtn = driver.findElement(By.xpath("//span[contains(@id,'dijit_form_Button_')][text()='New Search']"));
            wait5.until(ExpectedConditions.elementToBeClickable(NewSearchBtn)).click();
            Thread.sleep(3000);

//            WebElement RetrievingInfo = driver.findElement(By.xpath("//div[@id='dijit_layout_ContentPane_20']"));
//            wait.until(ExpectedConditions.invisibilityOf(RetrievingInfo));

            WebElement SearchByDocGrp = driver.findElement(By.xpath("//input[contains(@id,'ecm_widget_search_AttributeDefinitionWidget_')][@value='Document Title']"));
            wait20.until(ExpectedConditions.elementToBeClickable(SearchByDocGrp)).clear();
            wait5.until(ExpectedConditions.elementToBeClickable(SearchByDocGrp)).sendKeys(SearchBy);
            Thread.sleep(300);

              driver.findElement(By.xpath("//div[@id='ecm_widget_search_AttributeDefinitionWidget_0_field_popup0']")).click();

            WebElement Equals = driver.findElement(By.xpath("//input[contains(@id,'ecm_widget_FilteringSelect_')][@value='Starts With']"));
            wait5.until(ExpectedConditions.visibilityOf(Equals)).clear();
            wait5.until(ExpectedConditions.visibilityOf(Equals)).sendKeys("Equals");
            Thread.sleep(300);
//            driver.findElement(By.xpath("///div[@id='ecm_widget_FilteringSelect_0_popup0']")).click();



            WebElement AddProperty = driver.findElement(By.xpath("//span[contains(@id,'dijit_form_Button_')][text()='Add Property']"));
            wait5.until(ExpectedConditions.visibilityOf(AddProperty)).click();


            WebElement SearchBySize = driver.findElement(By.xpath("//input[contains(@id,'ecm_widget_search_AttributeDefinitionWidget_1')][@value='Document Title']"));
            wait20.until(ExpectedConditions.elementToBeClickable(SearchBySize)).clear();
            wait5.until(ExpectedConditions.elementToBeClickable(SearchBySize)).sendKeys("Size");
            Thread.sleep(300);

            WebElement FileGroupSearch = driver.findElement(By.xpath("//div[@id='ecm_widget_search_AttributeDefinitionWidget_0']/div[3]/div[2]/div[1]/div[2]/input"));
            wait5.until(ExpectedConditions.visibilityOf(FileGroupSearch)).click();
            wait5.until(ExpectedConditions.visibilityOf(FileGroupSearch)).sendKeys(FileNetGroupID);

            WebElement IsNotEmpty = driver.findElement(By.xpath("//input[contains(@id,'ecm_widget_FilteringSelect_')][@value='Equals']"));
            wait5.until(ExpectedConditions.visibilityOf(IsNotEmpty)).clear();
            wait5.until(ExpectedConditions.visibilityOf(IsNotEmpty)).sendKeys("Is Not Empty");
            Thread.sleep(300);

            wait5.until(ExpectedConditions.visibilityOf(FileGroupSearch)).click();

            WebElement SearchBtn = driver.findElement(By.xpath("//span[@id='ecm_widget_search_BasicSearchDefinition_0_searchButton_label']"));
            wait5.until(ExpectedConditions.elementToBeClickable(SearchBtn)).click();

            Instant start = Instant.now();
            Thread.sleep(600);
            By LoadingSearch = By.xpath("//*[@id='ecm_widget_dialog_StatusDialog_0']/div[1]/div[4]");
            wait300.until(ExpectedConditions.invisibilityOf(driver.findElement(LoadingSearch)));


            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println("Time consumed for searching: "+timeElapsed.toString().substring(2));
            ExcelDb.SetStatus("Search_Duration", timeElapsed.toString().substring(2), TCNumber);

            WebElement SearchResult = driver.findElement(By.xpath("//*[contains(@id,'gridx_Grid_')]/div[3]/div[2]/div[contains(@rowid,'Document')][1]/table/tbody/tr"));
            wait.until(ExpectedConditions.elementToBeClickable(SearchResult)).click();

            ExcelDb.SetStatus("Search_Status", "Can", TCNumber);

            return true;

        } catch (Exception e){
            ExcelDb.SetStatus("Search_Status","Cannot",TCNumber);
            ExcelDb.SetStatus("View_Status","Cannot",TCNumber);
            ExcelDb.SetStatus("Edit_Status","Cannot",TCNumber);
            ExcelDb.SetStatus("Delete_Status","Cannot",TCNumber);
            return false;

        }


    }


    public void ViewPrivilege(int TCNumber) throws FilloException, IOException {
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebDriverWait wait20 = new WebDriverWait(driver,40);

        ExcelDb = new ExcelDB(driver);

//        try {
//            WebElement LoadingData = driver.findElement(By.xpath("//div[text()='Retrieving attribute information...']"));
//            wait.until(ExpectedConditions.visibilityOf(LoadingData));
//            wait20.until(ExpectedConditions.invisibilityOf(LoadingData));
//        } catch (Exception e){
//
//        }
        WebElement Actions = driver.findElement(By.xpath("//span[@aria-disabled='false']/span[contains(@id,'dijit_form_DropDownButton_')][text()='Actions']"));
        wait20.until(ExpectedConditions.elementToBeClickable(Actions));

        try {

            WebElement Properties = driver.findElement(By.xpath("//div[@aria-label='Properties']"));
            wait.until(ExpectedConditions.visibilityOf(Properties));
            ExcelDb.SetStatus("View_Status","Can",TCNumber);
        } catch (Exception e){
            ExcelDb.SetStatus("View_Status","Cannot",TCNumber);
        }

    }

    public void EditPrivilege(int TCNumber) throws FilloException, IOException {
        WebDriverWait wait = new WebDriverWait(driver,5);
        ExcelDb = new ExcelDB(driver);
        try {
            WebElement EditProperties = driver.findElement(By.xpath("//*[@class='editPropertiesLinkAction']/a[1]"));
            wait.until(ExpectedConditions.visibilityOf(EditProperties));
//            wait.until(ExpectedConditions.elementToBeClickable(EditProperties));
            ExcelDb.SetStatus("Edit_Status","Can",TCNumber);
        } catch (Exception e){
            ExcelDb.SetStatus("Edit_Status","Cannot",TCNumber);
        }

    }


    public void DeletePrivilege(int TCNumber) throws FilloException, IOException {
        WebDriverWait wait = new WebDriverWait(driver,5);
        ExcelDb = new ExcelDB(driver);

        try {
            WebElement Actions = driver.findElement(By.xpath("//span[@aria-disabled='false']/span[contains(@id,'dijit_form_DropDownButton_')][text()='Actions']"));
            wait.until(ExpectedConditions.elementToBeClickable(Actions)).click();

            WebElement DeleteBtn = driver.findElement(By.xpath("//tr[contains(@id,'dijit_MenuItem_')][@aria-label='Delete ']"));
            String Disabled = wait.until(ExpectedConditions.visibilityOf(DeleteBtn)).getAttribute("aria-disabled");
            if (Disabled.contains("f")) {
                ExcelDb.SetStatus("Delete_Status", "Can", TCNumber);
            } else {
                ExcelDb.SetStatus("Delete_Status","Cannot",TCNumber);
            }
        } catch (Exception e){
            ExcelDb.SetStatus("Delete_Status","Cannot",TCNumber);
        }

    }




    public ArrayList<String> ExpectedResults(String AccessR, int TCNumber) throws Exception {
        ArrayList<String> Expected = new ArrayList<String>();
        String Access_Right = AccessR;
        switch (Access_Right){
            case "Admin":
                Expected.add("Can");
                Expected.add("Can");
                Expected.add("Can");
                Expected.add("Can");
                Expected.add("Can");
                break;
            case "Normal":
                Expected.add("Can");
                Expected.add("Can");
                Expected.add("Can");
                Expected.add("Can");
                Expected.add("Cannot");
                break;
            case "Read_Only":
                Expected.add("Can");
                Expected.add("Can");
                Expected.add("Can");
                Expected.add("Cannot");
                Expected.add("Cannot");
                break;
            case "Restricited":
                Expected.add("Can");
                Expected.add("Cannot");
                Expected.add("Cannot");
                Expected.add("Cannot");
                Expected.add("Cannot");
                break;
        }
        System.out.println("Expected Results:   "+Expected);
        return Expected;
    }


    }
