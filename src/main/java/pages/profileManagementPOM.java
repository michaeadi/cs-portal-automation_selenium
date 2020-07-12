package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class profileManagementPOM extends BasePage {
    By totalProfileHeading = By.xpath("//span[text()=\"Total Profiles\"]");

    public profileManagementPOM(WebDriver driver) {
        super(driver);
    }

    public WebElement getConfigFilterElement() {
        List<WebElement> webElements = driver.findElements(By.xpath("//*[starts-with(@id,'mat-select')]"));
        return webElements.get(0);
    }

    public WebElement getRoleStatusFilterElement() {
        List<WebElement> webElements = driver.findElements(By.xpath("//*[starts-with(@id,'mat-select')]"));
        return webElements.get(1);
    }

    public void waitTillPMPageLoads() {
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalProfileHeading));
    }

    public boolean isProfileConfigFilterPresent() {
        return getConfigFilterElement().isEnabled();
    }

    public boolean isRoleStatusFilterPresent() {
        return getRoleStatusFilterElement().isEnabled();
    }

    public int getNumberOfColumns() {
        List<WebElement> webElements = driver.findElements(By.xpath("//th//span"));
        return webElements.size();
    }

    public String getNameOfCol(int ColNo) {
        List<WebElement> webElements = driver.findElements(By.xpath("//th//span"));
        try {
            return webElements.get(ColNo).getText();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Total Number of columns available" + webElements.size());
            return "Not Found";
        }
    }
}
