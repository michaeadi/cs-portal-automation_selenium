package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.TariffPlanPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class TariffPlanPage extends BasePage {
    public TariffPlanPageElements tariffPlanPageElements;

    public TariffPlanPage(WebDriver driver) {
        super(driver);
        tariffPlanPageElements = PageFactory.initElements(driver, TariffPlanPageElements.class);
    }

    /*
    This Method will verify if Service Class Tab opened or not
     */
    public Boolean isChangeServiceClassTabOpened() {
        boolean result = false;
        try {
            result = elementVisibleWithExplictWait(tariffPlanPageElements.serviceClassTab);
        } catch (Exception e) {
            log.error("Service Class Tab is not Visible", e);
        }
        return result;
    }

    /*
    This Method will return the current plan of the customer from change service class tab
     */
    public String getCurrentPlan() {
        return readText(tariffPlanPageElements.currentPlanName);
    }

    /*
    This Method will return true/false depending upon checkbox under Service Class Tab is checked or unchecked
     */
    public Boolean isCheckBoxChecked() {
        return getAttributeBoolean(tariffPlanPageElements.currentPlanDetailsCheckBox, "aria-checked");
    }

    /*
    This Method will return Name of the current plan showing over Plan Description
     */
    public String getCurrentPlanDetailsHeader() {
        return readText(tariffPlanPageElements.currentPlanDetailsHeaderName);
    }

    /*
    This Method will return us the Plan Description
     */
    public String getPlanDescription() {
        return readText(tariffPlanPageElements.planDescription);
    }

    /*
    This Method will give us the drop down name under Service Class Tab
     */
    public String getDropDownName() {
        return readText(tariffPlanPageElements.dropDownName);
    }

    /*
    This Method will check Current Plan of the Customer Should not be there under Available Plans
     */
    public Boolean checkCurrentPlanNotInDropDownList(String currentPlan) {
        boolean result = true;
        click(tariffPlanPageElements.dropDown);
        final List<WebElement> elementsListfromBy = getElementsListFromBy(tariffPlanPageElements.dropDownList);
        for (WebElement dropDownList : elementsListfromBy) {
            result = dropDownList.getText().equalsIgnoreCase(currentPlan);
        }
        return result;
    }

    /*
    This Method will return us the size of the Drop Down present under Service Class Tab
     */
    public int getDropDownListSize() {
        final List<WebElement> elementsListfromBy = getElementsListFromBy(tariffPlanPageElements.dropDownList);
        return elementsListfromBy.size();
    }

    /*
    This Method will help us to select any plan other than Current Plan
     */
    public String selectPlanOtherThanCurrentPlan(String currentPlan) {
        String selectedValue = null;
        final List<WebElement> elementsListfromBy = getElementsListFromBy(tariffPlanPageElements.dropDownList);
        for (WebElement dropDownList : elementsListfromBy) {
            if (!(dropDownList.getText().equalsIgnoreCase(currentPlan))) {
                click(tariffPlanPageElements.dropDownList);
                selectedValue = readText(tariffPlanPageElements.selectedDropDownValue);
                break;
            }
        }
        return selectedValue;
    }

    /*
    This Method will give us the Note present under Service Class Tab, once select the Plan other than Current Plan
     */
    public String getFootNote() {
        return readText(tariffPlanPageElements.planFootNote);
    }

    /*
    This Method will check Migrate Button is present or not under Service Class Tab
     */
    public Boolean isMigrateButtonPresent() {
        return isVisible(tariffPlanPageElements.migrateButton);
    }

    /*
    This Method will let us know, Migrate button is enabled or not
     */
    public Boolean isMigrateBtnEnabled() {
        return checkState(tariffPlanPageElements.migrateButton);
    }

    /*
    This Method will open the issue details popup after click on Migrate btn
     */
    public void openIssueDetailsModal() {
        try {
            if (isVisible(tariffPlanPageElements.migrateButton)) {
                click(tariffPlanPageElements.migrateButton);
            }
        } catch (Exception e) {
            log.error("Not Clicked on Migrate Button", e.getMessage());
        }
    }

    /*
    This Method will check issue details pop up opened or not
     */
    public Boolean isIssueDetailModalOpened() {
        commonLib.hardWait();
        return isVisible(tariffPlanPageElements.issueDetailPopUp);
    }

    /*
    This Method will let us know, Comment box is visible or not over Issue details pop up
     */
    public Boolean isCommentBoxVisible() {
        return isVisible(tariffPlanPageElements.commentBox);
    }

    /*
    This Method will select the reason over Issue details pop up
     */
    public Boolean isSelectReasonVisible() {
        return isVisible(tariffPlanPageElements.issueDetailsReason);
    }

    /*
    This Method will let us know, Cancel Button is visible or not over Issue Details Popup
     */
    public Boolean isCancelBtnVisisble() {
        return isVisible(tariffPlanPageElements.cancelBtn);
    }

    /*
        This Method will let us know, Submit Button is visible or not over Issue Details Popup
     */
    public Boolean isSubmitBtnVisible() {
        return isVisible(tariffPlanPageElements.submitBtn);
    }

    /*
    This Method will click on the Cancel Btn over issue details pop up
     */
    public void clickCancelBtn() {
        click(tariffPlanPageElements.cancelBtn);
    }

    /*
    This Method will click on the Submit Btn over issue details pop up
     */
    public void clickSubmitBtn() {
        click(tariffPlanPageElements.submitBtn);
    }

    /*
    This Method will click over issue details Popup
     */
    public void clickIssueDetails() {
        click(tariffPlanPageElements.issueDetailsReason);
    }

    /*
    This Method will select the Reason over issue details pop up
     */
    public void selectReason() {
        final List<WebElement> elementsListfromBy = getElementsListFromBy(tariffPlanPageElements.selectReason);
        for (WebElement dropDownList : elementsListfromBy) {
            if (dropDownList.getSize() != null) {
                click(tariffPlanPageElements.selectReason);
                break;
            }
        }
    }

    public void enterCommentIssuePopUp() {
        setTextWithTimeStamp(tariffPlanPageElements.commentBox, "Comment By Automation");
    }

    /*
    This Method will let us know, submit Btn is enabled or not over issue details pop up
     */
    public Boolean isSubmitEnabled() {
        return checkState(tariffPlanPageElements.submitBtn);
    }

    /*
    This Method will return the Note Present over the issue details pop up
     */
    public String getNoteTextIssueDetailsPopUp() {
        return getAttribute(tariffPlanPageElements.noteText, "title", false);
    }

    /*
    With this Method will open issue details modal,select reason, enter comment
     */
    public void enterDetailsIssuePopup() {
        openIssueDetailsModal();
        clickIssueDetails();
        selectReason();
        enterCommentIssuePopUp();
    }

    /*
    This Method will click on submit btn and plan will get changed
     */
    public Boolean changePlan() {
        click(tariffPlanPageElements.submitBtn);
        waitTillLoaderGetsRemoved();
        return isVisible(tariffPlanPageElements.successMsg);
    }

    /*
    This Method will provide us the text present over the Success or Failure modal
     */
    public String getModalText() {
        return readText(tariffPlanPageElements.modalSuccessFailureMsg);
    }
}
