package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class supervisorTicketListPagePOM extends BasePage {

    By searchTicketBox= By.xpath("//html//body//app-root//app-dashboard//div//app-admin-panel//div//div//app-sidenav-bar//mat-sidenav-container//mat-sidenav-content//div//app-service-request//div//app-backend-supervisor//mat-sidenav-container//mat-sidenav-content//section//div//div//div//app-ticket-search-box//span//input");
    By searchTicketBtn=By.xpath("//html//body//app-root//app-dashboard//div//app-admin-panel//div//div//app-sidenav-bar//mat-sidenav-container//mat-sidenav-content//div//app-service-request//div//app-backend-supervisor//mat-sidenav-container//mat-sidenav-content//section//div//div//div//app-ticket-search-box//span//button");
    By ticketIdLabel=By.xpath("//div//div//div//div//div//div//div//div//div//ul[1]//li[1]//span[1]");
    By ticketIdvalue=By.xpath("//ul[1]//li[1]//span[2]");
    By workGroupName=By.xpath("//body/app-root/app-dashboard/div/app-admin-panel/div/div/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-backend-supervisor/mat-sidenav-container/mat-sidenav-content/section/div/div/div/div/div/ul[1]/li[2]/span[1]");
    By workgroupSLA=By.xpath("//ul[1]//li[2]//span[2]");
    By prioritylabel=By.xpath("//div//div//div//div//div//div//div//div//div//ul[1]//li[3]//span[1]");
    By priorityValue=By.xpath("//div//div//div//div//div//div//div//div//div//ul[1]//li[3]//span[1]");
    By stateLabel=By.xpath("//div//div//div//div//div//div//div//div//div//ul[1]//li[4]//span[1]");
    By statevalue=By.xpath("//ul[1]//li[4]//span[2]");
    By creationdateLabel=By.xpath("//ul[1]//li[5]//span[1]");
    By creationdatevalue=By.xpath("//ul[1]//li[5]//span[2]");
    By createdbyLabel=By.xpath("//li[6]//span[1]");
    By createdbyvalue=By.xpath("//li[6]//span[2]");
    By queueLabel=By.xpath("//li[7]//span[1]");
    By queueValue=By.xpath("//li[7]//span[1]");
    By issueLabel=By.xpath("//ul[2]//li[1]//span[1]");
    By issueValue=By.xpath("//ul[2]//li[1]//span[2]");
    By issueTypeLabel=By.xpath("//ul[2]//li[2]//span[1]");
    By issueTypeValue=By.xpath("//ul[2]//li[2]//span[2]");
    By subTypeLabel=By.xpath("//ul[2]//li[3]//span[1]");
    By subTypeValue=By.xpath("//ul[2]//li[3]//span[2]");
    By subSubTypeLabel=By.xpath("//ul[2]//li[4]//span[1]");
    By subSubTypeValue=By.xpath("//ul[2]//li[4]//span[2]");
    By codeLabel=By.xpath("//ul[2]//li[5]//span[1]");
    By codeValue=By.xpath("//ul[2]//li[5]//span[2]");
    By assignedto=By.xpath("//body//p//span[1]");

    public supervisorTicketListPagePOM(WebDriver driver) {
        super(driver);
    }

    public void writeTicketId(String ticketId){
        log.info("Search Ticket Id: "+ticketId);
        writeText(searchTicketBox,ticketId);
    }

    public void clickedSearchBtn(){
        log.info("Clicking on Search Button");
        click(searchTicketBtn);
    }

    public boolean isTicketIdLabel(){
        log.info("Is Ticket Id field Available :" + checkState(ticketIdLabel));
       return checkState(ticketIdLabel);
    }

    public boolean isWorkGroupName(){
        log.info("Ticket lie in WorkGroup :" + readText(workGroupName));
        return checkState(workGroupName);
    }

    public boolean isPrioritylabel(){
        log.info("Is Priority field Available :" + checkState(prioritylabel));
        return checkState(prioritylabel);
    }

    public boolean isStateLabel(){
        log.info("Is Priority field Available :" + checkState(stateLabel));
        return checkState(stateLabel);
    }

    public boolean isCreationdateLabel(){
        log.info("Is Priority field Available :" + checkState(creationdateLabel));
        return checkState(creationdateLabel);
    }

    public boolean isCreatedbyLabel(){
        log.info("Is Priority field Available :" + checkState(createdbyLabel));
        return checkState(createdbyLabel);
    }
    public boolean isQueueLabel(){
        log.info("Is Priority field Available :" + checkState(queueLabel));
        return checkState(queueLabel);
    }
    public boolean isIssueLabel(){
        log.info("Is Issue field Available :" + checkState(issueLabel));
        return checkState(issueLabel);
    }
    public boolean isIssueTypeLabel(){
        log.info("Is Issue Type field Available :" + checkState(issueTypeLabel));
        return checkState(issueTypeLabel);
    }
    public boolean isSubTypeLabel(){
        log.info("Is Issue Sub Type field Available :" + checkState(subTypeLabel));
        return checkState(subTypeLabel);
    }
    public boolean isSubSubTypeLabel(){
        log.info("Is Issue Sub Sub Type field Available :" + checkState(subSubTypeLabel));
        return checkState(subSubTypeLabel);
    }

    public boolean isCodeLabel(){
        log.info("Is Code field Available :" + checkState(codeLabel));
        return checkState(codeLabel);
    }

    public String getTicketIdvalue(){
        return readText(ticketIdvalue);
    }

    public String getWorkgroupSLA(){
        return readText(workgroupSLA);
    }

    public String getPriorityValue(){
        return readText(priorityValue);
    }

    public String getStatevalue(){
        return readText(statevalue);
    }

    public String getCreationdatevalue(){
        return readText(creationdatevalue);
    }

    public String getqueueValue(){
        return readText(queueValue);
    }

    public String getIssueValue(){
        return readText(issueValue);
    }

    public String getIssueTypeValue(){
        return readText(issueTypeValue);
    }

    public String getSubTypeValue(){
        return readText(subTypeValue);
    }

    public String getsubSubTypeValue(){
        return readText(subSubTypeValue);
    }

    public String getCodeValue(){
        return readText(codeValue);
    }

    public String getAssignedto(){
        return readText(assignedto);
    }

}
