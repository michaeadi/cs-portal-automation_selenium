package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class customerInteractionsSearchPOM extends BasePage {
    By numberSearch = By.xpath("//input[@name=\"search\"]");
    By searchButton = By.xpath("//button[@class=\"search-icon-btn\"]");

    public customerInteractionsSearchPOM(WebDriver driver) {
        super(driver);
    }

    public customerInteractionsSearchPOM enterNumber(String number) {
        writeText(numberSearch, number);
        return this;
    }

    public customerInteractionPagePOM clickOnSearch() {
        click(searchButton);
        return new customerInteractionPagePOM(driver);
    }
}
