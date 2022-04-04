package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import com.airtel.cs.pagerepository.pageelements.DemographicWidgetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DemographicWidget extends BasePage{
    DemographicWidgetPage pageElements;

    public DemographicWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DemographicWidgetPage.class);
    }

    /**
     * This method is used to check Customer Name label visible or not
     * @return
     */
    public boolean isCustomerNameHeaderVisible() {
        boolean status = isElementVisible(pageElements.customerNameHeader);
        commonLib.info("Is Customer Name Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Email label visible or not
     * @return
     */
    public boolean isEmailIdHeaderVisible() {
        boolean status = isElementVisible(pageElements.emailIdHeader);
        commonLib.info("Is Email Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Primary Id Type Header label visible or not
     * @return
     */
    public boolean isPrimaryIdTypeHeaderVisible() {
        boolean status = isElementVisible(pageElements.primaryIdTypeHeader);
        commonLib.info("Is Primary ID Type Header visible : " + status);
        return status;
    }


    /**
     * This method is used to check Secondary Id Type Header label visible or not
     * @return
     */
    public boolean isSecondaryIdTypeHeaderVisible() {
        boolean status = isElementVisible(pageElements.secondaryIdTypeHeader);
        commonLib.info("Is Secondary ID Type Header visible : " + status);
        return status;
    }


    /**
     * This method is used to check Address label visible or not
     * @return
     */
    public boolean isAddressHeaderVisible() {
        boolean status = isElementVisible(pageElements.addressHeader);
        commonLib.info("Is Address Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check user an agent  label visible or not
     * @return
     */
    public boolean isUserAgentHeaderVisible() {
        boolean status = isElementVisible(pageElements.isUserAgentHeader);
        commonLib.info("Is User AN Agent Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Customer Id label visible or not
     * @return
     */
    public boolean isCustomerIdHeaderVisible() {
        boolean status = isElementVisible(pageElements.customerIDHeader);
        commonLib.info("Is Customer Id Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Customer Category label visible or not
     * @return
     */
    public boolean isCustomerCategoryHeaderVisible() {
        boolean status = isElementVisible(pageElements.customerCategoryHeader);
        commonLib.info("Is Customer Category Header visible : " + status);
        return status;
    }


    /**
     * This method is used to check pin set label visible or not
     * @return
     */
    public boolean isPinSetHeaderVisible() {
        boolean status = isElementVisible(pageElements.pinSetHeader);
        commonLib.info("Is Pin Set Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Pin reset  label visible or not
     * @return
     */
    public boolean isPinResetHeaderVisible() {
        boolean status = isElementVisible(pageElements.pinResetHeader);
        commonLib.info("Is Pin Reset Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Primary Id Number label visible or not
     * @return
     */
    public boolean isPrimaryIdNumberHeaderVisible() {
        boolean status = isElementVisible(pageElements.primaryIdNumberHeader);
        commonLib.info("Is Primary Id Number Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Secondary Id Number label visible or not
     * @return
     */
    public boolean isSecondaryIdNumberHeaderVisible() {
        boolean status = isElementVisible(pageElements.secondaryIdNumberHeader);
        commonLib.info("Is Secondary Id Number header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Gender label visible or not
     * @return
     */
    public boolean isGenderHeaderVisible() {
        boolean status = isElementVisible(pageElements.genderHeader);
        commonLib.info("Is Gender Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Dob label visible or not
     * @return
     */
    public boolean isDobHeaderVisible() {
        boolean status = isElementVisible(pageElements.dobHeader);
        commonLib.info("Is Dob Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Place of birth label visible or not
     * @return
     */
    public boolean isPobHeaderVisible() {
        boolean status = isElementVisible(pageElements.pobHeader);
        commonLib.info("Is Place of Birth Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Mothers maiden name  label visible or not
     * @return
     */
    public boolean isMaidenNameHeaderVisible() {
        boolean status = isElementVisible(pageElements.mothersMaidenNameHeader);
        commonLib.info("Is Mothers Maiden Name Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Nationality label visible or not
     * @return
     */
    public boolean isNationalityHeaderVisible() {
        boolean status = isElementVisible(pageElements.nationalityHeader);
        commonLib.info("Is Nationality Header visible : " + status);
        return status;
    }

    /**
     * This method is used to check Customer Type label visible or not
     * @return
     */
    public boolean isCustomerTypeHeaderVisible() {
        boolean status = isElementVisible(pageElements.customerTypeHeader);
        commonLib.info("Is Customer Type Header visible : " + status);
        return status;
    }

    /**
     * This method is use to hover on Primary info icon
     */
    public void hoverOnPrimaryInfoIcon() {
        commonLib.info("Hover on Primary Info icon");
        hoverOverElement(pageElements.primaryInfoIcon);
    }

    /**
     * This method is use to hover on Secondary info icon
     */
    public void hoverOnSecondaryInfoIcon() {
        commonLib.info("Hover on Secondary Info icon");
        hoverOverElement(pageElements.secondaryInfoIcon);
    }

    /**
     * This method is use to hover on Customer info icon
     */
    public void hoverOnCustomerInfoIcon() {
        commonLib.info("Hover on Customer Info icon");
        hoverOverElement(pageElements.customerInfoIcon);
    }

    /**
     * This method is used to get customer name
     * @return String The value
     */
    public String getCustomerName() {
        final String text = getText(pageElements.customerName);
        commonLib.info("Getting Customer Name: " + text);
        return text;
    }

    /**
     * This method is used to get email id
     * @return String The value
     */
    public String getEmailId() {
        final String text = getText(pageElements.emailId);
        commonLib.info("Getting Email id: " + text);
        return text;
    }

    /**
     * This method is used to get primary id
     * @return String The value
     */
    public String getPrimaryIdType() {
        final String text = getText(pageElements.primaryIdType);
        commonLib.info("Getting  Primary Id Type: " + text);
        return text;
    }


    /**
     * This method is used to get Secondary Id Type
     * @return
     */
    public String getSecondaryIdType() {
        final String text = getText(pageElements.secondaryIdType);
        commonLib.info("Getting Secondary Id Type: " + text);
        return text;
    }


    /**
     * This method is used to get Address
     * @return
     */
    public String getAddress() {
        final String text = getText(pageElements.address);
        commonLib.info("Getting Address: " + text);
        return text;
    }

    /**
     * This method is used to get user an agent
     * @return
     */
    public String getIsUserAgent() {
        final String text = getText(pageElements.isUserAgent);
        commonLib.info("Getting Is user an agent: " + text);
        return text;
    }


    /**
     * This method is used to get Customer Id
     * @return
     */
    public String getCustomerId() {
        final String text = getText(pageElements.customerID);
        commonLib.info("Getting Customer id: " + text);
        return text;
    }


    /**
     * This method is used to get Customer Category
     * @return
     */
    public String getCustomerCategory() {
        final String text = getText(pageElements.customerCategory);
        commonLib.info("Getting Customer Category: " + text);
        return text;
    }


    /**
     * This method is used to get Pin Set
     * @return
     */
    public String getPinSet() {
        final String text = getText(pageElements.pinSet);
        commonLib.info("Getting Pin Set : " + text);
        return text;
    }


    /**
     * This method is used to get Pin Reset
     * @return
     */
    public String getPinReset() {
        final String text = getText(pageElements.pinReset);
        commonLib.info("Getting Reset: " + text);
        return text;
    }


    /**
     * This method is used to get Primary Id Number
     * @return
     */
    public String getPrimaryIdNumber() {
        final String text = getText(pageElements.primaryIdNumber);
        commonLib.info("Getting Primary Id Number: " + text);
        return text;
    }


    /**
     * This method is used to get Secondary Id Number
     * @return
     */
    public String getSecondaryIdNumber() {
        final String text = getText(pageElements.secondaryIdNumber);
        commonLib.info("Getting Secondary Id Number: " + text);
        return text;
    }


    /**
     * This method is used to get Dob
     * @return
     */
    public String getDob() {
        final String text = getText(pageElements.dob);
        commonLib.info("Getting DOB: " + text);
        return text;
    }

    /**
     * This method is used to get Gender
     * @return
     */
    public String getGender() {
        final String text = getText(pageElements.gender);
        commonLib.info("Getting Gender : " + text);
        return text;
    }

    /**
     * This method is used to check Place of birth
     * @return
     */
    public String getPob() {
        final String text = getText(pageElements.pob);
        commonLib.info("Getting Place of birth: " + text);
        return text;
    }

    /**
     * This method is used to get Mothers maiden name
     * @return
     */
    public String getMothersMaidenName() {
        final String text = getText(pageElements.mothersMaidenName);
        commonLib.info("Getting Mothers Maiden Name: " + text);
        return text;
    }

    /**
     * This method is used to get Nationality
     * @return
     */
    public String getNationality() {
        final String text = getText(pageElements.nationality);
        commonLib.info("Getting Nationality: " + text);
        return text;
    }

    /**
     * This method is used to get Customer Type
     * @return
     */
    public String getCustomerType() {
        final String text = getText(pageElements.customerType);
        commonLib.info("Getting Customer Type: " + text);
        return text;
    }

    /**
     * This method is used to check Intermediate Screen visible or not
     * @return
     */
    public boolean isIntermediateScreenVisible() {
        boolean status = isElementVisible(pageElements.intermediateScreen);
        commonLib.info("Is Intermediate Screen visible : " + status);
        return status;
    }

    /**
     * This method is used to check Msisdn label visible or not
     * @return
     */
    public boolean isMsisdnVisible() {
        boolean status = isElementVisible(pageElements.msisdn);
        commonLib.info("Is Msisdn visible : " + status);
        return status;
    }

    /**
     * This method is used to check Action label visible or not
     * @return
     */
    public boolean isActionVisible() {
        boolean status = isElementVisible(pageElements.action);
        commonLib.info("Is Action visible : " + status);
        return status;
    }

    /**
     * This method is used to check Type label visible or not
     * @return
     */
    public boolean isTypeVisible() {
        boolean status = isElementVisible(pageElements.type);
        commonLib.info("Is Type visible : " + status);
        return status;
    }

    /**
     * This method is used to check Created on label visible or not
     * @return
     */
    public boolean isCreatedOnVisible() {
        boolean status = isElementVisible(pageElements.createdOn);
        commonLib.info("Is Created on visible : " + status);
        return status;
    }

    /**
     * This method is used to check Nuban id label visible or not
     * @return
     */
    public boolean isNubanIdVisible() {
        boolean status = isElementVisible(pageElements.nunbanId);
        commonLib.info("Is Nuban id visible : " + status);
        return status;
    }

    /**
     * This method is used click CTA of a particular row
     * @param row
     * @return
     */
    public void clickHeaderValue(int row) {
        commonLib.info("Clicking"+row+ "is CTA");
       clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.row + row + pageElements.column));

    }

    /**
     * This method is used get total no of rows
     * @return
     */
    public int getNoOfRows() {
        int no=getSizeOfElement(By.xpath(pageElements.row));
        return no;

    }

    /**
     * This method is used get total no of rows
     * @return
     */
    public String getType(int row) {
        String type=getText(By.xpath(pageElements.row + row + pageElements.column));
         commonLib.info("Reading Type of " + row + ":" + type);
        return type;

    }


    /**
     * This method is used to check Wallet Information widget visible or not
     * @return
     */
    public boolean isWalletInformationWidgetVisible() {
        boolean status = isElementVisible(pageElements.walletWidget);
        commonLib.info("Is Wallet Information widget  visible : " + status);
        return status;
    }

    /**
     * This method is used to check Account Information widget visible or not
     * @return
     */
    public boolean isAccountInformationWidgetVisible() {
        boolean status = isElementVisible(pageElements.accountWidget);
        commonLib.info("Is Account Information widget  visible : " + status);
        return status;
    }

    /**
     * This method is used to check footer auuid visible or not
     * @return
     */
    public String getFooterAuuid() {
        String result = null;
        result = getText(pageElements.footerAuuid);
        return result;
    }

    /**
     * This method is used to check middle auuid  visible or not
     * @return
     */
    public String getMiddleAuuid() {
        String result = null;
        result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
        return result;
    }

    /**
     * This method will check whether customer profile page is loaded or not after searching the msisdn
     * @return
     */
    public boolean isPageLoaded(CLMDetailsResponse clmDetails)
    {
        int walletsSize = clmDetails.getResult().getDetails().get(0).getWallets().size();
        int accountsSize = clmDetails.getResult().getDetails().get(0).getAccounts().size();
        int totalSize = walletsSize + accountsSize;
        final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
        if (totalSize > 1) {
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isIntermediateScreenVisible(), true, "Intermediate Screen is visible successfully", "Intermediate Screen is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isActionVisible(), true, " Action is visible ", "Action is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isTypeVisible(), true, " Type is visible ", "Type is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isNubanIdVisible(), true, "Nuban Id is visible ", "Nuban Id is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isMsisdnVisible(), true, "Msisdn is visible ", "Msisdn is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isCreatedOnVisible(), true, " Created ON is visible ", "Created on is not visible "));
            int size = pages.getDemographicWidget().getNoOfRows();
            for (int i = 1; i <=size; i++) {
                pages.getDemographicWidget().clickHeaderValue(i);
                assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
                String type = pages.getDemographicWidget().getType(i);
                if (type.equalsIgnoreCase("Wallet"))
                    assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isWalletInformationWidgetVisible(), true, " Wallet Information widget is visible ", "Wallet Information widget  is not visible "));
                if (type.equalsIgnoreCase("Account"))
                    assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isAccountInformationWidgetVisible(), true, " Account Information widget is visible ", "Account Information widget  is not visible "));
            }
        } else
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));

        return pageLoaded;
    }

}

