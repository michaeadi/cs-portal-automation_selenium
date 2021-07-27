package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ActiveVasWidgetPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ActiveVasWidget extends BasePage {

  ActiveVasWidgetPage pageElements;

  public ActiveVasWidget(WebDriver driver) {
    super(driver);
    pageElements = PageFactory.initElements(driver, ActiveVasWidgetPage.class);
  }

  /*
    This Method will let us know Active Vas Widget is visible or not
     */
  public boolean isActiveVasWidgetVisible() {
    if (isElementVisible(pageElements.activeVasTransactionHeader)) {
      commonLib.info("Active Vas Widget is Visible");
      return true;
    }
    return false;
  }
}