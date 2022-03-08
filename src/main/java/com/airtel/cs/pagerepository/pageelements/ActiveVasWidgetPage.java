package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ActiveVasWidgetPage {
  public By activeVasHeader = By.xpath("//div[@data-csautomation-key='VAS']//span[contains(@class,'widget_header_label')]");
  public By activeVASWidget=By.xpath("//div[@data-csautomation-key='VAS']");
  public By serviceName=By.xpath("//div[@data-csautomation-key='VAS']//span[contains(text(),'Service Name')]");
  public By startDate = By.xpath("//div[@data-csautomation-key='VAS']//span[contains(text(),'Start Date')]");
  public By renewalDate = By.xpath("//span[contains(text(),'Renewal Date')]");
  public By lastCharged = By.xpath("//span[contains(text(),'Last Charged')]");
  public By vendor = By.xpath("//div[@data-csautomation-key='VAS']//span[contains(text(),'Vendor')]");
  public By action = By.xpath("//div[@data-csautomation-key='VAS']//span[contains(text(),'Action')]");
  public By moreIcon = By.xpath("//div[@data-csautomation-key='VAS']//img[@data-csautomation-key='menuButton']");
  public By footerAuuid = By.xpath("//div[@data-csautomation-key='VAS']//div[contains(@class,'auuid-container')]");
  public By middleAuuid=By.xpath("//div[@data-csautomation-key='VAS']");
  public By noResultFound = By.xpath("//div[@data-csautomation-key='VAS']//div[@class='no-result-found ng-star-inserted']//img");
  public By noResultFoundMessage = By.xpath("//div[@data-csautomation-key='VAS']//span[contains(text(),'No Result found')]");
  public By widgetErrorMessage=By.xpath("//div[@data-csautomation-key='VAS']//div[@data-csautomation-key='widgetErrorMsg']");
  public By unableToFetchDataMessage =By.xpath("//div[@data-csautomation-key='VAS']//div[@data-csautomation-key='unableToFetchData']");
  public By rows=By.xpath("//div[@data-csautomation-key='VAS']//div[@class='card__content restricted ng-star-inserted']//div[@class='table-data-wrapper ng-star-inserted']//div[@class='ng-star-inserted']");
  public String dataRow = "//div[@data-csautomation-key='VAS']//div[contains(@class,'table-data-wrapper')]/div[";
  public String valueColumns = "]//div[contains(@class,'data-list')]/div[";
  public String columnValue= "]/span";
  public String columnSubValue = "]/span//span";
/*
Derailed widget locators
 */
  public By activeVASDetailedWidget=By.xpath("//div[@data-csautomation-key='ACTIVE_VAS_DETAILS']");
  public By subscriptionVasWidget=By.xpath("//div[@data-csautomation-key='SUBSCRIPTION_VAS_DETAILS']");
  public By availableVasWidget=By.xpath("//div[@data-csautomation-key='AVAILABLE_VAS_DETAILS']");

}