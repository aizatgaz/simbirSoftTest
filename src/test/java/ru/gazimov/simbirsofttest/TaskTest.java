package ru.gazimov.simbirsofttest;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static ru.gazimov.simbirsofttest.Constants.TRANSACTIONS_FILE_PATH;

public class TaskTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private TransactionsPage transactionsPage;


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        transactionsPage = new TransactionsPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void loginAndTransactionsTest() throws InterruptedException {

        authorizeUser();                                    // 1. Авторизация пользователя

        int fibonacci = calculateFibonacciForCurrentDay();  // 2. Вычисление числа Фибоначчи

        depositAccount(fibonacci);                          // 3. Пополнение счета

        // я понимаю, что так нельзя делать, но с явным и неявным ожиданием список транзакций всегда пустой,
        // а изменения даты во вкладке транзакций, мне не помогло, возможно я её не правильно менял
        // засыпание на 800, не приносит ошибок на 20 тестов, думаю оптимален
        Thread.sleep(800);

        withdrawAccount(fibonacci);                         // 4. Списание со счета

        Thread.sleep(800);

        checkBalanceEqualsZero();                           // 5. Проверка баланса

        Thread.sleep(800);

        assertTransactionsIsTwo();                          // 6. Проверка количества транзакций равным двум

        writeCsvFileForTransactions(TRANSACTIONS_FILE_PATH);// 7. Запись транзакций в файл - transactions.csv

        addAllureAddAttachment();                           // 8. Добавляем файл к отчету Allure

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

    private void writeCsvFileForTransactions(String filePath) {
        // Удаляем существующий файл, если он существует
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        try(FileWriter writer = new FileWriter(filePath)) {

            for (WebElement row : transactionsPage.getTransactions()) {
                // Получаем данные из каждой ячейки строки
                List<WebElement> cells = row.findElements(By.tagName("td"));

                String dateTime = cells.get(0).getText(); // Дата-время транзакции
                String amount = cells.get(1).getText(); // Сумма транзакции
                String transactionType = cells.get(2).getText(); // Тип транзакции

                // Форматируем дату-время
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a", Locale.ENGLISH);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
                LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, inputFormatter);
                String formattedDateTime = parsedDateTime.format(outputFormatter);

                // Записываем данные в файл CSV
                writer.write(formattedDateTime + " " + amount + " " + transactionType + "\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Добавляем файл к отчету Allure
    private void addAllureAddAttachment() {
        String value;
        try {
            value = new String(Files.readAllBytes(Paths.get(TRANSACTIONS_FILE_PATH)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while reading file - " + TRANSACTIONS_FILE_PATH);
        }
        Allure.addAttachment("transactions", value);
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
