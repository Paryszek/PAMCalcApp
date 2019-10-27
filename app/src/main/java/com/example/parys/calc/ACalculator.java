package com.example.parys.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ACalculator extends AppCompatActivity {
    @BindView(R.id.equasion)
    TextView equasion;
    CalcValidator calcValidator;
    String display = "";
    Boolean dotError = false;
    Boolean zeroError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acalculator);
        ButterKnife.bind(this);
        calcValidator = new CalcValidator();
    }
    @OnClick(R.id.bksp)
    public void OnBkspClick() {
        display = display.substring(0, display.length() - 1);
        updateTextView(display);
    }

    @OnClick(R.id.c)
    public void onCClick() {
        display = "";
        updateTextView(display);
    }

    @OnClick(R.id.x2)
    public void onX2Click() {
        if (display.length() < 1) return;
        fillTheList("^2", "operator");
        updateTextView(display);
    }

    @OnClick(R.id.xy)
    public void onXYClick() {
        if (display.length() < 1) return;
        fillTheList("^", "operator");
        updateTextView(display);
    }

//    private String getNumber(String diss) {
//        String dis = diss;
//        boolean getNumber = false;
//        String number = "";
//        while (dis.length() != 0) {
//            if (dis.endsWith("^")) {
//                getNumber = true;
//            }
//            dis = dis.substring(0, dis.length() - 1);
//            if (getNumber) {
//                if (dis.endsWith(")")) {
//                    dis = removeBrackets(dis);
//                    isBrackets = true;
//                }
//                if (dis.length() != 0) {
//                    number = dis;
//                    if (Double.parseDouble(number) < 0) {
//                        isNegative = true;
//                    }
//                    break;
//                }
//            }
//        }
//        return number;
//    }
//
//    private String getPower(String dis) {
//        String power = "";
//        while (dis.length() != 0) {
//            if(dis.endsWith("^"))
//                break;
//            power += dis.substring(dis.length() - 1, dis.length());
//            dis = dis.substring(0, dis.length() - 1);
//        }
//        power = reverseString(power);
//        return power;
//    }
//
//    private String reverseString(String input) {
//        StringBuilder output = new StringBuilder();
//        output.append(input);
//        output = output.reverse();
//        return output.toString();
//    }

    @OnClick(R.id.sin)
    public void onSinClick() {
        if(display.length() != 0 && !doesTextEndsWithOperator(display)) {
            String newDisplay = executeMathFunctionOnValue(display, Math::sin);
            updateTextView(newDisplay);
        }
    }

    @OnClick(R.id.cos)
    public void onCosClick() {
        if(display.length() != 0 && !doesTextEndsWithOperator(display)) {
            String newDisplay = executeMathFunctionOnValue(display, Math::cos);
            updateTextView(newDisplay);
        }
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


    @OnClick(R.id.tan)
    public void onTanClick() {
        if(display.length() != 0 && !doesTextEndsWithOperator(display)) {
            String newDisplay = executeMathFunctionOnValue(display, Math::tan);
            updateTextView(newDisplay);
        }
    }

    @OnClick(R.id.ln)
    public void onLnClick() {
        if(display.length() != 0 && !doesTextEndsWithOperator(display) && isPositive(display)) {
            String newDisplay = executeMathFunctionOnValue(display, Math::log);
            updateTextView(newDisplay);
        }
    }

    private String executeMathFunctionOnValue(String display, Function<Double, Double> func) {
        String newDisplay = removeBrackets(display);
        Integer lengthOfNumber = getLengthOfNumber(newDisplay);
        String number = cutNumberFromEquasion(newDisplay, lengthOfNumber);

        Double nbr = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            nbr = func.apply(Double.parseDouble(number));
        }
        number = nbr.toString();

        newDisplay = newDisplay.substring(0, newDisplay.length() - lengthOfNumber);
        newDisplay += number;
        return newDisplay;
    }

    private Integer getLengthOfNumber(String display) {
        Integer lengthOfNumber = 0;
        String temp = display;
        do {
            temp = temp.substring(0, temp.length() - 1);
            lengthOfNumber++;
        } while ((!doesTextEndsWithOperator(temp) && !temp.equals("")) || temp.endsWith("."));
        return lengthOfNumber;
    }

    private String cutNumberFromEquasion(String equasion, Integer lengthOfNumber) {
        String result = equasion;
        result = display.substring(display.length() - lengthOfNumber);
        return result;
    }

    @OnClick(R.id.sqrt)
    public void onSqrtClick() {
        if(display.length() != 0 && !doesTextEndsWithOperator(display) && isPositive(display)) {
            String newDisplay = executeMathFunctionOnValue(display, Math::sqrt);
            updateTextView(newDisplay);
        }
    }

    private boolean isPositive(String dis) {
        String text = dis;
        boolean toReturn = true;
        while (text.length() > 0) {
            if (doesTextEndsWithOperator(text)) {
                if (text.endsWith("-")) {
                    toReturn = false;
                }
            }
            text = text.substring(0, text.length() - 1);
        }
        return toReturn;
    }

    @OnClick(R.id.log)
    public void onLogClick() {
        if(display.length() != 0 && !doesTextEndsWithOperator(display) && isPositive(display)) {
            String newDisplay = executeMathFunctionOnValue(display, Math::log);
            updateTextView(newDisplay);
        }
    }


    @OnClick(R.id.round)
    public void onRoundClick() {
        if (!doesTextEndsWithOperator(display) && display.length() != 0 && !zeroError && !display.endsWith("^")) {
            display = changeTheOperator(display);
            updateTextView(display);
        }
    }

    String changeTheOperator(String text) {
        String number = text;
        int amountOfOperators = countTheOperators(text);
        int amountOfSeperateNumbers = countTheNumbers(text);
        int lengthOfNumbers = 0;
        do {
            number = number.substring(0, number.length() - 1);
            lengthOfNumbers++;
        }
        while ((!doesTextEndsWithOperator(number) && !number.equals("") && !number.endsWith("^")) || number.endsWith("."));
        if (number.length() == 0) {
            number += "(-";
            number += text;
            number += ")";
        } else if (text.endsWith(")")) {
            String operator = number.substring(number.length() - 1, number.length());
            if (operator.equals("-")) {
                number = text.substring(0, text.length() - lengthOfNumbers - 2);
                number += text.substring(text.length() - lengthOfNumbers, text.length() - 1);
            }
        } else {
            String operator = number.substring(number.length() - 1, number.length());
            if (operator.equals("+")) {
                number = text.substring(0, text.length() - lengthOfNumbers - 1);
                number += "-";
                number += text.substring(text.length() - lengthOfNumbers);
            } else if (operator.equals("-")) {
                if (amountOfOperators > 1 && amountOfSeperateNumbers > 1) {
                    number = text.substring(0, text.length() - lengthOfNumbers - 1);
                    number += "+";
                    number += text.substring(text.length() - lengthOfNumbers);
                } else {
                    if (text.startsWith("(")) {
                        number = text.substring(2, text.length() - 1);
                    } else {
                        if (amountOfSeperateNumbers >= 1) {
                            number = text.substring(0, text.length() - lengthOfNumbers - 1);
                            number += "+";
                            number += text.substring(text.length() - lengthOfNumbers);
                        } else {
                            number = text.substring(1, text.length());
                        }
                    }
                }
            } else if (operator.equals("^")) {
                number = text.substring(0, text.length() - lengthOfNumbers);
                number += "(-";
                number += text.substring(text.length() - lengthOfNumbers);
                number += ")";
            } else {
                number = text;
            }
        }
        return number;
    }

    private int countTheNumbers(String text) {
        String temp = text;
        int amount = 0;
        do {
            temp = temp.substring(0, temp.length() - 1);
            if (doesTextEndsWithOperator(temp) || temp.length() == 0) {
                if (!temp.endsWith(".") && temp.length() != 0) {
                    amount++;
                }
            }
        } while (temp.length() != 0);
        return amount;
    }

    private int countTheOperators(String text) {
        int amount = 0;
        String temp = text;
        do {
            if (doesTextEndsWithOperator(temp) && !temp.endsWith(".")) {
                amount++;
            }
            temp = temp.substring(0,temp.length() - 1);
        } while (temp.length() != 0);
        return amount;
    }

    @OnClick(R.id.zero)
    public void onZeroClick() {
        fillTheList("0", "number");
    }

    @OnClick(R.id.one)
    public void onOneClick() {
        fillTheList("1", "number");
    }

    @OnClick(R.id.two)
    public void onTwoClick() {
        fillTheList("2", "number");
    }

    @OnClick(R.id.three)
    public void onThreeClick() {
        fillTheList("3", "number");
    }

    @OnClick(R.id.four)
    public void onFourClick() {
        fillTheList("4", "number");
    }

    @OnClick(R.id.five)
    public void onFiveClick() {
        fillTheList("5", "number");
    }

    @OnClick(R.id.six)
    public void onSixClick() {
        fillTheList("6", "number");
    }

    @OnClick(R.id.seven)
    public void onSevenClick() {
        fillTheList("7", "number");
    }

    @OnClick(R.id.eight)
    public void onEightClick() {
        fillTheList("8", "number");
    }

    @OnClick(R.id.nine)
    public void onNineClick() {
        fillTheList("9", "number");
    }

    @OnClick(R.id.plus)
    public void onPlusClick() {
        fillTheList("+", "operator");
    }

    @OnClick(R.id.minus)
    public void onMinusClick() {
        fillTheList("-", "operator");
    }

    @OnClick(R.id.divide)
    public void onDivideClick() {
        fillTheList("/", "operator");
    }

    @OnClick(R.id.multi)
    public void onMultiClick() {
        fillTheList("*", "operator");
    }

    @OnClick(R.id.dot)
    public void onDotClick() {
        if (!display.endsWith(")") && !display.contains("^")) {
            fillTheList(".", "operator");
        }
    }

    private void fillTheList(String value, String type) {
        clearErrors(value, type);
        if (isDotError(value) || zeroError) {
            value = "";
        }
        if (display.length() != 0 || !type.equals("operator")) {
            if (!doesTextEndsWithOperator(display) || type.equals("number")) {
                setErrors(value);
                if (value.equals(".")) {
                    dotError = true;
                }
                display += value;
                updateTextView(display);
            }
        }
    }

    private void setErrors(String value) {
        if (value.equals(".")) {
            dotError = true;
        }
    }

    private boolean isDotError(String value) {
        return dotError && value.equals(".");
    }

    private void clearErrors(String value, String type) {
        if (dotError && type.equals("operator") && !value.equals(".")) {
            dotError = false;
        }
    }

    private void updateTextView(String text) {
        display = text;
        equasion.setText(display);
    }

    @OnClick(R.id.equal)
    public void onEqualClick() {
        ReversePolishNotation rpn = new ReversePolishNotation();

        if (!calcValidator.Validate(display)) return;

        display = rpn.compute(display);
        dotError = true;
        updateTextView(display);

    }

    boolean doesTextEndsWithOperator(String text) {
        return (text.endsWith("*") || text.endsWith("/") || text.endsWith("-") || text.endsWith("+") || text.endsWith(".") || text.endsWith("E-") || text.endsWith("E"));
    }


}
