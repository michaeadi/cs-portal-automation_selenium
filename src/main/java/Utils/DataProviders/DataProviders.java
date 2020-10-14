package Utils.DataProviders;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.DataProvider;
import tests.BaseTest;

import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.*;

@Log4j2
public class DataProviders {

    public static Properties config = BaseTest.config;

    @DataProvider
    public Object[][] getTestData() {
        TestDataExcelToBeanDao credsExcelToBeanDao = new TestDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<TestDatabean> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("LoginSheet"));
        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }

    @DataProvider
    public Object[][] getTestData1() {
        ftrDataExcelToBeanDao credsExcelToBeanDao = new ftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<ftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "-FtrSheet"));

        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }

    @DataProvider
    public Object[][] getTestData2() {
        nftrDataExcelToBeanDao credsExcelToBeanDao = new nftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<nftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "-NftrSheet"));
        List<nftrDataBeans> finalList=new ArrayList<>();
        for (nftrDataBeans l : list) {
            if (l.getIssueCode()!=null) {
                if(!l.getIssueCode().isEmpty())
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
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<UMDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("UserManagementSheet"));
        ArrayList<String> finalList = new ArrayList<>();
        for (UMDataBeans l : list) {
            if (l.getInteraction()!=null) {
                if(!l.getInteraction().isEmpty())
                finalList.add(l.getInteraction().toLowerCase().trim());
            }
        }
        return finalList;
    }


    public ArrayList<String> getWorkFlowData() {
        UMDataExcelToBeanDao credsExcelToBeanDao = new UMDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<UMDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("UserManagementSheet"));
        ArrayList<String> finalList = new ArrayList<>();
        for (UMDataBeans l : list) {
            if (l.getWorkflow()!=null) {
                if(!l.getWorkflow().isEmpty())
                finalList.add(l.getWorkflow().toLowerCase().trim());
            }
        }
        return finalList;
    }

    public ArrayList<String> getLoginQueueData() {
        UMDataExcelToBeanDao credsExcelToBeanDao = new UMDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<UMDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("UserManagementSheet"));
        ArrayList<String> finalList = new ArrayList<>();
        for (UMDataBeans l : list) {
            if (l.getLoginQueue()!=null) {
                if(!l.getLoginQueue().isEmpty())
                finalList.add(l.getLoginQueue().toLowerCase().trim());
            }
        }
        return finalList;
    }

    public ArrayList<String> getRoles() {
        TemplateDataExcelToBeanDao templateExcelToBeanDao = new TemplateDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<TemplateDataBeans> list =
                templateExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("TemplateManagement"));
        ArrayList<String> finalList = new ArrayList<>();
        for (TemplateDataBeans l : list) {
            if (l.getRoles()!=null) {
                if(!l.getRoles().isEmpty())
                    finalList.add(l.getRoles().toLowerCase().trim());
            }
        }
        return finalList;
    }

    public ArrayList<String> getLanguage() {
        TemplateDataExcelToBeanDao templateExcelToBeanDao = new TemplateDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<TemplateDataBeans> list =
                templateExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("TemplateManagement"));
        ArrayList<String> finalList = new ArrayList<>();
        for (TemplateDataBeans l : list) {
            if (l.getLanguage()!=null) {
                if(!l.getLanguage().isEmpty())
                    finalList.add(l.getLanguage().toLowerCase().trim());
            }
        }
        return finalList;
    }

    @DataProvider(name = "pinTag")
    public Object[][] getPinTags(Method method) {
        PinnedTagDataExcelToBeanDao credsExcelToBeanDao = new PinnedTagDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        RowNumber rows = method.getAnnotation(RowNumber.class);
        List<PinnedtagsDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("PinnedTagSheet"));
        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
        }
        return hashMapObj;
    }

    @DataProvider(name = "ticketState")
    public Object[][] ticketStateList() {
        TicketStateToBean ticketStateToBean = new TicketStateToBean();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(Excel.getAbsolutePath(), config.getProperty("ticketState"));
        List<TicketStateDataBean> closeState = new ArrayList<TicketStateDataBean>();
        List<TicketStateDataBean> openState = new ArrayList<TicketStateDataBean>();
        for (TicketStateDataBean state : list) {
            if (state.getInternalState().equals(config.getProperty("closeState"))) {
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
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(Excel.getAbsolutePath(), config.getProperty("ticketState"));
        List<TicketStateDataBean> closeState = new ArrayList<TicketStateDataBean>();
        List<TicketStateDataBean> openState = new ArrayList<TicketStateDataBean>();
        for (TicketStateDataBean state : list) {
            if (state.getInternalState().equals(config.getProperty("closeState"))) {
                closeState.add(state);
            } else {
                openState.add(state);
            }
        }
       return closeState.get(0).getTicketStateName();
    }

    @DataProvider(name = "ticketId")
    public Object[][] getTestData5() {
        nftrDataExcelToBeanDao credsExcelToBeanDao = new nftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<nftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "-NftrSheet"));
        List<nftrDataBeans> finalTicketList = new ArrayList<nftrDataBeans>();
        for (nftrDataBeans nftrTicket : list) {
            if (nftrTicket.getTicketNumber() != null) {
                if(!nftrTicket.getTicketNumber().isEmpty()){
                    System.out.println("Ticket Id: " + nftrTicket.getTicketNumber());
                finalTicketList.add(nftrTicket);
                }
            } else {
                System.out.println("No Ticket Found");
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
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        User rows = method.getAnnotation(User.class);
        List<TestDatabean> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("LoginSheet"));
        List<TestDatabean> finalTicketList = new ArrayList<>();
        for (TestDatabean login : list) {
            System.out.println("User Type " + login.getUserType());
            if (login.getUserType().toLowerCase().trim().equals(rows.UserType().toLowerCase().trim())) {
                finalTicketList.add(login);
                System.out.println("User Type ADDED " + login.getUserType());

            } else {
                System.out.println("NO USER FOUND");
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
        HeaderDataExcelToBeanDao ExcelToBeanDao = new HeaderDataExcelToBeanDao();
        File ExcelDir = new File("Excels");
        File Excel = new File(ExcelDir, BaseTest.ExcelPath);
        Table table = method.getAnnotation(Table.class);
        List<HeaderDataBean> list =
                ExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("HeaderSheet"));
        List<HeaderDataBean> finalTicketList = new ArrayList<>();
        for (HeaderDataBean login : list) {
            System.out.println("User Type " + login.getTableName());
            if (login.getTableName().toLowerCase().trim().equals(table.Name().toLowerCase().trim())) {
                finalTicketList.add(login);
                System.out.println("User table ADDED " + login.getTableName());

            } else {
                System.out.println("NO Table FOUND");
            }
        }
        Object[][] hashMapObj = new Object[finalTicketList.size()][1];
        for (int i = 0; i < finalTicketList.size(); i++) {
            hashMapObj[i][0] = finalTicketList.get(i);
        }
        return hashMapObj;
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Table {
        String Name() default "";
    }


    @DataProvider(name = "ReOpenState")
    public Object[][] isReOpenState() {
        TicketStateToBean ticketStateToBean = new TicketStateToBean();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(Excel.getAbsolutePath(), config.getProperty("ticketState"));
        List<TicketStateDataBean> reOpen = new ArrayList<TicketStateDataBean>();
        for (TicketStateDataBean state : list) {
            System.out.println(state.getIsReopenState());
            if (state.getIsReopenState() == null) {
                System.out.println("SKIP");
            } else if (state.getIsReopenState().equalsIgnoreCase("true")) {
                reOpen.add(state);
            }
        }
        Object[][] hashMapObj = new Object[1][1];

        hashMapObj[0][0] = reOpen.get(0);

        return hashMapObj;
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
        String UserType() default "ALL";
    }

    @DataProvider
    public Object[][] interactionComment() {
        nftrDataExcelToBeanDao credsExcelToBeanDao = new nftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.Opco + ".xlsx");
        List<nftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "-NftrSheet"));

        Object[][] hashMapObj = new Object[1][1];

        hashMapObj[0][0] = list.get(0);

        return hashMapObj;
    }

    //helper method
    public List<TicketStateDataBean> getState(String stateName) {
        TicketStateToBean ticketStateToBean = new TicketStateToBean();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<TicketStateDataBean> list =
                ticketStateToBean.getData(Excel.getAbsolutePath(), config.getProperty("ticketState"));
        List<TicketStateDataBean> closeState = new ArrayList<TicketStateDataBean>();
        List<TicketStateDataBean> openState = new ArrayList<TicketStateDataBean>();
        for (TicketStateDataBean state : list) {
            if (state.getInternalState().equals(config.getProperty("closeState"))) {
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
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<PriorityDataBean> list =
                priorityDataBean.getData(Excel.getAbsolutePath(), config.getProperty("priority"));
        return list;
    }

    //helper method
    public Map<String, Boolean> getALLPinnedTags() {
        PinnedTagDataExcelToBeanDao pinnedTag = new PinnedTagDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<PinnedtagsDataBeans> list =
                pinnedTag.getData(Excel.getAbsolutePath(), config.getProperty("PinnedTagSheet"));
        Map<String, Boolean> finalList = new HashMap<String, Boolean>();
        for (PinnedtagsDataBeans l : list) {
            finalList.put(l.getTagName().toLowerCase().trim(), false);
        }
        return finalList;
    }

    @DataProvider(name = "singleTicketId")
    public Object[][] getSingleTicketId() {
        nftrDataExcelToBeanDao credsExcelToBeanDao = new nftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<nftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "-NftrSheet"));
        List<nftrDataBeans> finalTicketList = new ArrayList<nftrDataBeans>();
        for (nftrDataBeans nftrTicket : list) {
            System.out.println("Ticket Id: " + nftrTicket.getTicketNumber());
            if (nftrTicket.getTicketNumber() == null) {
                System.out.println("No Ticket ID Found");
            } else {
                finalTicketList.add(nftrTicket);
            }
        }

        Object[][] hashMapObj = new Object[1][1];

        hashMapObj[0][0] = finalTicketList.get(0);

        return hashMapObj;
    }

    //helper
    public static final Map<String, String> getWidgetTaggedIssue() {
        System.out.println("Calling instance");
        ftrDataExcelToBeanDao credsExcelToBeanDao = new ftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<ftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "-FtrSheet"));
        Map<String, String> finalList = new HashMap<>();
        for (ftrDataBeans ftr : list) {
            if (ftr.getWidgetName() != null) {
                System.out.println("Adding in Map<" + ftr.getIssueSubSubType().trim() + "," + ftr.getWidgetName() + ">");
                finalList.put(ftr.getIssueSubSubType().trim(), ftr.getWidgetName().toLowerCase().trim());
            }
        }
        return finalList;
    }

    //helper
    public Map<String, String> getListOfIssue(String widgetName) {
        Map<String, String> list = getWidgetTaggedIssue();
        Map<String, String> finalList = new HashMap<>();
        for (Map.Entry<String,String> mapElement : list.entrySet()) {
            if(mapElement.getValue().equalsIgnoreCase(widgetName)){
                finalList.put(mapElement.getKey(),mapElement.getValue());
            }
        }
        return finalList;
    }


    public String getCode(String text) {
        ftrDataExcelToBeanDao credsExcelToBeanDao = new ftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<ftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "-FtrSheet"));
        for(int i=0;i<list.size();i++) {
            if(list.get(i).getIssueSubSubType().equalsIgnoreCase(text)) {
                System.out.println("Found Single Row: "+list.get(i).getIssueSubSubType());
                return list.get(i).getIssueCode();
            }
        }
        return "not found";
    }

    @DataProvider(name = "ticketTransferRule")
    public Object[][] ticketTransferRule() {
        TicketTransferRuleDateToExcel credsExcelToBeanDao = new TicketTransferRuleDateToExcel();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<TicketTransferRuleDataBean> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("ticketTransferRule"));
        List<TicketTransferRuleDataBean> finalList=new ArrayList<>();
        for (TicketTransferRuleDataBean l : list) {
            if (l.getIssueCode()!=null) {
                if(!l.getIssueCode().isEmpty())
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
    public List<AuthTabDataBeans> getPolicy(){
        AuthTabBeanToExcel authTabBeanToExcel = new AuthTabBeanToExcel();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<AuthTabDataBeans> list =
                authTabBeanToExcel.getData(Excel.getAbsolutePath(), config.getProperty("authPolicy"));
        return list;
    }

    public List<String> getPolicyQuestion(){
        AuthTabBeanToExcel authTabBeanToExcel = new AuthTabBeanToExcel();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<AuthTabDataBeans> list =
                authTabBeanToExcel.getData(Excel.getAbsolutePath(), config.getProperty("authPolicy"));
        List<String> question=new ArrayList<>();
        String q1=list.get(0).getQ1();
        String q2=list.get(0).getQ2();
        String q3=list.get(0).getQ3();
        String q4=list.get(0).getQ4();
        String q5=list.get(0).getQ5();
        String q6=list.get(0).getQ6();
        String q7=list.get(0).getQ7();
        String q8=list.get(0).getQ8();
        String q9=list.get(0).getQ9();
        String q10=list.get(0).getQ10();
        if(q1!=null)
            if(!q1.isEmpty())
                question.add(q1);

        if(q2!=null)
            if(!q2.isEmpty())
                question.add(q2);

        if(q3!=null)
            if(!q3.isEmpty())
                question.add(q3);

        if(q4!=null)
            if(!q4.isEmpty())
                question.add(q4);

        if(q5!=null)
            if(!q5.isEmpty())
                question.add(q5);

        if(q6!=null)
            if(!q6.isEmpty())
                question.add(q6);

        if(q7!=null)
            if(!q7.isEmpty())
                question.add(q7);

        if(q8!=null)
            if(!q8.isEmpty())
                question.add(q8);

        if(q9!=null)
            if(!q9.isEmpty())
                question.add(q9);

        if(q10!=null)
            if(!q10.isEmpty())
                question.add(q10);

        return question;
    }

    //Get Action Tagging
    public List<ActionTagDataBeans> getActionTag(){
        ActionTagBeanToExcel actionTagDataBeans = new ActionTagBeanToExcel();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<ActionTagDataBeans> list =
                actionTagDataBeans.getData(Excel.getAbsolutePath(), config.getProperty("actionTagged"));
        return list;
    }

    public List<String> issueDetailReason(String actionTagName){
        ActionTagBeanToExcel actionTagDataBeans = new ActionTagBeanToExcel();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<ActionTagDataBeans> list =
                actionTagDataBeans.getData(Excel.getAbsolutePath(), config.getProperty("actionTagged"));
        List<String> reasons=new ArrayList<>();
        for(ActionTagDataBeans s:list){
            if(s.getActionTagName().trim().equalsIgnoreCase(actionTagName)){
                String r1=s.getOption1();
                String r2=s.getOption2();
                String r3=s.getOption3();
                String r4=s.getOption4();
                String r5=s.getOption5();

                if(r1!=null)
                    if(r1.isEmpty())
                        reasons.add(r1);
                if(r2!=null)
                    if(r2.isEmpty())
                        reasons.add(r2);
                if(r3!=null)
                    if(r3.isEmpty())
                        reasons.add(r3);
                if(r4!=null)
                    if(r4.isEmpty())
                        reasons.add(r4);
                if(r5!=null)
                    if(r5.isEmpty())
                        reasons.add(r5);
            }
        }
        return reasons;
    }

    @DataProvider(name = "queueState")
    public Object[][] getQueueState() {
        QueueStateBeanToExcel queueStateBeanToExcel = new QueueStateBeanToExcel();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<QueueStateDataBeans> list =
                queueStateBeanToExcel.getData(Excel.getAbsolutePath(), config.getProperty("stateQueue"));
        List<QueueStateDataBeans> finalTicketList = new ArrayList<QueueStateDataBeans>();
        for (QueueStateDataBeans states : list) {
            if (states.getQueue() != null) {
                if(!states.getQueue().isEmpty()){
                    finalTicketList.add(states);
                }
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
        File ExcelDir = new File("Excels");
        File Excel = new File(ExcelDir, BaseTest.ExcelPath);
        List<QueueStateDataBeans> list =
                queueStateBeanToExcel.getData(Excel.getAbsolutePath(), config.getProperty("stateQueue"));
        List<String> allStates = new ArrayList<String>();
        for (QueueStateDataBeans states : list) {
            if(states.getQueue().equalsIgnoreCase(queue)){
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

    public boolean isNull(String text){
        return text != null && !text.isEmpty();
    }



}
