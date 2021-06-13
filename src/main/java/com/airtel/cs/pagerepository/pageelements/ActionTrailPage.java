package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ActionTrailPage {
    public By headRow = By.xpath("//thead/tr//b");
    public By detailRow = By.xpath("//tbody/tr");
    public String actionTrailRow = "//app-action-trail-logging//tbody/tr[";
    public String actionTrailColumn = "]/td[";
}