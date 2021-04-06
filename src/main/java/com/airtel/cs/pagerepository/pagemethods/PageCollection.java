package com.airtel.cs.pagerepository.pagemethods;

import org.openqa.selenium.WebDriver;

public class PageCollection {


    /**
     * The driver.
     */
    private final WebDriver driver;
    private SideMenuPage sideMenuPOM;
    private LoginPage loginPage;
    private CustomerProfilePage customerProfilePage;
    private TariffPlanPage tariffPlanPage;
    private MsisdnSearchPage msisdnSearchPage;
    private ViewHistoryPage viewHistory;
    private UserManagementPage userManagementPage;
    private ActionTrailPage actionTrailPage;
    private AgentLoginPage agentLoginPage;
    private AMTransactionsWidgetPage amTxnWidgetPage;
    private AssignToAgentPage assignToAgentPage;
    private AuthTabPage authTabPage;
    private CRBTWidgetPage crbtWidgetPage;
    private CurrentBalanceWidgetPage currentBalanceWidgetPage;
    private DemoGraphicPage demoGraphicPage;
    private DADetailsPage daDetailsPage;
    private DetailedUsageHistoryPage detailedUsageHistoryPage;
    private FilterTabPage filterTabPage;
    private FrontendTicketHistoryPage frontendTicketHistoryPage;
    private InteractionsPage interactionsPage;
    private LoanDetailPage loanDetailPage;
    private LoanWidgetPage loanWidget;
    private MessageHistoryPage messageHistoryPage;
    private MoreAMTxnTabPage moreAMTxnTabPage;
    private MoreRechargeHistoryPage moreRechargeHistoryPage;
    private MoreUsageHistoryPage moreUsageHistoryPage;
    private ProfileManagementPage profileManagement;
    private RechargeHistoryWidgetPage rechargeHistoryWidget;
    private SendSMSPage sendSMS;
    private ServiceClassWidgetPage serviceClassWidget;
    private SideMenuPage sideMenu;
    private SupervisorTicketListPage supervisorTicketList;
    private TemplateManagementPage templateManagement;
    private TicketBulkUpdatePage ticketBulkUpdate;
    private TransferToQueuePage transferToQueue;
    private UsageHistoryWidgetPage usageHistoryWidget;
    private ViewCreatedTemplatePage viewCreatedTemplate;
    private ViewTicketPage viewTicket;
    private VoucherTabPage voucherTab;
    private WidgetInteractionPage widgetInteraction;

    /**
     * Instantiates a new page collection.
     *
     * @param driver the driver
     */
    public PageCollection(WebDriver driver) {
        this.driver = driver;

    }

    public SideMenuPage getSideMenuPOM() {
        return (sideMenuPOM == null) ? new SideMenuPage(driver) : sideMenuPOM;
    }

    public LoginPage getLoginPage() {
        return (loginPage == null) ? new LoginPage(driver) : loginPage;
    }

    public CustomerProfilePage getCustomerProfilePage() {
        return (customerProfilePage == null) ? new CustomerProfilePage(driver) : customerProfilePage;
    }

    public TariffPlanPage getTariffPlanPage() {
        return (tariffPlanPage == null) ? new TariffPlanPage(driver) : tariffPlanPage;
    }

    public MsisdnSearchPage getMsisdnSearchPage() {
        return (msisdnSearchPage == null) ? new MsisdnSearchPage(driver) : msisdnSearchPage;
    }

    public ViewHistoryPage getViewHistoryPOM() {
        return (viewHistory == null) ? new ViewHistoryPage(driver) : viewHistory;
    }

    public UserManagementPage getUserManagementPage() {
        return (userManagementPage == null) ? new UserManagementPage(driver) : userManagementPage;
    }

    public ActionTrailPage getActionTrailPage() {
        return actionTrailPage == null ? new ActionTrailPage(driver) : actionTrailPage;
    }

    public AgentLoginPage getAgentLoginPage() {
        return agentLoginPage == null ? new AgentLoginPage(driver) : agentLoginPage;
    }

    public AMTransactionsWidgetPage getAmTxnWidgetPage() {
        return amTxnWidgetPage == null ? new AMTransactionsWidgetPage(driver) : amTxnWidgetPage;
    }

    public AssignToAgentPage getAssignToAgentPage() {
        return assignToAgentPage == null ? new AssignToAgentPage(driver) : assignToAgentPage;
    }

    public AuthTabPage getAuthTabPage() {
        return authTabPage == null ? new AuthTabPage(driver) : authTabPage;
    }

    public CRBTWidgetPage getCrbtWidgetPage() {
        return crbtWidgetPage == null ? new CRBTWidgetPage(driver) : crbtWidgetPage;
    }

