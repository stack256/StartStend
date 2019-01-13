package Box;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


class About {

    @BeforeSuite
    public void setUpClass() {
        drivermap = new HashMap<Long,RemoteWebDriver>();
    }

    @AfterSuite
    public void setDownClass() {
        drivermap.clear();
    }

    @BeforeMethod
    public void setUp() {
        current_login = null;
        timeoutlnseconds = 10;
        baseUrl = System.getProperty("stend.url");
        adminpass = System.getProperty("admin.pass");


        if (System.getProperty("remote.grid") != null) {
            try {
                driver = new RemoteWebDriver(new URL(System.getProperty("remote.grid")), new ChromeOptions());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            driver = new ChromeDriver();
        }
        drivermap.put(Thread.currentThread().getId(),driver);
        drivermap.get(Thread.currentThread().getId()).manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        drivermap.get(Thread.currentThread().getId()).get(baseUrl);
    }


    @AfterMethod
    public void tiredDown() {
        drivermap.get(Thread.currentThread().getId()).quit();
    }

    static String baseUrl = null;
    static String adminpass = null;
    static RemoteWebDriver driver = null;
    static Integer timeoutlnseconds;
    static String current_login;
    static HashMap<Long, RemoteWebDriver> drivermap;

    @Attachment(value = "Page screenshot", type = "image/png")
    private static byte[] saveAllureScreenshot() {
        return ((TakesScreenshot) drivermap.get(Thread.currentThread().getId())).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Вложение", type = "text/application")
    private static String saveAllureText(String report) {
        return report;
    }

    static void report(String report) {
        saveAllureScreenshot();
        saveAllureText(report);
        //for (LogEntry entry : logEntries)
        //    System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
    }

    static void softassertfail(Boolean value, String report) {
        if (!value)
            softassertfail(report);
    }

    static void softassertfail(String expectation, String reality) {
        if (!expectation.equals(reality))
            softassertfail("Ожидаемое значение: " + expectation + ". Фактический результат: " + reality);
    }

    @Step("{0}")
    static void softassertfail(String report) {
        System.out.println("Есть неблокирующая ошибка");
        /*Allure.LIFECYCLE.fire(new StepFailureEvent());
        Allure.LIFECYCLE.fire(new TestCaseFailureEvent().withThrowable(new RuntimeException("Есть неблокирующие ошибки")));
        ArrayList<Stack> stack1 = reportmap.get(Thread.currentThread().getId());
        Stack buf = stack1.get(stack1.size() - 1);
        buf.value = false;
        stack1.set(stack1.size() - 1, buf);
        reportmap.put(Thread.currentThread().getId(),stack1);*/
        report(report);
    }

    static void hardassertfail(Boolean value, String report) {
        if (!value)
            hardassertfail(report);
    }

    static void hardassertfail(String expectation, String reality) {
        if (!expectation.equals(reality))
            hardassertfail("Ожидаемое значение: " + expectation + ". Фактический результат: " + reality);
    }

    @Step("{0}")
    static void hardassertfail(String report) {
        System.out.println("Есть блокирующая ошибка");
        report(report);
        Assert.fail(report);
    }

    static void waitForLoad() {
        /*ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
*/
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebDriver jsWaitDriver;
        jsWaitDriver = drivermap.get(Thread.currentThread().getId());
        WebDriverWait wait = new WebDriverWait(jsWaitDriver,15);
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;

        //Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) jsWaitDriver)
                .executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady =  (Boolean) jsExec.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if(!jsReady)
            wait.until(jsLoad);

    }
}
