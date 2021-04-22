package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.PriorityDataBean;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.FilterTabElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class FilterTabPage extends BasePage {

    FilterTabElements tabElements;

    public FilterTabPage(WebDriver driver) {
        super(driver);
        tabElements = PageFactory.initElements(driver, FilterTabElements.class);
    }

    public void clickQueueFilter() {
        click(tabElements.openQueueList);
    }

    public void scrollToQueueFilter() throws InterruptedException {
        scrollToViewElement(tabElements.showQueueFilter);
    }

    public void clickLast7DaysFilter() {
        commonLib.info("Clicking on filter by created date - Last 7 days");
        click(tabElements.last7DaysCD);
    }

    public void clickLast30DaysFilter() {
        commonLib.info("Clicking on filter by created date - Last 30 days");
        click(tabElements.last30DaysCD);
    }


    public void selectQueueByName(String queueName) throws InterruptedException {
        commonLib.info("Select Queue Filter Name: " + queueName);
        By queue = By.xpath("//mat-option//span[contains(text(),'" + queueName + "')]");
        scrollToViewElement(queue);
        click(queue);
    }

    public void clickApplyFilter() {
        commonLib.info("Clicking on APPLY Filter Button");
        click(tabElements.applyFilter);
        waitTillLoaderGetsRemoved();
    }

    public void clickOutsideFilter() {
        clickOutside();
    }

    public void clickUnAssignedFilter() throws InterruptedException {
        commonLib.info("Apply Filter By Ticket Assignee");
        scrollToViewElement(tabElements.unAssigned);
        click(tabElements.unAssigned);
    }

    public void openEscalationFilter() throws InterruptedException {
        commonLib.info("Apply filter by ticket escalation level");
        scrollToViewElement(tabElements.openEscalationFilter);
        Thread.sleep(1000);
        click(tabElements.openEscalationFilter);
    }

    public void selectAllLevel1() {
        commonLib.info("Selecting escalation level 1");
        try {
            List<WebElement> level1 = returnListOfElement(tabElements.level1Escalation);
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
        commonLib.info("Selecting escalation level 2");
        try {
            List<WebElement> level1 = returnListOfElement(tabElements.level2Escalation);
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
        commonLib.info("Selecting escalation level 3");
        try {
            List<WebElement> level1 = returnListOfElement(tabElements.level3Escalation);
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
        commonLib.pass("Is filter by created date available :" + checkState(tabElements.filterCreatedByLabel));
        return checkState(tabElements.filterCreatedByLabel);
    }

    public boolean isClosureByFilter() {
        commonLib.info("Is filter by Closure date available :" + checkState(tabElements.filterClosureByLabel));
        return checkState(tabElements.filterClosureByLabel);
    }

    public boolean isSlaDueDateFilter() {
        commonLib.pass("Is filter by SLA due date available :" + checkState(tabElements.sLADueDateLabel));
        return checkState(tabElements.sLADueDateLabel);
    }

    public boolean isCategoryFilter() {
        commonLib.pass("Is filter by issue category available :" + checkState(tabElements.categoryLabel));
        return checkState(tabElements.categoryLabel);
    }

    public boolean isQueueFilter() {
        try {
            commonLib.pass("Is filter by Queue available :" + checkState(tabElements.queueLabel));
            return checkState(tabElements.queueLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isTicketByAssigneeFilter() {
        try {
            commonLib.pass("Is filter by ticket assignee name available :" + checkState(tabElements.ticketAssigneeLabel));
            return checkState(tabElements.ticketAssigneeLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEscalatedLevelFilter() {
        try {
            commonLib.pass("Is filter by ticket escalation level available :" + checkState(tabElements.escalatedLevelLabel));
            return checkState(tabElements.escalatedLevelLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isStateFilter() {
        try {
            commonLib.pass("Is filter by ticket state available :" + checkState(tabElements.stateLabel));
            return checkState(tabElements.stateLabel);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPriorityFilter() {
        try {
            commonLib.pass("Is filter by ticket priority available :" + checkState(tabElements.priorityLabel));
            return checkState(tabElements.priorityLabel);
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
                commonLib.info("Is filter by state name " + state.getTicketStateName() + " available: " + checkState(check));
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
        boolean flag = true;
        for (TicketStateDataBean state : open) {
            By check = By.xpath("//span[@class='mat-checkbox-label'][contains(text(),'" + state.getTicketStateName() + "')]");
            try {
                log.info("Filter by state name " + state.getTicketStateName() + " is: " + checkState(check));
                commonLib.info("Is filter by state name " + state.getTicketStateName() + " available: " + checkState(check));
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
        boolean flag = true;
        for (PriorityDataBean state : priorityList) {
            By check = By.xpath("//span[@class='mat-checkbox-label'][contains(text(),'" + state.getTicketPriority() + "')]");
            try {
                commonLib.pass("Is filter by state name " + state.getTicketPriority() + " available: " + checkState(check));
            } catch (TimeoutException | NoSuchElementException e) {
                UtilsMethods.printFailLog("Priority does not mapped Correctly(Check Config): '" + state.getTicketPriority() + "' " + e.fillInStackTrace());
                flag = false;
            }
        }
        return flag;
    }

    public void clickCloseFilter() {
        try {
            commonLib.info("Closing Filter Tab");
            click(tabElements.closeFilter);
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.info("Close Filter Button does not display");
        }
    }

    public void applyFilterByCategoryCode(String code) throws InterruptedException {
        commonLib.info("Clicking Code Field");
        scrollToViewElement(tabElements.byCode);
        click(tabElements.byCode);
        commonLib.info("Searching category code: " + code);
        writeText(tabElements.searchBox, code);
        waitTillLoaderGetsRemoved();
        By selectCode = By.xpath("//span[@class='mat-option-text'][contains(text(),'" + code + "')]");
        click(selectCode);
        commonLib.info("Category Code Selected");
        waitTillLoaderGetsRemoved();
    }

    /*
    This Method is used to verify if soruce filter is present or not
     */
    public boolean isSourceFilterPresent() {
        boolean result;
        result = checkState(tabElements.sourceLabel);
        commonLib.pass("Is Source Filter available :" + result);
        return result;
    }

    /*
    This Method is used to select source filter value
     */
    public void selectSourceFilterValue() throws InterruptedException {
        if (isVisible(tabElements.sourceFilterLabel)) {
            scrollToViewElement(tabElements.sourceFilterLabel);
            click(tabElements.sourceFilterLabel);
            click(tabElements.selectCustomerService);
            waitTillLoaderGetsRemoved();
            clickApplyFilter();
            clearFilterDashbaord();
        }
    }

    public void clearFilterDashbaord() {
        if (isVisible(tabElements.clearFilterButtonDashboard)) {
            click(tabElements.clearFilterButtonDashboard);
        }
    }

    public Boolean isApplyFilterBtnEnabled() {
        selUtils.clickElementAfterScroll(tabElements.applyFilter);
        return checkState(tabElements.applyFilter);
    }
}
