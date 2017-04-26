package com.trimit.android.dagger;

import com.trimit.android.ui.BaseBottomBarActivity;
import com.trimit.android.ui.BottomBarActivity;
import com.trimit.android.ui.LoginActivity;
import com.trimit.android.ui.PasswordResetActivity;
import com.trimit.android.ui.SignupBaseActivity;
import com.trimit.android.ui.WelcomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by artem on 4/21/17.
 */
@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(LoginActivity activity);
    void inject(SignupBaseActivity activity);
    void inject(PasswordResetActivity activity);
    void inject(WelcomeActivity activity);
    void inject(BottomBarActivity activity);
    void inject(BaseBottomBarActivity activity);
}
