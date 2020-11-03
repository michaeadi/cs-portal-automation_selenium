package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LoanDetailPOM extends BasePage {

    By loanDetailWidgetTitle= By.xpath("//span[contains(text(),'LOAN DETAILS') and @class=\"card__card-header--label\"]");
    By headerList=By.xpath("//span[contains(text(),'LOAN DETAILS') and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div");
    List<WebElement> ls=driver.findElements(headerList);

    public LoanDetailPOM(WebDriver driver) {
        super(driver);
    }

    public boolean IsLoanDetailWidgetDisplay(){
        printInfoLog("Reading Widget: "+readText(loanDetailWidgetTitle));
        return checkState(loanDetailWidgetTitle);
    }

    public int getHeaderSize(){
        return ls.size();
    }

    public String getHeaderName(int i){
        By name=By.xpath("//span[contains(text(),'LOAN DETAILS') and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div["+i+"]//span");
        printInfoLog("Reading Header Name: "+readText(name));
        return readText(name);
    }

    public String getValueCorrespondingToHeader(int i){
        By value=By.xpath("//span[contains(text(),'LOAN DETAILS') and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"]//div["+i+"]//span");
        printInfoLog("Reading '"+getHeaderName(i)+"' = "+readText(value));
        return readText(value);
    }
}
