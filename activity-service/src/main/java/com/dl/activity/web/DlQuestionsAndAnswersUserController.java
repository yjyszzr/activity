package com.dl.activity.web;

import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.BeforePeriodNoteDTO;
import com.dl.activity.dto.MatchInfoDTO;
import com.dl.activity.dto.QuestionAndAnswersDTO;
import com.dl.activity.dto.UserPeriodDTO;
import com.dl.activity.model.DlQuestionsAndAnswers;
import com.dl.activity.model.DlQuestionsAndAnswersForBeforeNote;
import com.dl.activity.model.DlQuestionsAndAnswersUser;
import com.dl.activity.param.AddAnswersParam;
import com.dl.activity.param.MatchIdParam;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.DlQuestionsAndAnswersService;
import com.dl.activity.service.DlQuestionsAndAnswersUserService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.DateUtilNew;
import com.dl.base.util.SessionUtil;

/**
 * Created by CodeGenerator on 2018/06/21.
 */
@RestController
@RequestMapping("/dlQuestionsAndAnswersUser")
public class DlQuestionsAndAnswersUserController {
	@Resource
	private DlQuestionsAndAnswersUserService dlQuestionsAndAnswersUserService;

	@Resource
	private DlQuestionsAndAnswersService dlQuestionsAndAnswersService;

	@ApiOperation(value = "添加答题", notes = "添加答题")
	@PostMapping("/add")
	public BaseResult add(@RequestBody AddAnswersParam addAnswersParam) {
		// 每增加一个答题用户 奖池金额增加1元 答题人数累加一个
		DlQuestionsAndAnswers questionsAndAnswers = dlQuestionsAndAnswersService.getQuestionsAndAnswers(Integer.parseInt(addAnswersParam.getMatchId()));
		if (questionsAndAnswers != null) {
			// 添加一个人
			questionsAndAnswers.setNumOfPeople(questionsAndAnswers.getNumOfPeople() + 1);
			// 添加一块钱
			BigDecimal big = new BigDecimal(1);
			questionsAndAnswers.setBonusPool(big.add(questionsAndAnswers.getBonusPool()));
			dlQuestionsAndAnswersService.update(questionsAndAnswers);
			DlQuestionsAndAnswersUser dlQuestionsAndAnswersUser = new DlQuestionsAndAnswersUser();
			dlQuestionsAndAnswersUser.setId(0);
			dlQuestionsAndAnswersUser.setUserAnswer(addAnswersParam.getAnswers());
			dlQuestionsAndAnswersUser.setMatchId(questionsAndAnswers.getMatchId());
			Integer userId = SessionUtil.getUserId();
			dlQuestionsAndAnswersUser.setUserId(userId);
			dlQuestionsAndAnswersUser.setPeriod(questionsAndAnswers.getPeriod());
			dlQuestionsAndAnswersUser.setQuestionId(questionsAndAnswers.getId());
			dlQuestionsAndAnswersUser.setAnswerTime(DateUtilNew.getCurrentTimeLong());
			dlQuestionsAndAnswersUserService.save(dlQuestionsAndAnswersUser);
			return ResultGenerator.genSuccessResult("保存成功");
		}
		return ResultGenerator.genSuccessResult("赛事为空");
	}

