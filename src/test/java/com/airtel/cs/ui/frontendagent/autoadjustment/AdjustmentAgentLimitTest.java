package com.airtel.cs.ui.frontendagent.autoadjustment;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.agentlimit.AgentLimit;
import com.airtel.cs.model.cs.response.agentlimit.LimitConfig;
import com.airtel.cs.model.cs.response.agents.RoleDetails;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AdjustmentAgentLimitTest extends Driver {
    public static final String RUN_ADJUSTMENT_TEST_CASE = constants.getValue(ApplicationConstants.RUN_ADJUSTMENT_TEST_CASE);
    RequestSource api = new RequestSource();
    private String roleId = null;


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkAdjustmentFlag() {
        if (!StringUtils.equals(RUN_ADJUSTMENT_TEST_CASE, "true")) {
            commonLib.skip("Adjustment widget is NOT Enabled for this Opco " + OPCO);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void validateAgentLimitAPI() {
        try {
            selUtils.addTestcaseDescription("Verify that Limit configuration is successfully fetched using limit configuration API. (Roles Configured)", "description");
            boolean isLimitSet = false;
            List<RoleDetails> roles = UtilsMethods.getAgentDetail().getUserDetails().getUserDetails().getRole();
            for (RoleDetails role : roles) {
                roleId = role.getId();
                LimitConfig limitResult = UtilsMethods.getAgentLimitConfigBasedOnKey(constants.getValue(CommonConstants.ADJUSTMENT_LIMIT_API_KEY), role.getId());
                if (limitResult != null) {
                    isLimitSet = true;
                    assertCheck.append(actions.assertEqualIntNotNull(limitResult.getDailyLimit(), "Daily " + constants.getValue("cs.agent.limit.expected"), "Daily " + constants.getValue("cs.agent.limit.unexpected")));
                    assertCheck.append(actions.assertEqualIntNotNull(limitResult.getMonthlyLimit(), "Monthly " + constants.getValue("cs.agent.limit.expected"), "Monthly " + constants.getValue("cs.agent.limit.unexpected")));
                    assertCheck.append(actions.assertEqualIntNotNull(limitResult.getTransactionLimit(), "Transactional Limit " + constants.getValue("cs.agent.limit.expected"), "Transactional Limit " + constants.getValue("cs.agent.limit.unexpected")));
                    break;
                }
            }
            if (!isLimitSet) {
                commonLib.warning(constants.getValue("cs.agent.limit.not.configured"));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " - validateAgentLimitAPI " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = "RegressionTest")
    public void saveAgentLimit() {
        try {
            selUtils.addTestcaseDescription("Verify that limit configuration is successfully saved when valid limit configuration is added in the request for limit save configuration API.", "description");
            commonLib.info(constants.getValue("cs.agent.limit.configured"));
            AgentLimit agentLimit = api.saveAgentLimit(roleId, constants.getValue(CommonConstants.ADJUSTMENT_LIMIT_API_KEY), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_DAILY_LIMIT), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_MONTHLY_LIMIT), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_TRANSACTION_LIMIT));
            int statusCode = 0;
            if (ObjectUtils.isNotEmpty(agentLimit)) {
                statusCode = agentLimit.getStatusCode();
            }
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Agent Limit " + constants.getValue("cs.portal.api.success"), "Agent Limit " + constants.getValue("cs.portal.api.fail") + statusCode, false, true));
            LimitConfig limitResult = UtilsMethods.getAgentLimitConfigBasedOnKey(constants.getValue(CommonConstants.ADJUSTMENT_LIMIT_API_KEY), roleId);
            if (limitResult != null) {
                assertCheck.append(actions.assertEqualIntNotNull(limitResult.getDailyLimit(), "Daily " + constants.getValue("cs.agent.limit.expected"), "Daily " + constants.getValue("cs.agent.limit.unexpected")));
                assertCheck.append(actions.assertEqualIntNotNull(limitResult.getMonthlyLimit(), "Monthly " + constants.getValue("cs.agent.limit.expected"), "Monthly " + constants.getValue("cs.agent.limit.unexpected")));
                assertCheck.append(actions.assertEqualIntNotNull(limitResult.getTransactionLimit(), "Transactional Limit " + constants.getValue("cs.agent.limit.expected"), "Transactional Limit " + constants.getValue("cs.agent.limit.unexpected")));
            } else {
                commonLib.fail(constants.getValue("cs.agent.limit.not.configured"), false);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " - saveAgentLimit " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = "RegressionTest")
    public void saveAgentLimitWithDailyLimitExceedMonthlyLimit() {
        try {
            selUtils.addTestcaseDescription("Verify that daily limit in the limit save configuration API request can not be more than the Monthly limit.", "description");
            commonLib.info(constants.getValue("cs.agent.limit.configured"));
            AgentLimit agentLimit = api.saveAgentLimit(roleId, constants.getValue(CommonConstants.ADJUSTMENT_LIMIT_API_KEY), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_MONTHLY_LIMIT) + 1, constants.getValue(CommonConstants.AGENT_ADJUSTMENT_MONTHLY_LIMIT), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_TRANSACTION_LIMIT));
            int statusCode = 0;
            if (ObjectUtils.isNotEmpty(agentLimit)) {
                statusCode = agentLimit.getStatusCode();
            }
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Agent Limit " + constants.getValue("cs.portal.api.success"), "Agent Limit " + constants.getValue("cs.portal.api.fail") + statusCode, false));
            assertCheck.append(actions.matchUiAndAPIResponse(agentLimit.getStatus(), constants.getValue("cs.portal.failed.status"), "Agent Limit " + constants.getValue("cs.portal.api.fail"), "Agent Limit " + constants.getValue("cs.portal.api.success") + statusCode));
        } catch (Exception e) {
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " - saveAgentLimitWithDailyLimitExceedMonthlyLimit " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = "RegressionTest")
    public void saveAgentLimitWithTransactionLimitExceedDailyLimit() {
        try {
            selUtils.addTestcaseDescription("Verify that transaction limit in the limit save configuration API request can not be more than the Daily limit.", "description");
            commonLib.info(constants.getValue("cs.agent.limit.create.config"));
            AgentLimit agentLimit = api.saveAgentLimit(roleId, constants.getValue(CommonConstants.ADJUSTMENT_LIMIT_API_KEY), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_MONTHLY_LIMIT), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_MONTHLY_LIMIT), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_DAILY_LIMIT) + 1);
            int statusCode = 0;
            if (ObjectUtils.isNotEmpty(agentLimit)) {
                statusCode = agentLimit.getStatusCode();
            }
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Agent Limit " + constants.getValue("cs.portal.api.success"), "Agent Limit " + constants.getValue("cs.portal.api.fail") + statusCode, false));
            assertCheck.append(actions.matchUiAndAPIResponse(agentLimit.getStatus(), constants.getValue("cs.portal.failed.status"), "Agent Limit " + constants.getValue("cs.portal.api.fail"), "Agent Limit " + constants.getValue("cs.portal.api.success") + statusCode));
        } catch (Exception e) {
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " - saveAgentLimitWithTransactionLimitExceedDailyLimit " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 4, groups = "RegressionTest")
    public void saveAgentLimitWithRoleIdNull() {
        try {
            selUtils.addTestcaseDescription("Verify invalid Role ID can not be added in the request for limit save configuration API", "description");
            commonLib.info(constants.getValue("cs.agent.limit.create.config"));
            AgentLimit agentLimit = api.saveAgentLimit(null, constants.getValue(CommonConstants.ADJUSTMENT_LIMIT_API_KEY), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_MONTHLY_LIMIT), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_MONTHLY_LIMIT), constants.getValue(CommonConstants.AGENT_ADJUSTMENT_TRANSACTION_LIMIT));
            int statusCode = 0;
            if (ObjectUtils.isNotEmpty(agentLimit)) {
                statusCode = agentLimit.getStatusCode();
            }
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Agent Limit " + constants.getValue("cs.portal.api.success"), "Agent Limit " + constants.getValue("cs.portal.api.fail") + statusCode, false));
            assertCheck.append(actions.matchUiAndAPIResponse(agentLimit.getStatus(), constants.getValue("cs.portal.failed.status"), "Agent Limit " + constants.getValue("cs.portal.api.fail"), "Agent Limit " + constants.getValue("cs.portal.api.success") + statusCode));
        } catch (Exception e) {
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " - saveAgentLimitWithRoleIdNull " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

}
