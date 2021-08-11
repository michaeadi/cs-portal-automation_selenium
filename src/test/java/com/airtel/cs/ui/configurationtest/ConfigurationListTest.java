package com.airtel.cs.ui.configurationtest;

import com.airtel.cs.api.RequestSource;
import org.testng.annotations.Test;

public class ConfigurationListTest {
    RequestSource api=new RequestSource();

    @Test(priority = 1,groups = {"SanityTest","RegressionTest","ProdTest"})
    public void getConfigurationListWithValidateReq(){
        
    }
}
