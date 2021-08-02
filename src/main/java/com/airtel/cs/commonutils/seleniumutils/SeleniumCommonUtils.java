package com.airtel.cs.commonutils.seleniumutils;


import com.airtel.cs.commonutils.applicationutils.constants.ConstantsUtils;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.BasePage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.Map;
import java.util.NoSuchElementException;

public class SeleniumCommonUtils extends Driver {

    private static final String BREAK_LINE = "</br>";
    private static Map<String, String> styleMap;

    public SeleniumCommonUtils() {
        constants = ConstantsUtils.getInstance();
    }

    public void addTestcaseDescription(String description, String isPrerequisiteStepsORMainDescriptionSteps) {
        try {
            int index = 1;
            String[] testSteps = description.split(",");
            TESTCASE_DESCRIPTION_BUILDER.setLength(0);
            TESTCASE_DESCRIPTION_BUILDER.append(BREAK_LINE);
            for (String string : testSteps) {
                TESTCASE_DESCRIPTION_BUILDER.append("<font size=\"3\" color=\"DARKSALMON\">" + "      " + "<b>" + "     Step ").append(index).append(" - ").append("</b>").append("<font size=\"3\" color=\"MEDIUMAQUAMARINE\">").append(string).append("</font>").append(BREAK_LINE).append(System.lineSeparator());
                index++;
            }
            TESTCASE_DESCRIPTION_BUILDER.append(BREAK_LINE);
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
            FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
            wait.until(driver -> {
                commonLib.info("Current Window State  : "
                        + ((JavascriptExecutor) driver).executeScript("return document.readyState"));
                return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            });
        } catch (Exception e) {
            commonLib.warning("Exception in method - | waitForPageLoad | " + e.getMessage());
        }
    }

    /**
     * This Method can be used to get the text of WebElement
     *
     * @param element The WebElement
     * @return return
     */
    public String getText(WebElement element) {
        String text = "";
        try {
            Wait<WebDriver> wait = getWaitObject(10);
            element = wait.until(ExpectedConditions.visibilityOf(element));
            if (element != null) {
                text = element.getText().trim();
            } else {
                text = "BLANK" + BREAK_LINE + elementName + " - Element not visible. " + BREAK_LINE
                        + " Fail to Get Text by Method - [---- getText(By elementLocation, int time) ----]";
            }
        } catch (Exception e) {
            e.getStackTrace();
            commonLib.warning("Exception in method - | getText | " + BREAK_LINE + "Exception Message - " + e.getMessage());
        }
        return text;
    }

    /**
     * This Method will be used to check visibility of WeblEments
     *
     * @param webelementBy WebElement
     * @param time         The Time in Seconds
     * @return return
     */
    public boolean isVisible(By webelementBy, int time) {
        try {
            Wait<WebDriver> wait = getWaitObject(time);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(webelementBy));
            return element != null;
        } catch (Exception e) {
            commonLib.error("Element is NOT visible");
            return false;
        }
    }

    /*
    This Method will provide the fluent wait object
     */
    public Wait<WebDriver> getWaitObject(int maxWaitFor) {
        FluentWait wait = null;
        try {
            wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(maxWaitFor))
                    .pollingEvery(Duration.ofMillis(200)).ignoring(NoSuchElementException.class);
        } catch (Exception e) {
            e.getStackTrace();
            commonLib.warning(
                    "Exception in method - | methodName | " + BREAK_LINE + "Exception Message - " + e.getMessage());
        }
        return wait;
    }

    /*
    This Method will click after scroll
     */
    public void clickElementAfterScroll(By element) {
        new Actions(driver).moveToElement(getElementfromBy(element)).click().build().perform();
    }

    /**
     * THIS METHOD WILL RETURN WEB ELEMENT FROM BY -
     */
    public WebElement getElementfromBy(By elementLocation) {
        WebElement element = null;
        try {
            element = driver.findElement(elementLocation);
        } catch (Exception e) {
            commonLib.error("Not able to find out elements");
        }
        return element;
    }

    /**
     * This Method will let us know the computed css style for a particular webelement
     *
     * @param elementLocation the element
     * @return computed css
     */
    public Map<String, String> getStyle(By elementLocation) {
        BasePage basePage = new BasePage(driver);
        final String id = basePage.getAttribute(elementLocation, "id", false);
        String idnew = "var elem = document.getElementById('%s'); ";
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object allAttributes = executor.executeScript(
                "var items={};" +
                        String.format(idnew, id) +
                        "var css = window.getComputedStyle(elem, null);" +
                        "items={'font-weight':css.getPropertyValue('font-weight'),'font-color':css.getPropertyValue('color')}; return items;");
        ObjectMapper m = new ObjectMapper();
        styleMap = m.convertValue(allAttributes, Map.class);
        commonLib.info("Style is: " + allAttributes.toString());
        return styleMap;
    }

    /**
     * This Method will return us the font weight of a webelement
     *
     * @param elementLocation the webelement location
     * @return the font-weight
     */
    public String getDataPointFontWeight(By elementLocation) {
        String result = "";
        final Map<String, String> style = getStyle(elementLocation);
        int i = Integer.parseInt(style.get("font-weight"));
        if (i > 600)
            result = "Bold";
        else
            result = "Not Bold";
        return result;
    }

    /**
     * This Method will return us color of a webelement
     *
     * @param elementLocation the webelement location
     * @return the color
     */
    public String getDataPointColor(By elementLocation) {
        String result = "";
        final Map<String, String> style = getStyle(elementLocation);
        result = Color.fromString(style.get("font-color")).asHex();
        return result;
    }
}