package com.httt1.vietnamtravel.regis.presenter;

import android.widget.EditText;
import android.widget.ImageView;

public interface RegisContract {
    interface View{
        void showErrorPass(String message);
        void enableRegisButton(boolean enable);
        void setRegisButtonColor(int color);
        String getFullName();
        String getPhone();
        String getPassword();
        String getConfirmPassword();
        void toLoginActivity();
        void checkUser();
    }

    interface Presenter{
        void onEyePassClicked(ImageView eye, EditText pass);
        boolean onTextChanged();
        boolean validatePass(String password, String confirmPass);
        boolean isEmpty(String string);
        void onClickRegis();
    }
}
