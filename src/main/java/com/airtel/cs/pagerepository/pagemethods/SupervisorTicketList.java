package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.model.response.ticketlist.IssueDetails;
import com.airtel.cs.model.response.ticketlist.QueueStates;
import com.airtel.cs.pagerepository.pageelements.SupervisorTicketListPage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class SupervisorTicketList extends BasePage {


    SupervisorTicketListPage pageElements;

    public SupervisorTicketList(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SupervisorTicketListPage.class);
    }

    /**
     * This method use to get error message while performing transfer to queue
     * @return String The value
     * */
    public String getTransferErrorMessage() {
        String value = getText(pageElements.transferErrorMessage);
        commonLib.info("Reading transfer error message: " + value);
        return value;
    }

    /**
     * This method use to get error message while performing transfer to queue
     * @return String The value
     * */
    public String getTransferSuccessMessage() {
        try {
            String value = getText(pageElements.transferSuccessMessage);
            commonLib.info("Reading transfer Success message: " + value);
            return value;
        }catch (NoSuchElementException | TimeoutException e){
            commonLib.infoHighlight("","Not able to read message", ReportInfoMessageColorList.RED);
            return "Got some error";
        }
    }

    /**
     * This method use to check is cancel button display
     * @return true/false
     * */
    public Boolean isCancelBtn() {
        Boolean status = isEnabled(pageElements.cancelBtn);
        commonLib.info("Is Cancel Button Displayed: " + status);
        return status;
    }

    /**
     * This method use to check is Transfer AnyWay button display
     * @return true/false
     * */
    public Boolean isTransferAnyWayBtn() {
        Boolean status = isEnabled(pageElements.transferAnywayBtn);
        commonLib.info("Is Transfer Any Way Button Displayed: " + status);
        return status;
    }

    /**
     * This method use to click cancel button display
     * */
    public void clickCancelBtn() {
        commonLib.info("Clicking on Cancel Button.");
        clickAndWaitForLoaderToBeRemoved(pageElements.cancelBtn);
    }

    /**
     * This method use to click Transfer AnyWay button display
     * */
    public void clickTransferAnyWayBtn() {
        commonLib.info("Clicking on Transfer Anyway button");
        clickAndWaitForLoaderToBeRemoved(pageElements.transferAnywayBtn);
    }

    /**
     * This method use to write ticket id in search box
     * */
    public void writeTicketId(String ticketId) {
        commonLib.info("Search Ticket Id: " + ticketId);
        enterText(pageElements.searchTicketBox, ticketId);
    }

    /**
     * This method use to write ticket id in search second box
     * */
    public void writeTicketIdSecond(String ticketId) {
        commonLib.info("Search Ticket Id: " + ticketId);
        enterText(pageElements.searchTicketBox2, ticketId);
    }

    /**
     * This method use to clear ticket id from search box
     * */
    public void clearInputBox() {
        commonLib.info("Clear Search Box");
        for (int i = 0; i < 12; i++) {
            driver.findElement(pageElements.searchTicketBox).sendKeys(Keys.BACK_SPACE);
        }
        waitTillLoaderGetsRemoved();
    }

    /**
     * This method use to click ticket id search button
     * */
    public void clickSearchBtn() {
        waitTillLoaderGetsRemoved();
        isClickable(pageElements.searchTicketBtn);
        commonLib.info("Clicking on Search Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchTicketBtn);
    }

    /**
     * This method use to check ticket id label display or not
     * @return true/false
     * */
    public boolean isTicketIdLabel() {
        final boolean state = isVisible(pageElements.ticketIdLabel);
        commonLib.info("Is Ticket Id field Available :" + state);
        return state;
    }

    /**
     * This method use to check WorkGroup label display or not
     * @return true/false
     * */
    public boolean isWorkGroupName() {
        commonLib.info("Ticket lie in WorkGroup :" + getText(pageElements.workGroupName));
        return isEnabled(pageElements.workGroupName);
    }

    /**
     * This method use to get workGroup Name in which ticket currently lie
     * @return String The value
     * */
    public String getWorkGroupName() {
        return getText(pageElements.workGroupName);
    }

    /**
     * This method use to check Priority label display or not
     * @return true/false
     * */
    public boolean isPrioritylabel() {
        final boolean state = isEnabled(pageElements.prioritylabel);
        commonLib.info("Is Priority field Available :" + state);
        return state;
    }

    /**
     * This method use to check State label display or not
     * @return true/false
     * */
    public boolean isStateLabel() {
        final boolean state = isEnabled(pageElements.stateLabel);
        commonLib.info("Is State field Available :" + state);
        return state;
    }

    /**
     * This method use to Creation Date label display or not
     * @return true/false
     * */
    public boolean isCreationDateLabel() {
        final boolean state = isEnabled(pageElements.creationdateLabel);
        commonLib.info("Is Creation Date field Available :" + state);
        return state;
    }

    /**
     * This method use to Creation By label display or not
     * @return true/false
     * */
    public boolean isCreatedByLabel() {
        final boolean state = isEnabled(pageElements.createdbyLabel);
        commonLib.info("Is Created By field Available :" + state);
        return state;
    }

    /**
     * This method use to Queue label display or not
     * @return true/false
     * */
    public boolean isQueueLabel() {
        final boolean state = isEnabled(pageElements.queueLabel);
        commonLib.info("Is Queue field Available :" + state);
        return state;
    }

    /**
     * This method use to Issue label display or not
     * @return true/false
     * */
    public boolean isIssueLabel() {
        commonLib.info("Is Issue Label field Available :" + isEnabled(pageElements.prioritylabel));
        return isEnabled(pageElements.issueLabel);
    }

    /**
     * This method use to Issue Type label display or not
     * @return true/false
     * */
    public boolean isIssueTypeLabel() {
        commonLib.info("Is Issue Type field Available :" + isEnabled(pageElements.prioritylabel));
        return isEnabled(pageElements.issueTypeLabel);
    }

    /**
     * This method use to Issue Sub Type label display or not
     * @return true/false
     * */
    public boolean isSubTypeLabel() {
        commonLib.info("Is Issue Sub Type field Available :" + isEnabled(pageElements.issueTypeLabel));
        return isEnabled(pageElements.subTypeLabel);
    }

    /**
     * This method use to Issue Sub Sub Type label display or not
     * @return true/false
     * */
    public boolean isSubSubTypeLabel() {
        final boolean state = isEnabled(pageElements.subSubTypeLabel);
        commonLib.info("Is Issue Sub Sub Type field Available :" + state);
        return state;
    }

    /**
     * This method use to Issue Code label display or not
     * @return true/false
     * */
    public boolean isCodeLabel() {
        final boolean state = isEnabled(pageElements.codeLabel);
        commonLib.info("Is Code field Available :" + state);
        return state;
    }

    /**
     * This method use to get first ticket id from the supervisor ticket list
     * @return String The value
     * */
    public String getTicketIdValue() {
        final String text = getText(pageElements.ticketIdvalue);
        commonLib.info("Ticket Id: " + text);
        return text;
    }

    /**
     * This method use to get first ticket Workgroup SLA from the supervisor ticket list
     * @return String The value
     * */
    public String getWorkgroupSLA() {
        return getText(pageElements.workgroupSLA);
    }

    /**
     * This method use to get first ticket Priority from the supervisor ticket list
     * @return String The value
     * */
    public String getPriorityValue() {
        return getText(pageElements.priorityValue);
    }

    /**
     * This method use to get first ticket State from the supervisor ticket list
     * @return String The value
     * */
    public String getStatevalue() {
        return getText(pageElements.statevalue);
    }

    /**
     * This method use to get first ticket Creation date from the supervisor ticket list
     * @return String The value
     * */
    public String getCreationDatevalue() {
        return getText(pageElements.creationdatevalue);
    }

    /**
     * This method use to get first ticket Queue from the supervisor ticket list
     * @return String The value
     * */
    public String getQueueValue() {
        return getText(pageElements.queueValue);
    }

    /**
     * This method use to get first ticket Issue from the supervisor ticket list
     * @return String The value
     * */
    public String getIssueValue() {
        return getText(pageElements.issueValue);
    }

    /**
     * This method use to get first ticket Issue type from the supervisor ticket list
     * @return String The value
     * */
    public String getIssueTypeValue() {
        return getText(pageElements.issueTypeValue);
    }

    /**
     * This method use to get first ticket Issue Sub type from the supervisor ticket list
     * @return String The value
     * */
    public String getSubTypeValue() {
        return getText(pageElements.subTypeValue);
    }

    /**
     * This method use to get first ticket Issue Sub Sub type from the supervisor ticket list
     * @return String The value
     * */
    public String getsubSubTypeValue() {
        return getText(pageElements.subSubTypeValue);
    }

    /**
     * This method use to get first ticket Issue Code from the supervisor ticket list
     * @return String The value
     * */
    public String getCodeValue() {
        return getText(pageElements.codeValue);
    }

    /**
     * This method use to get first ticket assigned to user auuid from the supervisor ticket list
     * @return String The value
     * */
    public String getAssignedTo() {
        return getText(pageElements.assignedto);
    }

    /**
     * This method use to select first ticket from the supervisor ticket list
     * */
    public void clickCheckbox() {
        commonLib.info("Selecting Ticket");
        clickWithoutLoader(pageElements.checkBox);
    }

    /**
     * This method use to click assign to agent button
     * */
    public void clickAssigntoAgent() {
        commonLib.info("Clicking on Assign to Agent Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.assignToagentBtn);
    }

    /**
     * This method use to check whether assign to agent button display or not
     * @return true/false
     * */
    public boolean isAssignToAgent() {
        commonLib.info("Validate Assign to Agent Button Available");
        return isEnabled(pageElements.assignToagentBtn);
    }

    /**
     * This method use to click transfer to queue button
     * */
    public void clickTransfertoQueue() {
        commonLib.info("Clicking on Transfer to Queue Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.transfertoQueueBtn);
    }

    /**
     * This method use to check whether Transfer to Queue button display or not
     * @return true/false
     * */
    public boolean isTransferToQueue() {
        commonLib.info("Validate Transfer to Queue Button Available");
        return isEnabled(pageElements.transfertoQueueBtn);
    }

    /**
     * This method use to check open ticket list open or not
     * @return true/false
     * */
    public boolean checkOpenTicketStateType() {
        commonLib.info("Checking Open Ticket State Type Select");
        return isEnabled(pageElements.openTicketType);
    }

    /**
     * This method use to check closed ticket list open or not
     * @return true/false
     * */
    public boolean checkClosedTicketstateType() {
        commonLib.info("Checking Closed Ticket State Type Select");
        return isEnabled(pageElements.closedTicketType);
    }

    /**
     * This method use to change ticket state type to closed from open
     * */
    public void changeTicketTypeToClosed() {
        commonLib.info("Switch Ticket State Type to closed");
        clickWithoutLoader(pageElements.selectTicketType);
        clickAndWaitForLoaderToBeRemoved(pageElements.closedTicketType);
    }

    /**
     * This method use to change ticket state type to open from closed
     * */
    public void changeTicketTypeToOpen() {
        commonLib.info("Switch Ticket State Type to Open");
        clickAndWaitForLoaderToBeRemoved(pageElements.selectTicketType);
        clickAndWaitForLoaderToBeRemoved(pageElements.openTicketType);
    }

    /**
     * This method use to open view ticket detail page
     * */
    public void viewTicket() {
        commonLib.info("View Ticket: " + getTicketIdValue());
        clickAndWaitForLoaderToBeRemoved(pageElements.stateLabel);
    }

    /**
     * This method use to check no ticket found page display or not
     * @return true/false
     * */
    public boolean noTicketFound() {
        commonLib.info("No ticket found");
        final boolean visible = isElementVisible(pageElements.noResultFound);
        commonLib.info("Is No Ticket Found :" + visible);
        return visible;
    }

    /**
     * This method use to click Select filter button
     * */
    public void clickFilter() {
        commonLib.info("Selecting Filter");
        clickAndWaitForLoaderToBeRemoved(pageElements.selectFilterBtn);
    }

    /**
     * This method use to click remove/reset filter button
     * */
    public void resetFilter() {
        commonLib.info("Removing Filter");
        clickAndWaitForLoaderToBeRemoved(pageElements.resetFilterButton);
    }

    /**
     * This method use to check whether remove/reset filter button display or not
     * @return true/false
     * */
    public boolean isResetFilter() {
        commonLib.info("Is Removing Filter Button Available");
        try {
            return isEnabled(pageElements.resetFilterButton);
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    /**
     * This method use to check after applying filter by Queue all ticket display on first page same to applied filter or not
     * @return true/false
     * */
    public boolean validateQueueFilter(String text) {
        commonLib.info("Validating Queue Filter");
        boolean answer = false;
        if (getListSize() > 0) {
            for (int i = 1; i <= getListSize(); i++) {
                By queue = By.xpath(pageElements.ticketRow + i + pageElements.queueName);
                commonLib.info(getText(queue).trim() + " : " + text + " :" + getText(queue).trim().equalsIgnoreCase(text));
                answer = getText(queue).trim().equalsIgnoreCase(text);
            }
        } else {
            commonLib.warning("No Ticket Found");
        }
        return answer;
    }
    /**
     * This method use to click reopen button
     * */
    public void clickReopenButton() {
        commonLib.info("Clicking Reopen Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.reOpenBtn);
    }

    /**
     * This method use to add reopen comment
     * */
    public void addReopenComment(String comment) {
        commonLib.info("Add Reopen Comment: " + comment);
        enterText(pageElements.reOpenBox, comment);
        clickOutside();
    }

    /**
     * This method use to click reopen submit button
     * */
    public void submitReopenReq() {
        commonLib.info("Clicking on Submit Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.submitReopenComment);
    }

    /**
     * This method use to close reopen pop up tab
     * */
    public void closedReopenBox() {
        commonLib.info("Clicking on closing reopen comment box Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeReopenCommentBox);
    }

    /**
     * This method use to check reopen button display or not
     * @return true/false
     * */
    public boolean isReopenBtn() {
        final boolean state = isEnabled(pageElements.reOpenBtn);
        commonLib.info("Is Reopen Button Available: " + state);
        return state;
    }

    /**
     * This method use to check ticket assignee auuid same as expected auuid or not
     * @return true/false
     * */
    public boolean checkAssignedAUUID(String auuid) {
        commonLib.info("Ticket Validated and Assigned to Agent AUUID: " + auuid);
        By agentAUUID = By.xpath(pageElements.optionText + auuid + "')]");
        return isEnabled(agentAUUID);
    }

    /**
     * This method use to get escalation symbol on ticket
     * */
    public By getEscalationSymbol() {
        commonLib.info("Getting Escalation level");
        return pageElements.allTicket;
    }

    /**
     * This method use to get first ticket Assignee auuid from supervisor ticket list page
     * @return String The value
     * */
    public String getAssigneeAUUID() {
        try {
            final String text = getText(pageElements.assigneeAUUID);
            commonLib.info("Ticket Assignee to :" + text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "Not Assigned";
        }
    }

    /**
     * This method use to get first ticket Assignee name from supervisor ticket list page
     * @return String The value
     * */
    public String getAssigneeName() {
        try {
            final String text = getText(pageElements.assigneeName);
            commonLib.info("Ticket Assignee to :" + text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "Not Assigned";
        }
    }

    /**
     * This method use to check first ticket is unassigned state or not
     * @return true/false
     * */
    public Boolean isNotAssigneeDisplay() {
        commonLib.info("Checking Is not assigned displayed");
        return isEnabled(pageElements.notAssigned);
    }

    /**
     * This method use to check ticket SLA is negative or not
     * @return true/false
     * */
    public boolean isNegativeSLA() {
        try {
            final boolean state = isEnabled(pageElements.redDot);
            commonLib.info("Checking red dot symbol for negative SLA: " + state);
            return state;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * This method use to check supervisor all assigned ticket tab display or not
     * @return true/false
     * */
    public boolean isAllTicketTab() {
        try {
            boolean flag = isEnabled(pageElements.allTicketTab);
            commonLib.info("IS All Assigned Ticket Tab displayed: " + flag);
            return flag;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * This method use to check supervisor MY assigned ticket tab display or not
     * @return true/false
     * */
    public boolean isMyAssignedTicketTab() {
        try {
            boolean flag = isEnabled(pageElements.myTicketTab);
            commonLib.info("IS My Assigned Ticket Tab displayed: " + flag);
            return flag;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * This method use to check ticket SLA is positive or not
     * @return true/false
     * */
    public boolean isPositiveSLA() {
        try {
            final boolean state = isEnabled(pageElements.greenDot);
            commonLib.info("Checking green dot symbol for positive SLA: " + state);
            return state;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * This method use to get all ticket id from first page of supervisor ticket list
     * @return List The list of all ticket id
     * */
    public List<String> getALLTicketId() {
        List<WebElement> list = returnListOfElement(pageElements.allTicket);
        List<String> ticketList = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By ticket = By.xpath(pageElements.ticketRow + i + pageElements.ticketIdText);
            ticketList.add(getText(ticket).trim());
            commonLib.info("Ticket Id: " + getText(ticket).trim());
        }
        return ticketList;
    }

    /**
     * This method use to get total number ticket id present on first page of supervisor ticket list
     * */
    public int getListSize() {
        List<WebElement> list = returnListOfElement(pageElements.allTicket);
        final int size = list.size();
        commonLib.info("Size: " + size);
        return size;
    }

    /**
     * This method use to get Escalation symbol
     * @return String The value
     * */
    public String getSymbol(int i) {
        By ticket = By.xpath(pageElements.ticketRow + i + pageElements.ticketIdText);
        By symbol = By.xpath(pageElements.ticketRow + i + pageElements.ticketEscalation);
        final String text = getText(symbol);
        commonLib.info(text + ": Escalation symbol found on ticket Id: " + getText(ticket).trim());
        return text.trim();
    }

    /**
     * This method use to click drop down icon to open list of search by option
     * */
    public void clickTicketOption() {
        commonLib.info("Click on Ticket Icon to get list of option");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchOptionBtn);
    }

    /**
     * This method use to get list of search by option
     * @return List The list of search option
     * */
    public List<String> getListOfSearchOption() {
       commonLib.info("Getting Search Option");
        List<WebElement> list = returnListOfElement(pageElements.allSearchOption);
        List<String> searchOption = new ArrayList<>();
        for (int i = 1; i <= list.size(); i++) {
            By search = By.xpath("//ul[@class='ng-star-inserted']//li[" + i + "]");
            commonLib.info("Options Available : " + getText(search));
            searchOption.add(getText(search).trim());
        }
        return searchOption;
    }

    /**
     * This method use to get MSISDN of first ticket
     * @return String The value
     * */
    public String getMSISDN() {
        final String text = getText(pageElements.msisdn);
        commonLib.info("Reading MSISDN: " + text);
        return text;
    }

    /**
     * This method use click search By option  based on text
     * @param text Search By Option name
     * */
    public void clickSearchOptionByTextNoIgnoreCase(String text) {
        commonLib.info("Clicking search By option: " + text);
        By option = By.xpath(pageElements.selectByOption + text + "']");
        clickAndWaitForLoaderToBeRemoved(option);
    }

    /*
    This Method will be used to check Source Title is visible or not under Ticket Listing
     */
    public Boolean isSourceTitleVisible() {
        boolean result = false;
        result =isVisible(pageElements.sourceTitleTicketRowTicketListing);
        highLighterMethod(pageElements.sourceTitleTicketRowTicketListing);
        return result;
    }

    /*
    This Method will check source title for all the row present in a Page
     */
    public Boolean checkSourceTitleListingPage() {
        boolean result = false;
        final List<WebElement> listFromBy = getElementsListFromBy(pageElements.sourceTitleText);
        for (WebElement list : listFromBy) {
            result = StringUtils.isNoneBlank(selUtils.getText(list));
        }
        return result;
    }

    /**
     * This method is use to open ticket detail page
     */
    public void clickToOpenTicketFromDashboard() {
        if (isVisible(pageElements.openTicketDetailPage)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.openTicketDetailPage);
        } else {
            commonLib.error("Ticket Data is NOT available over dashboard");
        }
    }

    /**
     * This method is use get title of source detail page
     * @return String The value
     */
    public String checkSourceTitleDetailPage() {
        return getText(pageElements.sourceTitleText);
    }

    /**
     * This method is use to click back button and redirect to ticket list page
     */
    public void goBackToTicketListing() {
        if (isVisible(pageElements.backButtonDetailPage)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.backButtonDetailPage);
        }
    }

    /**
     * This method use to click select all checkbox
     * */
    public void clickSelectAll(){
        commonLib.info("Clicking on Select All Button");
        clickWithoutLoader(pageElements.selectAll);
    }

    /**
     * This method use to close overlay tab
     * */
    public void clickCloseTab(){
        commonLib.info("Closing the open overlay tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeTabBtn);
    }

    /**
     * This method is use to get all the state name from queue state meta info
     * @param assignState The List of queue state meta info
     * @return List The list of state name
     */
    public List<String> getAssignedStateName(List<QueueStates> assignState){
        List<String> state=new ArrayList<>();
        if (assignState != null) {
            if (assignState.isEmpty()) {
                return state;
            }
            for (QueueStates s : assignState) {
                commonLib.info("State Mapped in Application DB: " + s.getExternalStateName());
                state.add(s.getExternalStateName());
            }
        }
        return state;
    }

    /**
     * This method is use to validate all the state mapped to ticket queue as per config
     * @param state The ticket states
     * @param configState The config states
     * @param queueName The queue name
     */
    public void matchStateName(List<String> state,List<String> configState,String queueName){
        for (String s : state) {
            assertCheck.append(actions.assertEqual_boolean(configState.remove(s.toLowerCase().trim()),true,s + " :State must mapped to '" + queueName+ "' as its mention in config.",s + " :State must not mapped to '" + queueName+ "' as its not mention in config."));
        }
    }

    /**
     * This method is use to compare all workgroup which are mentioned in Excel sheet displayed on UI or not.
     * @param sla The API Workgroups
     * @param workGroups The excel sheet workgroups
     */
    public void compareWorkgroupName(Map<String, Long> sla, Map<String, String> workGroups) {
        for (Map.Entry mapElement : sla.entrySet()) {
            String key = (String) mapElement.getKey();
            String value = mapElement.getValue().toString();
            assertCheck.append(actions.assertEqual_stringType(workGroups.remove(key), key, key + " : workgroup is configured correctly in DB as mentioned in configuration", key + " : workgroup is not configured correctly in DB as mentioned in configuration"));
            if (!UtilsMethods.isValueNegative(value)) {
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isPositiveSLA(), true, "For positive SLA green symbol display", "For positive SLA green symbol does not display"));
            } else {
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isNegativeSLA(), true, "For Negative SLA red symbol display", "For negative SLA red symbol does not display"));
            }
        }
    }

    /**
     *This method is use to get workgroup name and sla if workgroup sla not null
     * @param data The NftrDataBeans Object
     * @return Map <workgroupName,SLA>
     */
    public Map<String, String> getWorkgroupConfig(NftrDataBeans data){
        Map<String, String> workGroups=new HashMap<>();
        if (data.getWorkgroup1() != null)
            workGroups.put(data.getWorkgroup1(), data.getSla1());
        if (data.getWorkgroup2() != null)
            workGroups.put(data.getWorkgroup2(), data.getSla2());
        if (data.getWorkgroup3() != null)
            workGroups.put(data.getWorkgroup3(), data.getSla3());
        if (data.getWorkgroup4() != null)
            workGroups.put(data.getWorkgroup4(), data.getSla4());
        return workGroups;
    }


    /**
     * This method is use to compare all workgroup which are mentioned in Excel sheet displayed on UI or not.
     * @param ticketLayout The API ticket layout
     * @param configTicketLayout The excel sheet ticket layout
     */
    public void compareTicketLayout(List<IssueDetails> ticketLayout, List<String> configTicketLayout) {
        if (ticketLayout.size() == 0) {
            for (IssueDetails layout : ticketLayout) {
                assertCheck.append(actions.assertEqual_boolean(configTicketLayout.remove(layout.getPlaceHolder().toLowerCase().trim()), true, layout.getPlaceHolder() + " : Ticket Layout configured in database as mention in Config sheet.", layout.getPlaceHolder() + " : Ticket Layout does not configured in database as mention in Config sheet."));
            }
        } else {
            commonLib.pass("No Ticket Layout Config in database");
        }
    }

    /**
     * This method is use to transfer ticket to given queue return true if transfer action performed
     * @param queueName Queue Name
     * @return true/false
     */
    public Boolean transferTicketToSelectedQueue(String queueName){
        boolean flag=false;
        try {
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isAssignToAgent(), true, "Assign to Agent Button Does Available after selecting ticket", "Assign to Agent Button Does Not Available after selecting ticket"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(), true, "Transfer to Queue Button Does Available after selecting ticket", "Transfer to Queue Button Does Not Available after selecting ticket"));
            pages.getSupervisorTicketList().clickTransfertoQueue();
            assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(), true, "Transfer to Queue Pop up open as expected", "Transfer to Queue Page Title Does not Display"));
            pages.getTransferToQueue().clickTransferQueue(queueName);
            flag=true;
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace(), true);
            pages.getTransferToQueue().clickCloseTab();
        }
        return flag;
    }

}
