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
package cn.xiaoandx.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xiaoandx.commons.core.DaoCode;
import cn.xiaoandx.commons.core.Parameter;
import cn.xiaoandx.commons.core.PublicErrorCode;
import cn.xiaoandx.commons.exception.CommonException;
import cn.xiaoandx.exam.dao.AnswerRecordDao;
import cn.xiaoandx.exam.dao.DayAnswerStatisticsDao;
import cn.xiaoandx.exam.dao.QuestionDao;
import cn.xiaoandx.exam.dao.QuestionOptionDao;
import cn.xiaoandx.exam.dao.TotalCollcetDao;
import cn.xiaoandx.exam.entity.DayAnswerStatistics;
import cn.xiaoandx.exam.entity.Question;
import cn.xiaoandx.exam.entity.TotalCollcetSubject;
import cn.xiaoandx.exam.vo.AboutMe;
import cn.xiaoandx.exam.vo.AnswerRanking;
import cn.xiaoandx.exam.vo.AnswerUser;

/**
 * <p>逻辑判断处理层</p>
 * 
 * @ClassName:IndexAnswerService
 * @author: Fuqiang.Wu
 * @since: V0.1
 * @version V0.1
 */
@Service
@Transactional
public class IndexAnswerService implements DaoCode, Parameter {
	@Autowired
	private TotalCollcetDao totalCollcetDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private QuestionOptionDao questionOptionDao;
	@Autowired
	private AnswerRecordDao answerRecordDao;
	@Autowired
	private DayAnswerStatisticsDao dayAnswerStatisticsDao;

	/**
	 * <p> 根据userId查询总成绩</p>
	 * @version:V0.1
	 * @param userId
	 * @return:TotalCollcetSubject
	 */
	public TotalCollcetSubject findSrcoeByOpenId(int userId) {
		if (0 != userId) {
			AnswerUser answerUser = new AnswerUser();
			answerUser.setUserID(userId);
			return totalCollcetDao.findSrcoeByUserId(answerUser);
		}
		new CommonException(PublicErrorCode.PARAM_EXCEPTION.getIntValue(), "参数异常");
		return null;

	}

	/**
	 * <p>查询排名</p>
	 * @version:V0.1
	 * @return:List<TotalCollcetSubject>
	 */
	public List<AnswerRanking> findRanking() {
		return totalCollcetDao.findRanking();
	}

	/**
	 * <p> 随机抽出一道题（包括选项） </p> 
	 * @version:V0.1
	 * @param question
	 * @return:Question
	 */
	public Question findById(String id) {
		Question question = questionDao.findById(id);
		if (null != question && question.getCONTENT().length() != ENTER_NUMBER) {
			question.setQuestionOptions(questionOptionDao.findById(id));
		} else {
			new CommonException(PublicErrorCode.PARAM_EXCEPTION.getIntValue(), "参数异常");
		}
		return question;
	}

	/**
	 * <p>
	 * 提交答案（判断正确更新答题记录数据）
	 * </p>
	 * 
	 * @Title: answer
	 * @version:V0.1
	 * @param answerUser
	 * @return:TotalCollcetSubject
	 */
	public TotalCollcetSubject answer(AnswerUser answerUser) {
		if (null != answerUser && null != answerUser.getUserID()) {
			// 1.先获得正确答案
			Question question = questionDao.findById(answerUser.getTopicID());
			// 2.添加答题记录表
			int addNumberOne = answerRecordDao.addQuestion(question, answerUser);
			// 3.添加每日答题记录表
			if (ADD_ERROR != addNumberOne) {
				List<DayAnswerStatistics> listDayAnswer = dayAnswerStatisticsDao.findBy(answerUser);
				if (ADD_ERROR != listDayAnswer.size()) {
					if (totalCollcetDao.findSrcoeByUserId(answerUser).getCurrent_total_score() <= 0) {
						int updateNumber = dayAnswerStatisticsDao.update(question, answerUser);
						if (ADD_ERROR == updateNumber) {
							new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "更新每日答题记录表异常");
						}
					} else {
						int updateNumber = dayAnswerStatisticsDao.update1(question, answerUser);
						if (ADD_ERROR == updateNumber) {
							new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "更新每日答题记录表异常");
						}
					}
				} else {
				 	int addNumberDay = dayAnswerStatisticsDao.add(question, answerUser);
					if (ADD_ERROR == addNumberDay) {
						new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "插入每日记录表异常");
					}
				}
			} else {
				new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "插入答题记录表异常");
			}
			// 更新答题汇总表
			List<TotalCollcetSubject> listTota = totalCollcetDao.findByUserId(answerUser);
			if (ADD_ERROR != listTota.size()) {
				if (totalCollcetDao.findSrcoeByUserId(answerUser).getCurrent_total_score() <= 0) {
					int updateNumberTwo = totalCollcetDao.updateUser(question, answerUser);
					if (ADD_ERROR == updateNumberTwo) {
						new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "更新汇总表记录表异常");
					}
				} else {
					int updateNumberTwo = totalCollcetDao.updateUser1(question, answerUser);
					if (ADD_ERROR == updateNumberTwo) {
						new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "更新汇总表记录表异常");
					}
				}
			} else {
				int addNumberTwo = totalCollcetDao.addTotaUser(question, answerUser);
				if (ADD_ERROR == addNumberTwo) {
					new CommonException(PublicErrorCode.SAVE_EXCEPTION.getIntValue(), "插入汇总表记录表异常");
				}
			}
			// 返回一个汇总表的对象
		} else {
			new CommonException(PublicErrorCode.PARAM_EXCEPTION.getIntValue(), "参数异常");
		}
		return totalCollcetDao.findByUserID(answerUser);
	}

	/**
	 * <p>
	 * 获取关于我页面数据
	 * </p>
	 * 
	 * @Title: findAllById
	 * @version:V0.1
	 * @param userId
	 * @return:AboutMe
	 */
	public AboutMe findAllById(Integer userId) {
		if (ENTER_NUMBER != userId) {
			AnswerUser answerUser = new AnswerUser();
			answerUser.setUserID(userId);
			AboutMe aboutMe = new AboutMe();
			// 获取用户排名即总分信息
			aboutMe.setAnswerRanking(totalCollcetDao.findUserRanking(userId));
			// 获取汇总数据
			aboutMe.setTotalCollcetSubject(totalCollcetDao.findByUserID(answerUser));
			// 获取用户的擅长的知识领域
			aboutMe.setAnswerRecord(answerRecordDao.findKDByUserId(userId));
			// 获取用户的每日答题记录
			aboutMe.setUserDayAnswer(dayAnswerStatisticsDao.findDayAnswer(userId));
			// 返回对象
			return aboutMe;
		}
		new CommonException(PublicErrorCode.PARAM_EXCEPTION.getIntValue(), "参数异常");
		return null;
	}
}
