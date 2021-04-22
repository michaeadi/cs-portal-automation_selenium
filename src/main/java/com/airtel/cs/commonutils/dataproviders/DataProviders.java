package com.airtel.cs.commonutils.dataproviders;

import com.airtel.cs.commonutils.excelutils.WriteTicket;
import com.airtel.cs.driver.Driver;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class DataProviders extends Driver {

    public static Properties config = Driver.config;
    public static List<String> ticketNumbers = new ArrayList<>();
    private static final String TICKET_STATE = "ticketState";
    private static final String CLOSE_STATE = "closeState";
    private static final String USER_MANAGEMENT_SHEET = "UserManagementSheet";
    private static final String FTR_SHEET = "-FtrSheet";
    private static final String NFTR_SHEET = "-NftrSheet";
    private static final String LOGIN_SHEET = "LoginSheet";
    private static final String TEMPLATE_MANAGEMENT = "TemplateManagement";
    private static final String PINNED_TAG_SHEET = "PinnedTagSheet";
    private static final String ACTION_TAGGED = "actionTagged";
    private static final String STATE_QUEUE = "stateQueue";

    //helper
    public static Map<String, String> getWidgetTaggedIssue() {
        FtrDataExcelToBeanDao credsExcelToBeanDao = new FtrDataExcelToBeanDao();
        List<FtrDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(SUITE_TYPE + FTR_SHEET));
        Map<String, String> finalList = new HashMap<>();
        for (FtrDataBeans ftr : list) {
            if (ftr.getWidgetName() != null) {
                log.info("Adding in Map<" + ftr.getIssueSubSubType().trim() + "," + ftr.getWidgetName() + ">");
                finalList.put(ftr.getIssueSubSubType().trim(), ftr.getWidgetName().toLowerCase().trim());
            }
        }
        return finalList;
    }

    @DataProvider
    public Object[][] getTestData() {
        TestDataExcelToBeanDao credsExcelToBeanDao = new TestDataExcelToBeanDao();
        List<TestDatabean> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(LOGIN_SHEET));
        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }

    @DataProvider
    public Object[][] getTestData1() {
        FtrDataExcelToBeanDao credsExcelToBeanDao = new FtrDataExcelToBeanDao();
        List<FtrDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(SUITE_TYPE + FTR_SHEET));

        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }

    @DataProvider
    public Object[][] getTestData2() {
        NftrDataExcelToBeanDao credsExcelToBeanDao = new NftrDataExcelToBeanDao();
        List<NftrDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(SUITE_TYPE + NFTR_SHEET));
        List<NftrDataBeans> finalList = new ArrayList<>();
        for (NftrDataBeans l : list) {
            if (l.getIssueCode() != null && !l.getIssueCode().isEmpty()) {
                finalList.add(l);
            }
        }
        Object[][] hashMapObj = new Object[finalList.size()][1];
        for (int i = 0; i < finalList.size(); i++) {
            hashMapObj[i][0] = finalList.get(i);
        }
        return hashMapObj;
    }

    public ArrayList<String> getInteractionChannelData() {
        UMDataExcelToBeanDao credsExcelToBeanDao = new UMDataExcelToBeanDao();
        List<UMDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(USER_MANAGEMENT_SHEET));
        ArrayList<String> finalList = new ArrayList<>();
        for (UMDataBeans l : list) {
            if (l.getInteraction() != null && !l.getInteraction().isEmpty()) {
                finalList.add(l.getInteraction().toLowerCase().trim());
            }
        }
        return finalList;
    }


    public ArrayList<String> getWorkFlowData() {
        UMDataExcelToBeanDao credsExcelToBeanDao = new UMDataExcelToBeanDao();
        List<UMDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(USER_MANAGEMENT_SHEET));
        ArrayList<String> finalList = new ArrayList<>();
        for (UMDataBeans l : list) {
            if (l.getWorkflow() != null && !l.getWorkflow().isEmpty()) {
                finalList.add(l.getWorkflow().toLowerCase().trim());
            }
        }
        return finalList;
    }

    public ArrayList<String> getLoginQueueData() {
        UMDataExcelToBeanDao credsExcelToBeanDao = new UMDataExcelToBeanDao();
        List<UMDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(USER_MANAGEMENT_SHEET));
        ArrayList<String> finalList = new ArrayList<>();
        for (UMDataBeans l : list) {
            if (l.getLoginQueue() != null && !l.getLoginQueue().isEmpty()) {
                finalList.add(l.getLoginQueue().toLowerCase().trim());
            }
        }
        return finalList;
    }

    public ArrayList<String> getRoles() {
        TemplateDataExcelToBeanDao templateExcelToBeanDao = new TemplateDataExcelToBeanDao();
        List<TemplateDataBeans> list =
                templateExcelToBeanDao.getData(excelPath, config.getProperty(TEMPLATE_MANAGEMENT));
        ArrayList<String> finalList = new ArrayList<>();
        for (TemplateDataBeans l : list) {
            if (l.getRoles() != null && !l.getRoles().isEmpty()) {
                finalList.add(l.getRoles().toLowerCase().trim());
            }
        }
        return finalList;
    }

    public ArrayList<String> getLanguage() {
        TemplateDataExcelToBeanDao templateExcelToBeanDao = new TemplateDataExcelToBeanDao();
        List<TemplateDataBeans> list =
                templateExcelToBeanDao.getData(excelPath, config.getProperty(TEMPLATE_MANAGEMENT));
        ArrayList<String> finalList = new ArrayList<>();
        for (TemplateDataBeans l : list) {
            if (l.getLanguage() != null && !l.getLanguage().isEmpty()) {
                finalList.add(l.getLanguage().toLowerCase().trim());
            }
        }
        return finalList;
    }

    @DataProvider(name = "pinTag")
    public Object[][] getPinTags(Method method) {
        PinnedTagDataExcelToBeanDao credsExcelToBeanDao = new PinnedTagDataExcelToBeanDao();
        RowNumber rows = method.getAnnotation(RowNumber.class);
        List<PinnedTagsDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(PINNED_TAG_SHEET));
        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }

    @DataProvider(name = TICKET_STATE)
    public Object[][] ticketStateList() {
        TicketStateToBean ticketStateToBean = new TicketStateToBean();
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(excelPath, config.getProperty(TICKET_STATE));
        List<TicketStateDataBean> closeState = new ArrayList<>();
        List<TicketStateDataBean> openState = new ArrayList<>();
        for (TicketStateDataBean state : list) {
            if (state.getInternalState().equals(config.getProperty(CLOSE_STATE))) {
                closeState.add(state);
            } else {
                openState.add(state);
            }
        }
        Object[][] hashMapObj = new Object[1][1];

        hashMapObj[0][0] = closeState.get(0);

        return hashMapObj;
    }

    public String ticketStateClosed() {
        TicketStateToBean ticketStateToBean = new TicketStateToBean();
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(excelPath, config.getProperty(TICKET_STATE));
        List<TicketStateDataBean> closeState = new ArrayList<>();
        List<TicketStateDataBean> openState = new ArrayList<>();
        for (TicketStateDataBean state : list) {
            if (state.getInternalState().equals(config.getProperty(CLOSE_STATE))) {
                closeState.add(state);
            } else {
                openState.add(state);
            }
        }
        return closeState.get(0).getTicketStateName();
    }

    @DataProvider(name = "ticketId")
    public Object[][] getTestData5() {
        NftrDataExcelToBeanDao credsExcelToBeanDao = new NftrDataExcelToBeanDao();
        List<NftrDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(SUITE_TYPE + NFTR_SHEET));
        List<NftrDataBeans> finalTicketList = new ArrayList<>();
        for (NftrDataBeans nftrTicket : list) {
            if (nftrTicket.getTicketNumber() != null) {
                if (!nftrTicket.getTicketNumber().isEmpty()) {
                    log.info("Ticket Id: " + nftrTicket.getTicketNumber());
                    finalTicketList.add(nftrTicket);
                }
            } else {
                log.info("No Ticket Found");
            }
        }

        Object[][] hashMapObj = new Object[finalTicketList.size()][1];
        for (int i = 0; i < finalTicketList.size(); i++) {
            hashMapObj[i][0] = finalTicketList.get(i);
        }
        return hashMapObj;
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData(Method method) {
        TestDataExcelToBeanDao credsExcelToBeanDao = new TestDataExcelToBeanDao();
        User rows = method.getAnnotation(User.class);
        List<TestDatabean> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(LOGIN_SHEET));
        List<TestDatabean> finalTicketList = new ArrayList<>();
        for (TestDatabean login : list) {
            if (login.getUserType().toLowerCase().trim().equals(rows.userType().toLowerCase().trim())) {
                finalTicketList.add(login);
                log.info("User Role Found For Login " + login.getUserType());
            }
        }
        Object[][] hashMapObj = new Object[finalTicketList.size()][1];
        for (int i = 0; i < finalTicketList.size(); i++) {
            hashMapObj[i][0] = finalTicketList.get(i);
        }
        return hashMapObj;
    }


    @DataProvider(name = "HeaderData")
    public Object[][] getHeaderData(Method method) {
        HeaderDataExcelToBeanDao excelToBeanDao = new HeaderDataExcelToBeanDao();
        Table table = method.getAnnotation(Table.class);
        List<HeaderDataBean> list =
                excelToBeanDao.getData(excelPath, config.getProperty("HeaderSheet"));
        List<HeaderDataBean> finalTicketList = new ArrayList<>();
        for (HeaderDataBean login : list) {
            if (login.getTableName().toLowerCase().trim().equals(table.name().toLowerCase().trim())) {
                finalTicketList.add(login);
            }
        }
        Object[][] hashMapObj = new Object[finalTicketList.size()][1];
        for (int i = 0; i < finalTicketList.size(); i++) {
            hashMapObj[i][0] = finalTicketList.get(i);
        }
        return hashMapObj;
    }

    @DataProvider(name = "ReOpenState")
    public Object[][] isReOpenState() {
        TicketStateToBean ticketStateToBean = new TicketStateToBean();
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(excelPath, config.getProperty(TICKET_STATE));
        List<TicketStateDataBean> reOpen = new ArrayList<>();
        for (TicketStateDataBean state : list) {
            log.info(state.getIsReopenState());
            if (state.getIsReopenState() == null) {
                log.info("SKIP");
            } else if (state.getIsReopenState().equalsIgnoreCase("true")) {
                reOpen.add(state);
            }
        }
        Object[][] hashMapObj = new Object[1][1];

        hashMapObj[0][0] = reOpen.get(0);

        return hashMapObj;
    }

    @DataProvider
    public Object[][] interactionComment() {
        NftrDataExcelToBeanDao credsExcelToBeanDao = new NftrDataExcelToBeanDao();
        List<NftrDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(SUITE_TYPE + NFTR_SHEET));

        Object[][] hashMapObj = new Object[1][1];

        hashMapObj[0][0] = list.get(0);

        return hashMapObj;
    }

    //helper method
    public List<TicketStateDataBean> getState(String stateName) {
        TicketStateToBean ticketStateToBean = new TicketStateToBean();
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(excelPath, config.getProperty(TICKET_STATE));
        List<TicketStateDataBean> closeState = new ArrayList<>();
        List<TicketStateDataBean> openState = new ArrayList<>();
        for (TicketStateDataBean state : list) {
            if (state.getInternalState().equals(config.getProperty(CLOSE_STATE))) {
                closeState.add(state);
            } else {
                openState.add(state);
            }
        }
        if (stateName.equalsIgnoreCase("open")) {
            return openState;
        } else {
            return closeState;
        }
    }

    //helper method
    public List<PriorityDataBean> getPriority() {
        PriorityDataExcelToBeanDao priorityDataBean = new PriorityDataExcelToBeanDao();
        return priorityDataBean.getData(excelPath, config.getProperty("priority"));
    }

    //helper method
    public Map<String, Boolean> getALLPinnedTags() {
        PinnedTagDataExcelToBeanDao pinnedTag = new PinnedTagDataExcelToBeanDao();
        List<PinnedTagsDataBeans> list =
                pinnedTag.getData(excelPath, config.getProperty(PINNED_TAG_SHEET));
        Map<String, Boolean> finalList = new HashMap<>();
        for (PinnedTagsDataBeans l : list) {
            finalList.put(l.getTagName().toLowerCase().trim(), false);
        }
        return finalList;
    }

    @DataProvider(name = "singleTicketId")
    public Object[][] getSingleTicketId() {
        NftrDataExcelToBeanDao credsExcelToBeanDao = new NftrDataExcelToBeanDao();
        List<NftrDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(SUITE_TYPE + NFTR_SHEET));
        List<NftrDataBeans> finalTicketList = new ArrayList<>();
        for (NftrDataBeans nftrTicket : list) {
            log.info("Ticket Id: " + nftrTicket.getTicketNumber());
            if (nftrTicket.getTicketNumber() == null) {
                log.info("No Ticket ID Found");
            } else {
                finalTicketList.add(nftrTicket);
            }
        }

        Object[][] hashMapObj = new Object[1][1];

        hashMapObj[0][0] = finalTicketList.get(0);

        return hashMapObj;
    }


    //helper
    public Map<String, String> getListOfIssue(String widgetName) {
        Map<String, String> list = getWidgetTaggedIssue();
        Map<String, String> finalList = new HashMap<>();
        for (Map.Entry<String, String> mapElement : list.entrySet()) {
            if (mapElement.getValue().equalsIgnoreCase(widgetName)) {
                finalList.put(mapElement.getKey(), mapElement.getValue());
            }
        }
        return finalList;
    }

    public String getCode(String text) {
        FtrDataExcelToBeanDao credsExcelToBeanDao = new FtrDataExcelToBeanDao();
        List<FtrDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(SUITE_TYPE + FTR_SHEET));
        for (FtrDataBeans ftrDataBeans : list) {
            if (ftrDataBeans.getIssueSubSubType().equalsIgnoreCase(text)) {
                log.info("Found Single Row: " + ftrDataBeans.getIssueSubSubType());
                return ftrDataBeans.getIssueCode();
            }
        }
        return "not found";
    }

    @DataProvider(name = "ticketTransferRule")
    public Object[][] ticketTransferRule() {
        TicketTransferRuleDateToExcel credsExcelToBeanDao = new TicketTransferRuleDateToExcel();
        List<TicketTransferRuleDataBean> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty("ticketTransferRule"));
        List<TicketTransferRuleDataBean> finalList = new ArrayList<>();
        for (TicketTransferRuleDataBean l : list) {
            if (l.getIssueCode() != null && !l.getIssueCode().isEmpty()) {
                finalList.add(l);
            }
        }
        Object[][] hashMapObj = new Object[finalList.size()][1];
        for (int i = 0; i < finalList.size(); i++) {
            hashMapObj[i][0] = finalList.get(i);
        }
        return hashMapObj;
    }

    //Get Auth Policy
    public List<AuthTabDataBeans> getPolicy() {
        AuthTabBeanToExcel authTabBeanToExcel = new AuthTabBeanToExcel();
        return authTabBeanToExcel.getData(excelPath, config.getProperty("authPolicy"));
    }

    public List<String> getPolicyQuestion() {
        AuthTabBeanToExcel authTabBeanToExcel = new AuthTabBeanToExcel();
        List<AuthTabDataBeans> list =
                authTabBeanToExcel.getData(excelPath, config.getProperty("authPolicy"));
        List<String> question = new ArrayList<>();
        question.add(list.get(0).getQ1());
        question.add(list.get(0).getQ2());
        question.add(list.get(0).getQ3());
        question.add(list.get(0).getQ4());
        question.add(list.get(0).getQ5());
        question.add(list.get(0).getQ6());
        question.add(list.get(0).getQ7());
        question.add(list.get(0).getQ8());
        question.add(list.get(0).getQ9());
        question.add(list.get(0).getQ10());

        while (question.contains(null)) {
            question.remove(null);
        }
        while (question.contains("")) {
            question.remove("");
        }

        return question;
    }

    public List<QuestionAnswerKeyDataBeans> getQuestionAnswerKey() {
        QuestionAnswerKeyToExcel authTabBeanToExcel = new QuestionAnswerKeyToExcel();
        return authTabBeanToExcel.getData(excelPath, config.getProperty("questionAnswerKey"));
    }

    //Get Action Tagging
    public List<ActionTagDataBeans> getActionTag() {
        ActionTagBeanToExcel actionTagDataBeans = new ActionTagBeanToExcel();
        return actionTagDataBeans.getData(excelPath, config.getProperty(ACTION_TAGGED));
    }

    //Get Voucher Id
    public String getVoucherId() {
        ActionTagBeanToExcel actionTagDataBeans = new ActionTagBeanToExcel();
        List<ActionTagDataBeans> list =
                actionTagDataBeans.getData(excelPath, config.getProperty(ACTION_TAGGED));
        for (ActionTagDataBeans s : list) {
            if (s.getActionTagName().equalsIgnoreCase("Voucher Id")) {
                if (evnName.equalsIgnoreCase("prod")) {
                    return s.getTestDataProd();
                } else {
                    return s.getTestDataUAT();
                }
            }
        }
        return null;
    }

    public List<String> issueDetailReason(String actionTagName) {
        ActionTagBeanToExcel actionTagDataBeans = new ActionTagBeanToExcel();
        List<ActionTagDataBeans> list =
                actionTagDataBeans.getData(excelPath, config.getProperty(ACTION_TAGGED));
        List<String> reasons = new ArrayList<>();
        for (ActionTagDataBeans s : list) {
            if (s.getActionTagName().trim().equalsIgnoreCase(actionTagName)) {
                reasons.add(s.getOption1());
                reasons.add(s.getOption2());
                reasons.add(s.getOption3());
                reasons.add(s.getOption4());
                reasons.add(s.getOption5());
            }
        }
        while (reasons.contains(null)) {
            reasons.remove(null);
        }

        while (reasons.contains("")) {
            reasons.remove("");
        }
        return reasons;
    }

    @DataProvider(name = "queueState")
    public Object[][] getQueueState() {
        QueueStateBeanToExcel queueStateBeanToExcel = new QueueStateBeanToExcel();
        List<QueueStateDataBeans> list =
                queueStateBeanToExcel.getData(excelPath, config.getProperty(STATE_QUEUE));
        List<QueueStateDataBeans> finalTicketList = new ArrayList<>();
        for (QueueStateDataBeans states : list) {
            assert states.getQueue() != null;
            if (states.getQueue() != null && !states.getQueue().isEmpty()) {
                finalTicketList.add(states);
            }
        }
        Object[][] hashMapObj = new Object[finalTicketList.size()][1];
        for (int i = 0; i < finalTicketList.size(); i++) {
            hashMapObj[i][0] = finalTicketList.get(i);
        }
        return hashMapObj;
    }

    public List<String> getQueueState(String queue) {
        QueueStateBeanToExcel queueStateBeanToExcel = new QueueStateBeanToExcel();
        List<QueueStateDataBeans> list =
                queueStateBeanToExcel.getData(excelPath, config.getProperty(STATE_QUEUE));
        List<String> allStates = new ArrayList<>();
        for (QueueStateDataBeans states : list) {
            if (states.getQueue().equalsIgnoreCase(queue)) {
                if (isNull(states.getState1())) {
                    allStates.add(states.getState1().toLowerCase().trim());
                }
                if (isNull(states.getState2())) {
                    allStates.add(states.getState2().toLowerCase().trim());
                }
                if (isNull(states.getState3())) {
                    allStates.add(states.getState3().toLowerCase().trim());
                }
                if (isNull(states.getState4())) {
                    allStates.add(states.getState4().toLowerCase().trim());
                }
                if (isNull(states.getState5())) {
                    allStates.add(states.getState5().toLowerCase().trim());
                }
                if (isNull(states.getState6())) {
                    allStates.add(states.getState6().toLowerCase().trim());
                }
                if (isNull(states.getState7())) {
                    allStates.add(states.getState7().toLowerCase().trim());
                }
                if (isNull(states.getState8())) {
                    allStates.add(states.getState8().toLowerCase().trim());
                }
                if (isNull(states.getState9())) {
                    allStates.add(states.getState9().toLowerCase().trim());
                }
                if (isNull(states.getState10())) {
                    allStates.add(states.getState10().toLowerCase().trim());
                }
            }
        }
        return allStates;
    }

    public boolean isNull(String text) {
        return text != null && !text.isEmpty();
    }

    //Get ticket layout using issue code
    public List<String> getTicketLayout(String code) {
        NftrDataExcelToBeanDao credsExcelToBeanDao = new NftrDataExcelToBeanDao();
        List<NftrDataBeans> list =
                credsExcelToBeanDao.getData(excelPath, config.getProperty(SUITE_TYPE + NFTR_SHEET));
        List<String> finalTicketList = new ArrayList<>();
        for (NftrDataBeans nftrTicket : list) {
            if (nftrTicket.getIssueCode().equalsIgnoreCase(code)) {
                if (isNull(nftrTicket.getTicketFieldLabel1())) {
                    finalTicketList.add(nftrTicket.getTicketFieldLabel1().toLowerCase().trim());
                }
                if (isNull(nftrTicket.getTicketFieldLabel2())) {
                    finalTicketList.add(nftrTicket.getTicketFieldLabel2().toLowerCase().trim());
                }
                if (isNull(nftrTicket.getTicketFieldLabel3())) {
                    finalTicketList.add(nftrTicket.getTicketFieldLabel3().toLowerCase().trim());
                }
                if (isNull(nftrTicket.getTicketFieldLabel4())) {
                    finalTicketList.add(nftrTicket.getTicketFieldLabel4().toLowerCase().trim());
                }
                if (isNull(nftrTicket.getTicketFieldLabel5())) {
                    finalTicketList.add(nftrTicket.getTicketFieldLabel5().toLowerCase().trim());
                }
                if (isNull(nftrTicket.getTicketFieldLabel6())) {
                    finalTicketList.add(nftrTicket.getTicketFieldLabel6().toLowerCase().trim());
                }
                if (isNull(nftrTicket.getTicketFieldLabel7())) {
                    finalTicketList.add(nftrTicket.getTicketFieldLabel7().toLowerCase().trim());
                }
            }
        }
        return finalTicketList;
    }

    public boolean writeTicketNumberToExcel() throws IOException {
        WriteTicket objExcelFile = new WriteTicket();
        File exceldir = new File("excels");
        File excel = new File(exceldir, config.getProperty("ticketBulkUpdate"));
        Object[][] list = getTestData5();
        boolean flag = false;
        int size = list.length;
        if (size >= 5) {
            size = 5;
        }
        for (int i = 0; i < size; i++) {
            NftrDataBeans n = (NftrDataBeans) list[i][0];
            log.info("No:" + n.getTicketNumber());
            String[] valueToWrite = new String[]{n.getTicketNumber()};
            objExcelFile.writeTicketNumber(excel.getAbsolutePath(), "Sheet1", valueToWrite, i + 1);
            flag = true;
            ticketNumbers.add(n.getTicketNumber());
        }
        log.info("Flag" + flag + ticketNumbers.size());
        return flag;
    }

    public List<String> getTicketNumbers() {
        return ticketNumbers;
    }

    @DataProvider(name = "TransferQueue")
    public Object[][] getTransferToQueueData() {
        TransferQueueDataToExcel transferQueue = new TransferQueueDataToExcel();
        List<TransferQueueDataBean> list =
                transferQueue.getData(excelPath, config.getProperty("transferToQueue"));
        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Table {
        String name() default "";
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface RowNumber {
        String rowNumber() default "";
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface User {
        String userType() default "ALL";
    }

    /**
     * This Method is used to get the data from excel sheet
     *
     * @param recordSet          recordSet
     * @param rowKeyword         rowKeyword
     * @param searchInColumn     searchInColumn
     * @param requiredColumnList requiredColumnList
     * @return requiredDataPointList
     */
    public static List<String> getScenarioDetailsFromExcelSheetColumnWise(Recordset recordSet, String rowKeyword, String searchInColumn, List<String> requiredColumnList) {

        List<String> requiredDataPointList = new ArrayList<>();
        try {
            recordSet.moveFirst();
            while (recordSet.next()) {
                String currentColumnName = recordSet.getField(searchInColumn);
                if (currentColumnName.equalsIgnoreCase(rowKeyword)) {
                    for (String columnName : requiredColumnList) {
                        requiredDataPointList.add(recordSet.getField(columnName));
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        recordSet.close();
        return requiredDataPointList;
    }

    /**
     * This Method is used to read excel sheet
     *
     * @param filePath  filePath
     * @param sheetName sheetName
     * @return recordSet
     */
    public static Recordset readExcelSheet(String filePath, String sheetName) {
        Recordset recordSet = null;
        Connection connection = null;
        try {
            Fillo fillo = new Fillo();
            connection = fillo.getConnection(filePath);
            String strQuery = "Select * from " + sheetName;
            recordSet = connection.executeQuery(strQuery);

            return recordSet;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return recordSet;
    }

}