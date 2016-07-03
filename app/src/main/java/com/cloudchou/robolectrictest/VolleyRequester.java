package com.cloudchou.robolectrictest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * date 2016/7/3
 *
 * @author Cloud
 * @version 1.1
 * @since Ver 1.1
 */
public class VolleyRequester {
    private static final String TAG = VolleyRequester.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private Context mContext;
    private volatile String mResponseStr;

    public void request(Context context) {
        mContext = context;
        RequestQueue volleyRequestQueue = getVolleyRequestQueue(mContext);
        String url = "http://www.mocky.io/v2/5597d86a6344715505576725";
        Response.Listener<String> dataListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("received response. ");
                mResponseStr = response;
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SLog.e(TAG, "request failed");
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, dataListener, errorListener);
        volleyRequestQueue.add(request);
    }

    public String getResponseString() {
        return mResponseStr;
    }

    private RequestQueue getVolleyRequestQueue(Context ctx) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ctx, new OkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    private static class OkHttpStack extends HurlStack {
        private OkHttpClient mOkHttpClient;

        public OkHttpStack(OkHttpClient okHttpClient) {
            mOkHttpClient = okHttpClient;
        }

        @Override
        protected HttpURLConnection createConnection(URL url) throws IOException {
            OkUrlFactory factory = new OkUrlFactory(mOkHttpClient);
            return factory.open(url);
        }
    }
}
