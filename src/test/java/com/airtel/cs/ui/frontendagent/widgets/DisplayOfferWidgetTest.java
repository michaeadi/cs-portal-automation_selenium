package com.airtel.cs.ui.frontendagent.widgets;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class DisplayOfferWidgetTest extends PreRequisites {

    private final BaseActions actions = new BaseActions();

    @Test(priority = 1, description = "Validate Customer Interaction Page")
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
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

    @DataProviders.Table(name = "UC-UT Offer")
    @Test(priority = 2, description = "Offers widget should be displayed on the DA details page.", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void  displayOfferHeaderTest(HeaderDataBean headerValues) {
        selUtils.addTestcaseDescription("Validate Offers widget header visible and display all the Column name as per config ", "CSP-63664 Verify that Offers widget is displayed on the DA details page.");
        try {
            pages.getCurrentBalanceWidgetPage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(),true, "Current Balance Widget MENU is visible","Current Balance Widget Menu is not visible"));
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isOfferWidgetDisplay(),true,"Display offer Widget display","Display offer widget does not display"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(1).toLowerCase().trim(), headerValues.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(2).toLowerCase().trim(), headerValues.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(3).toLowerCase().trim(), headerValues.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(4).toLowerCase().trim(), headerValues.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(5).toLowerCase().trim(), headerValues.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(6).toLowerCase().trim(), headerValues.getRow2().toLowerCase().trim(), "Header Name for Row 6 is as expected", "Header Name for Row 6 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(7).toLowerCase().trim(), headerValues.getRow3().toLowerCase().trim(), "Header Name for Row 7 is as expected", "Header Name for Row 7 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(8).toLowerCase().trim(), headerValues.getRow4().toLowerCase().trim(), "Header Name for Row 8 is as expected", "Header Name for Row 8 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(9).toLowerCase().trim(), headerValues.getRow5().toLowerCase().trim(), "Header Name for Row 9 is as expected", "Header Name for Row 9 is not as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - displayOfferHeaderTest" + e.fillInStackTrace(), true);
        }
    }


}
