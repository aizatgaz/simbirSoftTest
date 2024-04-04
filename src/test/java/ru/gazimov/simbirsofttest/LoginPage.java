package ru.gazimov.simbirsofttest;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class LoginPage {

    @FindBy(xpath = "//button[text() = 'Customer Login']")
    private WebElement customerLoginButton;

    @FindBy(xpath = "//label[text() = 'Your Name :']/../select")
    private WebElement selector;

    @FindBy(xpath = "//option[text() = 'Harry Potter']")
    private WebElement harryPotter;

    @FindBy(xpath = "//button[text() = 'Login']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
