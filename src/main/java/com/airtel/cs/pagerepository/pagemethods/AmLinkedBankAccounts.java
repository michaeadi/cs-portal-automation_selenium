package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AmLinkedBankAccountsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AmLinkedBankAccounts extends BasePage {
    AmLinkedBankAccountsPage pageElements;

    public AmLinkedBankAccounts(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AmLinkedBankAccountsPage.class);
    }


}
