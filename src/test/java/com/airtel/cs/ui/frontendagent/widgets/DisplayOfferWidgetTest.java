package com.airtel.cs.ui.frontendagent.widgets;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.pagerepository.pagemethods.DADetails;
import com.airtel.cs.pojo.response.offerdetails.OfferDetailPOJO;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class DisplayOfferWidgetTest extends PreRequisites {

    private static String customerNumber;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private OfferDetailPOJO offerDetailPOJO = null;

    @Test(priority = 1, description = "Validate Customer Interaction Page")
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
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
    @Test(priority = 2, description = "CSP-63664 Verify that Offers widget is displayed on the DA details page.", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void displayOfferHeaderTest(HeaderDataBean headerValues) {
        selUtils.addTestcaseDescription("Validate Offers widget header visible and display all the Column name as per config ", "description");
        try {
            pages.getCurrentBalanceWidgetPage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), true, "Current Balance Widget MENU is visible", "Current Balance Widget Menu is not visible"));
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isOfferWidgetDisplay(), true, "Display offer Widget display", "Display offer widget does not display"));
            offerDetailPOJO = api.offerDetailAPITest(customerNumber);
            if (Integer.parseInt(offerDetailPOJO.getStatus()) != 200) {
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", false);
            } else if(offerDetailPOJO.getResponse().size() > 0){
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(1).toLowerCase().trim(), headerValues.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(2).toLowerCase().trim(), headerValues.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(3).toLowerCase().trim(), headerValues.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(4).toLowerCase().trim(), headerValues.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(5).toLowerCase().trim(), headerValues.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(6).toLowerCase().trim(), headerValues.getRow6().toLowerCase().trim(), "Header Name for Row 6 is as expected", "Header Name for Row 6 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(7).toLowerCase().trim(), headerValues.getRow7().toLowerCase().trim(), "Header Name for Row 7 is as expected", "Header Name for Row 7 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(8).toLowerCase().trim(), headerValues.getRow8().toLowerCase().trim(), "Header Name for Row 8 is as expected", "Header Name for Row 8 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(9).toLowerCase().trim(), headerValues.getRow9().toLowerCase().trim(), "Header Name for Row 9 is as expected", "Header Name for Row 9 is not as expected"));
                assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isPaginationAvailable(),true,"Pagination is available on display offer widget as expected.","Pagination is not available on display offer widget as expected"));
            }else{
                commonLib.warning("API is unable to get Offer detail");
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - displayOfferHeaderTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, description = "CSP-63665 Offers widget column value should be displayed as per API response.", dataProviderClass = DataProviders.class, dependsOnMethods = {"displayOfferHeaderTest"})
    public void displayOfferWidgetTest() {
        selUtils.addTestcaseDescription("Validate Offers widget Column value display as per API Response ", "description");
        DADetails daDetailsPage = pages.getDaDetailsPage();
        try {
            if (Integer.parseInt(offerDetailPOJO.getStatus()) != 200) {
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", false);
            } else if (offerDetailPOJO.getResponse().size() > 0) {
                int size = Math.min(offerDetailPOJO.getResponse().size(), 5);
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 1), offerDetailPOJO.getResponse().get(i).getOfferId(), "Offer Id is as expected as API response", "offer Id is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 2), offerDetailPOJO.getResponse().get(i).getOfferName(), "Offer Name is as expected as API response", "offer Name is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 3), offerDetailPOJO.getResponse().get(i).getProductId(), "Product Id is as expected as API response", "Product Id is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 4), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(offerDetailPOJO.getResponse().get(i).getOfferStartDate()), constants.getValue(CommonConstants.OFFER_DETAILS_TIME_FORMAT)), "Offer Start date is as expected as API response", "offer Start date is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 5), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(offerDetailPOJO.getResponse().get(i).getOfferExpiryDate()), constants.getValue(CommonConstants.OFFER_DETAILS_TIME_FORMAT)), "Offer Expiry date is as expected as API response", "offer Expiry date is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 6), offerDetailPOJO.getResponse().get(i).getPamServiceId(), "Offer PAM Service Id is as expected as API response", "offer PAM Service Id is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 7), offerDetailPOJO.getResponse().get(i).getOfferType(), "Offer Type is as expected as API response", "offer Type is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 8), offerDetailPOJO.getResponse().get(i).getOfferState(), "Offer State is as expected as API response", "offer State is not expected as API response"));
                    assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 9), offerDetailPOJO.getResponse().get(i).getNoOfDAs(), "Offer No. of DA associated number is as expected as API response", "offer No. of DA associated number is not expected as API response"));
                }
                actions.assertAllFoundFailedAssert(assertCheck);
            }else{
                commonLib.warning("API is unable to get Offer detail");
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - displayOfferWidgetTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, description = "CSP-63676 Verify that hovering on the No Of DA's value displays the Associated DA's widget.", dependsOnMethods = {"displayOfferHeaderTest"})
    public void associatedDAPopUpTest() {
        selUtils.addTestcaseDescription("Validate Associated DA's widget Column value display as per API Response ", "description");
        DADetails daDetailsPage = pages.getDaDetailsPage();
        int size = Math.min(offerDetailPOJO.getResponse().size(), 5);
        try {
            for (int i = 0; i < size; i++) {
                if (offerDetailPOJO.getResponse().get(i).getNoOfDAs() > 0) {
                    daDetailsPage.hoverOnTotalDAIds(i + 1, 9);
                    assertCheck.append(actions.assertEqual_boolean(daDetailsPage.isAssociateDAWidgetDisplay(), true, "After hover on Number of DA's Associate widget display as expected", "After hover on Number of DA's Associate widget does not display as expected"));
                    for (int j = 0; j < daDetailsPage.getNumberOfAssociateHeader(); j++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToAssociateWidget(j, 0), offerDetailPOJO.getResponse().get(i).getAccountInformation().get(j).getDaId(), "DA Id same as API response in associated widget for row(" + i + ")", "DA Id not same as API response in associated widget for row(" + i + ")"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToAssociateWidget(j, 1), offerDetailPOJO.getResponse().get(i).getAccountInformation().get(j).getDaAmount(), "DA Amount same as API response in associated widget for row(" + i + ")", "DA Amount not same as API response in associated widget for row(" + i + ")"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToAssociateWidget(j, 2), offerDetailPOJO.getResponse().get(i).getAccountInformation().get(j).getOfferId(), "DA Offer id same as API response in associated widget for row(" + i + ")", "DA Offer id not same as API response in associated widget for row(" + i + ")"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToAssociateWidget(j, 2), offerDetailPOJO.getResponse().get(i).getAccountInformation().get(j).getProductId(), "DA Product id same as API response in associated widget for row(" + i + ")", "DA Product id not same as API response in associated widget for row(" + i + ")"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 3), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(offerDetailPOJO.getResponse().get(i).getAccountInformation().get(j).getDaStartDate()), constants.getValue(CommonConstants.OFFER_DETAILS_TIME_FORMAT)), "Offer Start date is as expected as API response", "offer Start date is not expected as API response"));
                        assertCheck.append(actions.matchUiAndAPIResponse(daDetailsPage.getValueCorrespondingToOffer(i + 1, 4), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(offerDetailPOJO.getResponse().get(i).getAccountInformation().get(j).getDaEndDate()), constants.getValue(CommonConstants.OFFER_DETAILS_TIME_FORMAT)), "Offer Expiry date is as expected as API response", "offer Expiry date is not expected as API response"));
                    }
                    break;
                }
            }
        }catch (Exception e){
            commonLib.fail("Exception in Method - associatedDAPopUpTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5,description = "Next and Previous button must be clickable and On click of next Page. Next 5 transaction will load. Clicked on Previous page , 5 previous transaction will load.",dependsOnMethods = {""})
    public void checkPaginationForOfferWidget(){
        selUtils.addTestcaseDescription("Validate Offers widget display pagination and agent able to navigate through pagination ", "description");
        try{

        }catch (Exception e){
            commonLib.fail("Exception in Method - checkPaginationForOfferWidget" + e.fillInStackTrace(), true);
        }
    }


}
