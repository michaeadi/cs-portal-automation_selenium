package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboard6MonthsOverviewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class KpiDashboard6MonthsOverview  extends BasePage {
    KpiDashboard6MonthsOverviewPage pageElements;
    public KpiDashboard6MonthsOverview(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, KpiDashboard6MonthsOverviewPage.class);
    }
    /**
     * This method is used to check Service Level Trend  is visible or not
     */
    public Boolean isServiceLevelTrendVisible() {
        Boolean status = isVisible(pageElements.serviceLevelTrend);
        commonLib.pass("Service Level Trend  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Connection Type Drop Down  is visible or not
     */
    public Boolean isConnectionTypeDropDownVisible() {
        Boolean status = isVisible(pageElements.connectionTypeDropDown);
        commonLib.pass("Connection Type Drop Down  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Queue Type Drop Down is visible or not
     */
    public Boolean isQueueTypeDropDownVisible() {
        Boolean status = isVisible(pageElements.queueTypeDropDown);
        commonLib.pass("Queue Type Drop Down is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Issue Type Drop Down  is visible or not
     */
    public Boolean isIssueTypeDropDownVisible() {
        Boolean status = isVisible(pageElements.issueTypeDropDown);
        commonLib.pass("Issue Type Drop Down is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Issue sub Type Drop Down  is visible or not
     */
    public Boolean isIssueSubTypeDropDownVisible() {
        Boolean status = isVisible(pageElements.issueSubTypeDropDown);
        commonLib.pass("Issue sub Type Drop Down  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Download Report Icon  is visible or not
     */
    public Boolean isDownloadReportIconVisible() {
        Boolean status = isVisible(pageElements.downloadReportIcon);
        commonLib.pass("Download Report Icon  is visible : " + status);
        return status;
    }
    /**
     * This method is used to click On Service Level Trend Details Icon
     */
    public void clickOnServiceLevelTrendDetailsIcon() {
        commonLib.pass("click On Service Level Trend Details Icon");
        if (isVisible(pageElements.serviceLevelTrendDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.serviceLevelTrendDetailsIcon);
    }
}
