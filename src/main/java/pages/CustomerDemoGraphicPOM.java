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
    By customerNumberSearchBox = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[1]//input");
    By customerName = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[1]/div[2]//p");
    By customerDOB = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[1]/div[2]//span[2]");
    By refreshIcon = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[1]/div[3]/img");

    /*
     * Activation Date & Time
     * */
    By activationDate = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[2]//span[1]");
    By activationTime = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[2]//span[2]");

    /*
     * SIM Number & Device Info
     * */
    By simNumber = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[1]/span");
    By simType = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[3]//li[2]/p[2]/span[1]");
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
    By pukLock = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[1]//ul//span[contains(text(),'Tap to unlock')]");
    By airtelMoneyLock = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[3]//ul//span[contains(text(),'Tap to unlock')]");
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
    By SIMStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[2]//span");
    By airtelMoneyStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[3]//li[2]//p[1]//span[2]");
    By serviceStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[3]//li[2]//p[2]//span[2]");

    /*
     * ID Type & ID Number
     * */
    By idType = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[4]//ul/li[1]//p");
    By idNumber = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[4]//ul/li[2]//span");

    By lineType = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"container-fluid cusomer-profile-detail\"]/div//div[1]//ul[1]//span//span[1]");
    By segment = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"container-fluid cusomer-profile-detail\"]/div//div[1]//ul[1]//span//span[2]");
    By serviceClass = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"container-fluid cusomer-profile-detail\"]/div//div[2]//p/span[2]");
    By serviceCategory = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"container-fluid cusomer-profile-detail\"]/div//div[3]//p/span[2]");
    By appStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"container-fluid cusomer-profile-detail\"]/div//div[4]//p/span[2]");
    By gsmKycStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"container-fluid cusomer-profile-detail\"]/div//div[5]//p/span[2]");

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

    public String getActivationDate() {
        log.info("Getting Activation Date " + readText(activationDate));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Activation Date " + readText(activationDate));
        return readText(activationDate);
    }

    public String getActivationTime() {
        log.info("Getting Activation Time " + readText(activationTime));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Activation Time " + readText(activationTime));
        return readText(activationTime);
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


}
