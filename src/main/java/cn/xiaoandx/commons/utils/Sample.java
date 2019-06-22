/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xiaoandx.commons.utils;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;

/**  
 * <p>人脸识别</p> 
 * @ClassName:Sample   
 * @author: junzhi.liu
 * @date: 2019年5月10日 下午1:59:34   
 * @since: V0.1 
 * @version V0.1
 * @Copyright: 注意：本内容仅限于学习传阅，禁止外泄以及用于其他的商业目
 */
public class Sample {

	//APPID
    public static final String APP_ID = "16217652";
    //API_KEY
    public static final String API_KEY = "MPN1dX5pOdxby9Gfaq5z7Ofb";
    //SECRET_KEY
    public static final String SECRET_KEY = "jCF9pHZLnhPRNPnwwDxGR32mEBbrxZh3";
    //client  AipFace对象
    private static AipFace client = null;

    
    static {
    	getAipFace();
    }
    
    /**
     *<p>获取 AipFace对象</p> 
     * @Title: getAipFace    
     * @version:V0.1     
     * @return    
     * @return:AipFace
     */
    public static AipFace getAipFace() {
    	client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    	client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    	return client;
    }
    
    
    /**
     *<p>人脸注册</p> 
     * @Title: faceRe    
     * @version:V0.1     
     * @param imageBase64
     * @return    
     * @return:String
     */
    public static String faceRe(String imageBase64) {
    	// 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        
        String imageType = "BASE64";
        String groupId = "xiaox_exam";
        String userId = IdAndTimeUtil.getQuestionId();
        
        // 人脸注册
        JSONObject res = client.addUser(imageBase64, imageType, groupId, userId, options);
        /**
         	* 直接获取的值face_token
         */
        String face_token = null;
        try {
			System.out.println(res.toString(2));
			face_token = res.getString("face_token");
		} catch (JSONException e1) {
			System.out.println("获取token失败");
		}
        //判断token是否为空
        if(null != face_token){
        	return face_token;
        }
        return "no Token";
    }
//    hello  my name is  manxiaotong
    
    /**
     *<p>人脸检测</p> 
     * @Title: searchFace    
     * @version:V0.1     
     * @param imageBase64
     * @return    
     * @return:String
     */
    public static String searchFace(String imageBase64) {
    	// 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "NONE");
        
        String image = imageBase64;
        String imageType = "BASE64";
        String groupIdList = "xiaox_exam";
        // 人脸搜索
        JSONObject res = client.search(image, imageType, groupIdList, options);
        try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
			return "检测失败";
		}
    }
}
