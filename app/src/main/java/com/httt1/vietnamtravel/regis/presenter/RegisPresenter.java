package com.httt1.vietnamtravel.regis.presenter;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.login.view.LoginActivity;
import com.httt1.vietnamtravel.regis.model.RegisModel;
import com.httt1.vietnamtravel.regis.model.RegisRepository;
import com.httt1.vietnamtravel.regis.view.RegisActivity;

public class RegisPresenter implements RegisContract.Presenter{
    private final RegisContract.View view;
    private final RegisRepository regisRepository;
    public RegisPresenter(RegisContract.View view){
        this.view = view;
        this.regisRepository = new RegisRepository();
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
        if (isEmpty(view.getFullName()) || isEmpty(view.getPhone()) || isEmpty(view.getPassword()) || isEmpty(view.getConfirmPassword())){
            boolean isVaild = false;
            view.enableRegisButton(isVaild);
            view.setRegisButtonColor(isVaild ? R.color.regis_after : R.color.regis_before);
            return false;
        }else {
            if(validatePass(view.getPassword(), view.getConfirmPassword())){
                boolean isVaild = true;
                view.enableRegisButton(isVaild);
                view.setRegisButtonColor(isVaild ? R.color.regis_after : R.color.regis_before);
                return true;
            }else{
                boolean isVaild = false;
                view.enableRegisButton(isVaild);
                view.setRegisButtonColor(isVaild ? R.color.regis_after : R.color.regis_before);
                return false;
            }
        }
    }
    @Override
    public boolean validatePass(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            view.showErrorPass("Mật khẩu không khớp!");
            return false;
        } else {
            view.showErrorPass(null);
            return true;
        }
    }
    @Override
    public boolean isEmpty(String string) {
        return (string.isEmpty());
    }

    @Override
    public void onClickRegis() {
        if (onTextChanged()){
            regisRepository.CheckUser(view.getPhone(), new RegisRepository.CheckUserCallBack() {
                @Override
                public void onCheckUser(boolean success) {
                    if (success){
                        view.checkUser();
                    }
                    else {
                        RegisModel regisModel = new RegisModel(view.getFullName(),view.getPhone(), view.getPassword());
                        regisRepository.User(regisModel);
                        view.toLoginActivity();
                    }
                }
            });
        }
    }
}
