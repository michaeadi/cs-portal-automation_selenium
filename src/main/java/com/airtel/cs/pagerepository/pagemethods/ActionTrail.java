package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.ActionTrailPage;
import com.airtel.cs.pojo.response.actionconfig.ActionConfigResult;
import com.airtel.cs.pojo.response.actionconfig.MetaInfo;
import com.airtel.cs.pojo.response.actiontrail.EventResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ActionTrail extends BasePage {
    ActionTrailPage pageElements;

    public ActionTrail(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ActionTrailPage.class);
    }

    /**
     *This method will be use to read widget header name based on column number
     * @param column The column number
     * @return String The value
     */
    public String getHeaderValue(int column) {
        String text = driver.findElements(pageElements.headRow).get(column).getText();
        commonLib.info(text);
        return text;
    }

    /**
     * This Method will give us action trail table value by providing row and column
     * @param row The row Number
     * @param column The Column Number
     * @return String The value
     */
    public String getValue(int row, int column) {
        String text = getText(By.xpath(pageElements.actionTrailRow + row + pageElements.actionTrailColumn + column + "]"));
        commonLib.info("Value: " + text);
        return text;
    }

    /**
     * This Method will give us action trail meta-info table value by providing row and column
     * @param row The row Number
     * @param column The Column Number
     * @return String The value
     */
    public String getMetaInfoValue(int row, int column) {
        String text = getText(By.xpath(pageElements.actionTrailRow + row + pageElements.metaInfoValue + column + "]"));
        commonLib.info("Value: " + text);
        return text;
    }

    /**
     * This Method will give us action trail meta-info table value by providing row and column
     * @param row The row Number
     * @param column The Column Number
     * @return String The value
     */
    public String getMetaInfoLabel(int row, int column) {
        String text = getText(By.xpath(pageElements.actionTrailRow + row + pageElements.metaInfoLabel + column + "]"));
        commonLib.info("Value: " + text);
        return text;
    }

    /**
     * This method is use to click down icon to open modal of meta-info based on row number
     * @param row The row number
     */
    public void clickMetaInfoIcon(int row){
        commonLib.info("Clicking on drop down icon to view meta info");
        By finalXpath=By.xpath(pageElements.actionTrailRow + row + pageElements.metaInfoIcon);
        clickWithoutLoader(finalXpath);
    }


    public void assertMetaInfoAfterActionPerformed(String actionKey,EventResult eventResult){
        ActionConfigResult actionConfigResultOP= UtilsMethods.getActionConfigBasedOnKey(actionKey);
        List<MetaInfo> configMetaInfo = actionConfigResultOP.getMetaInfo();
        List<MetaInfo> actionMetaInfo = eventResult.getMetaInfo();
        for(int i=0;i<configMetaInfo.size();i++){
            assertCheck.append(actions.matchUiAndAPIResponse(configMetaInfo.get(i).getLabel(),actionMetaInfo.get(i).getLabel(),actionMetaInfo.get(i).getLabel()+" Meta info stored after action performed as expected",actionMetaInfo.get(i).getLabel()+" Meta info does not stored after action performed as expected"));
            assertCheck.append(actions.assertEqualStringNotNull(actionMetaInfo.get(i).getValue(),actionMetaInfo.get(i).getLabel()+" : "+actionMetaInfo.get(i).getValue()+"value recorded as value can not be null",actionMetaInfo.get(i).getLabel()+" : meta info value is null but meta info value is mandatory"));
        }
    }
}
