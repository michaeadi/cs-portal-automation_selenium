package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.PlanAndPackDetailedWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class PlanAndPackDetailedWidget extends BasePage{

    PlanAndPackDetailedWidgetPage pageElements;
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
     */
    public Boolean isCheckBoxChecked() {
        return getAttributeBoolean(pageElements.activePackCheckbox, "aria-checked");
    }


}