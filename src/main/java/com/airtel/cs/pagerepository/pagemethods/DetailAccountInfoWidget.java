package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DetailAccountInfoWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class DetailAccountInfoWidget extends BasePage {

    DetailAccountInfoWidgetPage pageElements;
    private final String SCROLL_TO_WIDGET_MESSAGE = "Not able scroll to the widget";

    public DetailAccountInfoWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DetailAccountInfoWidgetPage.class);
    }

    /**
     * This method use to check whether account intformation widget display or not
     *
     * @return true/false
     */
    public Boolean isDetailedAccountInformationWidgetDisplay() {
        commonLib.info("Checking that Detailed Account Information widget is Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.getTitle);
            status = isElementVisible(pageElements.getTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE, true);
        }
        return status;
    }

    /**
     * This method use to get account information widget more icon displayed or not
     *
     * @return Boolean The  data value
     */
    public Boolean isActionIconVisibleOnAccountInfo() {
        Boolean status = isElementVisible(pageElements.accountInfoIcon);
        commonLib.info("Checking more icon is visible on detailed account information: '" + status);
        return status;
    }

    /**
     * This method is used to check account information widget in detail account info
     * @return
     */
    public String getAccountInfoDetailWidget() {
        commonLib.info(getText(pageElements.accountInfoDetailWidget));
        return getText(pageElements.accountInfoDetailWidget);
    }

    /**
     * This method use to click menu button for account info detail page
     */
    public void openAccountInformationDetailPage() {
        commonLib.info("Opening More under account information");
        if(isVisible(pageElements.accountInfoIcon)){
            clickAndWaitForLoaderToBeRemoved(pageElements.accountInfoIcon);
        }else{
            commonLib.error("Unable to open account info detail page");
        }
    }


}