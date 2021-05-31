package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.PriorityDataBean;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.pagerepository.pageelements.FilterTabPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class FilterTab extends BasePage {

    FilterTabPage tabElements;
    private static final String SCROLL_SCRIPT = "arguments[0].scrollIntoView(true);";
    private static final String AVAILABLE = " available: ";
    private static final String FILTER_TEXT = "Is filter by state name ";
    private static final String XPATH1 = "//span[@class='mat-checkbox-label'][contains(text(),'";

    public FilterTab(WebDriver driver) {
        super(driver);
        tabElements = PageFactory.initElements(driver, FilterTabPage.class);
    }

    public void clickQueueFilter() {
        clickAndWaitForLoaderToBeRemoved(tabElements.openQueueList);
    }

    public void scrollToQueueFilter() throws InterruptedException {
        scrollToViewElement(tabElements.showQueueFilter);
    }

    public void clickLast7DaysFilter() {
        commonLib.info("Clicking on filter by created date - Last 7 days");
        clickAndWaitForLoaderToBeRemoved(tabElements.last7DaysCD);
    }

    public void clickLast30DaysFilter() {
        commonLib.info("Clicking on filter by created date - Last 30 days");
        clickAndWaitForLoaderToBeRemoved(tabElements.last30DaysCD);
    }


    public void selectQueueByName(String queueName) throws InterruptedException {
        commonLib.info("Select Queue Filter Name: " + queueName);
        By queue = By.xpath("//mat-option//span[contains(text(),'" + queueName + "')]");
        scrollToViewElement(queue);
        clickAndWaitForLoaderToBeRemoved(queue);
    }

    public void clickApplyFilter() {
        commonLib.info("Clicking on APPLY Filter Button");
        clickAndWaitForLoaderToBeRemoved(tabElements.applyFilter);
        waitTillLoaderGetsRemoved();
    }

    public void clickOutsideFilter() {
        clickOutside();
    }

    public void clickUnAssignedFilter() throws InterruptedException {
        commonLib.info("Apply Filter By Ticket Assignee");
        scrollToViewElement(tabElements.unAssigned);
        clickAndWaitForLoaderToBeRemoved(tabElements.unAssigned);
    }

    public void openEscalationFilter() throws InterruptedException {
        commonLib.info("Apply filter by ticket escalation level");
        scrollToViewElement(tabElements.openEscalationFilter);
        Thread.sleep(1000);
        clickAndWaitForLoaderToBeRemoved(tabElements.openEscalationFilter);
    }

    public void selectAllLevel1() {
        commonLib.info("Selecting escalation level 1");
        try {
            List<WebElement> level1 = returnListOfElement(tabElements.level1Escalation);
            for (WebElement level : level1) {
                ((JavascriptExecutor) driver).executeScript(SCROLL_SCRIPT, level);
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
                ((JavascriptExecutor) driver).executeScript(SCROLL_SCRIPT, level);
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
                ((JavascriptExecutor) driver).executeScript(SCROLL_SCRIPT, level);
                Thread.sleep(500);
                level.click();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isCreatedByFilter() {
        final boolean state = isEnabled(tabElements.filterCreatedByLabel);
        commonLib.pass("Is filter by created date available :" + state);
        return state;
    }

    public boolean isClosureByFilter() {
        final boolean state = isEnabled(tabElements.filterClosureByLabel);
        commonLib.info("Is filter by Closure date available :" + state);
        return state;
    }

    public boolean isSlaDueDateFilter() {
        final boolean state = isEnabled(tabElements.sLADueDateLabel);
        commonLib.pass("Is filter by SLA due date available :" + state);
        return state;
    }

    public boolean isCategoryFilter() {
        final boolean state = isEnabled(tabElements.categoryLabel);
        commonLib.pass("Is filter by issue category available :" + state);
        return state;
    }

    public boolean isQueueFilter() {
        try {
            final boolean state = isEnabled(tabElements.queueLabel);
            commonLib.pass("Is filter by Queue available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isTicketByAssigneeFilter() {
        try {
            final boolean state = isEnabled(tabElements.ticketAssigneeLabel);
            commonLib.pass("Is filter by ticket assignee name available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEscalatedLevelFilter() {
        try {
            final boolean state = isEnabled(tabElements.escalatedLevelLabel);
            commonLib.pass("Is filter by ticket escalation level available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isStateFilter() {
        try {
            final boolean state = isEnabled(tabElements.stateLabel);
            commonLib.pass("Is filter by ticket state available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPriorityFilter() {
        try {
            final boolean state = isEnabled(tabElements.priorityLabel);
            commonLib.pass("Is filter by ticket priority available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean validateOpenStateFilter() {
        DataProviders d = new DataProviders();
        List<TicketStateDataBean> open = d.getState("open");
        boolean flag = true;
        for (TicketStateDataBean state : open) {
            final String stateName = state.getTicketStateName();
            By check = By.xpath(XPATH1 + stateName + "')]");
            try {
                commonLib.info(FILTER_TEXT + stateName + AVAILABLE + isEnabled(check));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("State does not mapped Correctly(Check Config): " + stateName + " " + e.fillInStackTrace(), true);
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
            final String stateName = state.getTicketStateName();
            By check = By.xpath(XPATH1 + stateName + "')]");
            try {
                final boolean checkState = isEnabled(check);
                log.info("Filter by state name " + stateName + " is: " + checkState);
                commonLib.info(FILTER_TEXT + stateName + AVAILABLE + checkState);
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("State does not mapped Correctly(Check Config): '" + stateName + "' " + e.fillInStackTrace(), true);
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
            final String ticketPriority = state.getTicketPriority();
            By check = By.xpath(XPATH1 + ticketPriority + "')]");
            try {
                commonLib.pass(FILTER_TEXT + ticketPriority + AVAILABLE + isEnabled(check));
            } catch (TimeoutException | NoSuchElementException e) {
                commonLib.fail("Priority does not mapped Correctly(Check Config): '" + ticketPriority + "' " + e.fillInStackTrace(), true);
                flag = false;
            }
        }
        return flag;
    }

    public void clickCloseFilter() {
        try {
            commonLib.info("Closing Filter Tab");
            clickAndWaitForLoaderToBeRemoved(tabElements.closeFilter);
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.info("Close Filter Button does not display");
        }
    }

    public void applyFilterByCategoryCode(String code) throws InterruptedException {
        commonLib.info("Clicking Code Field");
        scrollToViewElement(tabElements.byCode);
        clickAndWaitForLoaderToBeRemoved(tabElements.byCode);
        commonLib.info("Searching category code: " + code);
        enterText(tabElements.searchBox, code);
        waitTillLoaderGetsRemoved();
        By selectCode = By.xpath("//span[@class='mat-option-text'][contains(text(),'" + code + "')]");
        clickAndWaitForLoaderToBeRemoved(selectCode);
        commonLib.info("Category Code Selected");
        waitTillLoaderGetsRemoved();
    }

    /*
    This Method is used to verify if soruce filter is present or not
     */
    public boolean isSourceFilterPresent() {
        boolean result;
        result = isEnabled(tabElements.sourceLabel);
        commonLib.pass("Is Source Filter available :" + result);
        return result;
    }

    /*
    This Method is used to select source filter value
     */
    public void selectSourceFilterValue() throws InterruptedException {
        if (isVisible(tabElements.sourceFilterLabel)) {
            scrollToViewElement(tabElements.sourceFilterLabel);
            clickAndWaitForLoaderToBeRemoved(tabElements.sourceFilterLabel);
            clickAndWaitForLoaderToBeRemoved(tabElements.selectCustomerService);
            waitTillLoaderGetsRemoved();
            clickApplyFilter();
            clearFilterDashbaord();
        }
    }

    public void clearFilterDashbaord() {
        if (isVisible(tabElements.clearFilterButtonDashboard)) {
            clickAndWaitForLoaderToBeRemoved(tabElements.clearFilterButtonDashboard);
        }
    }

    public Boolean isApplyFilterBtnEnabled() {
        selUtils.clickElementAfterScroll(tabElements.applyFilter);
        return isEnabled(tabElements.applyFilter);
    }
}
