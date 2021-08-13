package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class GrowlPage {
    public By growl = By.xpath("//*[contains(@class,'mat-simple-snackbar')]/span");
    public By dashboardGrowl=By.xpath("//*/app-toast-component/p");
}
