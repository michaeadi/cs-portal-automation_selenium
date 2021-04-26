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

    public boolean isSubmitButtonEnable() {
        final boolean state = checkState(pageElements.submitButton);
        commonLib.info("Checking that is Submit Button Enable : " + state);
        return state;
    }

    public void clickingSubmitButton() {
        commonLib.info("Clicking Submit Button");
        click(pageElements.submitButton);
    }

    public String getConfigurationCol(int rowNumber) {
        List<WebElement> webElementConfigurationList = returnListOfElement(pageElements.configurationCol);
        final String text = webElementConfigurationList.get(rowNumber).getText();
        commonLib.info("Getting Configuration Status of Profile : " + text);
        return text;
    }

    public String getRoleStatusCol(int rowNumber) {
        List<WebElement> webElementRoleStatusList = returnListOfElement(pageElements.roleStatusCol);
        final String text = webElementRoleStatusList.get(rowNumber).getText();
        commonLib.info("Getting Role Status of Profile : " + text);
        return text;
    }

    public void checkAllUnselectedWidgetsCheckboxes() {
        List<WebElement> checkboxWebElements = returnListOfElement(pageElements.widgetUnCheckbox);
        for (WebElement element : checkboxWebElements) {
            commonLib.info("Selecting Check Box");
            element.click();
        }
    }

    public int getNumberOfProfiles() {
        List<WebElement> rowsElements = returnListOfElement(pageElements.rows);
        return rowsElements.size();
    }

    public void viewRoleWithName(String name) {
        List<WebElement> rowsElements = returnListOfElement(pageElements.rows);
        log.info("Clicking on view/Edit button for Role : " + name);
        for (WebElement rowsElement : rowsElements) {
            if (rowsElement.findElement(pageElements.roleName).getText().equalsIgnoreCase(name)) {
                log.info("Found Role " + name);
                rowsElement.findElement(pageElements.viewEditButton).click();
                log.info("Clicked on view/Edit button for Role : " + name);
                break;
            }
        }
    }

    public String getWidgetNameForOrder(int order) {
        log.info("Getting name for widget with order no : " + order);
        return getText(By.xpath("//span[@class=\"order-no ng-star-inserted\"and contains(text(),\"" + order + "\")]/ancestor::div[@class=\"widgetDataTable\"]/div[1]"));
    }

    public int getWidgetRowsSize() {
        List<WebElement> widgetsRowsElements = returnListOfElement(pageElements.widgetsRows);
        final int size = widgetsRowsElements.size();
        log.info("Getting number of Widgets : " + size);
        return size;
    }

    public int getDisableWidgetRows() {
        List<WebElement> rows = returnListOfElement(pageElements.disabledWidget);
        final int size = rows.size();
        log.info("Getting number of Widgets : " + size);
        return size;
    }

    public boolean isEditPageLoaded() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(pageElements.pageloadcheck)));
        return isElementVisible(pageElements.pageloadcheck);
    }

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

    public WebElement getConfigFilterElement() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//div[@class=\"mat-select-value\"]"));
        return webElements.get(0);
    }

    public WebElement getRoleStatusFilterElement() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//div[@class=\"mat-select-value\"]"));
        return webElements.get(1);
    }

    public void waitTillPMPageLoads() {
        commonLib.info("Waiting till the profile management page is loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.totalProfileHeading));
    }

    public boolean isProfileConfigFilterPresent() {
        final boolean enabled = getConfigFilterElement().isEnabled();
        commonLib.info("Checking is Config Filter is Visible on Profile Management Page : " + enabled);
        return enabled;
    }

    public boolean isRoleStatusFilterPresent() {
        final boolean enabled = getRoleStatusFilterElement().isEnabled();
        commonLib.info("Checking is Role Status Filter is Visible on Profile Management Page : " + enabled);
        return enabled;
    }

    public int getNumberOfColumns() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//th//span"));
        final int size = webElements.size();
        commonLib.info("Getting total number of Columns available on Profile Management Page : " + size);
        return size;
    }

    public String getNameOfCol(int colNo) {
        List<WebElement> webElements = returnListOfElement(By.xpath("//th//span"));
        try {
            commonLib.info("Getting Name of Columns available on Profile Management Page : " + webElements.get(colNo).getText());
            return webElements.get(colNo).getText();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            log.info("Total Number of columns available" + webElements.size());
            return "Not Found";
        }
    }

    public void clickOnOption(String option) {
        commonLib.info("Clicking on Filter : " + option);
        click(By.xpath("//span[@class=\"mat-option-text\" and text()=\" " + option + " \"]"));
    }
}
