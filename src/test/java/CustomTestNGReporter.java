import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomTestNGReporter implements IReporter {

    private static final String emailableReportTemplateFile = "src/test/java/report-template.html";

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        try {
            String customReportTemplateStr = this.readEmailabelReportTemplate();

            String customReportTitle = this.getCustomReportTitle("Custom TestNG Report");

            String customSuiteSummary = this.getTestSuiteSummary(suites);

            String customTestMethodSummary = this.getTestMehodSummary(suites);

            customReportTemplateStr = customReportTemplateStr.replaceAll("\\$TestNG_Custom_Report_Title\\$", customReportTitle);

            customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Summary\\$", customSuiteSummary);

            customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Detail\\$", customTestMethodSummary);

            File targetFile = new File(outputDirectory + "/custom-emailable-report.html");
            FileWriter fw = new FileWriter(targetFile);
            fw.write(customReportTemplateStr);
            fw.flush();
            fw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String readEmailabelReportTemplate() {
        StringBuffer retBuf = new StringBuffer();

        try {

            File file = new File(this.emailableReportTemplateFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                retBuf.append(line);
                line = br.readLine();
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            return retBuf.toString();
        }
    }

    private String getCustomReportTitle(String title) {
        StringBuffer retBuf = new StringBuffer();
        retBuf.append(title + " " + this.getDateInStringFormat(new Date()));
        return retBuf.toString();
    }

    private String getTestSuiteSummary(List<ISuite> suites) {
        StringBuffer retBuf = new StringBuffer();

        try {
            int totalTestCount = 0;
            int totalTestPassed = 0;
            int totalTestFailed = 0;
            int totalTestSkipped = 0;

            for (ISuite tempSuite : suites) {
                retBuf.append("<tr><td colspan=11><center><b>" + tempSuite.getName() + "</b></center></td></tr>");

                Map<String, ISuiteResult> testResults = tempSuite.getResults();

                for (ISuiteResult result : testResults.values()) {

                    retBuf.append("<tr>");

                    ITestContext testObj = result.getTestContext();

                    totalTestPassed = testObj.getPassedTests().getAllMethods().size();
                    totalTestSkipped = testObj.getSkippedTests().getAllMethods().size();
                    totalTestFailed = testObj.getFailedTests().getAllMethods().size();

                    totalTestCount = totalTestPassed + totalTestSkipped + totalTestFailed;

                    retBuf.append("<td>");
                    retBuf.append(testObj.getName());
                    retBuf.append("</td>");

                    retBuf.append("<td>");
                    retBuf.append(totalTestCount);
                    retBuf.append("</td>");

                    retBuf.append("<td bgcolor=green>");
                    retBuf.append(totalTestPassed);
                    retBuf.append("</td>");

                    retBuf.append("<td bgcolor=yellow>");
                    retBuf.append(totalTestSkipped);
                    retBuf.append("</td>");

                    retBuf.append("<td bgcolor=red>");
                    retBuf.append(totalTestFailed);
                    retBuf.append("</td>");

                    String browserType = tempSuite.getParameter("browserType");
                    if (browserType == null || browserType.trim().length() == 0) {
                        browserType = "Chrome";
                    }

                    retBuf.append("<td>");
                    retBuf.append(browserType);
                    retBuf.append("</td>");

                    Date startDate = testObj.getStartDate();
                    retBuf.append("<td>");
                    retBuf.append(this.getDateInStringFormat(startDate));
                    retBuf.append("</td>");

                    Date endDate = testObj.getEndDate();
                    retBuf.append("<td>");
                    retBuf.append(this.getDateInStringFormat(endDate));
                    retBuf.append("</td>");

                    long deltaTime = endDate.getTime() - startDate.getTime();
                    String deltaTimeStr = this.convertDeltaTimeToString(deltaTime);
                    retBuf.append("<td>");
                    retBuf.append(deltaTimeStr);
                    retBuf.append("</td>");

                    retBuf.append("<td>");
                    retBuf.append(this.stringArrayToString(testObj.getIncludedGroups()));
                    retBuf.append("</td>");

                    retBuf.append("<td>");
                    retBuf.append(this.stringArrayToString(testObj.getExcludedGroups()));
                    retBuf.append("</td>");

                    retBuf.append("</tr>");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return retBuf.toString();
        }
    }

    private String getDateInStringFormat(Date date) {
        StringBuffer retBuf = new StringBuffer();
        if (date == null) {
            date = new Date();
        }
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        retBuf.append(df.format(date));
        return retBuf.toString();
    }

    private String convertDeltaTimeToString(long deltaTime) {
        StringBuffer retBuf = new StringBuffer();

        long milli = deltaTime;

        long seconds = deltaTime / 1000;

        long minutes = seconds / 60;

        long hours = minutes / 60;

        retBuf.append(hours + ":" + minutes + ":" + seconds + ":" + milli);

        return retBuf.toString();
    }

    private String getTestMehodSummary(List<ISuite> suites) {
        StringBuffer retBuf = new StringBuffer();

        try {
            for (ISuite tempSuite : suites) {
                retBuf.append("<tr><td colspan=7><center><b>" + tempSuite.getName() + "</b></center></td></tr>");

                Map<String, ISuiteResult> testResults = tempSuite.getResults();

                for (ISuiteResult result : testResults.values()) {

                    ITestContext testObj = result.getTestContext();

                    String testName = testObj.getName();

                    IResultMap testFailedResult = testObj.getFailedTests();
                    String failedTestMethodInfo = this.getTestMethodReport(testName, testFailedResult, false, false);
                    retBuf.append(failedTestMethodInfo);

                    IResultMap testSkippedResult = testObj.getSkippedTests();
                    String skippedTestMethodInfo = this.getTestMethodReport(testName, testSkippedResult, false, true);
                    retBuf.append(skippedTestMethodInfo);

                    IResultMap testPassedResult = testObj.getPassedTests();
                    String passedTestMethodInfo = this.getTestMethodReport(testName, testPassedResult, true, false);
                    retBuf.append(passedTestMethodInfo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return retBuf.toString();
        }
    }

    private String getTestMethodReport(String testName, IResultMap testResultMap, boolean passedReault, boolean skippedResult) {
        StringBuffer retStrBuf = new StringBuffer();

        String resultTitle = testName;

        String color = "green";

        if (skippedResult) {
            resultTitle += " - Skipped ";
            color = "yellow";
        } else {
            if (!passedReault) {
                resultTitle += " - Failed ";
                color = "red";
            } else {
                resultTitle += " - Passed ";
                color = "green";
            }
        }

        retStrBuf.append("<tr bgcolor=" + color + "><td colspan=7><center><b>" + resultTitle + "</b></center></td></tr>");

        Set<ITestResult> testResultSet = testResultMap.getAllResults();

        for (ITestResult testResult : testResultSet) {
            String testClassName = "";
            String testMethodName = "";
            String startDateStr = "";
            String executeTimeStr = "";
            String paramStr = "";
            String reporterMessage = "";
            String exceptionMessage = "";

            testClassName = testResult.getTestClass().getName();

            testMethodName = testResult.getMethod().getMethodName();

            long startTimeMillis = testResult.getStartMillis();
            startDateStr = this.getDateInStringFormat(new Date(startTimeMillis));

            long deltaMillis = testResult.getEndMillis() - testResult.getStartMillis();
            executeTimeStr = this.convertDeltaTimeToString(deltaMillis);

            Object paramObjArr[] = testResult.getParameters();
            for (Object paramObj : paramObjArr) {
                paramStr += (String) paramObj;
                paramStr += " ";
            }

            List<String> repoterMessageList = Reporter.getOutput(testResult);
            for (String tmpMsg : repoterMessageList) {
                reporterMessage += tmpMsg;
                reporterMessage += " ";
            }

            Throwable exception = testResult.getThrowable();
            if (exception != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                exception.printStackTrace(pw);

                exceptionMessage = sw.toString();
            }

            retStrBuf.append("<tr bgcolor=" + color + ">");
            retStrBuf.append("<td>");
            retStrBuf.append(testClassName);
            retStrBuf.append("</td>");

            retStrBuf.append("<td>");
            retStrBuf.append(testMethodName);
            retStrBuf.append("</td>");

            retStrBuf.append("<td>");
            retStrBuf.append(startDateStr);
            retStrBuf.append("</td>");

            retStrBuf.append("<td>");
            retStrBuf.append(executeTimeStr);
            retStrBuf.append("</td>");

            retStrBuf.append("<td>");
            retStrBuf.append(paramStr);
            retStrBuf.append("</td>");

            retStrBuf.append("<td>");
            retStrBuf.append(reporterMessage);
            retStrBuf.append("</td>");

            retStrBuf.append("<td>");
            retStrBuf.append(exceptionMessage);
            retStrBuf.append("</td>");

            retStrBuf.append("</tr>");
        }
        return retStrBuf.toString();
    }

    private String stringArrayToString(String strArr[]) {
        StringBuffer retStrBuf = new StringBuffer();
        if (strArr != null) {
            for (String str : strArr) {
                retStrBuf.append(str);
                retStrBuf.append(" ");
            }
        }
        return retStrBuf.toString();
    }

}
