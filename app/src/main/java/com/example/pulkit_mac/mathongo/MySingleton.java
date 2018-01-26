package com.example.pulkit_mac.mathongo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by pulkit-mac on 26/01/18.
 */

public class MySingleton {

    private static MySingleton mySingleton;
    private static Context context;
    private RequestQueue requestQueue;

    public MySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {

        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            return requestQueue;
    }

    public static synchronized MySingleton mySingleton(Context context){

        if(mySingleton == null)
            mySingleton = new MySingleton(context);
        return  mySingleton;
    }

    public <T>void addtoRequestQue(Request<T> request){

        getRequestQueue().add(request);
    }
}
