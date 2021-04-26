package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.SupervisorTicketListPage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
public class SupervisorTicketList extends BasePage {


    SupervisorTicketListPage pageElements;

    public SupervisorTicketList(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SupervisorTicketListPage.class);
    }

    public String getTransferErrorMessage() {
        String value = getText(pageElements.transferErrorMessage);
        commonLib.info("Reading transfer error message: " + value);
        return value;
    }

    public Boolean isCancelBtn() {
        Boolean status = checkState(pageElements.cancelBtn);
        commonLib.info("Is Cancel Button Displayed: " + status);
        return status;
    }

    public Boolean isTransferAnyWayBtn() {
        Boolean status = checkState(pageElements.transferAnywayBtn);
        commonLib.info("Is Transfer Any Way Button Displayed: " + status);
        return status;
    }

    public void clickCancelBtn() {
        commonLib.info("Clicking on Cancel Button.");
        click(pageElements.cancelBtn);
    }

    public void clickTransferAnyWayBtn() {
        commonLib.info("Clicking on Transfer Anyway button");
        click(pageElements.transferAnywayBtn);
    }

    public void writeTicketId(String ticketId) {
        commonLib.info("Search Ticket Id: " + ticketId);
        writeText(pageElements.searchTicketBox, ticketId);
    }

    public void writeTicketIdSecond(String ticketId) {
        commonLib.info("Search Ticket Id: " + ticketId);
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
        commonLib.info("Clicking on Search Button");
        click(pageElements.searchTicketBtn);
    }

    public boolean isTicketIdLabel() {
        final boolean state = checkState(pageElements.ticketIdLabel);
        commonLib.pass("Is Ticket Id field Available :" + state);
        return state;
    }

    public boolean isWorkGroupName() {
        commonLib.pass("Ticket lie in WorkGroup :" + getText(pageElements.workGroupName));
        return checkState(pageElements.workGroupName);
    }

    public String getWorkGroupName() {
        return getText(pageElements.workGroupName);
    }

    public boolean isPrioritylabel() {
        final boolean state = checkState(pageElements.prioritylabel);
        commonLib.pass("Is Priority field Available :" + state);
        return state;
    }

    public boolean isStateLabel() {
        final boolean state = checkState(pageElements.stateLabel);
        commonLib.pass("Is State field Available :" + state);
        return state;
    }

    public boolean isCreationdateLabel() {
        final boolean state = checkState(pageElements.creationdateLabel);
        commonLib.pass("Is Creation Date field Available :" + state);
        return state;
    }

    public boolean isCreatedbyLabel() {
        final boolean state = checkState(pageElements.createdbyLabel);
        commonLib.info("Is Created By field Available :" + state);
        return state;
    }

    public boolean isQueueLabel() {
        final boolean state = checkState(pageElements.queueLabel);
        commonLib.info("Is Queue field Available :" + state);
        return state;
    }

    public boolean isIssueLabel() {
        commonLib.info("Is Issue Label field Available :" + checkState(pageElements.prioritylabel));
        return checkState(pageElements.issueLabel);
    }

    public boolean isIssueTypeLabel() {
        commonLib.info("Is Priority field Available :" + checkState(pageElements.prioritylabel));
        return checkState(pageElements.issueTypeLabel);
    }

    public boolean isSubTypeLabel() {
        commonLib.info("Is Issue Type field Available :" + checkState(pageElements.issueTypeLabel));
        return checkState(pageElements.subTypeLabel);
    }

    public boolean isSubSubTypeLabel() {
        final boolean state = checkState(pageElements.subSubTypeLabel);
        commonLib.info("Is Issue Sub Sub Type field Available :" + state);
        return state;
    }

    public boolean isCodeLabel() {
        final boolean state = checkState(pageElements.codeLabel);
        commonLib.info("Is Code field Available :" + state);
        return state;
    }

    public String getTicketIdvalue() {
        final String text = getText(pageElements.ticketIdvalue);
        commonLib.info("Ticket Id: " + text);
        return text;
    }

    public String getWorkgroupSLA() {
        return getText(pageElements.workgroupSLA);
    }

    public String getPriorityValue() {
        return getText(pageElements.priorityValue);
    }

    public String getStatevalue() {
        return getText(pageElements.statevalue);
    }

    public String getCreationdatevalue() {
        return getText(pageElements.creationdatevalue);
    }

    public String getqueueValue() {
        return getText(pageElements.queueValue);
    }

    public String getIssueValue() {
        return getText(pageElements.issueValue);
    }

    public String getIssueTypeValue() {
        return getText(pageElements.issueTypeValue);
    }

    public String getSubTypeValue() {
        return getText(pageElements.subTypeValue);
    }

    public String getsubSubTypeValue() {
        return getText(pageElements.subSubTypeValue);
    }

    public String getCodeValue() {
        return getText(pageElements.codeValue);
    }

    public String getAssignedto() {
        return getText(pageElements.assignedto);
    }

    public void clickCheckbox() {
        log.info("Selecting Ticket");
        click(pageElements.checkBox);
    }

    public void clickAssigntoAgent() {
        commonLib.info("Clicking on Assign to Agent Button");
        click(pageElements.assignToagentBtn);
    }

    public boolean isAssignToAgent() {
        commonLib.info("Validate Assign to Agent Button Available");
        return checkState(pageElements.assignToagentBtn);
    }

    public void clickTransfertoQueue() {
        commonLib.info("Clicking on Transfer to Queue Button");
        click(pageElements.transfertoQueueBtn);
    }

    public boolean isTransferToQueue() {
        commonLib.info("Validate Transfer to Queue Button Available");
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
        commonLib.info("Switch Ticket State Type to closed");
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
        final boolean visible = isElementVisible(pageElements.noResultFound);
        commonLib.info("Is No Ticket Found :" + visible);
        return visible;
    }


    public void clickFilter() {
        commonLib.info("Selecting Filter");
        click(pageElements.selectFilterBtn);
    }

    public void resetFilter() {
        commonLib.info("Removing Filter");
        click(pageElements.resetFilterButton);
        waitTillLoaderGetsRemoved();
    }

    public boolean isResetFilter() {
        commonLib.info("Is Removing Filter Button Available");
        try {
            return checkState(pageElements.resetFilterButton);
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public boolean validateQueueFilter(String text) {
        commonLib.info("Validating Queue Filter");
        boolean answer = false;
        if (getListSize() > 0) {
            for (int i = 1; i <= getListSize(); i++) {
                By queue = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//ul/li[7]/span[2]");
                commonLib.info(getText(queue).trim() + " : " + text + " :" + getText(queue).trim().equalsIgnoreCase(text));
                answer = getText(queue).trim().equalsIgnoreCase(text);
            }
        } else {
            commonLib.warning("No Ticket Found");
        }
        return answer;
    }

    public void clickReopenButton() {
        commonLib.info("Clicking Reopen Button");
        click(pageElements.reOpenBtn);
    }

    public void addReopenComment(String comment) {
        commonLib.info("Add Reopen Comment: " + comment);
        writeText(pageElements.reOpenBox, comment);
        clickOutside();
    }

    public void submitReopenReq() {
        commonLib.info("Clicking on Submit Button");
        click(pageElements.submitReopenComment);
    }

    public void closedReopenBox() {
        commonLib.info("Clicking on closing reopen comment box Button");
        click(pageElements.closeReopenCommentBox);
    }

    public boolean isReopenBtn() {
        final boolean state = checkState(pageElements.reOpenBtn);
        log.info("Is Reopen Button Available: " + state);
        return state;
    }

    public boolean checkAssignedAUUID(String auuid) {
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
            final String text = getText(pageElements.assigneeAUUID);
            log.info("Ticket Assignee to :" + text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "Not Assigned";
        }
    }

    public String getAssigneeName() {
        try {
            final String text = getText(pageElements.assigneeName);
            log.info("Ticket Assignee to :" + text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "Not Assigned";
        }
    }

    public Boolean isNotAssigneeDisplay() {
        commonLib.info("Checking Is not assigned displayed");
        return checkState(pageElements.notAssigned);
    }

    public boolean isNegativeSLA() {
        try {
            final boolean state = checkState(pageElements.redDot);
            commonLib.info("Checking red dot symbol for negative SLA: " + state);
            return state;
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public boolean isAllTicketTab() {
        try {
            boolean flag = checkState(pageElements.allTicketTab);
            commonLib.info("IS All Assigned Ticket Tab displayed: " + flag);
            return flag;
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public boolean isMyAssignedTicketTab() {
        try {
            boolean flag = checkState(pageElements.myTicketTab);
            commonLib.info("IS My Assigned Ticket Tab displayed: " + flag);
            return flag;
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public boolean isPositiveSLA() {
        try {
            final boolean state = checkState(pageElements.greenDot);
            commonLib.info("Checking green dot symbol for positive SLA: " + state);
            return state;
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
            ticketList.add(getText(ticket).trim());
            commonLib.info("Ticket Id: " + getText(ticket).trim());
        }
        return ticketList;
    }

    public int getListSize() {
        List<WebElement> list = returnListOfElement(pageElements.allTicket);
        final int size = list.size();
        log.info("Size: " + size);
        return size;
    }

    public String getSymbol(int i) {
        By ticket = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//ul[1]//li[1]//span[2]");
        By symbol = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//span[@class=\"escalation\"]");
        final String text = getText(symbol);
        commonLib.info(text + ": Escalation symbol found on ticket Id: " + getText(ticket).trim());
        return text.trim();
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
            commonLib.info("Options Available : " + getText(search));
            searchOption.add(getText(search).trim());
        }
        return searchOption;
    }

    public String getMSISDN() {
        final String text = getText(pageElements.msisdn);
        commonLib.info("Reading MSISDN: " + text);
        return text;
    }

    public void clickSearchOptionByTextNoIgnoreCase(String text) {
        commonLib.info("Clicking search By option: " + text);
        By option = By.xpath("//ul[@class='ng-star-inserted']//li[normalize-space()='" + text + "']");
        click(option);
    }

    /*
    This Method will be used to check Source Title is visible or not under Ticket Listing
     */
    public Boolean isSourceTitleVisible() {
        return isVisible(pageElements.sourceTitleTicketRowTicketListing);
    }

    /*
    This Method will check source title for all the row present in a Page
     */
    public Boolean checkSourceTitleListingPage() {
        boolean result = false;
        final List<WebElement> listFromBy = getElementsListFromBy(pageElements.sourceTitleText);
        for (WebElement list : listFromBy) {
            result = StringUtils.isNoneBlank(selUtils.getText(list));
        }
        return result;
    }

    public void clickToOpenTicketFromDashboard() {
        if (isVisible(pageElements.openTicketDetailPage)) {
            click(pageElements.openTicketDetailPage);
            waitTillLoaderGetsRemoved();
        } else {
            commonLib.error("Ticket Data is NOT available over dashboard");
        }
    }

    public String checkSourceTitleDetailPage() {
        return getText(pageElements.sourceTitleText);
    }

    public void goBackToTicketListing() {
        if (isVisible(pageElements.backButtonDetailPage)) {
            click(pageElements.backButtonDetailPage);
            waitTillLoaderGetsRemoved();
        }
    }
}
