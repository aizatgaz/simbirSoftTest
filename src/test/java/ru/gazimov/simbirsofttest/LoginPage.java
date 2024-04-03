package ru.gazimov.simbirsofttest;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class MainPage {
    @FindBy(xpath = "//button[text() = 'Customer Login']")
    private WebElement customerLoginButton;

    @FindBy(xpath = "//label[text() = 'Your Name :']/../select")
    private WebElement selector;

    @FindBy(xpath = "//option[text() = 'Harry Potter']")
    private WebElement harryPotter;

    @FindBy(xpath = "//button[text() = 'Login']")
    private WebElement loginButton;


    @FindBy(xpath = "//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']")
    private WebElement toolsMenu;

    @FindBy(css = "[data-test='site-header-search-action']")
    private WebElement searchButton;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
