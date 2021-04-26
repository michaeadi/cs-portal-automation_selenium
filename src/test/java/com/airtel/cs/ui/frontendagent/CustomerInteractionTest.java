package com.airtel.cs.ui.frontendagent;


import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.dataproviders.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.AuthTab;
import com.airtel.cs.pagerepository.pagemethods.DemoGraphic;
import com.airtel.cs.pojo.AMProfilePOJO;
import com.airtel.cs.pojo.GsmKycPOJO;
import com.airtel.cs.pojo.PlansPOJO;
import com.airtel.cs.pojo.ProfilePOJO;
import com.airtel.cs.pojo.RechargeHistoryPOJO;
import com.airtel.cs.pojo.UsageHistoryPOJO;
import com.airtel.cs.pojo.airtelmoney.AirtelMoneyPOJO;
import com.airtel.cs.pojo.hlrservice.HLRServicePOJO;
import com.airtel.cs.pojo.kycprofile.KYCProfile;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

@Log4j2
public class CustomerInteractionTest extends Driver {

    static String customerNumber;
    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA || !continueExecutionAPI) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        SoftAssert softAssert = new SoftAssert();
        if (continueExecutionAPI) {
            final String customerNumber = data.getCustomerNumber();
            selUtils.addTestcaseDescription("Validating the Search for Customer Interactions :" + customerNumber, "description");
            if (evnName.equalsIgnoreCase("Prod")) {
                CustomerInteractionTest.customerNumber = data.getProdCustomerNumber();
            } else {
                CustomerInteractionTest.customerNumber = customerNumber;
            }
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(CustomerInteractionTest.customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            if (!pages.getCustomerProfilePage().isPageLoaded()) {
                softAssert.fail("Customer Info Dashboard Page does not open using SIM Number.");
                pages.getMsisdnSearchPage().clearCustomerNumber();
            }

        } else {
            softAssert.fail("Execution Terminate due to either agent logout or User Password Update");
        }
        softAssert.assertAll();
    }


    @DataProviders.User()
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateDemographicInformation(TestDatabean data) {
        SoftAssert softAssert = new SoftAssert();
        DataProviders dataProviders = new DataProviders();
        selUtils.addTestcaseDescription("Validating the Demographic Information of User :" + data.getCustomerNumber(), "description");
        ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
        KYCProfile kycProfile = api.KYCProfileAPITest(customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(customerNumber);
        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(customerNumber);

        try {
            if (pages.getDemoGraphicPage().isPUKInfoLock()) {
                pages.getDemoGraphicPage().clickPUKToUnlock();
                Thread.sleep(5000);
                AuthTab authTab = new AuthTab(driver);
                authTab.waitTillLoaderGetsRemoved();
                Assert.assertTrue(authTab.isAuthTabLoad(), "Authentication tab does not load correctly");
                try {
                    List<AuthTabDataBeans> list = dataProviders.getPolicy();
                    for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                        authTab.clickCheckBox(i);
                    }
                    Assert.assertTrue(authTab.isAuthBtnEnable(), "Authenticate Button does not enable after choose minimum number of question");
                    authTab.clickAuthBtn();
                } catch (NoSuchElementException | AssertionError | TimeoutException e) {
                    e.printStackTrace();
                    String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                            getScreenshotAs(OutputType.BASE64);
                    commonLib.fail("Not able to authenticate user: ", true);
                    softAssert.fail("Not able to authenticate user: " + e.fillInStackTrace());
                    authTab.clickCloseBtn();
                }
            }
            try {
                softAssert.assertEquals(pages.getDemoGraphicPage().getPUK1().trim(), kycProfile.getResult().getPuk().get(0).getValue(), "Customer's PUK1 Number is not as Expected");
            } catch (NoSuchElementException e) {
                softAssert.fail("Customer's PUK1 Number is not visible", e.getCause());
                e.printStackTrace();
            }
            try {
                softAssert.assertEquals(pages.getDemoGraphicPage().getPUK2().trim(), kycProfile.getResult().getPuk().get(1).getValue(), "Customer's PUK2 Number is not as Expected");
            } catch (NoSuchElementException e) {
                softAssert.fail("Customer's  PUK2 Number is not visible", e.getCause());
                e.printStackTrace();
            }

        } catch (NoSuchElementException | TimeoutException | InterruptedException | AssertionError e) {
            e.printStackTrace();
            softAssert.fail("Not able to View PUK Details" + e.getMessage());
        }

        try {
            if (pages.getDemoGraphicPage().isAirtelMoneyStatusLock()) {
                pages.getDemoGraphicPage().clickAirtelStatusToUnlock();
                AuthTab authTab = new AuthTab(driver);
                authTab.waitTillLoaderGetsRemoved();
                Assert.assertTrue(authTab.isAuthTabLoad(), "Authentication tab does not load correctly");
                try {
                    List<AuthTabDataBeans> list = dataProviders.getPolicy();
                    for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                        authTab.clickCheckBox(i);
                    }
                    Assert.assertTrue(authTab.isAuthBtnEnable(), "Authenticate Button does not enable after choose minimum number of question");
                    authTab.clickAuthBtn();
                } catch (NoSuchElementException | TimeoutException e) {
                    e.fillInStackTrace();
                    softAssert.fail("Action(Airtel Money Status)Not able to authenticate user: " + e.fillInStackTrace());
                    authTab.clickCloseBtn();
                }
            }


        } catch (NoSuchElementException | TimeoutException | InterruptedException | AssertionError e) {
            e.printStackTrace();
            softAssert.fail("Airtel Money Status does not unlock" + e.getMessage());
        }

        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getCustomerName().trim(), gsmKycAPI.getResult().getName().trim(), "Customer Name is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer Name is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            pages.getDemoGraphicPage().hoverOnCustomerInfoIcon();
            softAssert.assertEquals(pages.getDemoGraphicPage().getCustomerDOB().trim(), UtilsMethods.getDateFromEpoch(gsmKycAPI.getResult().getDob(), "dd-MMM-yyyy"), "Customer DOB is not as Expected");
            if (UtilsMethods.isCustomerBirthday(pages.getDemoGraphicPage().getCustomerDOB().trim(), "dd-MMM-yyyy")) {
                commonLib.pass("Today is Customer Birthday");
                softAssert.assertTrue(pages.getDemoGraphicPage().isBirthday(), "Today is customer birthday but does not display birthday icon.");
            } else {
                softAssert.assertFalse(pages.getDemoGraphicPage().isBirthday(), "Today is not customer birthday but birthday icon display.");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer DOB is not visible or null", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getIdType().trim(), gsmKycAPI.getResult().getIdentificationType(), "Customer's ID Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Id Type is not visible or null", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertTrue(gsmKycAPI.getResult().getIdentificationNo().contains(pages.getDemoGraphicPage().getIdNumber().replace("*", "")), "Customer's ID Number is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Id Number is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            pages.getDemoGraphicPage().hoverOnSIMNumberIcon();
            softAssert.assertEquals(pages.getDemoGraphicPage().getActivationDate().trim(), UtilsMethods.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "dd-MMM-yyy"), "Customer's Activation Date is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NumberFormatException | NullPointerException e) {
            softAssert.fail("Customer's Activation Date is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getSIMStatus().toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is not as Expected");
            pages.getDemoGraphicPage().hoverOnSIMStatusInfoIcon();
            softAssert.assertEquals(pages.getDemoGraphicPage().getSIMStatusReasonCode().trim().toLowerCase(), kycProfile.getResult().getReason() == null || kycProfile.getResult().getReason() == "" ? "-" : kycProfile.getResult().getReason().toLowerCase().trim(), "Customer SIM Status Reason is not as Expected");
            softAssert.assertEquals(pages.getDemoGraphicPage().getSIMStatusModifiedBy().trim().toLowerCase(), kycProfile.getResult().getModifiedBy().trim().toLowerCase(), "Customer SIM Status Modified By is not as Expected");
            softAssert.assertEquals(pages.getDemoGraphicPage().getSIMStatusModifiedDate().trim(), UtilsMethods.getDateFromString(kycProfile.getResult().getModifiedDate(), "dd-MMM-yyy hh:mm aa", "dd-MMM-yyyy hh:mm aa"), "Customer SIM Status Modified Date is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's SIM Status is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (pages.getDemoGraphicPage().getDataManagerStatus().equalsIgnoreCase("true")) {
                softAssert.assertEquals("on", plansAPI.getResult().getDataManager().toLowerCase().trim(), "Customer's Data Manager Status is not as Expected");
            } else {
                softAssert.assertEquals("off", plansAPI.getResult().getDataManager().toLowerCase().trim(), "Customer's Data Manager Status is not as Expected");
            }

        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Data Manager Status is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getAirtelMoneyStatus().toLowerCase().trim(), profileAPI.getResult().getAirtelMoneyStatus().toLowerCase().trim(), "Customer's Airtel Money Status is not as Expected");

        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Airtel Money Status is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getServiceStatus().toLowerCase().trim(), profileAPI.getResult().getServiceStatus().toLowerCase().trim(), "Customer's Airtel Money Service Status is not as Expected");

        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Airtel Money Service Status is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getWalletBalance().toUpperCase().trim(), amProfileAPI.getResult().getWallet().get(0).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallet().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code not same not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Airtel Money Wallet Balance is not visible", e.getCause());
            e.printStackTrace();
        }

        if (OPCO.equalsIgnoreCase("CD")) {
            try {
                softAssert.assertEquals(pages.getDemoGraphicPage().getWalletBalance2().toUpperCase().trim(), amProfileAPI.getResult().getWallet().get(1).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallet().get(1).getBalance(), "Customer's Airtel Wallet Balance & Currency code not same not as Expected");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Customer's Airtel Money 2nd Wallet Balance is not visible", e.getCause());
                e.printStackTrace();
            }
        }
        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getRegistrationStatus().toLowerCase().trim(), amProfileAPI.getResult().getRegStatus().toLowerCase().trim(), "Customer's Airtel Money Registration Status not same not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Airtel Money Registration Status is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Device Type is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getSimNumber().trim(), kycProfile.getResult().getSim(), "Customer's SIM Number is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's SIM Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(pages.getDemoGraphicPage().getSimType().trim(), kycProfile.getResult().getSimType(), "Customer's SIM Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's SIM Type is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getLineType().isEmpty()) {
                softAssert.assertEquals(pages.getDemoGraphicPage().getLineType().trim(), "-", "Customer Connection Type as not expected");
            } else {
                softAssert.assertEquals(pages.getDemoGraphicPage().getLineType().toLowerCase().trim(), kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Connection Type is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getServiceCategory().isEmpty()) {
                softAssert.assertEquals(pages.getDemoGraphicPage().getServiceCategory().trim(), "-", "Customer Service Category as not expected");
            } else {
                softAssert.assertEquals(pages.getDemoGraphicPage().getServiceCategory().toLowerCase().trim(), kycProfile.getResult().getServiceCategory().toLowerCase().trim(), "Customer Service Category as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Service Category is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getSegment().isEmpty()) {
                softAssert.assertEquals(pages.getDemoGraphicPage().getSegment().trim(), "- -", "Customer Segment as not expected");
            } else {
                softAssert.assertEquals(pages.getDemoGraphicPage().getSegment().toLowerCase().trim(), "- " + kycProfile.getResult().getSegment().toLowerCase().trim(), "Customer Segment as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Segment is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getServiceClass().isEmpty()) {
                softAssert.assertEquals(pages.getDemoGraphicPage().getServiceClass().trim(), "-", "Customer Service Class as not expected");
            } else {
                softAssert.assertEquals(pages.getDemoGraphicPage().getServiceClass().toLowerCase().trim(), kycProfile.getResult().getServiceClass().toLowerCase().trim(), "Customer Service Class as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Service Class is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getVip()) {
                softAssert.assertTrue(pages.getDemoGraphicPage().isVIP(), "Customer is VIP but Icon does not display as expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer is VIP or not is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            pages.getDemoGraphicPage().hoverOnDeviceInfoIcon();
            softAssert.assertEquals(pages.getDemoGraphicPage().getIMEINumber().trim(), profileAPI.getResult().getImeiNumber(), "Customer device IMEI number as not expected.");

            try {
                softAssert.assertEquals(pages.getDemoGraphicPage().getDeviceType().toLowerCase().trim(), profileAPI.getResult().getType().toLowerCase().trim(), "Customer device type as not expected.");
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                softAssert.fail("Customer device type as not visible.", e.getCause());
                e.printStackTrace();
            }

            try {
                softAssert.assertEquals(pages.getDemoGraphicPage().getBrand().toLowerCase().trim(), profileAPI.getResult().getBrand().toLowerCase().trim(), "Customer device Brand as not expected.");
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                softAssert.fail("Customer device Brand as not visible.", e.getCause());
                e.printStackTrace();
            }

            try {
                softAssert.assertEquals(pages.getDemoGraphicPage().getDeviceModel().toLowerCase().trim(), profileAPI.getResult().getModel().toLowerCase().trim(), "Customer device model as not expected.");
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                softAssert.fail("Customer device model as not visible.", e.getCause());
                e.printStackTrace();
            }

            try {
                softAssert.assertEquals(pages.getDemoGraphicPage().getDeviceOS().toLowerCase().trim(), profileAPI.getResult().getOs().toLowerCase().trim(), "Customer device OS as not expected.");
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                softAssert.fail("Customer device OS as not visible.", e.getCause());
                e.printStackTrace();
            }

        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer device IMEI number is not visible.", e.getCause());
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(priority = 3, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionBySIM(TestDatabean Data) {
        selUtils.addTestcaseDescription("Validating the Search for Customer Interactions By Using SIM Number :" + Data.getSimNumber(), "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnName();
        pages.getSideMenuPage().openCustomerInteractionPage();
        if (evnName.equalsIgnoreCase("Prod")) {
            pages.getMsisdnSearchPage().enterNumber(Data.getProdSIMNumber());
            customerNumber = Data.getProdCustomerNumber();
        } else {
            pages.getMsisdnSearchPage().enterNumber(Data.getSimNumber());
            customerNumber = Data.getCustomerNumber();
        }
        pages.getMsisdnSearchPage().clickOnSearch();
        if (!pages.getCustomerProfilePage().isPageLoaded()) {
            softAssert.fail("Customer Info Dashboard Page does not open using SIM Number.");
            String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);
            commonLib.fail("Customer Info Dashboard Page does not open using SIM Number.", true);
            pages.getMsisdnSearchPage().clearCustomerNumber();
        }
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(priority = 4, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionBySIM")
    public void validateDemographicInformationBySIMNumber(TestDatabean Data) {
        final String customerNumber = Data.getCustomerNumber();
        selUtils.addTestcaseDescription("Validating the Demographic Information of User :" + customerNumber, "description");
        DemoGraphic demographic = new DemoGraphic(driver);
        SoftAssert softAssert = new SoftAssert();

        ProfilePOJO profileAPI = api.profileAPITest(CustomerInteractionTest.customerNumber);
        KYCProfile kycProfile = api.KYCProfileAPITest(CustomerInteractionTest.customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(CustomerInteractionTest.customerNumber);
        PlansPOJO plansAPI = api.accountPlansTest(CustomerInteractionTest.customerNumber);
        AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(CustomerInteractionTest.customerNumber);

        try {
            if (demographic.isPUKInfoLock()) {
                demographic.clickPUKToUnlock();
                Thread.sleep(5000);
                AuthTab authTab = new AuthTab(driver);
                DataProviders data = new DataProviders();
                authTab.waitTillLoaderGetsRemoved();
                Assert.assertTrue(authTab.isAuthTabLoad(), "Authentication tab does not load correctly");
                try {
                    List<AuthTabDataBeans> list = data.getPolicy();
                    for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                        authTab.clickCheckBox(i);
                    }
                    Assert.assertTrue(authTab.isAuthBtnEnable(), "Authenticate Button does not enable after choose minimum number of question");
                    authTab.clickAuthBtn();
                } catch (NoSuchElementException | AssertionError | TimeoutException e) {
                    e.printStackTrace();
                    String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                            getScreenshotAs(OutputType.BASE64);
                    commonLib.fail("Not able to authenticate user:", true);
                    softAssert.fail("Not able to authenticate user: " + e.fillInStackTrace());
                    authTab.clickCloseBtn();
                }
            }
            try {
                softAssert.assertEquals(demographic.getPUK1().trim(), kycProfile.getResult().getPuk().get(0).getValue(), "Customer's PUK1 Number is not as Expected");
            } catch (NoSuchElementException e) {
                softAssert.fail("Customer's PUK1 Number is not visible", e.getCause());
                e.printStackTrace();
            }
            try {
                softAssert.assertEquals(demographic.getPUK2().trim(), kycProfile.getResult().getPuk().get(1).getValue(), "Customer's PUK2 Number is not as Expected");
            } catch (NoSuchElementException e) {
                softAssert.fail("Customer's  PUK2 Number is not visible", e.getCause());
                e.printStackTrace();
            }

        } catch (NoSuchElementException | TimeoutException | InterruptedException | AssertionError e) {
            e.printStackTrace();
            softAssert.fail("Not able to View PUK Details" + e.getMessage());
        }

        try {
            if (pages.getDemoGraphicPage().checkAMProfileWidget()) {
                if (evnName.equalsIgnoreCase("NG")) {
                    softAssert.fail("AM Profile Widget Must not display for NG Opco.");
                } else if (demographic.isAirtelMoneyStatusLock()) {
                    demographic.clickAirtelStatusToUnlock();
                    Thread.sleep(5000);
                    AuthTab authTab = new AuthTab(driver);
                    DataProviders data = new DataProviders();
                    authTab.waitTillLoaderGetsRemoved();
                    Assert.assertTrue(authTab.isAuthTabLoad(), "Authentication tab does not load correctly");
                    try {
                        List<AuthTabDataBeans> list = data.getPolicy();
                        for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                            authTab.clickCheckBox(i);
                        }
                        Assert.assertTrue(authTab.isAuthBtnEnable(), "Authenticate Button does not enable after choose minimum number of question");
                        authTab.clickAuthBtn();
                    } catch (NoSuchElementException | TimeoutException e) {
                        e.fillInStackTrace();
                        softAssert.fail("Action(Airtel Money Status)Not able to authenticate user: " + e.fillInStackTrace());
                        authTab.clickCloseBtn();
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException | InterruptedException | AssertionError e) {
            e.printStackTrace();
            softAssert.fail("Airtel Money Status does not unlock" + e.getMessage());
        }

        try {
            softAssert.assertEquals(demographic.getCustomerName().trim(), gsmKycAPI.getResult().getName().trim(), "Customer Name is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer Name is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            demographic.hoverOnCustomerInfoIcon();
            softAssert.assertEquals(demographic.getCustomerDOB().trim(), UtilsMethods.getDateFromEpoch(gsmKycAPI.getResult().getDob(), "dd-MMM-yyyy"), "Customer DOB is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer DOB is not visible or null", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getIdType().trim(), gsmKycAPI.getResult().getIdentificationType(), "Customer's ID Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Id Type is not visible or null", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertTrue(gsmKycAPI.getResult().getIdentificationNo().contains(demographic.getIdNumber().replace("*", "")), "Customer's ID Number is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Id Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            demographic.hoverOnSIMNumberIcon();
            softAssert.assertEquals(demographic.getActivationDate().trim(), UtilsMethods.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "dd-MMM-yyy"), "Customer's Activation Date is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NumberFormatException | NullPointerException e) {
            softAssert.fail("Customer's Activation Date is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getSIMStatus().toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is not as Expected");
            demographic.hoverOnSIMStatusInfoIcon();
            softAssert.assertEquals(demographic.getSIMStatusReasonCode().trim().toLowerCase(), kycProfile.getResult().getReason() == null || kycProfile.getResult().getReason() == "" ? "-" : kycProfile.getResult().getReason().toLowerCase().trim(), "Customer SIM Status Reason is not as Expected");
            softAssert.assertEquals(demographic.getSIMStatusModifiedBy().trim().toLowerCase(), kycProfile.getResult().getModifiedBy().trim().toLowerCase(), "Customer SIM Status Modified By is not as Expected");
            softAssert.assertEquals(demographic.getSIMStatusModifiedDate().trim(), UtilsMethods.getDateFromString(kycProfile.getResult().getModifiedDate(), "dd-MMM-yyy hh:mm aa", "dd-MMM-yyyy hh:mm aa"), "Customer SIM Status Modified Date is not as Expected");
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's SIM Status is not visible", e.getCause());
            e.printStackTrace();
        }
        if (!evnName.equalsIgnoreCase("NG")) {
            try {
                softAssert.assertEquals(demographic.getWalletBalance().toUpperCase().trim(), amProfileAPI.getResult().getWallet().get(0).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallet().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code not same not as Expected");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Customer's Airtel Money Wallet Balance is not visible", e.getCause());
                e.printStackTrace();
            }

            try {
                softAssert.assertEquals(demographic.getRegistrationStatus().toLowerCase().trim(), amProfileAPI.getResult().getRegStatus().toLowerCase().trim(), "Customer's Airtel Money Registration Status not same not as Expected");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Customer's Airtel Money Registration Status is not visible", e.getCause());
                e.printStackTrace();
            }
        }
        try {
            softAssert.assertEquals(demographic.getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Device Type is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getSimNumber().trim(), customerNumber, "Customer's Mobile Number is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Mobile Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(demographic.getSimType().trim(), kycProfile.getResult().getSimType(), "Customer's SIM Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's SIM Type is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getLineType().isEmpty()) {
                softAssert.assertEquals(demographic.getLineType().trim(), "-", "Customer Connection Type as not expected");
            } else {
                softAssert.assertEquals(demographic.getLineType().toLowerCase().trim(), kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Connection Type is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getServiceCategory().isEmpty()) {
                softAssert.assertEquals(demographic.getServiceCategory().trim(), "-", "Customer Service Category as not expected");
            } else {
                softAssert.assertEquals(demographic.getServiceCategory().toLowerCase().trim(), kycProfile.getResult().getServiceCategory().toLowerCase().trim(), "Customer Service Category as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Service Category is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getSegment().isEmpty()) {
                softAssert.assertEquals(demographic.getSegment().trim(), "- -", "Customer Segment as not expected");
            } else {
                softAssert.assertEquals(demographic.getSegment().toLowerCase().trim(), "- " + kycProfile.getResult().getSegment().toLowerCase().trim(), "Customer Segment as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Segment is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getServiceClass().isEmpty()) {
                softAssert.assertEquals(demographic.getServiceClass().trim(), "-", "Customer Service Class as not expected");
            } else {
                softAssert.assertEquals(demographic.getServiceClass().toLowerCase().trim(), kycProfile.getResult().getServiceClass().toLowerCase().trim(), "Customer Service Class as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Service Class is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getVip()) {
                softAssert.assertTrue(demographic.isVIP(), "Customer is VIP but Icon does not display as expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer is VIP or not is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            demographic.hoverOnDeviceInfoIcon();
            softAssert.assertEquals(demographic.getIMEINumber().trim(), profileAPI.getResult().getImeiNumber(), "Customer device IMEI number as not expected.");

            try {
                softAssert.assertEquals(demographic.getDeviceType().toLowerCase().trim(), profileAPI.getResult().getType().toLowerCase().trim(), "Customer device type as not expected.");
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                softAssert.fail("Customer device type as not visible.", e.getCause());
                e.printStackTrace();
            }

            try {
                softAssert.assertEquals(demographic.getBrand().toLowerCase().trim(), profileAPI.getResult().getBrand().toLowerCase().trim(), "Customer device Brand as not expected.");
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                softAssert.fail("Customer device Brand as not visible.", e.getCause());
                e.printStackTrace();
            }

            try {
                softAssert.assertEquals(demographic.getDeviceModel().toLowerCase().trim(), profileAPI.getResult().getModel().toLowerCase().trim(), "Customer device model as not expected.");
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                softAssert.fail("Customer device model as not visible.", e.getCause());
                e.printStackTrace();
            }

            try {
                softAssert.assertEquals(demographic.getDeviceOS().toLowerCase().trim(), profileAPI.getResult().getOs().toLowerCase().trim(), "Customer device OS as not expected.");
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                softAssert.fail("Customer device OS as not visible.", e.getCause());
                e.printStackTrace();
            }

        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer device IMEI number is not visible.", e.getCause());
            e.printStackTrace();
        }

        softAssert.assertAll();
    }

    @Test(priority = 5, description = "As an agent I want capability to check if an MSISDN is valid or invalid", dependsOnMethods = "openCustomerInteractionBySIM")
    public void invalidMSISDNTest() {
        selUtils.addTestcaseDescription("Validating the Demographic Information of User with invalid MSISDN : 123456789", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getDemoGraphicPage().clearSearchBox(customerNumber.length());
        pages.getDemoGraphicPage().enterMSISDN("123456789"); //Entering Invalid MSISDN
        pages.getDemoGraphicPage().waitTillLoaderGetsRemoved();
        try {
            softAssert.assertTrue(pages.getDemoGraphicPage().invalidMSISDNError(), "Invalid MSISDN error message does not display");
        } catch (TimeoutException | NoSuchElementException e) {
            softAssert.fail("Invalid Error Message does not display " + e.getMessage());
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "API")
    @Test(priority = 6, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionAPI(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating the Search for Customer Interactions for Widget :" + data.getCustomerNumber(), "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnName();
        pages.getSideMenuPage().openCustomerInteractionPage();
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        if (evnName.equalsIgnoreCase("Prod")) {
            customerNumber = data.getProdCustomerNumber();
        } else {
            customerNumber = data.getCustomerNumber();
        }
        pages.getMsisdnSearchPage().enterNumber(customerNumber);
        pages.getMsisdnSearchPage().clickOnSearch();
        if (!pages.getCustomerProfilePage().isPageLoaded()) {
            //Take base64Screenshot screenshot.
            String base64Screenshot1 = "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);

            //ExtentReports log and screenshot operations for failed tests.
            commonLib.fail("Test Failed", true);
            softAssert.fail("Customer Info Dashboard Page does not open using MSISDN Number.");
            pages.getMsisdnSearchPage().clearCustomerNumber();
        }
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Airtel Money")
    @Test(priority = 7, description = "Validating AM Transaction Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void airtelMoneyTransactionWidgetTest(HeaderDataBean data) throws IOException, UnsupportedFlavorException {
        selUtils.addTestcaseDescription("Validating AM Transaction Widget of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getAmTxnWidgetPage().isAirtelMoneyTransactionWidgetIsVisible(), "Airtel Money Transaction Widget is not visible");
        softAssert.assertTrue(pages.getAmTxnWidgetPage().isAirtelMoneyWidgetDatePickerVisible(), "Airtel Money Transaction Widget's Date Picker is not visible");
        AirtelMoneyPOJO amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
        AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(customerNumber);
        if (amProfileAPI.getStatusCode() == 200) {
            softAssert.assertEquals(pages.getAmTxnWidgetPage().gettingAirtelMoneyBalance(), amProfileAPI.getResult().getWallet().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code not same not as Expected");
            if (OPCO.equalsIgnoreCase("CD")) {
                softAssert.assertEquals(pages.getAmTxnWidgetPage().gettingAirtelMoneyBalance2(), amProfileAPI.getResult().getWallet().get(1).getBalance(), "Customer's Airtel Wallet Balance & Currency code not same not as Expected");
            } else {
                softAssert.fail("Not able to fetch 2nd Airtel Money Wallet balance for DRC Opco.");
            }
        } else {
            softAssert.fail("API is Unable to Get AM Profile for Customer");
        }
        if (amTransactionHistoryAPI.getStatusCode() != 200) {
            softAssert.assertTrue(pages.getAmTxnWidgetPage().isAirtelMoneyErrorVisible(), "API is Giving error But Widget is not showing error Message on com.airtel.cs.API is " + amTransactionHistoryAPI.getMessage());
            softAssert.fail("API is Unable to Get AM Transaction History for Customer");
        } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
            softAssert.assertTrue(pages.getAmTxnWidgetPage().isAirtelMoneyNoResultFoundVisible(), "No Result Found Icon does not display on UI.");
        } else {
            int count = amTransactionHistoryAPI.getResult().getTotalCount();
            if (count > 0) {
                if (count > 5) {
                    count = 5;
                }
                softAssert.assertEquals(pages.getAmTxnWidgetPage().getHeaders(0).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                softAssert.assertEquals(pages.getAmTxnWidgetPage().getHeaders(1).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                softAssert.assertEquals(pages.getAmTxnWidgetPage().getHeaders(2).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                softAssert.assertEquals(pages.getAmTxnWidgetPage().getHeaders(3).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
                softAssert.assertEquals(pages.getAmTxnWidgetPage().getHeaders(4).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                softAssert.assertTrue(pages.getAmTxnWidgetPage().isTransactionId(), "Transaction Id Search Box does not displayed on UI");
                for (int i = 0; i < count; i++) {
                    softAssert.assertEquals(pages.getAmTxnWidgetPage().getValueCorrespondingToHeader(i + 1, 1), amTransactionHistoryAPI.getResult().getData().get(i).getAmount(), "Amount is not expected as com.airtel.cs.API response.");
                    softAssert.assertEquals(pages.getAmTxnWidgetPage().getValueCorrespondingToHeader(i + 1, 2), amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn(), "Receiver MSISDN is not expected as com.airtel.cs.API response.");
                    softAssert.assertEquals(pages.getAmTxnWidgetPage().getValueCorrespondingToHeader(i + 1, 3), UtilsMethods.getDateFromEpochInUTC(new Long(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate()), config.getProperty("AMHistoryTimeFormat")), "Date is not expected as com.airtel.cs.API response.");
                    softAssert.assertEquals(pages.getAmTxnWidgetPage().getValueCorrespondingToHeader(i + 1, 4), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), "Transaction Id is not expected as com.airtel.cs.API response.");
                    softAssert.assertEquals(pages.getAmTxnWidgetPage().getValueCorrespondingToHeader(i + 1, 5), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), "Status is not expected as com.airtel.cs.API response.");
                    if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                        softAssert.assertTrue(pages.getAmTxnWidgetPage().isResendSMS(), "Resend SMS Icon does not enable as mentioned in com.airtel.cs.API Response.");
                    }
                    String id = pages.getAmTxnWidgetPage().doubleClickOnTransactionId(i + 1);
                    String clipboardText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    commonLib.info("Reading Clipboard copied text: " + clipboardText);
                    softAssert.assertEquals(id, clipboardText, "After double clicking on Transaction id. Transaction id does not copy to clipboard.");
                }
            }
        }
        softAssert.assertAll();
    }

    //Needs Discussion
    @Test(priority = 8, description = "Validating Current Balance Widget", dependsOnMethods = "openCustomerInteractionAPI")
    public void yourCurrentBalanceWidgetTest() {
        selUtils.addTestcaseDescription("Validating Current Balance Transaction Widget of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetVisible(), "Current Balance Widget is not visible ");

        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        if (plansAPI.getResult().getMainAccountBalance() != null) {
            softAssert.assertEquals(Double.parseDouble(pages.getCurrentBalanceWidgetPage().gettingMainAccountBalance()), Double.parseDouble(plansAPI.getResult().getMainAccountBalance().getBalance()), "Current Balance is not as Received in com.airtel.cs.API ");
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().gettingCurrentBalanceCurrency(), plansAPI.getResult().getMainAccountBalance().getCurrency(), "Current Balance Currency is not as Received in com.airtel.cs.API ");
        } else {
            commonLib.warning("Unable to get Main Balance from com.airtel.cs.API");

        }
        if (plansAPI.getResult().getLastRecharge() != null) {
            try {
                softAssert.assertEquals(Integer.parseInt(pages.getCurrentBalanceWidgetPage().gettingLastRechargeAmount()), Integer.parseInt(plansAPI.getResult().getLastRecharge().getAmount()), "Last Recharge is not as Received in com.airtel.cs.API ");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                commonLib.fail("Last Recharge is not in expected format", true);
                softAssert.fail("Last Recharge is not in expected format");
            }
            String Time = UtilsMethods.getDateFromEpochInUTC(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
            String Date = UtilsMethods.getDateFromEpochInUTC(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is not as Received in com.airtel.cs.API");
        } else {
            commonLib.warning("Unable to get Last Recharge Details from com.airtel.cs.API");
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().gettingLastRechargeAmount().replace('-', ' ').trim(), "", "Last Recharge Amount is not as expected");
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().getLastRechargeDateTime(), "- -", "Last Recharge Date & Time is not as expected");
        }

        log.info(plansAPI.getResult().toString());
        if (plansAPI.getResult().getVoice() != null) {
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().getVoiceExpiryDate(), UtilsMethods.getDateFromEpoch(plansAPI.getResult().getVoice().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Voice Expiry Date is not as Received in com.airtel.cs.API ");
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().getVoiceBalance().replace("-", "null"), plansAPI.getResult().getVoice().getBalance(), "Voice Balance is not as Received in com.airtel.cs.API ");
        }
        if (plansAPI.getResult().getData() != null) {
            try {
                double amount = Double.parseDouble(plansAPI.getResult().getData().getBalance().split(" ")[0]);
                if (amount > 0) {
                    String unit = plansAPI.getResult().getData().getBalance().split(" ")[1];
                    if (unit.equalsIgnoreCase("MB") && amount > 1024) {
                        softAssert.fail("MB to GB conversion does not done Correctly. Data Balance" + pages.getCurrentBalanceWidgetPage().getDataBalance());
                    } else {
                        commonLib.pass("MB to GB Conversion Verified. Balance " + pages.getCurrentBalanceWidgetPage().getDataBalance());
                    }
                }
            } catch (NumberFormatException ns) {
                commonLib.info("Not able to fetch amount" + ns.fillInStackTrace());
            }
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().getDataBalance().replace("-", "null"), plansAPI.getResult().getData().getBalance(), "Data Balance is not as Received in com.airtel.cs.API ");
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().getDataExpiryDate(), UtilsMethods.getDateFromEpoch(plansAPI.getResult().getData().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Data Expiry Date is not as Received in com.airtel.cs.API ");
        }
        if (plansAPI.getResult().getSms() != null) {

            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().getSmsExpiryDate(), UtilsMethods.getDateFromEpoch(plansAPI.getResult().getSms().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "SMS Expiry Date is not as Received in com.airtel.cs.API ");
            softAssert.assertEquals(pages.getCurrentBalanceWidgetPage().getSmsBalance().replace("-", "null"), plansAPI.getResult().getSms().getBalance(), "SMS Balance is not as Received in com.airtel.cs.API ");
        }
        if (plansAPI.getStatusCode() != 200) {
            softAssert.fail("com.airtel.cs.API unable to get Last recharge and MAIN Balance ");
        }
        softAssert.assertAll();
    }


    @DataProviders.Table(name = "Usage History")
    @Test(priority = 9, description = "Validating Usage History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void usageHistoryWidgetTest(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating Usage History Widget of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getUsageHistoryWidget().isUsageHistoryWidgetIsVisible(), "Usage History Widget is not visible");
        softAssert.assertTrue(pages.getUsageHistoryWidget().isUsageHistoryDatePickerVisible(), "Usage History Widget's Date Picker is not visible");

        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
        int size = pages.getUsageHistoryWidget().getNumberOfRows();
        if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
            softAssert.assertTrue(pages.getUsageHistoryWidget().isUsageHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(pages.getUsageHistoryWidget().gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            softAssert.assertEquals(pages.getUsageHistoryWidget().getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(pages.getUsageHistoryWidget().getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(pages.getUsageHistoryWidget().getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(pages.getUsageHistoryWidget().getHeaders(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
            softAssert.assertEquals(pages.getUsageHistoryWidget().getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(pages.getUsageHistoryWidget().getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is not As received in com.airtel.cs.API for row number " + i);
                softAssert.assertEquals(pages.getUsageHistoryWidget().getHistoryCharge(i).replaceAll("[^0-9]", "").trim(), usageHistoryAPI.getResult().get(i).getCharges().replaceAll("[^0-9]", ""), "Usage History Charge is not As received in com.airtel.cs.API for row number " + i);
                softAssert.assertEquals(pages.getUsageHistoryWidget().getHistoryDateTime(i), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Usage History Date Time is not As received in com.airtel.cs.API for row number " + i);
                softAssert.assertEquals(pages.getUsageHistoryWidget().getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is not As received in com.airtel.cs.API for row number " + i);
                softAssert.assertEquals(pages.getUsageHistoryWidget().getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is not As received in com.airtel.cs.API for row number " + i);
                if (i != 0) {
                    softAssert.assertTrue(UtilsMethods.isSortOrderDisplay(pages.getUsageHistoryWidget().getHistoryDateTime(i).replace("\n", " "), pages.getUsageHistoryWidget().getHistoryDateTime(i - 1).replace("\n", " "), "EEE dd MMM yyy hh:mm:ss aa"), pages.getUsageHistoryWidget().getHistoryDateTime(i - 1) + "should not display before " + pages.getUsageHistoryWidget().getHistoryDateTime(i));
                }
            }
        }
        if (usageHistoryAPI.getStatusCode() != 200) {
            softAssert.assertTrue(pages.getUsageHistoryWidget().isUsageHistoryErrorVisible(), "API is Giving error But Widget is not showing error Message on com.airtel.cs.API is ");
            softAssert.fail("com.airtel.cs.API is unable to give Usage History ");
        }
        softAssert.assertAll();
    }


    @DataProviders.Table(name = "Recharge History")
    @Test(priority = 10, description = "Validating Recharge History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void rechargeHistoryWidgetTest(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating Recharge History Widget of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getRechargeHistoryWidget().isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible");
        softAssert.assertTrue(pages.getRechargeHistoryWidget().isRechargeHistoryDatePickerVisible(), "Recharge History Widget's Date Picker is not visible");
        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
        if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            softAssert.assertTrue(pages.getRechargeHistoryWidget().isRechargeHistoryErrorVisible(), "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is ");
            softAssert.fail("com.airtel.cs.API is unable to give Recharge History ");
        } else {
            int size = pages.getRechargeHistoryWidget().getNumberOfRows();
            if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get Last Recharge Details from com.airtel.cs.API");
                softAssert.assertTrue(pages.getRechargeHistoryWidget().isRechargeHistoryNoResultFoundVisible(), "Error Message is not Visible");
                softAssert.assertEquals(pages.getRechargeHistoryWidget().gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
            } else {
                softAssert.assertEquals(pages.getRechargeHistoryWidget().getHeaders(1).toLowerCase().trim() + " " + pages.getRechargeHistoryWidget().getSubHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                softAssert.assertEquals(pages.getRechargeHistoryWidget().getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                softAssert.assertEquals(pages.getRechargeHistoryWidget().getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                softAssert.assertEquals(pages.getRechargeHistoryWidget().getHeaders(4).toLowerCase().trim() + pages.getRechargeHistoryWidget().getSubHeaders(4).toLowerCase().trim().replace("|", ""), data.getRow4().toLowerCase().replace("|", "").trim(), "Header Name for Row 4 is not as expected");
                softAssert.assertEquals(pages.getRechargeHistoryWidget().getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(pages.getRechargeHistoryWidget().getRechargeHistoryCharges(i + 1), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(pages.getRechargeHistoryWidget().getRechargeHistoryDateTime(i + 1), UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), config.getProperty("UIRechargeHistoryTimeFormat"), config.getProperty("APIRechargeHistoryTimeFormat")), "Recharge History Date Time is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(pages.getRechargeHistoryWidget().getRechargeHistoryBundleName(i + 1), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(pages.getRechargeHistoryWidget().getRechargeHistoryBenefits(i + 1).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(pages.getRechargeHistoryWidget().getRechargeHistoryStatus(i + 1), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is not As received in com.airtel.cs.API for row number " + i);
                    if (i != 0) {
                        softAssert.assertTrue(UtilsMethods.isSortOrderDisplay(pages.getRechargeHistoryWidget().getRechargeHistoryDateTime(i + 1), pages.getRechargeHistoryWidget().getRechargeHistoryDateTime(i), "dd-MMM-yyy HH:mm"), pages.getRechargeHistoryWidget().getRechargeHistoryDateTime(i + 1) + "should not display before " + pages.getRechargeHistoryWidget().getRechargeHistoryDateTime(i));
                    }
                }

            }
        }
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Service Profile")
    @Test(priority = 11, description = "Verify Service Profile Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void validateServiceProfileWidget(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Verify Service Profile Widget: " + customerNumber, "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(pages.getServiceClassWidget().isServiceClassWidgetDisplay(), "Service Profile Widget does not display correctly.");
        HLRServicePOJO hlrService = api.getServiceProfileWidgetInfo(customerNumber);
        int size = pages.getServiceClassWidget().getNumberOfRows();
        if (Integer.parseInt(hlrService.getStatusCode()) == 200) {
            if (hlrService.getResult().isEmpty() || hlrService.getResult() == null) {
                commonLib.warning("Unable to get Last Service Profile from com.airtel.cs.API");
                softAssert.assertTrue(pages.getServiceClassWidget().isServiceProfileNoResultFoundVisible(), "Error Message is not Visible");
                softAssert.assertEquals(pages.getServiceClassWidget().gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
            } else {
                softAssert.assertEquals(pages.getServiceClassWidget().getHeaders(1).trim().toLowerCase(), data.getRow1().trim().toLowerCase(), "Header Name at Row(1) is not as expected.");
                softAssert.assertEquals(pages.getServiceClassWidget().getHeaders(2).trim().toLowerCase(), data.getRow2().trim().toLowerCase(), "Header Name at Row(2) is not as expected.");
                softAssert.assertEquals(pages.getServiceClassWidget().getHeaders(3).trim().toLowerCase(), data.getRow3().trim().toLowerCase(), "Header Name at Row(3) is not as expected.");
                softAssert.assertEquals(pages.getServiceClassWidget().getHeaders(4).trim().toLowerCase(), data.getRow4().trim().toLowerCase(), "Header Name at Row(4) is not as expected.");
                softAssert.assertEquals(pages.getServiceClassWidget().getHeaders(5).trim().toLowerCase(), data.getRow5().trim().toLowerCase(), "Header Name at Row(5) is not as expected.");
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(pages.getServiceClassWidget().getValueCorrespondingToAccumulator(i + 1, 1), hlrService.getResult().get(i).getServiceName(), "Service Name is not As received in API for row number " + i);
                    softAssert.assertEquals(pages.getServiceClassWidget().getValueCorrespondingToAccumulator(i + 1, 2), hlrService.getResult().get(i).getServiceDesc(), "Service desc is not As received in API for row number " + i);
                    softAssert.assertEquals(pages.getServiceClassWidget().getValueCorrespondingToAccumulator(i + 1, 3), hlrService.getResult().get(i).getHlrCodes().get(0), "HLR Code is not As received in API for row number " + i);
                    softAssert.assertEquals(pages.getServiceClassWidget().getValueCorrespondingToAccumulator(i + 1, 4), hlrService.getResult().get(i).getHlrCodeDetails().get(0), "HLR code details is not As received in API for row number " + i);
                    if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                        if (hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                            softAssert.assertTrue(pages.getServiceClassWidget().getServiceStatus(), "Service Status is not as expected.");
                        } else {
                            softAssert.assertFalse(pages.getServiceClassWidget().getServiceStatus(), "Service Status is not as expected.");
                        }
                    }
                }
            }
        } else {
            softAssert.assertTrue(pages.getServiceClassWidget().isServiceProfileErrorVisible(), "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is ");
            softAssert.fail("com.airtel.cs.API is unable to fetch Service Profile History ");
        }
        softAssert.assertAll();
    }
}
