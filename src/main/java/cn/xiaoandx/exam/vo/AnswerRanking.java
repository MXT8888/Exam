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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**  
 * <p>答题排名实体</p> 
 * @ClassName:AnswerRanking   
 * @author: junzhi.liu
 * @date: 2019年5月19日 上午11:35:23   
 * @since: V0.1 
 * @version V0.1
 * @Copyright: 注意：本内容仅限于学习传阅，禁止外泄以及用于其他的商业目
 */
@ApiModel(value = "答题排名")
@Data
public class AnswerRanking {
	
	@ApiModelProperty(value =  "排名",name = "rownum")
	private Integer rownum;
	@ApiModelProperty(value =  "用户id",name = "id")
	private Integer id;
	@ApiModelProperty(value =  "用户总成绩",name = "current_total_score")
	private Integer current_total_score;
	@ApiModelProperty(value =  "微信昵称",name = "name")
	private String name;
	@ApiModelProperty(value =  "头像地址",name = "head_portrait")
	private String head_portrait;
	@ApiModelProperty(value =  "城市",name = "city")
	private String city;
}