	/**
	 * 用户点进去之后
	 * 
	 * @param matchIdParam
	 * @return
	 */
	// @ApiOperation(value = "用户竞猜答题详情页", notes = "用户竞猜答题详情页")
	// @PostMapping("/userAnswersDetail")
	// public BaseResult<MatchInfoDTO> userAnswersDetail(@RequestBody
	// MatchIdParam matchIdParam) {
	// return guessingCompetitionDetails(matchIdParam);
	// }
	@ApiOperation(value = "获取往期记录", notes = "获取往期记录")
	@PostMapping("/beforePeriodNote")
	public BaseResult<BeforePeriodNoteDTO> getBeforePeriodNote(@RequestBody MatchIdParam matchIdParam) {
		DlQuestionsAndAnswers questionsAndAnswers = new DlQuestionsAndAnswers();
		DlQuestionsAndAnswersForBeforeNote answersNote = new DlQuestionsAndAnswersForBeforeNote();
		questionsAndAnswers = dlQuestionsAndAnswersService.getQuestionsAndAnswers(matchIdParam.getMatchId());
		answersNote = dlQuestionsAndAnswersService.findBeforePeriodNoteBymatchId(questionsAndAnswers.getId());
		Integer userId = SessionUtil.getUserId();
		// Integer userId = 400408;
		BeforePeriodNoteDTO beforePeriodNote = new BeforePeriodNoteDTO();
		if (userId != null) {
			DlQuestionsAndAnswersUser userAnswerInfo = dlQuestionsAndAnswersUserService.findUserAnswerMatchId(answersNote.getMatchId(), userId);
			// 判断上期是否参与
			if (userAnswerInfo == null) {
				beforePeriodNote.setParticipateOrNot(0);
			} else {
				// 判断是否中奖
				if (null == userAnswerInfo.getGetAward() || userAnswerInfo.getGetAward() == 0) {
					beforePeriodNote.setGetAwardOrNot(0);
				} else {
					beforePeriodNote.setGetAwardOrNot(1);
				}
				beforePeriodNote.setParticipateOrNot(1);
			}
		}
		if (answersNote != null) {
			beforePeriodNote.setBonusPool(answersNote.getBonusPool().toString());
			beforePeriodNote.setNumOfPeople(answersNote.getPrizewinningNum() == null ? 0 : answersNote.getPrizewinningNum());
			BigDecimal bonusPool = new BigDecimal(answersNote.getBonusPool().toString());
			BigDecimal prizewinningNum = new BigDecimal(answersNote.getPrizewinningNum() == null ? "0" : answersNote.getPrizewinningNum().toString());
			BigDecimal bigDecimal = new BigDecimal("0");
			if (prizewinningNum.compareTo(bigDecimal) == 0) {
				beforePeriodNote.setReward("0");
			} else {
				beforePeriodNote.setReward(bonusPool.divide(prizewinningNum, 3, BigDecimal.ROUND_HALF_DOWN).toString());
			}
		}
		return ResultGenerator.genSuccessResult(null, beforePeriodNote);
	}

	@ApiOperation(value = "用户竞猜答题列表", notes = "用户竞猜答题列表")
	@PostMapping("/userAnswersList")
	public BaseResult<List<UserPeriodDTO>> userAnswersList(@RequestBody StrParam strParam) {
		Integer userId = SessionUtil.getUserId();
		// Integer userId = 400093;
		List<UserPeriodDTO> periodList = dlQuestionsAndAnswersUserService.findByUserId(userId);
		return ResultGenerator.genSuccessResult(null, periodList);
	}

