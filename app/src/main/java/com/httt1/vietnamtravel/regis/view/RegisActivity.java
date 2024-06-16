package com.httt1.vietnamtravel.regis.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.login.view.LoginActivity;
import com.httt1.vietnamtravel.regis.presenter.RegisContract;
import com.httt1.vietnamtravel.regis.presenter.RegisPresenter;

public class RegisActivity extends AppCompatActivity implements RegisContract.View {
    EditText etFullName, etPhone, etPass, etConfirmPass;
    TextView tvError, tvLogin;
    ImageView ivEyePass, ivEyeConfirmPass;
    Button btnRegis;
    RegisPresenter regisPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        regisPresenter = new RegisPresenter(this);

        ivEyePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regisPresenter.onEyePassClicked(ivEyePass, etPass);
            }
        });

        ivEyeConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regisPresenter.onEyePassClicked(ivEyeConfirmPass,etConfirmPass);
            }
        });

        addTextChangedListeners();

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regisPresenter.onClickRegis();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginActivity();
            }
        });
    }

    private void init(){
        etFullName = findViewById(R.id.activity_regis_et_full_name);
        etPhone = findViewById(R.id.activity_regis_et_phone);
        etPass = findViewById(R.id.activity_regis_et_pass);
        etConfirmPass = findViewById(R.id.activity_regis_et_confirm_pass);
        tvError = findViewById(R.id.activity_regis_tv_error);
        tvLogin = findViewById(R.id.activity_regis_tv_login);
        ivEyePass = findViewById(R.id.activity_regis_iv_pass_eye_hiden);
        ivEyeConfirmPass = findViewById(R.id.activity_regis_iv_confirm_eye_hiden);
        btnRegis = findViewById(R.id.activity_regis_btn_regis);
    }

    private void addTextChangedListeners() {
        etFullName.addTextChangedListener(createTextWatcher());
        etPhone.addTextChangedListener(createTextWatcher());
        etPass.addTextChangedListener(createTextWatcher());
        etConfirmPass.addTextChangedListener(createTextWatcher());
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                regisPresenter.onTextChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @Override
    public void showErrorPass(String message) {
        tvError.setText(message);
    }

    @Override
    public void enableRegisButton(boolean enable) {
        btnRegis.setEnabled(enable);
    }

    @Override
    public void setRegisButtonColor(int color) {
        btnRegis.setBackgroundColor(getResources().getColor(color));
    }
    @Override
    public String getFullName() {
        return etFullName.getText().toString();
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPass.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return etConfirmPass.getText().toString();
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(RegisActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}