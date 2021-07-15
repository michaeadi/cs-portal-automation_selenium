package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AdjustmentHistoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AdjustmentHistory extends BasePage {
    AdjustmentHistoryPage pageElements;
    public AdjustmentHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AdjustmentHistoryPage.class);
    }

    /**
     *This method will be use to read widget header name based on column number
     * @param column The column number
     * @return String The value
     */
    public String getHeaderValue(int column) {
        String text = driver.findElements(pageElements.headerRow).get(column).getText();
        commonLib.info(text);
        return text;
    }

    /**
     * This Method will give us action trail table value by providing row and column
     * @param row The row Number
     * @param column The Column Number
     * @return String The value
     */
    public String getValue(int row, int column) {
        String text = getText(By.xpath(pageElements.adjustmentRow + row + pageElements.adjustmentColumn + column + "]"));
        commonLib.info("Value: " + text);
        return text;
    }
}
