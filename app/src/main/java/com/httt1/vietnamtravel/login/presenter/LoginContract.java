package com.httt1.vietnamtravel.login.presenter;

import android.widget.EditText;
import android.widget.ImageView;

public interface LoginContract {
    interface View{
        String getPhone();
        String getPass();
        void enableLoginButton(boolean enable);
        void setLoginButtonColor(int color);
        void toRegisActivity();
    }

    interface Presenter{
        void onEyePassClicked(ImageView eye, EditText pass);
        boolean onTextChanged();
        boolean isEmpty(String string);
        void onClickLogin();
    }
}
