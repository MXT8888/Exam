package cn.xiaoandx.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>标识对象</p> 
 * @ClassName:OneValueVO   
 * @author: Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 */
@ApiModel(description="接收前端传来的数据")
@Data
@NoArgsConstructor
public class OneValueVO {
    @ApiModelProperty(value = "userId")
    private int userId;
    @ApiModelProperty(value = "token")
    private String token;

	/**
	 * 
	 * @param userId
	 * @param token
	 */
	public OneValueVO(int userId, String token) {
		this.userId = userId;
		this.token = token;
	}
    
   
}
