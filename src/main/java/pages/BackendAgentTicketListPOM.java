package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class BackendAgentTicketListPOM extends BasePage {

    By searchTicketBox = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-ticket/mat-sidenav-container/mat-sidenav-content/div/div[1]/div[2]/app-ticket-search-box/span/input");
    By searchTicketBtn = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-ticket/mat-sidenav-container/mat-sidenav-content/div/div[1]/div[2]/app-ticket-search-box/span/button/img");
    By ticketIdLabel = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-ticket/mat-sidenav-container/mat-sidenav-content/div/app-ticket-details/div/div[1]/div[1]/div/table/tbody/tr[1]/td[1]/div[2]/span[1]/b");
    By ticketIdvalue = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-ticket/mat-sidenav-container/mat-sidenav-content/div/app-ticket-details/div/div[1]/div[1]/div/table/tbody/tr[1]/td[1]/div[2]/span[2]");
    By workGroupName = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-ticket/mat-sidenav-container/mat-sidenav-content/div/app-ticket-details/div/div[1]/div[1]/div/table/tbody/tr[1]/td[2]/span[1]");
    By workgroupSLA = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[2]//span[2]");
    By prioritylabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[3]//span[1]//b[1]");
    By priorityValue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[3]//span[2]");
    By stateLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[4]//span[1]//b[1]");
    By statevalue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[4]//span[2]");
    By creationdateLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[5]//span[1]//b[1]");
    By creationdatevalue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[5]//span[2]");
    By createdbyLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[6]//span[1]//b[1]");
    By createdbyvalue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[6]//span[2]");
    By queueLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[7]//span[1]//b[1]");
    By queueValue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[1]//td[7]//span[2]");
    By issueLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[1]//span[1]//b[1]");
    By issueValue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[1]//span[2]");
    By issueTypeLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[2]//span[1]//b[1]");
    By issueTypeValue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[2]//span[2]");
    By subTypeLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[3]//span[1]//b[1]");
    By subTypeValue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[3]//span[2]");
    By subSubTypeLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[4]//span[1]//b[1]");
    By subSubTypeValue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[4]//span[2]");
    By codeLabel = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[5]//span[1]//b[1]");
    By codeValue = By.xpath("//body//div//div//div//div//div//div//div//div//div[1]//div[1]//table[1]//tbody[1]//tr[2]//td[5]//span[2]");
    By selectFilterBtn = By.xpath("//span[contains(text(),'Select Filter')]");
    By pageRefreshBtn = By.xpath("//span[contains(text(),'Refresh ')]");
    By noResultFound = By.xpath("//html//body//app-root//app-dashboard//div//app-admin-panel//div//div//app-sidenav-bar//mat-sidenav-container//mat-sidenav-content//div//app-service-request//div//app-backend-ticket//mat-sidenav-container//mat-sidenav-content//div//app-ticket-details//div//div//mat-error//img");


    public BackendAgentTicketListPOM(WebDriver driver) {
        super(driver);
    }

    public void writeTicketId(String ticketId) {
        log.info("Search Ticket Id: " + ticketId);
        writeText(searchTicketBox, ticketId);
    }

    public void clickedSearchBtn() {
        log.info("Clicking on Search Button");
        click(searchTicketBtn);
    }


    public boolean isTicketIdLabel() {
        log.info("Is Ticket Id field Available :" + checkState(ticketIdLabel));
        return checkState(ticketIdLabel);
    }


    public boolean isWorkGroupName() {
        log.info("Ticket lie in WorkGroup :" + readText(workGroupName));
        return checkState(workGroupName);
    }


    public boolean isPrioritylabel() {
        log.info("Is Priority field Available :" + checkState(prioritylabel));
        return checkState(prioritylabel);
    }


    public boolean isStateLabel() {
        log.info("Is Priority field Available :" + checkState(stateLabel));
        return checkState(stateLabel);
    }


    public boolean isCreationdateLabel() {
        log.info("Is Priority field Available :" + checkState(creationdateLabel));
        return checkState(creationdateLabel);
    }

    public boolean isCreatedbyLabel() {
        log.info("Is Priority field Available :" + checkState(createdbyLabel));
        return checkState(createdbyLabel);
    }

    public boolean isQueueLabel() {
        log.info("Is Priority field Available :" + checkState(queueLabel));
        return checkState(queueLabel);
    }

    public boolean isIssueLabel() {
        log.info("Is Issue field Available :" + checkState(issueLabel));
        return checkState(issueLabel);
    }

    public boolean isIssueTypeLabel() {
        log.info("Is Issue Type field Available :" + checkState(issueTypeLabel));
        return checkState(issueTypeLabel);
    }

    public boolean isSubTypeLabel() {
        log.info("Is Issue Sub Type field Available :" + checkState(subTypeLabel));
        return checkState(subTypeLabel);
    }

    public boolean isSubSubTypeLabel() {
        log.info("Is Issue Sub Sub Type field Available :" + checkState(subSubTypeLabel));
        return checkState(subSubTypeLabel);
    }


    public boolean isCodeLabel() {
        log.info("Is Code field Available :" + checkState(codeLabel));
        return checkState(codeLabel);
    }


    public String getTicketIdvalue() {
        log.info("Ticket Id: " + readText(ticketIdvalue));
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


    public void viewTicket() {
        log.info("View Ticket: " + getTicketIdvalue());
        click(stateLabel);
    }

    public void clickFilter() {
        log.info("Selecting Filter");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting Filter");
        click(selectFilterBtn);
    }

    public void clickSearchBtn() {
        log.info("Clicking on Search Button");
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.elementToBeClickable(searchTicketBtn));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Search Button");
        click(searchTicketBtn);
    }

    public boolean noTicketFound() {
        log.info("No ticket found");
        try {
            boolean status = checkState(noResultFound);
            ExtentTestManager.getTest().log(LogStatus.PASS, "No Ticket Found");
            return status;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return false;
    }
}
