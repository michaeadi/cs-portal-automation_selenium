package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AdjustmentTabPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class AdjustmentWidget extends BasePage{
    AdjustmentTabPage pageElements;

    public AdjustmentWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AdjustmentTabPage.class);
    }

    /**
     * This method use to get Adjust widget unique identifier
     */
    public String getWidgetId(){
        return pageElements.adjustmentWidgetTitle;
    }

    /**
     * This method is use to get all adjustment reason after clicking on adjustment reason select box
     * @return list of String The Adjustment Reasons
     */
    public List<String> getAllAdjustmentReason(){
        commonLib.info("Getting Adjustment Reason");
        OpenAdjustmentReason();
        return getAllOptionsDisplayed();
    }

    /**
     * This is method is use to click on select box to open adjustment reason dropdown
     */
    public void OpenAdjustmentReason(){
        commonLib.info("Clicking on Select option to open adjustment reason");
        clickWithoutLoader(pageElements.openAdjustmentReason);
    }

    /**
     * This method is use to get all adjustment type after clicking on adjustment type select box
     * @return list of String The Adjustment types
     */
    public List<String> getAllAdjustmentType(){
        commonLib.info("Getting Adjustment Type");
        OpenAdjustmentType();
        return getAllOptionsDisplayed();
    }

    /**
     * This is method is use to click on select box to open adjustment type dropdown
     */
    public void OpenAdjustmentType(){
        commonLib.info("Clicking on Select option to open adjustment type");
        clickWithoutLoader(pageElements.openAdjustmentType);
    }

    /**
     * This method is use to get all adjustment prepaid account type after clicking on adjustment prepaid account type select box
     * @return list of String The all prepaid account type
     */
    public List<String> getAllAdjustmentPrepaidType(){
        commonLib.info("Getting Adjustment prepaid account type");
        OpenAdjustmentPrepaidAccountType();
        return getAllOptionsDisplayed();
    }

    /**
     * This is method is use to click on select box to open adjustment prepaid account type dropdown
     */
    public void OpenAdjustmentPrepaidAccountType(){
        commonLib.info("Clicking on Select option to open adjustment prepaid account type");
        clickWithoutLoader(pageElements.openPrepaidAccountType);
    }

    /**
     * This method is use to get all option displayed on UI
     * @return list of String The All options available
     */
    public List<String> getAllOptionsDisplayed(){
        List<String> reason=new ArrayList<>();
        List<WebElement> ls=returnListOfElement(pageElements.allOption);
        for(WebElement wb:ls){
            commonLib.info("Reading Options; "+wb.getText());
            reason.add(wb.getText().trim());
        }
        clickOutside();
        return reason;
    }

    /**
     * This Method is use to get service number
     * @return String The value
     */
    public String getServiceNumber(){
        commonLib.info("Reading Service number");
        return getText(pageElements.serviceNumber);
    }

    /**
     * This Method is use to get adjustment currency
     * @return String The value
     */
    public String getAdjustmentCurrency(){
        commonLib.info("Reading adjustment currency");
        return getText(pageElements.adjustmentCurrency);
    }

    /**
     * This method is use to write comment into comment box
     * @param comment The comment
     */
    public void writeComment(String comment){
        commonLib.info("Writing adjustment comment");
        enterText(pageElements.comments,comment);
    }

    /**
     * This method is use to check submit enable or not
     * @return true/false
     */
    public Boolean isSubmitEnable(){
        commonLib.info("Checking submit button enable or not");
        return isEnabled(pageElements.submitBtn);
    }

    /**
     * This method is use to click submit button
     */
    public void clickSubmitButton(){
        commonLib.info("This method is use to click on submit button");
        clickAndWaitForLoaderToBeRemoved(pageElements.submitBtn);
    }

    /**
     * This method is use to check adjustment reason fields display over UI or not
     * @return true/false
     */
    public Boolean isAdjustmentReasonVisible(){
        commonLib.info("Checking Adjustment reason fields visible or not");
        return isElementVisible(pageElements.openAdjustmentReason);
    }

    /**
     * This method is use to check adjustment type fields display over UI or not
     * @return true/false
     */
    public Boolean isAdjustmentTypeVisible(){
        commonLib.info("Checking Adjustment type fields visible or not");
        return isElementVisible(pageElements.openAdjustmentType);
    }

    /**
     * This method is use to check adjustment account type fields display over UI or not
     * @return true/false
     */
    public Boolean isAdjustmentAccountTypeVisible(){
        commonLib.info("Checking Adjustment account type fields visible or not");
        return isElementVisible(pageElements.openPrepaidAccountType);
    }

    /**
     * This method is use to check comment box visible or not
     * @return true/false
     */
    public Boolean isCommentBoxVisible(){
        commonLib.info("Checking comment box display or not");
        return isElementVisible(pageElements.comments);
    }

}
