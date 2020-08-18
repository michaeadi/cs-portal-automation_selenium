package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class supervisorTicketListPagePOM extends BasePage {

    By searchTicketBox = By.xpath("//span[@class='search-box small-search']//input");
    By searchTicketBtn = By.xpath("//html//body//app-root//app-dashboard//div//app-admin-panel//div//div//app-sidenav-bar//mat-sidenav-container//mat-sidenav-content//div//app-service-request//div//app-backend-supervisor//mat-sidenav-container//mat-sidenav-content//section//div//div//div//app-ticket-search-box//span//button");
    By ticketIdLabel = By.xpath("//div//div//div//div//div//div//div//div//div//ul[1]//li[1]//span[1]");
    By ticketIdvalue = By.xpath("//ul[1]//li[1]//span[2]");
    By workGroupName = By.xpath("//body/app-root/app-dashboard/div/app-admin-panel/div/div/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-supervisor/mat-sidenav-container/mat-sidenav-content/section/div/div/div/div/div/ul[1]/li[2]/span[1]");
    By workgroupSLA = By.xpath("//ul[1]//li[2]//span[2]");
    By prioritylabel = By.xpath("//div//div//div//div//div//div//div//div//div//ul[1]//li[3]//span[1]");
    By priorityValue = By.xpath("//div//div//div//div//div//div//div//div//div//ul[1]//li[3]//span[2]");
    By stateLabel = By.xpath("//div//div//div//div//div//div//div//div//div//ul[1]//li[4]//span[1]");
    By statevalue = By.xpath("//ul[1]//li[4]//span[2]");
    By creationdateLabel = By.xpath("//ul[1]//li[5]//span[1]");
    By creationdatevalue = By.xpath("//ul[1]//li[5]//span[2]");
    By createdbyLabel = By.xpath("//li[6]//span[1]");
    By createdbyvalue = By.xpath("//li[6]//span[2]");
    By queueLabel = By.xpath("//li[7]//span[1]");
    By queueValue = By.xpath("//li[7]//span[2]");
    By listQueueValue = By.xpath("//ul/li[7]/span[2]");
    By issueLabel = By.xpath("//ul[2]//li[1]//span[1]");
    By issueValue = By.xpath("//ul[2]//li[1]//span[2]");
    By issueTypeLabel = By.xpath("//ul[2]//li[2]//span[1]");
    By issueTypeValue = By.xpath("//ul[2]//li[2]//span[2]");
    By subTypeLabel = By.xpath("//ul[2]//li[3]//span[1]");
    By subTypeValue = By.xpath("//ul[2]//li[3]//span[2]");
    By subSubTypeLabel = By.xpath("//ul[2]//li[4]//span[1]");
    By subSubTypeValue = By.xpath("//ul[2]//li[4]//span[2]");
    By codeLabel = By.xpath("//ul[2]//li[5]//span[1]");
    By codeValue = By.xpath("//ul[2]//li[5]//span[2]");
    By assignedto = By.xpath("//body//p//span[1]");
    By checkBox = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-supervisor/mat-sidenav-container/mat-sidenav-content/section/div[2]/div[1]/div/div[1]/input");
    By assignToagentBtn = By.xpath("//li[1]//button[1]");
    By transfertoQueueBtn = By.xpath("//li[2]//button[1]");
    By loggedInQueue = By.xpath("//span[contains(text(),'Login with Ticket Pool')]");
    By selectTicketType = By.xpath("//*[@name=\"state\"]");
    By openTicketType = By.xpath("//span[contains(text(),' Open ')]");
    By closedTicketType = By.xpath("//span[contains(text(),' Closed ')]");
    By selectFilterBtn = By.xpath("//span[contains(text(),'Select Filter')]");
    By pageRefreshBtn = By.xpath("//span[contains(text(),'Refresh ')]");
    By noResultFound = By.xpath("//body//mat-error//p[1]");
    By resetFilterButton = By.xpath("//body/app-root/app-dashboard/div/app-admin-panel/div/div/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-supervisor/mat-sidenav-container/mat-sidenav-content/section/div/div/button[1]");
    By reOpenBtn = By.xpath("//li[1]//button[1]");
    By reOpenBox = By.xpath("//*[@placeholder=\"Leave a comment\"]");
    By submitReopenComment = By.className("sbt-btn");
    By redDot = By.xpath("//span[@class='reddot ng-star-inserted']");
    By greenDot = By.xpath("//span[@class='greendot ng-star-inserted']");
    By assigneeAUUID = By.xpath("//div[@class='service-request']//div[1]//div[1]//div[2]//div[2]//p[1]//span[3]");
    By allTicket = By.xpath("//div[@class=\"table-card ng-star-inserted\"]");
    By searchOptionBtn=By.xpath("//div[@class='options']");
    By allSearchOption=By.xpath("//ul[@class='ng-star-inserted']//li");

    public supervisorTicketListPagePOM(WebDriver driver) {
        super(driver);
    }

    public void writeTicketId(String ticketId) {
        log.info("Search Ticket Id: " + ticketId);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Search Ticket Id: " + ticketId);
        writeText(searchTicketBox, ticketId);
    }

    public void clearInputBox() {
        log.info("Clear Search Box");
//        click(searchTicketBox);
//        clearInputTag(searchTicketBox);
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
        log.info("Is Created By field Available :" + checkState(createdbyLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Created By field Available :" + checkState(createdbyLabel));
        return checkState(createdbyLabel);
    }

    public boolean isQueueLabel() {
        log.info("Is Queue field Available :" + checkState(queueLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Queue field Available :" + checkState(queueLabel));
        return checkState(queueLabel);
    }

    public boolean isIssueLabel() {
        log.info("Is Issue label Field Available :" + checkState(issueLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Issue Label field Available :" + checkState(prioritylabel));
        return checkState(issueLabel);
    }

    public boolean isIssueTypeLabel() {
        log.info("Is Issue Type field Available :" + checkState(issueTypeLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Priority field Available :" + checkState(prioritylabel));
        return checkState(issueTypeLabel);
    }

    public boolean isSubTypeLabel() {
        log.info("Is Issue Sub Type field Available :" + checkState(subTypeLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Issue Type field Available :" + checkState(issueTypeLabel));
        return checkState(subTypeLabel);
    }

    public boolean isSubSubTypeLabel() {
        log.info("Is Issue Sub Sub Type field Available :" + checkState(subSubTypeLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Issue Sub Sub Type field Available :" + checkState(subSubTypeLabel));
        return checkState(subSubTypeLabel);
    }

    public boolean isCodeLabel() {
        log.info("Is Code field Available :" + checkState(codeLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is Code field Available :" + checkState(codeLabel));
        return checkState(codeLabel);
    }

    public String getTicketIdvalue() {
        log.info("Ticket Id: " + readText(ticketIdvalue));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Ticket Id: " + readText(ticketIdvalue));
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
        log.info("Switch Ticket State Type to closed");
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


    public void clickFilter() {
        log.info("Selecting Filter");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting Filter");
        click(selectFilterBtn);
    }

    public void resetFilter() {
        log.info("Removing Filter");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Removing Filter");
        click(resetFilterButton);
    }

    public boolean validateQueueFilter(String text) {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating Queue Filter");
        boolean answer=false;
        for(int i=1;i<getListSize();i++){
            By queue=By.xpath("//div[@class=\"table-card ng-star-inserted\"]["+i+"]//ul/li[7]/span[2]");
            log.info(readText(queue).trim()+" : "+text+" :"+readText(queue).trim().equalsIgnoreCase(text));
            answer=readText(queue).trim().equalsIgnoreCase(text);
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
        List<WebElement> list = driver.findElements(allTicket);
        List<String> ticketList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By ticket = By.xpath("//div[@class=\"table-card ng-star-inserted\"][" + i + "]//ul[1]//li[1]//span[2]");
            ticketList.add(readText(ticket).trim());
            ExtentTestManager.getTest().log(LogStatus.INFO, "Ticket Id: " + readText(ticket).trim());
        }
        return ticketList;
    }

    public int getListSize(){
        List<WebElement> list = driver.findElements(allTicket);
        log.info("Size: "+list.size());
        return list.size();
    }

    public String getSymbol(int i) {
            By ticket = By.xpath("//div[@class=\"table-card ng-star-inserted\"][" + i + "]//ul[1]//li[1]//span[2]");
            By symbol = By.xpath("//div[@class=\"table-card ng-star-inserted\"][" + i + "]//span[@class=\"escalation\"]");
            log.info(readText(symbol)+": Escalation symbol found on ticket Id: " + readText(ticket).trim());
            ExtentTestManager.getTest().log(LogStatus.INFO, readText(symbol)+": Escalation symbol found on ticket Id: " + readText(ticket).trim());
            return readText(symbol).trim();
    }

    public void clickTicketOption(){
        log.info("Click on Ticket Icon to get list of option");
        click(searchOptionBtn);
    }

    public List<String> getListOfSearchOption(){
        log.info("Getting Search Option");
        List<WebElement> list=driver.findElements(allSearchOption);
        List<String> searchOption=new ArrayList<>();
        for(int i=1;i<=list.size();i++){
            By search=By.xpath("//ul[@class='ng-star-inserted']//li["+i+"]");
            log.info("Options Available : "+readText(search));
            searchOption.add(readText(search).trim());
        }
        return searchOption;
    }


}
