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
package cn.xiaoandx.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**  
 * <p>人脸数据试题</p> 
 * @ClassName:UserImage   
 * @author: Fuqiang.Wus
 * @since: V0.1 
 * @version V0.1
 */
@ApiModel(value = "人脸数据实体")
@Data
public class UserImage {
	@ApiModelProperty(value = "imageBase",name = "人脸数据")
	private String imageBase;
}
