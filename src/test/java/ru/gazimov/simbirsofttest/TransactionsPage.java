package ru.gazimov.simbirsofttest;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class TransactionsPage {

    @FindBy(css = "table.table")
    private WebElement table;

    @FindBy(css = "table.table tbody tr")
    private List<WebElement> transactions;

    public TransactionsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
