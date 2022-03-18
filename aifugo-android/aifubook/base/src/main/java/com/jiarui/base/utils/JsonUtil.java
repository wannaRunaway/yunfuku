package com.jiarui.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jiarui.base.db.service.UserDataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class JsonUtil {

    public static <T> T getObject(String jsonString, Class<T> cls) {
        return JSON.parseObject(jsonString, cls);
    }

    public static <T> List<T> getObjects(String jsonString, Class<T> cls) {
        return JSON.parseArray(jsonString, cls);
    }

    public static List<Map<String, String>> getKeyMapsList(String jsonString) {
        List<Map<String, String>> mLists;
        mLists = JSON.parseObject(jsonString, new TypeReference<List<Map<String, String>>>() {
        });
        return mLists;
    }

    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }


    private static final String TAG = "JSONUtil";
    public static JSONObject parse(ResponseBody responseBody, boolean isShowToast){
        if(null==responseBody){
            if(isShowToast){
                //ToastUtil.showTopToast(R.drawable.icon_error,ContextUtil.getString(R.string.network_is_exception));
            }
            return null;
        }
        try {
            String jsonStr = responseBody.string();
            //	ToastUtil.showTopToast(R.drawable.icon_error,jsonStr);
            LogUtil.d(TAG,"jsonStr is " + jsonStr);
            if(StringUtil.checkStr(jsonStr)){
                JSONObject obj = new JSONObject(jsonStr);
                String msg=obj.optString("msg");
                if ("invalid_session_token".equalsIgnoreCase(msg)) {
                    UserDataService mUserDataService = new UserDataService();
                    //mUserDataService.clearCookies();
                    mUserDataService.saveSession("");
                    mUserDataService.setLogin(false);
                    //	ArouterUtil.greenChannel("/activity/LandingPageActivity", null);
                }
                //String status = obj.optString("status");
			/*	if("ok".equalsIgnoreCase(status)|| String.valueOf(0).equalsIgnoreCase(obj.optString("status_code"))){

				}else{
					String reason = obj.optString("reason");
					if(!StringUtil.checkStr(reason)){
						reason = obj.optString("message");
						obj.put("reason",reason);
					}
					if(StringUtil.checkStr(reason)){//{"status":"error","reason":"Authorization header is missing"}
						if("invalid session token".equalsIgnoreCase(reason)||"Authorization header is missing".equalsIgnoreCase(reason)){
							if(null==mUserDataService){
								mUserDataService = new UserDataService();
							}
							mUserDataService.saveUid("");
							mUserDataService.saveUsertoken("");
							mUserDataService.savePwd("");
							mUserDataService.clearCookies();
							if(!GlobalAppComponent.hasEnterLogin&&GlobalAppComponent.isAutoForwardLogin){
								ArouterUtil.greenChannel("/login/LoginActivity",null);
							}
						}else{
							if("unconfirmed device".equalsIgnoreCase(reason)){
								ToastUtil.showTopToast(null,R.drawable.icon_notice,""+reason);
							}else{
								ToastUtil.showTopToast(null,R.drawable.icon_error,""+reason);
							}
						}
					}
				}*/
                return obj;
            }
        } catch (IOException e) {
            e.printStackTrace();
//            if(isShowToast){
//                ToastUtil.showTopToast(R.drawable.icon_error,"IOException "+e.getMessage());
//            }
            LogUtil.e(TAG,"io exception "+e.getMessage());
        }catch(JSONException e){
            if(isShowToast){
                //ToastUtil.showTopToast(null,R.drawable.icon_error,"json exception "+e.getMessage());
            }
            LogUtil.e(TAG,"json exception "+e.getMessage());
            return null;
        }catch (Exception e) {
            e.printStackTrace();
//            if(isShowToast){
//                ToastUtil.showTopToast(R.drawable.icon_error,"server error "+e.getMessage());
//            }
            LogUtil.e(TAG,"exception "+e.getMessage());

        }
        return null;
    }

    /**
     * 将json对象转换成Map
     *
     * @param jsonObject
     *            json对象
     * @return Map对象
     */
    public static Map<String, Object> jsonObjtoMap(JSONObject jsonObject) {
        if (null == jsonObject)
            return null;
        Map<String, Object> result = new HashMap<String, Object>();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.opt(key);
            result.put(key, value);
        }
        return result;
    }

    public static ArrayList<JSONObject> jsonObjtoArrayList(JSONObject jsonObject) {
        if (null == jsonObject)
            return null;
        Iterator<String> iterator = jsonObject.keys();
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = jsonObject.optString(key);
            JSONObject jsoObject = new JSONObject();
            try {
                jsoObject.put("id", key);
                jsoObject.put("name", value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(jsoObject);
        }
        return list;
    }

    /*
     * 把map转为json
     */
    public static JSONObject mapToJson(Map<String, Object> maps) {
        if (null == maps || maps.isEmpty())
            return null;
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> map : maps.entrySet()) {
            try {
                jsonObject.put(map.getKey(), map.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /*
     * List转为jsonArray
     */
    public static JSONArray listToJsonArray(List<String> list) {
        JSONArray array = new JSONArray();
        if (null == list || list.size() <= 0)
            return array;
        for (int i = 0; i < list.size(); i++) {
            array.put(list.get(i));
        }
        return array;
    }

    /*
     * 将jsonArray转换为List
     */
    public static ArrayList<JSONObject> arrayToList(JSONArray array) {
        if (null == array || array.length() <= 0)
            return null;
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.optJSONObject(i);
            if(null != jsonObject){
                list.add(jsonObject);
            }
        }
        return list;
    }

    /*
     * 将jsonArray转换为ArrayList<String>
     */
    public static ArrayList<String> arrayToStringList(JSONArray array) {
        if (null == array || array.length() <= 0)
            return null;
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.optString(i));
        }
        return list;
    }

    public static String arryToString(JSONArray array,String split) {
        if (null == array||array.length()<=0)
            return null;
        if(null==split)
            split = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length(); i++) {
            String str = array.optString(i);
            sb.append(str + split);
        }
        sb = sb.deleteCharAt(sb.lastIndexOf(split));
        return sb.toString();
    }

    public static String arryToStringWithSplit(JSONArray array) {
        if (null == array||array.length()<=0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length(); i++) {
            String str = array.optString(i);
            sb.append(str + ",");
        }
        sb=sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    /*
     * String[] 转为jsonarray
     */
    public static JSONArray stringArrayToJsonarry(String[] str) {
        if (null == str || str.length <= 0)
            return null;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < str.length; i++) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", str[i]);
                obj.put("value", i + "");
                jsonArray.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    /*
     * JSONObject转为list
     */
    public static ArrayList<JSONObject> jsonObjtoList(JSONObject jsonObject) {
        if (null == jsonObject)
            return null;
        ArrayList<JSONObject> result = new ArrayList<JSONObject>();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.opt(key);
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put(key, value);
                result.add(jsonObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * 将JSONArray转为map
     */
    public static HashMap<Integer, JSONObject> JSONArrayToMap(JSONArray array){
        if(null==array || array.length()<=0)
            return null;
        HashMap<Integer, JSONObject> map = new HashMap<Integer, JSONObject>();
        for(int i=0;i<array.length();i++){
            JSONObject obj = array.optJSONObject(i);
            int type = obj.optInt("type");
            map.put(type, obj);
        }
        return map;
    }

    public static boolean hasExist(JSONArray array,String varl){
        if(null==array||array.length()<=0)
            return false;
        boolean hasExist = false;
        for(int i=0;i<array.length();i++){
            String varl1 = array.optString(i);
            if(null!=varl1&&varl1.equals(varl)){
                hasExist = true;
                break;
            }
        }
        return hasExist;
    }

    /*
     * 要保证参数名和变量值名一样
     */
    public static JSONArray getJSONArray(String...arg){
        if(null==arg||0==arg.length){
            return null;
        }
        JSONArray array = new JSONArray();
        for(String str:arg){
            JSONObject obj = new JSONObject();
            try {
                obj.put("str",str);
                array.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    /*
     *
     */
    public static JSONArray mapToJSONArray(Map<String,Object> maps){
        if(null==maps||0==maps.size()){
            return null;
        }
        JSONArray array = new JSONArray();
        for(Map.Entry<String,Object> map:maps.entrySet()){
            Object value = map.getValue();
            if(null!=value){
                JSONObject obj = new JSONObject();
                try {
                    obj.put(map.getKey(),value);
                    array.put(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return array;
    }
}