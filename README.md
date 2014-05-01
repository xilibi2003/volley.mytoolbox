实现了几个volley工具类
1.  CommonRequest: 用来下载小文件,可以是任意格式，如下载zip文件等。
    如： CommonRequest zipRequest = new CommonRequest(url, zipSaveLocation, new Listener<String>() {
            @Override
            public void onResponse(String location) {
                if (DEG) {
                    GLog.d(TAG, "download zip to :" + location);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(zipRequest);

2. GsonRequest: 用来下载Json，并直接获取Gson解析好对象。


3. PostJsonObjectRequest：用post 方式请求Json. 
   有两种来设置参数( 使用时可任选一种)：
   1. setParams(Map<String,String> params); 
   2. setParams(String key, String value);
   如： 
   PostJsonObjectRequest jsObjRequest = new PostJsonObjectRequest(
                URL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
            
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        jsObjRequest.setParams("id", "123456");

		Volley.newRequestQueue(context).add(jsObjRequest);

