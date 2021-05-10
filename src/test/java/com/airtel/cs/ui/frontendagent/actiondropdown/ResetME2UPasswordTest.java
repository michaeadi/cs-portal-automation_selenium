package com.airtel.cs.ui.frontendagent.actiondropdown;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Test;

public class ResetME2UPasswordTest extends Driver {

    private final BaseActions actions = new BaseActions();


    @Test(priority = 1, description = "Validate Customer Profile Page")
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, description = "Verify the Reset ME2U Password tab", dependsOnMethods = "openCustomerInteraction")
    public void validateResetME2UPassword() {
        try {
            selUtils.addTestcaseDescription("Open action drop down and click on Reset ME2U Password option,Validate title visible over modal,Close modal by clicking over cancel button", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickResetME2U();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isResetME2UPasswordTitle(), true, "Reset ME2U Password Tab Opened", "Reset ME2U Password Tab Does not open."));
            pages.getCustomerProfilePage().clickCancelBtn();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException e) {
            pages.getCustomerProfilePage().clickCloseBtn();
            commonLib.fail("Exception in Method :- validateResetME2UPassword" + e.fillInStackTrace(), true);
            pages.getCustomerProfilePage().clickOutside();
        }
    }
}
