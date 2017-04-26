package com.trimit.android.map;

/**
 * Created by artem on 4/25/17.
 */

public interface MapContract {

    interface View {
        void setPresenter(Presenter presenter);
        void showData();
    }
    interface Presenter {
        void getData();
        void cancel();
    }
}
