package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AdjustmentHistoryPage {
    public By headerRow = By.xpath("//thead/tr//b");
    public By detailRow = By.xpath("//tbody/tr");
    public String adjustmentRow = "//app-action-trail-logging//tbody/tr[";
    public String adjustmentColumn = "]/td[";
}
