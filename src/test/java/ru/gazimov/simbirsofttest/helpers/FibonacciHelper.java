package ru.gazimov.simbirsofttest.helpers;

import java.time.LocalDate;

public class FibonacciHelper {

    public static int calculateFibonacciForCurrentDay() {
        int currentDay = LocalDate.now().getDayOfMonth(); // Получаем текущий день месяца
        return calculateFibonacci(currentDay + 1); // Вычисляем (N+1)-е число Фибоначчи
    }

    // Метод для вычисления N-го числа Фибоначчи
    public static int calculateFibonacci(int n) {
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
