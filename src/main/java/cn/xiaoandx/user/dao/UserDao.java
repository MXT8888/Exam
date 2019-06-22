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
package cn.xiaoandx.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.xiaoandx.commons.utils.IdAndTimeUtil;
import cn.xiaoandx.user.entity.User;
import cn.xiaoandx.user.vo.OneValueVO;
import cn.xiaoandx.user.vo.WUser;
import lombok.extern.slf4j.Slf4j;

/**  
 * <p> </p> 
 * @ClassName:UserDao   
 * @author: Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 */
@Component
@Slf4j
public class UserDao{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/**  
	 *<p>根据openID查询是否该微信用户注册过</p> 
	 * @Title: findUser    
	 * @version:V0.1     
	 * @param openid
	 * @return:List<User>
	 */
	public List<User> findUser(String openid) {
		RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
		String sql = "SELECT u.`ID`,u.`openID`,u.`head_portrait`,u.`name`,u.`country`,u.`province`,u.`city`,u.`creation_time`,u.`token` "
				+ "FROM`user_information` AS u "
				+ "WHERE u.`openID` = ?";
		return jdbcTemplate.query(sql, rowMapper, openid);
	}

	/**  
	 *<p>注册用户</p> 
	 * @version:V0.1     
	 * @param user
	 * @param openid
	 * @return    
	 */
	public int reUser(WUser user, String openid) {
		String sql = "INSERT INTO  `user_information` "
				+ "(`openID`,`head_portrait`,`name`,`country`,`province`,`city`,`creation_time`) "
				+ "VALUES (?,?,?,?,?,?,?)";
		int number = jdbcTemplate.update(sql,openid,user.getHead_portrait(),user.getName(),user.getCountry(),user.getProvince(),user.getCity(),IdAndTimeUtil.getNewDate());
		log.info("注册用户"+String.valueOf(number));
		return number;
	}

	/**  
	 *<p>获取用户数据</p>    
	 * @version:V0.1     
	 * @param openid
	 * @return    
	 * @return:User
	 */
	public User findByOpenId(String openid) {
		RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
		String sql = "SELECT u.`ID`,u.`openID`,u.`head_portrait`,u.`name`,u.`country`,u.`province`,u.`city`,u.`creation_time`,u.`token` "
				+ "FROM`user_information` AS u "
				+ "WHERE u.`openID` = ?";
		return jdbcTemplate.queryForObject(sql, rowMapper, openid);
	}

	/**  
	 *<p>用户注册人脸获得token写入数据库</p>    
	 * @version:V0.1     
	 * @param oneValueVO
	 * @return    
	 */
	public int addToken(OneValueVO oneValueVO) {
		String sql = "UPDATE  `user_information` SET `token` = ? WHERE `ID` = ?";
		int number = jdbcTemplate.update(sql,oneValueVO.getToken(),oneValueVO.getUserId());
		log.info("写入token成功"+String.valueOf(number));
		return number;
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User findByUserId(int userId) {
		RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
		String sql = "SELECT u.`ID`,u.`openID`,u.`head_portrait`,u.`name`,u.`country`,u.`province`,u.`city`,u.`creation_time`,u.`token` "
				+ "FROM`user_information` AS u "
				+ "WHERE u.`ID` = ?";
		return jdbcTemplate.queryForObject(sql, rowMapper, userId);
	}
	
}
