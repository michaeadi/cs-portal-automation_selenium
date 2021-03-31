package tests.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.dataproviders.nftrDataBeans;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pagemethods.InteractionsPOM;
import com.airtel.cs.pagerepository.pagemethods.MessageHistoryTabPOM;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.customerInteractionPagePOM;
import com.airtel.cs.pagerepository.pagemethods.customerInteractionsSearchPOM;
import com.airtel.cs.pagerepository.pagemethods.viewHistoryPOM;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InteractionCommentTest extends BaseTest {

    String ticket_number = null;

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(Method method, TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Create Interaction ", dataProvider = "interactionComment", dataProviderClass = DataProviders.class)
    public void addInteractionComment(nftrDataBeans Data) throws InterruptedException, IOException {
        ExtentTestManager.startTest("Add Interaction Ticket Comment", "Add Interaction Ticket Comment on Ticket" + Data.getIssueCode());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        customerInteractionPagePOM customerInteractionPagePOM = new customerInteractionPagePOM(driver);
        InteractionsPOM interactionsPOM = customerInteractionPagePOM.clickOnInteractionIcon();
        SoftAssert softAssert = new SoftAssert();
        interactionsPOM.clickOnCode();
//        if (!interactionsPOM.isSearchVisible()) {
//            interactionsPOM.clickOnCode();
//        }
        try {
            interactionsPOM.searchCode(Data.getIssueCode());
        } catch (Exception e) {
            Thread.sleep(1000);
            interactionsPOM.clickOnCode();
            interactionsPOM.searchCode(Data.getIssueCode());

        }
        interactionsPOM.selectCode(Data.getIssueCode());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Creating ticket with issue code -" + Data.getIssueCode());
        System.out.println(interactionsPOM.getIssue());
        softAssert.assertEquals(interactionsPOM.getIssue().trim().toLowerCase().replace(" ", ""), Data.getIssue().trim().toLowerCase().replace(" ", ""), "Issue is not as expected ");
        System.out.println(interactionsPOM.getIssueSubSubType());
        softAssert.assertEquals(interactionsPOM.getIssueSubSubType().trim().toLowerCase().replace(" ", ""), Data.getIssueSubSubType().trim().toLowerCase().replace(" ", ""), "Issue sub sub type is not as expected ");
        System.out.println(interactionsPOM.getIssueType());
        softAssert.assertEquals(interactionsPOM.getIssueType().trim().toLowerCase().replace(" ", ""), Data.getIssueType().trim().toLowerCase().replace(" ", ""), "Issue type is not as expected ");
        System.out.println(interactionsPOM.getIssueSubType());
        softAssert.assertEquals(interactionsPOM.getIssueSubType().trim().toLowerCase().replace(" ", ""), Data.getIssueSubType().trim().toLowerCase().replace(" ", ""), "Issue sub type is not as expected ");
        try {

            if (Data.getIssueFieldLabel1() != null)
                if (Data.getIssueFieldType1().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel1().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("1"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("1").replace("*", "").trim(), (Data.getIssueFieldLabel1().replace("*", "").trim()), Data.getIssueFieldLabel1() + " Label does not match");
                    if (Data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("1").contains("*"), Data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("1", "012345");
                } else if (Data.getIssueFieldType1().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel1().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel1()));
                    if (Data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));

                } else if (Data.getIssueFieldType1().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel1().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("1"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("1").replace("*", "").trim(), (Data.getIssueFieldLabel1().replace("*", "").trim()), Data.getIssueFieldLabel1() + "Label does not match");
                    if (Data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("1").contains("*"), Data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("1");
                }

            if (Data.getIssueFieldLabel2() != null)
                if (Data.getIssueFieldType2().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel2().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("2"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("2").replace("*", "").trim(), (Data.getIssueFieldLabel2().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("2").contains("*"), Data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("2", "012345");
                } else if (Data.getIssueFieldType2().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel2().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel2()));
                    if (Data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType2().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel2().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("2"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("2").replace("*", "").trim(), (Data.getIssueFieldLabel2().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("2").contains("*"), Data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("2");
                }

            if (Data.getIssueFieldLabel3() != null)
                if (Data.getIssueFieldType3().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel3().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("3"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("3").replace("*", "").trim(), (Data.getIssueFieldLabel3().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("3").contains("*"), Data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("3", "012345");
                } else if (Data.getIssueFieldType3().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel3().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel3()));
                    if (Data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));

                } else if (Data.getIssueFieldType3().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel3().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("3"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("3").replace("*", "").trim(), (Data.getIssueFieldLabel3().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("3").contains("*"), Data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("3");
                }

            System.out.println("Field Type-4" + Data.getIssueFieldType4());

            if (Data.getIssueFieldLabel4() != null)
                if (Data.getIssueFieldType4().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel4().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("4"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("4").replace("*", "").trim(), (Data.getIssueFieldLabel4().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("4").contains("*"), Data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("4", "012345");
                } else if (Data.getIssueFieldType4().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel4().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel4()));
                    if (Data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType4().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel4().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("4"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("4").replace("*", "").trim(), (Data.getIssueFieldLabel4().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("4").contains("*"), Data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("4");
                }

            if (Data.getIssueFieldLabel5() != null)
                if (Data.getIssueFieldType5().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel5().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("5"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("5").replace("*", "").trim(), (Data.getIssueFieldLabel5().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("5").contains("*"), Data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("5", "012345");
                } else if (Data.getIssueFieldType5().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel5().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel5()));
                    if (Data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType5().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel5().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("5"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("5").replace("*", "").trim(), (Data.getIssueFieldLabel5().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("5").contains("*"), Data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("5");
                }

            if (Data.getIssueFieldLabel6() != null)
                if (Data.getIssueFieldType6().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel6().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("6"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("6").replace("*", "").trim(), (Data.getIssueFieldLabel6().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("6").contains("*"), Data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("6", "012345");
                } else if (Data.getIssueFieldType6().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel6().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel6()));
                    if (Data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType6().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel6().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("6"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("6").replace("*", "").trim(), (Data.getIssueFieldLabel6().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("6").contains("*"), Data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("6");
                }

            if (Data.getIssueFieldLabel7() != null)
                if (Data.getIssueFieldType7().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel7().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("7"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("7").replace("*", "").trim(), (Data.getIssueFieldLabel7().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("7").contains("*"), Data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("7", "012345");
                } else if (Data.getIssueFieldType7().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel7().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel7()));
                    if (Data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType7().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel7().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("7"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("7").replace("*", "").trim(), (Data.getIssueFieldLabel7().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("7").contains("*"), Data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("7");
                }
            interactionsPOM.sendComment("Automation Suite");
            Assert.assertTrue(interactionsPOM.isSaveEnable());
            interactionsPOM.clickOnSave();
            softAssert.assertTrue(interactionsPOM.isResolvedFTRDisplayed());
            System.out.println(interactionsPOM.getResolvedFTRDisplayed());
            String[] valueToWrite = {""};
            if (!interactionsPOM.getResolvedFTRDisplayed().contains("Resolved FTR")) {
                ticket_number = interactionsPOM.getResolvedFTRDisplayed();
                System.out.println(ticket_number);
                String comment = "Adding Interaction Comment Using Automation";
                interactionsPOM.clickCommentIcon();
                interactionsPOM.addInteractionComment(comment);
                interactionsPOM.saveInteractionComment();
                interactionsPOM.waitTillLoaderGetsRemoved();
                interactionsPOM.openAddedComment();
                softAssert.assertEquals(interactionsPOM.getAddedComment().toLowerCase().trim(), comment.toLowerCase().trim(), "Agent Added Interaction Ticket comment does not match");
            } else {
                softAssert.fail("It's FTR not NFTR");
            }
        } catch (NoSuchElementException e) {
            System.out.println("in catch");
            interactionsPOM.closeInteractions();
            interactionsPOM.clickOnContinueButton();
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        interactionsPOM.closeTicketCommentBox();
        interactionsPOM.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Check Sent SMS display in message history", dependsOnMethods = "addInteractionComment")
    public void checkSendMessageLog() {
        ExtentTestManager.startTest("Check Sent SMS display in message history for System Type", "Check Sent SMS display in message history");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
        viewHistory.waitTillLoaderGetsRemoved();
        MessageHistoryTabPOM messageHistory = viewHistory.clickOnMessageHistory();
        messageHistory.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(messageHistory.isMessageTypeColumn(), "Message Type Column does not display on UI");
        softAssert.assertTrue(messageHistory.isDateSentColumn(), "Date Sent Column does not display on UI");
        softAssert.assertTrue(messageHistory.isTemplateColumn(), "Template/Event Column does not display on UI");
        softAssert.assertTrue(messageHistory.isMessageLanguageColumn(), "Message Language Column does not display on UI");
        softAssert.assertTrue(messageHistory.isMessageTextColumn(), "Message Text Column does not display on UI");
        softAssert.assertTrue(messageHistory.isMessageTypeColumn(), "Message Type Column does not display on UI");
        softAssert.assertEquals(messageHistory.messageType(1).toLowerCase().trim(), config.getProperty("systemSMSType").toLowerCase().trim(), "Message Type is not system");
        softAssert.assertEquals(messageHistory.templateEvent(1).toLowerCase().trim(), config.getProperty("ticketCreateEvent").toLowerCase().trim(), "Template event not same as defined.");
        softAssert.assertTrue(messageHistory.messageText(1).contains(ticket_number), "Message content not same as set message content.");
        softAssert.assertTrue(messageHistory.isActionBtnDisable(1), "Resend SMS icon does not disable");
        softAssert.assertTrue(messageHistory.agentId(1).trim().equalsIgnoreCase("-"), " :Agent id does not empty");
        softAssert.assertTrue(messageHistory.agentName(1).trim().equalsIgnoreCase("-"), " :Agent name does not empty");
        softAssert.assertAll();
    }
}
