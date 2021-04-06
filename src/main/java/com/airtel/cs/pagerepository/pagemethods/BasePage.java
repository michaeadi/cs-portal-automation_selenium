package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.driver.Driver1;
import com.airtel.cs.pagerepository.pageelements.AirtelByWrapper;
import com.airtel.cs.pagerepository.pageelements.BasePageElements;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Log4j2
public class BasePage extends Driver1 {
    public static Properties config = Driver1.config;
    public WebDriver driver;
    public Wait<WebDriver> wait;
    public Wait<WebDriver> wait1;
    public JavascriptExecutor js;
    BasePageElements basePageElements;
    public static final APIEndPoints api = new APIEndPoints();


    //Constructor
    public BasePage(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> expectation = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").toString().equals("complete");
        wait1 = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Integer.parseInt(Driver1.config.getProperty("GeneralWaitInSeconds"))))
                .pollingEvery(Duration.ofSeconds(Integer.parseInt(Driver1.config.getProperty("PoolingWaitInSeconds"))))
                .ignoring(NoSuchElementException.class);
        wait1.until(expectation);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Driver1.config.getProperty("GeneralWaitInSeconds"))));
        basePageElements = new BasePageElements();
    }


    public void waitTillLoaderGetsRemoved() {
        UtilsMethods.printInfoLog("Waiting for loader to be removed");
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(basePageElements.loader1));
        UtilsMethods.printInfoLog("Loader Removed");
    }

    public void waitTillOverlayGetsRemoved() {
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(basePageElements.overlay));
    }

    public void waitTillTimeLineGetsRemoved() {
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(basePageElements.timeLine));
    }

    //Click Method
    public void click(By elementLocation) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(elementLocation));
            highLighterMethod(elementLocation);
            driver.findElement(elementLocation).click();
            log.info("Clicking on element" + elementLocation.toString());
        } catch (ElementClickInterceptedException e) {
            waitTillLoaderGetsRemoved();
            driver.findElement(elementLocation).click();
            log.info("Clicking Again on element" + elementLocation.toString());
        }
    }

    public void scrollToViewElement(By element) throws InterruptedException {
        WebElement element1 = driver.findElement(element);
        waitVisibility(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
        Thread.sleep(500);
    }

    //Write Text
    public void writeText(By elementLocation, String text) {
        waitVisibility(elementLocation);
        highLighterMethod(elementLocation);
        driver.findElement(elementLocation).sendKeys(text);
        log.info("Writing " + text + "to  " + elementLocation.toString());

    }

    //Read Text
    public String readText(By elementLocation) {
        waitVisibility(elementLocation);
        //highLighterMethod(elementLocation);
        return driver.findElement(elementLocation).getText();
    }

    //HighlightElement
    public void highLighterMethod(By element) {
        waitTillLoaderGetsRemoved();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid black;');", driver.findElement(element));
    }

    //Check the state of element
    public boolean checkState(By elementLocation) {
        try {
            // waitVisibility(elementLocation);
            highLighterMethod(elementLocation);
            return driver.findElement(elementLocation).isEnabled();
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }

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

    //Hover and click on Element Using ACTIONS class
    public void hoverAndClick(By elementLocation) {
        Actions actions = new Actions(driver);
        waitVisibility(elementLocation);
        WebElement target = driver.findElement(elementLocation);
        actions.moveToElement(target).build().perform();
    }

    public CustomerProfilePage openingCustomerInteractionDashboard() {
        log.info("Opening Customer Interactions Dashboard");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Customer Interactions Dashboard");
        click(basePageElements.home);
        waitTillLoaderGetsRemoved();
        return new CustomerProfilePage(driver);
    }

    public String getToastMessage() {
        String message = readText(basePageElements.toastMessage);
        UtilsMethods.printInfoLog(message);
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
        log.info("Clear Search Box");
        driver.findElement(element).clear();
    }

    public boolean validateFilter(By element, String text) {
        List<WebElement> list = returnListOfElement(element);
        log.info("Validating Filter");
        for (WebElement x : list) {
            log.info("Element Text : " + x.getText());
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
            UtilsMethods.printInfoLog("Not able to Fetch List of Elements :" + e.fillInStackTrace());
        }
        return list;
    }

    public String readTextOnRows(By elementLocation, int row) {
        waitVisibility(elementLocation);
        return driver.findElements(elementLocation).get(row).getText();
    }

    public String readOnRowColumn(By rowLocation, By columnLocation, int row, int column) {
        waitVisibility(rowLocation);
        log.info("Row Size: " + driver.findElements(rowLocation).size());
        log.info("Column Size: " + driver.findElement(rowLocation).findElements(columnLocation).size());
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
            log.error("Element Not Found", e);
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
        String attributeValue = "";
        try {
            ElementName = getElementNameFromAirtelByWrapper(elementLocation);
            Message = ElementName
                    + " - element not visible. Not able to Get Attribute by Method - [--getAttribute(By elementLocation, String attributeName)--]";
            if (requireWait) {
                if (isVisible(elementLocation, 5)) {
                    return getElementfromBy(elementLocation).getAttribute(attributeName).trim();
                } else {
                    commonLib.warning(Message);
                    return Message;
                }
            } else {
                attributeValue = getElementfromBy(elementLocation).getAttribute(attributeName);
                attributeValue = (attributeValue == null) ? ""
                        : (attributeValue == "") ? attributeValue : attributeValue.trim();
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
            commonLib.warning(Message + "</br>" + "Exception - " + e.getMessage());
            return Message;
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
        ElementName = "---this Element is not an instance of ameyo wrapper please add Valid Name of this Element---";
        try {
            if (element instanceof AirtelByWrapper) {
                ElementName = ((AirtelByWrapper) element).getDescription();
            }
        } catch (Exception e) {
            commonLib.warning("Caught some Exception inside method - [--getElementName--] " + e.getMessage());
        }
        return ElementName;
    }

    /**
     * CREATED THIS TO BYPASS THE DEFAULT 10 SEC WAIT CAUSED BY ISDISPLAYED METHOD
     *
     * @param webelementBy element locator
     * @return will return true false
     */
    public boolean isVisible(By webelementBy) {
        return isVisible(webelementBy, 2);
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
            ElementName = getElementNameFromAirtelByWrapper(webelementBy);
            Wait<WebDriver> driverWait = getWaitObject(time);
            WebElement webElement = driverWait.until(ExpectedConditions.visibilityOfElementLocated(webelementBy));
            return webElement != null;
        } catch (Exception e) {
            log.error("Element Not Visible", e);
            return false;
        }
    }

    public Wait<WebDriver> getWaitObject(int maxWaitFor) {
        FluentWait wait = null;
        try {
            wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(maxWaitFor))
                    .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
        } catch (Exception e) {
            e.getStackTrace();
            commonLib.warning(
                    "Exception in method - | methodName | " + "</br>" + "Exception Message - " + e.getMessage());
        }
        return wait;
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
            if (requiredClearFieldYesNo.toLowerCase().equalsIgnoreCase("yes")) {
                getElementfromBy(elementLocation).clear();
            }
            setText(elementLocation, text + System.currentTimeMillis());
        } catch (Exception ex) {
            commonLib.error(
                    ElementName + " - element not visible. Not able to set Random Text by [--setRandomText--] Method");
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
        Message = ElementName + " - element not visible. Not able to Enter Text.";
        try {
            if (isVisible(elementLocation, time)) {
                getElementfromBy(elementLocation).clear();
                getElementfromBy(elementLocation).sendKeys(text);
                commonLib.infoHighlight("Entered Value in field " + ElementName + " - ", text,
                        ReportInfoMessageColorList.GOLD);
            } else {
                commonLib.fail(Message, true);
            }
        } catch (Exception e) {
            commonLib.fail(
                    "CAUGHT EXCEPTION IN SET-TEXT METHOD for Element - " + ElementName + "</br>" + e.getMessage(),
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
        Message = ElementName + " - element not visible. Not able to Click";
        try {
            if (isVisible(elementLocation, time) && isClickable(elementLocation, time)) {
                WebElement element = getElementfromBy(elementLocation);
                if ((element.getTagName()).equals("input")) {
                    highLighterMethod(elementLocation);
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", element);
                } else {
                    highLighterMethod(elementLocation);
                    getElementfromBy(elementLocation).click();
                }
                commonLib.infoHighlight(ElementName, " - Clicked.", ReportInfoMessageColorList.GREEN);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (requireToReportFailForException) {
                Message = Message + "</br>" + e.getMessage();
                commonLib.fail(Message, true);
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
        return isClickable(webelementBy, 2);
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
            ElementName = getElementNameFromAirtelByWrapper(webelementBy);
            Wait<WebDriver> wait = getWaitObject(time);
            WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(webelementBy));
            if (Element != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}