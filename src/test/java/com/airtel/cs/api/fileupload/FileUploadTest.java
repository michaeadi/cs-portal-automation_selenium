package com.airtel.cs.api.fileupload;

import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.model.response.fileupload.FileUploadResponse;
import org.testng.annotations.Test;


public class FileUploadTest extends ApiPrerequisites {
    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"ProdTest"})
    public void uploadAPITest() {
        selUtils.addTestcaseDescription("Validate /api/sr-service/v1/files API with valid Request", "description");
        FileUploadResponse fileUpload;
        fileUpload = api.fileUpload();
        assertCheck.append(actions.assertEqualStringType(fileUpload.getStatusCode(), "200", "Status Code Matched", "Status Code Not Matched and is - " + fileUpload.getStatusCode()));
    }

}

