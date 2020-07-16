package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FilterTabPOM extends BasePage{

    By showQueueFilter= By.xpath("//mat-label[contains(text(),'Select Queue')]");
    By openQueueList= By.xpath("//div[5]//div[2]//div[1]//mat-form-field[1]//div[1]//div[1]//div[1]//mat-select[1]//div[1]//div[2]//div[1]");
    By SelectQueue= By.xpath("//span[contains(text(),' KYC ')]");
    By applyFilter= By.xpath("//button[@class=\"filter-button mat-button\"]");

    public FilterTabPOM(WebDriver driver) {
        super(driver);
    }

    public void clickQueueFilter() {
        click(openQueueList);
    }

    public void scrollToQueueFilter() throws InterruptedException {scrollToViewElement(showQueueFilter);}


    public void selectQueueByName(String queueName) throws InterruptedException {
        click(By.xpath("//span[contains(text(),' "+queueName+" ')]"));
    }

    public void clickApplyFilter(){click(applyFilter);}

    public void clickOutsideFilter(){
        clickOutside();
    }

}
