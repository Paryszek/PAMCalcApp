package com.example.parys.calc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcValidator {
    private Pattern calcPattern;

    public CalcValidator() {
        String O = "[\\+\\-\\*\\^\\/]";
        String C = "\\d+([\\.]\\d+)?([e][-]?\\d+)?";
        String  L = "((" + C + ")|(\\(([\\-])?" + C + "\\)))";
        String W = "((\\(" + L + "(" + O + L + "+" + ")*\\))|" + L + ")";
        String A = "(" + W + "(" + O + W + "+" + ")*)";
        calcPattern = Pattern.compile(String.format("^(%s;(%s;)*)$", A, A));
    }

    public boolean Validate(String value) {
        Matcher calcMatcher = calcPattern.matcher(value);
        return calcMatcher.matches();
    }

}
