package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.ProfileManagementPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class ProfileManagementPage extends BasePage {

    ProfileManagementPageElements pageElements;

    public ProfileManagementPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ProfileManagementPageElements.class);
    }

    public boolean isSubmitButtonEnable() {
        UtilsMethods.printInfoLog("Checking that is Submit Button Enable : " + checkState(pageElements.submitButton));
        return checkState(pageElements.submitButton);
    }

    public void clickingSubmitButton() {
        UtilsMethods.printInfoLog("Clicking Submit Button");
        click(pageElements.submitButton);
    }

    public String getConfigurationCol(int rowNumber) {
        List<WebElement> webElementConfigurationList = returnListOfElement(pageElements.configurationCol);
        UtilsMethods.printInfoLog("Getting Configuration Status of Profile : " + webElementConfigurationList.get(rowNumber).getText());
        return webElementConfigurationList.get(rowNumber).getText();
    }

    public String getRoleStatusCol(int rowNumber) {
        List<WebElement> webElementRoleStatusList = returnListOfElement(pageElements.roleStatusCol);
        UtilsMethods.printInfoLog("Getting Role Status of Profile : " + webElementRoleStatusList.get(rowNumber).getText());
        return webElementRoleStatusList.get(rowNumber).getText();
    }

    public void checkAllUnselectedWidgetsCheckboxes() {
        List<WebElement> checkboxWebElements = returnListOfElement(pageElements.widgetUnCheckbox);
        for (WebElement element : checkboxWebElements) {
            UtilsMethods.printInfoLog("Selecting Check Box");
            element.click();
        }
    }

    public int getNumberOfProfiles() {
        List<WebElement> rowsElements = returnListOfElement(pageElements.rows);
        return rowsElements.size();
    }

    public void viewRoleWithName(String Name) {
        List<WebElement> rowsElements = returnListOfElement(pageElements.rows);
        log.info("Clicking on view/Edit button for Role : " + Name);
        for (WebElement rowsElement : rowsElements) {
            if (rowsElement.findElement(pageElements.roleName).getText().equalsIgnoreCase(Name)) {
                log.info("Found Role " + Name);
                rowsElement.findElement(pageElements.viewEditButton).click();
                log.info("Clicked on view/Edit button for Role : " + Name);
                break;
            }
        }
    }

    public String getWidgetNameForOrder(int Order) {
        log.info("Getting name for widget with order no : " + Order);
        return getText(By.xpath("//span[@class=\"order-no ng-star-inserted\"and contains(text(),\"" + Order + "\")]/ancestor::div[@class=\"widgetDataTable\"]/div[1]"));
    }

    public int getWidgetRowsSize() {
        List<WebElement> widgetsRowsElements = returnListOfElement(pageElements.widgetsRows);
        log.info("Getting number of Widgets : " + widgetsRowsElements.size());
        return widgetsRowsElements.size();
    }

    public int getDisableWidgetRows() {
        List<WebElement> rows = returnListOfElement(pageElements.disabledWidget);
        log.info("Getting number of Widgets : " + rows.size());
        return rows.size();
    }

    public boolean isEditPageLoaded() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(pageElements.pageloadcheck)));
        return isElementVisible(pageElements.pageloadcheck);
    }

    public boolean isRoleConfigured(String Name) {
        List<WebElement> rowsElements = returnListOfElement(pageElements.rows);
        boolean isConfigured = false;
        for (WebElement rowsElement : rowsElements) {
            if (rowsElement.findElement(pageElements.roleName).getText().equalsIgnoreCase(Name)) {
                if (rowsElement.findElement(pageElements.profileConfigurationStatus).getText().equalsIgnoreCase("Configured")) {
                    isConfigured = true;
                }
                break;
            }
        }
        return isConfigured;
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
        UtilsMethods.printInfoLog("Waiting till the profile management page is loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.totalProfileHeading));
    }

    public boolean isProfileConfigFilterPresent() {
        UtilsMethods.printInfoLog("Checking is Config Filter is Visible on Profile Management Page : " + getConfigFilterElement().isEnabled());
        return getConfigFilterElement().isEnabled();
    }

    public boolean isRoleStatusFilterPresent() {
        UtilsMethods.printInfoLog("Checking is Role Status Filter is Visible on Profile Management Page : " + getRoleStatusFilterElement().isEnabled());
        return getRoleStatusFilterElement().isEnabled();
    }

    public int getNumberOfColumns() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//th//span"));
        UtilsMethods.printInfoLog("Getting total number of Columns available on Profile Management Page : " + webElements.size());
        return webElements.size();
    }

    public String getNameOfCol(int ColNo) {
        List<WebElement> webElements = returnListOfElement(By.xpath("//th//span"));
        try {
            UtilsMethods.printInfoLog("Getting Name of Columns available on Profile Management Page : " + webElements.get(ColNo).getText());
            return webElements.get(ColNo).getText();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            log.info("Total Number of columns available" + webElements.size());
            return "Not Found";
        }
    }

    public void clickOnOption(String option) {
        UtilsMethods.printInfoLog("Clicking on Filter : " + option);
        click(By.xpath("//span[@class=\"mat-option-text\" and text()=\" " + option + " \"]"));
    }
}
