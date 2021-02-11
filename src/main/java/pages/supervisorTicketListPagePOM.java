package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class supervisorTicketListPagePOM extends BasePage {

    @CacheLookup
    By searchTicketBox = By.xpath("//input[@type='search'][1]");
    By searchTicketBox2 = By.xpath("//span[@class='search-box small-search' or @class='search-box small-search search-box-container']//input");
    @CacheLookup
    By searchTicketBtn = By.xpath("//app-ticket-search-box//button");
    By ticketIdLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[1]//span[@class=\"data-title\"]");
    By ticketIdvalue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area\" or @class=\"data-area-full\"]//ul[1]//li[1]//span[@class=\"blue-clr\"]");
    By workGroupName = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[2]//span[@class=\"data-title ellipsis value-clr\"]");
    By workgroupSLA = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[2]//span[2]");
    By prioritylabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[3]//span[1]");
    By priorityValue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[3]//span[2]");
    By stateLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[4]//span[1]");
    By statevalue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[4]//span[2]");
    By creationdateLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[5]//span[1]");
    By creationdatevalue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[5]//span[2]");
    By createdbyLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[6]//span[1]");
    By createdbyvalue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[6]//span[2]");
    By queueLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[7]//span[1]");
    By listQueueValue = By.xpath("//ul/li[7]/span[2]");
    By queueValue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[1]//li[7]//span[2]");
    By issueLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[1]//span[1]");
    By issueValue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[1]//span[2]");
    By issueTypeLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[2]//span[1]");
    By issueTypeValue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[2]//span[2]");
    By subTypeLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[3]//span[1]");
    By subTypeValue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[3]//span[2]");
    By subSubTypeLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[4]//span[1]");
    By subSubTypeValue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[4]//span[2]");
    By codeLabel = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[5]//span[1]");
    By codeValue = By.xpath("//app-ticket-list//div[@class=\"container-fluid table-card ng-star-inserted\"][1]//div[@class=\"data-area-full\" or @class=\"data-area\"]//ul[2]//li[5]//span[2]");
    By assignedto = By.xpath("//body//p//span[1]");
    By checkBox = By.xpath("//app-ticket-list//div[1]//div[1]//div[1]//input[@class=\"supercheck\"]");
    By assignToagentBtn = By.xpath("//li[1]//button[1]");
    By transfertoQueueBtn = By.xpath("//li[2]//button[1]");
    By loggedInQueue = By.xpath("//span[contains(text(),'Login with Ticket Pool')]");
    By selectTicketType = By.xpath("//*[@name=\"state\"]");
    By openTicketType = By.xpath("//span[contains(text(),' Open ') and @class='mat-option-text']");
    By closedTicketType = By.xpath("//span[contains(text(),' Closed ') and @class='mat-option-text']]");
    By selectFilterBtn = By.xpath("//span[contains(text(),'Select Filter')]");
    By pageRefreshBtn = By.xpath("//span[contains(text(),'Refresh ')]");
    By noResultFound = By.xpath("//body//mat-error//p[1]");
    By resetFilterButton = By.xpath("//div[@class='clear-filter-btn']//button");
    By reOpenBtn = By.xpath("//li[1]//button[1]");
    By reOpenBox = By.xpath("//*[@placeholder=\"Leave a comment\"]");
    By submitReopenComment = By.className("sbt-btn");
    By closeReopenCommentBox=By.xpath("//button[@class=\"close-btn\"]");
    By redDot = By.xpath("//span[@class='reddot ng-star-inserted']");
    By greenDot = By.xpath("//span[@class='greendot ng-star-inserted']");
    By assigneeAUUID = By.xpath("//div[@class='service-request']//div[1]//div[1]//div[2]//div[2]//p[1]//span[3]");
    By allTicket = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"]");
    By searchOptionBtn = By.xpath("//div[@class='options']");
    By allSearchOption = By.xpath("//ul[@class='ng-star-inserted']//li");
    By msisdn=By.xpath("//span[@class=\"td-msisdn auuid-clr\"]");

    public supervisorTicketListPagePOM(WebDriver driver) {
        super(driver);
    }

    public void writeTicketId(String ticketId) {
        log.info("Search Ticket Id: " + ticketId);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Search Ticket Id: " + ticketId);
        writeText(searchTicketBox, ticketId);
    }

    public void writeTicketIdSecond(String ticketId) {
        log.info("Search Ticket Id: " + ticketId);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Search Ticket Id: " + ticketId);
        writeText(searchTicketBox2, ticketId);
    }

    public void clearInputBox() {
        log.info("Clear Search Box");
        for (int i = 0; i < 12; i++) {
            driver.findElement(searchTicketBox).sendKeys(Keys.BACK_SPACE);
        }
    }

    public void clickSearchBtn() {
        log.info("Clicking on Search Button");
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.elementToBeClickable(searchTicketBtn));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Search Button");
        click(searchTicketBtn);
    }

    public boolean isTicketIdLabel() {
        log.info("Is Ticket Id field Available :" + checkState(ticketIdLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Ticket Id field Available :" + checkState(ticketIdLabel));
        return checkState(ticketIdLabel);
    }

    public boolean isWorkGroupName() {
        log.info("Ticket lie in WorkGroup :" + readText(workGroupName));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Ticket lie in WorkGroup :" + readText(workGroupName));
        return checkState(workGroupName);
    }

    public String getWorkGroupName() {
        return readText(workGroupName);
    }

    public boolean isPrioritylabel() {
        log.info("Is Priority field Available :" + checkState(prioritylabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Priority field Available :" + checkState(prioritylabel));
        return checkState(prioritylabel);
    }

    public boolean isStateLabel() {
        log.info("Is State field Available :" + checkState(stateLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is State field Available :" + checkState(stateLabel));
        return checkState(stateLabel);
    }

    public boolean isCreationdateLabel() {
        log.info("Is Creation Date field Available :" + checkState(creationdateLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Creation Date field Available :" + checkState(creationdateLabel));
        return checkState(creationdateLabel);
    }

    public boolean isCreatedbyLabel() {
        printInfoLog("Is Created By field Available :" + checkState(createdbyLabel));
        return checkState(createdbyLabel);
    }

    public boolean isQueueLabel() {
        printInfoLog("Is Queue field Available :" + checkState(queueLabel));
        return checkState(queueLabel);
    }

    public boolean isIssueLabel() {
        printInfoLog("Is Issue Label field Available :" + checkState(prioritylabel));
        return checkState(issueLabel);
    }

    public boolean isIssueTypeLabel() {
        printInfoLog("Is Priority field Available :" + checkState(prioritylabel));
        return checkState(issueTypeLabel);
    }

    public boolean isSubTypeLabel() {
        printInfoLog("Is Issue Type field Available :" + checkState(issueTypeLabel));
        return checkState(subTypeLabel);
    }

    public boolean isSubSubTypeLabel() {
        printInfoLog("Is Issue Sub Sub Type field Available :" + checkState(subSubTypeLabel));
        return checkState(subSubTypeLabel);
    }

    public boolean isCodeLabel() {
        printInfoLog("Is Code field Available :" + checkState(codeLabel));
        return checkState(codeLabel);
    }

    public String getTicketIdvalue() {
        printInfoLog("Ticket Id: " + readText(ticketIdvalue));
        return readText(ticketIdvalue);
    }

    public String getWorkgroupSLA() {
        return readText(workgroupSLA);
    }

    public String getPriorityValue() {
        return readText(priorityValue);
    }

    public String getStatevalue() {
        return readText(statevalue);
    }

    public String getCreationdatevalue() {
        return readText(creationdatevalue);
    }

    public String getqueueValue() {
        return readText(queueValue);
    }

    public String getIssueValue() {
        return readText(issueValue);
    }

    public String getIssueTypeValue() {
        return readText(issueTypeValue);
    }

    public String getSubTypeValue() {
        return readText(subTypeValue);
    }

    public String getsubSubTypeValue() {
        return readText(subSubTypeValue);
    }

    public String getCodeValue() {
        return readText(codeValue);
    }

    public String getAssignedto() {
        return readText(assignedto);
    }

    public void clickCheckbox() {
        log.info("Selecting Ticket");
        click(checkBox);
    }

    public void clickAssigntoAgent() {
        log.info("Clicking on Assign to Agent");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Assign to Agent Button");
        click(assignToagentBtn);
    }

    public boolean isAssignToAgent() {
        log.info("Is Assign to agent button: " + checkState(assignToagentBtn));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validate Assign to Agent Button Available");
        return checkState(assignToagentBtn);
    }

    public void clickTransfertoQueue() {
        log.info("Clicking on Transfer to Queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Transfer to Queue Button");
        click(transfertoQueueBtn);
    }

    public boolean isTransferToQueue() {
        log.info("Is Transfer to Queue button: " + checkState(transfertoQueueBtn));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validate Transfer to Queue Button Available");
        return checkState(transfertoQueueBtn);
    }

    public boolean checkOpenTicketStateType() {
        log.info("Checking Open Ticket State Type Select");
        return checkState(openTicketType);
    }


    private boolean checkClosedTicketstateType() {
        log.info("Checking Closed Ticket State Type Select");
        return checkState(closedTicketType);
    }


    public void changeTicketTypeToClosed() {
        printInfoLog("Switch Ticket State Type to closed");
        click(selectTicketType);
        click(closedTicketType);
    }

    public void changeTicketTypeToOpen() {
        log.info("Switch Ticket State Type to Open");
        click(selectTicketType);
        click(openTicketType);
    }


    public void viewTicket() {
        log.info("View Ticket: " + getTicketIdvalue());
        click(stateLabel);
    }

    public boolean noTicketFound() {
        log.info("No ticket found");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Is No Ticket Found :" + isElementVisible(noResultFound));
        return isElementVisible(noResultFound);
    }


    public FilterTabPOM clickFilter() {
        log.info("Selecting Filter");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting Filter");
        click(selectFilterBtn);
        return new FilterTabPOM(driver);
    }

    public void resetFilter() {
        log.info("Removing Filter");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Removing Filter");
        click(resetFilterButton);
        waitTillLoaderGetsRemoved();
    }

    public boolean isResetFilter() {
        printInfoLog("Is Removing Filter Button Available");
        try {
            return checkState(resetFilterButton);
        }catch (NoSuchElementException | TimeoutException e){
            return false;
        }
    }

    public boolean validateQueueFilter(String text) {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating Queue Filter");
        boolean answer = false;
        for (int i = 1; i <= getListSize(); i++) {
            By queue = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//ul/li[7]/span[2]");
            printInfoLog(readText(queue).trim() + " : " + text + " :" + readText(queue).trim().equalsIgnoreCase(text));
            answer = readText(queue).trim().equalsIgnoreCase(text);
        }
        return answer;
    }

    public void ClickReopenButton() {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Reopen Button");
        click(reOpenBtn);
    }

    public void addReopenComment(String comment) {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Add Reopen Comment: " + comment);
        writeText(reOpenBox, comment);
        clickOutside();
    }

    public void submitReopenReq() {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Submit Button");
        click(submitReopenComment);
    }

    public void closedReopenBox() {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on closing reopen comment box Button");
        click(closeReopenCommentBox);
    }

    public boolean isReopenBtn() {
        log.info("Is Reopen Button Available: " + checkState(reOpenBtn));
        return checkState(reOpenBtn);
    }

    public boolean CheckAssignedAUUID(String auuid) {
        log.info("Ticket Validated and Assigned to Agent AUUID: " + auuid);
        By agentAUUID = By.xpath("//span[contains(text(),'" + auuid + "')]");
        return checkState(agentAUUID);
    }

    public By getEscalationSymbol() {
        log.info("Getting Escalation level");
        return allTicket;
    }

    public String getAssigneeAUUID() {
        try {
            log.info("Ticket Assignee to :" + readText(assigneeAUUID));
            return readText(assigneeAUUID);
        } catch (Exception e) {
            e.printStackTrace();
            return "Not Assigned";
        }
    }

    public boolean isNegativeSLA() {
        try {
            log.info("Checking red dot symbol for negative SLA: " + checkState(redDot));
            ExtentTestManager.getTest().log(LogStatus.INFO, "Checking red dot symbol for negative SLA: " + checkState(redDot));
            return checkState(redDot);
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public boolean isPositiveSLA() {
        try {
            log.info("Checking green dot symbol for positive SLA: " + checkState(greenDot));
            ExtentTestManager.getTest().log(LogStatus.INFO, "Checking green dot symbol for positive SLA: " + checkState(greenDot));
            return checkState(greenDot);
        } catch (TimeoutException e) {
            log.info(e.fillInStackTrace());
            return false;
        }
    }

    public List<String> getALLTicketId() {
        List<WebElement> list = returnListOfElement(allTicket);
        List<String> ticketList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By ticket = By.xpath("//div[@class=\"table-card ng-star-inserted\"][" + i + "]//ul[1]//li[1]//span[2]");
            ticketList.add(readText(ticket).trim());
            ExtentTestManager.getTest().log(LogStatus.INFO, "Ticket Id: " + readText(ticket).trim());
        }
        return ticketList;
    }

    public int getListSize() {
        List<WebElement> list = returnListOfElement(allTicket);
        log.info("Size: " + list.size());
        return list.size();
    }

    public String getSymbol(int i) {
        By ticket = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//ul[1]//li[1]//span[2]");
        By symbol = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//span[@class=\"escalation\"]");
        log.info(readText(symbol) + ": Escalation symbol found on ticket Id: " + readText(ticket).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, readText(symbol) + ": Escalation symbol found on ticket Id: " + readText(ticket).trim());
        return readText(symbol).trim();
    }

    public void clickTicketOption() {
        log.info("Click on Ticket Icon to get list of option");
        click(searchOptionBtn);
    }

    public List<String> getListOfSearchOption() {
        log.info("Getting Search Option");
        List<WebElement> list = returnListOfElement(allSearchOption);
        List<String> searchOption = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By search = By.xpath("//ul[@class='ng-star-inserted']//li[" + i + "]");
            log.info("Options Available : " + readText(search));
            ExtentTestManager.getTest().log(LogStatus.INFO, "Options Available : " + readText(search));
            searchOption.add(readText(search).trim());
        }
        return searchOption;
    }

    public String getMSISDN(){
        printInfoLog("Reading MSISDN: "+readText(msisdn));
        return readText(msisdn);
    }


}
