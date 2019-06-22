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
package cn.xiaoandx.exam.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.xiaoandx.exam.entity.Question;
import cn.xiaoandx.exam.entity.TotalCollcetSubject;
import cn.xiaoandx.exam.vo.AnswerRanking;
import cn.xiaoandx.exam.vo.AnswerUser;
import cn.xiaoandx.user.entity.User;


/**  
 * <p>汇总表dao</p> 
 * @ClassName:TotalCollcetDao   
 * @author: junzhi.liu 
 * @date: 2019年5月18日 下午7:57:32   
 * @since: V0.1 
 * @version V0.1
 * @Copyright: 注意：本内容仅限于学习传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class TotalCollcetDao {

	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/**  
	 *<p>根据用户openid查询成绩</p> 
	 * @Title: findSrcoeByOpenId    
	 * @version:V0.1     
	 * @param openId
	 * @return:TotalCollcetSubject
	 */
	public TotalCollcetSubject findSrcoeByOpenId(String openId) {
		RowMapper<TotalCollcetSubject> totalRowMapper = new BeanPropertyRowMapper<TotalCollcetSubject>(TotalCollcetSubject.class);
		String questionSql = "SELECT t.current_total_score,t.userID "
				+ "FROM `user_information` AS u RIGHT JOIN answer_summary_statistics as t ON u.ID = t.userID "
				+ "WHERE u.openID = ?";
		return jdbcTemplate.queryForObject(questionSql, totalRowMapper, openId);
	}

	/**  
	 *<p>查询排名</p> 
	 * @Title: findRanking    
	 * @version:V0.1     
	 * @return:List<TotalCollcetSubject>
	 */
	public List<AnswerRanking> findRanking() {
		RowMapper<AnswerRanking> rowMapper = new BeanPropertyRowMapper<AnswerRanking>(AnswerRanking.class);
		String optionSql = "SELECT u.`ID`,b.current_total_score,b.rownum,u.`name`,u.`head_portrait` ,u.`city` "
				+ "FROM (SELECT  t.*, @rownum := @rownum + 1 AS rownum  FROM  (SELECT @rownum := 0) r, answer_summary_statistics AS t "
				+ "ORDER BY current_total_score DESC ) as b  LEFT JOIN `user_information` AS u ON b.userID = u.ID "
				+ "LIMIT 0,50";
		return jdbcTemplate.query(optionSql, rowMapper);
	}
	
	/**
	 * 
	 * @param answerUse
	 * @return
	 */
	public TotalCollcetSubject findSrcoeByUserId(AnswerUser answerUse) {
		RowMapper<TotalCollcetSubject> totalRowMapper = new BeanPropertyRowMapper<>(TotalCollcetSubject.class);
		String questionsql = "SELECT `ID`, `userID`, `current_total_score`, `correct_number`, `number_of_errors`, `total_number_of_answers`"
				+"FROM `answer_summary_statistics`"
				+"WHERE `userID` = ?";
		return jdbcTemplate.queryForObject(questionsql, totalRowMapper, answerUse.getUserID());
	}

	/**  
	 *<p>查询有无用户的汇总数据</p> 
	 * @Title: findByUserId    
	 * @version:V0.1     
	 * @param answerUser
	 * @return:List<TotalCollcetSubject>
	 */
	public List<TotalCollcetSubject> findByUserId(AnswerUser answerUser) {
		RowMapper<TotalCollcetSubject> rowMapper = new BeanPropertyRowMapper<TotalCollcetSubject>(TotalCollcetSubject.class);
		String optionSql = "SELECT `ID`, `userID`, `current_total_score`, `correct_number`, `number_of_errors`, `total_number_of_answers` "
				+ "FROM `answer_summary_statistics` "
				+ "WHERE `userID` = ?";
		return jdbcTemplate.query(optionSql, rowMapper,answerUser.getUserID());
	}

	/**  
	 *<p>更新汇总表的数据（加分）</p> 
	 * @Title: updateUser    
	 * @version:V0.1     
	 * @param question
	 * @param answerUser
	 * @return:int
	 */
	public int updateUser(Question question, AnswerUser answerUser) {
		String sql = "UPDATE `answer_summary_statistics` SET "
				+ "`correct_number` = `correct_number`+?, "
				+ "`number_of_errors` =  `number_of_errors`+?, "
				+ "`total_number_of_answers` = `total_number_of_answers`+?, "
				+ "`current_total_score` = `current_total_score`+? "
				+ "WHERE `userID` = ?;";
		int number = jdbcTemplate.update(sql,
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 1 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 0 : 1),
				1,(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : 0),
				answerUser.getUserID()
				);
		return number;
	}
	
	/**  
	 *<p>更新汇总表的数据（减分）</p> 
	 * @Title: updateUser    
	 * @version:V0.1     
	 * @param question
	 * @param answerUser
	 * @return:int
	 */
	public int updateUser1(Question question, AnswerUser answerUser) {
		String sql ="UPDATE `answer_summary_statistics` SET "
				+ "`correct_number` = `correct_number`+?, "
				+ "`number_of_errors` =  `number_of_errors`+?, "
				+ "`total_number_of_answers` = `total_number_of_answers`+?, "
				+ "`current_total_score` = `current_total_score`+? "
				+ "WHERE `userID` = ?;";
		int number = jdbcTemplate.update(sql,
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 1 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 0 : 1),
				1,(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : -5),
				answerUser.getUserID()
				);
		return number;
	}

	/**  
	 *<p>插入新的汇总表数据</p> 
	 * @Title: addTotaUser    
	 * @version:V0.1     
	 * @param question
	 * @param answerUser
	 * @return:int
	 */
	public int addTotaUser(Question question, AnswerUser answerUser) {
		String sql = "INSERT INTO `answer_summary_statistics` "
				+ "(`userID`,`current_total_score`,`correct_number`,`number_of_errors`,`total_number_of_answers`) "
				+ "VALUES (?,?,?,?,?)";
		int number = jdbcTemplate.update(sql,answerUser.getUserID(),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 1 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 0 : 1),1);
		return number;
	}

	/**  
	 *<p>更具用户id查询表</p> 
	 * @Title: findByUserID    
	 * @version:V0.1     
	 * @param answerUser
	 * @return    
	 * @return:TotalCollcetSubject
	 */
	public TotalCollcetSubject findByUserID(AnswerUser answerUser) {
		RowMapper<TotalCollcetSubject> rowMapper = new BeanPropertyRowMapper<TotalCollcetSubject>(TotalCollcetSubject.class);
		String sql = "SELECT `ID`, `userID`, `current_total_score`, `correct_number`, `number_of_errors`, `total_number_of_answers` "
				+ "FROM `answer_summary_statistics` "
				+ "WHERE `userID` = ?";
		return jdbcTemplate.queryForObject(sql, rowMapper,answerUser.getUserID());
	}

	/**  
	 *<p>获取用户的排名即总分</p> 
	 * @Title: findUserRanking    
	 * @version:V0.1     
	 * @param userId
	 * @return:AnswerRanking
	 */
	public AnswerRanking findUserRanking(Integer userId) {
		RowMapper<AnswerRanking> rowMapper = new BeanPropertyRowMapper<AnswerRanking>(AnswerRanking.class);
		String optionSql = "SELECT u.`ID`,b.current_total_score,b.rownum,u.`name`,u.`head_portrait` ,u.`city` "
				+ "FROM (SELECT  t.*, @rownum := @rownum + 1 AS rownum  FROM  (SELECT @rownum := 0) r, answer_summary_statistics AS t "
				+ "ORDER BY current_total_score DESC ) as b  LEFT JOIN `user_information` AS u ON b.userID = u.ID "
				+ "WHERE b.userID = ? ";
		return jdbcTemplate.queryForObject(optionSql, rowMapper,userId);
	}

	/**  
	 *<p>初始化数据</p> 
	 * @Title: addDefulent    
	 * @version:V0.1     
	 * @param user
	 * @return    
	 */
	public int addDefulent(User user) {
		String sql = "INSERT INTO `answer_summary_statistics` "
				+ "(`userID`,`current_total_score`,`correct_number`,`number_of_errors`,`total_number_of_answers`) "
				+ "VALUES (?,?,?,?,?)";
		int number = jdbcTemplate.update(sql,user.getID(),0,0,0,0);
		return number;
	}

}
