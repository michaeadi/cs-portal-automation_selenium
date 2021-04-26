package com.airtel.cs.commonutils.extentreports;

import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ExtentReport extends Driver {

    private static final String FONT_SIZE_COLOR_MEDIUMQUAMARINE = "<font size=\"3\" color=\"MEDIUMAQUAMARINE\">";
    private static final String FONT_SIZE_COLOR_DARKSALMON = "<font size=\"3\" color=\"DARKSALMON\">";
    private static final String FONT_SIZE_COLOR_CORAL = "<font size=\"3\" color=\"Coral\">";
    private static final String FONT_SIZE_COLOR_WHITE = "<font size=\"3\" color=\"White\">";
    private static final String FONT_SIZE_COLOR_GOLDENROD = "<font size=\"3\" color=\"GoldenRod\">";
    private static final String FONT = "</font>";
    private static final String BREAK_LINE = "</br>";


    /**
     * @param testName   : testName is the Type of test (Smoke, Sanity, Regression
     *                   etc.)
     * @param methodName : The Current Method/Function name where user is calling this
     *                   function.
     * @throws InvalidFormatException InvalidFormatException
     * @throws Exception              Exception
     */
    public static void startTest(String testName, String methodName) {
        test = extent.startTest(testName + " :: " + methodName);
        test.assignCategory(testName);
        test.assignAuthor("Airtel-Africa");
    }

    /**
     * Below Function is End the test case and using flush method it write the
     * test logs.
     */
    public static void endTest(ExtentTest test) {
        extent.endTest(test);
        extent.flush();
    }

    public void showInExtentReport(LogStatus logStatus, String details, Boolean withScreenshot) {
        try {
            // screenshot.
            String message = null;
            if (logStatus.equals(LogStatus.PASS)) {
                message = FONT_SIZE_COLOR_MEDIUMQUAMARINE + details + FONT;
            } else if (logStatus.equals(LogStatus.FAIL)) {
                message = "<b>" + FONT_SIZE_COLOR_DARKSALMON + details + FONT + "</b>";
            } else if (logStatus.equals(LogStatus.ERROR)) {
                message = "<b>" + FONT_SIZE_COLOR_DARKSALMON + details + FONT + "</b>";
            } else if (logStatus.equals(LogStatus.WARNING)) {
                message = "<b>" + "<font size=\"3\" color=\"GOLD\">" + details + FONT + "</b>";
            } else if (logStatus.equals(LogStatus.SKIP)) {
                message = "<b>" + "<font size=\"3\" color=\"GOLD\">" + details + FONT + "</b>";
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
                    + FONT + "</b>" + BREAK_LINE + TESTCASE_DESCRIPTION_BUILDER);
        } else if (isPrerequisiteStepsORMainDescriptionSteps.toLowerCase().contains("pre")) {
            test.log(LogStatus.INFO,
                    "<b>" + "<font size=\"4\" color=\"DARKSALMON\">" + "PRE-REQUISITES STEPS" + FONT + "</b>");
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
                test.log(LogStatus.INFO, "HTML", normalText + "<b>" + FONT_SIZE_COLOR_DARKSALMON
                        + highlightText + FONT + "</b>");
                break;
            case GOLD:
                test.log(LogStatus.INFO, "HTML",
                        normalText + "<b>" + "<font size=\"3\" color=\"gold\">" + highlightText + FONT + "</b>");
                break;
            case GREEN:
                test.log(LogStatus.INFO, "HTML", normalText + "<b>" + FONT_SIZE_COLOR_MEDIUMQUAMARINE
                        + highlightText + FONT + "</b>");
                break;

            case BLUE:
                test.log(LogStatus.INFO, "HTML", normalText + "<b>" + "<center>"
                        + "<font size=\"4\" color=\"LightBlue\">" + highlightText + FONT + "</center>" + "</b>");
                break;
            case STEELBLUE:
                int index = 1;
                String[] valuesHeaders = normalText.split(",");
                String[] values = highlightText.split(",");
                StringBuilder headers = new StringBuilder();
                for (int i = 0; i < valuesHeaders.length; i++) {
                    headers.append(FONT_SIZE_COLOR_CORAL + "<b>").append(index).append(" . ").append("</b>").append(FONT_SIZE_COLOR_WHITE).append(valuesHeaders[i]).append(FONT).append(FONT_SIZE_COLOR_GOLDENROD).append(" - ").append(values[i]).append(FONT).append(BREAK_LINE).append(System.lineSeparator());
                    index++;
                }
                test.log(LogStatus.INFO, headers.toString());
                break;
            default:
                test.log(LogStatus.INFO, "HTML", normalText + "<b>" + "<font size=\"2\" color=\"MEDIUMAQUAMARINE\">"
                        + highlightText + FONT + "</b>");
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
                    headers.append(FONT_SIZE_COLOR_GOLDENROD + "<b>").append(index).append(" . ").append("</b>").append(FONT_SIZE_COLOR_WHITE).append(valuesHeaders[i]).append(FONT).append(FONT_SIZE_COLOR_MEDIUMQUAMARINE).append(" - ").append(valuesActual[i]).append(FONT).append(BREAK_LINE).append(System.lineSeparator());
                } else {
                    headers.append(FONT_SIZE_COLOR_CORAL + "<b>").append(index).append(" . ").append("</b>").append(FONT_SIZE_COLOR_WHITE).append(valuesHeaders[i]).append(FONT).append(FONT_SIZE_COLOR_CORAL).append(" - ").append(valuesActual[i]).append(FONT).append(FONT_SIZE_COLOR_GOLDENROD).append(" , Expected - ").append(valuesExpected[i]).append(FONT).append(BREAK_LINE).append(System.lineSeparator());
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
     */
    public String addScreenShot(WebDriver driver) {
        String filePath = null;
        try {
            filePath = "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * This Method will attach screenshot as Per Execution Type
     *
     * @param logStatus The LogStatus
     */
    public void attachScreenshotAsPerExecutionType(LogStatus logStatus) {
        try {
            ExtentReport extent = new ExtentReport();
            String screenshot = "";
            screenshot = extent.addScreenShot(driver);
            test.log(logStatus, test.addBase64ScreenShot(screenshot));
        } catch (Exception e) {
            commonLib.error("ERROR INSIDE METHOD - attachScreenshotAsPerExecutionType" + BREAK_LINE + e.getMessage());
        }
    }
}