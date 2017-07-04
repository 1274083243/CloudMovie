package com.ike.commonutils.net.retrofitnetutils.TransformUtils;

import android.text.TextUtils;
import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ike.commonutils.net.retrofitnetutils.model.ExtendsApiResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * author ike
 * create time 10:39 2017/5/21
 * function: 将resonsebody数据转换为ExtendsApiResult<T>数据
 **/

public class TransForm2Clazz<T> implements Func1<ResponseBody, ExtendsApiResult<T>> {
    private Class<T> clazz;
    private String Tag = "TransForm2Clazz";

    public TransForm2Clazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public ExtendsApiResult<T> call(ResponseBody responseBody) {
        Gson gson = new Gson();
        String data = "";
        String msg = "";
        int op = -1;
        int code = -1;
        ExtendsApiResult<T> result = new ExtendsApiResult<T>();
        try {

            String jsonStr = responseBody.string();
            //输出回来的是网页
            if (jsonStr.startsWith("<!doctype html>")){

                result.setData((T) jsonStr);
            }
            else {
                JSONObject jsonObject = JSON.parseObject(jsonStr);
                if (jsonObject.containsKey("data")) {
                    data = jsonObject.getString("data");
                }
                if (jsonObject.containsKey("op")) {
                    op = jsonObject.getInteger("op");
                }
                if (jsonObject.containsKey("message")) {
                    msg = jsonObject.getString("message");
                }
                if (jsonObject.containsKey("code")) {
                    code = jsonObject.getInteger("code");
                }
                if (clazz.equals(String.class)) {
                    result.setData((T) data);
                } else {
                    if (!TextUtils.isEmpty(data)) {
                        T t = gson.fromJson(data, clazz);
                        result.setData(t);
                    }
                }

            }

        } catch (Exception e) {
            Log.e(Tag,"解析出戳了:"+e.getMessage());
            e.printStackTrace();
        } finally {
            responseBody.close();
        }
        result.setOp(op);
        result.setMessage(msg);
        result.setCode(code);
        return result;
    }
}
