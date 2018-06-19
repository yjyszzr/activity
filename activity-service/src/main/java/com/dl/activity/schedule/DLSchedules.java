package com.dl.activity.schedule;

import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.dl.base.param.EmptyParam;
import com.dl.lottery.api.ILotteryPrintService;
import com.dl.lottery.api.ILotteryRewardService;
import com.dl.member.api.IUserBonusService;
import com.dl.order.api.IOrderService;
import com.dl.shop.payment.api.IpaymentService;
import lombok.extern.slf4j.Slf4j;

/**
 * 各个工程的定时任务集中在这里管理
 * @author zzr
 *
 */
@Slf4j
@Configuration
@EnableScheduling
public class DLSchedules {
	
	@Resource
	private IUserBonusService userBonusService;
	
	@Resource
	private ILotteryRewardService lotteryRewardService;
	
	@Resource
	private ILotteryPrintService lotteryPrintService;
	
	@Resource
	private IOrderService orderService;
	
	@Resource
	private IpaymentService paymentService;

	
    /****************调用用户**************/
	/**
	 * 更新过期的红包
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	public void updateBonusExpire() {
		log.info("更新过期的红包定时任务开始");
		EmptyParam emptyParam = new EmptyParam();
		userBonusService.updateBonusExpire(emptyParam);
		log.info("更新过期的红包的定时任务结束");
	}
	
	/****************调用彩票**************/
	/**
	 * 出票任务 ,调用第三方接口出票定时任务
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
    public void printLottery() {
		EmptyParam emptyParam = new EmptyParam();
		lotteryPrintService.printLottery(emptyParam);
    }
	
	
	/**
	 * 更新待开奖的订单
	 * 
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void updateOrderAfterOpenReward() {
		log.info("更新待开奖的订单开始");
		EmptyParam emptyParam = new EmptyParam();
		lotteryRewardService.updateOrderAfterOpenReward(emptyParam);
		log.info("更新待开奖的订单结束");
	}
	
	/**
	 * 更新彩票信息
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void updatePrintLotteryCompareStatus() {
		log.info("更新彩票信息，彩票对奖开始");
		EmptyParam emptyParam = new EmptyParam();
		lotteryPrintService.updatePrintLotteryCompareStatus(emptyParam);
		log.info("更新彩票信息，彩票对奖结束");			
	}
	
	/****************调用订单**************/
	/**
	 * 订单详情赛果 （每5分钟执行一次）
	 */
	@Scheduled(cron = "0 0/2 * * * ?")
    public void updateOrderMatchResult() {
		log.info("开始执行更新订单详情赛果任务");
		EmptyParam emptyParam = new EmptyParam();
		orderService.updateOrderMatchResult(emptyParam);
		log.info("结束执行更新订单详情赛果任务");
	}
	
	/**
	 * 订单出票结果 ,这里暂时主要处理出票失败的订单
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	public void refreshOrderPrintStatus() {
		log.info("开始执行更新订单出票结果任务");
		EmptyParam emptyParam = new EmptyParam();
		orderService.refreshOrderPrintStatus(emptyParam);
		log.info("结束执行更新订单出票结果任务");
	}
	
	/**
	 * 更新中奖用户的账户
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void addRewardMoneyToUsers() {
		log.info("更新中奖用户的账户，派奖开始");
		EmptyParam emptyParam = new EmptyParam();
		orderService.addRewardMoneyToUsers(emptyParam);
		log.info("更新中奖用户的账户，派奖结束");
	}
	
	/****************调用支付**************/
	@Scheduled(cron = "0 0/2 * * * ?")
    public void dealBeyondPayTimeOrderOut() {
		log.info("开始执行混合支付超时订单任务");
		EmptyParam emptyParam = new EmptyParam();
		paymentService.dealBeyondPayTimeOrderOut(emptyParam);
		log.info("结束执行支混合付超时订单任务");
	}	
	
	/**
	 * 第三方支付的query
	 */
	@Scheduled(fixedRate = 1000*5)
    public void timerOrderQueryScheduled() {
		EmptyParam emptyParam = new EmptyParam();
		paymentService.timerCheckCashReq(emptyParam);
	}
	
	/**
	 * 提现状态轮询
	 */
	@Scheduled(fixedRate = 1000*20)
    public void timerCheckCashReq() {
		EmptyParam emptyParam = new EmptyParam();
		paymentService.timerCheckCashReq(emptyParam);
	}
	
}
