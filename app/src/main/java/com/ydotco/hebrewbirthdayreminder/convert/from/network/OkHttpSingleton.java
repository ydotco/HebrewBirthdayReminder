package com.ydotco.hebrewbirthdayreminder.convert.from.network;

import okhttp3.OkHttpClient;

/**
 * Created by yotamc on 14-Aug-16.
 */
public class OkHttpSingleton {
    private static OkHttpSingleton ourInstance = null;

    public OkHttpClient getClient() {
        return client;
    }

    private OkHttpClient client;

    public static OkHttpSingleton getInstance() {
        if (ourInstance==null)
            ourInstance=new OkHttpSingleton();
        return ourInstance;
    }

    private OkHttpSingleton() {
        client=new OkHttpClient();
    }


}
