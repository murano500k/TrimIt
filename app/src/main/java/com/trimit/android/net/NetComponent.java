package com.trimit.android.net;

import com.trimit.android.LoginActivity;
import com.trimit.android.PasswordResetActivity;
import com.trimit.android.SignupBaseActivity;
import com.trimit.android.dagger.AppModule;

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
}
