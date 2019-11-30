package com.example.parys.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.parys.calc.services.CalcValidator;
import com.example.parys.calc.services.CalculatorHelper;
import com.example.parys.calc.services.ReversePolishNotation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ACalculator extends AppCompatActivity {
    @BindView(R.id.equasion)
    TextView equasion;
    CalcValidator calcValidator;
    Boolean lastDot = false;
    String display = "";
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
        display = display.length() > 0 ? display.substring(0, display.length() - 1) : display;
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
        passInput("^2");
        updateTextView(display);
    }

    @OnClick(R.id.xy)
    public void onXYClick() {
        if (display.length() < 1) return;
        passInput("^");
        updateTextView(display);
    }

    @OnClick(R.id.sin)
    public void onSinClick() {
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display)) {
            String newDisplay = calcHelper.ExecuteMathTrigonometryFunctionOnValue(display, Math::sin);
            updateTextView(newDisplay);
        }
    }

    @OnClick(R.id.cos)
    public void onCosClick() {
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display)) {
            String newDisplay = calcHelper.ExecuteMathTrigonometryFunctionOnValue(display, Math::cos);
            updateTextView(newDisplay);
        }
    }

    @OnClick(R.id.tan)
    public void onTanClick() {
        if(display.length() != 0 && !calcHelper.DoesEquasionEndWithOperator(display)) {
            String newDisplay = calcHelper.ExecuteMathTrigonometryFunctionOnValue(display, Math::tan);
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
            String newDisplay = calcHelper.ExecuteMathFunctionOnValue(display, Math::log10);
            updateTextView(newDisplay);
        }
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
    public void onZeroClick() {
        passInput("0");
    }

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

        display = rpn.compute(display);
        updateTextView(display);

    }
}
