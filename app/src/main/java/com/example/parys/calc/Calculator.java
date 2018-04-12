package com.example.parys.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Calculator extends AppCompatActivity {
    @BindView(R.id.equasion)
    TextView equasion;
    String display = "";
    Boolean dotError = false;
    Boolean zeroError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.bksp)
    public void OnBkspClick() {
        if(display.endsWith(".")) {
            dotError = false;
        }
        if (display.length() == 0) {
            zeroError = false;
        } else {
            display = display.substring(0, display.length() - 1);
        }

        updateTextView(display);
    }

    @OnClick(R.id.c)
    public void onCClick() {
        dotError = false;
        zeroError = false;
        display = "";
        updateTextView(display);
    }
    @OnClick(R.id.round)
    public void onRoundClick() {
        if (!doesTextEndsWithOperator(display) && display.length() != 0 && !zeroError) {
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
        } while ((!doesTextEndsWithOperator(number) && !number.equals("")) || number.endsWith("."));
        if (number.length() == 0) {
            number += "(-";
            number += text;
            number += ")";
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
    public void onDotClick() { fillTheList(".", "operator"); }

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
        equasion.setText(text);
    }

    @OnClick(R.id.equal)
    public void onEqualClick() {
        ReversePolishNotation rpn = new ReversePolishNotation();
        if (!display.contains("Dzielenie przez zero!") && !zeroError && display.length() != 0 && !display.startsWith("+")) {
            if (!doesTextEndsWithOperator(display)) {
                display = rpn.compute(display);
                dotError = true;
                if (display.contains("Infinity")) {
                    display = "Dzielenie przez zero!";
                    zeroError = true;
                }
                updateTextView(display);
            }
        }
    }


    boolean doesTextEndsWithOperator(String text) {
        return (text.endsWith("*") || text.endsWith("/") || text.endsWith("-") || text.endsWith("+") || text.endsWith("."));
    }


}
