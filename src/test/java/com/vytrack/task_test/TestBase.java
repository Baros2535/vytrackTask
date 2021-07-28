package com.vytrack.task_test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.vytrack.pages.CalenderEventsPage;
import com.vytrack.pages.LoginPage;
import com.vytrack.utilities.BrowserUtils;
import com.vytrack.utilities.ConfigurationReader;
import com.vytrack.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver; //protected=> (public de olurdu, subchild public mor evisible olmasi gereken subchild) Login Test den erisimesi icin default yetmez o yuzden protected yaptik (ingeritence -extends olan class dan erisilmesi icin)
    protected Actions actions;
    protected WebDriverWait wait;

    protected LoginPage loginPage;
    protected CalenderEventsPage calenderEventsPage;

    //this class is used for starting and building reports
    public ExtentReports report; //problem olursa static ekle
    // protected static ExtentReports report;

    //this class is used create HTML report file
    public ExtentHtmlReporter htmlReporter;
    //protected static ExtentHtmlReporter htmlReporter; //problem olursa static ekle

    //this will define a test, enables adding logs, authors, test steps
    public ExtentTest extentLogger;
    //protected static ExtentTest extentLogger; //problem olursa static ekle

    @BeforeTest
    public void setUpTest(){


        //initialize the class
        report = new ExtentReports();

        //create a report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/test-output/report.html";  // report.html isimli report file i olusturacak kendisi,  => /test-output   bu da path i olacak

        //bu alttaki 2 satiri ekler ve line 43 u komment e alirsak, yukardaki gibi override etmeyecek herseferinde, bu yuzden her seferinde yeni isimle yeni report hazirlar
//        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//        String path = projectPath + "/test-output/+ report"+ date +".html";


        //initialize the html reporter with the report path
        htmlReporter = new ExtentHtmlReporter(path); // creating an object from Constructor and passing my path in it, i will save my report file in path( projectPath + "/test-output/report.html)

        //attach the html report to report object
        report.attachReporter(htmlReporter);  // creating connection my main object report to html report-file

        //title in report
        htmlReporter.config().setReportName("Vytrack Smoke Test");

        //set environment information
        report.setSystemInfo("Environment", "QA");
        report.setSystemInfo("Browser", ConfigurationReader.get("browser"));
        report.setSystemInfo("OS",System.getProperty("os.name"));

    }

    @BeforeMethod
    public void SetUp(){
        driver = Driver.get();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);  //implicit wait de HTML codeunda aradigimiz htmel  locate inin gorunmesini bekliyoruz
        // NosuchElement exception verir
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, 10); // aradigimiz sey htm de var locate edebilmisiz ama saafanin yuklemnmesini bekliyoruz aradigimiz seyi sayfada laoding edilmesii
        //TimeOUT exception veriri
        driver.get(ConfigurationReader.get("url")); // url i aciyoruz

        //  Driver.get().get(ConfigurationReader.get("url")); ==> bu da olurdu

        loginPage = new LoginPage();
        calenderEventsPage = new CalenderEventsPage();

    }

    //ITestResult class describes the result of a test in TestNG
    @AfterMethod
    public void tearDown(ITestResult result) throws InterruptedException, IOException {
        //if test fails
        if (result.getStatus()==ITestResult.FAILURE){        //result.getStatus() => get the results of current executed test
            // record the name of failed test case
            extentLogger.fail(result.getName());

            //take the screenshot and return location of screenshot
            String screenShotPath= BrowserUtils.getScreenshot(result.getName()); //result.getName() => get the name of test cases

            //add your screenshot to your report
            extentLogger.addScreenCaptureFromPath(screenShotPath);

            //capture the exception and put inside the report
            extentLogger.fail(result.getThrowable()); // putting my exception of the fail into my report

        }

        Thread.sleep(2000);
        Driver.closeDriver();
    }



    @AfterTest
    public void tearDownTest(){
        //this is when the report is actually created
        report.flush();
    }




}
