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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.MatchInfoDTO;
import com.dl.activity.dto.QuestionAndAnswersDTO;
import com.dl.activity.dto.UserPeriodDTO;
import com.dl.activity.model.DlQuestionsAndAnswers;
import com.dl.activity.model.DlQuestionsAndAnswersUser;
import com.dl.activity.param.MatchIdParam;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.DlQuestionsAndAnswersService;
import com.dl.activity.service.DlQuestionsAndAnswersUserService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.DateUtilNew;

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

	@PostMapping("/add")
	public BaseResult add(DlQuestionsAndAnswersUser dlQuestionsAndAnswersUser) {
		dlQuestionsAndAnswersUserService.save(dlQuestionsAndAnswersUser);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/delete")
	public BaseResult delete(@RequestParam Integer id) {
		dlQuestionsAndAnswersUserService.deleteById(id);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/update")
	public BaseResult update(DlQuestionsAndAnswersUser dlQuestionsAndAnswersUser) {
		dlQuestionsAndAnswersUserService.update(dlQuestionsAndAnswersUser);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/detail")
	public BaseResult detail(@RequestParam Integer id) {
		DlQuestionsAndAnswersUser dlQuestionsAndAnswersUser = dlQuestionsAndAnswersUserService.findById(id);
		return ResultGenerator.genSuccessResult(null, dlQuestionsAndAnswersUser);
	}

	@PostMapping("/list")
	public BaseResult list(@RequestBody StrParam strParam) {
		// Integer userId = SessionUtil.getUserId();
		Integer userId = 400093;
		List<UserPeriodDTO> list = dlQuestionsAndAnswersUserService.findByUserId(userId);
		return ResultGenerator.genSuccessResult(null, list);
	}

	@ApiOperation(value = "竞猜答题详情页", notes = "竞猜答题详情页")
	@PostMapping("/guessingCompetitionDetails")
	public BaseResult<MatchInfoDTO> guessingCompetitionDetails(@RequestBody MatchIdParam matchIdParam) {
		MatchInfoDTO matchInfo = dlQuestionsAndAnswersUserService.findMatchInfo(matchIdParam.getMatchId());
		DlQuestionsAndAnswers questionsAndAnswers = dlQuestionsAndAnswersService.getQuestionsAndAnswers(matchIdParam.getMatchId());
		if (questionsAndAnswers != null) {
			// 解析题干以及题设Json
			JSONArray jsonArray = JSONArray.fromObject(questionsAndAnswers.getQuestionAndAnswer());
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
			// Integer userId = SessionUtil.getUserId();
			Integer userId = 400093;
			// 判断用户是否登录
			if (userId != null) {
				// 查询用户是否答题
				DlQuestionsAndAnswersUser questionsAndAnswersUser = dlQuestionsAndAnswersUserService.getQuestionsAndAnswersForUser(userId, questionsAndAnswers.getId());
				// 如果不为空 则该用户答过题 将题干和该用户的答案返回
				if (questionsAndAnswersUser != null) {
					JSONArray jsonArrayForUser = JSONArray.fromObject(questionsAndAnswersUser.getUserAnswer());
					// 解析用户答案Json
					List<QuestionAndAnswersDTO> usesQuestionAndAnswerList = (List<QuestionAndAnswersDTO>) JSONArray.toCollection(jsonArrayForUser, QuestionAndAnswersDTO.class);
					if (questionsAndAnswers.getStatus() == 2) {
						matchInfo.setAnswerStatus(1);
						matchInfo.setUserAnswersList(usesQuestionAndAnswerList);
					} else {
						matchInfo.setAnswerStatus(0);
						// 用户答案转成Map
						Map<Integer, QuestionAndAnswersDTO> usesQuestionAndAnswerMap = new HashMap<Integer, QuestionAndAnswersDTO>(usesQuestionAndAnswerList.size());
						usesQuestionAndAnswerList.forEach(item -> usesQuestionAndAnswerMap.put(item.getQuestionNum(), item));
						// 将用户答案写到题干
						for (int i = 0; i < questionAndAnswerList.size(); i++) {
							QuestionAndAnswersDTO userAnswer = new QuestionAndAnswersDTO();
							userAnswer = usesQuestionAndAnswerMap.get(questionAndAnswerList.get(i).getQuestionNum());
							questionAndAnswerList.get(i).setAnswerStatus1(userAnswer.getAnswerStatus1());
							questionAndAnswerList.get(i).setAnswerStatus2(userAnswer.getAnswerStatus2());
						}
					}
				}
				// String currentDate = DateUtilNew.getCurrentYearMonthDay();
				String currentDate = "2018-05-08";
				BigDecimal bigAmount = dlQuestionsAndAnswersUserService.getTodayAllOrderAmount(userId, currentDate);
				if (bigAmount == null) {
					bigAmount = new BigDecimal(0);
					matchInfo.setOnceBettingAmount("0");
					matchInfo.setMultiple(0);
				} else {
					BigDecimal onceBettingAmount = questionsAndAnswers.getLimitLotteryAmount().subtract(bigAmount.divideAndRemainder(questionsAndAnswers.getLimitLotteryAmount())[1].setScale(0, BigDecimal.ROUND_HALF_UP));
					Integer num = bigAmount.divide(questionsAndAnswers.getLimitLotteryAmount()).intValue();
					matchInfo.setOnceBettingAmount(onceBettingAmount.toString());
					matchInfo.setMultiple(num + 1);
				}
			}
			matchInfo.setQuestionAndAnswersList(questionAndAnswerList);
		}
		matchInfo.setStopTime(questionsAndAnswers.getEndTime());
		return ResultGenerator.genSuccessResult(null, matchInfo);
	}
}
