package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ViewHistoryElements {

    public By paginationDetails = new AirtelByWrapper("paginationDetails -- InteractionTab", By.xpath("//*[contains(@class,'pagination-details')]"));
    public By actionTrailTab = new AirtelByWrapper("actionTrailTab -- ViewHistory",By.xpath("//*[contains(text(),'Action Trail')]"));

}
