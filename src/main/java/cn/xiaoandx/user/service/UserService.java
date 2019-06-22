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
package cn.xiaoandx.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xiaoandx.commons.core.DaoCode;
import cn.xiaoandx.commons.core.Parameter;
import cn.xiaoandx.commons.core.PublicErrorCode;
import cn.xiaoandx.commons.exception.CommonException;
import cn.xiaoandx.commons.utils.GetWeChatOpenId;
import cn.xiaoandx.commons.utils.Sample;
import cn.xiaoandx.exam.dao.TotalCollcetDao;
import cn.xiaoandx.user.dao.UserDao;
import cn.xiaoandx.user.entity.User;
import cn.xiaoandx.user.vo.OneValueVO;
import cn.xiaoandx.user.vo.WUser;
import lombok.extern.slf4j.Slf4j;


/**  
 * <p>微信用户操作逻辑控制层</p> 
 * @ClassName:UserService   
 * @author: Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 */
@Service
@Transactional
@Slf4j
public class UserService implements DaoCode,Parameter{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private TotalCollcetDao totalCollcetDao;
	
	
	/**  
	 *<p>获取openID</p> 
	 * @version:V0.1     
	 * @param code		传入code
	 * @return    
	 * @return:String	传出openID
	 */
	public String getWeixinOpenInfo(String code) {
		return GetWeChatOpenId.getWeixinOpenInfo(code);
	}
	
	/**
	 * 
	 *<p>先根据code查询是否已经注册，若注册跳转检测界面，若未注册就进行注册</p> 
	 * @param openid	用户的微信openID
	 * @return:User		用户对象
	 */
	public User findUser(String openID,WUser user) {
		if(null != openID) {
			List<User> listuserservice = userDao.findUser(openID);
			if(ENTER_NUMBER == listuserservice.size()) {
				int number = userDao.reUser(user,openID);
				if(ADD_ERROR != number) {
					log.info("注册用户成功");
				}
			}else {
				return userDao.findByOpenId(openID);
			}
		}
		 return userDao.findByOpenId(openID);
	}
	
	/**  
	 *<p>人脸注册</p>    
	 * @version:V0.1     
	 * @param imageBase
	 * @return:String
	 */
	public String faceRe(String imageBase) {
		if(null != imageBase) {
			return Sample.faceRe(imageBase);
		}
		new CommonException(PublicErrorCode.PARAM_EXCEPTION.getIntValue(),"照片异常");
		return null;
	}

	/**  
	 *人脸注册的时候将人脸的token写入数据库   
	 * @version:V0.1     
	 * @param oneValueVO    
	 * @return:
	 */
	public void addToken(OneValueVO oneValueVO) {
		
		// 获取用户id
		User user = userDao.findByUserId(oneValueVO.getUserId());
		// 初始化答题汇总表
		int numbers = totalCollcetDao.addDefulent(user);
		if(ADD_ERROR == numbers) {
			new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "初始化数据表失败");
		}
		int number = userDao.addToken(oneValueVO);
		if (ADD_ERROR == number) {
			new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "更新token失败");
		}
	}

	/**  
	 *<p>人脸检测</p>    
	 * @version:V0.1     
	 * @param imageBase
	 * @return    
	 * @return:String
	 */
	public String searchFace(String imageBase) {
		if(null != imageBase) {
			return Sample.searchFace(imageBase);
		}
		new CommonException(PublicErrorCode.PARAM_EXCEPTION.getIntValue(),"照片异常");
		return null;
	}

	

	
}
