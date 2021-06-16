package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pageelements.AirtelByWrapper;
import com.airtel.cs.pagerepository.pageelements.BasePageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class BasePage extends Driver {
    public Wait<WebDriver> wait;
    public Wait<WebDriver> fluentWait;
    public JavascriptExecutor js;
    BasePageElements basePageElements;
    public static final RequestSource api = new RequestSource();
    private static final String BREAK_LINE = "</br>";

    //Constructor
    public BasePage(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        Driver.driver = driver;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> expectation = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").toString().equals("complete");
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Integer.parseInt(constants.getValue(ApplicationConstants.GENERAL_WAIT_IN_SEC))))
                .pollingEvery(Duration.ofSeconds(Integer.parseInt(constants.getValue(ApplicationConstants.POOLING_WAIT_IN_SEC))));
        fluentWait.until(expectation);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(constants.getValue(ApplicationConstants.GENERAL_WAIT_IN_SEC))));
        basePageElements = new BasePageElements();
    }


    public void waitTillLoaderGetsRemoved() {
        commonLib.info("Waiting for loader to be removed");
        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(basePageElements.loader));
        commonLib.info("Loader Removed");
    }

    public void waitTillOverlayGetsRemoved() {
        commonLib.info("Waiting for overlay to be removed");
        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(basePageElements.overlay));
        commonLib.info("Overlay Removed");
    }

    public void waitTillTimeLineGetsRemoved() {
        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(basePageElements.timeLine));
    }

    /**
     * This Method will click and wait until loader gets removed
     *
     * @param elementLocation The Element
     */
    public void clickAndWaitForLoaderToBeRemoved(By elementLocation) {
        try {
            if (isVisible(elementLocation) && isClickable(elementLocation)) {
                highLighterMethod(elementLocation);
                driver.findElement(elementLocation).click();
                commonLib.info("Element Clicked " + elementLocation.toString());
                waitTillLoaderGetsRemoved();
            } else {
                commonLib.fail("Exception in method - clickAndWaitForLoaderToBeRemoved", true);
            }
        } catch (ElementClickInterceptedException e) {
            waitTillLoaderGetsRemoved();
            driver.findElement(elementLocation).click();
            commonLib.info("Again Element Clicked " + elementLocation.toString());
        }
    }

    /**
     * This Method will be used to click where loader will not come after click
     *
     * @param elementLocation The Element
     */
    public void clickWithoutLoader(By elementLocation) {
        if (isVisible(elementLocation) && isClickable(elementLocation)) {
            driver.findElement(elementLocation).click();
            commonLib.info("Element Clicked " + elementLocation.toString());
        } else {
            commonLib.fail("Exception in method - clickWithoutLoader", true);
        }
    }

    public void scrollToViewElement(By element) throws InterruptedException {
        WebElement element1 = driver.findElement(element);
        waitVisibility(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        Thread.sleep(500);
    }

    //Write Text
    public void enterText(By elementLocation, String text) {
        if (isVisible(elementLocation)) {
            highLighterMethod(elementLocation);
            driver.findElement(elementLocation).clear();
            driver.findElement(elementLocation).sendKeys(text);
            commonLib.info("Entering " + text + " to  " + elementLocation.toString());
        } else {
            commonLib.error("Text box is NOT visible : " + elementLocation);
        }
    }

    //Read Text
    public String getText(By elementLocation) {
        waitVisibility(elementLocation);
        highLighterMethod(elementLocation);
        return driver.findElement(elementLocation).getText();
    }

    /**
     * This Method will highlight the given element
     *
     * @param element The element
     */
    public void highLighterMethod(By element) {
        if (isVisible(element)) {
            js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'border: 1px solid orange;');", driver.findElement(element));
        } else {
            commonLib.fail("Exception Caught in Method - highLighterMethod", true);
        }
    }

    //Check the state of element
    public boolean isEnabled(By elementLocation) {
        boolean result = false;
        try {
            if (isVisible(elementLocation))
                result = driver.findElement(elementLocation).isEnabled();
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    //Wait For Element
    public void waitVisibility(By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitAndSwitchWindow(int windownumber) {
        wait.until(ExpectedConditions.numberOfWindowsToBe(windownumber));
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(windownumber - 1));
    }

    /**
     * This Method will be used to hover over element
     *
     * @param elementLocation The Element
     */
    public void hoverOverElement(By elementLocation) {
        Actions actions = new Actions(driver);
        waitVisibility(elementLocation);
        WebElement target = driver.findElement(elementLocation);
        if (isVisible(elementLocation) && isClickable(elementLocation))
            actions.moveToElement(target).build().perform();
        else
            commonLib.fail("Exception in method - hoverOverElement", true);
    }

    public CustomerProfile openingCustomerInteractionDashboard() {
        commonLib.info("Opening Customer Interactions Dashboard");
        clickAndWaitForLoaderToBeRemoved(basePageElements.home);
        waitTillLoaderGetsRemoved();
        return new CustomerProfile(driver);
    }

    public String getToastMessage() {
        String message = getText(basePageElements.toastMessage);
        commonLib.info(message);
        return message;
    }

    //Switch to parent frame
    public void switchToParentFrme() {
        driver.switchTo().parentFrame();
    }

    // is element  visible
    public boolean isElementVisible(By element) {
        try {
            return driver.findElement(element).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void selectByText(String text) {
        WebElement elementby = driver.findElement(By.xpath("//span[contains(text(),'" + text + "')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementby);
        driver.findElement(By.xpath("//span[contains(text(),'" + text + "')]")).click();
    }

    public void clickOutside() {
        Actions action = new Actions(driver);
        action.moveByOffset(0, 0).click().build().perform();
    }

    public void clearInputTag(By element) {
        commonLib.info("Clear Search Box");
        driver.findElement(element).clear();
    }

    public boolean validateFilter(By element, String text) {
        List<WebElement> list = returnListOfElement(element);
        commonLib.info("Validating Filter");
        for (WebElement x : list) {
            commonLib.info("Element Text : " + x.getText());
            if (!x.getText().equalsIgnoreCase(text)) {
                return false;
            }
        }
        return true;
    }

    public List<WebElement> returnListOfElement(By element) {
        List<WebElement> list = new ArrayList<>();
        try {
            list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
        } catch (TimeoutException | NoSuchElementException e) {
            commonLib.info("Not able to Fetch List of Elements :" + e.fillInStackTrace());
        }
        return list;
    }

    public String readTextOnRows(By elementLocation, int row) {
        waitVisibility(elementLocation);
        return driver.findElements(elementLocation).get(row).getText();
    }

    public String readOnRowColumn(By rowLocation, By columnLocation, int row, int column) {
        waitVisibility(rowLocation);
        commonLib.info("Row Size: " + driver.findElements(rowLocation).size());
        commonLib.info("Column Size: " + driver.findElement(rowLocation).findElements(columnLocation).size());
        return driver.findElements(rowLocation).get(row).findElements(columnLocation).get(column).getText();
    }

    /**
     * With This Method you can provide the hard wait time in seconds
     *
     * @param time time in seconds
     * @throws InterruptedException InterruptedException
     */
    public void hardWait(int time) throws InterruptedException {
        time = time * 1000;
        Thread.sleep(time);
    }

    /*
    With this Method we can put hard wait of 3 seconds
     */
    public void hardWait() throws InterruptedException {
        hardWait(3);
    }

    /**
     * This Method will let us know the visiblity of the element after an explicit wait time
     *
     * @param elementLocation element locator
     * @return true/false
     */
    public boolean elementVisibleWithExplictWait(By elementLocation) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement modal = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(elementLocation));
        return modal.isDisplayed();
    }

    /**
     * This Method will return the attribute value in true false like checkbox is checked or not etc
     *
     * @param elementLocation element locator
     * @param attributeName   attribute name
     * @return true/false
     */
    public boolean getAttributeBoolean(By elementLocation, String attributeName) {
        commonLib.hardWait();
        return "true".equalsIgnoreCase(getElementfromBy(elementLocation).getAttribute(attributeName));
    }

    /**
     * THIS METHOD WILL RETURN WEBELEMENT FROM BY -
     */
    public WebElement getElementfromBy(By elementLocation) {
        WebElement element = null;
        try {
            element = driver.findElement(elementLocation);
        } catch (Exception e) {
            commonLib.error("Element Not Found" + e, true);
        }
        return element;
    }

    /**
     * This Method will provide us the attribute value from the DOM
     *
     * @param elementLocation element locator
     * @param attributeName   attribute name
     * @param requireWait     yes/no
     * @return value of the attribute
     */
    public String getAttribute(By elementLocation, String attributeName, boolean requireWait) {
        String attributeValue;
        try {
            elementName = getElementNameFromAirtelByWrapper(elementLocation);
            message = elementName
                    + " - element not visible. Not able to Get Attribute by Method - [--getAttribute(By elementLocation, String attributeName)--]";
            if (requireWait) {
                if (isVisible(elementLocation, 5)) {
                    return getElementfromBy(elementLocation).getAttribute(attributeName).trim();
                } else {
                    commonLib.warning(message);
                    return message;
                }
            } else {
                attributeValue = getElementfromBy(elementLocation).getAttribute(attributeName);
                if ((attributeValue == null)) {
                    attributeValue = "";
                } else {
                    if (!attributeValue.equals(""))
                        attributeValue = attributeValue.trim();
                }
                if (attributeValue.equals("")) {
                    WebElement element = getElementfromBy(elementLocation);
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    Object allAttributes = executor.executeScript(
                            "var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
                            element);
                    commonLib.logs(allAttributes.toString());
                }
            }
        } catch (Exception e) {
            commonLib.warning(message + BREAK_LINE + "Exception - " + e.getMessage());
            return message;
        }
        return attributeValue;
    }

    /**
     * This Method will provide us the name of the element mentioned under Airtel By Wrapper class
     *
     * @param element element locator
     * @return element name
     */
    public String getElementNameFromAirtelByWrapper(By element) {
        elementName = "---this Element is not an instance of Airtel wrapper please add Valid Name of this Element---";
        try {
            if (element instanceof AirtelByWrapper) {
                elementName = ((AirtelByWrapper) element).getDescription();
            }
        } catch (Exception e) {
            commonLib.warning("Caught some Exception inside method - [--getElementName--] " + e.getMessage());
        }
        return elementName;
    }

    /**
     * CREATED THIS TO BYPASS THE DEFAULT 10 SEC WAIT CAUSED BY ISDISPLAYED METHOD
     *
     * @param webelementBy element locator
     * @return will return true false
     */
    public boolean isVisible(By webelementBy) {
        return isVisible(webelementBy, Integer.parseInt(constants.getValue(ApplicationConstants.GENERAL_WAIT_IN_SEC)));
    }

    /**
     * This Method will let us know, if element is visible or not
     *
     * @param webelementBy element lcoator
     * @param time         time in seconds
     * @return true/false
     */
    public boolean isVisible(By webelementBy, int time) {
        try {
            elementName = getElementNameFromAirtelByWrapper(webelementBy);
            Wait<WebDriver> driverWait = getWaitObject(time);
            WebElement webElement = driverWait.until(ExpectedConditions.visibilityOfElementLocated(webelementBy));
            return webElement != null;
        } catch (Exception e) {
            commonLib.error("Element Not Visible :-" + webelementBy);
            return false;
        }
    }


    public boolean isVisibleContinueExecution(By webelementBy) {
        return isVisibleContinueExecution(webelementBy, Integer.parseInt(constants.getValue(ApplicationConstants.GENERAL_WAIT_IN_SEC)));
    }

    public boolean isVisibleContinueExecution(By webelementBy, int time){
        elementName = getElementNameFromAirtelByWrapper(webelementBy);
        Wait<WebDriver> driverWait = getWaitObject(time);
        WebElement webElement = driverWait.until(ExpectedConditions.visibilityOfElementLocated(webelementBy));
        return webElement != null;


    }

    public Wait<WebDriver> getWaitObject(int maxWaitFor) {
        FluentWait fluentWait = null;
        try {
            fluentWait = new FluentWait(driver).withTimeout(Duration.ofSeconds(maxWaitFor))
                    .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
        } catch (Exception e) {
            e.getStackTrace();
            commonLib.warning(
                    "Exception in method - | methodName | " + BREAK_LINE + "Exception Message - " + e.getMessage());
        }
        return fluentWait;
    }

    /**
     * This Method will return list of web elements
     *
     * @param elementLocation element locator
     * @return Will return list of elements
     */
    public List<WebElement> getElementsListFromBy(By elementLocation) {
        List<WebElement> elements = null;
        try {
            elements = driver.findElements(elementLocation);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return elements;
    }

    /**
     * This Method will enter text with timestamp as random variable
     *
     * @param elementLocation element locator
     * @param text            text to be enter
     */
    public void setTextWithTimeStamp(By elementLocation, String text) {
        setTextWithTimeStamp(elementLocation, text, "yes");
    }

    /**
     * This Method will enter text with timestamp as random variable
     *
     * @param elementLocation         element locator
     * @param text                    text to be enter
     * @param requiredClearFieldYesNo Do you need to clear field? yes or no
     */
    public void setTextWithTimeStamp(By elementLocation, String text, String requiredClearFieldYesNo) {
        try {
            if (requiredClearFieldYesNo.equalsIgnoreCase("yes")) {
                getElementfromBy(elementLocation).clear();
            }
            setText(elementLocation, text + System.currentTimeMillis());
        } catch (Exception ex) {
            commonLib.error(
                    elementName + " - element not visible. Not able to set Random Text by [--setRandomText--] Method");
        }
    }

    /**
     * This Method will enter the text
     *
     * @param elementLocation element locator
     * @param text            text to be enter
     */
    public void setText(By elementLocation, String text) {
        setText(elementLocation, text, 3);
    }

    /**
     * This method will enter the text
     *
     * @param elementLocation element locator
     * @param text            text to be enter
     * @param time            time in seconds
     */
    public void setText(By elementLocation, String text, int time) {
        message = elementName + " - element not visible. Not able to Enter Text.";
        try {
            if (isVisible(elementLocation, time)) {
                getElementfromBy(elementLocation).clear();
                getElementfromBy(elementLocation).sendKeys(text);
                commonLib.infoHighlight("Entered Value in field " + elementName + " - ", text,
                        ReportInfoMessageColorList.GOLD);
            } else {
                commonLib.fail(message, true);
            }
        } catch (Exception e) {
            commonLib.fail(
                    "CAUGHT EXCEPTION IN SET-TEXT METHOD for Element - " + elementName + BREAK_LINE + e.getMessage(),
                    true);
        }
    }

    /**
     * This Method wil click with the help of java script executor
     *
     * @param elementLocation element locator
     */
    public void clickByJavascriptExecutor(By elementLocation) {
        clickByJavascriptExecutor(elementLocation, 3, true);
    }

    /**
     * This Method wil click with the help of java script executor
     *
     * @param elementLocation                 element locator
     * @param time                            time in seconds
     * @param requireToReportFailForException require to report fail for exception
     */
    public void clickByJavascriptExecutor(By elementLocation, int time, boolean requireToReportFailForException) {
        message = elementName + " - element not visible. Not able to Click";
        try {
            if (isVisible(elementLocation, time) && isClickable(elementLocation, time)) {
                WebElement element = getElementfromBy(elementLocation);
                if (element.getTagName().equals("input") || element.getTagName().equals("button")) {
                    highLighterMethod(elementLocation);
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", element);
                } else {
                    highLighterMethod(elementLocation);
                    getElementfromBy(elementLocation).click();
                }
                commonLib.infoHighlight(elementName, " - Clicked.", ReportInfoMessageColorList.GREEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (requireToReportFailForException) {
                message = message + "</br>" + e.getMessage();
                commonLib.fail(message, true);
            }
        }
    }

    /**
     * This Method will let us know, element is clickable or not
     *
     * @param webelementBy element locator
     * @return return true false
     */
    public boolean isClickable(By webelementBy) {
        return isClickable(webelementBy, Integer.parseInt(constants.getValue(ApplicationConstants.GENERAL_WAIT_IN_SEC)));
    }

    /**
     * This Method will let us know, element is clickable or not
     *
     * @param webelementBy element locator
     * @param time         time in seconds
     * @return return true false
     */
    public boolean isClickable(By webelementBy, int time) {
        try {
            elementName = getElementNameFromAirtelByWrapper(webelementBy);
            wait = getWaitObject(time);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webelementBy));
            return element != null;
        } catch (Exception e) {
            return false;
        }
    }


}