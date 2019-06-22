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
import lombok.EqualsAndHashCode;

/**
 * <p>接收分页查询试题的实体类</p> 
 * @ClassName:QueryQuestionDomain   
 * @author: junzhi.liu
 * @date: 2019年5月16日 上午9:57:42   
 * @since: V0.1 
 * @version V0.1
 * @Copyright: 注意：本内容仅限于学习传阅，禁止外泄以及用于其他的商业目
 */
@ApiModel(description="题目查询")
@Data
@EqualsAndHashCode(callSuper=false)
public class QueryQuestionDomain {
	
	@ApiModelProperty(value = "第几页", required = false,name = "pageNumber")
	private Integer pageNumber;
	
	@ApiModelProperty(value = "每页数据的数目", required = false,name = "pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value = "查询关键字", required = false,name = "keyWords")
	private String keyWords;
	
}
