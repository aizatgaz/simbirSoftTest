package ru.gazimov.simbirsofttest.helpers;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileHelper {

    @Step("Запись транзакций в файл {filePath}")
    public static void writeCsvFileForTransactions(String filePath, List<WebElement> transactions) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        try(FileWriter writer = new FileWriter(filePath)) {

            for (WebElement row : transactions) {
                // Получаем данные из каждой ячейки строки
                List<WebElement> cells = row.findElements(By.tagName("td"));

                String dateTime = cells.get(0).getText(); // Дата-время транзакции
                String amount = cells.get(1).getText(); // Сумма транзакции
                String transactionType = cells.get(2).getText(); // Тип транзакции

                String formattedDateTime = DateTimeHelper.formatDateTime(dateTime);

                // Записываем данные в файл CSV
                writer.write(formattedDateTime + " " + amount + " " + transactionType + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
