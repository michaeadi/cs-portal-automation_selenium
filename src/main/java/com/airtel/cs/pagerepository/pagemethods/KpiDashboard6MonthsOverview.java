package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboard6MonthsOverviewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class KpiDashboard6MonthsOverview  extends BasePage {
    KpiDashboard6MonthsOverviewPage pageElements;
    public KpiDashboard6MonthsOverview(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, KpiDashboard6MonthsOverviewPage.class);
    }
}
