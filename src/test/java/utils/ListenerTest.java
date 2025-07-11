package utils;

import org.testng.IConfigurationListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ListenerTest extends Core implements ITestListener, IConfigurationListener {
    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshot(result);
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        takeScreenshot(result);
    }

    private void takeScreenshot(ITestResult result) {
        getScreenShotError(result.getMethod().getMethodName() + ".jpg");
        String file = ConfigPath.IMAGE_ERROR_PATH + result.getMethod().getMethodName() + ".jpg";
        Reporter.log("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"red\" class=\"bi bi-x-octagon-fill\" viewBox=\"0 0 16 16\">\n" +
                "  <path d=\"M11.46.146A.5.5 0 0 0 11.107 0H4.893a.5.5 0 0 0-.353.146L.146 4.54A.5.5 0 0 0 0 4.893v6.214a.5.5 0 0 0 .146.353l4.394 4.394a.5.5 0 0 0 .353.146h6.214a.5.5 0 0 0 .353-.146l4.394-4.394a.5.5 0 0 0 .146-.353V4.893a.5.5 0 0 0-.146-.353L11.46.146zm-6.106 4.5L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708z\"/>\n" +
                "</svg><a href='"+file+"' target='_blank'><img src='"+file+"' width=100 height=auto' /></a></p>",false);
    }

}
