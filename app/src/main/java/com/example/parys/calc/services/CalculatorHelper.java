package com.example.parys.calc.services;

import java.util.function.Function;

public class CalculatorHelper {

    public String ExecuteMathFunctionOnValue(String display, Function<Double, Double> func) {
        String newDisplay = removeBrackets(display);
        Integer lengthOfNumber = getLengthOfNumber(newDisplay);
        String number = cutNumberFromEquasion(newDisplay, lengthOfNumber);

        Double nbr = 0.0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            nbr = func.apply(Double.parseDouble(number));
        }
        number = nbr.toString();

        newDisplay = newDisplay.substring(0, newDisplay.length() - lengthOfNumber);
        newDisplay += number;
        return newDisplay;
    }

    public String ExecuteMathTrigonometryFunctionOnValue(String display, Function<Double, Double> func) {
        String newDisplay = ExecuteMathFunctionOnValue(display, Math::toRadians);
        newDisplay = ExecuteMathFunctionOnValue(newDisplay, func);
        return newDisplay;
    }

    public boolean DoesEquasionEndWithOperator(String text) {
        return (text.endsWith("*") || text.endsWith("/") || text.endsWith("-") || text.endsWith("+") || text.endsWith(".") || text.endsWith("E-") || text.endsWith("E"));
    }

    private String removeBrackets(String equasion) {
        if (equasion.endsWith(")")) {
            equasion = equasion.substring(0, equasion.length() - 1);
            String text = equasion;
            for (int i = text.length(); i > 0; i--) {
                if (text.endsWith("(")) {
                    text = equasion.substring(0, i - 1);
                    text += equasion.substring(i);
                    break;
                } else {
                    text = text.substring(0, text.length() - 1);
                }
            }
            return text;
        }
        return equasion;
    }

    private Integer getLengthOfNumber(String display) {
        Integer lengthOfNumber = 0;
        String temp = display;
        do {
            temp = temp.substring(0, temp.length() - 1);
            lengthOfNumber++;
        } while ((!DoesEquasionEndWithOperator(temp) && !temp.equals("")) || temp.endsWith("."));
        return lengthOfNumber;
    }

    private String cutNumberFromEquasion(String equasion, Integer lengthOfNumber) {
        String result = equasion;
        result = result.substring(result.length() - lengthOfNumber);
        return result;
    }
}
