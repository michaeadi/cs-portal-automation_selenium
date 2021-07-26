package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.DADetails;
import com.airtel.cs.pojo.response.offerdetails.OfferDetailPOJO;
import io.restassured.http.Headers;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Log4j2
public class DisplayOfferWidgetTest extends Driver {

    public static final String RUN_DISPLAY_OFFER_TEST_CASE = constants.getValue(ApplicationConstants.RUN_DISPLAY_OFFER_TEST_CASE);
    private static String customerNumber;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private OfferDetailPOJO offerDetailPOJO = null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkServiceProfileFlag() {
        if (!StringUtils.equals(RUN_DISPLAY_OFFER_TEST_CASE, "true")) {
            commonLib.skip("Display Offer Widget is NOT Enabled for this Opco=" + OPCO);
            throw new SkipException("Display Offer Widget is NOT Enabled for this Opco=" + OPCO);
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "UC-UT Offer")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void displayOfferHeaderTest(HeaderDataBean headerValues) {
        selUtils.addTestcaseDescription("CSP-63664 : Validate Offers widget header visible and display all the Column name as per config ", "description");
        try {
            assertCheck.append(actions.assertEqualBoolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), true, "Current Balance Widget MENU is visible", "Current Balance Widget Menu is not visible"));
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            assertCheck.append(actions.assertEqualBoolean(pages.getDaDetailsPage().isOfferWidgetDisplay(), true, "Display offer Widget display", "Display offer widget does not display"));
            offerDetailPOJO = api.offerDetailAPITest(customerNumber);
            final int statusCode = offerDetailPOJO.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Display Offer Widget API success and status code is :" + statusCode, "Display Offer Widget API got failed and status code is :" + statusCode, false));
            if (statusCode != 200) {
                commonLib.fail("API is Unable to Get Display Offer for Customer", false);
            } else if (offerDetailPOJO.getResult().size() > 0) {
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(1).toLowerCase().trim(), headerValues.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(2).toLowerCase().trim(), headerValues.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(3).toLowerCase().trim(), headerValues.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(4).toLowerCase().trim(), headerValues.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(5).toLowerCase().trim(), headerValues.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(6).toLowerCase().trim(), headerValues.getRow6().toLowerCase().trim(), "Header Name for Row 6 is as expected", "Header Name for Row 6 is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(7).toLowerCase().trim(), headerValues.getRow7().toLowerCase().trim(), "Header Name for Row 7 is as expected", "Header Name for Row 7 is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(8).toLowerCase().trim(), headerValues.getRow8().toLowerCase().trim(), "Header Name for Row 8 is as expected", "Header Name for Row 8 is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getDisplayOfferHeader(9).toLowerCase().trim(), headerValues.getRow9().toLowerCase().trim(), "Header Name for Row 9 is as expected", "Header Name for Row 9 is not as expected"));
                assertCheck.append(actions.assertEqualBoolean(pages.getDaDetailsPage().isPaginationAvailable(), true, "Pagination is available on display offer widget as expected.", "Pagination is not available on display offer widget as expected"));
            } else {
                commonLib.warning("API is unable to get Offer detail");
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - displayOfferHeaderTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProviderClass = DataProviders.class, dependsOnMethods = {"displayOfferHeaderTest", "openCustomerInteraction"})
    public void displayOfferWidgetTest() {
        selUtils.addTestcaseDescription("Validate Offers widget Column value display as per API Response ", "description");
        DADetails daDetailsPage = pages.getDaDetailsPage();
        try {
            final int statusCode = offerDetailPOJO.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Display Offer Widget API success and status code is :" + statusCode, "Display Offer Widget API got failed and status code is :" + statusCode, false));
            if (statusCode != 200) {
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", false);
            } else if (offerDetailPOJO.getResult().size() > 0) {
                int size = Math.min(offerDetailPOJO.getResult().size(), 5);
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 1), offerDetailPOJO.getResult().get(i).getOfferId(), "Offer Id is as expected as API response", "offer Id is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 2), offerDetailPOJO.getResult().get(i).getOfferName(), "Offer Name is as expected as API response", "offer Name is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 3), offerDetailPOJO.getResult().get(i).getProductID(), "Product Id is as expected as API response", "Product Id is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 4), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(offerDetailPOJO.getResult().get(i).getOfferstartDate()), constants.getValue(CommonConstants.OFFER_DETAILS_TIME_FORMAT)), "Offer Start date is as expected as API response", "offer Start date is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 5), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(offerDetailPOJO.getResult().get(i).getOfferExpiryDate()), constants.getValue(CommonConstants.OFFER_DETAILS_TIME_FORMAT)), "Offer Expiry date is as expected as API response", "offer Expiry date is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 6), offerDetailPOJO.getResult().get(i).getPamServiceId(), "Offer PAM Service Id is as expected as API response", "offer PAM Service Id is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 7), offerDetailPOJO.getResult().get(i).getOfferType(), "Offer Type is as expected as API response", "offer Type is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 8), offerDetailPOJO.getResult().get(i).getOfferState(), "Offer State is as expected as API response", "offer State is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 9), offerDetailPOJO.getResult().get(i).getNoOfDAs(), "Offer No. of DA associated number is as expected as API response", "offer No. of DA associated number is not expected as API response"));
                }
            } else {
                commonLib.warning("API is unable to get Offer detail");
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - displayOfferWidgetTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"displayOfferHeaderTest", "openCustomerInteraction"})
    public void associatedDAPopUpTest() {
        try {
            selUtils.addTestcaseDescription("Validate Associated DA's widget Column value display as per API Response ", "description");
            DADetails daDetailsPage = pages.getDaDetailsPage();
            int size = Math.min(offerDetailPOJO.getResult().size(), 5);
            if (size <= 0) {
                commonLib.warning("API is unable to get Offer detail");
            }
            for (int i = 0; i < size; i++) {
                int daSize = offerDetailPOJO.getResult().get(i).getNoOfDAs() != null ? offerDetailPOJO.getResult().get(i).getNoOfDAs() : -1;
                if (daSize > 0) {
                    daDetailsPage.hoverOnTotalDAIds(i + 1, 9);
                    assertCheck.append(actions.assertEqualBoolean(daDetailsPage.isAssociateDAWidgetDisplay(), true, "After hover on Number of DA's Associate widget display as expected", "After hover on Number of DA's Associate widget does not display as expected"));
                    for (int j = 0; j < daDetailsPage.getNumberOfAssociateHeader(); j++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToAssociateWidget(j, 0), offerDetailPOJO.getResult().get(i).getAccountInformation().get(j).getDaId(), "DA Id same as API response in associated widget for row(" + i + ")", "DA Id not same as API response in associated widget for row(" + i + ")"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToAssociateWidget(j, 1), offerDetailPOJO.getResult().get(i).getAccountInformation().get(j).getDaAmount(), "DA Amount same as API response in associated widget for row(" + i + ")", "DA Amount not same as API response in associated widget for row(" + i + ")"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToAssociateWidget(j, 2), offerDetailPOJO.getResult().get(i).getAccountInformation().get(j).getOfferId(), "DA Offer id same as API response in associated widget for row(" + i + ")", "DA Offer id not same as API response in associated widget for row(" + i + ")"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToAssociateWidget(j, 2), offerDetailPOJO.getResult().get(i).getAccountInformation().get(j).getProductId(), "DA Product id same as API response in associated widget for row(" + i + ")", "DA Product id not same as API response in associated widget for row(" + i + ")"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 3), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(offerDetailPOJO.getResult().get(i).getAccountInformation().get(j).getDaStartDate()), constants.getValue(CommonConstants.OFFER_DETAILS_TIME_FORMAT)), "Offer Start date is as expected as API response", "offer Start date is not expected as API response"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 4), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(offerDetailPOJO.getResult().get(i).getAccountInformation().get(j).getDaEndDate()), constants.getValue(CommonConstants.OFFER_DETAILS_TIME_FORMAT)), "Offer Expiry date is as expected as API response", "offer Expiry date is not expected as API response"));
                    }
                    break;
                } else {
                    commonLib.infoHighlight("No Associated DA Id's with Offer:- ", String.valueOf(offerDetailPOJO.getResult().get(i).getOfferId()), ReportInfoMessageColorList.RED, true);
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - associatedDAPopUpTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"associatedDAPopUpTest", "openCustomerInteraction"})
    public void checkPaginationForOfferWidget() {
        selUtils.addTestcaseDescription("Validate Offers widget display pagination and agent able to navigate through pagination ", "description");
        try {
            int pageSize = Math.min(offerDetailPOJO.getResult().size(), 5);
            String paginationResult = "1 - " + pageSize + " of " + offerDetailPOJO.getResult().size() + " Results";
            assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getPaginationText(), paginationResult, "Pagination Count as expected", "Pagination count as not expected"));
            if (offerDetailPOJO.getResult().size() > 5) {
                assertCheck.append(actions.assertEqualBoolean(pages.getDaDetailsPage().checkNextBtnEnable(), true, "In pagination next button is enable as result is greater than 5", "In Pagination next button is not enable but result is greater than 5."));
                pages.getDaDetailsPage().clickNextBtn();
                assertCheck.append(actions.assertEqualBoolean(pages.getDaDetailsPage().checkPreviousBtnDisable(), false, "In pagination Previous button is enable", "In Pagination previous button is not enable"));
                pages.getDaDetailsPage().clickPreviousBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getDaDetailsPage().getPaginationText(), paginationResult, "Pagination Count as expected", "Pagination count as not expected"));
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getDaDetailsPage().checkNextBtnEnable(), false, "In pagination next button is disable as result is <= 5", "In Pagination next button is not disable but result is <= 5."));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkPaginationForOfferWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"associatedDAPopUpTest", "openCustomerInteraction"})
    public void isUserHasOfferPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that necessary permissions are added for offers widget to be displayed.", "description");
            String offer_widget_permission = constants.getValue(PermissionConstants.OFFER_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getDaDetailsPage().isOfferWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), offer_widget_permission), "Display Offer Widget displayed correctly as per user permission", "Display Offer Widget does not display correctly as per user permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasOfferPermission" + e.fillInStackTrace(), true);
        }
    }


}
