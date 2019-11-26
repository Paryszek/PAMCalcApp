package com.example.parys.calc.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcValidator {
    private Pattern calcPattern;
    private Pattern operatorPattern;

    public CalcValidator() {
        String O = "[\\+\\-\\*\\^\\/]";
        String C = "\\d+([\\.]\\d+)?([e][-]?\\d+)?";
        String  L = "((([\\-])?" + C + ")|(\\(([\\-])?" + C + "\\)))";
        String W = "((\\(" + L + "(" + O + L + "+" + ")*\\))|" + L + ")";
        String A = "(" + W + "(" + O + W + "+" + ")*)";
        calcPattern = Pattern.compile(String.format("^(%s(%s)*)$", A, A));
        operatorPattern = Pattern.compile(String.format(".*?%s.*?", O));
    }

    public boolean Validate(String value) {
        Matcher calcMatcher = calcPattern.matcher(value.toLowerCase());
        return calcMatcher.matches();
    }

    public boolean IsOperator(String value) {
        Matcher operatorMatcher = operatorPattern.matcher(value.toLowerCase());
        return operatorMatcher.matches();
    }

    public int CountOperators(String value) {
        Matcher operatorMatcher = operatorPattern.matcher(value.toLowerCase());

        int count = 0;
        while (operatorMatcher.find())
            count++;
        return count;
    }
}
