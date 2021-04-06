package com.airtel.cs.commonutils.extentreports;

import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

import static org.apache.hc.core5.util.Deadline.DATE_FORMAT;

public class ExtentReport extends Driver {
    /**
     * @param testName   : testName is the Type of test (Smoke, Sanity, Regression
     *                   etc.)
     * @param methodName : The Current Method/Function name where user is calling this
     *                   function.
     * @throws InvalidFormatException
     * @throws Exception
     */
    public static void startTest(String testName, String methodName) {
        test = reports.startTest(testName + " :: " + methodName);
        test.assignCategory(testName);
        test.assignAuthor("Airtel-Africa");
    }

    /**
     * Below Function is End the test case and using flush method it write the
     * test logs.
     */
    public static void endTest(ExtentTest test) {
        reports.endTest(test);
        reports.flush();
    }

    public void showInExtentReport(LogStatus logStatus, String details, Boolean withScreenshot) {
        try {
            // screenshot.
            String message = null;
            if (logStatus.equals(LogStatus.PASS)) {
                message = "<font size=\"3\" color=\"MEDIUMAQUAMARINE\">" + details + "</font>";
            } else if (logStatus.equals(LogStatus.FAIL)) {
                message = "<b>" + "<font size=\"3\" color=\"DARKSALMON\">" + details + "</font>" + "</b>";
            } else if (logStatus.equals(LogStatus.ERROR)) {
                message = "<b>" + "<font size=\"3\" color=\"DARKSALMON\">" + details + "</font>" + "</b>";
            } else if (logStatus.equals(LogStatus.WARNING)) {
                message = "<b>" + "<font size=\"3\" color=\"GOLD\">" + details + "</font>" + "</b>";
            }else if (logStatus.equals(LogStatus.SKIP)) {
                message = "<b>" + "<font size=\"3\" color=\"GOLD\">" + details + "</font>" + "</b>";
            } else {
                message = details;
            }
            test.log(logStatus, message);
            if (withScreenshot == null || Boolean.TRUE.equals(withScreenshot)) {
                attachScreenshotAsPerExecutionType(logStatus);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Method for ShowInExtentReport - For adding Description of the testcase
    public void showInExtentReportAddDescriptionOFTestcase(String isPrerequisiteStepsORMainDescriptionSteps) {

        if (isPrerequisiteStepsORMainDescriptionSteps.toLowerCase().contains("description")) {
            test.log(LogStatus.INFO, "<b>" + "<font size=\"4\" color=\"DARKSALMON\">" + "DESCRIPTION OF TESTCASE"
                    + "</font>" + "</b>" + "</br>" + TESTCASE_DESCRIPTION_BUILDER);
        } else if (isPrerequisiteStepsORMainDescriptionSteps.toLowerCase().contains("pre")) {
            test.log(LogStatus.INFO,
                    "<b>" + "<font size=\"4\" color=\"DARKSALMON\">" + "PRE-REQUISITES STEPS" + "</font>" + "</b>");
            test.log(LogStatus.INFO, "HTML", "<b>" + TESTCASE_DESCRIPTION_BUILDER + "</b>");
        }
    }

    //Method for ShowInExtentReport - For adding Header in testcase

    public void showInExtentReportTextHighlight(String normalText, String highlightText,
                                                ReportInfoMessageColorList color) {
        showInExtentReportTextHighlight(normalText, highlightText, color, false);
    }

    public void showInExtentReportTextHighlight(String normalText, String highlightText,
                                                ReportInfoMessageColorList color, Boolean requiredScreenshot) {
        switch (color) {
            case RED:
                test.log(LogStatus.INFO, "HTML", normalText + "<b>" + "<font size=\"3\" color=\"DARKSALMON\">"
                        + highlightText + "</font>" + "</b>");
                break;
            case GOLD:
                test.log(LogStatus.INFO, "HTML",
                        normalText + "<b>" + "<font size=\"3\" color=\"gold\">" + highlightText + "</font>" + "</b>");
                break;
            case GREEN:
                test.log(LogStatus.INFO, "HTML", normalText + "<b>" + "<font size=\"3\" color=\"MEDIUMAQUAMARINE\">"
                        + highlightText + "</font>" + "</b>");
                break;

            case BLUE:
                test.log(LogStatus.INFO, "HTML", normalText + "<b>" + "<center>"
                        + "<font size=\"4\" color=\"LightBlue\">" + highlightText + "</font>" + "</center>" + "</b>");
                break;
            case STEELBLUE:
                int index = 1;
                String[] valuesHeaders = normalText.split(",");
                String[] values = highlightText.split(",");
                StringBuilder headers = new StringBuilder();
                for (int i = 0; i < valuesHeaders.length; i++) {
                    headers.append("<font size=\"3\" color=\"Coral\">" + "<b>").append(index).append(" . ").append("</b>").append("<font size=\"3\" color=\"White\">").append(valuesHeaders[i]).append("</font>").append("<font size=\"3\" color=\"GoldenRod\">").append(" - ").append(values[i]).append("</font>").append("</br>").append(System.lineSeparator());
                    index++;
                }
                test.log(LogStatus.INFO, headers.toString());
                break;
            default:
                test.log(LogStatus.INFO, "HTML", normalText + "<b>" + "<font size=\"2\" color=\"MEDIUMAQUAMARINE\">"
                        + highlightText + "</font>" + "</b>");
        }
        if (Boolean.TRUE.equals(requiredScreenshot)) {
            attachScreenshotAsPerExecutionType(LogStatus.INFO);
        }
    }

    public void showInExtentReportHighlightUnmatchedValue(String itemNamesForComparision, String actualValues,
                                                          String expectedValues) {
        try {
            int index = 1;
            String[] valuesHeaders = itemNamesForComparision.split(",");
            String[] valuesActual = actualValues.split(",");
            String[] valuesExpected = expectedValues.split(",");
            StringBuilder headers = new StringBuilder();
            for (int i = 0; i < valuesHeaders.length; i++) {
                if (valuesActual[i].trim().equals(valuesExpected[i].trim())) {
                    headers.append("<font size=\"3\" color=\"GoldenRod\">" + "<b>").append(index).append(" . ").append("</b>").append("<font size=\"3\" color=\"White\">").append(valuesHeaders[i]).append("</font>").append("<font size=\"3\" color=\"MEDIUMAQUAMARINE\">").append(" - ").append(valuesActual[i]).append("</font>").append("</br>").append(System.lineSeparator());
                } else {
                    headers.append("<font size=\"3\" color=\"Coral\">" + "<b>").append(index).append(" . ").append("</b>").append("<font size=\"3\" color=\"White\">").append(valuesHeaders[i]).append("</font>").append("<font size=\"3\" color=\"Coral\">").append(" - ").append(valuesActual[i]).append("</font>").append("<font size=\"3\" color=\"GoldenRod\">").append(" , Expected - ").append(valuesExpected[i]).append("</font>").append("</br>").append(System.lineSeparator());
                }
                index++;
            }
            test.log(LogStatus.INFO, headers.toString());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Below method is use to take the screenshot and Add it to the particular
     * test step.
     *
     * @throws IOException
     */
    public String addScreenShot(WebDriver driver) {
        String filePath = null;
        try {
            String htmlfileScreenshotsPath = System.getProperty("user.dir") + "/resources/screenshots/"
                    + dateTime.toString(DATE_FORMAT) + "/";
            filePath = htmlfileScreenshotsPath + dateTime.toString().toLowerCase() + ".png";
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(src, destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public void attachScreenshotAsPerExecutionType(LogStatus logStatus) {
        try {
            ExtentReport extent = new ExtentReport();
            String screenshot = "";
            String[] arrSplit = null;
            screenshot = extent.addScreenShot(driver);
            if (screenshot.toLowerCase().contains("PM".toLowerCase())) {
                arrSplit = screenshot.toLowerCase().split("PM/".toLowerCase());
            } else if (screenshot.toLowerCase().contains("AM".toLowerCase())) {
                arrSplit = screenshot.toLowerCase().split("AM/".toLowerCase());
            }
            assert arrSplit != null;
            screenshot = arrSplit[1];
            screenshot = "./".concat(screenshot);
            test.log(logStatus, test.addScreenCapture(screenshot));
        } catch (Exception e) {
            commonLib.error("ERROR INSIDE METHOD - attachScreenshotAsPerExecutionType" + "</br>" + e.getMessage());
        }
    }
}