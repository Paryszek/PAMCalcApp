package com.example.parys.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.parys.calc.services.CalcValidator;
import com.example.parys.calc.services.ReversePolishNotation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Calculator extends AppCompatActivity {
    @BindView(R.id.equasion)
    TextView equasion;

    String display = "";
    Boolean lastDot = false;
    CalcValidator calcValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);
        calcValidator = new CalcValidator();
    }

    @OnClick(R.id.bksp)
    public void OnBkspClick() {
        updateTextView(display.substring(0, display.length() - 1));
    }

    @OnClick(R.id.c)
    public void onCClick() {
        updateTextView("");
    }

    @OnClick(R.id.round)
    public void onRoundClick() {
        String newDisplay = display;
        Integer plusPos = newDisplay.indexOf("+");
        Integer minusPos = newDisplay.indexOf("-");
        if (!calcValidator.IsOperator(newDisplay) || (plusPos == -1 && minusPos == -1)) {
            newDisplay = "-" + newDisplay;
            updateTextView(newDisplay);
            return;
        }

        if (calcValidator.CountOperators(newDisplay) == 1 && plusPos != -1) {
            newDisplay = newDisplay.replaceFirst("\\+", "-");
            updateTextView(newDisplay);
            return;
        }

        if (calcValidator.CountOperators(newDisplay) == 1 && minusPos != -1) {
            newDisplay = newDisplay.replaceFirst("-", "");
            updateTextView(newDisplay);
            return;
        }

        if (calcValidator.CountOperators(newDisplay) > 1 && (plusPos > -1 || minusPos > -1)) {
            newDisplay = new StringBuilder(newDisplay).reverse().toString();
            plusPos = newDisplay.indexOf("+");
            minusPos = newDisplay.indexOf("-");
            if ((plusPos >  minusPos && minusPos > -1) || (plusPos == -1 && minusPos > -1)) {
                newDisplay = (minusPos > 0 ? newDisplay.substring(0, minusPos) : "") + newDisplay.substring(minusPos).replaceFirst("-", "+");
            } else if ((minusPos > plusPos && plusPos > -1) || (minusPos == -1 && plusPos > -1)) {
                newDisplay = (plusPos > 0 ? newDisplay.substring(0, plusPos) : "") + newDisplay.substring(plusPos).replaceFirst("\\+", "-");
            }
            updateTextView(new StringBuilder(newDisplay).reverse().toString());
        }
    }

    @OnClick(R.id.zero)
    public void onZeroClick() { passInput("0"); }

    @OnClick(R.id.one)
    public void onOneClick() {
        passInput("1");
    }

    @OnClick(R.id.two)
    public void onTwoClick() {
        passInput("2");
    }

    @OnClick(R.id.three)
    public void onThreeClick() {
        passInput("3");
    }

    @OnClick(R.id.four)
    public void onFourClick() {
        passInput("4");
    }

    @OnClick(R.id.five)
    public void onFiveClick() {
        passInput("5");
    }

    @OnClick(R.id.six)
    public void onSixClick() {
        passInput("6");
    }

    @OnClick(R.id.seven)
    public void onSevenClick() {
        passInput("7");
    }

    @OnClick(R.id.eight)
    public void onEightClick() {
        passInput("8");
    }

    @OnClick(R.id.nine)
    public void onNineClick() {
        passInput("9");
    }

    @OnClick(R.id.plus)
    public void onPlusClick() {
        passInput("+");
    }

    @OnClick(R.id.minus)
    public void onMinusClick() {
        passInput("-");
    }

    @OnClick(R.id.divide)
    public void onDivideClick() {
        passInput("/");
    }

    @OnClick(R.id.multi)
    public void onMultiClick() {
        passInput("*");
    }

    @OnClick(R.id.dot)
    public void onDotClick() { passInput("."); }

    private void passInput(String value) {
        if (lastDot && value.equals(".")) return;
        lastDot = value.equals(".");
        String newDisplay = display + value;
        updateTextView(newDisplay);
    }

    private void updateTextView(String text) {
        display = text;
        equasion.setText(display);
    }

    @OnClick(R.id.equal)
    public void onEqualClick() {
        ReversePolishNotation rpn = new ReversePolishNotation();

        if (!calcValidator.Validate(display)) return;

        String newDisplay = rpn.compute(display);
        updateTextView(newDisplay);
    }
}
