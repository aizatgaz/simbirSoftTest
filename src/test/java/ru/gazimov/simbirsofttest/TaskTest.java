package ru.gazimov.simbirsofttest;

import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;

public class MainPageTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private TransactionsPage transactionsPage;
    private WebDriverWait wait;



    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        transactionsPage = new TransactionsPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void loginAndTransactionsTest() {

        authorizeUser();                                    // 1. Авторизация пользователя

        int fibonacci = calculateFibonacciForCurrentDay();  // 2. Вычисление числа Фибоначчи

        depositAccount(fibonacci);                          // 3. Пополнение счета

        withdrawAccount(fibonacci);                         // 4. Списание со счета

        checkBalanceEqualsZero();                           // 5. Проверка баланса

        assertTransactionsIsTwo();                          // 6. Проверка количества транзакций равным двум

        int i = 0;
    }



    // Метод для авторизации пользователя
    private void authorizeUser() {
        loginPage.getCustomerLoginButton().click();

        Select select = new Select(loginPage.getSelector());
        select.selectByValue(loginPage.getHarryPotter().getAttribute("value"));

        loginPage.getLoginButton().click();


    }

    // Метод для пополнения счета
    private void depositAccount(int fibonacci) {
        accountPage.getDepositButton().click();

        accountPage.getAmountToDeposit().sendKeys(String.valueOf(fibonacci));

        accountPage.getSubmitDepositButton().click();
    }

    // Метод для списания со счета
    private void withdrawAccount(int fibonacci) {
        accountPage.getWithdrawButton().click();
        accountPage.getAmountToWithdraw().sendKeys(String.valueOf(fibonacci));

        accountPage.getSubmitWithdrawButton().click();
    }

    // Метод для проверки баланса
    private void checkBalanceEqualsZero() {
        String balance = accountPage.getBalance().getText();
        Assertions.assertEquals("0", balance);
    }

    // Метод для проверки количества выполненных транзакций
    private void assertTransactionsIsTwo() {
        accountPage.getTransactionsButton().click();
        Assertions.assertEquals(2, transactionsPage.getTransactions().size());
    }


    private static int calculateFibonacciForCurrentDay() {
        int currentDay = LocalDate.now().getDayOfMonth(); // Получаем текущий день месяца
        return calculateFibonacci(currentDay + 1); // Вычисляем (N+1)-е число Фибоначчи
    }

    // Метод для вычисления N-го числа Фибоначчи
    private static int calculateFibonacci(int n) {
        int fibPrev = 0;
        int fibCurr = 1;
        for (int i = 2; i <= n; i++) {
            int temp = fibPrev + fibCurr;
            fibPrev = fibCurr;
            fibCurr = temp;
        }
        return fibPrev;
    }
}
