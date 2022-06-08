package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.model.cs.response.airtelmoney.AirtelMoney;
import com.airtel.cs.pagerepository.pageelements.AmReversalPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class AmReversal extends BasePage {

    AmReversalPage pageElements;

    public AmReversal(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AmReversalPage.class);
    }

    String openingBracket = "[";

    /**
     * This method use to get data value based on row and column number(Primary Widget)
     *
     * @param row The Row Number
     */
    public void clickReversalIcon(int row) {
        clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.valueRow + openingBracket + row + pageElements.columnText));
        commonLib.info("Clicking (" + row + "): ");
    }

    /**
     * This method use to get data value no of rows
     */
    public int getRowSize() {
        List<WebElement> list = getElementsListFromBy(By.xpath(pageElements.valueRow));
        return list.size();
    }

    /**
     * This method is used to select reason from dropdown
     */
    public void selectReasonFromDropdown() {
        commonLib.info("Going to select reason : Customer Request");
        if (isVisible(pageElements.selectReasonFromDropdown))
            clickWithoutLoader((pageElements.selectReasonFromDropdown));
    }

    /**
     * This method is used to  BAR/UNBAR smartcash profile by selecting reason and comment
     */
    public void performReversal() {
        commonLib.info("Going to perform ReversalAction");
        try {
            pages.getBarUnbar().clickOnSelectReason();
            selectReasonFromDropdown();
            pages.getBarUnbar().enterComment(COMMENT);
            pages.getBarUnbar().clickOnSubmitButton();
        } catch (Exception e) {
            pages.getBarUnbar().clickCrossIcon();
            commonLib.fail("Exception in Method - performReversal" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This Method will return the number of pages of records
     *
     * @return page size
     */
    public int getNumberOfPages() {
        int pageCount = 0;
        int rowCount = Integer.parseInt(getSMSRowCount());
        if (rowCount % 5 == 0) {
            pageCount = rowCount / 5;
        }
        if (rowCount % 5 != 0) {
            rowCount = rowCount + (5 - rowCount % 5);
            pageCount = rowCount / 5;
        }
        return pageCount;
    }

    /**
     * This Method will be used to get the total row size in a table
     *
     * @return the row count
     */
    public String getSMSRowCount() {
        String rowCount;
        rowCount = getText(pageElements.totalSMSRow);
        rowCount = rowCount.substring(rowCount.indexOf("of") + 3);
        rowCount = rowCount.replace("Results", "").trim();
        return rowCount;
    }

    /**
     * This method is used to click on cross icon of success pop up
     */
    public void clickCrossIcon() {
        commonLib.info("Going to click cross icon");
        if (isVisible(pageElements.crossIcon))
            clickWithoutLoader(pageElements.crossIcon);
    }

    /**
     * This method is used to click right arrow of pagination
     */
    public void goToNextPage() {
        commonLib.info("Going to click right arrow of pagination");
        if (isVisible(pageElements.nextPage))
            clickAndWaitForLoaderToBeRemoved(pageElements.nextPage);
    }

    public StringBuilder reversal(AirtelMoney amTransactionHistoryAPI) {
        int pageCount = pages.getAmReversal().getNumberOfPages();
        boolean rowFound = false;
        for (int x = 0; x < pageCount; x++) {
            for (int i = 0; i < pages.getAmReversal().getRowSize(); i++) {
                if (amTransactionHistoryAPI.getResult().getData().get(i).getIsReversed().equals(false)) {
                    rowFound = true;
                    pages.getAmReversal().clickReversalIcon(i + 1);
                    pages.getDemoGraphicPage().selectPolicyQuestion();
                    pages.getAuthTabPage().clickAuthBtn();
                    pages.getAmReversal().performReversal();
                    assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSuccessPopUpVisible(), true, "Success Popup is visible after performing txn reversal", "Success Popup is not visible after performing txn reversal"));
                    String successText = "";
                    assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
                    pages.getAmReversal().clickCrossIcon();
                    break;
                } else
                    commonLib.info("No txn id found for reversal for row no : " + (i + 1), true);

            }

            if (!rowFound) {
                commonLib.warning("No txn id found for reversal on page no : " + (x + 1), true);
                pages.getAmReversal().goToNextPage();
            } else {
                break;
            }
        }
        return assertCheck;
    }

}
