package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.PlanAndPackDetailedWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class PlanAndPackDetailedWidget extends BasePage{

    PlanAndPackDetailedWidgetPage pageElements;
    List<WebElement> rows;
    private final String SCROLL_TO_WIDGET_MESSAGE=config.getProperty("scrollToWidgetMessage");

    public PlanAndPackDetailedWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, PlanAndPackDetailedWidgetPage.class);
    }

    /**
     * This method use to check whether plan and pack widget display or not
     * @return true/false
     * */
    public Boolean isPlanWidgetDisplay(){
        commonLib.info("Checking that Plan widget Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.planTitle);
            status=isElementVisible(pageElements.planTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return  status;
    }

    /**
     * This method use to check whether  pack widget display or not
     * @return true/false
     * */
    public Boolean isPackWidgetDisplay(){
        commonLib.info("Checking that Pack widget Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.packTitle);
            status=isElementVisible(pageElements.packTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return  status;
    }

    /*
       This Method will give us footer auuid shown in current plan pack widget
       UHW = current plan pack Widget
        */
    public String getPackFooterAuuidUHW() {
        commonLib.info(getText(pageElements.footerPackAuuid));
        return getText(pageElements.footerPackAuuid);
    }

    /*
    This Method will give us auuid shown in the middle of the current plan pack widget
    UHW = current plan pack Widget
     */
    public String getPackMiddleAuuidUHW() {
        String result;
        result = getAttribute(pageElements.middlePackAuuid, "data-auuid", false);
        commonLib.info(result);
        return result;
    }

    /*
       This Method will give us footer auuid shown in current plan pack widget
       UHW = current plan pack Widget
        */
    public String getPlanFooterAuuidUHW() {
        commonLib.info(getText(pageElements.footerPlanAuuid));
        return  getText(pageElements.footerPlanAuuid);
    }

    /*
    This Method will give us auuid shown in the middle of the current plan pack widget
    UHW = current plan pack Widget
     */
    public String getPlanMiddleAuuidUHW() {
        String result;
        result = getAttribute(pageElements.middlePlanAuuid, "data-auuid", false);
        commonLib.info(result);
        return result;
    }




    /**
     * This method use to get current plan widget more icon displayed or not
     * @return Boolean The  data value
     * */
    public Boolean isActionIconVisibleOnCurrentPlan() {
        Boolean status=isElementVisible(pageElements.currentPlanDetailed);
        commonLib.info("Checking more icon is visible on current plan widget: '"+status);
        return status;
    }

    /*
       This Method will give us product name (add on bundles)
        */
    public String getProductName() {
        commonLib.info(getText(pageElements.productName));
        return getText(pageElements.productName);
    }

    /*
       This Method will give us category column name (add on bundles)
        */
    public String getCategory() {
        commonLib.info(getText(pageElements.category));
        return getText(pageElements.category);
    }

    /*
       This Method will give us benefit column name (add on bundles)
        */
    public String getBenefit() {
        commonLib.info(getText(pageElements.benefit));
        return getText(pageElements.benefit);
    }
    /*
       This Method will give us used column name (add on bundles)
        */
    public String getUsed() {
        commonLib.info(getText(pageElements.used));
        return getText(pageElements.used);
    }
    /*
       This Method will give us available column name (add on bundles)
        */
    public String getAvailable() {
        commonLib.info(getText(pageElements.available));
        return getText(pageElements.available);
    }

    /*
       This Method will give us active pack tab name
        */
    public String getActivePacks() {
        commonLib.info(getText(pageElements.activePackWidgetTab));
        return getText(pageElements.activePackWidgetTab);
    }

    /*
       This Method will give us active pack detail plan name
        */
    public String getActivePackDetailsPlan() {
        commonLib.info(getText(pageElements.activePackPlanDetails));
        return getText(pageElements.activePackPlanDetails);
    }

    /*
       This Method will give us active pack Add on bundle name
        */
    public String getActivePackAddOnBundle() {
        commonLib.info(getText(pageElements.activePackAddOnBundle));
        return getText(pageElements.activePackAddOnBundle);
    }


    /**
     * This method use to click menu button for current plan detail page
     */
    public void openCurrentPlanDetailPage() {
        commonLib.info("Opening More under Current Plan");
        if(isVisible(pageElements.currentPlanDetailed)){
            clickAndWaitForLoaderToBeRemoved(pageElements.currentPlanDetailed);
        }else{
            commonLib.error("Unable to open current plan detail page");
        }
    }

    /**
     * This method use to check whether profile management widget display or not
     * @return true/false
     * */
    public Boolean isProfileManagementDisplay(){
        commonLib.info("Checking that profile manageent widget Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.activePackWidgetTab);
            status=isElementVisible(pageElements.activePackWidgetTab);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        commonLib.info("status :" + status);
        return  status;
    }


    /*
    This Method will return true/false depending upon checkbox under Active Packs Tab is checked or unchecked
     *//*
    public Boolean isCheckBoxChecked() {
        return getAttributeBoolean(pageElements.activePackCheckbox, "aria-checked");
    }*/

    /**
     * This method use to get total number of checkbox display
     * @return Integer The Size
     */
    public int getWidgetRowsSize() {
        List<WebElement> widgetsRowsElements = returnListOfElement(pageElements.checkboxInActivePack);
        final int size = widgetsRowsElements.size();
        commonLib.info("Getting number of checkbox : " + size);
        return size;
    }

    /**
     * This method use to check checkbox is visible or not
     * @return true/false
     * */
    public Boolean isCheckboxDisplay(){
        commonLib.info("Checking that checkbox Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.checkboxInActivePack);
            status=isElementVisible(pageElements.checkboxInActivePack);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return  status;
    }

    /**
     * This method use to get total number of tabs present in Roles permission list
     * @return Integer The Size
     */
    public int getActivePackOnPM() {
        List<WebElement> widgetsRowsElements = returnListOfElement(pageElements.activePackWidgetOnPM);
        final int size = widgetsRowsElements.size();
        commonLib.info("Getting number of checkbox : " + size);
        return size;
    }

    /**
     * This method use to get header name from profile management
     * @param column The column number
     * @return String The header name
     * */
    public String getHeaders(int column) {
        String header = readTextOnRows(pageElements.activePackWidgetOnPM, column);
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /*
       This Method will give us unable to fetch value for plan detail
        */
    public String getPlanDetailUnableToFetch() {
        commonLib.info(getText(pageElements.planDetailUnableToFetch));
        return getText(pageElements.planDetailUnableToFetch);
    }

    /*
       This Method will give us unable to fetch value for pack detail
        */
    public String getPackDetailUnableToFetch() {
        commonLib.info(getText(pageElements.packDetailUnableToFetch));
        return getText(pageElements.packDetailUnableToFetch);
    }

    /**
     * This method use to get header name of add on bundle widget
     * @param column
     * @return
     */
    public String getPackHeaders(int column) {
        String header = getText(By.xpath(pageElements.packDetailsHeaderRow + column + pageElements.packDetailsHeaderValue));
        commonLib.info("Getting Pack header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get total number of add on bundles present on UI
     * */
    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.rows);
        return rows.size();
    }

    /**
     * This method use to get product name from plan detail detail widget
     * */
    public String getProdName(int rowNumber) {
        By productName = By.xpath(pageElements.valueRow + rowNumber + pageElements.productNameValue);
        final String text = getText(productName);
        commonLib.info("Getting product name from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get category from plan detail detail widget
     * */
    public String getCategory(int rowNumber) {
        By category = By.xpath(pageElements.valueRow + rowNumber + pageElements.categoryValue);
        final String text = getText(category);
        commonLib.info("Getting category name from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get benefit from plan detail detail widget
     * */
    public String getBenefit(int rowNumber) {
        By benefit = By.xpath(pageElements.valueRow + rowNumber + pageElements.benefitValue);
        final String text = getText(benefit);
        commonLib.info("Getting benefit  from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get used value from plan detail detail widget
     * */
    public String getUsed(int rowNumber) {
        By used = By.xpath(pageElements.valueRow + rowNumber + pageElements.usedValue);
        final String text = getText(used);
        commonLib.info("Getting used from Row Number " + rowNumber + " : " + text);
        return text;
    }

    /**
     * This method use to get available value from plan detail detail widget
     * */
    public String getAvailable(int rowNumber) {
        By available = By.xpath(pageElements.valueRow + rowNumber + pageElements.availableValue);
        final String text = getText(available);
        commonLib.info("Getting available from Row Number " + rowNumber + " : " + text);
        return text;
    }

}