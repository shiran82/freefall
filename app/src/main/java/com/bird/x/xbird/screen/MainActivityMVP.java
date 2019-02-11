package com.bird.x.xbird.screen;

import com.bird.x.xbird.model.Fall;

import java.util.List;

public interface MainActivityMVP {
    void showToast(long durationInMS);

    void showFall(Fall fall);

    void showFalls(List<Fall> falls);

    void freeFallState(boolean freeFall, long timestamp);
}
