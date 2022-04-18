package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class PsbDemographicWidgetPage {
    /*
    Intermediate screen Header Locators
    */
    public By intermediateScreen=By.xpath("//div[@class='clm-options']");
    public  By action= By.xpath("//span[contains(text(),'Action')]");
    public  By msisdn= By.xpath("//span[contains(text(),'MSISDN')]");
    public  By nunbanId= By.xpath("//span[contains(text(),'Nuban')]");
    public  By createdOn= By.xpath("//span[contains(text(),'Created')]");
    public  By type= By.xpath("//span[contains(text(),'Type')]");

    public String row="//tr[contains(@class,'list--list-row')]";
    public String noOfRows="//tr[contains(@class,'list--list-row')]";
    public String typeRow="]//td[3]//div";
    public String column="]//td[5]//img[contains(@src,'maximize.svg')]";

    public  By walletWidget= By.xpath("//span[contains(text(),'Wallet Information')]");
    public  By accountWidget= By.xpath("//span[contains(text(),'Account Information')]");

    /*
    Auuid  Locators
    */
    public By middleAuuid = By.xpath("//*[@id='AM_PROFILE']");
    public By footerAuuid = By.xpath("//div[@id='AM_PROFILE']//div[contains(@class,'auuid-container am_profile')]");


    /*
       Headers  Locators
       */
    public By msisdnDashboardSearchBox = By.xpath("//input[@data-csautomation-key='dashBoardSearchBox']");
    public  By customerNameHeader= By.xpath("//span[contains(text(),'Customer Name')]");
    public  By emailIdHeader= By.xpath("//span[contains(text(),'Email Id')]");
    public  By primaryIdTypeHeader= By.xpath("//span[contains(text(),'Primary')]");
    public  By secondaryIdTypeHeader= By.xpath("//span[contains(text(),'Secondary')]");
    public  By addressHeader= By.xpath("//span[contains(text(),'Address')]");
    public  By isUserAgentHeader= By.xpath("//span[contains(text(),'Agent')]");
    public  By customerCategoryHeader= By.xpath("//span[contains(text(),'Customer Category')]");
    public  By customerIdInfoIcon= By.xpath("//*[@class='summary-table horizontal-table ng-star-inserted']");
    public  By pinResetHeader= By.xpath("//span[contains(text(),'Pin Reset')]");
    public  By pinSetHeader= By.xpath("//span[contains(text(),'Pin Set')]");
    public  By customerIDHeader= By.xpath("//span[contains(text(),'Customer ID')]");
    public By customerInfoIcon = By.xpath("//span[@class='customer-name ng-star-inserted']/a");
    public  By primaryInfoIcon= By.xpath("//span[contains(text(),'Primary')]//following-sibling::span/a");
    public  By secondaryInfoIcon= By.xpath("//span[contains(text(),'Secondary')]//following-sibling::span/a");
    public By smartCashLogo=By.xpath("//img[contains(@src,'SmartCash')]");
    public By resetPinIcon=By.xpath("//img[contains(@src,'images/lock')]");
    public By barIcon=By.xpath("//img[contains(@src,'airtelmoney')]");
    public  By genderHeader= By.xpath("//*[contains(text(),'Gender')]");
    public  By dobHeader= By.xpath("//*[contains(text(),'Date Of Birth')]");
    public  By pobHeader= By.xpath("//*[contains(text(),'Place Of Birth')]");
    public  By nationalityHeader= By.xpath("//*[contains(text(),'Nationality')]");
    public  By mothersMaidenNameHeader= By.xpath("//*[contains(text(),'Maiden Name')]");
    public  By customerTypeHeader= By.xpath("//*[contains(text(),'Customer Type')]");
    public  By primaryIdNumberHeader= By.xpath("//span[contains(text(),'Primary ID Number')]");
    public  By secondaryIdNumberHeader= By.xpath("//span[contains(text(),'Secondary ID Number')]");

/*
Values Locators
 */
    public  By customerName= By.xpath("//span[@class='customer-name ng-star-inserted']/span[1]");
    public  By emailId= By.xpath("//span[contains(text(),'Email Id')]//following-sibling::span");
    public  By primaryIdType= By.xpath("//span[contains(text(),'Primary')]//following-sibling::span");
    public  By secondaryIdType= By.xpath("//span[contains(text(),'Secondary')]//following-sibling::span");
    public  By address= By.xpath("//span[contains(text(),'Address')]//following-sibling::span");
    public  By isUserAgent= By.xpath("//span[contains(text(),'Agent')]//following-sibling::span//span");
    public  By customerCategory= By.xpath("//span[contains(text(),'Customer Category')]//following-sibling::span");
    public  By pinReset= By.xpath("//span[contains(text(),'Pin Reset')]//following-sibling::span");
    public  By pinSet= By.xpath("//span[contains(text(),'Pin Set')]//following-sibling::span");
    public  By customerID= By.xpath("//span[contains(text(),'Customer ID')]//following-sibling::span");

    public  By gender= By.xpath("//*[contains(text(),'Gender')]//following-sibling::td//span");
    public  By dob= By.xpath("//td[contains(text(),'Date Of Birth')]//following-sibling::td//span");
    public  By pob= By.xpath("//td[contains(text(),'Place')]//following-sibling::td//span");
    public  By nationality= By.xpath("//td[contains(text(),'Nationality')]//following-sibling::td//span");
    public  By mothersMaidenName= By.xpath("//td[contains(text(),'Maiden Name')]//following-sibling::td//span");
    public  By customerType= By.xpath("//td[contains(text(),'Customer Type')]//following-sibling::td//span");
    public  By primaryIdNumber= By.xpath("//td[contains(text(),'Primary ID Number')]//following-sibling::td//span");
    public  By secondaryIdNumber= By.xpath("//td[contains(text(),'Secondary ID Number')]//following-sibling::td//span");
    public  By FirstName= By.xpath("//th[contains(text(),'First Name')]");
    public  By LastName= By.xpath("//th[contains(text(),'Last Name')]");
    public  By MSISDN= By.xpath("//th[contains(text(),'MSISDN");
}
