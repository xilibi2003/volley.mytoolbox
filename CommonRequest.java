package com.android.volley.mytoolbox;

import java.io.FileOutputStream;
import java.io.OutputStream;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;


public class CommonRequest extends Request<String> {
    /** Socket timeout in milliseconds for image requests */
    private static final int TIMEOUT_MS = 2500;

    /** Default number of retries for zip requests */
    private static final int MAX_RETRIES = 2;

    /** Default backoff multiplier for image requests */
    private static final float BACKOFF_MULT = 2f;
    
    private final Listener<String> mListener;
    private String mSaveLocation;

    public CommonRequest(String url, String saveLoaction, Listener<String> listener,
            ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
        mSaveLocation = saveLoaction;
        setRetryPolicy(
                new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT));
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            OutputStream output = new FileOutputStream(mSaveLocation);
            byte[] data = response.data;
            output.write(data, 0, data.length);
            output.close();
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }

        return Response.success(mSaveLocation, HttpHeaderParser.parseCacheHeaders(response));
    }


    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
