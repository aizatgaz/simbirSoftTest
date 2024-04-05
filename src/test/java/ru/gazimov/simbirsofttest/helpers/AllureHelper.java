package ru.gazimov.simbirsofttest.helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.gazimov.simbirsofttest.constants.Constants.TRANSACTIONS_FILE_PATH;

public class AllureHelper {

    @Step("Добавление файла к отчету Allure")
    public static void addAllureAttachment() {
        String value;
        try {
            value = new String(Files.readAllBytes(Paths.get(TRANSACTIONS_FILE_PATH)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while reading file - " + TRANSACTIONS_FILE_PATH);
        }
        Allure.addAttachment("transactions", value);
    }

}
