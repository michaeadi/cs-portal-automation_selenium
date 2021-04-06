package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.SupervisorTicketListPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class SupervisorTicketListPage extends BasePage {


    SupervisorTicketListPageElements pageElements;

    public SupervisorTicketListPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SupervisorTicketListPageElements.class);
    }

    public String getTransferErrorMessage() {
        String value = readText(pageElements.transferErrorMessage);
        UtilsMethods.printInfoLog("Reading transfer error message: " + value);
        return value;
    }

    public Boolean isCancelBtn() {
        Boolean status = checkState(pageElements.cancelBtn);
        UtilsMethods.printInfoLog("Is Cancel Button Displayed: " + status);
        return status;
    }

    public Boolean isTransferAnyWayBtn() {
        Boolean status = checkState(pageElements.transferAnywayBtn);
        UtilsMethods.printInfoLog("Is Transfer Any Way Button Displayed: " + status);
        return status;
    }

    public void clickCancelBtn() {
        UtilsMethods.printInfoLog("Clicking on Cancel Button.");
        click(pageElements.cancelBtn);
    }

    public void clickTransferAnyWayBtn() {
        UtilsMethods.printInfoLog("Clicking on Transfer Anyway button");
        click(pageElements.transferAnywayBtn);
    }

    public void writeTicketId(String ticketId) {
        UtilsMethods.printInfoLog("Search Ticket Id: " + ticketId);
        writeText(pageElements.searchTicketBox, ticketId);
    }

    public void writeTicketIdSecond(String ticketId) {
        UtilsMethods.printInfoLog("Search Ticket Id: " + ticketId);
        writeText(pageElements.searchTicketBox2, ticketId);
    }

    public void clearInputBox() {
        log.info("Clear Search Box");
        for (int i = 0; i < 12; i++) {
            driver.findElement(pageElements.searchTicketBox).sendKeys(Keys.BACK_SPACE);
        }
    }

    public void clickSearchBtn() {
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.searchTicketBtn));
        UtilsMethods.printInfoLog("Clicking on Search Button");
        click(pageElements.searchTicketBtn);
    }

    public boolean isTicketIdLabel() {
        UtilsMethods.printPassLog("Is Ticket Id field Available :" + checkState(pageElements.ticketIdLabel));
        return checkState(pageElements.ticketIdLabel);
    }

    public boolean isWorkGroupName() {
        UtilsMethods.printPassLog("Ticket lie in WorkGroup :" + readText(pageElements.workGroupName));
        return checkState(pageElements.workGroupName);
    }

    public String getWorkGroupName() {
        return readText(pageElements.workGroupName);
    }

    public boolean isPrioritylabel() {
        UtilsMethods.printPassLog("Is Priority field Available :" + checkState(pageElements.prioritylabel));
        return checkState(pageElements.prioritylabel);
    }

    public boolean isStateLabel() {
        UtilsMethods.printPassLog("Is State field Available :" + checkState(pageElements.stateLabel));
        return checkState(pageElements.stateLabel);
    }

    public boolean isCreationdateLabel() {
        UtilsMethods.printPassLog("Is Creation Date field Available :" + checkState(pageElements.creationdateLabel));
        return checkState(pageElements.creationdateLabel);
    }

    public boolean isCreatedbyLabel() {
        UtilsMethods.printInfoLog("Is Created By field Available :" + checkState(pageElements.createdbyLabel));
        return checkState(pageElements.createdbyLabel);
    }

    public boolean isQueueLabel() {
        UtilsMethods.printInfoLog("Is Queue field Available :" + checkState(pageElements.queueLabel));
        return checkState(pageElements.queueLabel);
    }

    public boolean isIssueLabel() {
        UtilsMethods.printInfoLog("Is Issue Label field Available :" + checkState(pageElements.prioritylabel));
        return checkState(pageElements.issueLabel);
    }

    public boolean isIssueTypeLabel() {
        UtilsMethods.printInfoLog("Is Priority field Available :" + checkState(pageElements.prioritylabel));
        return checkState(pageElements.issueTypeLabel);
    }

    public boolean isSubTypeLabel() {
        UtilsMethods.printInfoLog("Is Issue Type field Available :" + checkState(pageElements.issueTypeLabel));
        return checkState(pageElements.subTypeLabel);
    }

    public boolean isSubSubTypeLabel() {
        UtilsMethods.printInfoLog("Is Issue Sub Sub Type field Available :" + checkState(pageElements.subSubTypeLabel));
        return checkState(pageElements.subSubTypeLabel);
    }

    public boolean isCodeLabel() {
        UtilsMethods.printInfoLog("Is Code field Available :" + checkState(pageElements.codeLabel));
        return checkState(pageElements.codeLabel);
    }

    public String getTicketIdvalue() {
        UtilsMethods.printInfoLog("Ticket Id: " + readText(pageElements.ticketIdvalue));
        return readText(pageElements.ticketIdvalue);
    }

    public String getWorkgroupSLA() {
        return readText(pageElements.workgroupSLA);
    }

    public String getPriorityValue() {
        return readText(pageElements.priorityValue);
    }

    public String getStatevalue() {
        return readText(pageElements.statevalue);
    }

    public String getCreationdatevalue() {
        return readText(pageElements.creationdatevalue);
    }

    public String getqueueValue() {
        return readText(pageElements.queueValue);
    }

    public String getIssueValue() {
        return readText(pageElements.issueValue);
    }

    public String getIssueTypeValue() {
        return readText(pageElements.issueTypeValue);
    }

    public String getSubTypeValue() {
        return readText(pageElements.subTypeValue);
    }

    public String getsubSubTypeValue() {
        return readText(pageElements.subSubTypeValue);
    }

    public String getCodeValue() {
        return readText(pageElements.codeValue);
    }

    public String getAssignedto() {
        return readText(pageElements.assignedto);
    }

    public void clickCheckbox() {
        log.info("Selecting Ticket");
        click(pageElements.checkBox);
    }

    public void clickAssigntoAgent() {
        UtilsMethods.printInfoLog("Clicking on Assign to Agent Button");
        click(pageElements.assignToagentBtn);
    }

    public boolean isAssignToAgent() {
        UtilsMethods.printInfoLog("Validate Assign to Agent Button Available");
        return checkState(pageElements.assignToagentBtn);
    }

    public void clickTransfertoQueue() {
        UtilsMethods.printInfoLog("Clicking on Transfer to Queue Button");
        click(pageElements.transfertoQueueBtn);
    }

    public boolean isTransferToQueue() {
        UtilsMethods.printInfoLog("Validate Transfer to Queue Button Available");
        return checkState(pageElements.transfertoQueueBtn);
    }

    public boolean checkOpenTicketStateType() {
        log.info("Checking Open Ticket State Type Select");
        return checkState(pageElements.openTicketType);
    }


    private boolean checkClosedTicketstateType() {
        log.info("Checking Closed Ticket State Type Select");
        return checkState(pageElements.closedTicketType);
    }


    public void changeTicketTypeToClosed() {
        UtilsMethods.printInfoLog("Switch Ticket State Type to closed");
        click(pageElements.selectTicketType);
        click(pageElements.closedTicketType);
    }

    public void changeTicketTypeToOpen() {
        log.info("Switch Ticket State Type to Open");
        click(pageElements.selectTicketType);
        click(pageElements.openTicketType);
    }


    public void viewTicket() {
        log.info("View Ticket: " + getTicketIdvalue());
        click(pageElements.stateLabel);
    }

    public boolean noTicketFound() {
        log.info("No ticket found");
        UtilsMethods.printInfoLog("Is No Ticket Found :" + isElementVisible(pageElements.noResultFound));
        return isElementVisible(pageElements.noResultFound);
    }


    public void clickFilter() {
        UtilsMethods.printInfoLog("Selecting Filter");
        click(pageElements.selectFilterBtn);
    }

    public void resetFilter() {
        UtilsMethods.printInfoLog("Removing Filter");
        click(pageElements.resetFilterButton);
        waitTillLoaderGetsRemoved();
    }

    public boolean isResetFilter() {
        UtilsMethods.printInfoLog("Is Removing Filter Button Available");
        try {
            return checkState(pageElements.resetFilterButton);
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public boolean validateQueueFilter(String text) {
        UtilsMethods.printInfoLog("Validating Queue Filter");
        boolean answer = false;
        if (getListSize() > 0) {
            for (int i = 1; i <= getListSize(); i++) {
                By queue = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//ul/li[7]/span[2]");
                UtilsMethods.printInfoLog(readText(queue).trim() + " : " + text + " :" + readText(queue).trim().equalsIgnoreCase(text));
                answer = readText(queue).trim().equalsIgnoreCase(text);
            }
        } else {
            UtilsMethods.printWarningLog("No Ticket Found");
        }
        return answer;
    }

    public void ClickReopenButton() {
        UtilsMethods.printInfoLog("Clicking Reopen Button");
        click(pageElements.reOpenBtn);
    }

    public void addReopenComment(String comment) {
        UtilsMethods.printInfoLog("Add Reopen Comment: " + comment);
        writeText(pageElements.reOpenBox, comment);
        clickOutside();
    }

    public void submitReopenReq() {
        UtilsMethods.printInfoLog("Clicking on Submit Button");
        click(pageElements.submitReopenComment);
    }

    public void closedReopenBox() {
        UtilsMethods.printInfoLog("Clicking on closing reopen comment box Button");
        click(pageElements.closeReopenCommentBox);
    }

    public boolean isReopenBtn() {
        log.info("Is Reopen Button Available: " + checkState(pageElements.reOpenBtn));
        return checkState(pageElements.reOpenBtn);
    }

    public boolean CheckAssignedAUUID(String auuid) {
        log.info("Ticket Validated and Assigned to Agent AUUID: " + auuid);
        By agentAUUID = By.xpath("//span[contains(text(),'" + auuid + "')]");
        return checkState(agentAUUID);
    }

    public By getEscalationSymbol() {
        log.info("Getting Escalation level");
        return pageElements.allTicket;
    }

    public String getAssigneeAUUID() {
        try {
            log.info("Ticket Assignee to :" + readText(pageElements.assigneeAUUID));
            return readText(pageElements.assigneeAUUID);
        } catch (Exception e) {
            e.printStackTrace();
            return "Not Assigned";
        }
    }

    public String getAssigneeName() {
        try {
            log.info("Ticket Assignee to :" + readText(pageElements.assigneeName));
            return readText(pageElements.assigneeName);
        } catch (Exception e) {
            e.printStackTrace();
            return "Not Assigned";
        }
    }

    public Boolean isNotAssigneeDisplay() {
        UtilsMethods.printInfoLog("Checking Is not assigned displayed");
        return checkState(pageElements.notAssigned);
    }

    public boolean isNegativeSLA() {
        try {
            UtilsMethods.printInfoLog("Checking red dot symbol for negative SLA: " + checkState(pageElements.redDot));
            return checkState(pageElements.redDot);
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public boolean isAllTicketTab() {
        try {
            boolean flag = checkState(pageElements.allTicketTab);
            UtilsMethods.printInfoLog("IS All Assigned Ticket Tab displayed: " + flag);
            return flag;
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public boolean isMyAssignedTicketTab() {
        try {
            boolean flag = checkState(pageElements.myTicketTab);
            UtilsMethods.printInfoLog("IS My Assigned Ticket Tab displayed: " + flag);
            return flag;
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public boolean isPositiveSLA() {
        try {
            UtilsMethods.printInfoLog("Checking green dot symbol for positive SLA: " + checkState(pageElements.greenDot));
            return checkState(pageElements.greenDot);
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public List<String> getALLTicketId() {
        List<WebElement> list = returnListOfElement(pageElements.allTicket);
        List<String> ticketList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By ticket = By.xpath("//div[@class=\"table-card ng-star-inserted\"][" + i + "]//ul[1]//li[1]//span[2]");
            ticketList.add(readText(ticket).trim());
            UtilsMethods.printInfoLog("Ticket Id: " + readText(ticket).trim());
        }
        return ticketList;
    }

    public int getListSize() {
        List<WebElement> list = returnListOfElement(pageElements.allTicket);
        log.info("Size: " + list.size());
        return list.size();
    }

    public String getSymbol(int i) {
        By ticket = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//ul[1]//li[1]//span[2]");
        By symbol = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//span[@class=\"escalation\"]");
        UtilsMethods.printInfoLog(readText(symbol) + ": Escalation symbol found on ticket Id: " + readText(ticket).trim());
        return readText(symbol).trim();
    }

    public void clickTicketOption() {
        log.info("Click on Ticket Icon to get list of option");
        click(pageElements.searchOptionBtn);
    }

    public List<String> getListOfSearchOption() {
        log.info("Getting Search Option");
        List<WebElement> list = returnListOfElement(pageElements.allSearchOption);
        List<String> searchOption = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By search = By.xpath("//ul[@class='ng-star-inserted']//li[" + i + "]");
            UtilsMethods.printInfoLog("Options Available : " + readText(search));
            searchOption.add(readText(search).trim());
        }
        return searchOption;
    }

    public String getMSISDN() {
        UtilsMethods.printInfoLog("Reading MSISDN: " + readText(pageElements.msisdn));
        return readText(pageElements.msisdn);
    }

    public void clickSearchOptionByTextNoIgnoreCase(String text) {
        UtilsMethods.printInfoLog("Clicking search By option: " + text);
        By option = By.xpath("//ul[@class='ng-star-inserted']//li[normalize-space()='" + text + "']");
        click(option);
    }
}
