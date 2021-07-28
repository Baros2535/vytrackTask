package com.vytrack.task_test;


import com.vytrack.pages.CalenderEventsPage;
import com.vytrack.pages.DashboardPage;
import com.vytrack.pages.LoginPage;
import com.vytrack.utilities.BrowserUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class Task extends TestBase {


//        1. Go to “https://qa1.vytrack.com/"
//        2. Login as a store manager
//        3. Navigate to “Activities -> Calendar Events”
//        4. Verify that page subtitle "Options" is displayed
   @Test
    public void test1(){


       extentLogger = report.createTest("Test 1");
       extentLogger.info("Login as Store Manager");
       loginPage.loginAsStoreManager();
       extentLogger.info("Navigate to “Activities -> Calendar Events”");
       new DashboardPage().navigateToModule("Activities","Calendar Events");
       new CalenderEventsPage().waitUntilLoaderScreenDisappear();
       extentLogger.info("Verify that page subtitle 'Options' is displayed");
       Assert.assertTrue(calenderEventsPage.options.isDisplayed(), "Verify that page subtitle 'Options' is displayed");

       extentLogger.pass("Test 1 PASSED");
    }

//    Test case #2
//            1. Go to “https://qa1.vytrack.com/"
//            2. Login as a store manager
//            3. Navigate to “Activities -> Calendar Events”
//            4. Verify that page number is equals to "1"
    @Test
    public void test2(){

        extentLogger = report.createTest("Test 2");
        extentLogger.info("Login as Store Manager");
        loginPage.loginAsStoreManager();
        extentLogger.info("Navigate to “Activities -> Calendar Events”");
        new DashboardPage().navigateToModule("Activities","Calendar Events");
        new CalenderEventsPage().waitUntilLoaderScreenDisappear();
        extentLogger.info("Verify that page number is equals to 1");
        Assert.assertEquals(calenderEventsPage.pageNumber.getAttribute("value"), "1", "Verify that page number is equals to 1");

        extentLogger.pass("Test 2 PASSED");
    }

//    Test case #3
//            1. Go to “https://qa1.vytrack.com/"
//            2. Login as a store manager
//            3. Navigate to “Activities -> Calendar Events”
//            4. Verify that view per page number is equals to"25"
    @Test
    public void test3(){

        extentLogger = report.createTest("Test 3");
        extentLogger.info("Login as Store Manager");
        loginPage.loginAsStoreManager();
        extentLogger.info("Navigate to “Activities -> Calendar Events”");
        new DashboardPage().navigateToModule("Activities","Calendar Events");
        new CalenderEventsPage().waitUntilLoaderScreenDisappear();
        extentLogger.info("Verify that view per page number is equals to 25");
        Assert.assertEquals(calenderEventsPage.viewPerPage25.getAttribute("data-size"), "25", "Verify that view per page number is equals to 25");

        extentLogger.pass("Test 3 PASSED");
    }

//    Test case #4
//            1. Go to “https://qa1.vytrack.com/"
//            2. Login as a store manager
//            3. Navigate to “Activities -> Calendar Events”
//            4. Verify that number of calendar events (rows in the table) is equals to number of records

    @Test
    public void test4(){

        extentLogger = report.createTest("Test 4");
        extentLogger.info("Login as Store Manager");
        loginPage.loginAsStoreManager();
        extentLogger.info("Navigate to “Activities -> Calendar Events”");
        new DashboardPage().navigateToModule("Activities","Calendar Events");
        new CalenderEventsPage().waitUntilLoaderScreenDisappear();
        extentLogger.info("Verify that number of calendar events (rows in the table) is equals to number of records");

        String actualRowNumber = calenderEventsPage.numberOfRecords("testers meeting");
        String expectedEventsNumber = calenderEventsPage.numberOfRecordsOfEvents.getText().split(" ")[2].trim();

        Assert.assertEquals(actualRowNumber, expectedEventsNumber, "Verify that number of calendar events (rows in the table) is equals to number of records");
        extentLogger.pass("Test 4 PASSED");
    }


//    Test Case #5
//            1. Go to “https://qa1.vytrack.com/"
//            2. Login as a store manager
//            3. Navigate to “Activities -> Calendar Events”
//            4. Click on the top checkbox to select all
//            5. Verify that all calendar events were selected


    @Test
    public void test5(){

        extentLogger = report.createTest("Test 5");
        extentLogger.info("Login as Store Manager");
        loginPage.loginAsStoreManager();
        extentLogger.info("Navigate to “Activities -> Calendar Events”");
        new DashboardPage().navigateToModule("Activities","Calendar Events");
        new CalenderEventsPage().waitUntilLoaderScreenDisappear();
        extentLogger.info("Click on the top checkbox to select all");

        BrowserUtils.clickWithJS(calenderEventsPage.selectAllCheckBox);

        List<WebElement> checkBoxesElement = calenderEventsPage.checkBoxes;

        int i =1;

        for (WebElement element : checkBoxesElement) {
            Assert.assertTrue(element.isSelected(), "Verify that all calendar events were selected" + i++);
        }


        extentLogger.pass("Test 5 PASSED");

    }

//    Test Case #6
//            1. Go to “https://qa1.vytrack.com/"
//            2. Login as a store manager
//            3. Navigate to “Activities -> Calendar Events”
//            4. Select “Testers meeting”
//            5. Verify that following data is displayed:


    @Test
    public void test6(){

        extentLogger = report.createTest("Test 6");
        extentLogger.info("Login as Store Manager");
        loginPage.loginAsStoreManager();
        extentLogger.info("Navigate to “Activities -> Calendar Events”");
        new DashboardPage().navigateToModule("Activities","Calendar Events");
        new CalenderEventsPage().waitUntilLoaderScreenDisappear();
        extentLogger.info("Select “Testers meeting”");

        calenderEventsPage.filtersWithTitle("testers meeting");

        extentLogger.info(" 5. Verify that following data is displayed:");
        String [] [] expected={
                {"TITLE","Testers meeting"},
                {"CALENDAR","Stephan Haley"},
                {"START","Nov 27, 2020, 9:30 AM"},
                {"END","Nov 27, 2020, 10:30 AM"},
                {"RECURRENT","No"},
                {"RECURRENCE","N/A"},
                {"INVITATION STATUS","Not responded"}};

        String [][] actual = new String[7][2];

        int i =0;


        for (int j = 0; j < actual.length; j++) {
                actual[j][0] =  calenderEventsPage.testersMeetingTitles.get(j).getText();
                actual[j][1] =  calenderEventsPage.testersMeetingRow.get(j).getText();
            }

        System.out.println("Arrays.deepToString(actual) = " + Arrays.deepToString(actual));
        System.out.println("Arrays.deepToString(expected) = " + Arrays.deepToString(expected));

        Assert.assertTrue(Arrays.deepEquals(actual, expected));
        extentLogger.pass("Test 6 PASSED");

    }


}


