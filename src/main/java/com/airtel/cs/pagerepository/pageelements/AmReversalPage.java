package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AmReversalPage {
    public String valueRow = "//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--data-list row-border']";
    public String columnText = "]//img[contains(@src,'reversal.svg')]";
    public By selectReasonFromDropdown=By.xpath("//span[contains(text(),'Customer Request')]");
    public By totalRows = By.xpath("//div[@data-csautomation-key='paginationResult']");
    public By crossIcon = By.xpath("//div[contains(@class,'main-container__header success')]//mat-icon[contains(text(),'close')]");
    public By nextPage = By.xpath("//*[contains(@class,'pagination-next')]/a");
}
