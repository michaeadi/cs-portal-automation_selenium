package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class MessageHistoryPage {
    public By messageTypeLabel = By.xpath("//table[@id='fetchTicketByCustomer']//thead/tr[1]/th[1]//b");
    public By dateSentLabel = By.xpath("//table[@id='fetchTicketByCustomer']//thead/tr[1]/th[2]//b");
    public By templateLabel = By.xpath("//table[@id='fetchTicketByCustomer']//thead/tr[1]/th[3]//b");
    public By messageLanguageLabel = By.xpath("//table[@id='fetchTicketByCustomer']//thead/tr[1]/th[4]//b");
    public By messageTextLabel = By.xpath("//table[@id='fetchTicketByCustomer']//thead/tr[1]/th[5]//b");
    public By agentIdLabel = By.xpath("//table[@id='fetchTicketByCustomer']//thead/tr[1]/th[6]//b");
    public By agentNameLabel = By.xpath("//table[@id='fetchTicketByCustomer']//thead/tr[1]/th[7]//b");
    public By actionLabel = By.xpath("//table[@id='fetchTicketByCustomer']//thead/tr[1]/th[8]//b");
    public By listOfMessage = By.xpath("//table[@id='fetchTicketByCustomer']//tbody/tr");
    public By popUpTitle = By.xpath("//h1[contains(text(),'Resend Message')]");
    public By popUpMessage = By.xpath("//p[@class='error']");
    public By yesBtn = By.xpath("//div[@class='deactivate-popup__content mat-dialog-content']//button[2]");
    public By noBtn = By.xpath("//div[@class='deactivate-popup__content mat-dialog-content']//button[1]");
    public String messageTypeValue="]//td[1]//b";
    public String messageSentDate="]//td[2]//p//span[@class='date_time']";
    public String templateEvent="]//td[3]//p//b";
    public String messageLanguageValue="]//td[4]//p//b";
    public String messageText="]//td[5]//p//b";
    public String agentIdValue="]//td[6]//p//b";
    public String agentNameValue="]//td[7]//p//span";
    public String actionBtnEnable="]//td[8]//img";
    public String actionButtonDisable="]//td[8]//img[@class='disabled-icon']";
}
