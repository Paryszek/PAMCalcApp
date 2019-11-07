package com.example.parys.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.parys.calc.services.CalcValidator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Calculator extends AppCompatActivity {
    @BindView(R.id.equasion)
    TextView equasion;

    String display = "";
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
        if (calcValidator.IsOperator(newDisplay)) {
            Integer plusPos = newDisplay.indexOf("+");
            Integer minusPos = newDisplay.indexOf("-");
            newDisplay =  minusPos != 0 ? (
                    plusPos > minusPos ?
                            ((plusPos > 0 ?
                                    newDisplay.substring(0, plusPos) : "") + newDisplay.substring(plusPos).replaceFirst("\\+", "-"))
                            :
                            ((minusPos > 0 ?
                                    newDisplay.substring(0, minusPos) : "") + newDisplay.substring(minusPos).replaceFirst("-", "+")))
                    : newDisplay.substring(1);
        } else {
            newDisplay = "-" + newDisplay;
        }
        updateTextView(newDisplay);
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
    public void onDotClick() { passInput(".", "operator"); }

    private void passInput(String value, String type) {
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
