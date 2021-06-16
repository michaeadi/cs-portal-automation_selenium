package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ProfileManagementPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class ProfileManagement extends BasePage {

    ProfileManagementPage pageElements;

    public ProfileManagement(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ProfileManagementPage.class);
    }

    /**
     * This method is use to check submit button enable or not
     * @return true/false
     */
    public boolean isSubmitButtonEnable() {
        final boolean state = isEnabled(pageElements.submitButton);
        commonLib.info("Checking that is Submit Button Enable : " + state);
        return state;
    }

    /**
     * This method is use to click submit button
     */
    public void clickingSubmitButton() {
        commonLib.info("Clicking Submit Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.submitButton);
    }

    /**
     * This method is use to get configuration column value based on row
     * @param rowNumber The row number
     * @return String The value
     */
    public String getConfigurationCol(int rowNumber) {
        List<WebElement> webElementConfigurationList = returnListOfElement(pageElements.configurationCol);
        final String text = webElementConfigurationList.get(rowNumber).getText();
        commonLib.info("Getting Configuration Status of Profile : " + text);
        return text;
    }

    /**
     * This method is use to get role status column value based on row
     * @param rowNumber The row number
     * @return String The value
     */
    public String getRoleStatusCol(int rowNumber) {
        List<WebElement> webElementRoleStatusList = returnListOfElement(pageElements.roleStatusCol);
        final String text = webElementRoleStatusList.get(rowNumber).getText();
        commonLib.info("Getting Role Status of Profile : " + text);
        return text;
    }

    /**
     * This method is use to check all uncheck widget
     */
    public void checkAllUnselectedWidgetsCheckboxes() {
        List<WebElement> checkboxWebElements = returnListOfElement(pageElements.widgetUnCheckbox);
        for (WebElement element : checkboxWebElements) {
            commonLib.info("Selecting Check Box");
            element.click();
        }
    }

    /**
     * This method is use to get total number of roles
     */
    public int getNumberOfProfiles() {
        List<WebElement> rowsElements = returnListOfElement(pageElements.rows);
        return rowsElements.size();
    }

    /**
     * This method is use to click edit role button based on role name
     * @param name The role name
     */
    public void viewRoleWithName(String name) {
        List<WebElement> rowsElements = returnListOfElement(pageElements.rows);
        commonLib.info("Clicking on view/Edit button for Role : " + name);
        for (WebElement rowsElement : rowsElements) {
            if (rowsElement.findElement(pageElements.roleName).getText().equalsIgnoreCase(name)) {
                commonLib.info("Found Role " + name);
                rowsElement.findElement(pageElements.viewEditButton).click();
                commonLib.info("Clicked on view/Edit button for Role : " + name);
                break;
            }
        }
    }

    /**
     * This method is use to get widget name based on order of widget display
     * @param order The order number
     * @return String The value
     */
    public String getWidgetNameForOrder(int order) {
        commonLib.info("Getting name for widget with order no : " + order);
        return getText(By.xpath( pageElements.widgetOrder+ order + pageElements.widgetOrderName));
    }

    /**
     * This method use to get total number of widget display
     * @return Integer The Size
     */
    public int getWidgetRowsSize() {
        List<WebElement> widgetsRowsElements = returnListOfElement(pageElements.widgetsRows);
        final int size = widgetsRowsElements.size();
        commonLib.info("Getting number of Widgets : " + size);
        return size;
    }

    /**
     * This method use to get total number of unselected widget display
     * @return Integer The Size
     */
    public int getDisableWidgetRows() {
        List<WebElement> rows = returnListOfElement(pageElements.disabledWidget);
        final int size = rows.size();
        commonLib.info("Getting number of Widgets : " + size);
        return size;
    }

    /**
     * This method is use to check edit page load or not
     * @return true/false
     */
    public boolean isEditPageLoaded() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(pageElements.pageloadcheck)));
        return isElementVisible(pageElements.pageloadcheck);
    }

    /**
     * This method is use to check role configure or not based on role name
     * @param name The role name
     * @return true/false
     */
    public boolean isRoleConfigured(String name) {
        List<WebElement> rowsElements = returnListOfElement(pageElements.rows);
        boolean isConfigured = false;
        for (WebElement rowsElement : rowsElements) {
            if (rowsElement.findElement(pageElements.roleName).getText().equalsIgnoreCase(name)) {
                if (rowsElement.findElement(pageElements.profileConfigurationStatus).getText().equalsIgnoreCase("Configured")) {
                    isConfigured = true;
                }
                break;
            }
        }
        return isConfigured;
    }

    /**
     * This method is use to get list of state associated with configured  status filter
     * @return WebElement The element
     */
    public WebElement getConfigFilterElement() {
        List<WebElement> webElements = returnListOfElement(By.xpath(pageElements.selectValue));
        return webElements.get(0);
    }

    /**
     * This method is use to get list of state associated with role status filter
     * @return WebElement The element
     */
    public WebElement getRoleStatusFilterElement() {
        List<WebElement> webElements = returnListOfElement(By.xpath(pageElements.selectValue));
        return webElements.get(1);
    }

    /**
     * This method is use to wait until UM module load
     */
    public void waitTillPMPageLoads() {
        commonLib.info("Waiting till the profile management page is loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.totalProfileHeading));
    }

    /**
     * This method use to check filter by config state display or not
     * @return true/false
     */
    public boolean isProfileConfigFilterPresent() {
        final boolean enabled = getConfigFilterElement().isEnabled();
        commonLib.info("Checking is Config Filter is Visible on Profile Management Page : " + enabled);
        return enabled;
    }

    /**
     * This method use to check filter by role status display or not
     * @return true/false
     */
    public boolean isRoleStatusFilterPresent() {
        final boolean enabled = getRoleStatusFilterElement().isEnabled();
        commonLib.info("Checking is Role Status Filter is Visible on Profile Management Page : " + enabled);
        return enabled;
    }

    /**
     * This method use to get total number of columns present on profile management page
     * @return true/false
     */
    public int getNumberOfColumns() {
        List<WebElement> webElements = returnListOfElement(By.xpath(pageElements.columnNumber));
        final int size = webElements.size();
        commonLib.info("Getting total number of Columns available on Profile Management Page : " + size);
        return size;
    }

    /**
     * This method use to get name of column name from profile management page based on column number
     * @param colNo The column Number
     * @return String The value
     */
    public String getNameOfCol(int colNo) {
        List<WebElement> webElements = returnListOfElement(By.xpath(pageElements.columnNumber));
        try {
            commonLib.info("Getting Name of Columns available on Profile Management Page : " + webElements.get(colNo).getText());
            return webElements.get(colNo).getText();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            commonLib.info("Total Number of columns available" + webElements.size());
            return "Not Found";
        }
    }

    /**
     * This method is use to click on option by option name
     * @param option The option name
     */
    public void clickOnOption(String option) {
        commonLib.info("Clicking on Filter : " + option);
        clickAndWaitForLoaderToBeRemoved(By.xpath( pageElements.optionText+ option + " ']"));
    }
}
