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

import cn.xiaoandx.commons.utils.IdAndTimeUtil;
import cn.xiaoandx.exam.entity.DayAnswerStatistics;
import cn.xiaoandx.exam.entity.Question;
import cn.xiaoandx.exam.vo.AnswerUser;
import cn.xiaoandx.exam.vo.UserDayAnswer;

/**  
 * <p>每日答题记录持久层</p> 
 * @ClassName:DayAnswerStatisticsDao   
 * @author:Fuqiang.Wu 
 * @since: V0.1 
 * @version V0.1
 */
@Component
public class DayAnswerStatisticsDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	/**  
	 *<p>查询当天用户是否情况</p> 
	 * @Title: findBy    
	 * @version:V0.1     
	 * @param answerUser
	 * @return    
	 * @return:List<DayAnswerStatistics>
	 */
	public List<DayAnswerStatistics> findBy(AnswerUser answerUser) {
		RowMapper<DayAnswerStatistics> rowMapper=new BeanPropertyRowMapper<DayAnswerStatistics>(DayAnswerStatistics.class);
		String optionSql = "SELECT `ID`, `userID`, `correct_number`, `number_of_errors`, "
				+ "`total_number_of_answers`, `current_total_score`, `total_score_of_the_day`, `answer_time` "
				+ "FROM `daily_answer_statistics` "
				+ "WHERE `userID` = ? AND DATE(`answer_time`) = ?;";
		return jdbcTemplate.query(optionSql, rowMapper,answerUser.getUserID(),IdAndTimeUtil.getDate());
	}

	/**  
	 *<p>更新每日答题数据</p> 
	 * @version:V0.1     
	 * @param question
	 * @param answerUser
	 * @return    
	 */
	public int update(Question question, AnswerUser answerUser) {
		String sql = "UPDATE `daily_answer_statistics` "
				+ "SET `correct_number` = `correct_number`+?, "
				+ "`number_of_errors` =`number_of_errors`+?, "
				+ "`total_number_of_answers` = `total_number_of_answers`+?, "
				+ "`current_total_score` = `current_total_score`+?, "
				+ "`total_score_of_the_day` = `total_score_of_the_day`+? "
				+ "WHERE `userID` = ? AND DATE(`answer_time`) = ?";
		int number = jdbcTemplate.update(sql,
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 1 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 0 : 1),
				1,(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : 0),
				answerUser.getUserID(),IdAndTimeUtil.getDate());
		return number;
	}
	public int update1(Question question, AnswerUser answerUser) {
		String sql = "UPDATE `daily_answer_statistics` "
				+ "SET `correct_number` = `correct_number`+?, "
				+ "`number_of_errors` =`number_of_errors`+?, "
				+ "`total_number_of_answers` = `total_number_of_answers`+?, "
				+ "`current_total_score` = `current_total_score`+?, "
				+ "`total_score_of_the_day` = `total_score_of_the_day`+? "
				+ "WHERE `userID` = ? AND DATE(`answer_time`) = ?";
		int number = jdbcTemplate.update(sql,
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 1 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 0 : 1),
				1,(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : -5),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : -5),
				answerUser.getUserID(),IdAndTimeUtil.getDate());
		return number;
	}

	/**  
	 *<p>添加每日答题记录</p> 
	 * @Title: add    
	 * @version:V0.1     
	 * @param question
	 * @param answerUser
	 * @return    
	 * @return:int
	 */
	public int add(Question question, AnswerUser answerUser) {
		String sql = "INSERT INTO `daily_answer_statistics` "
				+ "(`userID`,`correct_number`,`number_of_errors`,`total_number_of_answers`,`current_total_score`,`total_score_of_the_day`,`answer_time`) "
				+ "VALUES (?,?,?,?,?,?,?)";
		int number = jdbcTemplate.update(sql,answerUser.getUserID(),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 1 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 0 : 1),
				1,(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : 0),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : 0),IdAndTimeUtil.getDate()
				);
		return number;
	}

	/**  
	 *<p>查询用户每日的答题记录</p> 
	 * @Title: findDayAnswer    
	 * @version:V0.1     
	 * @param userId
	 * @return    
	 * @return:List<UserDayAnswer>
	 */
	public List<UserDayAnswer> findDayAnswer(Integer userId) {
		RowMapper<UserDayAnswer> rowMapper=new BeanPropertyRowMapper<UserDayAnswer>(UserDayAnswer.class);
		String sql = "SELECT DATE(d.answer_time) as answerTime, "
				+ "SUM(d.total_number_of_answers) AS subTotal,"
				+ "SUM(d.correct_number) AS correctNumber, "
				+ "SUM(d.number_of_errors) AS errorNUmber ,"
				+ "SUM(d.total_score_of_the_day) AS dayScore "
				+ "FROM daily_answer_statistics AS d "
				+ "WHERE userID = ? "
				+ "GROUP BY DATE(d.answer_time) "
				+ "ORDER BY DATE(d.answer_time) DESC";
		return jdbcTemplate.query(sql, rowMapper,userId);
	}
}
