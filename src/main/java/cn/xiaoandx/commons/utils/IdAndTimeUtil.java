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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**  
 * <p>生成试题id+当前时间</p> 
 * @ClassName:IdAndTimeUtil   
 * @author: Fuqiang.Wu
 * @version V0.1
 */
public class IdAndTimeUtil {
	
	/**
	 *<p>生成试题编号（ID）</p> 
	 * @Title: getQuestionId    
	 * @version:V0.1     
	 * @return    
	 * @return:String
	 */
	public static String getQuestionId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 *<p>获取当前时间</p> 
	 * @Title: getNewDate    
	 * @version:V0.1     
	 * @return    
	 * @return:Timestamp
	 */
	public static Timestamp getNewDate(){
		return new Timestamp( System.currentTimeMillis());
	}
	
	public static String getDate () {
		Date day = new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		return df.format(day);
	}
	
	/**
	 *<p>随机生成题号（1980-15726）</p> 
	 * @Title: getQuestion    
	 * @version:V0.1     
	 * @return    
	 * @return:Integer
	 */
	public static String getQuestion() {
		Integer number =  (int)(Math.random()*(13747)+1980);
		return String.valueOf(number);
	}
	
}
