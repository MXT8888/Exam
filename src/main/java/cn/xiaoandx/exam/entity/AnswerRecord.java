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
 * <p>答题记录实体类</p> 
 * @ClassName:AnswerRecord   
 * @author: Fuqiang.Wu
 * @version V0.1
 */
@ApiModel(value = "答题记录实体类")
@Data
public class AnswerRecord {
	@ApiModelProperty(value = "答题记录id",name = "id")
	private Integer ID;
	@ApiModelProperty(value = "题目id",name = "topicId")
	private Integer topicID;
	@ApiModelProperty(value = "提交选项",name = "submit_Answers")
	private String 	submit_Answers;
	@ApiModelProperty(value = "答题是否正确(正确为0，错误为1)",name = "judge_answer")
	private String judge_answer;
	@ApiModelProperty(value = "用户id",name = "userId")
	private Integer userID;
	@ApiModelProperty(value = "本题正确答案",name = "correct_answer")
	private String 	correct_answer;
	@ApiModelProperty(value = "答题时间",name = "answer_time")
	private Date answer_time;
	@ApiModelProperty(value = "本题知识领域",name = "knowledge_point")
	private String  knowledge_point;
	@ApiModelProperty(value = "本题得分",name = "score")
	private Integer score;
	@ApiModelProperty(value = "知识领域数量",name = "number")
	private Integer number;
}