	/**
	 * 首次进入
	 * 
	 * @param matchIdParam
	 * @return
	 */
	@SuppressWarnings("unused")
	@ApiOperation(value = "竞猜答题详情页", notes = "竞猜答题详情页")
	@PostMapping("/guessingCompetitionDetails")
	public BaseResult<MatchInfoDTO> guessingCompetitionDetails(@RequestBody MatchIdParam matchIdParam) {
		MatchInfoDTO matchInfo = dlQuestionsAndAnswersUserService.findMatchInfo(matchIdParam.getMatchId());
		DlQuestionsAndAnswers questionsAndAnswers = dlQuestionsAndAnswersService.getQuestionsAndAnswers(matchIdParam.getMatchId());
		if (questionsAndAnswers != null) {
			// 解析题干以及题设Json
			JSONArray jsonArray = JSONArray.fromObject(questionsAndAnswers.getQuestionAndAnswer());
			@SuppressWarnings("unchecked")
			List<QuestionAndAnswersDTO> questionAndAnswerList = (List<QuestionAndAnswersDTO>) JSONArray.toCollection(jsonArray, QuestionAndAnswersDTO.class);
			Integer currentTime = DateUtilNew.getCurrentTimeLong();
			// 如果开始时间大于当前时间并且小于结束时间则表示可以开始
			if (questionsAndAnswers.getStartTime() < currentTime && questionsAndAnswers.getEndTime() > currentTime) {
				matchInfo.setAnswerTimeStatus(1);
				// 如果开始时间大于当前时间 则表示还未开始
			} else if (questionsAndAnswers.getStartTime() > currentTime) {
				matchInfo.setAnswerTimeStatus(2);
				// 如果结束时间小于当前时间则表示已经结束
			} else if (questionsAndAnswers.getEndTime() < currentTime) {
				matchInfo.setAnswerTimeStatus(0);
			}
			Integer userId = SessionUtil.getUserId();
			// Integer userId = 400408;
			// 判断用户是否登录
			if (userId != null) {
				// 查询用户是否答题
				DlQuestionsAndAnswersUser questionsAndAnswersUser = dlQuestionsAndAnswersUserService.getQuestionsAndAnswersForUser(userId, questionsAndAnswers.getId());
				// 如果不为空 则该用户答过题 将题干和该用户的答案返回
				if (questionsAndAnswersUser != null) {
					JSONArray jsonArrayForUser = JSONArray.fromObject(questionsAndAnswersUser.getUserAnswer());
					// 解析用户答案Json
					@SuppressWarnings("unchecked")
					List<QuestionAndAnswersDTO> usesQuestionAndAnswerList = (List<QuestionAndAnswersDTO>) JSONArray.toCollection(jsonArrayForUser, QuestionAndAnswersDTO.class);
					if (questionsAndAnswers.getStatus() == 2) {
						matchInfo.setAnswerStatus(1);
						// matchInfo.setUserAnswersList(usesQuestionAndAnswerList);
					} else {
						matchInfo.setAnswerStatus(0);
					}
					// 用户答案转成Map
					Map<Integer, QuestionAndAnswersDTO> usesQuestionAndAnswerMap = new HashMap<Integer, QuestionAndAnswersDTO>(usesQuestionAndAnswerList.size());
					usesQuestionAndAnswerList.forEach(item -> usesQuestionAndAnswerMap.put(item.getQuestionNum(), item));
					// 将用户答案写到题干
					for (int i = 0; i < questionAndAnswerList.size(); i++) {
						QuestionAndAnswersDTO userAnswer = new QuestionAndAnswersDTO();
						userAnswer = usesQuestionAndAnswerMap.get(questionAndAnswerList.get(i).getQuestionNum());
						questionAndAnswerList.get(i).setRightAnswerStatus1(questionAndAnswerList.get(i).getAnswerStatus1());
						questionAndAnswerList.get(i).setRightAnswerStatus2(questionAndAnswerList.get(i).getAnswerStatus2());
						questionAndAnswerList.get(i).setAnswerStatus1(userAnswer.getAnswerStatus1());
						questionAndAnswerList.get(i).setAnswerStatus2(userAnswer.getAnswerStatus2());
					}
				} else {
					String currentDate = DateUtilNew.getCurrentYearMonthDay();
					// String currentDate = "2018-05-08";

					// BigDecimal bigAmount =
					// dlQuestionsAndAnswersUserService.getTodayAllOrderAmount(userId,
					// currentDate);
					// 临时测试使用
					BigDecimal bigAmount = new BigDecimal(500);
					// 临时测试使用
					if (bigAmount == null) {
						bigAmount = new BigDecimal(0);
						matchInfo.setOnceBettingAmount(questionsAndAnswers.getLimitLotteryAmount());
						matchInfo.setChance(0);
					} else {
						BigDecimal onceBettingAmount = bigAmount.subtract(questionsAndAnswers.getLimitLotteryAmount());
						if (onceBettingAmount.doubleValue() >= 0) {
							matchInfo.setOnceBettingAmount(bigAmount);
							matchInfo.setChance(1);
						} else {
							BigDecimal num = new BigDecimal(0);
							matchInfo.setOnceBettingAmount(num.subtract(onceBettingAmount));
							matchInfo.setChance(0);
						}
					}
				}
			}
			matchInfo.setQuestionAndAnswersList(questionAndAnswerList);
			matchInfo.setStopTime(questionsAndAnswers.getEndTime());
			matchInfo.setNumOfPeople(questionsAndAnswers.getNumOfPeople());
			matchInfo.setBonusPool(questionsAndAnswers.getBonusPool().toString());
		}
		return ResultGenerator.genSuccessResult(null, matchInfo);
	}
}
