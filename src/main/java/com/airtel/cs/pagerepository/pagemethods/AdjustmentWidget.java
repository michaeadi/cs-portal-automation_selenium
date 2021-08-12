package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AdjustmentTabPage;
import com.airtel.cs.model.response.adjustmentreason.AdjustmentReasonPOJO;
import com.airtel.cs.model.response.adjustmentreason.ReasonDetail;
import org.openqa.selenium.By;
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
        return getAttribute(pageElements.serviceNumber,"value",false);
    }

    /**
     * This Method is use to get adjustment currency
     * @return String The value
     */
    public String getAdjustmentCurrency(){
        commonLib.info("Reading adjustment currency");
        return getAttribute(pageElements.adjustmentCurrency,"value",false);
    }

    /**
     * This Method is use to get adjustment Transaction number
     * @return String The value
     */
    public String getTransactionNumber(){
        commonLib.info("Reading Transaction number");
        return getText(pageElements.transactionNumber);
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
     * This method is use to click pop up yes button
     */
    public void clickYesButton(){
        commonLib.info("This method is use to click on Yes button");
        clickAndWaitForLoaderToBeRemoved(pageElements.yesBtn);
    }

    /**
     * This method is use to click pop up no button
     */
    public void clickNoButton(){
        commonLib.info("This method is use to click on No button");
        clickAndWaitForLoaderToBeRemoved(pageElements.noBtn);
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

    /**
     * This method is use to validate UI Adjustment reasons and API Adjustment reason is same or not
     * @param reasons The UI Adjustment Reasons
     * @param reasonPOJO The API Adjustment Reasons
     */
    public void validateReasonsDetail(List<String> reasons, AdjustmentReasonPOJO reasonPOJO){
        if (!reasons.isEmpty()) {
            for (ReasonDetail detail : reasonPOJO.getResult()) {
                assertCheck.append(actions.assertEqualBoolean(reasons.remove(detail.getReason()),true,detail.getReason() + " reason displayed on UI as per api response",detail.getReason() + " reason does not displayed on UI as per api response"));
            }
        } else {
            commonLib.fail("No Adjustment reason displayed on UI", true);
        }
    }

    /**
     * This method is use to choose available option based on index
     * @param index The index number
     * @return String The Selected option text
     */
    public String chooseOption(int index){
        commonLib.info("Choosing Option with index: "+index);
        final By element= By.xpath(pageElements.chooseOption+index+"]");
        String chosenOption=getText(element);
        clickAndWaitForLoaderToBeRemoved(element);
        return chosenOption;
    }

    /**
     * This method is use to get pop up title
     * @return String The Value
     */
    public String getPopUpTitle(){
        String title=getText(pageElements.popUpTitle);
        commonLib.info("Reading Pop up title index: "+title);
        return title;
    }

    /**
     * This method is use to choose available option based on text
     * @param byText The text
     * @return String The Selected option text
     */
    public String chooseOption(String byText){
        commonLib.info("Choosing Option with text: "+byText);
        final By element=By.xpath(pageElements.chooseText+"contains(text(),'"+byText+"')]");
        clickAndWaitForLoaderToBeRemoved(element);
        return byText;
    }

    /**
     * This method is use to enter main amount value on UI
     * @param amount The amount
     */
    public void enterMainAmount(String amount){
        commonLib.info("Entering Amount : "+amount);
        amount=Double.parseDouble(amount)>1.0?"1":amount;
        enterText(pageElements.mainAmount,amount);
    }

    /**
     * This method is use to enter DA amount value on UI
     * @param amount The amount
     */
    public void enterDAAmount(String amount){
        commonLib.info("Entering Amount : "+amount);
        amount=Double.parseDouble(amount)>1.0?"1":amount;
        enterText(pageElements.daAmount,amount);
    }

    /**
     * This method is use to get access denied message display or not
     * @return true/false
     */
    public Boolean isAccessDeniedMsg(){
        commonLib.info("Checking action denied message displayed");
        boolean flag=isVisibleContinueExecution(pageElements.errorMsg);
        if(flag){
            commonLib.info("Reading Access Denied Message : "+getText(pageElements.errorMsg));
        }
        return flag;
    }

    /**
     * This is method is use to click on select box to open adjustment DA id's dropdown
     */
    public void OpenDAType(){
        commonLib.info("Clicking on Select option to open DA Id's");
        clickWithoutLoader(pageElements.openDaId);
    }

    /**
     * This is method is use to click on select box to open adjustment DA Unit dropdown
     */
    public void OpenDAUnit(){
        commonLib.info("Clicking on Select option to open DA unit's");
        clickWithoutLoader(pageElements.openDaUnit);
    }

}
