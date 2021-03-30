package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ActionTrailTabPOM extends BasePage{

    By headRow=By.xpath("//thead/tr//b");
    By detailRow=By.xpath("//tbody/tr");
    By detailColumn=By.xpath("//tbody/tr[1]/td");

    public ActionTrailTabPOM(WebDriver driver) {
        super(driver);
    }

    public String getHeaderValue(int i){
        String text=driver.findElements(headRow).get(i).getText();
        UtilsMethods.printInfoLog(text);
        return text;
    }

    public Integer getNumberOfHeadRow(){
        return driver.findElements(headRow).size();
    }

    public Integer getNumberOfColumns(){
        return driver.findElements(detailRow).size();
    }

    public String getValue(int row,int column){
        String text=driver.findElements(detailRow).get(0).findElements(detailColumn).get(column).getText();
        UtilsMethods.printInfoLog("Value: "+text);
        return text;
    }
}
