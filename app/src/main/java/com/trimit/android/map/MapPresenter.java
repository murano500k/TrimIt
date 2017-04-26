package com.trimit.android.map;

/**
 * Created by artem on 4/25/17.
 */

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mView;

    public MapPresenter(MapContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getData() {

    }

    @Override
    public void cancel() {

    }
}
