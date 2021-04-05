package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class UserManagementElements {
    public By roles =new AirtelByWrapper("roles --RolesManagement",By.xpath("//*[text()='Roles']"));
    public By editCSBetaUserRole = new AirtelByWrapper("editCSBetaUserRole -- RoleList",By.xpath("//*[text()=' Automation CS Beta User Role ']/ancestor::td/following-sibling::td//*[text()=' Edit ']"));
    public By updateRoleBtn =new AirtelByWrapper("updateRoleBtn --PermissionList",By.xpath("//*[text()=' UPDATE ROLE ']"));
    public By logoutUMBtn = new AirtelByWrapper("logoutUMBtn --UMHomePage",By.xpath("//*[text()='power_settings_new']"));

}
