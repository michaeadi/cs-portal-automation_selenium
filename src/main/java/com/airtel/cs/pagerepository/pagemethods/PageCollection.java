package com.airtel.cs.pagerepository.pagemethods;

import org.openqa.selenium.WebDriver;

public class PageCollection {


    /**
     * The driver.
     */
    private final WebDriver driver;

    /**
     * Instantiates a new page collection.
     *
     * @param driver the driver
     */
    public PageCollection(WebDriver driver) {
        this.driver = driver;
    }

    public SideMenu getSideMenuPage() {
        return new SideMenu(driver);
    }

    public Login getLoginPage() {
        return new Login(driver);
    }

    public CustomerProfile getCustomerProfilePage() {
        return new CustomerProfile(driver);
    }

    public HbbProfile getHbbProfilePage() {
        return new HbbProfile(driver);
    }

    public TariffPlan getTariffPlanPage() {
        return new TariffPlan(driver);
    }

    public MsisdnSearch getMsisdnSearchPage() {
        return new MsisdnSearch(driver);
    }

    public ViewHistory getViewHistoryPOM() {
        return new ViewHistory(driver);
    }

    public UserManagement getUserManagementPage() {
        return new UserManagement(driver);
    }

    public ActionTrail getActionTrailPage() {
        return new ActionTrail(driver);
    }

    public AgentLogin getAgentLoginPage() {
        return new AgentLogin(driver);
    }

    public AMTransactionsWidget getAmTxnWidgetPage() {
        return new AMTransactionsWidget(driver);
    }

    public AssignToAgent getAssignToAgentPage() {
        return new AssignToAgent(driver);
    }

    public AuthTab getAuthTabPage() {
        return new AuthTab(driver);
    }

    public CRBTWidget getCrbtWidgetPage() {
        return new CRBTWidget(driver);
    }

    public CurrentBalanceWidget getCurrentBalanceWidgetPage() {
        return new CurrentBalanceWidget(driver);
    }

    public DemoGraphic getDemoGraphicPage() {
        return new DemoGraphic(driver);
    }

    public DADetails getDaDetailsPage() {
        return new DADetails(driver);
    }


    public DetailedUsageHistory getDetailedUsageHistoryPage() {
        return new DetailedUsageHistory(driver);
    }

    public FilterTab getFilterTabPage() {
        return new FilterTab(driver);
    }

    public FrontendTicketHistory getFrontendTicketHistoryPage() {
        return new FrontendTicketHistory(driver);
    }

    public Interactions getInteractionsPage() {
        return new Interactions(driver);
    }

    public LoanDetail getLoanDetailPage() {
        return new LoanDetail(driver);
    }

    public LoanWidget getLoanWidget() {
        return new LoanWidget(driver);
    }

    public MessageHistory getMessageHistoryPage() {
        return new MessageHistory(driver);
    }

    public MoreAMTxnTab getMoreAMTxnTabPage() {
        return new MoreAMTxnTab(driver);
    }

    public MoreRechargeHistory getMoreRechargeHistoryPage() {
        return new MoreRechargeHistory(driver);
    }

    public ProfileManagement getProfileManagement() {
        return new ProfileManagement(driver);
    }

    public RechargeHistoryWidget getRechargeHistoryWidget() {
        return new RechargeHistoryWidget(driver);
    }

    public SendSMS getSendSMS() {
        return new SendSMS(driver);
    }

    public ServiceClassWidget getServiceClassWidget() {
        return new ServiceClassWidget(driver);
    }

    public SupervisorTicketList getSupervisorTicketList() {
        return new SupervisorTicketList(driver);
    }

    public TemplateManagement getTemplateManagement() {
        return new TemplateManagement(driver);
    }

    public TicketBulkUpdate getTicketBulkUpdate() {
        return new TicketBulkUpdate(driver);
    }

    public TransferToQueue getTransferToQueue() {
        return new TransferToQueue(driver);
    }

    public UsageHistoryWidget getUsageHistoryWidget() {
        return new UsageHistoryWidget(driver);
    }

    public ViewCreatedTemplate getViewCreatedTemplate() {
        return new ViewCreatedTemplate(driver);
    }

    public ViewHistory getViewHistory() {
        return new ViewHistory(driver);
    }

    public ViewTicket getViewTicket() {
        return new ViewTicket(driver);
    }

    public OscRecharge getOscRecharge() {
        return new OscRecharge(driver);
    }

    public WidgetInteraction getWidgetInteraction() {
        return new WidgetInteraction(driver);
    }

    public Growl getGrowl() {
        return new Growl(driver);
    }

