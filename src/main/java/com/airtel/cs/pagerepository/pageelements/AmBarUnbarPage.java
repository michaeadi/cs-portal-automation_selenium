package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AmBarUnbarPage {
    public By selectReasonFromDropdown=By.xpath("//span[contains(text(),'Suspected Fraud')]");
    public By selectBarTypeFromDropdown=By.xpath("//span[contains(text(),'Sender')]");
}
