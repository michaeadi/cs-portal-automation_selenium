package pages;


import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;

@Log4j2
public class CustomerDemoGraphicPOM extends BasePage {

    /*
     * Customer Name & DOB & Refresh Icon Element Locator
     * */
    By customerNumberSearchBox = By.xpath("//input[@type='search' and @placeholder='Search']");
    By customerName = By.xpath("//span[@class='customer-name ng-star-inserted']/span[1]");
    By customerDOB = By.xpath("//span[contains(text(),'DOB')]//following-sibling::span/span");
    By customerInfoIcon=By.xpath("//span[@class='customer-name ng-star-inserted']/a");
    By refreshIcon = By.xpath("//img[@class='refresh-button cursor-pointer']");

    /*
     * Activation Date & Time No longer required in newer version
     * */
    By activationDate = By.xpath("//span[contains(text(),'Customer Activation Date')]//following-sibling::span/span");

    /*
     * SIM Number & Device Info
     * */
    By simNumber = By.xpath("//span[contains(text(),'SIM Number')]//following-sibling::span/span");
    By simNumberInfoIcon=By.xpath("//span[contains(text(),'SIM Number')]//following-sibling::span/a");
    By simType = By.xpath("//span[contains(text(),'SIM Type')]//following-sibling::span/span");
    By deviceCompatible = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[2]/span[2]");

    /*
     * Device Info
     * */
    By deviceInfoIcon=By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[2]/span[2]/a");
    By IMEINumber = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[2]/span[2]/a//div[1]//span");
    By type = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[2]/span[2]/a//div[2]//span");
    By brand = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[2]/span[2]/a//div[3]//span");
    By model = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[2]/span[2]/a//div[4]//span");
    By os = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[2]/span[2]/a//div[5]//span");

