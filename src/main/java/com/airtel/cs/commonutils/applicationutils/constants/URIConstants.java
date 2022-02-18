package com.airtel.cs.commonutils.applicationutils.constants;

public class URIConstants {

    /**
     *  CS URI Constants
     */
    public static final String ACCOUNT_PLAN = "/cs-gsm-service/v1/account/plans";
    public static final String USAGE_HISTORY = "/cs-gsm-service/v1/usage/history";
    public static final String GSM_AM_PROFILE = "/cs-gsm-service/v1/am/profile";
    public static final String GSM_KYC = "/cs-gsm-service/v1/gsm/kyc";
    public static final String GSM_PROFILE = "/cs-gsm-service/v1/profile";
    public static final String KYC_PROFILE = "/cs-gsm-service/v1/kyc/profile";
    public static final String SEARCH = "/api/cs-gsm-service/v1/search";
    public static final String AUTOFILL_ISSUE_FIELD = "/api/sr-service/v1/autofilled/layout";
    public static final String AUTOFILL_CONFIGS = "/api/sr-service/v1/autofill/configs";
    public static final String AM_PROFILE = "/cs-am-service/v1/profile";
    public static final String RECHARGE_HISTORY = "/cs-gsm-service/v1/recharge/history";
    public static final String BUNDLE_RECHARGE_HISTORY = "/cs-gsm-service/v1/bundle/recharge/history";
    public static final String TRANSACTION_HISTORY = "/cs-am-service/v1/transaction/history";
    public static final String ACCOUNT_BALANCE = "/cs-gsm-service/v1/accounts/balance";
    public static final String SR_FETCH_HISTORY = "/sr/api/sr-service/v1/fetch/ticket";
    public static final String NOTIFICATION_FETCH_HISTORY = "/cs-notification-service/v1/fetch/history";
    public static final String VOUCHER_DETAILS = "/cs-gsm-service/v1/voucher/detail";
    public static final String OVERSCRATCH_RECHARGE = "/cs-gsm-service/v1/voucher/overscratch/recharge";
    public static final String VENDORS = "/cs-vas-service/v1/vendors";
    public static final String LOAN_SUMMARY = "/cs-vas-service/v1/loan/summary";
    public static final String LOAN_DETAILS = "/cs-vas-service/v1/loan/details";
    public static final String REFILL_STATUS = "/cs-gsm-service/v1/refill/status";
    public static final String SEARCH_TUNES = "/cs-vas-service/v1/search/tunes";
    public static final String FETCH_TUNES = "/cs-vas-service/v1/fetch/tunes";
    public static final String ACCUMULATORS = "/cs-gsm-service/v1/accumulators";
    public static final String SERVICE_PROFILE = "/cs-gsm-service/v1/hlr/service/profiles";
    public static final String HLR_ORDER_HISTORY_V1 = "/cs-gsm-service/v1/hlr/order/history";
    public static final String AUTH_USER = "/cs-service/api/cs-service/v1/auth/user";
    public static final String CONFIGURATIONS = "/cs-service/api/cs-service/v1/configurations";
    public static final String TARIFF_AVAILABLE_PLANS = "/cs-gsm-service/v1/tariff/available-plans";
    public static final String TARIFF_CURRENT_PLAN = "/cs-gsm-service/v1/tariff/current-plan";
    public static final String V2_LOGIN = "/auth/api/user-mngmnt/v2/login";
    public static final String OFFER_DETAILS = "/cs-gsm-service/v1/offer/details";
    public static final String FRIENDS_FAMILY = "/cs-gsm-service/v1/friendsNfamily/details";
    public static final String AGENT_PERMISSION = "/sr/api/sr-service/v1/agent/permissions";
    public static final String FETCH_TICKET_POOL = "/sr/api/sr-service/v1/fetch/ticketpool";
    public static final String AGENT_DETAILS = "/sr/api/sr-service/v1/agents";
    public static final String EVENTS_LOG="cs-data-service/v1/event/logs";
    public static final String ADJUSTMENT_ACTION="cs-gsm-service/v1/adjustment/mapping?action=";
    public static final String ADJUSTMENT_HISTORY="/cs-gsm-service/v1/adjustments";
    public static final String POSTPAID_ACCOUNT_INFORMATION = "/cs-gsm-service/v1/postpaid/account/information";
    public static final String ACTION_CONFIG = "/cs-service/api/cs-service/v1/actions/config";
    public static final String GET_FIELD_MASK_CONFIG = "/cs-service/api/cs-service/v1/get/field/mask/config";
    public static final String AGENT_LIMIT_API = "/cs-service/api/cs-service/v1/limit/configuration";
    public static final String SAVE_AGENT_LIMIT_API = "/cs-service/api/cs-service/v1/limit/save/configuration";
    public static final String GET_PARENT_CATEGORY_V1 = "/sr/api/sr-service/v1/parent/categories";
    public static final String GET_TICKET_HISTORY_V1 = "/sr/api/sr-service/v1/tickets";
    public static final String PLAN_AND_PACK = "/cs-gsm-service/v1/postpaid/plan-pack/details";
    public static final String CURRENT_PLAN = "/cs-gsm-service/v1/postpaid/currentplan";
    public static final String POSTPAID_ACCOUNT_DETAILS = "/cs-gsm-service/v1/postpaid/account/details";
    public static final String TICKET_HISTORY_LOG="/sr/api/sr-service/v1/fetch/ticket/history/log";
    public static final String POSTPAID_ACCOUNT_MSISDN_DETAILS = "/cs-gsm-service/v1/postpaid/msisdn/details";
    public static final String GET_CONFIGURATION_API="/api/cs-service/v1/config";
    public static final String CREATE_CONFIGURATION_API="/api/cs-service/v1/create/config";
    public static final String DELETE_CONFIGURATION_API="/api/cs-service/v1/delete/config";
    public static final String UPDATE_CONFIGURATION_API="/api/cs-service/v1/update/config";
    public static final String GET_HBB_LINKED_ACCOUNTS_API="/cs-gsm-service/v1/hbb/linked/accounts";
    public static final String NOTIFICATION_SERVICE_API="/cs-notification-service/v1/send/notification";

