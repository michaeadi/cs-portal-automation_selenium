package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CRBTWidgetPOM extends BasePage {

    By titleCRBT= By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]");
    By ticketIcon=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//child::span/img");
    By myTuneTab=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@role='tab'][1]/div");
    By top20TuneTab=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@role='tab'][2]/div");
    By searchTuneTab=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@role='tab'][3]/div");
    By noResultImg=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//img[@class=\"ng-star-inserted\"]");
    By noResultMessage=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//span/span");
    By widgetError=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//div[@class='image-container']//img");
    By searchBox=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//input");
    By searchBtn=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//input//following-sibling::img");
    By searchOptionBtn=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"search-area\"]//span");
    By option1=By.xpath("//div[@class=\"mat-menu-content\"]/button[1]/span");
    By option2=By.xpath("//div[@class=\"mat-menu-content\"]/button[2]/span");


    public CRBTWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isCRBTWidgetDisplay(){
        printInfoLog("Is CRBT Widget Display: "+checkState(titleCRBT));
        return checkState(titleCRBT);
    }

    public void clickTicketIcon(){
        printInfoLog("Clicking Ticket Icon");
        click(ticketIcon);
    }

    public boolean isMyTuneTabDisplay(){
        printInfoLog("Is My Tune Tab Display: "+checkState(myTuneTab));
        return checkState(myTuneTab);
    }

    public boolean isTop20TuneTabDisplay(){
        printInfoLog("Is Top 20 Tune Tab Display: "+checkState(top20TuneTab));
        return checkState(top20TuneTab);
    }

    public boolean isSearchTuneTabDisplay(){
        printInfoLog("Is Search Tune Tab Display: "+checkState(searchTuneTab));
        return checkState(searchTuneTab);
    }

    public boolean isTabSelected(int i){
        By tab=By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@role='tab']["+i+"]");
        return driver.findElement(tab).getAttribute("aria-selected")=="true" ? true :false;
    }



}
