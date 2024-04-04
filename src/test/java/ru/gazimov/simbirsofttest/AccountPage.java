package ru.gazimov.simbirsofttest;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


@Getter
public class AccountPage {

    @FindBy(xpath = "//button[@ng-click = 'deposit()' and contains(text(), 'Deposit')]")
    private WebElement depositButton;

    @FindBy(xpath = "//label[text() = 'Amount to be Deposited :']/../input[@placeholder = 'amount']")
    private WebElement amountToDeposit;

    @FindBy(xpath = "//button[@class='btn btn-default' and text()='Deposit']")
    private WebElement submitDepositButton;

    @FindBy(xpath = "//button[@ng-click = 'withdrawl()' and contains(text(), 'Withdrawl')]")
    private WebElement withdrawButton;

    @FindBy(xpath = "//label[text() = 'Amount to be Withdrawn :']/../input[@placeholder = 'amount']")
    private WebElement amountToWithdraw;

    @FindBy(xpath = "//button[@class='btn btn-default' and text()='Withdraw']")
    private WebElement submitWithdrawButton;

    @FindBy(xpath = "//div[@class='center']/strong[text()='0']")
    private WebElement balance;

    @FindBy(xpath = "//button[@ng-click = 'transactions()']")
    private WebElement transactionsButton;

    public AccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
