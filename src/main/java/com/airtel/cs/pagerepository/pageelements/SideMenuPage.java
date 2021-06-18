package com.airtel.cs.pagerepository.pageelements;


import org.openqa.selenium.By;

public class SideMenuPage {
    public By sideMenuOption = By.xpath("//*[@id='left-side']//mat-icon");
    public By visi = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    public By userName = By.xpath("//*[@class='ellipsis']");
    public By adminSettings = By.xpath("//mat-list-item//span[contains(text(),'Admin')]");
    public By customerServices = By.xpath("//mat-list-item//span[contains(text(),'Customer')]/..");
    public By caseManagement = By.xpath("//mat-list-item//span[contains(text(),'Case')]");
    public By userManagement = By.xpath("//a[contains(text(),'User management') or contains(text(),'User Management')]");
    public By profileManagement = By.xpath("//a[contains(text(),'Profile Management')]");
    public By customerInteraction = By.xpath("//a[contains(text(),'Customer Interaction')]");
    public By templateManagement = By.xpath("//a[contains(text(),'Template Management')]");
    public By ticketBulkUpdate = By.xpath("//a[contains(text(),'Bulk')]");
    public By supervisorDashboard = By.xpath("//a[contains(text(),'Supervisor Dashboard')]");
    public By agentDashboard = By.xpath("//a[contains(text(),'Ticket Dashboard')]");
    public By logout = By.xpath("//span[@class='logout-icon']");
    public By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    public By backendAgentDashboard = By.xpath("//*[@data-csautomation-key='pageTitle']");
}
