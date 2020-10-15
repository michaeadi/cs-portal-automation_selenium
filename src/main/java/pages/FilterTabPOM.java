package pages;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.PriorityDataBean;
import Utils.DataProviders.TicketStateDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class FilterTabPOM extends BasePage {

    By applyFilter = By.xpath("//button[@class=\"filter-button mat-button\"]");
    By closeFilter = By.xpath("//span[@class='close-button']");

    //Filter By Created date
    By filterCreatedByLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter By Created Date')]");
    By last7DaysCD = By.xpath("//mat-radio-group[@formcontrolname='days']/mat-radio-button[1]/label/div[2]/span[2]");
    By last30DaysCD = By.xpath("//mat-radio-group[@formcontrolname='days']/mat-radio-button[2]/label/div[2]/span[2]");
    By dateDurationCD = By.xpath("//mat-radio-group[@formcontrolname='days']/mat-radio-button[3]/label/div[2]/span[2]");
    By startDateCD = By.xpath("//input[@formcontrolname=\"startDate\"]");
    By endDateCD = By.xpath("//input[@formcontrolname=\"endDate\"]");

    //Filter By SLA Date
    By sLADueDateLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter By SLA Due Date')]");
    By last7DaysSD = By.xpath("//mat-radio-group[@formcontrolname='slaDays']/mat-radio-button[1]/label/div[2]/span[2]");
    By last30DaysSD = By.xpath("//mat-radio-group[@formcontrolname='slaDays']/mat-radio-button[2]/label/div[2]/span[2]");
    By dateDurationSD = By.xpath("//mat-radio-group[@formcontrolname='slaDays']/mat-radio-button[3]/label/div[2]/span[2]");
    By slaStartDate = By.xpath("//input[@formcontrolname=\"slaStartDate\"]");
    By slaEndDate = By.xpath("//input[@formcontrolname=\"slaEndDate\"]");

    //Filter By Category
    By categoryLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter By Category')]");
    By byCode = By.xpath("//div[@formarrayname=\"category\"]//div[2]//div[@class=\"mat-select-value\"]");
    By byIssue = By.xpath("//label[@class='mat-form-field-label ng-tns-c6-98 mat-empty mat-form-field-empty ng-star-inserted']//mat-label[@class='ng-star-inserted'][contains(text(),'Issue')]");
    By byIssueType = By.xpath("//mat-label[contains(text(),'Issue Type')]");
    By byIssueSubType = By.xpath("//mat-label[contains(text(),'Issue sub type')]");
    By byIssueSubSubType = By.xpath("//mat-label[contains(text(),'Issue sub sub type')]");
    By searchBox = By.xpath("//input[@placeholder=\"Search\"]");

    //Filter By Queue
    By queueLabel = By.xpath("//span[contains(text(),'Filter By Queue')]");
    By showQueueFilter = By.xpath("//mat-label[contains(text(),'Select Queue')]");
    By openQueueList = By.xpath("//mat-select[@formcontrolname=\"selectedFilterQueue\"]");

    //Filter By Ticket Assignee
    By ticketAssigneeLabel = By.xpath("//span[contains(text(),'Filter By Ticket Assignee')]");
    By unAssigned = By.xpath("//span[contains(text(),'Unassigned')]");
    By assigned = By.xpath("//span[contains(text(),'Assigned')]");
    By assigneeList = By.xpath("//mat-select[@formcontrolname=\"selectedAssigneeName\"]");

    //Filter By Ticket Escalated level
    By escalatedLevelLabel = By.xpath("//span[contains(text(),'Filter By Escalated Level')]");
    By openEscalationFilter = By.xpath("//*[@formcontrolname=\"selectedEscalatedLevel\"]/div/div[2]");
    By level1Escalation = By.xpath("//span[contains(text(),' L1 ')]");
    By level2Escalation = By.xpath("//span[contains(text(),' L2 ')]");
    By level3Escalation = By.xpath("//span[contains(text(),' L3 ')]");

    //Filter By State
    By stateLabel = By.xpath("//span[contains(text(),'Filter By State')]");

    //Filter By Priority
    By priorityLabel = By.xpath("//span[contains(text(),'By Priority')]");


    public FilterTabPOM(WebDriver driver) {
        super(driver);
    }

    public void clickQueueFilter() {
        click(openQueueList);
    }

    public void scrollToQueueFilter() throws InterruptedException {
        scrollToViewElement(showQueueFilter);
    }


    public void selectQueueByName(String queueName) throws InterruptedException {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Select Queue Filter Name: " + queueName);
        By queue = By.xpath("//mat-option//span[contains(text(),'" + queueName + "')]");
        scrollToViewElement(queue);
        click(queue);
    }

    public void clickApplyFilter() {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on APPLY Filter Button");
        click(applyFilter);
    }

    public void clickOutsideFilter() {
        clickOutside();
    }

    public void clickUnAssignedFilter() throws InterruptedException {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Apply Filter By Ticket Assignee");
        scrollToViewElement(unAssigned);
        click(unAssigned);
    }

    public void OpenEscalationFilter() throws InterruptedException {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Apply filter by ticket escalation level");
        scrollToViewElement(openEscalationFilter);
        Thread.sleep(1000);
        click(openEscalationFilter);
    }

    public void selectAllLevel1() {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting escalation level 1");
        try {
            List<WebElement> level1 = driver.findElements(level1Escalation);
            for (WebElement level : level1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", level);
                Thread.sleep(500);
                level.click();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectAllLevel2() {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting escalation level 2");
        try {
            List<WebElement> level1 = driver.findElements(level2Escalation);
            for (WebElement level : level1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", level);
                Thread.sleep(500);
                level.click();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectAllLevel3() {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting escalation level 3");
        try {
            List<WebElement> level1 = driver.findElements(level3Escalation);
            for (WebElement level : level1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", level);
                Thread.sleep(500);
                level.click();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isCreatedByFilter() {
        log.info("Is filter by created date available :" + checkState(filterCreatedByLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by created date available :" + checkState(filterCreatedByLabel));
        return checkState(filterCreatedByLabel);
    }

    public boolean isSlaDueDateFilter() {
        log.info("Is filter by SLA due date available :" + checkState(sLADueDateLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by SLA due date available :" + checkState(sLADueDateLabel));
        return checkState(sLADueDateLabel);
    }

    public boolean isCategoryFilter() {
        log.info("Is filter by issue category available :" + checkState(categoryLabel));
        ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by issue category available :" + checkState(categoryLabel));
        return checkState(categoryLabel);
    }

    public boolean isQueueFilter() {
        try {
            log.info("Is filter by Queue available :" + checkState(queueLabel));
            ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by Queue available :" + checkState(queueLabel));
            return checkState(queueLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isTicketByAssigneeFilter() {
        try {
            log.info("Is filter by ticket assignee name available :" + checkState(ticketAssigneeLabel));
            ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by ticket assignee name available :" + checkState(ticketAssigneeLabel));
            return checkState(ticketAssigneeLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEscalatedLevelFilter() {
        try {
            log.info("Is filter by ticket escalation level available :" + checkState(escalatedLevelLabel));
            ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by ticket escalation level available :" + checkState(escalatedLevelLabel));
            return checkState(escalatedLevelLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isStateFilter() {
        try {
            log.info("Is filter by ticket state available :" + checkState(stateLabel));
            ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by ticket state available :" + checkState(stateLabel));
            return checkState(stateLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPriorityFilter() {
        try {
            log.info("Is filter by ticket priority available :" + checkState(priorityLabel));
            ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by ticket priority available :" + checkState(priorityLabel));
            return checkState(priorityLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean validateOpenStateFilter() {
        DataProviders d = new DataProviders();
        List<TicketStateDataBean> open = d.getState("open");
        try {
            for (TicketStateDataBean state : open) {
                By check = By.xpath("//span[@class='mat-checkbox-label'][contains(text(),'" + state.getTicketStateName() + "')]");
                log.info("Filter by state name " + state.getTicketStateName() + " is: " + checkState(check));
                ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by state name " + state.getTicketStateName() + " available: " + checkState(check));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean validatePriorityFilter() {
        DataProviders d = new DataProviders();
        List<PriorityDataBean> priorityList = d.getPriority();
        try {
            for (PriorityDataBean state : priorityList) {
                By check = By.xpath("//span[@class='mat-checkbox-label'][contains(text(),'" + state.getTicketPriority() + "')]");
                log.info("Filter by state name " + state.getTicketPriority() + " is: " + checkState(check));
                ExtentTestManager.getTest().log(LogStatus.PASS, "Is filter by state name " + state.getTicketPriority() + " available: " + checkState(check));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clickCloseFilter() {
        log.info("Closing Filter Tab");
        click(closeFilter);
    }

    public void applyFilterByCategoryCode(String code) throws InterruptedException {
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Code Field");
        scrollToViewElement(byCode);
        click(byCode);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Searching category code: " + code);
        writeText(searchBox, code);
        waitTillLoaderGetsRemoved();
        By selectCode = By.xpath("//span[@class='mat-option-text'][contains(text(),'" + code + "')]");
        click(selectCode);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Category Code Selected");
        waitTillLoaderGetsRemoved();
    }


}
