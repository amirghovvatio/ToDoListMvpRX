package com.example.mvpgitserverrxjavaadapter;

public interface BasePresenter <T extends BaseView>{
    void onAttach(T view);
    void onDetach();
}
