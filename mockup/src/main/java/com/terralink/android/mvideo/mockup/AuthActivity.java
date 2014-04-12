package com.terralink.android.mvideo.mockup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthActivity extends ActionBarMutableActivity implements View.OnClickListener  {

    private Button loginBtn;
    private EditText loginText;
    private EditText passwordText;
    private EditText vehicleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setFinishOnTouchOutside(false);

        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    protected int getActionBarLayout() {
        return R.layout.action_bar_login;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MainDrawerActivity.class));
        //startActivity(new Intent(this, MyActivity.class));
    }
}
