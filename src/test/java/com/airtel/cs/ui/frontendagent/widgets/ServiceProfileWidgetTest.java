package com.airtel.cs.ui.frontendagent.widgets;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.ServiceClassWidget;
import com.airtel.cs.pojo.response.hlrservice.HLRServicePOJO;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class ServiceProfileWidgetTest extends Driver {

    private static String customerNumber = null;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();


    @Test(priority = 1, description = "Validate Customer Interaction Page")
    public void openCustomerInteractionAPI() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
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

    @DataProviders.Table(name = "Service Profile")
    @Test(priority = 2, description = "Verify Service Profile Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void validateServiceProfileWidget(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Verify Service Profile Widget: " + customerNumber, "description");
            commonLib.info("Opening URL");
            final ServiceClassWidget serviceClassWidget = pages.getServiceClassWidget();
            assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.isServiceClassWidgetDisplay(), true, "Service Profile Widget displayed correctly", "Service Profile Widget does not display correctly"));
            HLRServicePOJO hlrService = api.getServiceProfileWidgetInfo(customerNumber);
            int size = serviceClassWidget.getNumberOfRows();
            if (Integer.parseInt(hlrService.getStatusCode()) == 200) {
                if (hlrService.getResult().isEmpty() || hlrService.getResult() == null) {
                    commonLib.warning("Unable to get Last Service Profile from com.airtel.cs.API");
                    assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.isServiceProfileNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(1).trim().toLowerCase(), data.getRow1().trim().toLowerCase(), "Header Name at Row(1) is as expected", "Header Name at Row(1) is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(2).trim().toLowerCase(), data.getRow2().trim().toLowerCase(), "Header Name at Row(2) is as expected", "Header Name at Row(2) is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(3).trim().toLowerCase(), data.getRow3().trim().toLowerCase(), "Header Name at Row(3) is as expected", "Header Name at Row(3) is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(4).trim().toLowerCase(), data.getRow4().trim().toLowerCase(), "Header Name at Row(4) is as expected", "Header Name at Row(4) is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(5).trim().toLowerCase(), data.getRow5().trim().toLowerCase(), "Header Name at Row(5) is as expected", "Header Name at Row(5) is not as expected"));
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getValueCorrespondingToAccumulator(i + 1, 1), hlrService.getResult().get(i).getServiceName(), "Service Name is As received in API for row number " + i, "Service Name is not As received in API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getValueCorrespondingToAccumulator(i + 1, 2), hlrService.getResult().get(i).getServiceDesc(), "Service desc is As received in API for row number " + i, "Service desc is not As received in API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getValueCorrespondingToAccumulator(i + 1, 3), hlrService.getResult().get(i).getHlrCodes().get(0), "HLR Code is As received in API for row number " + i, "HLR Code is not As received in API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getValueCorrespondingToAccumulator(i + 1, 4), hlrService.getResult().get(i).getHlrCodeDetails().get(0), "HLR code details is As received in API for row number " + i, "HLR code details is not As received in API for row number " + i));
                        if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                            if (hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                                assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.getServiceStatus(), true, "Service Status is as expected", "Service Status is not as expected"));
                            } else {
                                assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.getServiceStatus(), false, "Service Status is as expected", "Service Status is not as expected"));
                            }
                        }
                    }
                }

            } else {
                assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.isServiceProfileErrorVisible(), true, "com.airtel.cs.API is Giving error But Widget is showing error Message on com.airtel.cs.API is", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("com.airtel.cs.API is unable to fetch Service Profile History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateServiceProfileWidget" + e.fillInStackTrace(), true);
        }
    }
}
