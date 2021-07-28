package com.vytrack.pages;

import com.vytrack.utilities.BrowserUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.security.Key;
import java.util.List;

public class CalenderEventsPage extends BasePage {

    @FindBy(xpath = "//div[contains(text(),'Options')]")
    public WebElement options;

    @FindBy(xpath = "//ul[@class='icons-holder']//input")
    public WebElement pageNumber;

    @FindBy(xpath = "//button[@class=\"btn dropdown-toggle \"]")
    public WebElement viewPerPage;

    @FindBy(xpath = "(//a[@class='dropdown-item'])[2]")
    public WebElement viewPerPage25;

    @FindBy(xpath = "//label[contains(text(),'Total of')]")
    public WebElement numberOfRecordsOfEvents;

    @FindBy(xpath = "(//div[@class='btn-group dropdown'])[1]//input")
    public WebElement selectAllCheckBox;

    @FindBy(xpath = "//tr/td/input")
    public List<WebElement> checkBoxes;

    @FindBy(css = "i.fa-filter.hide-text")
    public WebElement filterBtn;

    @FindBy(xpath = "//div[contains(text(),'Title')]")
    public WebElement titleBtn;

    @FindBy (xpath = "//input[@name ='value']")
    public WebElement textBox;

    @FindBy (css = "table>tbody>tr")
    public List<WebElement> numOfRows;

    @FindBy(xpath = "//span[@class='grid-header-cell__label']")
    public List<WebElement> testersMeetingTitles;

    @FindBy(xpath = "//tr[@class='grid-row'][4]/td[contains(@class,'-cell grid-cell grid-body-cell grid')]")
    public List<WebElement> testersMeetingRow;



    public String numberOfRecords(String sendKeys){

        filtersWithTitle(sendKeys);
        BrowserUtils.waitFor(10);
        return numOfRows.size() + "";

    }


    public void filtersWithTitle(String sendKeys){

        // filterBtn.click();
        BrowserUtils.waitForClickablility(filterBtn,10).click();
        // titleBtn.click();
        BrowserUtils.waitForClickablility(titleBtn,5).click();

        textBox.sendKeys(sendKeys + Keys.ENTER);
        BrowserUtils.waitFor(5);

    }

}
