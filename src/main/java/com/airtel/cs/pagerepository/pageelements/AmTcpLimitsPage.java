package com.airtel.cs.pagerepository.pageelements;
import org.openqa.selenium.By;
public class AmTcpLimitsPage {

   public By amTransactionsWidget=By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']");
   public  By moreIcon =By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//img[contains(@src,'more.svg')]");
   public By  amProfileDetailsTab=By.xpath("//span[contains(text(),'AM PROFILE DETAILS')]");
   public By amProfileDetailsWidget =By.xpath("//mat-tab-group[@class='mat-tab-group mat-primary ng-star-inserted']");
   public By wallets =By.xpath("//span[contains(text(),'WALLETS')]");
   public By icon =By.xpath("//mat-icon[contains(@class,'cursor-pointer mat-icon')]");
   public By tcpLimits=By.xpath("//span[contains(text(),'TCP LIMITS')]");

   /*
    No result found and unable to fetch data  locators
    */
      public By noResultFound = By.xpath("//img[@data-csautomation-key='noResultFoundIcon']");
      public By noResultFoundMessage = By.xpath("//span[@data-csautomation-key='noResultFoundMsg']");
      public By widgetErrorMessage=By.xpath("//div[@data-csautomation-key='widgetErrorMsg']");
      public By unableToFetchDataMessage =By.xpath("//h3[@data-csautomation-key='unableToFetchData']");

   /*
   TCP Limit Layout locators
    */
   public By transferProfileDetails=By.xpath("//span[contains(text(),'Transfer Profile Details')]");
   public By paymentMethodThresholdDetails=By.xpath(" //span[contains(text(),'Threshold Details')]");
   public By profileId=By.xpath("//span[contains(text(),'Profile ID')]");
   public By currency=By.xpath("//div[contains(text(),'Currency')]");
   public By profileName=By.xpath("//div[contains(text(),'Profile Name')]");
   public By minimumResidualBalance=By.xpath("//div[contains(text(),'Minimum Residual Balance')]");
   public By minimumTransactionAmount=By.xpath("//div[contains(text(),'Minimum Transaction Amount')]");
   public By minimumTransferAllowed=By.xpath("//div[contains(text(),'Maximum %')]");
   public By maximumTransactionAmount=By.xpath("//div[contains(text(),'Maximum Transaction Amount')]");
   public By maximumBalance=By.xpath("//div[contains(text(),'Maximum Balance')]");
   public By payeeTcpLimits=By.xpath("//span[contains(text(),'Payee TCP Limits')]");
   public By payee=By.xpath("//div[contains(text(),'Payee')]");
   public By payer=By.xpath("//div[contains(text(),'Payer')]");
   public By payerTcpLimits=By.xpath("//span[contains(text(),'Payer TCP Limits')]");

   public String transferDetailsRow="//div[@class='transfer-details-data']//div[@class='data-set'][";
   public String transferDetailColumn="]//div[2]";

   public String thresholdDetailsRow="//div[@class='threshold-details-data']//div[@class='data-set'][";
   public String thresholdDetailsColumn="]//div[2]";

   /*
   Bearer Type locators
    */
   public By ussd=By.xpath("//span[contains(text(),'USSD')]");
   public By p2p=By.xpath("//span[contains(text(),'P2P')]");
   public By webService=By.xpath("//span[contains(text(),'WEBSERVCE')]");
   public By ivr=By.xpath("//span[contains(text(),'IVR')]");
   public By bank=By.xpath("//span[contains(text(),'BANK')]");
   public By all=By.xpath("//span[contains(text(),'ALL')]");
   public By stk=By.xpath("//span[contains(text(),'STK')]");
   public By web=By.xpath("//span[(text()='WEB')]");


















}
