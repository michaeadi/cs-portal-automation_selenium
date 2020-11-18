package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class profileManagementPOM extends BasePage {
    By totalProfileHeading = By.xpath("//span[text()=\"Total Profiles\"]");
    By rows = By.xpath("//tr[@class=\"agent-list-container__agent-list--list-row ng-star-inserted\"]");
    By pageloadcheck = By.xpath("//div[contains(text(),\"Widget Name\")]");
    By roleName = By.xpath("td[1]/span");
    By viewEditButton = By.xpath("td[5]/span/div/div/img");
    By profileConfigurationStatus = By.xpath("td[2]/span");
    By widgetName = By.xpath("//span[@class=\"order-no ng-star-inserted\"and contains(text(),\"1\")]/ancestor::div[@class=\"widgetDataTable\"]/div[1]");
    By widgetsRows = By.xpath("//div[@class=\"data-list ng-star-inserted\"]");
    By widgetUnCheckbox = By.xpath("//div[@class='mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin']");
    By submitButton = By.xpath("//button[contains(text(),\"Submit\")]");
    By configurationCol = By.xpath("//tr//td[2]");
    By roleStatusCol = By.xpath("//tr//td[3]");

    public boolean isSubmitButtonEnable() {
        log.info("Checking that is Submit Button Enable : " + checkState(submitButton));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Submit Button Enable : " + checkState(submitButton));
        return checkState(submitButton);
    }

    public void clickingSubmitButton() {
        log.info("Clciking Submit Button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clciking Submit Button");
        click(submitButton);
    }

    public String getConfigurationCol(int rowNumber) {
        List<WebElement> webElementConfigurationList = returnListOfElement(configurationCol);
        log.info("Getting Configuration Status of Profile : " + webElementConfigurationList.get(rowNumber).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Configuration Status of Profile : " + webElementConfigurationList.get(rowNumber).getText());
        return webElementConfigurationList.get(rowNumber).getText();
    }


    public String getRoleStatusCol(int rowNumber) {
        List<WebElement> webElementRoleStatusList = returnListOfElement(roleStatusCol);
        log.info("Getting Role Status of Profile : " + webElementRoleStatusList.get(rowNumber).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Role Status of Profile : " + webElementRoleStatusList.get(rowNumber).getText());
        return webElementRoleStatusList.get(rowNumber).getText();
    }

    public void checkAllUnselectedWidgetsCheckboxes() {
        List<WebElement> checkboxWebElements = returnListOfElement(widgetUnCheckbox);
        for (WebElement element : checkboxWebElements) {
            log.info("Selecting Check Box");
            ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting Check Box");
            element.click();
        }
    }

    public int getNumberOfProfiles() {
        List<WebElement> rowsElements = returnListOfElement(rows);
        return rowsElements.size();
    }

    public void viewRoleWithName(String Name) {
        List<WebElement> rowsElements = returnListOfElement(rows);
        log.info("Clicking on view/Edit button for Role : " + Name);
        System.out.println(rowsElements.size());
        for (WebElement rowsElement : rowsElements) {
            if (rowsElement.findElement(roleName).getText().equalsIgnoreCase(Name)) {
                log.info("Found Role " + Name);
                rowsElement.findElement(viewEditButton).click();
                log.info("Clicked on view/Edit button for Role : " + Name);
                break;
            }
        }
    }

    public String getWidgetNameForOrder(int Order) {
        log.info("Getting name for widget with order no : " + Order);
        return readText(By.xpath("//span[@class=\"order-no ng-star-inserted\"and contains(text(),\"" + Order + "\")]/ancestor::div[@class=\"widgetDataTable\"]/div[1]"));
    }

    public int getWidgetRowsSize() {
        List<WebElement> widgetsRowsElements = returnListOfElement(widgetsRows);
        log.info("Getting number of Widgets : " + widgetsRowsElements.size());
        return widgetsRowsElements.size();
    }

    public boolean isEditPageLoaded() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(pageloadcheck)));
        return isElementVisible(pageloadcheck);
    }

    public boolean isRoleConfigured(String Name) {
        List<WebElement> rowsElements = returnListOfElement(rows);
        boolean isConfigured = false;
        for (WebElement rowsElement : rowsElements) {
            if (rowsElement.findElement(roleName).getText().equalsIgnoreCase(Name)) {
                if (rowsElement.findElement(profileConfigurationStatus).getText().equalsIgnoreCase("Configured")) {
                    isConfigured = true;
                }
                break;
            }
        }
        return isConfigured;
    }

    public profileManagementPOM(WebDriver driver) {
        super(driver);
    }

    public WebElement getConfigFilterElement() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//div[@class=\"mat-select-value\"]"));

        return webElements.get(0);
    }

    public WebElement getRoleStatusFilterElement() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//div[@class=\"mat-select-value\"]"));
        return webElements.get(1);
    }

    public void waitTillPMPageLoads() {
        log.info("Waiting till the profile management page is loaded");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Waiting till the profile management page is loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalProfileHeading));
    }

    public boolean isProfileConfigFilterPresent() {
        log.info("Checking is Config Filter is Visible on Profile Management Page : " + getConfigFilterElement().isEnabled());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Config Filter is Visible on Profile Management Page : " + getConfigFilterElement().isEnabled());
        return getConfigFilterElement().isEnabled();
    }

    public boolean isRoleStatusFilterPresent() {
        log.info("Checking is Role Status Filter is Visible on Profile Management Page : " + getRoleStatusFilterElement().isEnabled());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Role Status Filter is Visible on Profile Management Page : " + getRoleStatusFilterElement().isEnabled());
        return getRoleStatusFilterElement().isEnabled();
    }

    public int getNumberOfColumns() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//th//span"));
        log.info("Getting total number of Columns available on Profile Management Page : " + webElements.size());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting total number of Columns available on Profile Management Page : " + webElements.size());
        return webElements.size();
    }

    public String getNameOfCol(int ColNo) {
        List<WebElement> webElements = returnListOfElement(By.xpath("//th//span"));
        try {
            log.info("Getting Name of Columns available on Profile Management Page : " + webElements.get(ColNo).getText());
            ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Name of Columns available on Profile Management Page : " + webElements.get(ColNo).getText());
            return webElements.get(ColNo).getText();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Total Number of columns available" + webElements.size());
            return "Not Found";
        }
    }

    public void clickOnOption(String option) {
        log.info("Clicking on Filter : " + option);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Filter : " + option);
        click(By.xpath("//span[@class=\"mat-option-text\" and text()=\" " + option + " \"]"));
    }
}
