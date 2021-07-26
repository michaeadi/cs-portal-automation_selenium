package com.airtel.cs.commonutils.extentreports;

import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.driver.Driver;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ExtentReport extends Driver {

    private static final String FONT_SIZE_COLOR_DARKCYAN = "<font size=\"3\" color=\"DarkCyan\">";
    private static final String FONT_SIZE_COLOR_DARKSALMON = "<font size=\"3\" color=\"DARKSALMON\">";
    private static final String FONT_SIZE_COLOR_CORAL = "<font size=\"3\" color=\"Coral\">";
    private static final String FONT_SIZE_COLOR_WHITE = "<font size=\"3\" color=\"White\">";
    private static final String FONT_SIZE_COLOR_GOLDENROD = "<font size=\"3\" color=\"GoldenRod\">";
    private static final String FONT = "</font>";
    private static final String BREAK_LINE = "</br>";

    /**
     * @param testName : testName is the Type of test (Smoke, Sanity, Regression
     *                 etc.)
     * @throws InvalidFormatException InvalidFormatException
     * @throws Exception              Exception
     */
    public static void startTest(String testName, String methodName) {
        test = extent.createTest(testName + " :: " + methodName);
        test.assignCategory(testName);
        test.assignAuthor("Airtel-Africa");
    }

    /**
     * Below Function is End the test case and using flush method it write the
     * test logs.
     */
    public static void endTest() {
        extent.flush();
    }

    public void showInExtentReport(Status logStatus, String details, Boolean withScreenshot) {
        try {
            // screenshot.
            String message = null;
            if (logStatus.equals(Status.PASS)) {
                message = FONT_SIZE_COLOR_DARKCYAN + details + FONT;
            } else if (logStatus.equals(Status.FAIL)) {
                message = "<b>" + FONT_SIZE_COLOR_DARKSALMON + details + FONT + "</b>";
            } else if (logStatus.equals(Status.WARNING)) {
                message = "<b>" + "<font size=\"3\" color=\"GOLD\">" + details + FONT + "</b>";
            } else if (logStatus.equals(Status.SKIP)) {
                message = "<b>" + "<font size=\"3\" color=\"GOLD\">" + details + FONT + "</b>";
            } else {
                message = details;
            }
            test.log(logStatus, message);
            if (withScreenshot == null || Boolean.TRUE.equals(withScreenshot)) {
                attachScreenshotAsPerExecutionType(logStatus);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - showInExtentReport " + e.getMessage(), true);
        }
    }

    //Method for ShowInExtentReport - For adding Description of the testcase
    public void showInExtentReportAddDescriptionOFTestcase(String isPrerequisiteStepsORMainDescriptionSteps) {
        if (isPrerequisiteStepsORMainDescriptionSteps.equalsIgnoreCase("description")) {
            test.log(Status.INFO, "<b>" + "<font size=\"4\" color=\"DARKSALMON\">" + "DESCRIPTION OF TESTCASE"
                    + FONT + "</b>" + BREAK_LINE + TESTCASE_DESCRIPTION_BUILDER);
        } else if (isPrerequisiteStepsORMainDescriptionSteps.equalsIgnoreCase("pre")) {
            test.log(Status.INFO,
                    "<b>" + "<font size=\"4\" color=\"DARKSALMON\">" + "PRE-REQUISITES STEPS" + FONT + "</b>");
            test.log(Status.INFO, "<b>" + TESTCASE_DESCRIPTION_BUILDER + "</b>");
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
                test.log(Status.INFO, normalText + "<b>" + FONT_SIZE_COLOR_DARKSALMON
                        + highlightText + FONT + "</b>");
                break;
            case GOLD:
                test.log(Status.INFO,
                        normalText + "<b>" + "<font size=\"3\" color=\"gold\">" + highlightText + FONT + "</b>");
                break;
            case GREEN:
                test.log(Status.INFO, normalText + "<b>" + FONT_SIZE_COLOR_DARKCYAN
                        + highlightText + FONT + "</b>");
                break;

            case BLUE:
                test.log(Status.INFO, normalText + "<b>" + "<center>"
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
                test.log(Status.INFO, headers.toString());
                break;
            default:
                test.log(Status.INFO, normalText + "<b>" + "<font size=\"2\" color=\"MEDIUMAQUAMARINE\">"
                        + highlightText + FONT + "</b>");
        }
        if (Boolean.TRUE.equals(requiredScreenshot)) {
            attachScreenshotAsPerExecutionType(Status.INFO);
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
                    headers.append(FONT_SIZE_COLOR_GOLDENROD + "<b>").append(index).append(" . ").append("</b>").append(FONT_SIZE_COLOR_WHITE).append(valuesHeaders[i]).append(FONT).append(FONT_SIZE_COLOR_DARKCYAN).append(" - ").append(valuesActual[i]).append(FONT).append(BREAK_LINE).append(System.lineSeparator());
                } else {
                    headers.append(FONT_SIZE_COLOR_CORAL + "<b>").append(index).append(" . ").append("</b>").append(FONT_SIZE_COLOR_WHITE).append(valuesHeaders[i]).append(FONT).append(FONT_SIZE_COLOR_CORAL).append(" - ").append(valuesActual[i]).append(FONT).append(FONT_SIZE_COLOR_GOLDENROD).append(" , Expected - ").append(valuesExpected[i]).append(FONT).append(BREAK_LINE).append(System.lineSeparator());
                }
                index++;
            }
            test.log(Status.INFO, headers.toString());
        } catch (Exception e) {
            commonLib.fail("Exception in method - showInExtentReportHighlightUnmatchedValue " + e.getMessage(), true);
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
     * @param logStatus The Status
     */
    public void attachScreenshotAsPerExecutionType(Status logStatus) {
        try {
            ExtentReport extent = new ExtentReport();
            String screenshot = "";
            screenshot = extent.addScreenShot(driver);
            test.log(logStatus, MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            commonLib.error("ERROR INSIDE METHOD - attachScreenshotAsPerExecutionType" + BREAK_LINE + e.getMessage());
        }
    }
}