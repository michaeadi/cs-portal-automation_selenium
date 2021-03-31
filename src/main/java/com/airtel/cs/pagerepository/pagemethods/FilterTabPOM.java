package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.PriorityDataBean;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.commonutils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;

import java.util.List;

@Log4j2
public class FilterTabPOM extends BasePage {

    By applyFilter = By.xpath("//button[@class=\"filter-button mat-button\"]");
    By closeFilter = By.xpath("//span[@class='close-button']");

    //Filter By Created date
    By filterCreatedByLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter By Created Date')]");
    By filterClosureByLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter by Closure Date')]");
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
    By openQueueList = By.xpath("//app-custom-mat-select[@formcontrolname=\"selectedFilterQueue\"]");

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

    public void clickLast7DaysFilter() {
        UtilsMethods.printInfoLog("Clicking on filter by created date - Last 7 days");
        click(last7DaysCD);
    }

    public void clickLast30DaysFilter() {
        UtilsMethods.printInfoLog("Clicking on filter by created date - Last 30 days");
        click(last30DaysCD);
    }


    public void selectQueueByName(String queueName) throws InterruptedException {
        UtilsMethods.printInfoLog("Select Queue Filter Name: " + queueName);
        By queue = By.xpath("//mat-option//span[contains(text(),'" + queueName + "')]");
        scrollToViewElement(queue);
        click(queue);
    }

    public void clickApplyFilter() {
        UtilsMethods.printInfoLog("Clicking on APPLY Filter Button");
        click(applyFilter);
    }

    public void clickOutsideFilter() {
        clickOutside();
    }

    public void clickUnAssignedFilter() throws InterruptedException {
        UtilsMethods.printInfoLog("Apply Filter By Ticket Assignee");
        scrollToViewElement(unAssigned);
        click(unAssigned);
    }

    public void OpenEscalationFilter() throws InterruptedException {
        UtilsMethods.printInfoLog("Apply filter by ticket escalation level");
        scrollToViewElement(openEscalationFilter);
        Thread.sleep(1000);
        click(openEscalationFilter);
    }

    public void selectAllLevel1() {
        UtilsMethods.printInfoLog("Selecting escalation level 1");
        try {
            List<WebElement> level1 = returnListOfElement(level1Escalation);
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
        UtilsMethods.printInfoLog("Selecting escalation level 2");
        try {
            List<WebElement> level1 = returnListOfElement(level2Escalation);
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
        UtilsMethods.printInfoLog("Selecting escalation level 3");
        try {
            List<WebElement> level1 = returnListOfElement(level3Escalation);
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
        UtilsMethods.printPassLog("Is filter by created date available :" + checkState(filterCreatedByLabel));
        return checkState(filterCreatedByLabel);
    }

    public boolean isClosureByFilter() {
        UtilsMethods.printInfoLog("Is filter by Closure date available :" + checkState(filterClosureByLabel));
        return checkState(filterClosureByLabel);
    }

    public boolean isSlaDueDateFilter() {
        UtilsMethods.printPassLog("Is filter by SLA due date available :" + checkState(sLADueDateLabel));
        return checkState(sLADueDateLabel);
    }

    public boolean isCategoryFilter() {
        UtilsMethods.printPassLog("Is filter by issue category available :" + checkState(categoryLabel));
        return checkState(categoryLabel);
    }

    public boolean isQueueFilter() {
        try {
            UtilsMethods.printPassLog("Is filter by Queue available :" + checkState(queueLabel));
            return checkState(queueLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isTicketByAssigneeFilter() {
        try {
            UtilsMethods.printPassLog("Is filter by ticket assignee name available :" + checkState(ticketAssigneeLabel));
            return checkState(ticketAssigneeLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEscalatedLevelFilter() {
        try {
            UtilsMethods.printPassLog("Is filter by ticket escalation level available :" + checkState(escalatedLevelLabel));
            return checkState(escalatedLevelLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isStateFilter() {
        try {
            UtilsMethods.printPassLog("Is filter by ticket state available :" + checkState(stateLabel));
            return checkState(stateLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPriorityFilter() {
        try {
            UtilsMethods.printPassLog("Is filter by ticket priority available :" + checkState(priorityLabel));
            return checkState(priorityLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean validateOpenStateFilter() {
        DataProviders d = new DataProviders();
        List<TicketStateDataBean> open = d.getState("open");
        Boolean flag = true;
        for (TicketStateDataBean state : open) {
            By check = By.xpath("//span[@class='mat-checkbox-label'][contains(text(),'" + state.getTicketStateName() + "')]");
            try {
                UtilsMethods.printInfoLog("Is filter by state name " + state.getTicketStateName() + " available: " + checkState(check));
            } catch (NoSuchElementException | TimeoutException e) {
                UtilsMethods.printFailLog("State does not mapped Correctly(Check Config): " + state.getTicketStateName() + " " + e.fillInStackTrace());
                flag = false;
            }
        }
        return flag;
    }


    public boolean validateCloseStateFilter() {
        DataProviders d = new DataProviders();
        List<TicketStateDataBean> open = d.getState("Close");
        Boolean flag = true;
        for (TicketStateDataBean state : open) {
            By check = By.xpath("//span[@class='mat-checkbox-label'][contains(text(),'" + state.getTicketStateName() + "')]");
            try {
                log.info("Filter by state name " + state.getTicketStateName() + " is: " + checkState(check));
                UtilsMethods.printInfoLog("Is filter by state name " + state.getTicketStateName() + " available: " + checkState(check));
            } catch (NoSuchElementException | TimeoutException e) {
                UtilsMethods.printFailLog("State does not mapped Correctly(Check Config): '" + state.getTicketStateName() + "' " + e.fillInStackTrace());
                flag = false;
            }
        }
        return flag;
    }


    public boolean validatePriorityFilter() {
        DataProviders d = new DataProviders();
        List<PriorityDataBean> priorityList = d.getPriority();
        Boolean flag = true;
        for (PriorityDataBean state : priorityList) {
            By check = By.xpath("//span[@class='mat-checkbox-label'][contains(text(),'" + state.getTicketPriority() + "')]");
            try {
                UtilsMethods.printPassLog("Is filter by state name " + state.getTicketPriority() + " available: " + checkState(check));
            } catch (TimeoutException | NoSuchElementException e) {
                UtilsMethods.printFailLog("Priority does not mapped Correctly(Check Config): '" + state.getTicketPriority() + "' " + e.fillInStackTrace());
                flag = false;
            }
        }
        return flag;
    }

    public void clickCloseFilter() {
        try {
            UtilsMethods.printInfoLog("Closing Filter Tab");
            click(closeFilter);
        } catch (NoSuchElementException | TimeoutException e) {
            UtilsMethods.printInfoLog("Close Filter Button does not display");
        }
    }

    public void applyFilterByCategoryCode(String code) throws InterruptedException {
        UtilsMethods.printInfoLog("Clicking Code Field");
        scrollToViewElement(byCode);
        click(byCode);
        UtilsMethods.printInfoLog("Searching category code: " + code);
        writeText(searchBox, code);
        waitTillLoaderGetsRemoved();
        By selectCode = By.xpath("//span[@class='mat-option-text'][contains(text(),'" + code + "')]");
        click(selectCode);
        UtilsMethods.printInfoLog("Category Code Selected");
        waitTillLoaderGetsRemoved();
    }


}
