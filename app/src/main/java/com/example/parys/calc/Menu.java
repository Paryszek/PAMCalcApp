package com.example.parys.calc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.simple)
    protected void OnSimpleClick() {
        Intent intent = new Intent(this, Calculator.class);
        startActivity(intent);
    }

    @OnClick(R.id.advenced)
    protected void OnAdvencedClick() {
        Intent intent = new Intent(this, ACalculator.class);
        startActivity(intent);
    }

    @OnClick(R.id.about)
    protected void OnAboutClick() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    @OnClick(R.id.exit)
    protected void OnExitClick() {
        finish();
        System.exit(0);
    }

}
