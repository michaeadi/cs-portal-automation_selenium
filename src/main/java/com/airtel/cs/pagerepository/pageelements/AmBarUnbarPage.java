package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AmBarUnbarPage {
    public By selectReasonFromDropdown=By.xpath("//*[contains(text(),'BAR')]/ancestor::div/following-sibling::div//mat-option[1]");
    public By selectBarTypeFromDropdown=By.xpath("//*[contains(text(),'BAR')]/ancestor::div/following-sibling::div//mat-option[1]");
}
