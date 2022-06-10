package com.airtel.cs.commonutils.applicationutils.constants;

public class ESBURIConstants {
    /*
    EndPoint Constants
     */
    public static final String USAGE_HISTORY = "/api/subscriber-profile/v1/usage";
    public static final String V3_USAGE_HISTORY = "/api/subscriber-transaction/v3/usage";
    public static final String RECHARGE_HISTORY = "/api/subscriber-profile/v1/customer/service/recharge-history";
    public static final String CUSTOMER_PROFILLE = "/api/subscriber-profile/v2/customer-profile";
    public static final String TOKEN = "/api/authorization/token";
    public static final String AM_PROFILE = "/api/am-profile/v1/users/";
    public static final String GSM_KYC_REQUEST = "/api/am-profile/v1/kycrequest";
    public static final String SELF_CARE_USER_DETAILS = "/selfcare/user-mngmnt/v1/get-selfcare-user-details";
    public static final String DEVICE_INFO = "/api/subscriber-profile/v1/deviceInfo";
    public static final String TRANSACTION_HISTORY = "/api/am-send-money/v1/transaction-history-details";
    public static final String QUERY_BALANCE = "/api/subscriber-profile/v2/query-balance";
    public static final String VOUCHER_DETAIL = "/api/vs/v1/voucher-detail";
    public static final String OSC_REFILL = "/api/vs/v1/osc-refill";
    public static final String VENDORS = "/api/vas-service/v1/loan/vendors";
    public static final String LOAN_SUMMARY = "/api/vas-service/v1/loan/summary";
    public static final String LOAN_DETAILS = "/api/vas-service/v1/loan/loan-details";
    public static final String SERVICE_CLASS_RATE_PLAN = "/api/subscriber-product/v1/fetch/service-class-rate-plan";
    public static final String VOUCHER_REFILL_BARRED = "/api/subscriber-profile/v1/voucher-refill-barred";
    public static final String TOP_TWENTY_RINGBACK_TONE = "/api/ring-back-tones/v1/top-twenty-ring-back-tone-list";
    public static final String SEARCH_NAME_TUNE = "/api/ring-back-tones/v1/search-name-tune";
    public static final String GENERIC_SEARCH_API = "/api/ring-back-tones/v1/generic-search-api";
    public static final String RING_BACK_TONE_LIST = "/api/ring-back-tones/v1/ring-back-tone-list";
    public static final String GET_ACCUMULATORS = "/api/subscriber-profile/v1/get-accumulators";
    public static final String HLR_FETCH_DETAILS = "/ps/v1/hlr/fetch-details";
    public static final String HLR_ORDER_HISTORY = "/customer-status/atomic/generic/search";
    public static final String TARIFF_AVAILABLE_PLANS = "/api/bundle-service/v1/tariff/available-plans";
    public static final String TARIFF_CURRENT_PLAN = "/api/bundle-service/v1/tariff/current-plan";
    public static final String OFFER_DETAILS = "/api/subscriber-product/v1/customer/service/getOffers";
    public static final String FRIENDS_FAMILY = "/api/subscriber-product/v1/customer/service/getFaFList";
    public static final String GET_CREDIT_LIMIT = "/api/subscriber-profile/v1/get-credit-limit";
    public static final String INVOICE_HISTORY = "/api/subscriber-profile/v1/invoice-history";
    public static final String POSTPAID_BILL_DETAILS = "/api/subscriber-profile/v1/postpaid-bill-details";
    public static final String CUSTOMER_PROFILE_V2 = "/api/subscriber-profile/v2/customer-profile";
    public static final String INVOICE_HISTORY_V1 = "/api/subscriber-profile/v1/invoice-history";
    public static final String POSTPAID_BILL_DETAIL_V1 = "/api/subscriber-profile/v1/postpaid-bill-details";
    public static final String ACCOUNT_PAYMENT = "/api/enterprise-service/v1/accounts/payments";
    public static final String CREDIT_LIMIT = "/api/subscriber-profile/v1/get-credit-limit";
    public static final String GET_USAGE = "/api/subscriber-profile/v1/get-usage";
    public static final String ACCOUNT_STATEMENT = "/api/enterprise-service/v1/accounts/statement";
    public static final String POSTPAID_ACCOUNT_DETAILS = "/api/enterprise-service/v1/accounts/statement";
    public static final String POSTPAID_ACCOUNTS_LINE = "/api/enterprise-service/v1/accounts/lines";
    public static final String MY_PLAN = "/api/subscriber-profile/v1/fetch/my-plans";
    public static final String MY_PACK = "/api/subscriber-profile/v1/fetch/my-packs";
    public static final String ENTERPRISE_SEARCH_ACCOUNT = "/api/enterprise-service/v1/accounts";
    public static final String ENTERPRISE_INVOICE_HISTORY = "/api/enterprise-service/v1/accounts/invoices";
    public static final String ENTERPRISE_ACCOUNT_LINES = "/api/enterprise-service/v1/accounts/lines";
    public static final String ENTERPRISE_PAYMENT_HISTORY = "/api/enterprise-service/v1/accounts/payments";
    public static final String ACTIVE_VAS = "/api/vass-service/v1/msisdn";
    public static final String LOAN_SUMMARY_V2 = "/api/loan-service/v2/summary";
    public static final String LOAN_DETAILS_V2 = "/api/loan-service/v2/transactions";
    public static final String TCP_LIMITS = "/api/am-profile/v1/transfer-profiles";
    public static final String SMS_LOGS1 = "/api/am-send-money/v1/users/";
    public static final String SMS_LOGS2 = "/sms-details/";
    public static final String CLM_DETAILS = "/collective/v2/psb/customer";
    public static final String PSB_TRANSCATION_HISTORY = "/arch-in/web/api/am-esb-bank/v1/transactions";
    public static final String FETCH_BALANCE1 = "/arch-in/web/api/am-esb-bank/v1/users/";
    public static final String FETCH_BALANCE2 = "/balance";
    public static final String ACCOUNT_LEVEL_INFO = "/api/enterprise-service/v1/accounts";
    public static final String PSB_SMS_SUMMMARY = "/arch-in/web/api/am-esb-bank/v1/transactions/sms-summary";
    public static final String BANK_DETAILS = "/api/am-profile/v1/bank-details?";

