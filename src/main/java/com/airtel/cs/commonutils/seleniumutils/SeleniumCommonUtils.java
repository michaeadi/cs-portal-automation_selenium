package com.airtel.cs.commonutils.seleniumutils;


import com.airtel.cs.commonutils.applicationutils.constants.ConstantsUtils;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class SeleniumCommonUtils extends Driver {
    public SeleniumCommonUtils() {
        constants = ConstantsUtils.getInstance();
    }

    public void addTestcaseDescription(String description, String isPrerequisiteStepsORMainDescriptionSteps) {
        try {
            int index = 1;
            String[] testSteps = description.split(",");
            TESTCASE_DESCRIPTION_BUILDER.setLength(0);
            TESTCASE_DESCRIPTION_BUILDER.append("</br>");
            for (String string : testSteps) {
                TESTCASE_DESCRIPTION_BUILDER.append("<font size=\"3\" color=\"DARKSALMON\">" + "      " + "<b>" + "     Step ").append(index).append(" - ").append("</b>").append("<font size=\"3\" color=\"MEDIUMAQUAMARINE\">").append(string).append("</font>").append("</br>").append(System.lineSeparator());
                index++;
            }
            TESTCASE_DESCRIPTION_BUILDER.append("</br>");
            reporter.showInExtentReportAddDescriptionOFTestcase(isPrerequisiteStepsORMainDescriptionSteps);
        } catch (Exception e) {
            commonLib.fail(e.getMessage(), false);
        }
    }
    /*----------------------------------------------------------------------------------------*/

    /**
     * ---------------------------- Alert Methods --------------------
     */
    /*----------------------------------------------------------------------------------------*/
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    public void isAlertPresent(String ifFoundAcceptORReject) {
        try {
            if (isAlertPresent()) {
                Alert alert = driver.switchTo().alert();
                commonLib.warning("Alert PopUp Found with Message - " + alert.getText());
                if (ifFoundAcceptORReject.equalsIgnoreCase("accept")) {
                    alert.accept();
                } else if (ifFoundAcceptORReject.equalsIgnoreCase("reject")) {
                    alert.dismiss();
                } else {
                    commonLib.warning("Not Yet Decided - What to do with this Alert?");
                }
            }
        } catch (NoAlertPresentException ex) {
            commonLib.info("NO Alert Found");
        }
    }

    public void waitForPageLoad() {
        try {
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
            wait.until(driver -> {
                commonLib.info("Current Window State  : "
                        + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
                return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            });
        } catch (Exception e) {
            commonLib.warning("Exception in method - | waitForPageLoad | " + e.getMessage());
        }
    }
}