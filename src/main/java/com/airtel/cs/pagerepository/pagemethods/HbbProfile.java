package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.HbbProfilePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import java.util.NoSuchElementException;

@Log4j2
public class HbbProfile extends BasePage{

    public HbbProfilePage pageElements;

    public HbbProfile(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, HbbProfilePage.class);
    }
    /**
     * This method is use to check current plan widget
     *
     * @return true/false
     */
    public Boolean isCurrentPlanVisible() {
        final boolean state = (isElementVisible(pageElements.currentPlan)&&isElementVisible(pageElements.currentPlanMore));
        commonLib.info("Is Current Plan Widget and more icon visible: " + state);
        return state;
    }

    /**
     * This method is use to check Recharge History Widget
     *
     * @return true/false
     */
    public Boolean isRechargeHistoryVisible() {
        final boolean state = (isElementVisible(pageElements.currentPlan)&&isElementVisible(pageElements.rechargeHistoryMore));
        commonLib.info("Is Recharge History Widget and more icon visible: " + state);
        return state;
    }
    /**
     * This method is use to check usage history visible or not
     *
     * @return true/false
     */
    public Boolean isUsageHistoryVisible() {
        final boolean state = (isElementVisible(pageElements.usageHistory)&&isElementVisible(pageElements.usageHistoryMore));
        commonLib.info("Is Usage History Widget visible: " + state);
        return state;
    }
    /**
     * This method is use to check service  profile visible or not
     *
     * @return true/false
     */
    public Boolean isServiceProfileVisible() {
        final boolean state = (isElementVisible(pageElements.serviceProfile));
        commonLib.info("Is Service Profile Widget visible: " + state);
        return state;
    }

    /**
     * This method is use to check hbb profile visible or not
     *
     * @return true/false
     */
    public Boolean isHBBProfileVisible() {
        final boolean state = isElementVisible(pageElements.hbbProfile);
        commonLib.info("Is Hbb Profile displayed: " + state);
        return state;
    }

    /**
     * This method is use to check others  visible or not in hbb
     *
     * @return true/false
     */
    public Boolean isHBBOthersVisible() {
        final boolean state = isElementVisible(pageElements.hbbOthers);
        commonLib.info("Is Others displayed in Hbb Profile: " + state);
        return state;
    }
    /**
     * This method is use to check hbb tab visible or not
     *
     * @return true/false
     */
    public Boolean isHBBTabVisible() {
        final boolean state = isElementVisible(pageElements.hbbTab);
        commonLib.info("Is Hbb Tab displayed: " + state);
        return state;
    }

    /**
     * This method is use to check count to hbb linked numbers
     *
     * @return true/false
     */
    public Integer getHbbLinkedNumbers() {
        final Integer size= getSizeOfElement(pageElements.hbbLinkedNumbers);
        commonLib.info("Count of Hbb Linked Numbers : " + size);
        return size;
    }

    /**
     * This method is use to check hbb profile visiblility for non airtel number
     *
     * @return true/false
     */
    public Boolean isHBBNonAirtelPopUpVisible() {
        final boolean state = isElementVisible(pageElements.hbbProfileNonAirtelNo);
        commonLib.info("Is Pop Up visible: " + state);
        return state;
    }

    /**
     * This method is use to check hbb profile visiblility for non airtel number
     *
     * @return true/false
     */
    public Boolean isHBBIconVisible() {
        final boolean state = isElementVisible(pageElements.hbbIcon);
        commonLib.info("Is Pop Up visible: " + state);
        return state;
    }

    /**
     * This method is use to check whether Hbb customer interaction is displayed or not
     *
     * @return true/false
     */
    public Boolean isHBBCustomerInteractionTitleVisible() {
        final boolean state = isElementVisible(pageElements.hbbCustomerInteraction);
        commonLib.info("Is Hbb Customer Interaction Title visible: " + state);
        return state;
    }
    /**
     * This method is use to check whether Non Airtel number is displayed or not
     *
     * @return true/false
     */
    public String getMsisdin() {
        final String text = getText(pageElements.nonAirtelMisdin);
        commonLib.info(" Non Airtel Msisdn visible: " + text);
        return text;
    }

    /**
     * This method is used to check HBB Page for Non Airtel Msisdn
     */

    public boolean ishbbPageForNonAirtelNo()
    {
        final boolean state =isVisible(pageElements.hbbPageNonAirtel);
        commonLib.info("Is Hbb Page loaded for non airtel msisdn: " + state);
        return state;
    }

    /**
     * This method is use to check whether Send sms tab is displayed or not
     *
     * @return true/false
     */
    public Boolean isSendSmsVisible() {
        final Boolean state =isVisible(pageElements.sendSMS);
        commonLib.info("Is SMS tab visible: " + state);
        return state;
    }

    /**
     * This method is use Message History Tab
     *
     * @return true/false
     */
    public Boolean isMessageHistoryVisible() {
        final Boolean state =isVisible(pageElements.messageHistory);
        commonLib.info("Is Message History tab visible: " + state);
        return state;
    }

    /**
     * This method is use Interaction History Tab
     *
     * @return true/false
     */
    public Boolean isInteractionHistoryVisible() {
        final Boolean state =isVisible(pageElements.interactionHistory);
        commonLib.info("Is Interaction History tab visible: " + state);
        return state;
    }
    /**
     * This method is used to check Suspend SIM visibility
     * @return true/false
     */
    public Boolean isSuspendSIMVisible() {
        final Boolean state =isVisible(pageElements.suspendSIM);
        commonLib.info("Is Suspend SIM option visible: " + state);
        return state;
    }
    /**
       * This method is used to check Reactivate SIM Visibility
      * @return true/false
     */
    public Boolean isReactivateSimVisible() {
        final Boolean state =isVisible(pageElements.reactivateSIM);
        commonLib.info("Is Reactivate SIM option visible: " + state);
        return state;
    }
    /**
     * This method is used to check Ticket History
     * @return true/false
     */
    public Boolean isTicketHistoryVisible() {
        final Boolean state =isVisible(pageElements.ticketHistory);
        commonLib.info("Is Ticket History visible: " + state);
        return state;
    }

    /**
     * This method is use to check purple line visibility under hbb tab
     *
     *  @return true/false
     * */
    public Boolean isPurpleLineVisible()
    {
        final boolean state = (isElementVisible(pageElements.hbbPurpleLine));
        commonLib.info("Is purple line visible under HBB tab : " + state);
        return state;

    }

    /**
     * This method is use to click on Hbb Profile
     */
    public void clickOnHbbProfile() {
        if (isVisible(pageElements.hbbProfile) && isClickable(pageElements.hbbProfile)) {
            commonLib.info("Clicking on Hbb Profile");
            clickWithoutLoader(pageElements.hbbProfile);
        } else {
            commonLib.fail("Exception in method - clickOnHbbProfile ", true);
        }
    }

    /**
     * This method is used to click on Hbb Tab
     */
    public void clickOnHbbTab()
    {
        if(isVisible(pageElements.hbbTab))
        commonLib.info("Clicking on Hbb Tab");
        clickWithoutLoader(pageElements.hbbTab);
    }
    /**
     * This method is use to click on Hbb Profile
     */
    public void clickOnCurrentPlan() {
        if (isVisible(pageElements.currentPlan) && isClickable(pageElements.currentPlanMore))
        {commonLib.info("Clicking on Current Plan");
            clickWithoutLoader(pageElements.currentPlanMore);
        } else {
            commonLib.fail("Exception in method - clickOnCurrentPlan ", true);
        }
    }

    /**
     * This method is use to check that hbb tab is displayed next to GSM profile
     *
     *  @return true/false
     * */
    public Boolean isHbbDisplayedNextToGSM()
    {
        final boolean state = (isElementVisible(pageElements.hbbLocation));
        commonLib.info("Is Hbb tab displayed next to GSM : " + state);
        return state;

    }

    /**
     * This method is use to check gsm profile visibility along with am profile
     *  @return true/false
     * */
    public Boolean isGSMAMProfileVisible() {
        final boolean state = (isElementVisible(pageElements.gsmProfile))&& (isElementVisible(pageElements.amProfile));
        commonLib.info("Is GSM Profile visible along with AM Profile : " + state);
        return state;
    }
    /**
      This Method will give us footer auuid shown in Current plan
      Current plan
       */
    public String getFooterAuuid() {
        commonLib.info(getText(pageElements.hbbFoooterAuid));
        return getText(pageElements.hbbMiddleAuid);
    }

    /**
    This Method will give us auuid shown in the middle of the Current plan
     */
    public String getMiddleAuuid() {
        String result;
        result = getAttribute(pageElements.hbbMiddleAuid, "data-auuid", false);
        commonLib.info(result);
        return result;
    }

    /**
     * This method is use to get customer name of Hbb User
     *
     * @return String The value
     */
    public String getUserName() {
        final String text = getText(pageElements.hbbUserName);
        commonLib.info("Getting User Name " + text);
        return text;
    }
    /**
     * This method is use to get alternate number of Hbb User
     *
     * @return String The value
     */
    public String getAlternateNumber() {
        final String text = getText(pageElements.hbbAlternateNumber);
        commonLib.info("Getting Alternate Number" + text);
        return text;
    }
    /**
     * This method is use to get Email id  of Hbb User
     *
     * @return String The value
     */
    public String getEmailId() {
        final String text = getText(pageElements.hbbEmailId);
        commonLib.info("Getting Email id  " + text);
        return text;
    }

    /**
     * This method is use to enter msisdn in search box of non airtel msisdn page
     *
     * @param text The text
     */
    public void searchNonAirtelMsisdnBox(String text) {
        if(isVisible(pageElements.searchNonAirtel)) {
            commonLib.info("Writing MSISDN in Search Box: " + text);
            enterText(pageElements.searchNonAirtel, text);
            driver.findElement(pageElements.searchNonAirtel).sendKeys(Keys.ENTER);
        }
        else {
            commonLib.error("Search box is NOT visible");
        }
    }

    /**
     * This method is use for invalid msisdn for Non Airtel Page
     * @return  true/false
     */
    public Boolean isErrorDisplayed() {
        final boolean state = (isElementVisible(pageElements.nonAirtelMsisdnError));
        commonLib.info("Is error displayed for invalid msisdn " + state);
        return state;
    }

    /**
     * This method is use for first Name visibility
     * @return  true/false
     */
    public Boolean firstName() {
        final boolean state = (isElementVisible(pageElements.firstName));
        commonLib.info("Is first Name is visible" + state);
        return state;
    }
    /**
     * This method is use for last Name visibility
     * @return  true/false
     */
    public Boolean lastName() {
        final boolean state = (isElementVisible(pageElements.lastName));
        commonLib.info("Is last Name is visible" + state);
        return state;
    }
    /**
     * This method is use for middle Name visibility
     * @return  true/false
     */
    public Boolean middleName() {
        final boolean state = (isElementVisible(pageElements.middleName));
        commonLib.info("Is middle name visible " + state);
        return state;
    }
    /**
     * This method is use for last Name visibility
     * @return  true/false
     */
    public Boolean userName() {
        final boolean state = (isElementVisible(pageElements.userName));
        commonLib.info("Is user Name visible" + state);
        return state;
    }

    /**
     * This method is use for last Name visibility
     * @return  true/false
     */
    public Boolean emailID() {
        final boolean state = (isElementVisible(pageElements.emailId));
        commonLib.info("Is email ID visible" + state);
        return state;
    }
    /**
     * This method is use for DOB visibility
     * @return  true/false
     */
    public Boolean dob() {
        final boolean state = (isElementVisible(pageElements.dob));
        commonLib.info("Is dob visible" + state);
        return state;
    }

    /**
     * This method is use for GSM Profile Visibility
     * @return  true/false
     */
    public Boolean isGSMProfileVisible() {
        final boolean state = (isElementVisible(pageElements.gsmProfile));
        commonLib.info("Is gsm profile visible " + state);
        return state;
    }

    /**
     * This method is use to check invalid msisdn text
     */
    public String getInvalidText() {
        final String text = getText(pageElements.nonAirtelMsisdnError);
        commonLib.info("Invalid Msisdn error: " + text);
        return text;
    }

    /**
     * This method is use to hover on search box on non airtel msisdn page
     */
    public void hoverOnSearchBox() {
        commonLib.info("Hover on SIM Status Reason Info icon");
        hoverOverElement(pageElements.searchNonAirtel);
    }

    /**
     * This method is used to open customer interaction from side menu
     */

    public void openCustomerInteraction(String text)
    {
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(text);
        pages.getMsisdnSearchPage().clickOnSearch();
    }

    /**
     * This method is use to click on call to Action icon of Alternate Number
     */
    public void clickOnCallToAction() {
        if (isVisible(pageElements.callToActionIcon) && isClickable(pageElements.callToActionIcon))
        {commonLib.info("Clicking on Call to Action");
            clickAndWaitForLoaderToBeRemoved(pageElements.callToActionIcon);
        } else {
            commonLib.fail("Exception in method - clickOnCallToAction ", true);
        }
    }

    /**
     * This method is use to click on edit icon for alternate number
     */
    public void clickOnEditIconAlternateNo() {
        if (isVisible(pageElements.editIconNumber) && isClickable(pageElements.editIconNumber))
        {commonLib.info("Clicking on Edit Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.editIconNumber);
        } else {
            commonLib.fail("Exception in method - clickOnEditIconAlternateNo ", true);
        }
    }

    /**
     * This method is use to click on edit icon for email id
     */
    public void clickOnEditIconEmail() {
        if (isVisible(pageElements.editEmailIcon) && isClickable(pageElements.editEmailIcon))
        {commonLib.info("Clicking on Edit Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.editEmailIcon);
        } else {
            commonLib.fail("Exception in method - clickOnEditIconEmail ", true);
        }
    }

    /**
     * This method is use to check call to action icon visibility for Alternate Number
     *
     * @return true/false
     */
    public Boolean isCallToActionVisible() {
        final boolean state = isElementVisible(pageElements.callToActionIcon);
        commonLib.info("Is Call to Action Visible : " + state);
        return state;
    }
    /**
     * This method is use to check edit icon visibility for Email id
     *
     * @return true/false
     */
    public Boolean isEmailEditIconVisible() {
        final boolean state = isElementVisible(pageElements.callToActionIcon);
        commonLib.info("Is Edit Icon Visible : " + state);
        return state;
    }
    /**
     * This method is use to check edit icon visibility for Alternate No.
     *
     * @return true/false
     */
    public Boolean isAlternateNoEditIconVisible() {
        final boolean state = isElementVisible(pageElements.callToActionIcon);
        commonLib.info("Is edit Icon Visible : " + state);
        return state;
    }

    /**
     * This method is use to check edit alternate number pop up
     *
     * @return true/false
     */
    public Boolean isEditAlternatePopUpVisible(){
        final boolean state = isElementVisible(pageElements.editAlternatePopUp);
        commonLib.info("Is edit Pop up Vsisble" + state);
        return state;
    }

    /**
     * This method is use to check edit alternate number label visible
     *
     * @return true/false
     */
    public Boolean isEnterAlternateNoVisible(){
        final boolean state = isElementVisible(pageElements.enterAlternateNo);
        commonLib.info("Is edit Alternate number label Vsisble" + state);
        return state;
    }

    /**
     * This method is use to check edit alternate id label visible
     *
     * @return true/false
     */
    public Boolean isEnterEmailIdVisible(){
        final boolean state = isElementVisible(pageElements.enterAlternateEmail);
        commonLib.info("Is edit Alternate email label Vsisble" + state);
        return state;
    }

    /**
     * This method is use to check error message visible on entering invalid msisdn in enter alternate no field
     *
     * @return true/false
     */
    public Boolean isErrorMessageVisible(){
        final boolean state = isElementVisible(pageElements.enterAlternateNo);
        commonLib.info("Is error message Vsisble" + state);
        return state;
    }

    /**
     * This method is use to check submit button disable or not
     *
     * @return true/false
     */
    public boolean isSubmitBtnDisabled() {
        return isEnabled(pageElements.submitButton);
    }


    /**
     * This method is use to check select reason dropdown
     *
     * @return true/false
     */
    public Boolean isSelectReasonVisible(){
        final boolean state = isElementVisible(pageElements.selectReason);
        commonLib.info("Is select reason dropdown  Visible" + state);
        return state;
    }

    /**
     * This method is use to check enter comment field
     * @return true/false
     */
    public Boolean isEnterCommentVisible(){
        final boolean state = isElementVisible(pageElements.enterComment);
        commonLib.info("Is enter comment visible" + state);
        return state;
    }

    /**
     * This method is use to check that enter comment field is mandatory or not
     * @return true/false
     */
    public Boolean isAsteriskVisible(){
        final boolean state = isElementVisible(pageElements.asteriskVisible);
        commonLib.info("Is asterisk symbol visible" + state);
        return state;
    }
    /**
     * This method is use to check visibility of cancel button
     * @return true/false
     */
    public Boolean isCancelButtonVisible(){
        final boolean state = isElementVisible(pageElements.cancelButton);
        commonLib.info("Is cancel button visible" + state);
        return state;

    }

    public void clickCancelButton()
    {if (isVisible(pageElements.hbbProfile) && isClickable(pageElements.submitButton)) {
        commonLib.info("Clicking on Cancel");
        clickWithoutLoader(pageElements.cancelButton);
    } else {
        commonLib.fail("Exception in method - clickCancelButton ", true);
    }
    }

    /**
     * This method is use to check enter comment
     * @return true/false
     */
    public Boolean isSuccessPopUpVisible(){
        final boolean state = isElementVisible(pageElements.confirmationPopUp);
        commonLib.info("Is confirmation Pop Up visible" + state);
        return state;

    }

    /**
     * This method is use to check email Validation
     * @return true/false
     */
    public Boolean isRedLineVisible(){
        final boolean state = isElementVisible(pageElements.emailValidation);
        commonLib.info("Is red line visible after entering invalid email id" + state);
        return state;

    }

    /**
     * This method is use to get text of alternate no.
     *
     * @return true/false
     */
    public String getEnterAlternateNoText() {
        final String text = getText(pageElements.enterAlternateNoText);
        commonLib.info("Getting Alternate Number" + text);
        return text;
    }

    /**
     * This method is use to write comment into comment box
     * @param text The comment
     */
    public void enterComment(String text) {
        commonLib.info("Writing comment into comment box: " + text);
        enterText(pageElements.enterComment, text);
    }
    /**
     * This method is use to click on Submit Button
     */
    public void clickOnSubmit() {
        if (isVisible(pageElements.hbbProfile) && isClickable(pageElements.submitButton)) {
            commonLib.info("Clicking on Submit");
            clickWithoutLoader(pageElements.submitButton);
        } else {
            commonLib.fail("Exception in method - clickOnHbbProfile ", true);
        }
    }

    /**
     * This method is use to enter msisdn in enter alternate field  of edit alternate pop up
     *
     * @param text The text
     */
    public void enterMsisdnAlternateNo(String text) {
        if(isVisible(pageElements.enterAlternateNoText)) {
            commonLib.info("Writing MSISDN in Enter Alternate Number: " + text);
            enterText(pageElements.enterAlternateNoText, text);
        }
        else {
            commonLib.error("Enter Alternate Number is not visible");
        }
    }
    /**
     * This method is use to get text of success message
     *
     * @return true/false
     */
    public String getSuccessText() {
        final String text = getText(pageElements.confirmationPopUp);
        commonLib.info("Getting success pop up" + text);
        return text;
    }
    /**
     * This method is used to create interaction
     */

    public void createNFTRInteraction(String customerNumber) {
        String ticketNumber = null;
      customerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
        try {
            final String issueCode = UtilsMethods.getCategoryHierarchy().get(4);
            selUtils.addTestcaseDescription("Creating NFTR issue " + issueCode, "description");
            pages.getCustomerProfilePage().clickOnInteractionIcon();
            pages.getInteractionsPage().clickOnCode();
            try {
                pages.getInteractionsPage().searchCode(issueCode);
                pages.getInteractionsPage().selectCode(issueCode);
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to select code", true);
                pages.getInteractionsPage().clickOutside();
                throw new NoSuchElementException("Not able to select code or code not found");
            }
            commonLib.info("Creating ticket with issue code -" + issueCode);
            pages.getInteractionsPage().sendComment(constants.getValue("cs.automation.comment"));
            assertCheck.append(actions.assertEqualBoolean(pages.getInteractionsPage().isSaveEnable(), true, "Save Button Enabled Successfully", "Save Button NOT Enabled"));
            pages.getInteractionsPage().clickOnSave();
            assertCheck.append(actions.assertEqualBoolean(pages.getInteractionsPage().isTicketIdVisible(), true, "Ticket Id Visible Successfully over Header", "Ticket Id NOT Visible over Header"));
            commonLib.info(pages.getInteractionsPage().getResolvedFTRDisplayed());
            String[] valueToWrite;
            if (!pages.getInteractionsPage().getResolvedFTRDisplayed().contains("Resolved FTR")) {
                ticketNumber = pages.getInteractionsPage().getResolvedFTRDisplayed();
                commonLib.info("Ticket Number:ME " + ticketNumber);
                valueToWrite = new String[]{ticketNumber};
                UtilsMethods.setAutoAssignmentTicketId(valueToWrite[0]);
                commonLib.pass("Ticket Number Written into properties file " + valueToWrite[0]);
            } else {
                commonLib.fail("It's FTR not NFTR", true);
            }
            pages.getInteractionsPage().closeInteractions();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - CreateNFTRInteraction" + e.fillInStackTrace(), true);
            pages.getInteractionsPage().closeInteractions();
            pages.getInteractionsPage().clickOnContinueButton();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
