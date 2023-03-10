package com.airtel.cs.ui.frontendagent.demographicwidget;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.dataproviders.databeans.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.TestDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.AuthTab;
import com.airtel.cs.pagerepository.pagemethods.DemoGraphic;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import com.airtel.cs.model.cs.response.kycprofile.GsmKyc;
import com.airtel.cs.model.cs.response.kycprofile.Profile;
import com.airtel.cs.model.cs.response.kycprofile.KYCProfile;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Test;

import java.util.List;

public class DemoGraphicWidgetBySIMTest extends Driver {
    RequestSource api = new RequestSource();
    private static String customerNumber = null;

    @DataProviders.User()
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class, enabled = false)
    public void openCustomerInteractionBySIM(TestDataBean Data) {
        try {
            selUtils.addTestcaseDescription("Validating the Search for Customer Interactions By Using SIM Number :" + Data.getSimNumber(), "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            if (evnName.equalsIgnoreCase("Prod")) {
                pages.getMsisdnSearchPage().enterNumber(Data.getProdSIMNumber());
                customerNumber = Data.getProdCustomerNumber();
            } else {
                pages.getMsisdnSearchPage().enterNumber(Data.getSimNumber());
                customerNumber = Data.getCustomerNumber();
            }
            pages.getMsisdnSearchPage().clickOnSearch();
            if (!pages.getCustomerProfilePage().isCustomerProfilePageLoaded()) {
                commonLib.fail("Customer Info Dashboard Page does not open using SIM Number.", true);
                pages.getMsisdnSearchPage().clearCustomerNumber();
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteractionBySIM" + e.fillInStackTrace(), true);
        }
    }

    @DataProviders.User()
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionBySIM", enabled = false)
    public void validateDemographicInformationBySIMNumber(TestDataBean Data) {
        try {
            final String customerNumber = Data.getCustomerNumber();
            selUtils.addTestcaseDescription("Validating the Demographic Information of User :" + customerNumber, "description");
            DemoGraphic demographic = new DemoGraphic(driver);
            Profile profileAPI = api.profileAPITest(DemoGraphicWidgetBySIMTest.customerNumber);
            KYCProfile kycProfile = api.kycProfileAPITest(DemoGraphicWidgetBySIMTest.customerNumber);
            GsmKyc gsmKycAPI = api.gsmKYCAPITest(DemoGraphicWidgetBySIMTest.customerNumber);
            api.accountPlansTest(DemoGraphicWidgetBySIMTest.customerNumber);
            AMProfile amProfileAPI = api.amServiceProfileAPITest(DemoGraphicWidgetBySIMTest.customerNumber);

            try {
                if (demographic.isPUKInfoLocked()) {
                    demographic.clickPukUnlock();
                    Thread.sleep(5000);
                    AuthTab authTab = new AuthTab(driver);
                    DataProviders data = new DataProviders();
                    authTab.waitTillLoaderGetsRemoved();
                    assertCheck.append(actions.assertEqualBoolean(authTab.isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                    try {
                        List<AuthTabDataBeans> list = data.getPolicy();
                        for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                            authTab.clickCheckBox(i);
                        }
                        assertCheck.append(actions.assertEqualBoolean(authTab.isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
                        authTab.clickAuthBtn();
                    } catch (NoSuchElementException | AssertionError | TimeoutException e) {
                        commonLib.fail("Not able to authenticate user: " + e.fillInStackTrace(), true);
                        authTab.clickCloseBtn();
                    }
                }
                try {
                    assertCheck.append(actions.assertEqualStringType(demographic.getPUK1().trim(), kycProfile.getResult().getPuk().get(0).getValue(), "Customer's PUK1 Number is as Expected", "Customer's PUK1 Number is not as Expected"));
                } catch (NoSuchElementException e) {
                    commonLib.fail("Customer's PUK1 Number is not visible" + e.getCause(), true);
                }
                try {
                    assertCheck.append(actions.assertEqualStringType(demographic.getPUK2().trim(), kycProfile.getResult().getPuk().get(1).getValue(), "Customer's PUK2 Number is as Expected", "Customer's PUK2 Number is not as Expected"));
                } catch (NoSuchElementException e) {
                    commonLib.fail("Customer's  PUK2 Number is not visible" + e.getCause(), true);
                }

            } catch (NoSuchElementException | TimeoutException | InterruptedException | AssertionError e) {
                commonLib.fail("Not able to View PUK Details" + e.getMessage(), true);
            }

            try {
                if (pages.getDemoGraphicPage().checkAMProfileWidget()) {
                    if (evnName.equalsIgnoreCase("NG")) {
                        commonLib.fail("AM Profile Widget Must not display for NG Opco.", true);
                    } else if (demographic.isAirtelMoneyProfileLocked()) {
                        demographic.clickAirtelStatusToUnlock();
                        Thread.sleep(5000);
                        AuthTab authTab = new AuthTab(driver);
                        DataProviders data = new DataProviders();
                        authTab.waitTillLoaderGetsRemoved();
                        assertCheck.append(actions.assertEqualBoolean(authTab.isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                        try {
                            List<AuthTabDataBeans> list = data.getPolicy();
                            for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                                authTab.clickCheckBox(i);
                            }
                            assertCheck.append(actions.assertEqualBoolean(authTab.isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
                            authTab.clickAuthBtn();
                        } catch (NoSuchElementException | TimeoutException e) {
                            e.fillInStackTrace();
                            commonLib.fail("Action(Airtel Money Status)Not able to authenticate user: " + e.fillInStackTrace(), true);
                            authTab.clickCloseBtn();
                        }
                    }
                }
            } catch (NoSuchElementException | TimeoutException | InterruptedException | AssertionError e) {
                commonLib.fail("Airtel Money Status does not unlock" + e.getMessage(), true);
            }

            try {
                assertCheck.append(actions.assertEqualStringType(demographic.getCustomerName().trim(), gsmKycAPI.getResult().getName().trim(), "Customer Name is as Expected", "Customer Name is not as Expected"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Customer Name is not visible" + e.getCause(), true);
            }
            try {
                demographic.hoverOnCustomerInfoIcon();
                assertCheck.append(actions.assertEqualStringType(demographic.getCustomerDOB().trim(), String.valueOf(gsmKycAPI.getResult().getDob()), "Customer DOB is as Expected", "Customer DOB is not as Expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer DOB is not visible or null" + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqualStringType(demographic.getIdType().trim(), gsmKycAPI.getResult().getIdentificationType(), "Customer's ID Type is as Expected", "Customer's ID Type is not as Expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer's Id Type is not visible or null" + e.getCause(), true);
            }
            try {
                assertCheck.append(actions.assertEqualBoolean(gsmKycAPI.getResult().getIdentificationNo().contains(demographic.getIdNumber().replace("*", "")), true, "Customer's ID Number is as Expected", "Customer's ID Number is not as Expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer's Id Number is not visible" + e.getCause(), true);
            }
            try {
                demographic.hoverOnSIMNumberIcon();
                assertCheck.append(actions.assertEqualStringType(demographic.getActivationDate().trim(), kycProfile.getResult().getActivationDate(), "Customer's Activation Date is as Expected", "Customer's Activation Date is not as Expected"));
            } catch (NoSuchElementException | TimeoutException | NumberFormatException | NullPointerException e) {
                commonLib.fail("Customer's Activation Date is not visible" + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqualStringType(demographic.getGSMStatus().toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is as Expected", "Customer's SIM Status is not as Expected"));
                demographic.hoverOnSIMStatusInfoIcon();
                assertCheck.append(actions.assertEqualStringType(demographic.getGSMStatusReasonCode().trim().toLowerCase(), kycProfile.getResult().getReason() == null || kycProfile.getResult().getReason().equals("") ? "-" : kycProfile.getResult().getReason().toLowerCase().trim(), "Customer SIM Status Reason is as Expected", "Customer SIM Status Reason is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(demographic.getGSMStatusModifiedBy().trim().toLowerCase(), kycProfile.getResult().getModifiedBy().trim().toLowerCase(), "Customer SIM Status Modified By is as Expected", "Customer SIM Status Modified By is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(demographic.getGSMStatusModifiedDate().trim(), UtilsMethods.getDateFromString(kycProfile.getResult().getModifiedDate(), "dd-MMM-yyy hh:mm aa", "dd-MMM-yyyy hh:mm aa"), "Customer SIM Status Modified Date is as Expected", "Customer SIM Status Modified Date is not as Expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer's SIM Status is not visible" + e.getCause(), true);
            }
            if (!evnName.equalsIgnoreCase("NG")) {
                try {
                    assertCheck.append(actions.assertEqualStringType(demographic.getWalletBalance().toUpperCase().trim(), amProfileAPI.getResult().getWallets().get(0).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallets().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Customer's Airtel Money Wallet Balance is not visible" + e.getCause(), true);
                }

                try {
                    assertCheck.append(actions.assertEqualStringType(demographic.getRegistrationStatus().toLowerCase().trim(), amProfileAPI.getResult().getRegStatus().toLowerCase().trim(), "Customer's Airtel Money Registration Status as Expected", "Customer's Airtel Money Registration Status not same not as Expected"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Customer's Airtel Money Registration Status is not visible" + e.getCause(), true);
                }
            }
            try {
                assertCheck.append(actions.assertEqualStringType(demographic.getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is as Expected", "Customer's Device Type is not as Expected"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Customer's Device Type is not visible" + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqualStringType(demographic.getSimNumber().trim(), customerNumber, "Customer's Mobile Number is as Expected", "Customer's Mobile Number is not as Expected"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Customer's Mobile Number is not visible" + e.getCause(), true);
            }
            try {
                assertCheck.append(actions.assertEqualStringType(demographic.getSimType().trim(), kycProfile.getResult().getSimType(), "Customer's SIM Type is as Expected", "Customer's SIM Type is not as Expected"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Customer's SIM Type is not visible" + e.getCause(), true);
            }

            try {
                if (kycProfile.getResult().getLineType().isEmpty()) {
                    assertCheck.append(actions.assertEqualStringType(demographic.getConnectionType().trim(), "-", "Customer Connection Type as expected", "Customer Connection Type as not expected"));
                } else {
                    assertCheck.append(actions.assertEqualStringType(demographic.getConnectionType().toLowerCase().trim(), kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type as expected", "Customer Connection Type as not expected"));
                }
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer's Connection Type is not visible" + e.getCause(), true);
            }

            try {
                if (kycProfile.getResult().getServiceCategory().isEmpty()) {
                    assertCheck.append(actions.assertEqualStringType(demographic.getServiceCategory().trim(), "-", "Customer Service Category as expected", "Customer Service Category as not expected"));
                } else {
                    assertCheck.append(actions.assertEqualStringType(demographic.getServiceCategory().toLowerCase().trim(), kycProfile.getResult().getServiceCategory().toLowerCase().trim(), "Customer Service Category as expected", "Customer Service Category as not expected"));
                }
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer's Service Category is not visible" + e.getCause(), true);
            }

            try {
                if (kycProfile.getResult().getSegment().isEmpty()) {
                    assertCheck.append(actions.assertEqualStringType(demographic.getSegment().trim(), "- -", "Customer Segment as expected", "Customer Segment as not expected"));
                } else {
                    assertCheck.append(actions.assertEqualStringType(demographic.getSegment().toLowerCase().trim(), "- " + kycProfile.getResult().getSegment().toLowerCase().trim(), "Customer Segment as expected", "Customer Segment as not expected"));
                }
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer's Segment is not visible" + e.getCause(), true);
            }

            try {
                if (kycProfile.getResult().getServiceClass().isEmpty()) {
                    assertCheck.append(actions.assertEqualStringType(demographic.getServiceClass().trim(), "-", "Customer Service Class as expected", "Customer Service Class as not expected"));
                } else {
                    assertCheck.append(actions.assertEqualStringType(demographic.getServiceClass().toLowerCase().trim(), kycProfile.getResult().getServiceClass().toLowerCase().trim(), "Customer Service Class as expected", "Customer Service Class as not expected"));
                }
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer's Service Class is not visible" + e.getCause(), true);
            }

            try {
                if (kycProfile.getResult().getVip()) {
                    assertCheck.append(actions.assertEqualBoolean(demographic.isVIP(), true, "Customer is VIP but Icon displayed as expected", "Customer is VIP but Icon does not display as expected"));
                }
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer is VIP or not is not visible" + e.getCause(), true);
            }

            try {
                demographic.hoverOnDeviceInfoIcon();
                assertCheck.append(actions.assertEqualStringType(demographic.getIMEINumber().trim(), profileAPI.getResult().getImeiNumber(), "Customer device IMEI number as expected", "Customer device IMEI number as not expected"));

                try {
                    assertCheck.append(actions.assertEqualStringType(demographic.getDeviceType().toLowerCase().trim(), profileAPI.getResult().getType().toLowerCase().trim(), "Customer device type as expected", "Customer device type as not expected"));
                } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                    commonLib.fail("Customer device type as not visible." + e.getCause(), true);
                }

                try {
                    assertCheck.append(actions.assertEqualStringType(demographic.getBrand().toLowerCase().trim(), profileAPI.getResult().getBrand().toLowerCase().trim(), "Customer device Brand as expected", "Customer device Brand as not expected"));
                } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                    commonLib.fail("Customer device Brand as not visible." + e.getCause(), true);
                }

                try {
                    assertCheck.append(actions.assertEqualStringType(demographic.getDeviceModel().toLowerCase().trim(), profileAPI.getResult().getModel().toLowerCase().trim(), "Customer device model as expected", "Customer device model as not expected"));
                } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                    commonLib.fail("Customer device model as not visible." + e.getCause(), true);
                }

                try {
                    assertCheck.append(actions.assertEqualStringType(demographic.getDeviceOS().toLowerCase().trim(), profileAPI.getResult().getOs().toLowerCase().trim(), "Customer device OS as expected", "Customer device OS as not expected"));
                } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                    commonLib.fail("Customer device OS as not visible." + e.getCause(), true);
                }

            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device IMEI number is not visible." + e.getCause(), true);
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateDemographicInformationBySIMNumber" + e.fillInStackTrace(), true);
        }
    }
}
