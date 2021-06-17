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
     *This method use to get header name based on row number and widget identifier
     * @param widgetIdentifier The Widget unique identifier
     * @param columnNumber The column number
     * @return String The header name
     */
    public String getHeaderName(String widgetIdentifier,int columnNumber){
        By elementLocation=By.xpath(widgetIdentifier+pageElements.widgetHeader);
        String value=readTextOnRows(elementLocation,columnNumber);
        commonLib.info("Getting header Number " + columnNumber + " : " + value);
        return value;
    }

    /**
     *This method use to get column data value based on row number and widget identifier
     * @param widgetIdentifier The Widget unique identifier
     * @param rowNumber The data row number
     * @param columnNumber The column number
     * @return String The header name
     */
    public String getColumnValue(String widgetIdentifier,int rowNumber,int columnNumber){
        By rowElement=By.xpath(widgetIdentifier+pageElements.widgetColumnRows);
        String value=readOnRowColumn(rowElement,By.xpath(pageElements.widgetColumnValue),rowNumber,columnNumber);
        commonLib.info("Reading Row("+rowNumber+") : Column(" + columnNumber + ") = " + value);
        return value;
    }

    /**
     * This method use to click widget menu button based on widget identifier
     * @param widgetIdentifier The Widget unique identifier
     */
    public void clickMenuButton(String widgetIdentifier){
        By elementLocation=By.xpath(widgetIdentifier+pageElements.menuButton);
        commonLib.info("Clicking Menu button");
        clickAndWaitForLoaderToBeRemoved(elementLocation);
    }

    /**
     * This method use to get total number of data rows based on widget identifier
     * @param widgetIdentifier The Widget unique identifier
     * @return Integer The size
     */
    public Integer getNumberOfDataRows(String widgetIdentifier){
        commonLib.info("Reading Number of elements");
        By elementLocation=By.xpath(widgetIdentifier+pageElements.widgetColumnRows);
        return returnListOfElement(elementLocation).size();
    }


}
