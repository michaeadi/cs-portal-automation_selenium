package tests.frontendagent;


import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.pojo.*;
import com.airtel.cs.pojo.AirtelMoney.AirtelMoneyPOJO;
import com.airtel.cs.pojo.HLRService.HLRServicePOJO;
import com.airtel.cs.pojo.KYCProfile.KYCProfile;
import com.airtel.cs.commonutils.dataproviders.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pagemethods.AMTransactionsWidgetPOM;
import com.airtel.cs.pagerepository.pagemethods.AuthenticationTabPOM;
import com.airtel.cs.pagerepository.pagemethods.CurrentBalanceWidgetPOM;
import com.airtel.cs.pagerepository.pagemethods.CustomerDemoGraphicPOM;
import com.airtel.cs.pagerepository.pagemethods.RechargeHistoryWidgetPOM;
import com.airtel.cs.pagerepository.pagemethods.ServiceClassWidgetPOM;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.UsageHistoryWidgetPOM;
import com.airtel.cs.pagerepository.pagemethods.customerInteractionPagePOM;
import com.airtel.cs.pagerepository.pagemethods.customerInteractionsSearchPOM;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import static com.airtel.cs.commonutils.dataproviders.DataProviders.User;

public class customerInteractionTest extends BaseTest {

    static String customerNumber;
    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA | !continueExecutionAPI){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @User()
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        SoftAssert softAssert = new SoftAssert();
        if (continueExecutionAPI) {
            ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
            SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
            if (Env.equalsIgnoreCase("Prod")) {
                customerNumber = Data.getProdCustomerNumber();
            } else {
                customerNumber = Data.getCustomerNumber();
            }
            SideMenuPOM.clickOnSideMenu();
            SideMenuPOM.clickOnName();
            customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
            customerInteractionsSearchPOM.enterNumber(customerNumber);
            customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
            if (!customerInteractionPagePOM.isPageLoaded()) {
                softAssert.fail("Customer Info Dashboard Page does not open using SIM Number.");
                customerInteractionsSearchPOM.clearCustomerNumber();
            }

        } else {
            softAssert.fail("Execution Terminate due to either agent logout or User Password Update");
        }
        softAssert.assertAll();
    }


    @User()
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateDemographicInformation(TestDatabean Data) {
        SoftAssert softAssert = new SoftAssert();
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        CustomerDemoGraphicPOM demographic = new CustomerDemoGraphicPOM(driver);

        ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
        KYCProfile kycProfile = api.KYCProfileAPITest(customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(customerNumber);
        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(customerNumber);

        try {
            if (demographic.isPUKInfoLock()) {
                demographic.clickPUKToUnlock();
                Thread.sleep(5000);
                AuthenticationTabPOM authTab = new AuthenticationTabPOM(driver);
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
                    ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
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
            if (demographic.isAirtelMoneyStatusLock()) {
                demographic.clickAirtelStatusToUnlock();
                AuthenticationTabPOM authTab = new AuthenticationTabPOM(driver);
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
            if(UtilsMethods.isCustomerBirthday(demographic.getCustomerDOB().trim(),"dd-MMM-yyyy")){
                UtilsMethods.printPassLog("Today is Customer Birthday");
                softAssert.assertTrue(demographic.isBirthday(),"Today is customer birthday but does not display birthday icon.");
            }else{
                softAssert.assertFalse(demographic.isBirthday(),"Today is not customer birthday but birthday icon display.");
            }
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

        try {
            if (demographic.getDataManagerStatus().equalsIgnoreCase("true")) {
                softAssert.assertEquals("on", plansAPI.getResult().getDataManager().toLowerCase().trim(), "Customer's Data Manager Status is not as Expected");
            } else {
                softAssert.assertEquals("off", plansAPI.getResult().getDataManager().toLowerCase().trim(), "Customer's Data Manager Status is not as Expected");
            }

        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Data Manager Status is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getAirtelMoneyStatus().toLowerCase().trim(), profileAPI.getResult().getAirtelMoneyStatus().toLowerCase().trim(), "Customer's Airtel Money Status is not as Expected");

        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Airtel Money Status is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getServiceStatus().toLowerCase().trim(), profileAPI.getResult().getServiceStatus().toLowerCase().trim(), "Customer's Airtel Money Service Status is not as Expected");

        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Airtel Money Service Status is not visible", e.getCause());
            e.printStackTrace();
        }

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

        try {
            softAssert.assertEquals(demographic.getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Device Type is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(demographic.getSimNumber().trim(), kycProfile.getResult().getSim(), "Customer's SIM Number is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's SIM Number is not visible", e.getCause());
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

    @User()
    @Test(priority = 3, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionBySIM(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions By Using SIM Number :" + Data.getSimNumber(), "Validating the Search for Customer Interactions By Using SIM Number : " + Data.getSimNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        if (Env.equalsIgnoreCase("Prod")) {
            customerInteractionsSearchPOM.enterNumber(Data.getProdSIMNumber());
            customerNumber = Data.getProdCustomerNumber();
        } else {
            customerInteractionsSearchPOM.enterNumber(Data.getSimNumber());
            customerNumber = Data.getCustomerNumber();
        }
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        if (!customerInteractionPagePOM.isPageLoaded()) {
            softAssert.fail("Customer Info Dashboard Page does not open using SIM Number.");
            String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);
            ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
            customerInteractionsSearchPOM.clearCustomerNumber();
        }
        softAssert.assertAll();
    }

    @User()
    @Test(priority = 4, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionBySIM")
    public void validateDemographicInformationBySIMNumber(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        CustomerDemoGraphicPOM demographic = new CustomerDemoGraphicPOM(driver);
        SoftAssert softAssert = new SoftAssert();

        ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
        KYCProfile kycProfile = api.KYCProfileAPITest(customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(customerNumber);
        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(customerNumber);

        try {
            if (demographic.isPUKInfoLock()) {
                demographic.clickPUKToUnlock();
                Thread.sleep(5000);
                AuthenticationTabPOM authTab = new AuthenticationTabPOM(driver);
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
                    ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
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
            if (demographic.isAirtelMoneyStatusLock()) {
                demographic.clickAirtelStatusToUnlock();
                Thread.sleep(5000);
                AuthenticationTabPOM authTab = new AuthenticationTabPOM(driver);
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

        try {
            softAssert.assertEquals(demographic.getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Device Type is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getSimNumber().trim(), Data.getCustomerNumber(), "Customer's Mobile Number is not as Expected");
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
        ExtentTestManager.startTest("Validating the Demographic Information of User with invalid MSISDN : 123456789", "Validating the Demographic Information of User : 123456789");
        CustomerDemoGraphicPOM demographic = new CustomerDemoGraphicPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        demographic.clearSearchBox(customerNumber.length());
        demographic.enterMSISDN("123456789"); //Entering Invalid MSISDN
        demographic.waitTillLoaderGetsRemoved();
        try {
            softAssert.assertTrue(demographic.invalidMSISDNError(), "Invalid MSISDN error message does not display");
        } catch (TimeoutException | NoSuchElementException e) {
            softAssert.fail("Invalid Error Message does not display " + e.getMessage());
        }
        softAssert.assertAll();
    }

    @User(userType = "com/airtel/cs/api")
    @Test(priority = 6, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionAPI(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions for Widget :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        if (Env.equalsIgnoreCase("Prod")) {
            customerNumber = Data.getProdCustomerNumber();
        } else {
            customerNumber = Data.getCustomerNumber();
        }
        customerInteractionsSearchPOM.enterNumber(customerNumber);
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        if (!customerInteractionPagePOM.isPageLoaded()) {
            //Take base64Screenshot screenshot.
            String base64Screenshot1 = "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);

            //ExtentReports log and screenshot operations for failed tests.
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot1));
            softAssert.fail("Customer Info Dashboard Page does not open using MSISDN Number.");
            customerInteractionsSearchPOM.clearCustomerNumber();
        }
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Airtel Money")
    @Test(priority = 7, description = "Validating AM Transaction Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, enabled = true, dependsOnMethods = "openCustomerInteractionAPI")
    public void airtelMoneyTransactionWidgetTest(HeaderDataBean Data) throws IOException, UnsupportedFlavorException {
        ExtentTestManager.startTest("Validating AM Transaction Widget", "Validating AM Transaction Widget of User :" + customerNumber);
        AMTransactionsWidgetPOM amTransactionsWidget = new AMTransactionsWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyTransactionWidgetIsVisible(), "Airtel Money Transaction Widget is not visible");
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyWidgetDatePickerVisible(), "Airtel Money Transaction Widget's Date Picker is not visible");
        AirtelMoneyPOJO amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
        if (amTransactionHistoryAPI.getStatusCode() != 200) {
            softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyErrorVisible(), "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is " + amTransactionHistoryAPI.getMessage());
            softAssert.fail("com.airtel.cs.API is Unable to Get AM Transaction History for Customer");
        } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
            softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyNoResultFoundVisible(), "No Result Found Icon does not display on UI.");
        } else {
            int count = amTransactionHistoryAPI.getResult().getTotalCount();
            if (count > 0) {
                if (count > 5) {
                    count = 5;
                }
                softAssert.assertEquals(amTransactionsWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                softAssert.assertEquals(amTransactionsWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                softAssert.assertEquals(amTransactionsWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                softAssert.assertEquals(amTransactionsWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
                softAssert.assertEquals(amTransactionsWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                softAssert.assertTrue(amTransactionsWidget.isTransactionId(), "Transaction Id Search Box does not displayed on UI");
                for (int i = 0; i < count; i++) {
                    softAssert.assertEquals(amTransactionsWidget.getValueCorrespondingToHeader(i + 1, 1), amTransactionHistoryAPI.getResult().getData().get(i).getAmount(), "Amount is not expected as com.airtel.cs.API response.");
                    softAssert.assertEquals(amTransactionsWidget.getValueCorrespondingToHeader(i + 1, 2), amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn(), "Receiver MSISDN is not expected as com.airtel.cs.API response.");
                    softAssert.assertEquals(amTransactionsWidget.getValueCorrespondingToHeader(i + 1, 3), UtilsMethods.getDateFromEpochInUTC(new Long(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate()), config.getProperty("AMHistoryTimeFormat")), "Date is not expected as com.airtel.cs.API response.");
                    softAssert.assertEquals(amTransactionsWidget.getValueCorrespondingToHeader(i + 1, 4), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), "Transaction Id is not expected as com.airtel.cs.API response.");
                    softAssert.assertEquals(amTransactionsWidget.getValueCorrespondingToHeader(i + 1, 5), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), "Status is not expected as com.airtel.cs.API response.");
                    if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                        softAssert.assertTrue(amTransactionsWidget.isResendSMS(), "Resend SMS Icon does not enable as mentioned in com.airtel.cs.API Response.");
                    }
                    String id=amTransactionsWidget.doubleClickOnTransactionId(i+1);
                    String clipboardText=(String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    UtilsMethods.printInfoLog("Reading Clipboard copied text: "+clipboardText);
                    softAssert.assertEquals(id,clipboardText,"After double clicking on Transaction id. Transaction id does not copy to clipboard.");
                }
            }
        }
        softAssert.assertAll();
    }

    //Needs Discussion
    @Test(priority = 8, description = "Validating Current Balance Widget",dependsOnMethods = "openCustomerInteractionAPI")
    public void yourCurrentBalanceWidgetTest() {
        ExtentTestManager.startTest("Validating Current Balance Transaction Widget", "Validating Current Balance Transaction Widget of User :" + customerNumber);
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetVisible(), "Current Balance Widget is not visible ");

        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        if (plansAPI.getResult().getMainAccountBalance() != null) {
            softAssert.assertEquals(Double.parseDouble(currentBalanceWidget.gettingMainAccountBalance()), Double.parseDouble(plansAPI.getResult().getMainAccountBalance().getBalance()), "Current Balance is not as Received in com.airtel.cs.API ");
            softAssert.assertEquals(currentBalanceWidget.gettingCurrentBalanceCurrency(), plansAPI.getResult().getMainAccountBalance().getCurrency(), "Current Balance Currency is not as Received in com.airtel.cs.API ");
        } else {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Main Balance from com.airtel.cs.API");

        }
        if (plansAPI.getResult().getLastRecharge() != null) {
            try {
                softAssert.assertEquals(Integer.parseInt(currentBalanceWidget.gettingLastRechargeAmount()), Integer.parseInt(plansAPI.getResult().getLastRecharge().getAmount()), "Last Recharge is not as Received in com.airtel.cs.API ");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                ExtentTestManager.getTest().log(LogStatus.FAIL, e.fillInStackTrace());
                softAssert.fail("Last Recharge is not in expected format");
            }
            String Time = UtilsMethods.getDateFromEpochInUTC(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
            String Date = UtilsMethods.getDateFromEpochInUTC(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
            softAssert.assertEquals(currentBalanceWidget.getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is not as Received in com.airtel.cs.API");
        } else {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from com.airtel.cs.API");
            softAssert.assertEquals(currentBalanceWidget.gettingLastRechargeAmount().replace('-', ' ').trim(), "", "Last Recharge Amount is not as expected");
            softAssert.assertEquals(currentBalanceWidget.getLastRechargeDateTime(), "- -", "Last Recharge Date & Time is not as expected");
        }

        System.out.println(plansAPI.getResult().toString());
        if (plansAPI.getResult().getVoice() != null) {
            softAssert.assertEquals(currentBalanceWidget.getVoiceExpiryDate(), UtilsMethods.getDateFromEpoch(plansAPI.getResult().getVoice().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Voice Expiry Date is not as Received in com.airtel.cs.API ");
            softAssert.assertEquals(currentBalanceWidget.getVoiceBalance().replace("-", "null"), plansAPI.getResult().getVoice().getBalance(), "Voice Balance is not as Received in com.airtel.cs.API ");
        }
        if (plansAPI.getResult().getData() != null) {

            softAssert.assertEquals(currentBalanceWidget.getDataBalance().replace("-", "null"), plansAPI.getResult().getData().getBalance(), "Data Balance is not as Received in com.airtel.cs.API ");
            softAssert.assertEquals(currentBalanceWidget.getDataExpiryDate(), UtilsMethods.getDateFromEpoch(plansAPI.getResult().getData().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Data Expiry Date is not as Received in com.airtel.cs.API ");
        }
        if (plansAPI.getResult().getSms() != null) {

            softAssert.assertEquals(currentBalanceWidget.getSmsExpiryDate(), UtilsMethods.getDateFromEpoch(plansAPI.getResult().getSms().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "SMS Expiry Date is not as Received in com.airtel.cs.API ");
            softAssert.assertEquals(currentBalanceWidget.getSmsBalance().replace("-", "null"), plansAPI.getResult().getSms().getBalance(), "SMS Balance is not as Received in com.airtel.cs.API ");
        }
        if (plansAPI.getStatusCode() != 200) {
            softAssert.fail("com.airtel.cs.API unable to get Last recharge and MAIN Balance ");
        }
        softAssert.assertAll();
    }


    @DataProviders.Table(name = "Usage History")
    @Test(priority = 9, description = "Validating Usage History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void usageHistoryWidgetTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Usage History Widget", "Validating Usage History Widget of User :" + customerNumber);
        UsageHistoryWidgetPOM usageHistoryWidget = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryWidgetIsVisible(), "Usage History Widget is not visible");
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryDatePickerVisible(), "Usage History Widget's Date Picker is not visible");

        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
        int size = usageHistoryWidget.getNumberOfRows();
        if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Usage History Details from com.airtel.cs.API");
            softAssert.assertTrue(usageHistoryWidget.isUsageHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(usageHistoryWidget.gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            softAssert.assertEquals(usageHistoryWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(usageHistoryWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(usageHistoryWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(usageHistoryWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
            softAssert.assertEquals(usageHistoryWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is not As received in com.airtel.cs.API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryCharge(i).replaceAll("[^0-9]", "").trim(), usageHistoryAPI.getResult().get(i).getCharges().replaceAll("[^0-9]", ""), "Usage History Charge is not As received in com.airtel.cs.API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryDateTime(i), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Usage History Date Time is not As received in com.airtel.cs.API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is not As received in com.airtel.cs.API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is not As received in com.airtel.cs.API for row number " + i);
                if (i != 0) {
                    softAssert.assertTrue(UtilsMethods.isSortOrderDisplay(usageHistoryWidget.getHistoryDateTime(i).replace("\n", " "), usageHistoryWidget.getHistoryDateTime(i - 1).replace("\n", " "), "EEE dd MMM yyy hh:mm:ss aa"), usageHistoryWidget.getHistoryDateTime(i - 1) + "should not display before " + usageHistoryWidget.getHistoryDateTime(i));
                }
            }
        }
        if (usageHistoryAPI.getStatusCode() != 200) {
            softAssert.assertTrue(usageHistoryWidget.isUsageHistoryErrorVisible(), "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is ");
            softAssert.fail("com.airtel.cs.API is unable to give Usage History ");
        }
        softAssert.assertAll();
    }


    @DataProviders.Table(name = "Recharge History")
    @Test(priority = 10, description = "Validating Recharge History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void rechargeHistoryWidgetTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Recharge History Widget", "Validating Recharge History Widget of User :" + customerNumber);
        RechargeHistoryWidgetPOM rechargeHistoryWidget = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible");
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), "Recharge History Widget's Date Picker is not visible");
        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
        if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryErrorVisible(), "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is ");
            softAssert.fail("com.airtel.cs.API is unable to give Recharge History ");
        } else {
            int size = rechargeHistoryWidget.getNumberOfRows();
            if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
                ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from com.airtel.cs.API");
                softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryNoResultFoundVisible(), "Error Message is not Visible");
                softAssert.assertEquals(rechargeHistoryWidget.gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
            } else {
                softAssert.assertEquals(rechargeHistoryWidget.getHeaders(1).toLowerCase().trim() + " " + rechargeHistoryWidget.getSubHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                softAssert.assertEquals(rechargeHistoryWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                softAssert.assertEquals(rechargeHistoryWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                softAssert.assertEquals(rechargeHistoryWidget.getHeaders(4).toLowerCase().trim() + rechargeHistoryWidget.getSubHeaders(4).toLowerCase().trim().replace("|", ""), Data.getRow4().toLowerCase().replace("|", "").trim(), "Header Name for Row 4 is not as expected");
                softAssert.assertEquals(rechargeHistoryWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryCharges(i + 1), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1), UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), config.getProperty("UIRechargeHistoryTimeFormat"), config.getProperty("APIRechargeHistoryTimeFormat")), "Recharge History Date Time is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBundleName(i + 1), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBenefits(i + 1).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryStatus(i + 1), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is not As received in com.airtel.cs.API for row number " + i);
                    if (i != 0) {
                        softAssert.assertTrue(UtilsMethods.isSortOrderDisplay(rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1), rechargeHistoryWidget.getRechargeHistoryDateTime(i), "dd-MMM-yyy HH:mm"), rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1) + "should not display before " + rechargeHistoryWidget.getRechargeHistoryDateTime(i));
                    }
                }

            }
        }
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Service Profile")
    @Test(priority = 11, description = "Verify Service Profile Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, enabled = true, dependsOnMethods = "openCustomerInteractionAPI")
    public void validateServiceProfileWidget(HeaderDataBean Data) {
        ExtentTestManager.startTest("Verify Service Profile Widget: " + customerNumber, "Verify Service Profile Widget: " + customerNumber);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        ServiceClassWidgetPOM hlrWidget = new ServiceClassWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(hlrWidget.isServiceClassWidgetDisplay(), "Service Profile Widget does not display correctly.");
        HLRServicePOJO hlrService = api.getServiceProfileWidgetInfo(customerNumber);
        int size = hlrWidget.getNumberOfRows();
        if (Integer.parseInt(hlrService.getStatusCode()) == 200) {
            if (hlrService.getResult().size() == 0 || hlrService.getResult() == null) {
                UtilsMethods.printWarningLog("Unable to get Last Service Profile from com.airtel.cs.API");
                softAssert.assertTrue(hlrWidget.isServiceProfileNoResultFoundVisible(), "Error Message is not Visible");
                softAssert.assertEquals(hlrWidget.gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
            } else {
                softAssert.assertEquals(hlrWidget.getHeaders(1).trim().toLowerCase(), Data.getRow1().trim().toLowerCase(), "Header Name at Row(1) is not as expected.");
                softAssert.assertEquals(hlrWidget.getHeaders(2).trim().toLowerCase(), Data.getRow2().trim().toLowerCase(), "Header Name at Row(2) is not as expected.");
                softAssert.assertEquals(hlrWidget.getHeaders(3).trim().toLowerCase(), Data.getRow3().trim().toLowerCase(), "Header Name at Row(3) is not as expected.");
                softAssert.assertEquals(hlrWidget.getHeaders(4).trim().toLowerCase(), Data.getRow4().trim().toLowerCase(), "Header Name at Row(4) is not as expected.");
                softAssert.assertEquals(hlrWidget.getHeaders(5).trim().toLowerCase(), Data.getRow5().trim().toLowerCase(), "Header Name at Row(5) is not as expected.");
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(hlrWidget.getValueCorrespondingToAccumulator(i + 1, 1), hlrService.getResult().get(i).getServiceName(), "Service Name is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(hlrWidget.getValueCorrespondingToAccumulator(i + 1, 2), hlrService.getResult().get(i).getServiceDesc(), "Service desc is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(hlrWidget.getValueCorrespondingToAccumulator(i + 1, 3), hlrService.getResult().get(i).getHlrCodes().get(0), "HLR Code is not As received in com.airtel.cs.API for row number " + i);
                    softAssert.assertEquals(hlrWidget.getValueCorrespondingToAccumulator(i + 1, 4), hlrService.getResult().get(i).getHlrCodeDetails().get(0), "HLR code details is not As received in com.airtel.cs.API for row number " + i);
                    if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                        if (hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                            softAssert.assertTrue(hlrWidget.getServiceStatus(), "Service Status is not as expected.");
                        } else {
                            softAssert.assertFalse(hlrWidget.getServiceStatus(), "Service Status is not as expected.");
                        }
                    }
                }
            }
        } else {
            softAssert.assertTrue(hlrWidget.isServiceProfileErrorVisible(), "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is ");
            softAssert.fail("com.airtel.cs.API is unable to fetch Service Profile History ");
        }
        softAssert.assertAll();
    }
}
