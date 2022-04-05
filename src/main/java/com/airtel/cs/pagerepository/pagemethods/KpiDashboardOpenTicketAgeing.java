package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboardOpenTicketAgeingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;



    public class KpiDashboardOpenTicketAgeing extends BasePage {
        KpiDashboardOpenTicketAgeingPage pageElements;

        public KpiDashboardOpenTicketAgeing(WebDriver driver) {
            super(driver);
            pageElements = PageFactory.initElements(driver, KpiDashboardOpenTicketAgeingPage.class);

        }


        /**
         * This method is used to check Lifetime Till Date Label is visible or not
         */
        public Boolean isLifetimeTillDateLabelVisible() {
            Boolean status = isVisible(pageElements.lifetimeTillDateLabel);
            commonLib.pass("Lifetime Till Date Label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Open Ticket Ageing Icon is visible or not
         */
        public Boolean isOpenTicketAgeingLabelVisible() {
            Boolean status = isVisible(pageElements.openTicketAgeingLabel);
            commonLib.pass("Open Ticket Ageing Label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Connection  is visible or not
         */
        public Boolean isConnectionLabelVisible() {
            Boolean status = isVisible(pageElements.connectionLabel);
            commonLib.pass("Connection  Label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Month  is visible or not
         */
        public Boolean isMonthLabelVisible() {
            Boolean status = isVisible(pageElements.monthLabel);
            commonLib.pass("Month  Label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Issue Type is visible or not
         */
        public Boolean isIssueTypeLabelVisible() {
            Boolean status = isVisible(pageElements.issueTypeLabel);
            commonLib.pass("Issue Type  Label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check IssueSubType  is visible or not
         */
        public Boolean isIssueSubTypeLabelVisible() {
            Boolean status = isVisible(pageElements.issueSubTypeLabel);
            commonLib.pass("Issue Sub Type  Label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Download Icon  is visible or not
         */
        public Boolean isDownLoadIconlVisible() {
            Boolean status = isVisible(pageElements.downloadIcon);
            commonLib.pass("Download  Icon  is visible : " + status);
            return status;
        }

        /**
         * This method is used to click Open Ticket Ageing Details Icon
         */
        public void clickOpenTicketAgeingDetailsIcon() {
            commonLib.info("Going to click Open Ticket Ageing Details Icon");
            if (isVisible(pageElements.openTicketAgeingDetailsIcon))
                clickAndWaitForLoaderToBeRemoved(pageElements.openTicketAgeingDetailsIcon);

        }

        /**
         * This method is used to check Issue Code  label  is visible or not
         */
        public Boolean isIssueCodeLabellVisible() {
            Boolean status = isVisible(pageElements.issueCodeLabel);
            commonLib.pass("Issue Code Label is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Queue Name    label  is visible or not
         */
        public Boolean isQueueNameLabellVisible() {
            Boolean status = isVisible(pageElements.queueNameLabel);
            commonLib.pass("Queue Name Label is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Less Than A Day label  is visible or not
         */
        public Boolean isLessThanADayLabellVisible() {
            Boolean status = isVisible(pageElements.lessThanADayLabel);
            commonLib.pass("Less Than A Day Label is visible : " + status);
            return status;
        }

        /**
         * This method is used to check 2 -3 days label  is visible or not
         */
        public Boolean isTwoToThreeDaysLabellVisible() {
            Boolean status = isVisible(pageElements.twoToThreeDaysLabel);
            commonLib.pass("2 -3 Days label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check 3 -10 days label  is visible or not
         */
        public Boolean isThreeToTenDaysLabellVisible() {
            Boolean status = isVisible(pageElements.threeToTenDaysLabel);
            commonLib.pass("3 -10 Days label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check 10 -30 days label  is visible or not
         */
        public Boolean isTenToThrityDaysLabellVisible() {
            Boolean status = isVisible(pageElements.tenToThirtyDaysLabel);
            commonLib.pass("10 -30 Days label  is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Beyond 30 Days label  is visible or not
         */
        public Boolean isBeyond30DaysDaysLabellVisible() {
            Boolean status = isVisible(pageElements.beyond30DaysLabe);
            commonLib.pass("Beyond 30 Days label is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Sla breach Ticket Ageing Issue Type And Queue Label  is visible or not
         */
        public Boolean isSlabreachTicketAgeingIssueTypeAndQueueLabel() {
            Boolean status = isVisible(pageElements.slabreachTicketAgeingIssueTypeAndQueueLabel);
            commonLib.pass("Sla breach Ticket Ageing Issue Type And Queue Label is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Sla Breach Ticket Ageing Agent Label  is visible or not
         */
        public Boolean isSlaBreachTicketAgeingAgentLabel() {
            Boolean status = isVisible(pageElements.slaBreachTicketAgeingAgentLabel);
            commonLib.pass("Sla Breach Ticket Ageing Agent Labell is visible : " + status);
            return status;
        }

        /**
         * This method is used to check Agent ID label  is visible or not
         */
        public Boolean isAgentIdLabellVisible() {
            Boolean status = isVisible(pageElements.agentIdLabe);
            commonLib.pass("Agent ID label is visible : " + status);
            return status;
        }

        /**
         * This method is used to chec kAgent Name  label  is visible or not
         */
        public Boolean isAgentNameLabellVisible() {
            Boolean status = isVisible(pageElements.agentNameLabe);
            commonLib.pass("Agent Name label is visible : " + status);
            return status;
        }

        /**
         * This method is used to click on Back Icon
         */
        public void clickOnBackIcon() {
            commonLib.info("Going to click Back Icon");
            if (isVisible(pageElements.backIcon))
                clickAndWaitForLoaderToBeRemoved(pageElements.backIcon);
        }
    }