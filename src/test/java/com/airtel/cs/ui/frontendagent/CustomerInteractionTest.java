package com.airtel.cs.ui.frontendagent;


import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.dataproviders.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.AMTransactionsWidget;
import com.airtel.cs.pagerepository.pagemethods.AuthTab;
import com.airtel.cs.pagerepository.pagemethods.CurrentBalanceWidget;
import com.airtel.cs.pagerepository.pagemethods.DemoGraphic;
import com.airtel.cs.pagerepository.pagemethods.RechargeHistoryWidget;
import com.airtel.cs.pagerepository.pagemethods.ServiceClassWidget;
import com.airtel.cs.pagerepository.pagemethods.UsageHistoryWidget;
import com.airtel.cs.pojo.AMProfilePOJO;
import com.airtel.cs.pojo.GsmKycPOJO;
import com.airtel.cs.pojo.PlansPOJO;
import com.airtel.cs.pojo.PlansResultPOJO;
import com.airtel.cs.pojo.ProfilePOJO;
import com.airtel.cs.pojo.RechargeHistoryPOJO;
import com.airtel.cs.pojo.UsageHistoryPOJO;
import com.airtel.cs.pojo.airtelmoney.AirtelMoneyPOJO;
import com.airtel.cs.pojo.hlrservice.HLRServicePOJO;
import com.airtel.cs.pojo.kycprofile.KYCProfile;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

@Log4j2
public class CustomerInteractionTest extends Driver {