    public static final String ENTERPRISE_POSTPAID_ACCOUNT_INFORMATION="/cs-gsm-service/v1/enterprise/accounts";
    public static final String ENTERPRISE_ACCOUNT_SEARCH = "/cs-gsm-service/v1/enterprise/search";
    public static final String ENTERPRISE_LINKED_SERVICES="/cs-gsm-service/v1/enterprise/linked/services";
    public static final String ENTERPRISE_INTERACTION_HISTORY="/sr/api/sr-service/v1/issue/history";
    public static final String ENTERPRISE_PAYMENT_HISTORY = "/cs-gsm-service/v1/enterprise/payment/history";
    public static final String VAS_SUBSCRIPTION_HISTORY="/cs-vas-service/v1/subscriptions/history";

    /**
     * SR URI Constants
     */

    public static final String TICKET_STATS = "/api/sr-service/v1/ticket/stats";
    public static final String HBB_USER = "/api/user-service/user/v1/details";
    public static final String INTERACTION_ISSUE = "/api/sr-service/v1/interactions/issue";
    public static final String OPEN_API_INTERACTION_ISSUE = "/api/sr-service/v1/openapi/interactions/issue";
    public static final String OPEN_API_CREATE_COMMENT = "/api/sr-service/v1/openapi/comment";
    public static final String OPEN_API_UPDATE_COMMENT = "/api/sr-service/v1/openapi/update/comment";
    public static final String OPEN_API_DELETE_COMMENT = "/api/sr-service/v1/openapi/delete/comment";
    public static final String OPEN_API_CLIENT_CONFIG = "/api/sr-service/v1/openapi/clients/config";
    public static final String OPEN_API_FETCH_TICKET = "/api/sr-service/v1/openapi/fetch/ticket";
    public static final String OPEN_API_FETCH_TICKET_HISTORY_LOG = "/api/sr-service/v1/openapi/fetch/ticket/history/log";
    public static final String OPEN_API_SEARCH_TICKET = "/api/sr-service/v1/openapi/tickets";
    public static final String OPEN_API_ISSUE_LAYOUT = "/api/sr-service/v1/openapi/layout";
    public static final String OPEN_API_CHILD_CATEGORY = "/api/sr-service/v1/openapi/child/categories";
    public static final String OPEN_API_PARENT_CATEGORY = "/api/sr-service/v1/openapi/parent/categories";
    public static final String OPEN_API_FIRST_LAST = "/api/sr-service/v1/openapi/firstlast/categories";
    public static final String REOPEN_TICKET = "/api/sr-service/v1/ticket/reopen";
    public static final String FETCH_TICKET = "/api/sr-service/v1/fetch/ticket";
    public static final String ISSUE_HISTORY = "/api/sr-service/v1/issue/history";
    public static final String CREATE_ISSUE = "/api/sr-service/v1/issue";
    public static final String FETCH_TICKET_HISTORY_LOG = "/api/sr-service/v1/fetch/ticket/history/log";
    public static final String DEACTIVATE_CLIENT_CONFIG = "/api/sr-service/v1/deactivate/clients/config";
    public static final String CLIENT_CONFIG = "/api/sr-service/v1/clients/config";
    public static final String CREATE_INTERACTION = "/api/sr-service/v1/interactions";
    public static final String ISSUE_LAYOUT = "/api/sr-service/v1/layout";
    public static final String PARENT_CATEGORY = "/api/sr-service/v1/parent/categories";
    public static final String FIRST_LAST = "/api/sr-service/v1/firstlast/categories";
    public static final String CONFIGURED_CLIENTS = "/api/sr-service/v1/client/fields";
    public static final String UPDATE_TICKET = "/api/sr-service/v1/update/ticket";
    public static final String TICKETS_BY_AGENT = "/api/sr-service/v1/tickets/agent";
    public static final String TICKET_HISTORY = "/api/sr-service/v1/tickets";
    public static final String ASSIGN_TICKET = "/api/sr-service/v1/assign/ticket";
    public static final String V2_LAYOUT_CONFIG = "/api/sr-service/v2/layout";
    private URIConstants() {

    }
}
