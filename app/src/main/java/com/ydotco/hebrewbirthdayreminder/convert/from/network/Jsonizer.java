package com.ydotco.hebrewbirthdayreminder.convert.from.network;

import com.ydotco.hebrewbirthdayreminder.HebEngDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yotamc on 14-Aug-16.
 */
public class Jsonizer {

    HebEngDate hebEngDate = null;

    public ArrayList<Date> getList() {
        return list;
    }

    OkHttpClient client = null;
    ArrayList<Date> list = null;
    String curHebYear = "5776";
    int i = 0;

    public Jsonizer() {
        hebEngDate = new HebEngDate();
        client = OkHttpSingleton.getInstance().getClient();
        list = new ArrayList<>();
    }

    public void makeHebEngRequest(String url) {

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    hebEngDate.setGy(Integer.parseInt(jsonObject.get("gy").toString()));
                    hebEngDate.setGd(Integer.parseInt(jsonObject.get("gd").toString()));
                    hebEngDate.setGm(Integer.parseInt(jsonObject.get("gm").toString()));
                    hebEngDate.setHy(Integer.parseInt(jsonObject.get("hy").toString()));
                    hebEngDate.setHm(jsonObject.get("hm").toString());
                    hebEngDate.setHd(Integer.parseInt(jsonObject.get("hd").toString()));
                    //add birthday to contact
                    convertAll();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }


    public void convertAll() {
        i = 0;
        String url0 = "http://www.hebcal.com/converter/?cfg=json&hy=" + String.valueOf(Integer.valueOf(curHebYear))
                + "&hm=" + hebEngDate.getHm() + "&hd=" + hebEngDate.getHd() + "&h2g=1";
        String url1 = "http://www.hebcal.com/converter/?cfg=json&hy=" + String.valueOf(Integer.valueOf(curHebYear) + 1)
                + "&hm=" + hebEngDate.getHm() + "&hd=" + hebEngDate.getHd() + "&h2g=1";
        String url2 = "http://www.hebcal.com/converter/?cfg=json&hy=" + String.valueOf(Integer.valueOf(curHebYear) + 2)
                + "&hm=" + hebEngDate.getHm() + "&hd=" + hebEngDate.getHd() + "&h2g=1";
        String url3 = "http://www.hebcal.com/converter/?cfg=json&hy=" + String.valueOf(Integer.valueOf(curHebYear) + 3)
                + "&hm=" + hebEngDate.getHm() + "&hd=" + hebEngDate.getHd() + "&h2g=1";
        String url4 = "http://www.hebcal.com/converter/?cfg=json&hy=" + String.valueOf(Integer.valueOf(curHebYear) + 4)
                + "&hm=" + hebEngDate.getHm() + "&hd=" + hebEngDate.getHd() + "&h2g=1";


        Request request0 = new Request.Builder().url(url0).build();
        Request request1 = new Request.Builder().url(url1).build();
        Request request2 = new Request.Builder().url(url2).build();
        Request request3 = new Request.Builder().url(url3).build();
        Request request4 = new Request.Builder().url(url4).build();
        getJsonF(request0);
        getJsonF(request1);
        getJsonF(request2);
        getJsonF(request3);
        getJsonF(request4);

    }


    private void getJsonF(Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    hebEngDate.setGy(Integer.parseInt(jsonObject.get("gy").toString()));
                    hebEngDate.setGd(Integer.parseInt(jsonObject.get("gd").toString()));
                    hebEngDate.setGm(Integer.parseInt(jsonObject.get("gm").toString()));
                    hebEngDate.setHy(Integer.parseInt(jsonObject.get("hy").toString()));
                    hebEngDate.setHm(jsonObject.get("hm").toString());
                    hebEngDate.setHd(Integer.parseInt(jsonObject.get("hd").toString()));
                    list.add(hebEngDate.makeDate());
                    i++;
                    if (i == 5) {
                        SortList(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void SortList(ArrayList<Date> list) {
        Collections.sort(list);
    }

    public HebEngDate getHebEngDate() {
        return hebEngDate;
    }

}