    /*
     * PUK Tap to unlock & Airtel Money Status Lock
     * */
    By pukLock = By.xpath("//span[contains(text(),'PUK')]//parent::div//span[contains(text(),'Tap to unlock')]");
    By airtelMoneyLock = By.xpath("//span[contains(text(),'Airtel Money Profile')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[contains(text(),'Tap to unlock')]");
    By airtelStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"container-fluid cusomer-profile-detail\"]/div//div[6]//span[contains(text(),'Tap to unlock')]");
    /*
     * PUK1 & PUK2
     * */
    By PUK1 = By.xpath("//p[@class=\"puk-show\"][1]/span[contains(text(),\"PUK1\")]//following-sibling::span");
    By PUK2 = By.xpath("//p[@class=\"puk-show\"][1]/span[contains(text(),\"PUK2\")]//following-sibling::span");

    /*
     * Data Manager on & off
     * */
    By dataManagerStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[1]//div[@class=\"mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin\"]//input");
    /*
     * SIM Status & Airtel Money Status & Service Status
     * */
    By SIMStatus = By.xpath("//span[contains(text(),'GSM Status')]//following-sibling::span/span");
    By SIMStatusReason=By.xpath("//span[contains(text(),'GSM Status')]//following-sibling::span/a");
    By airtelMoneyStatus = By.xpath("//span[contains(text(),'Account Status')]//following-sibling::span/span");
    By serviceStatus = By.xpath("//span[contains(text(),'Service Status')]//following-sibling::span/span");
    By walletBalance=By.xpath("//span[contains(text(),'Wallet Balance')]//following-sibling::span/span");
    By registrationStatus=By.xpath("//span[contains(text(),'Registration Status')]//following-sibling::span/span");

    /*
    * SIM Status Reason
    * */
    By reasonCode=By.xpath("//span[contains(text(),'Reason Code')]//following-sibling::span");
    By modifiedBy=By.xpath("//span[contains(text(),'Modified By')]//following-sibling::span");
    By modifiedDate=By.xpath("//span[contains(text(),'Modified Date')]//following-sibling::span");
    /*
     * ID Type & ID Number
     * */
    By idType = By.xpath("//span[contains(text(),'ID Type')]//following-sibling::span");
    By idNumber = By.xpath("//span[contains(text(),'ID No.')]//following-sibling::span");

    By lineType = By.xpath("//span[contains(text(),'Connection Type')]//following-sibling::span");
    By segment = By.xpath("//span[contains(text(),'Segment')]//following-sibling::span");
    By hoverInfoSegment=By.xpath("//span[contains(text(),'Segment')]//following-sibling::span/a");
    By serviceClass = By.xpath("//span[contains(text(),'Service Class')]//following-sibling::span");
    By serviceCategory = By.xpath("//span[contains(text(),'Service Category')]//following-sibling::span");
    By subSegment=By.xpath("//span[contains(text(),'Sub Segment')]//following-sibling::span");
    By appStatus = By.xpath("//span[contains(text(),'App Status')]//following-sibling::span");
    By gsmKycStatus = By.xpath("//span[contains(text(),'GSM KYC Status')]//following-sibling::span");

    By vipFlag=By.xpath("//div[@class=\"customer-details\"]//div[@class=\"vip-flag ng-star-inserted\"]");
    By errorMessage=By.xpath("//p[contains(text(),'Entered customer number is Invalid')]");
    By clearSearchBox=By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[1]//span[contains(text(),'X')]");

    public CustomerDemoGraphicPOM(WebDriver driver) {
        super(driver);
    }

    public String getCustomerName() {
        log.info("Getting Customer Name " + readText(customerName));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Customer Name " + readText(customerName));

        return readText(customerName);
    }

    public String getDeviceCompatible(){
        printInfoLog("Getting Device Compatible: "+readText(deviceCompatible));
        return readText(deviceCompatible).trim();
    }


    public String getCustomerDOB() {
        log.info("Getting Customer DOB " + readText(customerDOB));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Customer DOB " + readText(customerDOB));
        return readText(customerDOB);
    }

    public String getSIMStatusReasonCode() {
        printInfoLog("Getting SIM Status Reason Code " + readText(reasonCode));
        return readText(reasonCode);
    }

    public String getSIMStatusModifiedBy() {
        printInfoLog("Getting SIM Status Modified By " + readText(modifiedBy));
        return readText(modifiedBy);
    }

    public String getSIMStatusModifiedDate() {
        printInfoLog("Getting SIM Status Modified Date " + readText(modifiedDate));
        return readText(modifiedDate);
    }

    public String getActivationDate() {
        log.info("Getting Activation Date " + readText(activationDate));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Activation Date " + readText(activationDate));
        return readText(activationDate);
    }

    public String getSimNumber() {
        log.info("Getting Sim Number " + readText(simNumber));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Sim Number " + readText(simNumber));
        return readText(simNumber);
    }

    public String getSimType() {
        log.info("Getting Sim Type " + readText(simType));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Sim Type " + readText(simType));
        return readText(simType);
    }

    public String getPUK1() {

        log.info("Getting PUK1 " + readText(PUK1));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting PUK1 " + readText(PUK1));
        return readText(PUK1);
    }

    public String getPUK2() {
        log.info("Getting PUK2 " + readText(PUK2));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting PUK2 " + readText(PUK2));
        return readText(PUK2);
    }

    public String getIdType() {
        log.info("Getting ID Type " + readText(idType));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting ID Type " + readText(idType));
        return readText(idType);
    }

    public String getIdNumber() {
        log.info("Getting masked ID Number " + readText(idNumber));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting masked ID Number " + readText(idNumber));
        return readText(idNumber);
    }

    public boolean isPUKInfoLock() {
        try {
            printInfoLog("Is PUK Info locked: " + checkState(pukLock));
            return checkState(pukLock);
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVIP() {
        try {
            printInfoLog("Is Customer VIP: " + checkState(vipFlag));
            return checkState(vipFlag);
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAirtelMoneyStatusLock() {
        try {
            printInfoLog("Is Airtel Money Status Info locked: " + checkState(airtelMoneyLock));
            return checkState(airtelMoneyLock);
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickPUKToUnlock() {
        printInfoLog("Clicking Tap to unlock on PUK Info");
        click(pukLock);
    }

    public void clickAirtelStatusToUnlock() {
        printInfoLog("Clicking Tap to unlock on Airtel Status Info");
        click(airtelMoneyLock);
    }


    public String getDataManagerStatus(){
        printInfoLog("Status of Data Manager Status+ "+driver.findElement(dataManagerStatus).getAttribute("aria-checked"));
        return driver.findElement(dataManagerStatus).getAttribute("aria-checked");
    }

    public String getSIMStatus(){
        printInfoLog("Getting SIM Status: "+readText(SIMStatus));
        return readText(SIMStatus);
    }

    public String getAirtelMoneyStatus(){
        printInfoLog("Getting Airtel Money Status: "+readText(airtelMoneyStatus));
        return readText(airtelMoneyStatus);
    }

    public String getServiceStatus(){
        printInfoLog("Getting Service Status: "+readText(serviceStatus));
        return readText(serviceStatus);
    }

    public String getWalletBalance(){
        printInfoLog("Getting Airtel Money Wallet Balance: "+readText(walletBalance));
        return readText(walletBalance);
    }

    public String getRegistrationStatus(){
        printInfoLog("Getting Airtel Money Registration Status : "+readText(registrationStatus));
        return readText(registrationStatus);
    }

    public String getLineType() {
        printInfoLog("Getting Line Type: "+readText(lineType));
        return readText(lineType);
    }

    public String getSegment() {
        printInfoLog("Getting Segment: "+readText(segment));
        return readText(segment);
    }

    public String getServiceClass() {
        printInfoLog("Getting service class: "+readText(serviceClass));
        return readText(serviceClass);
    }

    public String getServiceCategory() {
        printInfoLog("Getting service Category: "+readText(serviceCategory));
        return readText(serviceCategory);
    }

    public String getAppStatus() {
        printInfoLog("Getting app Status: "+readText(appStatus));
        return readText(appStatus);
    }

    public String getGsmKycStatus() {
        printInfoLog("Getting Gsm Kyc Status: "+readText(gsmKycStatus));
        return readText(gsmKycStatus);
    }

    public String getIMEINumber(){
        printInfoLog("Getting IMEI Number: "+readText(IMEINumber));
        return readText(IMEINumber);
    }

    public String getDeviceType(){
        printInfoLog("Getting Device Type: "+readText(type));
        return readText(type);
    }

    public String getBrand(){
        printInfoLog("Getting Device Brand: "+readText(brand));
        return readText(brand);
    }

    public String getDeviceModel(){
        printInfoLog("Getting Device model: "+readText(model));
        return readText(model);
    }

    public String getDeviceOS(){
        printInfoLog("Getting Device Operating System Number: "+readText(os));
        return readText(os);
    }

    public void hoverOnDeviceInfoIcon(){
        printInfoLog("Hover on Device Info icon");
        hoverAndClick(deviceInfoIcon);
    }

    public void hoverOnSIMStatusInfoIcon(){
        printInfoLog("Hover on SIM Status Reason Info icon");
        hoverAndClick(SIMStatusReason);
    }

    public void hoverOnCustomerInfoIcon(){
        printInfoLog("Hover on Customer Info icon");
        hoverAndClick(customerInfoIcon);
    }

    public void hoverOnSegmentInfoIcon(){
        printInfoLog("Hover on Segment Info icon");
        hoverAndClick(hoverInfoSegment);
    }

    public boolean invalidMSISDNError(){
        printInfoLog("Reading Error Message: "+readText(errorMessage));
        return checkState(errorMessage);
    }

    public void enterMSISDN(String text){
        printInfoLog("Writing MSISDN in Search Box: "+text);
        writeText(customerNumberSearchBox,text);
        driver.findElement(customerNumberSearchBox).sendKeys(Keys.ENTER);
    }

    public void clearSearchBox(int size){
        printInfoLog("Clearing Search box");
        for (int i = 0; i < size; i++) {
            driver.findElement(customerNumberSearchBox).sendKeys(Keys.BACK_SPACE);
        }
    }


    public void hoverOnSIMNumberIcon() {
        printInfoLog("Hover on SIM Number Info icon");
        hoverAndClick(simNumberInfoIcon);
    }
}
