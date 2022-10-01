package Runner;

import Pages.*;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;
import gherkin.lexer.En;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class RunTest extends UtilityMethods {
    //Select Phase by changin TCsFile and SearchBy
    public static String TCsFile = "Test_data.xlsx";

    ExcelDB ExcelDb;
    Login login;
    AccessRights accessRights;
    int CountTC;
    String UN;
    String PW;
    String SearchBy;
    String Access_Right;


    @Test(priority = 1)
    public void GetIterations() throws Exception {

        ExcelDb = new ExcelDB(driver);
        CountTC = ExcelDb.NumOfTestCases();
        System.out.println("Total Number of TCs: "+ CountTC);
    }

    @Test(priority = 2)
    public void Execution() throws Exception{
        ExcelDb = new ExcelDB(driver);
        login = new Login(driver);
        accessRights = new AccessRights(driver);
        boolean SearchStatus;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        for (int i = 1; i <= CountTC; i++){
            Date date1 = new Date();
            String time1 = formatter.format(date1);
            System.out.println(time1);
            long Start = formatter.parse(time1).getTime();

            //Starting Browser and navigate to FileNet URL
            try {
                init(BrowserEnum.Chrome);
            }catch (Exception e){
                TearDown();
                Thread.sleep(20000);
                init(BrowserEnum.Chrome);
            }
            System.out.println("Starting Test Case: "+i);
            //Getting test data from test data sheet
            UN = ExcelDb.GetDataFromExcel("User_Name",i);
            PW = ExcelDb.GetDataFromExcel("Password",i);
            if (TCsFile.contains("2")) {
                SearchBy = ExcelDb.GetDataFromExcel("File_Category", i);
            } else {
                SearchBy = ExcelDb.GetDataFromExcel("File_Group", i);
            }
            Access_Right = ExcelDb.GetDataFromExcel("Access_Right",i);
            //Starting Login
            boolean LoginStatus = login.FileNetLogin(UN,PW,i);
            //Starting Search for files group
            if (LoginStatus) {

                if (TCsFile.contains("2")) {
                     SearchStatus = accessRights.SearchForDocGroup("Document Category", SearchBy, i);
                } else {
                     SearchStatus = accessRights.SearchForDocGroup("Document Group", SearchBy, i);
                }
                if (SearchStatus) {
                    //Try to view document properties
                    accessRights.ViewPrivilege(i);
                    //Try to Edit document properties
                    accessRights.EditPrivilege(i);
                    //Check delete button enabled or not
                    accessRights.DeletePrivilege(i);
                }
            }
            //Closing the driver after each TC to clear the cached data and cookies
            Thread.sleep(1000);

            if (ExcelDb.GetStatusFromExcel(i).equals(accessRights.ExpectedResults(Access_Right, i))){
                ExcelDb.SetStatus("Final_Status","Pass",i);
            }else{
                ExcelDb.SetStatus("Final_Status","Fail",i);
                System.out.println("Adding Screenshot..........");
                TakesScreenshot scrShot =((TakesScreenshot)driver);
                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
                File DestFile=new File(System.getProperty("user.dir")+"/ScreenShots/TC_"+i+".png");
                FileUtils.copyFile(SrcFile, DestFile);
            }
            try {
                driver.quit();
            } catch (Exception e){
                Thread.sleep(5000);
                driver.quit();
            }
            Date date2 = new Date();
            String time2 = formatter.format(date2);
            System.out.println(time2);
            long End = formatter.parse(time2).getTime();
            long Duration = End - Start;
            ExcelDb.SetDuration(Duration/1000,i);

        }
    }

    @Test(priority = 3)
    public void TearDown(){
    driver.quit();
    }

}