    public CurrentBalanceWidgetPage getCurrentBalanceWidgetPage() {
        return currentBalanceWidgetPage == null ? new CurrentBalanceWidgetPage(driver) : currentBalanceWidgetPage;
    }

    public DemoGraphicPage getDemoGraphicPage() {
        return demoGraphicPage == null ? new DemoGraphicPage(driver) : demoGraphicPage;
    }

    public DADetailsPage getDaDetailsPage() {
        return daDetailsPage == null ? new DADetailsPage(driver) : daDetailsPage;
    }

    public DetailedUsageHistoryPage getDetailedUsageHistoryPage() {
        return detailedUsageHistoryPage == null ? new DetailedUsageHistoryPage(driver) : detailedUsageHistoryPage;
    }

    public FilterTabPage getFilterTabPage() {
        return filterTabPage == null ? new FilterTabPage(driver) : filterTabPage;
    }

    public FrontendTicketHistoryPage getFrontendTicketHistoryPage() {
        return frontendTicketHistoryPage == null ? new FrontendTicketHistoryPage(driver) : frontendTicketHistoryPage;
    }

    public InteractionsPage getInteractionsPage() {
        return interactionsPage == null ? new InteractionsPage(driver) : interactionsPage;
    }

    public LoanDetailPage getLoanDetailPage() {
        return loanDetailPage == null ? new LoanDetailPage(driver) : loanDetailPage;
    }

    public LoanWidgetPage getLoanWidget() {
        return loanWidget == null ? new LoanWidgetPage(driver) : loanWidget;
    }

    public MessageHistoryPage getMessageHistoryPage() {
        return messageHistoryPage == null ? new MessageHistoryPage(driver) : messageHistoryPage;
    }

    public MoreAMTxnTabPage getMoreAMTxnTabPage() {
        return moreAMTxnTabPage == null ? new MoreAMTxnTabPage(driver) : moreAMTxnTabPage;
    }

    public MoreRechargeHistoryPage getMoreRechargeHistoryPage() {
        return moreRechargeHistoryPage == null ? new MoreRechargeHistoryPage(driver) : moreRechargeHistoryPage;
    }

    public MoreUsageHistoryPage getMoreUsageHistoryPage() {
        return moreUsageHistoryPage == null ? new MoreUsageHistoryPage(driver) : moreUsageHistoryPage;
    }

    public ProfileManagementPage getProfileManagement() {
        return profileManagement == null ? new ProfileManagementPage(driver) : profileManagement;
    }

    public RechargeHistoryWidgetPage getRechargeHistoryWidget() {
        return rechargeHistoryWidget == null ? new RechargeHistoryWidgetPage(driver) : rechargeHistoryWidget;
    }

    public SendSMSPage getSendSMS() {
        return sendSMS == null ? new SendSMSPage(driver) : sendSMS;
    }

    public ServiceClassWidgetPage getServiceClassWidget() {
        return serviceClassWidget == null ? new ServiceClassWidgetPage(driver) : serviceClassWidget;
    }

    public SideMenuPage getSideMenu() {
        return sideMenu == null ? new SideMenuPage(driver) : sideMenu;
    }

    public SupervisorTicketListPage getSupervisorTicketList() {
        return supervisorTicketList == null ? new SupervisorTicketListPage(driver) : supervisorTicketList;
    }

    public TemplateManagementPage getTemplateManagement() {
        return templateManagement == null ? new TemplateManagementPage(driver) : templateManagement;
    }

    public TicketBulkUpdatePage getTicketBulkUpdate() {
        return ticketBulkUpdate == null ? new TicketBulkUpdatePage(driver) : ticketBulkUpdate;
    }

    public TransferToQueuePage getTransferToQueue() {
        return transferToQueue == null ? new TransferToQueuePage(driver) : transferToQueue;
    }

    public UsageHistoryWidgetPage getUsageHistoryWidget() {
        return usageHistoryWidget == null ? new UsageHistoryWidgetPage(driver) : usageHistoryWidget;
    }

    public ViewCreatedTemplatePage getViewCreatedTemplate() {
        return viewCreatedTemplate == null ? new ViewCreatedTemplatePage(driver) : viewCreatedTemplate;
    }

    public ViewHistoryPage getViewHistory() {
        return viewHistory == null ? new ViewHistoryPage(driver) : viewHistory;
    }

    public ViewTicketPage getViewTicket() {
        return viewTicket == null ? new ViewTicketPage(driver) : viewTicket;
    }

    public VoucherTabPage getVoucherTab() {
        return voucherTab == null ? new VoucherTabPage(driver) : voucherTab;
    }

    public WidgetInteractionPage getWidgetInteraction() {
        return widgetInteraction == null ? new WidgetInteractionPage(driver) : widgetInteraction;
    }
}