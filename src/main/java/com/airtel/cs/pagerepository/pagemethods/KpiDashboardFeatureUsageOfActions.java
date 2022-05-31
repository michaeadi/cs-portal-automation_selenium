package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboardFeatureUsageOfActionsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class KpiDashboardFeatureUsageOfActions extends BasePage {
    KpiDashboardFeatureUsageOfActionsPage pageElements;

    public KpiDashboardFeatureUsageOfActions(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, KpiDashboardFeatureUsageOfActionsPage.class);

    }

    /**
     * This method is used to check Feature Usage Of Actions (Monthly) Label is visible or not
     */
    public Boolean isFeatureUsageOfActionsLabelVisible() {
        Boolean status = isVisible(pageElements.featureUsageOfActionsLabel);
        commonLib.info("Feature Usage Of Actions Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Connection  Label is visible or not
     */
    public Boolean isConnectionLabelVisible() {
        Boolean status = isVisible(pageElements.connectionLabel);
        commonLib.info("Connection Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Month Selection  Label is visible or not
     */
    public Boolean isMonthSelectionLabelVisible() {
        Boolean status = isVisible(pageElements.monthSelectionLabel);
        commonLib.info("Month Selection Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Feature Usage Of Actions Details  Icon
     */
    public void clickOnFeatureUsageOfActionsDetailsIcon() {
        commonLib.info("Going to click Feature Usage Details Icon");
        if (isVisible(pageElements.featureUsageOfActionsDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.featureUsageOfActionsDetailsIcon);
        else {
            commonLib.error("Feature Usage Of Actions Details  Icon not Visible");
        }

    }

    /**
     * This method is used to check Agent ID  Label is visible or not
     */
    public Boolean isAgentIdLabelVisible() {
        Boolean status = isVisible(pageElements.agentIdLabel);
        commonLib.info("Agent ID Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Agent Name   Label is visible or not
     */
    public Boolean isAgentNameLabelVisible() {
        Boolean status = isVisible(pageElements.agentNameLabel);
        commonLib.info("Agent Name  Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check MTD   Label is visible or not
     */
    public Boolean isMTDLabelVisible() {
        Boolean status = isVisible(pageElements.mtdLabel);
        commonLib.info("MTD  Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Back Icon
     */
    public void clickOnBackIcon() {
        commonLib.info("Going to click Back Icon");
        if (isVisible(pageElements.backIcon)) clickAndWaitForLoaderToBeRemoved(pageElements.backIcon);
        else {
            commonLib.error("Back Icon not Visible");
        }
    }

    /*
    This method is used to get the Text for Feature usage detailed widget header
     */
    public boolean featureUsageDetailsWidgetHeader() {
        return isVisible(pageElements.featureUsageDetailedWidget);
    }
}
