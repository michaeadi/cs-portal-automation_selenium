package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ActionTrailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ActionTrail extends BasePage {
    ActionTrailPage pageElements;

    public ActionTrail(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ActionTrailPage.class);
    }

    public String getHeaderValue(int i) {
        String text = driver.findElements(pageElements.headRow).get(i).getText();
        commonLib.info(text);
        return text;
    }

    public Integer getNumberOfHeadRow() {
        return driver.findElements(pageElements.headRow).size();
    }

    public Integer getNumberOfColumns() {
        return driver.findElements(pageElements.detailRow).size();
    }

    /*
    This Method will give us action trail table value by providing row and column
     */
    public String getValue(int row, int column) {
        String text = getText(By.xpath(pageElements.actionTrailRow + row + pageElements.actionTrailColumn + column + "]"));
        commonLib.info("Value: " + text);
        return text;
    }
}
