package com.example.myafinal.http;

public abstract class AjaxCallBack<T> {

    public void onSuccess(T t) {
    };

    public void onFailure(Throwable t, int errorNo, String strMsg) {
    };
}
