package com.httt1.vietnamtravel.login.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.login.presenter.LoginContract;
import com.httt1.vietnamtravel.login.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private EditText etPhone, etPass;
    private ImageView ivEyeHidden;
    private TextView tvForgotPass, tvRegis;
    private Button btnLogin;
    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        loginPresenter = new LoginPresenter(this);
        ivEyeHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.onEyePassClicked(ivEyeHidden, etPass);
            }
        });
        addTextChangedListeners();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.onClickLogin();
            }
        });
    }
    private void init(){
        etPhone = findViewById(R.id.activity_login_et_phone);
        etPass = findViewById(R.id.activity_login_et_pass);
        ivEyeHidden = findViewById(R.id.actvity_login_iv_eye_hiden);
        tvForgotPass = findViewById(R.id.activity_login_tv_forgot_pass);
        tvRegis = findViewById(R.id.activity_login_tv_regis);
        btnLogin = findViewById(R.id.activity_login_btn_login);
    }
    private void addTextChangedListeners() {
        etPhone.addTextChangedListener(createTextWatcher());
        etPass.addTextChangedListener(createTextWatcher());
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPresenter.onTextChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @Override
    public String getPhone() {
        Log.d("ET phone", etPhone.getText().toString());
        return etPhone.getText().toString();
    }

    @Override
    public String getPass() {
        Log.d("ET Pass", etPass.getText().toString());
        return etPass.getText().toString();
    }

    @Override
    public void enableLoginButton(boolean enable) {
        if(enable){
            Log.d("Set enal" , "co vao");}
        btnLogin.setEnabled(enable);
    }

    @Override
    public void setLoginButtonColor(int color) {
        btnLogin.setBackgroundColor(getResources().getColor(color));
    }
}