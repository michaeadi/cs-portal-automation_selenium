package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.PriorityDataBean;
import com.airtel.cs.commonutils.dataproviders.databeans.TicketStateDataBean;
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

    /**
     * This method is use to click open queue filter list
     */
    public void clickQueueFilter() {
        clickAndWaitForLoaderToBeRemoved(tabElements.openQueueList);
    }

    /**
     * This method is use to scroll to queue filter
     */
    public void scrollToQueueFilter() throws InterruptedException {
        scrollToViewElement(tabElements.showQueueFilter);
    }

    /**
     * This method is use to click last 7 days filter
     */
    public void clickLast7DaysFilter() {
        commonLib.info("Clicking on filter by created date - Last 7 days");
        clickAndWaitForLoaderToBeRemoved(tabElements.last7DaysCD);
    }

    /**
     * This method is use to click last 30 days filter
     */
    public void clickLast30DaysFilter() {
        commonLib.info("Clicking on filter by created date - Last 30 days");
        clickWithoutLoader(tabElements.last30DaysCD);
    }

    /**
     * This method is use to click date duration
     */
    public void clickDateDurationFilter() {
        commonLib.info("Clicking on filter by created date - date duration");
        clickWithoutLoader(tabElements.dateDuration);
    }

    /**
     * This method is use to select queue option by  queue name
     * @param queueName Queue name
     * @throws InterruptedException in-case scroll interrupt
     */
    public void selectQueueByName(String queueName) throws InterruptedException {
        commonLib.info("Select Queue Filter Name: " + queueName);
        By queue = By.xpath( tabElements.queueOption+ queueName + "')]");
        scrollToViewElement(queue);
        clickAndWaitForLoaderToBeRemoved(queue);
    }

    /**
     * This method is use to click apply filter button
     */
    public void clickApplyFilter() {
        commonLib.info("Clicking on APPLY Filter Button");
        clickAndWaitForLoaderToBeRemoved(tabElements.applyFilter);
    }

    /**
     * This method is use to click outside the filter
     */
    public void clickOutsideFilter() {
        clickOutside();
    }

    /**
     * This method is use to click un-assigned filter button
     */
    public void clickUnAssignedFilter() throws InterruptedException {
        commonLib.info("Apply Filter By Ticket Assignee");
        scrollToViewElement(tabElements.unAssigned);
        clickAndWaitForLoaderToBeRemoved(tabElements.unAssigned);
    }

    /**
     * This method is use to click open escalation filter icon
     */
    public void openEscalationFilter() throws InterruptedException {
        commonLib.info("Apply filter by ticket escalation level");
        scrollToViewElement(tabElements.openEscalationFilter);
        Thread.sleep(1000);
        clickAndWaitForLoaderToBeRemoved(tabElements.openEscalationFilter);
    }

    /**
     * This method is use to check all level 1 escalation checkbox
     */
    public void selectAllLevel1() throws InterruptedException {
        commonLib.info("Selecting escalation level 1");
            List<WebElement> level1 = returnListOfElement(tabElements.level1Escalation);
            for (WebElement level : level1) {
                ((JavascriptExecutor) driver).executeScript(SCROLL_SCRIPT, level);
                Thread.sleep(500);
                level.click();
            }
    }

    /**
     * This method is use to check all level 2 escalation checkbox
     */
    public void selectAllLevel2() throws InterruptedException {
        commonLib.info("Selecting escalation level 2");
            List<WebElement> level1 = returnListOfElement(tabElements.level2Escalation);
            for (WebElement level : level1) {
                ((JavascriptExecutor) driver).executeScript(SCROLL_SCRIPT, level);
                Thread.sleep(500);
                level.click();
            }
    }

    /**
     * This method is use to check all level 3 escalation checkbox
     */
    public void selectAllLevel3() throws InterruptedException {
        commonLib.info("Selecting escalation level 3");
            List<WebElement> level1 = returnListOfElement(tabElements.level3Escalation);
            for (WebElement level : level1) {
                ((JavascriptExecutor) driver).executeScript(SCROLL_SCRIPT, level);
                Thread.sleep(500);
                level.click();
            }
    }

    /**
     * This method is use to check filter by created date available or not
     * @return true/false
     */
    public boolean isCreatedByFilter() {
        final boolean state = isEnabled(tabElements.filterCreatedByLabel);
        commonLib.pass("Is filter by created date available :" + state);
        return state;
    }

    /**
     * This method is use to check filter by closure date available or not
     * @return true/false
     */
    public boolean isClosureByFilter() {
        final boolean state = isEnabled(tabElements.filterClosureByLabel);
        commonLib.info("Is filter by Closure date available :" + state);
        return state;
    }

    /**
     * This method is use to check filter by SLA Due date available or not
     * @return true/false
     */
    public boolean isSlaDueDateFilter() {
        final boolean state = isEnabled(tabElements.sLADueDateLabel);
        commonLib.pass("Is filter by SLA due date available :" + state);
        return state;
    }

    /**
     * This method is use to check filter by Category available or not
     * @return true/false
     */
    public boolean isCategoryFilter() {
        final boolean state = isEnabled(tabElements.categoryLabel);
        commonLib.pass("Is filter by issue category available :" + state);
        return state;
    }

    /**
     * This method is use to check filter by Queue available or not
     * @return true/false
     */
    public boolean isQueueFilter() {
        try {
            final boolean state = isEnabled(tabElements.queueLabel);
            commonLib.pass("Is filter by Queue available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This method is use to check filter by Ticket Assignee name available or not
     * @return true/false
     */
    public boolean isTicketByAssigneeFilter() {
        try {
            final boolean state = isEnabled(tabElements.ticketAssigneeLabel);
            commonLib.pass("Is filter by ticket assignee name available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This method is use to check filter by escalated level available or not
     * @return true/false
     */
    public boolean isEscalatedLevelFilter() {
        try {
            final boolean state = isEnabled(tabElements.escalatedLevelLabel);
            commonLib.pass("Is filter by ticket escalation level available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This method is use to check filter by ticket state available or not
     * @return true/false
     */
    public boolean isStateFilter() {
        try {
            final boolean state = isEnabled(tabElements.stateLabel);
            commonLib.pass("Is filter by ticket state available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This method is use to check filter by ticket priority available or not
     * @return true/false
     */
    public boolean isPriorityFilter() {
        try {
            final boolean state = isEnabled(tabElements.priorityLabel);
            commonLib.pass("Is filter by ticket priority available :" + state);
            return state;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This method is use to validate all open state option visible or not in filter tab
     * @return true/false
     */
    public boolean validateOpenStateFilter() {
        DataProviders d = new DataProviders();
        List<TicketStateDataBean> open = d.getState(constants.getValue(CommonConstants.TICKET_OPEN_STATE));
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

    /**
     * This method is use to validate all close state option visible or not in filter tab
     * @return true/false
     */
    public boolean validateCloseStateFilter() {
        DataProviders d = new DataProviders();
        List<TicketStateDataBean> open = d.getState(constants.getValue(CommonConstants.TICKET_CLOSE_STATE));
        boolean flag = true;
        for (TicketStateDataBean state : open) {
            final String stateName = state.getTicketStateName();
            By check = By.xpath(XPATH1 + stateName + "')]");
            try {
                final boolean checkState = isEnabled(check);
                commonLib.info(FILTER_TEXT + stateName + AVAILABLE + checkState);
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("State does not mapped Correctly(Check Config): '" + stateName + "' " + e.fillInStackTrace(), true);
                flag = false;
            }
        }
        return flag;
    }


    /**
     * This method is use to validate all priority option visible or not in filter tab
     * @return true/false
     */
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

    /**
     * This method use to click close filter
     */
    public void clickCloseFilter() {
        try {
            commonLib.info("Closing Filter Tab");
            clickAndWaitForLoaderToBeRemoved(tabElements.closeFilter);
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.info("Close Filter Button does not display");
        }
    }

    /**
     * This method use to select code in filter by category based on issue code
     * @param code The code
     * @throws InterruptedException in-case of scroll interrupt
     */
    public void applyFilterByCategoryCode(String code) throws InterruptedException {
        commonLib.info("Clicking Code Field");
        scrollToViewElement(tabElements.byCode);
        clickAndWaitForLoaderToBeRemoved(tabElements.byCode);
        commonLib.info("Searching category code: " + code);
        enterText(tabElements.searchBox, code);
        waitTillLoaderGetsRemoved();
        By selectCode = By.xpath(tabElements.codeOption + code + "')]");
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
        commonLib.info("Is Source Filter available :" + result);
        return result;
    }

    /*
    This Method is used to select source filter value
     */
    public void selectSourceFilterValue() throws InterruptedException {
        if (isVisible(tabElements.sourceFilterLabel)) {
            scrollToViewElement(tabElements.sourceFilterLabel);
            clickWithoutLoader(tabElements.sourceFilterLabel);
            clickWithoutLoader(tabElements.selectCustomerService);
            clickApplyFilter();
            clearFilterDashboard();
        }
    }

    /**
     * This method use to click clear filter button
     */
    public void clearFilterDashboard() {
        if (isVisible(tabElements.clearFilterButtonDashboard)) {
            clickAndWaitForLoaderToBeRemoved(tabElements.clearFilterButtonDashboard);
        }
    }

    /**
     * This method use to check apply filter button enabled or not
     * @return true/false
     */
    public Boolean isApplyFilterBtnEnabled() {
        selUtils.clickElementAfterScroll(tabElements.applyFilter);
        return isEnabled(tabElements.applyFilter);
    }
}