    public WidgetCommonMethod getWidgetCommonMethod() {
        return new WidgetCommonMethod(driver);
    }

    public AdjustmentWidget getAdjustmentTabPage() {
        return new AdjustmentWidget(driver);
    }

    public AdjustmentHistory getAdjustmentHistoryPage() {
        return new AdjustmentHistory(driver);
    }

    public AccountInformationWidget getAccountInformationWidget() {
        return new AccountInformationWidget(driver);
    }

    public PlanAndPackDetailedWidget getPlanAndPackDetailedWidget() {
        return new PlanAndPackDetailedWidget(driver);
    }

    public CurrentPlanWidget getCurrentPlanWidget() {
        return new CurrentPlanWidget(driver);
    }

    public DetailAccountInfoWidget getDetailAccountInfoWidget() {
        return new DetailAccountInfoWidget(driver);
    }

    public ActiveVasWidget getActiveVasWidgetPage() {
        return new ActiveVasWidget(driver);
    }

    public ActiveVasWidget getActiveVasWidget() {
        return new ActiveVasWidget(driver);
    }

    public DetailAccountInfoViewBillWidget getDetailAccountInfoViewBillWidget() {
        return new DetailAccountInfoViewBillWidget(driver);
    }

    public LinkedMsisdnToAccountNoWidget getLinkedMsisdnToAccountNoWidget() {
        return new LinkedMsisdnToAccountNoWidget(driver);
    }

    public BasePage getBasePage() {
        return new BasePage(driver);
    }

    public DetailAccountSendBill getDetailAccountSendBill() {
        return new DetailAccountSendBill(driver);
    }

    public DetailAccountPromiseToPay getDetailAccountPromiseToPay() {
        return new DetailAccountPromiseToPay(driver);
    }

    public AmTcpLimits getAmTcpLimits() {
        return new AmTcpLimits(driver);
    }


    public AmProfile getAmProfile() {
        return new AmProfile(driver);
    }


    public ServiceProfileDetailWidget getServiceProfileDetailWidget() { return new ServiceProfileDetailWidget (driver);
    }
    public AmSmsTrails getAmSmsTrails() {
        return new AmSmsTrails(driver);
    }

    public AmLinkedWallets getAmLinkedWallets() {

        return new AmLinkedWallets(driver);
    }

    public PsbDemographicWidget getPsbDemographicWidget() {
        return new PsbDemographicWidget(driver);
    }
    public WalletInformation getWalletInformation() {
        return new WalletInformation(driver);
    }
    public AccountInformation getAccountInformation() {
        return new AccountInformation(driver);
    }

    public BankAccount getBankAccount() {
        return new BankAccount(driver);
    }

    public SmartCashTransactionHistory getSmartCashTransactionHistory() {
        return new SmartCashTransactionHistory(driver);
    }

    public PinReset getPinReset() {
        return new PinReset(driver);
    }

    public BarUnbar getBarUnbar() {
        return new BarUnbar(driver);
    }
    public KpiDashboardTopPanel getKpiDashboardTopPanel() {
        return new KpiDashboardTopPanel(driver);
    }

    public KpiDashboard6MonthsOverview getKpiDashboard6MonthsOverview() {
        return new KpiDashboard6MonthsOverview(driver);
    }

    public KpiDashboardOpenTicketAgeing getKpiDashboardOpenTicketAgeing() {
        return
                new KpiDashboardOpenTicketAgeing(driver);
    }

    public KpiDashboard6MonthsTicketType getKpiDashboard6MonthsTicketType() {
        return new KpiDashboard6MonthsTicketType(driver);
    }

    public KpiDashboardFeatureUsageOfActions getKpiDashboardFeatureUsageOfActions() {
        return new KpiDashboardFeatureUsageOfActions(driver);
    }

    public KpiDashbaordQueueWiseSLAPerformance getKpiDashboardQueueWiseSLAPerformance() {
        return new KpiDashbaordQueueWiseSLAPerformance(driver);
    }

    public IntermediateScreen getCustomerInteractionScreen() {
        return new IntermediateScreen(driver);
    }

    public DashboardWidgetDropdown getDashboardWidgetDropdown() {
        return new DashboardWidgetDropdown(driver);
    }

    public AmBarUnbar getAmBarUnbar() {
        return new AmBarUnbar(driver);
    }

    public AmReversal getAmReversal() {
        return new AmReversal(driver);
    }

    public AmLinkedBankAccounts getAmLinkedBankAccounts() {
        return new AmLinkedBankAccounts(driver);
    }
}