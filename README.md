# Задание для SimbirSoft

## Само задание:

В задании необходимо:
1) Использовать Python/Java, подключить библиотеку Selenium Webdriver;
2) С помощью Selenium открыть браузер, открыть страницу страницу
https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login;
3) Авторизоваться пользователем «Harry Potter»;
4) Вычислить N-е число Фибоначчи, где N - это текущий день месяца + 1.
Пример: сегодня 08.02.2023, 9-е чисто Фибоначчи равно 21;
5) Выполнить пополнение счета (Deposit) на сумму равную вычисленному в
п.4 числу;
6) Выполнить списание со счета (Withdrawl) на сумму равную вычисленному
в п.4 числу;
7) Выполнить проверку баланса - должен быть равен нулю;
8) Открыть страницу транзакций и проверить наличие обеих транзакций;
9) Сформировать файл формата csv, в который выгрузить данные о
проведенных транзакциях;
Файл должен содержать строки следующего формата
<Дата-времяТранзакции Сумма ТипТранзакции>, где
Формат Дата-времяТранзакции - "ДД Месяц ГГГГ ЧЧ:ММ:СС"
Формат Сумма - число
Формат ТипТранзакции - значение из списка [Credit, Debit]
10) Оформить сформированный файл как вложение к отчету allure.