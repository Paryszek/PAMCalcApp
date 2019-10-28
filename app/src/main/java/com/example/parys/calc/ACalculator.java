package com.example.parys.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.parys.calc.services.CalcValidator;
import com.example.parys.calc.services.CalculatorHelper;

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
    private CalculatorHelper calcHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acalculator);
        ButterKnife.bind(this);
        calcValidator = new CalcValidator();
        calcHelper = new CalculatorHelper();
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
        passInput("^2", "operator");
        updateTextView(display);
    }

    @OnClick(R.id.xy)
    public void onXYClick() {
        if (display.length() < 1) return;
        passInput("^", "operator");
        updateTextView(display);
    }

    @OnClick(R.id.sin)
    public void onSinClick() {
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display)) {
            String newDisplay = calcHelper.ExecuteMathFunctionOnValue(display, Math::sin);
            updateTextView(newDisplay);
        }
    }

    @OnClick(R.id.cos)
    public void onCosClick() {
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display)) {
            String newDisplay = calcHelper.ExecuteMathFunctionOnValue(display, Math::cos);
            updateTextView(newDisplay);
        }
    }




    @OnClick(R.id.tan)
    public void onTanClick() {
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display)) {
            String newDisplay = calcHelper.ExecuteMathFunctionOnValue(display, Math::tan);
            updateTextView(newDisplay);
        }
    }

    @OnClick(R.id.ln)
    public void onLnClick() {
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display) && isPositive(display)) {
            String newDisplay = calcHelper.ExecuteMathFunctionOnValue(display, Math::log);
            updateTextView(newDisplay);
        }
    }

    @OnClick(R.id.sqrt)
    public void onSqrtClick() {
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display) && isPositive(display)) {
            String newDisplay = calcHelper.ExecuteMathFunctionOnValue(display, Math::sqrt);
            updateTextView(newDisplay);
        }
    }

    private boolean isPositive(String dis) {
        String text = dis;
        boolean toReturn = true;
        while (text.length() > 0) {
            if (calcHelper.DoesEquasionEndWithOperator(text)) {
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
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display) && isPositive(display)) {
            String newDisplay = calcHelper.ExecuteMathFunctionOnValue(display, Math::log);
            updateTextView(newDisplay);
        }
    }


    @OnClick(R.id.round)
    public void onRoundClick() {
        if (!calcHelper.DoesEquasionEndWithOperator(display) && display.length() != 0 && !zeroError && !display.endsWith("^")) {
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
        while ((!calcHelper.DoesEquasionEndWithOperator(number) && !number.equals("") && !number.endsWith("^")) || number.endsWith("."));
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
            if (calcHelper.DoesEquasionEndWithOperator(temp) || temp.length() == 0) {
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
            if (calcHelper.DoesEquasionEndWithOperator(temp) && !temp.endsWith(".")) {
                amount++;
            }
            temp = temp.substring(0,temp.length() - 1);
        } while (temp.length() != 0);
        return amount;
    }

    @OnClick(R.id.zero)
    public void onZeroClick() {
        passInput("0", "number");
    }

    @OnClick(R.id.one)
    public void onOneClick() {
        passInput("1", "number");
    }

    @OnClick(R.id.two)
    public void onTwoClick() {
        passInput("2", "number");
    }

    @OnClick(R.id.three)
    public void onThreeClick() {
        passInput("3", "number");
    }

    @OnClick(R.id.four)
    public void onFourClick() {
        passInput("4", "number");
    }

    @OnClick(R.id.five)
    public void onFiveClick() {
        passInput("5", "number");
    }

    @OnClick(R.id.six)
    public void onSixClick() {
        passInput("6", "number");
    }

    @OnClick(R.id.seven)
    public void onSevenClick() {
        passInput("7", "number");
    }

    @OnClick(R.id.eight)
    public void onEightClick() {
        passInput("8", "number");
    }

    @OnClick(R.id.nine)
    public void onNineClick() {
        passInput("9", "number");
    }

    @OnClick(R.id.plus)
    public void onPlusClick() {
        passInput("+", "operator");
    }

    @OnClick(R.id.minus)
    public void onMinusClick() {
        passInput("-", "operator");
    }

    @OnClick(R.id.divide)
    public void onDivideClick() {
        passInput("/", "operator");
    }

    @OnClick(R.id.multi)
    public void onMultiClick() {
        passInput("*", "operator");
    }

    @OnClick(R.id.dot)
    public void onDotClick() {
        if (!display.endsWith(")") && !display.contains("^")) {
            passInput(".", "operator");
        }
    }

    private void passInput(String value, String type) {
        clearErrors(value, type);
        if (isDotError(value) || zeroError) {
            value = "";
        }
        if (display.length() != 0 || !type.equals("operator")) {
            if (!calcHelper.DoesEquasionEndWithOperator(display) || type.equals("number")) {
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
}
