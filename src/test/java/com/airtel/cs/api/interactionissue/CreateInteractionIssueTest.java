package com.airtel.cs.api.interactionissue;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.FtrDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.excelutils.WriteToExcel;
import com.airtel.cs.model.cs.request.interactionissue.InteractionIssueRequest;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

@Log4j2
public class CreateInteractionIssueTest extends ApiPrerequisites {

    public String interactionId = null;
    String validClientInfo = null;
    Integer validCategoryId = 0;
    String code = null;
    String issueDetails = "";
    String categoryHierarchy = null;

    @Test(priority = 1, description = "Validate API Response Test is Successful for NFTR Issue", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest"})
    public void createNFTRIssueWithValidTest(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate Create NFTR Issue /v1/interactions/issue API With Category Id: " +
                    getClientCode(list), "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            InteractionIssueRequest interactionIssue = api.createInteractionIssue(validHeaderList, getValidClientConfig(MSISDN), getIssueDetails(validCategoryId), getLastCategoryId(validCategoryId));
            assertCheck.append(actions.assertEqualIntType(interactionIssue.getStatusCode(), 200, "Status Code Matched", "Status Code Not Matched and is - " + interactionIssue.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(interactionIssue.getMessage(), constants.getValue("interactionCreated"), "Response Message Matched", "Response Message not Matched"));
            if (interactionIssue.getResult().getIssues().get(0).getTicket() != null && interactionIssue.getResult().getIssues().get(0).getTicket().getTicketId() != null) {
                String ticket_number = interactionIssue.getResult().getIssues().get(0).getTicket().getTicketId();
                commonLib.info("Ticket Number:ME " + ticket_number);
                String[] valueToWrite = new String[]{ticket_number};
                WriteToExcel objExcelFile = new WriteToExcel();
                File Excel = new File(excelPath);
                commonLib.info("Ticket Number:You " + list.getRowNum());
                objExcelFile.writeTicketNumber(Excel.getAbsolutePath(), constants.getValue(nftrSheetValue), valueToWrite, list.getRowNum());
                commonLib.info("Ticket Number Written to Excel " + valueToWrite[0]);
            } else {
                commonLib.fail("Ticket Id does not generated for NFTR Ticket.", false);
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - createNFTRIssueWithValidTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 2, description = "Validate API Response Test is Successful for FTR Issue", dataProvider = "FTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest"})
    public void createFTRIssueWithValidTest(FtrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate Create FTR Issue /v1/interactions/issue API With Category Id: " + getClientCode(list), "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            InteractionIssueRequest interactionIssue = api.createInteractionIssue(validHeaderList, getValidClientConfig(MSISDN), getIssueDetails(validCategoryId), getLastCategoryId(validCategoryId));
            assertCheck.append(actions.assertEqualIntType(interactionIssue.getStatusCode(), 200, "Status Code Matched Successfully", "Status Code Not Matched and is - " + interactionIssue.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(interactionIssue.getMessage(), constants.getValue("interactionCreated"), "Response Message Matched Successfully", "API message is not same as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - createFTRIssueWithValidTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, description = "Validate API Response With Invalid Token Test is Successful", dataProvider = "FTRIssue", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void createFTRIssueWithInValidTokenTest(FtrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate Create issue /v1/interactions/issue API With Invalid Token Test: ", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            InteractionIssueRequest interactionIssue = api.createInteractionIssue(restUtils.invalidToken(), getValidClientConfig(MSISDN), getIssueDetails(validCategoryId), getLastCategoryId(validCategoryId));
            assertCheck.append(actions.assertEqualStringType(interactionIssue.getStatus(), "401", "Status Code Matched Successfully", "Status Code Not Matched and is - " + interactionIssue.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(interactionIssue.getMessage(), constants.getValue("unauthorized"), "Response Message Matched Successfully", "API Response Message as not expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - createFTRIssueWithInValidTokenTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, description = "Validate Interaction Issue API with last Category Id only", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest"})
    public void testInteractionIssueApiWithLastCategoryId(NftrDataBeans list) throws IOException {
        try {
            selUtils.addTestcaseDescription("Validate Create NFTR Interaction Issue /v1/interactions/issue API With Last Category Id: " +
                    getClientCode(list), "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            InteractionIssueRequest interactionIssue = api.createInteractionIssue(validHeaderList, getValidClientConfig(MSISDN), getIssueDetails(validCategoryId), getLastCategoryId(validCategoryId));
            assertCheck.append(actions.assertEqualIntType(interactionIssue.getStatusCode(), 200, "Status Code Matched", "Status Code Not Matched and is - " + interactionIssue.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(interactionIssue.getMessage(), constants.getValue("interactionCreated"), "Response Message Matched", "Response Message not Matched"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testInteractionIssueApiWithLastCategoryId " + e.getMessage(), false);
        }
    }
}
