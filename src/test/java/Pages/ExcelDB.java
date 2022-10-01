package Pages;
import Runner.RunTest;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelDB {
    WebDriver driver;
    String TestDataFile = RunTest.TCsFile;


    public ExcelDB(WebDriver driver) {
        this.driver = driver;
    }

    public String GetDataFromExcel(String DataColumn, int TCNumber) throws Exception {
        String Result = "";
        Fillo fillo=new Fillo();
        Connection conn = fillo.getConnection(System.getProperty("user.dir")+"/"+TestDataFile);
        String strQuery="Select * from TestData where TC = "+TCNumber;
        Recordset recordset= conn.executeQuery(strQuery);

        while(recordset.next()){
            Result = recordset.getField(DataColumn);
            System.out.println(DataColumn+":   "+Result);
        }

        recordset.close();
        conn.close();
        return Result;
    }
    public int NumOfTestCases() throws Exception{
        Fillo fillo=new Fillo();
        int Num=0;
        Connection conn = fillo.getConnection(System.getProperty("user.dir")+"/"+TestDataFile);
            String strQuery="Select TC from TestData";
            Recordset recordset= conn.executeQuery(strQuery);
            Num = recordset.getCount();

        return Num;


    }
    public void SetDuration(long Time, int Step) throws FilloException {
        Fillo fillo=new Fillo();
        Connection conn = fillo.getConnection(System.getProperty("user.dir")+"/"+TestDataFile);
        String strQuery="update TestData set Duration_in_Sec='"+Time+"' where TC = "+Step;
        System.out.println(strQuery);
        conn.executeUpdate(strQuery);
        conn.close();
    }
    public void SetStatus(String ColName, String Result, int TCNumber) throws FilloException, IOException {
        Fillo fillo=new Fillo();
        Connection conn = fillo.getConnection(System.getProperty("user.dir")+"/"+TestDataFile);
        String strQuery="update TestData set "+ColName+"='"+Result+"' where TC = "+TCNumber;
        System.out.println(strQuery);
        conn.executeUpdate(strQuery);
        conn.close();

    }

    public ArrayList<String> GetStatusFromExcel( int TCNumber) throws Exception {
        ArrayList<String> Result = new ArrayList<String>();
        Fillo fillo=new Fillo();
        Connection conn = fillo.getConnection(System.getProperty("user.dir")+"/"+TestDataFile);
        String strQuery="Select * from TestData where TC = "+TCNumber;
        Recordset recordset= conn.executeQuery(strQuery);

        while(recordset.next()){
            Result.add(recordset.getField("Login_Status"));
            Result.add(recordset.getField("Search_Status"));
            Result.add(recordset.getField("View_Status"));
            Result.add(recordset.getField("Edit_Status"));
            Result.add(recordset.getField("Delete_Status"));
            System.out.println("Status Results:   "+Result);
        }

        recordset.close();
        conn.close();
        return Result;
    }




    }
