package com.android.volley.mytoolbox;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class PostJsonObjectRequest extends JsonObjectRequest {

    private Map<String, String> mParams;

    public PostJsonObjectRequest(String url, Listener<JSONObject> listener,
            ErrorListener errorListener) {
        super(Method.POST, url, null, listener, errorListener);
    }

    public void setParams(Map<String,String> params) {
        mParams = params;
    }

    public void setParams(String key, String value) {
        if (mParams == null) {
            mParams = new HashMap<String, String>();
        }
        mParams.put(key, value);
    };

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }

    /**
     * Returns the raw POST or PUT body to be sent.
     *
     * @throws AuthFailureError in the event of auth failure
     */
    public byte[] getBody() {
        if (mParams != null && mParams.size() > 0) {
            return encodeParameters(mParams, getParamsEncoding());
        }
        return null;
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
}
