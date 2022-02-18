package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ActiveVasWidgetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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
    if (isElementVisible(pageElements.activeVASWidget)) {
      commonLib.info("Active Vas Widget is Visible");
      return true;
    }
    return false;
  }

  /**
   * This method is used to check Active Vas Widget header visible or not
   * @return
   */
  public boolean isActiveVasWidgetHeaderVisible() {
    Boolean status = isElementVisible(pageElements.activeVasHeader);
    commonLib.info("Is Active VAS Widget Header visible : " + status);
    return status;
    }

  /**
   * This method is used to check Service Name label visible or not
   * @return
   */
  public boolean isServiceNameLabelVisible() {
    Boolean status = isElementVisible(pageElements.serviceName);
    commonLib.info("Is Service Name visible : " + status);
    return status;
  }

  /**
   * This method is used to check Start Date label visible or not
   * @return
   */
  public boolean isStartDateLabelVisible() {
    Boolean status = isElementVisible(pageElements.startDate);
    commonLib.info("Is Start Date visible : " + status);
    return status;
  }

  /**
   * This method is used to check Renew Date label visible or not
   * @return
   */
  public boolean isRenewalDateLabelVisible() {
    Boolean status = isElementVisible(pageElements.renewalDate);
    commonLib.info("Is Renewal Date visible : " + status);
    return status;
  }

  /**
   * This method is used to check Last Charged label visible or not
   * @return
   */
  public boolean isLastChargedLabelVisible() {
    Boolean status = isElementVisible(pageElements.lastCharged);
    commonLib.info("Is Last Charged visible : " + status);
    return status;
  }

  /**
   * This method is used to check Vendor label visible or not
   * @return
   */
  public boolean isVendorLabelVisible() {
    Boolean status = isElementVisible(pageElements.vendor);
    commonLib.info("Is Vendor visible : " + status);
    return status;
  }

  /**
   * This method is used to check Action visible or not
   * @return
   */
  public boolean isActionVisible() {
    Boolean status = isElementVisible(pageElements.action);
    commonLib.info("Is Action visible : " + status);
    return status;
  }

  /**
   * This method is used to check detailed icon visible or not
   * @return
   */
  public boolean isMoreIcon() {
    Boolean status = isElementVisible(pageElements.moreIcon);
    commonLib.info("Is detailed icon visible : " + status);
    return status;
  }

  /**
   * This method is used to click on more icon
   *
   * @return
   */
  public void clickMoreIcon() {
    commonLib.info("Going to click detailed icon");
    clickAndWaitForLoaderToBeRemoved(pageElements.moreIcon);
  }

  /**
   * This method is used to get Footer auuid of Active VAS widget
   *
   * @return
   */
  public String getFooterAuuid() {
    String result = getText(pageElements.footerAuuid);
    commonLib.info("Getting footer auuid :" + result);
    return result;
  }

  /**
   * This method is used to get Middle auuid of Active VAS widget
   *
   * @return
   */
  public String getMiddleAuuid() {
    String result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
    commonLib.info("Getting footer auuid :" + result);
    return result;
  }

  /**
   * This method is used to get no result found message
   *
   * @return String The String
   */
  public String getNoResultFoundMessage() {
    final String text = getText(pageElements.noResultFoundMessage);
    commonLib.info("Getting error message when there is no data available in Active Vas for the msisdn " + text);
    return text;
  }

  /**
   * This method is use to check no result found icon visible or not
   *
   * @return true/false
   */
  public boolean isNoResultFoundVisible() {
    final boolean visible = isElementVisible(pageElements.noResultFound);
    commonLib.info("Is error visible when is no data available in Active Vas for the msisdn : " + visible);
    return visible;
  }

  /**
   * This method is used to check error message visible or not
   *
   * @return true/false
   */
  public Boolean isWidgetErrorMessageVisible() {
    Boolean status=(isElementVisible(pageElements.widgetErrorMessage) || isElementVisible(pageElements.unableToFetchDataMessage));
    commonLib.info("Is error message visible when there is widget error" + status);
    return status;
  }

  /**
   * This method is used to get total number of data rows displayed on UI
   *
   * @return Integer
   */
  public int getNumberOfRows() {
    if (isVisibleContinueExecution(pageElements.rows))
    {
      List<WebElement> list = returnListOfElement(pageElements.rows);
      return list.size();
    } else {
      commonLib.warning("No Data is available under Active Vas Widget");
      return 0;
    }
  }

  /**
   * This method is used get first header value based on passed row and column
   * @param row
   * @param column
   * @return
   */
  public String getHeaderValue(int row, int column) {
    String result;
    result = getText(By.xpath(pageElements.dataRow + row + pageElements.valueColumns + column + pageElements.columnValue));
    commonLib.info("Reading Value(" + row + "): " + result);
    return result;
  }

  /**
   * This method is used get second row of header value based on passed row and column
   * @param row
   * @param column
   * @return
   */
  public String getHeaderSubValue(int row, int column) {
    String result;
    result = getText(By.xpath(pageElements.dataRow + row + pageElements.valueColumns + column + pageElements.columnSubValue));
    commonLib.info("Reading Value(" + row + "): " + result);
    return result;
  }

  /**
   * This method is used check Active Vas widget visible or not in Active Vas detailed Page
   */
  public Boolean isActiveVasDetailedWidgetVisible() {
    Boolean status = isElementVisible(pageElements.activeVASDetailedWidget);
    commonLib.info("Is Active Vas Widget is Visible :" + status);
    return status;
  }

  /**
   * This method is used check Available Vas widget visible or not in Active Vas detailed Page
   */
  public Boolean isAvailableVasWidgetVisible() {
    Boolean status = isElementVisible(pageElements.availableVasWidget);
    commonLib.info("Is Available Vas Widget is Visible :" + status);
    return status;
  }

  /**
   * This method is used check Subscription Vas widget visible or not in Active Vas detailed Page
   */
  public Boolean isSubscriptionVasWidgetVisible() {
    Boolean status = isElementVisible(pageElements.subscriptionVasWidget);
    commonLib.info("Is Subscription Vas Widget is Visible :" + status);
    return status;
  }

}