    /*
    HBB Downstream API
     */
    public static final String HBB_LINKED_ACCOUNT_DETAILS = "/api/linked-account-orchestrator/v2/account/detail";
    public static final String HBB_QUERY_BALANCE = "/api/subscriber-profile/v2/hbb-query-balance";

    /*
    Ip Port Constants
     */
    public static final String SUBSCRIBER_PROFILE_BASE_URL = "subscriber.profile.base.url";
    public static final String SUBSCRIBER_TRANSACTIONS_BASE_URL = "subscriber.transaction.base.url";
    public static final String VAS_SERVICE_LOAN_BASE_URL = "vas.service.loan.base.url";
    public static final String SUBSCRIBER_PRODUCT_BASE_URL = "subscriber.product.base.url";
    public static final String VOUCHER_SERVICE_BASE_URL = "voucher.service.base.url";
    public static final String AM_PROFILE_SERVICE_PROFILE_BASE_URL = "am.profile.service.base.url";
    public static final String PRODUCT_CATALOG_SERVICE_BASE_URL = "product.catalog.service.base.url";
    public static final String ENTERPRISE_SERVICE_BASE_URL = "enterprise.service.base.url";
    public static final String HBB_LINKED_ACCOUNT_ORCHESTRATOR_BASE_URL = "hbb.linked.account.orchestrator.base.url";
    public static final String GSM_SELFCARE_USER_DETAILS_URL = "gsm.self.care.user.details.api.url";
    public static final String KYC_AUTH_TOKEN_URL = "api.kyc.auth.token.url";
    public static final String VAS_SERVICE_TUNE_BASE_URL = "vas.service.tune.base.url";
    public static final String HLR_SERVICE_BASE_URL = "hlr.service.base.url";
    public static final String BUNDLE_SERVICE_BASE_URL = "bundle.service.base.url";
    public static final String COLLECTIVE_BASE_URL = "collective.api.base.url";
}