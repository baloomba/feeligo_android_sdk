package com.baloomba.feeligo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {

    public static String getString(JSONObject object, String key) throws JSONException {
        String string = null;
        if (object != null && object.has(key) && !object.isNull(key))
            string = object.getString(key);
        return string;
    }

    public static Object get(JSONObject object, String key) throws JSONException {
        Object obj = null;
        if (object != null && object.has(key) && !object.isNull(key))
            obj = object.get(key);
        return obj;
    }

    public static Boolean getBoolean(JSONObject object, String key) throws JSONException {
        Boolean bool = false;
        if (object != null && object.has(key) && !object.isNull(key))
            bool = object.getBoolean(key);
        return bool;
    }

    public static Double getDouble(JSONObject object, String key) throws JSONException {
        Double d = .0;
        if (object != null && object.has(key) && !object.isNull(key))
            d = object.getDouble(key);
        return d;
    }

    public static JSONArray getJSONArray(JSONObject object, String key) throws JSONException {
        JSONArray jsonArray = null;
        if (object != null && object.has(key) && !object.isNull(key))
            jsonArray = object.getJSONArray(key);
        return jsonArray;
    }

    public static Integer getInt(JSONObject object, String key) throws JSONException {
        Integer integer = 0;
        if (object != null && object.has(key) && !object.isNull(key))
            integer = object.getInt(key);
        return integer;
    }

    public static JSONObject getJSONObject(JSONObject object, String key) throws JSONException {
        JSONObject jsonObject = null;
        if (object != null && object.has(key) && !object.isNull(key))
            jsonObject = object.getJSONObject(key);
        return jsonObject;
    }

    public static Long getLong(JSONObject object, String key) throws JSONException {
        Long l = 0l;
        if (object != null && object.has(key) && !object.isNull(key))
            l = object.getLong(key);
        return l;
    }

}
