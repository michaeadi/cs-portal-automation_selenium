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

        Object[][] hashMapObj = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            hashMapObj[i][0] = list.get(i);
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
            if (!l.getInteraction().isEmpty()) {
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
            if (!l.getWorkflow().isEmpty()) {
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
            if (!l.getLoginQueue().isEmpty()) {
                finalList.add(l.getLoginQueue().toLowerCase().trim());
            }
        }
        return finalList;
    }

    @DataProvider(name="pinTag")
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

    @DataProvider(name = "ticketId")
    public Object[][] getTestData5() {
        nftrDataExcelToBeanDao credsExcelToBeanDao = new nftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, BaseTest.ExcelPath);
        List<nftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "NftrSheet"));
        List<nftrDataBeans> finalTicketList = new ArrayList<nftrDataBeans>();
        for (nftrDataBeans nftrTicket : list) {
            System.out.println("Ticket Id: " + nftrTicket.getTicketNumber());
            if (nftrTicket.getTicketNumber() == null) {
                System.out.println("No Ticket ID Found");
            } else {
                finalTicketList.add(nftrTicket);
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
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("NftrSheet"));

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
       if(stateName.equalsIgnoreCase("open")){
           return openState;
       }else {
           return closeState;
       }
    }

    //helper method
    public List<PriorityDataBean> getPriority(){
        PriorityDataExcelToBeanDao priorityDataBean = new PriorityDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<PriorityDataBean> list =
                priorityDataBean.getData(Excel.getAbsolutePath(), config.getProperty("priority"));
        return list;
    }

    //helper method
    public Map<String,Boolean> getALLPinnedTags(){
        PinnedTagDataExcelToBeanDao pinnedTag=new PinnedTagDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<PinnedtagsDataBeans> list =
                pinnedTag.getData(Excel.getAbsolutePath(), config.getProperty("PinnedTagSheet"));
        Map<String,Boolean> finalList=new HashMap<String,Boolean>();
        for(PinnedtagsDataBeans l:list){
            finalList.put(l.getTagName().toLowerCase().trim(),false);
        }
        return finalList;
    }

    @DataProvider(name = "singleTicketId")
    public Object[][] getSingleTicketId() {
        nftrDataExcelToBeanDao credsExcelToBeanDao = new nftrDataExcelToBeanDao();
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, tests.BaseTest.Opco + ".xlsx");
        List<nftrDataBeans> list =
                credsExcelToBeanDao.getData(Excel.getAbsolutePath(), config.getProperty("NftrSheet"));
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
}
