package com.trimit.android.dagger;

import com.trimit.android.ui.BaseActivity;
import com.trimit.android.ui.BottomBarActivity;
import com.trimit.android.ui.LoginActivity;
import com.trimit.android.ui.PasswordResetActivity;
import com.trimit.android.ui.WelcomeActivity;
import com.trimit.android.ui.signup.SignupActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by artem on 4/21/17.
 */
@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(LoginActivity activity);
    void inject(SignupActivity activity);
    void inject(PasswordResetActivity activity);
    void inject(WelcomeActivity activity);
    void inject(BottomBarActivity activity);
    void inject(BaseActivity activity);
}
