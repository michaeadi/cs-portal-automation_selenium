package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.WidgetCommonPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class WidgetCommonMethod extends BasePage {
    WidgetCommonPage pageElements;

    public WidgetCommonMethod(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, WidgetCommonPage.class);
    }

    /**
     *
     * @param widgetIdentifier
     * @param columnNumber
     * @return
     */
    public String getHeaderValue(String widgetIdentifier,int columnNumber){
        By elementLocation=By.xpath(widgetIdentifier+pageElements.widgetHeader);
        String value=readTextOnRows(elementLocation,columnNumber);
        commonLib.info("Getting header Number " + columnNumber + " : " + value);
        return value;
    }

    public String getColumnValue(String widgetIdentifier,int rowNumber,int columnNumber){
        By rowElement=By.xpath(widgetIdentifier+pageElements.widgetColumnRows);
        String value=readOnRowColumn(rowElement,By.xpath(pageElements.widgetColumnValue),rowNumber,columnNumber);
        commonLib.info("Reading Row("+rowNumber+") : Column(" + columnNumber + ") = " + value);
        return value;
    }

    public void clickMenuButton(String widgetIdentifier){
        By elementLocation=By.xpath(widgetIdentifier+pageElements.menuButton);
        commonLib.info("Clicking Menu button");
        clickAndWaitForLoaderToBeRemoved(elementLocation);
    }

    public Integer getNumberOfDataRows(String widgetIdentifier){
        commonLib.info("Reading Number of elements");
        By elementLocation=By.xpath(widgetIdentifier+pageElements.widgetColumnRows);
        return returnListOfElement(elementLocation).size();
    }


}
