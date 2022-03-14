package com.airtel.cs.api;

import com.airtel.cs.commonutils.restutils.RestCommonUtils;

public class PsbRequestSource extends RestCommonUtils {

    private static final String CLM_DETAILS = "CLM details";
    private static final String SEARCHID = "searchid";
    private ESBRequestSource esbRequestSource = new ESBRequestSource();

    /*
       This Method will hit the Available Plan API and returns the response
        */
    /*public AMUserProfileApiResponse getCLMDetails(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + CLM_DETAILS, JavaColors.GREEN, false);
        AMUserProfileApiResponse result = null;
        try {
            queryParam.clear();
            queryParam.put(SEARCHID, msisdn);
            commonGetMethodWithQueryParam(URIConstants.ROOT_URL + URIConstants.CLM_DETAILS, queryParam);
            result = response.as(AMUserProfileApiResponse.class);
            if (result.getStatusCode() != 200) {

            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getCLMDetails " + e.getMessage(), false);

        }
        return result;
    }*/
}