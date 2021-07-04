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
    boolean elementVisible = false;
    if (isVisible(pageElements.activeVasTransactionHeader)) {
      elementVisible = isElementVisible(pageElements.activeVasTransactionHeader);
      commonLib.info("Checking is Active Vas Widget Visible? " + elementVisible);
    }
    return elementVisible;
  }
}