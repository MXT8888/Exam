package cn.xiaoandx.commons.core;

/**
 * <p>公共的错误码</p> 
 * @ClassName:PublicErrorCode   
 * @author: Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 */
public enum PublicErrorCode {

	QUERY_EXCEPTION(48001000),//查询异常
	SAVE_EXCEPTION(48002000),//保存异常
	DELETE_EXCEPTION(48003000),//删除异常
	PARAM_EXCEPTION(48004000),//参数异常
	AUTH_EXCEPTION(48005000);//权限异常

	private Integer intValue;
	private String strValue;

	PublicErrorCode(Integer stateCode) {
		this.intValue = stateCode;
		switch(stateCode) {
		case 48001000:
			this.strValue = "查询异常";
			break;
		case 48002000:
			this.strValue = "保存异常";
			break;
		case 48003000:
			this.strValue = "删除异常";
			break;
		case 48004000:
			this.strValue = "参数异常";
			break;
		case 48005000:
			this.strValue = "权限异常";
			break;
		default:
			this.strValue = "未知异常";
		}
	}
	public Integer getIntValue() {
		return intValue;
	}
	public String getStrValue() {
		return strValue;
	}
}
