package com.httt1.vietnamtravel.login.presenter;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.login.model.LoginModel;
import com.httt1.vietnamtravel.login.model.LoginRepository;

public class LoginPresenter implements LoginContract.Presenter{
    private final LoginContract.View view;
    private final LoginRepository loginRepositor;
    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.loginRepositor = new LoginRepository();
    }

    @Override
    public void onEyePassClicked(ImageView eye, EditText pass) {
        if (eye.getTag() == null || (int)eye.getTag() == R.mipmap.icon_eye_hidden){
            eye.setImageResource(R.mipmap.activity_regis_icon_eye_show);
            eye.setTag(R.mipmap.activity_regis_icon_eye_show);
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            eye.setImageResource(R.mipmap.icon_eye_hidden);
            eye.setTag(R.mipmap.icon_eye_hidden);
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        pass.requestFocus();
        pass.clearFocus();
    }

    @Override
    public boolean onTextChanged() {
        if (isEmpty(view.getPhone()) || isEmpty(view.getPass()) ){
            boolean isVaild = false;
            view.enableLoginButton(isVaild);
            view.setLoginButtonColor(isVaild ? R.color.regis_after : R.color.regis_before);
            return false;
        }else{
                boolean isVaild = true;
                view.enableLoginButton(isVaild);
                view.setLoginButtonColor(isVaild ? R.color.regis_after : R.color.regis_before);
                return true;
        }
    }

    @Override
    public boolean isEmpty(String string) {
        return (string.isEmpty());
    }

    @Override
    public void onClickLogin() {
        if (onTextChanged()){
            LoginModel loginModel = new LoginModel(view.getPhone(), view.getPass());
            loginRepositor.login(loginModel, new LoginRepository.LoginCallBack() {
                @Override
                public void onResult(boolean success) {
                    if (success){
                        Log.d("Dang Nhap", "Dang nhapo thanh cong");
                    }else{
                        Log.d("Dang nhap", "That bai");
                    }
                }
            });
        }
    }
}
