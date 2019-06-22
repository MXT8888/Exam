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
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**  
 * <p>试题实体</p> 
 * @ClassName:Question   
 * @author: Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 */
@ApiModel("试题")
@Data
public class Question {
	@ApiModelProperty(dataType = "String",name = "ID",value = "试题id")
	private String ID;
	@ApiModelProperty(dataType = "String",name = "CONTENT",value = "试题题目")
	private String CONTENT;
	@ApiModelProperty(dataType = "String",name = "ANSWER",value = "试题的正确答案")
	private String ANSWER;
	@ApiModelProperty(dataType = "String",name = "KNOWLEDGE_AREA",value = "知识领域")
	private String KNOWLEDGE_AREA;
	@ApiModelProperty(dataType = "Date",name = "CREATED_DATE",value = "试题创建时间")
	private Date CREATED_DATE;
	@ApiModelProperty(name = "questionOptions",value = "试题选项")
	private List<QuestionOption> questionOptions;
	
}
