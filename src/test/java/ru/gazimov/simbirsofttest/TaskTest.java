package ru.gazimov.simbirsofttest;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.gazimov.simbirsofttest.helpers.AllureHelper;
import ru.gazimov.simbirsofttest.helpers.FibonacciHelper;
import ru.gazimov.simbirsofttest.helpers.FileHelper;
import ru.gazimov.simbirsofttest.pages.AccountPage;
import ru.gazimov.simbirsofttest.pages.LoginPage;
import ru.gazimov.simbirsofttest.pages.TransactionsPage;

import java.net.URL;
import java.time.Duration;

import static ru.gazimov.simbirsofttest.constants.Constants.GLOBALSQA_URL;
import static ru.gazimov.simbirsofttest.constants.Constants.TRANSACTIONS_FILE_PATH;

public class TaskTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private TransactionsPage transactionsPage;


    @SneakyThrows
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new RemoteWebDriver(new URL("http://172.17.0.3:4444/"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.get(GLOBALSQA_URL);

        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        transactionsPage = new TransactionsPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Step("Авторизация, выполнение транзакций и проверка")
    public void loginAndTransactionsTest() {
        loginPage.authorizeUser();
        int fibonacci = FibonacciHelper.calculateFibonacciForCurrentDay();
        accountPage.depositAccount(fibonacci);

        waitForTransactionProcessing();
        accountPage.withdrawAccount(fibonacci);
        waitForTransactionProcessing();
        checkBalanceEqualsZero();
        waitForTransactionProcessing();
        assertTransactionsIsTwo();
        FileHelper.writeCsvFileForTransactions(TRANSACTIONS_FILE_PATH, transactionsPage.getTransactions());
        AllureHelper.addAllureAttachment();
    }


    @Step("Проверка баланса на равенство нулю")
    public void checkBalanceEqualsZero() {
        String balance = accountPage.getBalance().getText();
        Assertions.assertEquals("0", balance);
    }

    @Step("Проверка количества выполненных транзакций равным двум")
    private void assertTransactionsIsTwo() {
        accountPage.getTransactionsButton().click();
        Assertions.assertEquals(2, transactionsPage.getTransactions().size());
    }

    @Step("Явное ожидание обработки транзакции")
    private void waitForTransactionProcessing() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("someElement")));
    }

}
