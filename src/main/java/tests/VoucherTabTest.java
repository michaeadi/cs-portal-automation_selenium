package tests;

import API.APITest;
import POJO.Voucher.VoucherDetail;
import POJO.Voucher.VoucherSearchPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

public class VoucherTabTest extends BaseTest {

    APITest api = new APITest();

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        customerInteractionPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Voucher Search Test")
    public void voucherSearchTest() throws InterruptedException {
        ExtentTestManager.startTest("Validate Voucher Search Test", "Validate Voucher Search Test");
        SoftAssert softAssert = new SoftAssert();
        RechargeHistoryWidgetPOM rechargeHistory = new RechargeHistoryWidgetPOM(driver);
        rechargeHistory.writeVoucherId("000106222035384");
        VoucherTabPOM voucherTab = rechargeHistory.clickSearchBtn();
        try {
            voucherTab.waitTillTimeLineGetsRemoved();
            Assert.assertTrue(voucherTab.isVoucherTabOpen(), "Voucher Id not found");
            VoucherSearchPOJO voucher = api.voucherSearchTest("000106222035384");
            VoucherDetail voucherDetail = voucher.getResult();
            if(voucher.getStatus().equalsIgnoreCase("200")) {
                softAssert.assertEquals(voucherTab.getSerialValue(), voucherDetail.getVoucherId(), "Voucher Serial number is not same as search voucher id");
                softAssert.assertEquals(voucherTab.getStatusValue().toLowerCase().trim(), voucherDetail.getStatus().toLowerCase().trim(), "Voucher Status is not same as voucher status received by api");
                softAssert.assertEquals(voucherTab.getSubStatus().toLowerCase().trim(), voucherDetail.getSubStatus().toLowerCase().trim(), "Voucher Sub Status is not same as voucher Sub Status received by api");
                softAssert.assertEquals(voucherTab.getRechargeAmt().toLowerCase().trim(), voucherDetail.getRechargeAmount().toLowerCase().trim(), "Voucher Recharge amount is not same as voucher Recharge amount received by api");
                softAssert.assertEquals(voucherTab.getTimeStamp().toLowerCase().trim(), voucherDetail.getTimestamp().toLowerCase().trim(), "Voucher Time Stamp is not same as voucher Time Stamp received by api");
                softAssert.assertEquals(voucherTab.getExpiryDate().toLowerCase().trim(), voucherDetail.getExpiryDate().toLowerCase().trim(), "Voucher Expiry date is not same as voucher Expiry date received by api");
                if (voucherDetail.getSubscriberId() != null)
                    softAssert.assertEquals(voucherTab.getSubscriberId().toLowerCase().trim(), voucherDetail.getSubscriberId().toLowerCase().trim(), "Voucher Subscriber Id is not same as voucher Subscriber Id received by api");
                softAssert.assertEquals(voucherTab.getAgent().toLowerCase().trim(), voucherDetail.getAgent().toLowerCase().trim(), "Voucher Agent not same as voucher Agent received by api");
                softAssert.assertEquals(voucherTab.getBatchID().toLowerCase().trim(), voucherDetail.getBatchId().toLowerCase().trim(), "Voucher Batch Id not same as voucher Batch Id received by api");
                softAssert.assertEquals(voucherTab.getVoucherGroup().toLowerCase().trim(), voucherDetail.getVoucherGroup().toLowerCase().trim(), "Voucher group not same as voucher group received by api");
                voucherTab.clickDoneBtn();
            }else{
                softAssert.fail("API Response is not 200.");
            }
        }catch (TimeoutException | NoSuchElementException e){
            Thread.sleep(500);
            voucherTab.clickDoneBtn();
            softAssert.fail("Not able to validate Voucher tab: "+e.getMessage());
        }
        softAssert.assertAll();
    }

}
