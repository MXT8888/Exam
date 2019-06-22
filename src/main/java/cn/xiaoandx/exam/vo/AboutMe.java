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
package cn.xiaoandx.exam.vo;

import java.util.List;

import cn.xiaoandx.exam.entity.AnswerRecord;
import cn.xiaoandx.exam.entity.TotalCollcetSubject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**  
 * <p>我的一个试题类</p> 
 * @ClassName:AboutMe   
 * @author:Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 
 */
@ApiModel(value = "我的数据实体")
@Data
public class AboutMe {
	@ApiModelProperty(value = "总分排名",name = "answerRanking")
	private AnswerRanking answerRanking;
	@ApiModelProperty(value = "答题汇总记录",name = "totalCollcetSubject")
	private TotalCollcetSubject totalCollcetSubject;
	@ApiModelProperty(value = "知识领域",name = "answerRecord")
	private List<AnswerRecord> answerRecord;
	@ApiModelProperty(value = "每日答题统计",name = "userDayAnswer")
	private List<UserDayAnswer> userDayAnswer;
}
