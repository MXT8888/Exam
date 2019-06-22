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
package cn.xiaoandx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**  
 * <p>项目主类</p> 
 * @ClassName:ExamV2WebApp   
 * @author: Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 */
@SpringBootApplication
public class ExamV2WebApp extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(ExamV2WebApp.class, args);
	}
	
	/**   
	 * <p>Title: configure</p>   
	 * <p>Description: </p>   
	 * @param builder
	 * @return   
	 * @see org.springframework.boot.web.servlet.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)   
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		//测试
		return builder.sources(ExamV2WebApp.class);
	}

}