    static String customerNumber;
    APIEndPoints api = new APIEndPoints();
    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User()
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        if (continueExecutionAPI) {
            final String customerNumber = data.getCustomerNumber();
            selUtils.addTestcaseDescription("Validating the Search for Customer Interactions :" + customerNumber, "description");
            if (evnName.equalsIgnoreCase("Prod")) {
                CustomerInteractionTest.customerNumber = data.getProdCustomerNumber();
            } else {
                CustomerInteractionTest.customerNumber = customerNumber;
            }
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(CustomerInteractionTest.customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            if (!pages.getCustomerProfilePage().isCustomerProfilePageLoaded()) {
                commonLib.fail("Customer Info Dashboard Page does not open using SIM Number.", true);
                pages.getMsisdnSearchPage().clearCustomerNumber();
            }

        } else {
            commonLib.fail("Execution Terminate due to either agent logout or User Password Update", true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @DataProviders.User()
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateDemographicInformation(TestDatabean data) {
        DataProviders dataProviders = new DataProviders();
        selUtils.addTestcaseDescription("Validating the Demographic Information of User :" + data.getCustomerNumber(), "description");
        ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
        KYCProfile kycProfile = api.KYCProfileAPITest(customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(customerNumber);
        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(customerNumber);

        final DemoGraphic demoGraphicPage = pages.getDemoGraphicPage();
        try {
            if (demoGraphicPage.isPUKInfoLock()) {
                demoGraphicPage.clickPUKToUnlock();
                Thread.sleep(5000);
                AuthTab authTab = new AuthTab(driver);
                authTab.waitTillLoaderGetsRemoved();
                assertCheck.append(actions.assertEqual_boolean(authTab.isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                try {
                    List<AuthTabDataBeans> list = dataProviders.getPolicy();
                    for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                        authTab.clickCheckBox(i);
                    }
                    assertCheck.append(actions.assertEqual_boolean(authTab.isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
                    authTab.clickAuthBtn();
                } catch (NoSuchElementException | AssertionError | TimeoutException e) {
                    commonLib.fail("Not able to authenticate user: ", true);
                    commonLib.fail("Not able to authenticate user: " + e.fillInStackTrace(), true);
                    authTab.clickCloseBtn();
                }
            }
            try {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getPUK1().trim(), kycProfile.getResult().getPuk().get(0).getValue(), "Customer's PUK1 Number is as Expected", "Customer's PUK1 Number is not as Expected"));
            } catch (NoSuchElementException e) {
                commonLib.fail("Customer's PUK1 Number is not visible" + e.getCause(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getPUK2().trim(), kycProfile.getResult().getPuk().get(1).getValue(), "Customer's PUK2 Number is as Expected", "Customer's PUK2 Number is not as Expected"));
            } catch (NoSuchElementException e) {
                commonLib.fail("Customer's  PUK2 Number is not visible" + e.getCause(), true);
            }

        } catch (NoSuchElementException | TimeoutException | InterruptedException | AssertionError e) {
            commonLib.fail("Not able to View PUK Details" + e.getMessage(), true);
        }

        try {
            if (demoGraphicPage.isAirtelMoneyStatusLock()) {
                demoGraphicPage.clickAirtelStatusToUnlock();
                AuthTab authTab = new AuthTab(driver);
                authTab.waitTillLoaderGetsRemoved();
                assertCheck.append(actions.assertEqual_boolean(authTab.isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                try {
                    List<AuthTabDataBeans> list = dataProviders.getPolicy();
                    for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                        authTab.clickCheckBox(i);
                    }
                    assertCheck.append(actions.assertEqual_boolean(authTab.isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
                    authTab.clickAuthBtn();
                } catch (NoSuchElementException | TimeoutException e) {
                    e.fillInStackTrace();
                    commonLib.fail("Action(Airtel Money Status)Not able to authenticate user: " + e.fillInStackTrace(), true);
                    authTab.clickCloseBtn();
                }
            }


        } catch (NoSuchElementException | TimeoutException | InterruptedException | AssertionError e) {
            commonLib.fail("Airtel Money Status does not unlock" + e.getMessage(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getCustomerName().trim(), gsmKycAPI.getResult().getName().trim(), "Customer Name is as Expected", "Customer Name is not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer Name is not visible" + e.getCause(), true);
        }
        try {
            demoGraphicPage.hoverOnCustomerInfoIcon();
            final String customerDOB = demoGraphicPage.getCustomerDOB();
            assertCheck.append(actions.assertEqual_stringType(customerDOB.trim(), UtilsMethods.getDateFromEpoch(gsmKycAPI.getResult().getDob(), "dd-MMM-yyyy"), "Customer DOB is as Expected", "Customer DOB is not as Expected"));
            final boolean birthday = demoGraphicPage.isBirthday();
            if (UtilsMethods.isCustomerBirthday(customerDOB.trim(), "dd-MMM-yyyy")) {
                commonLib.pass("Today is Customer Birthday");
                assertCheck.append(actions.assertEqual_boolean(birthday, true, "Today is customer birthday and birthday icon displayed", "Today is customer birthday but does not display birthday icon"));
            } else {
                assertCheck.append(actions.assertEqual_boolean(birthday, false, "Today is customer birthday and birthday icon display", "Today is not customer birthday but birthday icon display."));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer DOB is not visible or null" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getIdType().trim(), gsmKycAPI.getResult().getIdentificationType(), "Customer's ID Type is as Expected", "Customer's ID Type is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Id Type is not visible or null" + e.getCause(), true);
        }
        try {
            assertCheck.append(actions.assertEqual_boolean(gsmKycAPI.getResult().getIdentificationNo().contains(demoGraphicPage.getIdNumber().replace("*", "")), true, "Customer's ID Number is as Expected", "Customer's ID Number is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Id Number is not visible" + e.getCause(), true);
        }

        try {
            demoGraphicPage.hoverOnSIMNumberIcon();
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getActivationDate().trim(), UtilsMethods.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "dd-MMM-yyy"), "Customer's Activation Date is as Expected", "Customer's Activation Date is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NumberFormatException | NullPointerException e) {
            commonLib.fail("Customer's Activation Date is not visible" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getSIMStatus().toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is as Expected", "Customer's SIM Status is not as Expected"));
            demoGraphicPage.hoverOnSIMStatusInfoIcon();
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getSIMStatusReasonCode().trim().toLowerCase(), kycProfile.getResult().getReason() == null || kycProfile.getResult().getReason().equals("") ? "-" : kycProfile.getResult().getReason().toLowerCase().trim(), "Customer SIM Status Reason is as Expected", "Customer SIM Status Reason is not as Expected"));
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getSIMStatusModifiedBy().trim().toLowerCase(), kycProfile.getResult().getModifiedBy().trim().toLowerCase(), "Customer SIM Status Modified By is not as Expected", "Customer SIM Status Modified By is not as Expected"));
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getSIMStatusModifiedDate().trim(), UtilsMethods.getDateFromString(kycProfile.getResult().getModifiedDate(), "dd-MMM-yyy hh:mm aa", "dd-MMM-yyyy hh:mm aa"), "Customer SIM Status Modified Date is as Expected", "Customer SIM Status Modified Date is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's SIM Status is not visible" + e.getCause(), true);
        }

        try {
            if (demoGraphicPage.getDataManagerStatus().equalsIgnoreCase("true")) {
                assertCheck.append(actions.assertEqual_stringType("on", plansAPI.getResult().getDataManager().toLowerCase().trim(), "Customer's Data Manager Status is as Expected", "Customer's Data Manager Status is not as Expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType("off", plansAPI.getResult().getDataManager().toLowerCase().trim(), "Customer's Data Manager Status is as Expected", "Customer's Data Manager Status is not as Expected"));
            }

        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's Data Manager Status is not visible" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getAirtelMoneyStatus().toLowerCase().trim(), profileAPI.getResult().getAirtelMoneyStatus().toLowerCase().trim(), "Customer's Airtel Money Status is as Expected", "Customer's Airtel Money Status is not as Expected"));

        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's Airtel Money Status is not visible" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getServiceStatus().toLowerCase().trim(), profileAPI.getResult().getServiceStatus().toLowerCase().trim(), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));

        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's Airtel Money Service Status is not visible" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getWalletBalance().toUpperCase().trim(), amProfileAPI.getResult().getWallet().get(0).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallet().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's Airtel Money Wallet Balance is not visible" + e.getCause(), true);
        }

        if (OPCO.equalsIgnoreCase("CD")) {
            try {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getWalletBalance2().toUpperCase().trim(), amProfileAPI.getResult().getWallet().get(1).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallet().get(1).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Customer's Airtel Money 2nd Wallet Balance is not visible" + e.getCause(), true);
            }
        }
        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getRegistrationStatus().toLowerCase().trim(), amProfileAPI.getResult().getRegStatus().toLowerCase().trim(), "Customer's Airtel Money Registration Status as Expected", "Customer's Airtel Money Registration Status not same not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's Airtel Money Registration Status is not visible" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is as Expected", "Customer's Device Type is not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's Device Type is not visible" + e.getCause(), true);
        }
        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getSimNumber().trim(), kycProfile.getResult().getSim(), "Customer's SIM Number is as Expected", "Customer's SIM Number is not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's SIM Number is not visible" + e.getCause(), true);
        }
        try {
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getSimType().trim(), kycProfile.getResult().getSimType(), "Customer's SIM Type is as Expected", "Customer's SIM Type is not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's SIM Type is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getLineType().isEmpty()) {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getLineType().trim(), "-", "Customer Connection Type as expected", "Customer Connection Type as not expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getLineType().toLowerCase().trim(), kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type as expected", "Customer Connection Type as not expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Connection Type is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getServiceCategory().isEmpty()) {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getServiceCategory().trim(), "-", "Customer Service Category as expected", "Customer Service Category as not expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getServiceCategory().toLowerCase().trim(), kycProfile.getResult().getServiceCategory().toLowerCase().trim(), "Customer Service Category as expected", "Customer Service Category as not expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Service Category is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getSegment().isEmpty()) {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getSegment().trim(), "- -", "Customer Segment as expected", "Customer Segment as not expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getSegment().toLowerCase().trim(), "- " + kycProfile.getResult().getSegment().toLowerCase().trim(), "Customer Segment as expected", "Customer Segment as not expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Segment is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getServiceClass().isEmpty()) {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getServiceClass().trim(), "-", "Customer Service Class as expected", "Customer Service Class as not expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getServiceClass().toLowerCase().trim(), kycProfile.getResult().getServiceClass().toLowerCase().trim(), "Customer Service Class as expected", "Customer Service Class as not expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Service Class is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getVip()) {
                assertCheck.append(actions.assertEqual_boolean(demoGraphicPage.isVIP(), true, "Customer is VIP but Icon displayed as expected", "Customer is VIP but Icon does not display as expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer is VIP or not is not visible" + e.getCause(), true);
        }

        try {
            demoGraphicPage.hoverOnDeviceInfoIcon();
            assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getIMEINumber().trim(), profileAPI.getResult().getImeiNumber(), "Customer device IMEI number as expected", "Customer device IMEI number as not expected"));

            try {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getDeviceType().toLowerCase().trim(), profileAPI.getResult().getType().toLowerCase().trim(), "Customer device type as expected", "Customer device type as not expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device type as not visible." + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getBrand().toLowerCase().trim(), profileAPI.getResult().getBrand().toLowerCase().trim(), "Customer device Brand as expected", "Customer device Brand as not expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device Brand as not visible." + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getDeviceModel().toLowerCase().trim(), profileAPI.getResult().getModel().toLowerCase().trim(), "Customer device model as expected", "Customer device model as not expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device model as not visible." + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqual_stringType(demoGraphicPage.getDeviceOS().toLowerCase().trim(), profileAPI.getResult().getOs().toLowerCase().trim(), "Customer device OS as expected", "Customer device OS as not expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device OS as not visible." + e.getCause(), true);
            }

        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer device IMEI number is not visible." + e.getCause(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User()
    @Test(priority = 3, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionBySIM(TestDatabean Data) {
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
    }

    @DataProviders.User()
    @Test(priority = 4, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionBySIM")
    public void validateDemographicInformationBySIMNumber(TestDatabean Data) {
        final String customerNumber = Data.getCustomerNumber();
        selUtils.addTestcaseDescription("Validating the Demographic Information of User :" + customerNumber, "description");
        DemoGraphic demographic = new DemoGraphic(driver);
        ProfilePOJO profileAPI = api.profileAPITest(CustomerInteractionTest.customerNumber);
        KYCProfile kycProfile = api.KYCProfileAPITest(CustomerInteractionTest.customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(CustomerInteractionTest.customerNumber);
        api.accountPlansTest(CustomerInteractionTest.customerNumber);
        AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(CustomerInteractionTest.customerNumber);

        try {
            if (demographic.isPUKInfoLock()) {
                demographic.clickPUKToUnlock();
                Thread.sleep(5000);
                AuthTab authTab = new AuthTab(driver);
                DataProviders data = new DataProviders();
                authTab.waitTillLoaderGetsRemoved();
                assertCheck.append(actions.assertEqual_boolean(authTab.isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                try {
                    List<AuthTabDataBeans> list = data.getPolicy();
                    for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                        authTab.clickCheckBox(i);
                    }
                    assertCheck.append(actions.assertEqual_boolean(authTab.isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
                    authTab.clickAuthBtn();
                } catch (NoSuchElementException | AssertionError | TimeoutException e) {
                    commonLib.fail("Not able to authenticate user: " + e.fillInStackTrace(), true);
                    authTab.clickCloseBtn();
                }
            }
            try {
                assertCheck.append(actions.assertEqual_stringType(demographic.getPUK1().trim(), kycProfile.getResult().getPuk().get(0).getValue(), "Customer's PUK1 Number is as Expected", "Customer's PUK1 Number is not as Expected"));
            } catch (NoSuchElementException e) {
                commonLib.fail("Customer's PUK1 Number is not visible" + e.getCause(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_stringType(demographic.getPUK2().trim(), kycProfile.getResult().getPuk().get(1).getValue(), "Customer's PUK2 Number is as Expected", "Customer's PUK2 Number is not as Expected"));
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
                } else if (demographic.isAirtelMoneyStatusLock()) {
                    demographic.clickAirtelStatusToUnlock();
                    Thread.sleep(5000);
                    AuthTab authTab = new AuthTab(driver);
                    DataProviders data = new DataProviders();
                    authTab.waitTillLoaderGetsRemoved();
                    assertCheck.append(actions.assertEqual_boolean(authTab.isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                    try {
                        List<AuthTabDataBeans> list = data.getPolicy();
                        for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                            authTab.clickCheckBox(i);
                        }
                        assertCheck.append(actions.assertEqual_boolean(authTab.isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
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
            assertCheck.append(actions.assertEqual_stringType(demographic.getCustomerName().trim(), gsmKycAPI.getResult().getName().trim(), "Customer Name is as Expected", "Customer Name is not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer Name is not visible" + e.getCause(), true);
        }
        try {
            demographic.hoverOnCustomerInfoIcon();
            assertCheck.append(actions.assertEqual_stringType(demographic.getCustomerDOB().trim(), UtilsMethods.getDateFromEpoch(gsmKycAPI.getResult().getDob(), "dd-MMM-yyyy"), "Customer DOB is as Expected", "Customer DOB is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer DOB is not visible or null" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demographic.getIdType().trim(), gsmKycAPI.getResult().getIdentificationType(), "Customer's ID Type is as Expected", "Customer's ID Type is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Id Type is not visible or null" + e.getCause(), true);
        }
        try {
            assertCheck.append(actions.assertEqual_boolean(gsmKycAPI.getResult().getIdentificationNo().contains(demographic.getIdNumber().replace("*", "")), true, "Customer's ID Number is as Expected", "Customer's ID Number is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Id Number is not visible" + e.getCause(), true);
        }
        try {
            demographic.hoverOnSIMNumberIcon();
            assertCheck.append(actions.assertEqual_stringType(demographic.getActivationDate().trim(), UtilsMethods.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "dd-MMM-yyy"), "Customer's Activation Date is as Expected", "Customer's Activation Date is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NumberFormatException | NullPointerException e) {
            commonLib.fail("Customer's Activation Date is not visible" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demographic.getSIMStatus().toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is as Expected", "Customer's SIM Status is not as Expected"));
            demographic.hoverOnSIMStatusInfoIcon();
            assertCheck.append(actions.assertEqual_stringType(demographic.getSIMStatusReasonCode().trim().toLowerCase(), kycProfile.getResult().getReason() == null || kycProfile.getResult().getReason().equals("") ? "-" : kycProfile.getResult().getReason().toLowerCase().trim(), "Customer SIM Status Reason is as Expected", "Customer SIM Status Reason is not as Expected"));
            assertCheck.append(actions.assertEqual_stringType(demographic.getSIMStatusModifiedBy().trim().toLowerCase(), kycProfile.getResult().getModifiedBy().trim().toLowerCase(), "Customer SIM Status Modified By is as Expected", "Customer SIM Status Modified By is not as Expected"));
            assertCheck.append(actions.assertEqual_stringType(demographic.getSIMStatusModifiedDate().trim(), UtilsMethods.getDateFromString(kycProfile.getResult().getModifiedDate(), "dd-MMM-yyy hh:mm aa", "dd-MMM-yyyy hh:mm aa"), "Customer SIM Status Modified Date is as Expected", "Customer SIM Status Modified Date is not as Expected"));
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's SIM Status is not visible" + e.getCause(), true);
        }
        if (!evnName.equalsIgnoreCase("NG")) {
            try {
                assertCheck.append(actions.assertEqual_stringType(demographic.getWalletBalance().toUpperCase().trim(), amProfileAPI.getResult().getWallet().get(0).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallet().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Customer's Airtel Money Wallet Balance is not visible" + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqual_stringType(demographic.getRegistrationStatus().toLowerCase().trim(), amProfileAPI.getResult().getRegStatus().toLowerCase().trim(), "Customer's Airtel Money Registration Status as Expected", "Customer's Airtel Money Registration Status not same not as Expected"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Customer's Airtel Money Registration Status is not visible" + e.getCause(), true);
            }
        }
        try {
            assertCheck.append(actions.assertEqual_stringType(demographic.getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is as Expected", "Customer's Device Type is not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's Device Type is not visible" + e.getCause(), true);
        }

        try {
            assertCheck.append(actions.assertEqual_stringType(demographic.getSimNumber().trim(), customerNumber, "Customer's Mobile Number is as Expected", "Customer's Mobile Number is not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's Mobile Number is not visible" + e.getCause(), true);
        }
        try {
            assertCheck.append(actions.assertEqual_stringType(demographic.getSimType().trim(), kycProfile.getResult().getSimType(), "Customer's SIM Type is as Expected", "Customer's SIM Type is not as Expected"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Customer's SIM Type is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getLineType().isEmpty()) {
                assertCheck.append(actions.assertEqual_stringType(demographic.getLineType().trim(), "-", "Customer Connection Type as expected", "Customer Connection Type as not expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(demographic.getLineType().toLowerCase().trim(), kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type as expected", "Customer Connection Type as not expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Connection Type is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getServiceCategory().isEmpty()) {
                assertCheck.append(actions.assertEqual_stringType(demographic.getServiceCategory().trim(), "-", "Customer Service Category as expected", "Customer Service Category as not expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(demographic.getServiceCategory().toLowerCase().trim(), kycProfile.getResult().getServiceCategory().toLowerCase().trim(), "Customer Service Category as expected", "Customer Service Category as not expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Service Category is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getSegment().isEmpty()) {
                assertCheck.append(actions.assertEqual_stringType(demographic.getSegment().trim(), "- -", "Customer Segment as expected", "Customer Segment as not expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(demographic.getSegment().toLowerCase().trim(), "- " + kycProfile.getResult().getSegment().toLowerCase().trim(), "Customer Segment as expected", "Customer Segment as not expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Segment is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getServiceClass().isEmpty()) {
                assertCheck.append(actions.assertEqual_stringType(demographic.getServiceClass().trim(), "-", "Customer Service Class as expected", "Customer Service Class as not expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(demographic.getServiceClass().toLowerCase().trim(), kycProfile.getResult().getServiceClass().toLowerCase().trim(), "Customer Service Class as expected", "Customer Service Class as not expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer's Service Class is not visible" + e.getCause(), true);
        }

        try {
            if (kycProfile.getResult().getVip()) {
                assertCheck.append(actions.assertEqual_boolean(demographic.isVIP(), true, "Customer is VIP but Icon displayed as expected", "Customer is VIP but Icon does not display as expected"));
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer is VIP or not is not visible" + e.getCause(), true);
        }

        try {
            demographic.hoverOnDeviceInfoIcon();
            assertCheck.append(actions.assertEqual_stringType(demographic.getIMEINumber().trim(), profileAPI.getResult().getImeiNumber(), "Customer device IMEI number as expected", "Customer device IMEI number as not expected"));

            try {
                assertCheck.append(actions.assertEqual_stringType(demographic.getDeviceType().toLowerCase().trim(), profileAPI.getResult().getType().toLowerCase().trim(), "Customer device type as expected", "Customer device type as not expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device type as not visible." + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqual_stringType(demographic.getBrand().toLowerCase().trim(), profileAPI.getResult().getBrand().toLowerCase().trim(), "Customer device Brand as expected", "Customer device Brand as not expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device Brand as not visible." + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqual_stringType(demographic.getDeviceModel().toLowerCase().trim(), profileAPI.getResult().getModel().toLowerCase().trim(), "Customer device model as expected", "Customer device model as not expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device model as not visible." + e.getCause(), true);
            }

            try {
                assertCheck.append(actions.assertEqual_stringType(demographic.getDeviceOS().toLowerCase().trim(), profileAPI.getResult().getOs().toLowerCase().trim(), "Customer device OS as expected", "Customer device OS as not expected"));
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Customer device OS as not visible." + e.getCause(), true);
            }

        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Customer device IMEI number is not visible." + e.getCause(), true);
        }

        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, description = "As an agent I want capability to check if an MSISDN is valid or invalid", dependsOnMethods = "openCustomerInteractionBySIM")
    public void invalidMSISDNTest() {
        selUtils.addTestcaseDescription("Validating the Demographic Information of User with invalid MSISDN : 123456789", "description");
        pages.getDemoGraphicPage().clearSearchBox(customerNumber.length());
        pages.getDemoGraphicPage().enterMSISDN("123456789"); //Entering Invalid MSISDN
        pages.getDemoGraphicPage().waitTillLoaderGetsRemoved();
        try {
            assertCheck.append(actions.assertEqual_boolean(pages.getDemoGraphicPage().invalidMSISDNError(), true, "Invalid MSISDN error message displayed", "Invalid MSISDN error message does not display"));
        } catch (TimeoutException | NoSuchElementException e) {
            commonLib.fail("Invalid Error Message does not display " + e.getMessage(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User(userType = "API")
    @Test(priority = 6, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionAPI(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating the Search for Customer Interactions for Widget :" + data.getCustomerNumber(), "description");
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openCustomerInteractionPage();
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        if (evnName.equalsIgnoreCase("Prod")) {
            customerNumber = data.getProdCustomerNumber();
        } else {
            customerNumber = data.getCustomerNumber();
        }
        pages.getMsisdnSearchPage().enterNumber(customerNumber);
        pages.getMsisdnSearchPage().clickOnSearch();
        if (!pages.getCustomerProfilePage().isCustomerProfilePageLoaded()) {
            commonLib.fail("Test Failed", true);
            commonLib.fail("Customer Info Dashboard Page does not open using MSISDN Number.", true);
            pages.getMsisdnSearchPage().clearCustomerNumber();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "Airtel Money")
    @Test(priority = 7, description = "Validating AM Transaction Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void airtelMoneyTransactionWidgetTest(HeaderDataBean data) throws IOException, UnsupportedFlavorException {
        selUtils.addTestcaseDescription("Validating AM Transaction Widget of User :" + customerNumber, "description");
        final AMTransactionsWidget amTxnWidgetPage = pages.getAmTxnWidgetPage();
        assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isAirtelMoneyTransactionWidgetIsVisible(), true, "Airtel Money Transaction Widget is visible", "Airtel Money Transaction Widget is not visible"));
        assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isAirtelMoneyWidgetDatePickerVisible(), true, "Airtel Money Transaction Widget's Date Picker is visible", "Airtel Money Transaction Widget's Date Picker is not visible"));
        AirtelMoneyPOJO amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
        AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(customerNumber);
        if (amProfileAPI.getStatusCode() == 200) {
            assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.gettingAirtelMoneyBalance(), amProfileAPI.getResult().getWallet().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
            if (OPCO.equalsIgnoreCase("CD")) {
                assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.gettingAirtelMoneyBalance2(), amProfileAPI.getResult().getWallet().get(1).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
            } else {
                commonLib.fail("Not able to fetch 2nd Airtel Money Wallet balance for DRC Opco.", true);
            }
        } else {
            commonLib.fail("API is Unable to Get AM Profile for Customer", true);
        }
        if (amTransactionHistoryAPI.getStatusCode() != 200) {
            assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isAirtelMoneyErrorVisible(), true, "API and Widget both are showing error message", "API is Giving error But Widget is not showing error Message on com.airtel.cs.API is " + amTransactionHistoryAPI.getMessage()));
            commonLib.fail("API is Unable to Get AM Transaction History for Customer", true);
        } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
            assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isAirtelMoneyNoResultFoundVisible(), true, "No Result Found Icon displayed on UI", "No Result Found Icon does not display on UI"));
        } else {
            int count = amTransactionHistoryAPI.getResult().getTotalCount();
            if (count > 0) {
                if (count > 5) {
                    count = 5;
                }
                assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(0).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(1).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(2).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(3).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(4).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isTransactionId(), true, "Transaction Id Search Box displayed on UI", "Transaction Id Search Box does not displayed on UI"));
                for (int i = 0; i < count; i++) {
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 1), amTransactionHistoryAPI.getResult().getData().get(i).getAmount(), "Amount is as expected as com.airtel.cs.API response", "Amount is not expected as com.airtel.cs.API response"));
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 2), amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn(), "Receiver MSISDN is as expected as com.airtel.cs.API response", "Receiver MSISDN is not expected as com.airtel.cs.API response"));
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 3), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate()), config.getProperty("AMHistoryTimeFormat")), "Date is as expected as com.airtel.cs.API response", "Date is not expected as com.airtel.cs.API response"));
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 4), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), "Transaction Id is as expected as com.airtel.cs.API response", "Transaction Id is not expected as com.airtel.cs.API response"));
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 5), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), "Status is as expected as com.airtel.cs.API response", "Status is not expected as com.airtel.cs.API response"));
                    if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                        assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isResendSMS(), true, "Resend SMS Icon is enabled as mentioned in com.airtel.cs.API Response", "Resend SMS Icon does not enable as mentioned in com.airtel.cs.API Response"));
                    }
                    String id = amTxnWidgetPage.doubleClickOnTransactionId(i + 1);
                    String clipboardText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    commonLib.info("Reading Clipboard copied text: " + clipboardText);
                    assertCheck.append(actions.assertEqual_stringType(id, clipboardText, "Transaction id copied to clipboard successfully", "Transaction id does not copy to clipboard"));
                }
            }
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    //Needs Discussion
    @Test(priority = 8, description = "Validating Current Balance Widget", dependsOnMethods = "openCustomerInteractionAPI")
    public void yourCurrentBalanceWidgetTest() {
        selUtils.addTestcaseDescription("Validating Current Balance Transaction Widget of User :" + customerNumber, "description");
        final CurrentBalanceWidget currentBalanceWidgetPage = pages.getCurrentBalanceWidgetPage();
        assertCheck.append(actions.assertEqual_boolean(currentBalanceWidgetPage.isCurrentBalanceWidgetVisible(), true, "Current Balance Widget is visible", "Current Balance Widget is not visible"));

        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        final PlansResultPOJO result = plansAPI.getResult();
        if (result.getMainAccountBalance() != null) {
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.gettingMainAccountBalance(), result.getMainAccountBalance().getBalance(), "Current Balance is as Received in com.airtel.cs.API", "Current Balance is not as Received in com.airtel.cs.API"));
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.gettingCurrentBalanceCurrency(), result.getMainAccountBalance().getCurrency(), "Current Balance Currency is as Received in com.airtel.cs.API", "Current Balance Currency is not as Received in com.airtel.cs.API"));
        } else {
            commonLib.warning("Unable to get Main Balance from com.airtel.cs.API");

        }
        if (result.getLastRecharge() != null) {
            try {
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.gettingLastRechargeAmount(), result.getLastRecharge().getAmount(), "Last Recharge is as Received in com.airtel.cs.API", "Last Recharge is not as Received in com.airtel.cs.API"));
            } catch (NumberFormatException e) {
                commonLib.fail("Last Recharge is not in expected format", true);
                commonLib.fail("Last Recharge is not in expected format", true);
            }
            String Time = UtilsMethods.getDateFromEpochInUTC(result.getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
            String Date = UtilsMethods.getDateFromEpochInUTC(result.getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is as Received in com.airtel.cs.API", "Last Recharge Date and Time is not as Received in com.airtel.cs.API"));
        } else {
            commonLib.warning("Unable to get Last Recharge Details from com.airtel.cs.API");
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.gettingLastRechargeAmount().replace('-', ' ').trim(), "", "Last Recharge Amount is as expected", "Last Recharge Amount is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getLastRechargeDateTime(), "- -", "Last Recharge Date & Time is as expected", "Last Recharge Date & Time is not as expected"));
        }

        log.info(result.toString());
        if (result.getVoice() != null) {
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getVoiceExpiryDate(), UtilsMethods.getDateFromEpoch(result.getVoice().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Voice Expiry Date is as Received in com.airtel.cs.API", "Voice Expiry Date is not as Received in com.airtel.cs.API"));
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getVoiceBalance().replace("-", "null"), result.getVoice().getBalance(), "Voice Balance is as Received in com.airtel.cs.API", "Voice Balance is not as Received in com.airtel.cs.API"));
        }
        if (result.getData() != null) {
            try {
                double amount = Double.parseDouble(result.getData().getBalance().split(" ")[0]);
                if (amount > 0) {
                    String unit = result.getData().getBalance().split(" ")[1];
                    if (unit.equalsIgnoreCase("MB") && amount > 1024) {
                        commonLib.fail("MB to GB conversion does not done Correctly. Data Balance" + currentBalanceWidgetPage.getDataBalance(), true);
                    } else {
                        commonLib.pass("MB to GB Conversion Verified. Balance " + currentBalanceWidgetPage.getDataBalance());
                    }
                }
            } catch (NumberFormatException ns) {
                commonLib.info("Not able to fetch amount" + ns.fillInStackTrace());
            }
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getDataBalance().replace("-", "null"), result.getData().getBalance(), "Data Balance is as Received in com.airtel.cs.API", "Data Balance is not as Received in com.airtel.cs.API"));
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getDataExpiryDate(), UtilsMethods.getDateFromEpoch(result.getData().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Data Expiry Date is as Received in com.airtel.cs.API", "Data Expiry Date is not as Received in com.airtel.cs.API"));
        }
        if (result.getSms() != null) {

            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getSmsExpiryDate(), UtilsMethods.getDateFromEpoch(result.getSms().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "SMS Expiry Date is as Received in com.airtel.cs.API", "SMS Expiry Date is not as Received in com.airtel.cs.API"));
            assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getSmsBalance().replace("-", "null"), result.getSms().getBalance(), "SMS Balance is as Received in com.airtel.cs.API", "SMS Balance is not as Received in com.airtel.cs.API"));
        }
        if (plansAPI.getStatusCode() != 200) {
            commonLib.fail("com.airtel.cs.API unable to get Last recharge and MAIN Balance ", true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @DataProviders.Table(name = "Usage History")
    @Test(priority = 9, description = "Validating Usage History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void usageHistoryWidgetTest(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating Usage History Widget of User :" + customerNumber, "description");
        final UsageHistoryWidget usageHistoryWidget = pages.getUsageHistoryWidget();
        assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryDatePickerVisible(), true, "Usage History Widget's Date Picker is visible", "Usage History Widget's Date Picker is not visible"));

        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
        int size = usageHistoryWidget.getNumberOfRows();
        if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
            assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
            assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
        } else {
            assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
            for (int i = 0; i < size; i++) {
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is As received in com.airtel.cs.API for row number " + i, "Usage History Type is not As received in com.airtel.cs.API for row number " + i));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryCharge(i).replaceAll("[^0-9]", "").trim(), usageHistoryAPI.getResult().get(i).getCharges().replaceAll("[^0-9]", ""), "Usage History Charge is As received in com.airtel.cs.API for row number " + i, "Usage History Charge is not As received in com.airtel.cs.API for row number " + i));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryDateTime(i), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Usage History Date Time is As received in com.airtel.cs.API for row number " + i, "Usage History Date Time is not As received in com.airtel.cs.API for row number " + i));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is As received in com.airtel.cs.API for row number " + i, "Usage History Start Balance  is not As received in com.airtel.cs.API for row number " + i));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is As received in com.airtel.cs.API for row number " + i, "Usage History End Balance is not As received in com.airtel.cs.API for row number " + i));
                if (i != 0) {
                    assertCheck.append(actions.assertEqual_boolean(UtilsMethods.isSortOrderDisplay(usageHistoryWidget.getHistoryDateTime(i).replace("\n", " "), usageHistoryWidget.getHistoryDateTime(i - 1).replace("\n", " "), "EEE dd MMM yyy hh:mm:ss aa"), true, usageHistoryWidget.getHistoryDateTime(i - 1) + "displayed before " + usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getHistoryDateTime(i - 1) + "should not display before " + usageHistoryWidget.getHistoryDateTime(i)));
                }
            }
        }
        if (usageHistoryAPI.getStatusCode() != 200) {
            assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryErrorVisible(), true, "API and widget both are giving error message", "API is Giving error But Widget is not showing error Message on com.airtel.cs.API is "));
            commonLib.fail("com.airtel.cs.API is unable to give Usage History ", true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @DataProviders.Table(name = "Recharge History")
    @Test(priority = 10, description = "Validating Recharge History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void rechargeHistoryWidgetTest(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating Recharge History Widget of User :" + customerNumber, "description");
        final RechargeHistoryWidget rechargeHistoryWidget = pages.getRechargeHistoryWidget();
        assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), true, "Recharge History Widget is visible", "Recharge History Widget is not visible"));
        assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), true, "Recharge History Widget's Date Picker is visible", "Recharge History Widget's Date Picker is not visible"));
        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
        if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryErrorVisible(), true, "com.airtel.cs.API and widget both are Giving error", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
            commonLib.fail("com.airtel.cs.API is unable to give Recharge History ", true);
        } else {
            int size = rechargeHistoryWidget.getNumberOfRows();
            if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get Last Recharge Details from com.airtel.cs.API");
                assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(1).toLowerCase().trim() + " " + rechargeHistoryWidget.getSubHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(4).toLowerCase().trim() + rechargeHistoryWidget.getSubHeaders(4).toLowerCase().trim().replace("|", ""), data.getRow4().toLowerCase().replace("|", "").trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryCharges(i + 1), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is As received in com.airtel.cs.API for row number " + i, "Recharge History Charge is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1), UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), config.getProperty("UIRechargeHistoryTimeFormat"), config.getProperty("APIRechargeHistoryTimeFormat")), "Recharge History Date Time is As received in com.airtel.cs.API for row number " + i, "Recharge History Date Time is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryBundleName(i + 1), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is As received in com.airtel.cs.API for row number " + i, "Recharge History Bundle Name is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryBenefits(i + 1).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is As received in com.airtel.cs.API for row number " + i, "Recharge History Benefits is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryStatus(i + 1), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is As received in com.airtel.cs.API for row number " + i, "Recharge History Status is not As received in com.airtel.cs.API for row number " + i));
                    if (i != 0) {
                        assertCheck.append(actions.assertEqual_boolean(UtilsMethods.isSortOrderDisplay(rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1), rechargeHistoryWidget.getRechargeHistoryDateTime(i), "dd-MMM-yyy HH:mm"), true, rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1) + "displayed before " + rechargeHistoryWidget.getRechargeHistoryDateTime(i), rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1) + "should not display before " + rechargeHistoryWidget.getRechargeHistoryDateTime(i)));
                    }
                }

            }
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "Service Profile")
    @Test(priority = 11, description = "Verify Service Profile Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void validateServiceProfileWidget(HeaderDataBean data) {
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
    }
}
