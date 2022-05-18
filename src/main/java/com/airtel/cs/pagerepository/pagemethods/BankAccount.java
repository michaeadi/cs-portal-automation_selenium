package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.BankAccountPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BankAccount extends BasePage{
    BankAccountPage pageElements;

    public BankAccount(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, BankAccountPage.class);
    }

    /**
     * This method is used get first header value based on passed row and column
     * @param row
     * @param column
     * @return
     */
    public String getHeaderValue(int row, int column) {
        String result;
        result = getText(By.xpath(pageElements.dataRows + row + pageElements.dataColumns + column + pageElements.dataValue));
        commonLib.info("Reading Value(" + row + "): " + result);
        return result;
    }

    /**
     * This method is used get colour of header value based on passed row and column
     * @param row
     * @param column
     * @return
     */
    public String getHeaderValueColor(int row, int column) {
        return selUtils.getDataPointColor(By.xpath(pageElements.dataRows + row + pageElements.dataColumns + column + pageElements.dataValue));
    }

}
