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
package cn.xiaoandx.exam.entity;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**  
 * <p>选项实体</p> 
 * @ClassName:QuestionOption   
 * @author: Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 */
@ApiModel("选项实体")
@Data
public class QuestionOption {
	@ApiModelProperty(dataType = "Integer",name = "ID",value = "选项id")
	private Integer ID;
	@ApiModelProperty(dataType = "String",name = "OPTION_CONTENT",value = "选项内容")
	private String OPTION_CONTENT;
	@ApiModelProperty(dataType = "String",name = "OPTION",value = "选项")
	private String OPTION;
	@ApiModelProperty(dataType = "String",name = "QUESTION_ID",value = "题目id")
	private String QUESTION_ID;
	@ApiModelProperty(dataType = "Date",name = "CREATEDDATE",value = "创建时间")
	private Date CREATEDDATE;
}
