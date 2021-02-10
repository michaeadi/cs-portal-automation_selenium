package tests;


import API.APIEndPoints;
import POJO.*;
import POJO.KYCProfile.KYCProfile;
import Utils.DataProviders.AuthTabDataBeans;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.HeaderDataBean;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.util.List;

import static Utils.DataProviders.DataProviders.Table;
import static Utils.DataProviders.DataProviders.User;

public class customerInteractionTest extends BaseTest {

    static String customerNumber;
    APIEndPoints api = new APIEndPoints();

    @User()
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerNumber = Data.getCustomerNumber();
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }


    @User()
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class,dependsOnMethods = "openCustomerInteraction")
    public void validateDemographicInformation(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        CustomerDemoGraphicPOM demographic = new CustomerDemoGraphicPOM(driver);
        SoftAssert softAssert = new SoftAssert();

        ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
        KYCProfile kycProfile = api.KYCProfileAPITest(customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(customerNumber);
        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);

        try {
            if (demographic.isPUKInfoLock()) {
                demographic.clickPUKToUnlock();
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
            softAssert.assertEquals(demographic.getCustomerDOB().trim(), demographic.getDateFromEpoch(gsmKycAPI.getResult().getDob(), "dd-MMM-yyyy"), "Customer DOB is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer DOB is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(demographic.getActivationDate().trim(), demographic.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "dd MMMM yyyy"), "Customer's Activation Date is not as Expected");

        } catch (NoSuchElementException | TimeoutException | NumberFormatException e) {
            softAssert.fail("Customer's Activation Date is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getSIMStatus().toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is not as Expected");
            demographic.hoverOnSIMStatusInfoIcon();
            softAssert.assertEquals(demographic.getSIMStatusReasonCode().trim().toLowerCase(),kycProfile.getResult().getReason()==null || kycProfile.getResult().getReason()=="" ?"-":kycProfile.getResult().getReason().toLowerCase().trim(),"Customer SIM Status Reason is not as Expected");
            softAssert.assertEquals(demographic.getSIMStatusModifiedBy().trim().toLowerCase(),kycProfile.getResult().getModifiedBy().trim().toLowerCase(),"Customer SIM Status Modified By is not as Expected");
            softAssert.assertEquals(demographic.getSIMStatusModifiedDate().trim(),demographic.getDateFromString(kycProfile.getResult().getModifiedDate(),"dd-MMM-yyy HH:mm"),"Customer SIM Status Modified Date is not as Expected");
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
            softAssert.assertEquals(demographic.getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Device Type is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getActivationTime().trim(), demographic.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "hh: mm aa"), "Customer's Activation Time is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Activation Time is not visible", e.getCause());
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
            softAssert.assertEquals(demographic.getIdType().trim(), gsmKycAPI.getResult().getIdentificationType(), "Customer's ID Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException |NullPointerException e) {
            softAssert.fail("Customer's Id Type is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertTrue(gsmKycAPI.getResult().getIdentificationNo().contains(demographic.getIdNumber().replace("*", "")), "Customer's ID Number is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Id Number is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getLineType().isEmpty()) {
                softAssert.assertEquals(demographic.getLineType().trim(), "-", "Customer Line Type as not expected");
            } else {
                softAssert.assertEquals(demographic.getLineType().toLowerCase().trim(), kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Line Type as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Line Type is not visible", e.getCause());
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
        customerInteractionsSearchPOM.enterNumber(Data.getSimNumber());
        customerNumber = Data.getCustomerNumber();
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        if(customerInteractionPagePOM.isPageLoaded())
        softAssert.assertAll();
    }

    @User()
    @Test(priority = 4, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class,dependsOnMethods = "openCustomerInteraction")
    public void validateDemographicInformationBySIMNumber(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        CustomerDemoGraphicPOM demographic = new CustomerDemoGraphicPOM(driver);
        SoftAssert softAssert = new SoftAssert();

        ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
        KYCProfile kycProfile = api.KYCProfileAPITest(customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(customerNumber);
        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);

        try {
            if (demographic.isPUKInfoLock()) {
                demographic.clickPUKToUnlock();
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
            softAssert.assertEquals(demographic.getCustomerDOB().trim(), demographic.getDateFromEpoch(gsmKycAPI.getResult().getDob(), "dd-MMM-yyyy"), "Customer DOB is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer DOB is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(demographic.getActivationDate().trim(), demographic.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "dd MMMM yyyy"), "Customer's Activation Date is not as Expected");

        } catch (NoSuchElementException | TimeoutException | NumberFormatException e) {
            softAssert.fail("Customer's Activation Date is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getSIMStatus().toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is not as Expected");
            demographic.hoverOnSIMStatusInfoIcon();
            softAssert.assertEquals(demographic.getSIMStatusReasonCode().trim().toLowerCase(),kycProfile.getResult().getReason()==null || kycProfile.getResult().getReason()=="" ?"-":kycProfile.getResult().getReason().toLowerCase().trim(),"Customer SIM Status Reason is not as Expected");
            softAssert.assertEquals(demographic.getSIMStatusModifiedBy().trim().toLowerCase(),kycProfile.getResult().getModifiedBy().trim().toLowerCase(),"Customer SIM Status Modified By is not as Expected");
            softAssert.assertEquals(demographic.getSIMStatusModifiedDate().trim(),demographic.getDateFromString(kycProfile.getResult().getModifiedDate(),"dd-MMM-yyy HH:mm"),"Customer SIM Status Modified Date is not as Expected");
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
            softAssert.assertEquals(demographic.getDeviceCompatible(), profileAPI.getResult().getDeviceType(), "Customer's Device Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Device Type is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            softAssert.assertEquals(demographic.getActivationTime().trim(), demographic.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "hh: mm aa"), "Customer's Activation Time is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Activation Time is not visible", e.getCause());
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
            softAssert.assertEquals(demographic.getIdType().trim(), gsmKycAPI.getResult().getIdentificationType(), "Customer's ID Type is not as Expected");
        } catch (NoSuchElementException | TimeoutException |NullPointerException e) {
            softAssert.fail("Customer's Id Type is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertTrue(gsmKycAPI.getResult().getIdentificationNo().contains(demographic.getIdNumber().replace("*", "")), "Customer's ID Number is not as Expected");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Customer's Id Number is not visible", e.getCause());
            e.printStackTrace();
        }

        try {
            if (kycProfile.getResult().getLineType().isEmpty()) {
                softAssert.assertEquals(demographic.getLineType().trim(), "-", "Customer Line Type as not expected");
            } else {
                softAssert.assertEquals(demographic.getLineType().toLowerCase().trim(), kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Line Type as not expected");
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Customer's Line Type is not visible", e.getCause());
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

    @Test(priority = 5, description = "As an agent I want capability to check if an MSISDN is valid or invalid")
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

    @User(UserType = "API")
    @Test(priority = 6, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionAPI(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerNumber = Data.getCustomerNumber();
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        customerInteractionPagePOM.waitTillTimeLineGetsRemoved();
        softAssert.assertAll();
    }

    @Table(Name = "Airtel Money")
    @Test(priority = 7, description = "Validating AM Transaction Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, enabled = false)
    public void airtelMoneyTransactionWidgetTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating AM Transaction Widget", "Validating AM Transaction Widget of User :" + customerNumber);
        AMTransactionsWidgetPOM amTransactionsWidget = new AMTransactionsWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyTransactionWidgetIsVisible(), "Airtel Money Transaction Widget is not visible");
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyWidgetDatePickerVisible(), "Airtel Money Transaction Widget's Date Picker is not visible");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");

        AMProfilePOJO amServiceProfileAPI = api.amServiceProfileAPITest(customerNumber);
        AMTransactionHistoryPOJO amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
        if (amServiceProfileAPI.getResult() != null) {
            softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyBalance(), Double.parseDouble(amServiceProfileAPI.getResult().getWallet().get(0).getBalance())
                    , "Customer's Airtel Money Balance is not as Expected");
            softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyCurrency(), amServiceProfileAPI.getResult().getWallet().get(0).getCurrency(),
                    "Customer's Airtel Money Balance Currency  is not as Expected");
        } else if (amServiceProfileAPI.getStatusCode() != 200) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get AM Balance from API");
            softAssert.assertTrue(amTransactionsWidget.isAMBalanceUnableToFetch(), "No error Message on Unable to get AM Balance For User From API");
            softAssert.assertEquals(amTransactionsWidget.gettingAMBalanceUnableToFetchMessage(), "Unable to Fetch Data", "Error Message is not as Expected");
            softAssert.fail("API is Unable to Get AM Balance for Customer");
        }
        if (amTransactionHistoryAPI.getStatusCode() != 200) {
            softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyErrorVisible(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage());
            softAssert.fail("API is Unable to Get AM Transaction History for Customer");
        }
        softAssert.assertAll();
    }

    //Needs Discussion
    @Test(priority = 8, description = "Validating Current Balance Widget")
    public void yourCurrentBalanceWidgetTest() {
        ExtentTestManager.startTest("Validating Current Balance Transaction Widget", "Validating Current Balance Transaction Widget of User :" + customerNumber);
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetVisible(), "Current Balance Widget is not visible ");

        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        if (plansAPI.getResult().getMainAccountBalance() != null) {
            softAssert.assertEquals(Double.parseDouble(currentBalanceWidget.gettingMainAccountBalance()), Double.parseDouble(plansAPI.getResult().getMainAccountBalance().getBalance()), "Current Balance is not as Received in API ");
            softAssert.assertEquals(currentBalanceWidget.gettingCurrentBalanceCurrency(), plansAPI.getResult().getMainAccountBalance().getCurrency(), "Current Balance Currency is not as Received in API ");
        } else {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Main Balance from API");

        }
        if (plansAPI.getResult().getLastRecharge() != null) {
            try {
                softAssert.assertEquals(Integer.parseInt(currentBalanceWidget.gettingLastRechargeAmount()), Integer.parseInt(plansAPI.getResult().getLastRecharge().getAmount()), "Last Recharge is not as Received in API ");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                ExtentTestManager.getTest().log(LogStatus.FAIL, e.fillInStackTrace());
                softAssert.fail("Last Recharge is not in expected format");
            }
            String Time = currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
            String Date = currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
            softAssert.assertEquals(currentBalanceWidget.getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is not as Received in API");
        } else {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from API");
            softAssert.assertTrue(currentBalanceWidget.isLastRechargeUnableToFetchVisible(), "Error Message is not Visible");
            softAssert.assertTrue(currentBalanceWidget.isLastRechargeDateTImeUnableTOFetch(), "Error Message is not Visible");
            softAssert.assertEquals(currentBalanceWidget.gettingLastRechargeUnableToFetchVisible(), "Unable to Fetch Data", "Error Message is not as Expected");
            softAssert.assertEquals(currentBalanceWidget.gettingLastRechargeDateTImeUnableTOFetch(), "Unable to Fetch Data", "Error Message is not as Expected");

        }

        System.out.println(plansAPI.getResult().toString());
        if (plansAPI.getResult().getVoice() != null) {
            softAssert.assertEquals(currentBalanceWidget.getVoiceExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getVoice().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Voice Expiry Date is not as Received in API ");
            softAssert.assertEquals(currentBalanceWidget.getVoiceBalance().replace("-", "null"), plansAPI.getResult().getVoice().getBalance(), "Voice Balance is not as Received in API ");
        }
        if (plansAPI.getResult().getData() != null) {

            softAssert.assertEquals(currentBalanceWidget.getDataBalance().replace("-", "null"), plansAPI.getResult().getData().getBalance(), "Data Balance is not as Received in API ");
            softAssert.assertEquals(currentBalanceWidget.getDataExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getData().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Data Expiry Date is not as Received in API ");
        }
        if (plansAPI.getResult().getSms() != null) {

            softAssert.assertEquals(currentBalanceWidget.getSmsExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getSms().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "SMS Expiry Date is not as Received in API ");
            softAssert.assertEquals(currentBalanceWidget.getSmsBalance().replace("-", "null"), plansAPI.getResult().getSms().getBalance(), "SMS Balance is not as Received in API ");
        }
        if (plansAPI.getStatusCode() != 200) {
            softAssert.fail("API unable to get Last recharge and MAIN Balance ");
        }
        softAssert.assertAll();
    }


    @Table(Name = "Usage History")
    @Test(priority = 9, description = "Validating Usage History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void usageHistoryWidgetTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Usage History Widget", "Validating Usage History Widget of User :" + customerNumber);
        UsageHistoryWidgetPOM usageHistoryWidget = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryWidgetIsVisible(), "Usage History Widget is not visible");
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryDatePickerVisible(), "Usage History Widget's Date Picker is not visible");

        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
        int size = usageHistoryWidget.getNumberOfRows();
        if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Usage History Details from API");
            softAssert.assertTrue(usageHistoryWidget.isUsageHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(usageHistoryWidget.gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            softAssert.assertEquals(usageHistoryWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(usageHistoryWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(usageHistoryWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(usageHistoryWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
            softAssert.assertEquals(usageHistoryWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is not As received in API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryCharge(i), usageHistoryAPI.getResult().get(i).getCharges(), "Usage History Charge is not As received in API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getDateFromString(usageHistoryAPI.getResult().get(i).getDateTime(), "dd-MMM-yyy HH:mm"), "Usage History Date Time is not As received in API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is not As received in API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is not As received in API for row number " + i);
                if (i != 0) {
                    softAssert.assertTrue(usageHistoryWidget.isSortOrderDisplay(usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getHistoryDateTime(i-1), "dd-MMM-yyy HH:mm"), usageHistoryWidget.getHistoryDateTime(i) + "should not display before " + usageHistoryWidget.getHistoryDateTime(i-1));
                }
            }
        }
        if (usageHistoryAPI.getStatusCode() != 200) {
            softAssert.assertTrue(usageHistoryWidget.isUsageHistoryErrorVisible(), "API is Giving error But Widget is not showing error Message on API is ");
            softAssert.fail("API is unable to give Usage History ");
        }
        softAssert.assertAll();
    }


    @Table(Name = "Recharge History")
    @Test(priority = 10, description = "Validating Recharge History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void rechargeHistoryWidgetTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Recharge History Widget", "Validating Recharge History Widget of User :" + customerNumber);
        RechargeHistoryWidgetPOM rechargeHistoryWidget = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible");
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), "Recharge History Widget's Date Picker is not visible");
        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
        int size = rechargeHistoryWidget.getNumberOfRows();
        if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from API");
            softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(rechargeHistoryWidget.gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            softAssert.assertEquals(rechargeHistoryWidget.getHeaders(1).toLowerCase().trim() + " " + rechargeHistoryWidget.getSubHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(rechargeHistoryWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(rechargeHistoryWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(rechargeHistoryWidget.getHeaders(4).toLowerCase().trim() + rechargeHistoryWidget.getSubHeaders(4).toLowerCase().trim().replace("|", ""), Data.getRow4().toLowerCase().trim().replace("|", ""), "Header Name for Row 4 is not as expected");
            softAssert.assertEquals(rechargeHistoryWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryCharges(i+1), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is not As received in API for row number " + i);
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryDateTime(i+1), rechargeHistoryWidget.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), "dd-MMM-yyyy HH:mm"), "Recharge History Date Time is not As received in API for row number " + i);
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBundleName(i+1), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is not As received in API for row number " + i);
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBenefits(i+1).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is not As received in API for row number " + i);
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryStatus(i+1), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is not As received in API for row number " + i);
                if (i != 0) {
                    softAssert.assertTrue(rechargeHistoryWidget.isSortOrderDisplay(rechargeHistoryWidget.getRechargeHistoryDateTime(i+1), rechargeHistoryWidget.getRechargeHistoryDateTime(i ), "dd-MMM-yyy HH:mm"), rechargeHistoryWidget.getRechargeHistoryDateTime(i+1) + "should not display before " + rechargeHistoryWidget.getRechargeHistoryDateTime(i));
                }
            }

        }
        if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryErrorVisible(), "API is Giving error But Widget is not showing error Message on API is ");
            softAssert.fail("API is unable to give Recharge History ");
        }
        softAssert.assertAll();
    }

}
