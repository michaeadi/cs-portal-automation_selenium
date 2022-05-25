package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboard6MonthsTicketTypePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class KpiDashboard6MonthsTicketType extends BasePage {
    KpiDashboard6MonthsTicketTypePage pageElements;

    public KpiDashboard6MonthsTicketType(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, KpiDashboard6MonthsTicketTypePage.class);

    }

    /**
     * This method is used to check 6 Months Ticket Type Label is visible or not
     */
    public Boolean is6MonthsTicketTypeLabelVisible() {
        Boolean status = isVisible(pageElements.sixMonthsTicketTypeLabel);
        commonLib.info("6 Months Ticket Type Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check FTR  Icon  is visible or not
     */
    public Boolean isFTRIconlVisible() {
        Boolean status = isVisible(pageElements.ftrIcon);
        commonLib.info("FTR Icon is visible : " + status);
        return status;
    }

    /**
     * This method is used to check NFTR  Icon  is visible or not
     */
    public Boolean isNFTRIconVisible() {
        Boolean status = isVisible(pageElements.nftrIcon);
        commonLib.info("NFTR Icon is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Connection  Label  is visible or not
     */
    public Boolean isConnectionLabelVisible() {
        Boolean status = isVisible(pageElements.connectionLabel);
        commonLib.info("Connection Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Issue Type Label  is visible or not
     */
    public Boolean isIssueTypeLabelVisible() {
        Boolean status = isVisible(pageElements.issueTypelLabel);
        commonLib.info("Issue Type   Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on 6 Months Ticket Details  Icon
     */
    public void clickOn6MonthsTicketDetailsIcon() {
        commonLib.info("Going to click6 Months Ticket Details  Icon");
        if (isVisible(pageElements.sixMonthsTicketDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.sixMonthsTicketDetailsIcon);
        else {
            commonLib.error(" 6 Months Ticket Details Icon not Visible");
        }

    }

    /**
     * This method is used to check TICKET TYPE DETAILS  is visible or not
     */
    public Boolean isTicketTypeDetailsLabelVisible() {
        Boolean status = isVisible(pageElements.ticketTypeDetailsLabel);
        commonLib.info("TICKET TYPE DETAILS  Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Month Label is visible or not
     */
    public Boolean isMonthLabelVisible() {
        Boolean status = isVisible(pageElements.monthLabel);
        commonLib.info("Month Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check FTR Label is visible or not
     */
    public Boolean isFTRLabelVisible() {
        Boolean status = isVisible(pageElements.ftrLabel);
        commonLib.info("Month Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check NFTR  Label is visible or not
     */
    public Boolean isNFTRLabelVisible() {
        Boolean status = isVisible(pageElements.nftrLabel);
        commonLib.info("NFTR  Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check FTR % Label is visible or not
     */
    public Boolean isFTRPercentageLabelVisible() {
        Boolean status = isVisible(pageElements.ftrPercentageLabel);
        commonLib.info("FTR % Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Back Icon
     */
    public void clickOnBackIcon() {
        commonLib.info("Going to click Back Icon");
        if (isVisible(pageElements.backIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.backIcon);
        else {
            commonLib.error("Back Icon not Visible");
        }

    }
}
