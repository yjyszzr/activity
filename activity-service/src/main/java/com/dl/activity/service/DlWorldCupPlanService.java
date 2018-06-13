package com.dl.activity.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.dao.DlWorldCupPlanMapper;
import com.dl.activity.dto.WCContryDTO;
import com.dl.activity.dto.WCPlanCellDTO;
import com.dl.activity.dto.WCPlanDTO;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.base.service.AbstractService;
import com.dl.base.util.DateUtil;
import com.dl.base.util.SessionUtil;

import tk.mybatis.mapper.entity.Condition;

@Service
// @Transactional(value = "transactionManager1")
public class DlWorldCupPlanService extends AbstractService<DlWorldCupPlan> {
	@Resource
	private DlWorldCupPlanMapper dlWorldCupPlanMapper;
	
	@Resource
	private DlWorldCupContryService dlWorldCupContryService;
	
	@Transactional(value = "transactionManager1")
	public Integer saveWorldCupPlan(String palnStr) {
		Integer userId = SessionUtil.getUserId();
		Integer addTime = DateUtil.getCurrentTimeLong();
		DlWorldCupPlan worldCupPlan = new DlWorldCupPlan();
		worldCupPlan.setUserId(userId);
		worldCupPlan.setAddTime(addTime);
		worldCupPlan.setModifyTime(addTime);
		worldCupPlan.setPlanJson(palnStr);
		dlWorldCupPlanMapper.insertWorldCupPlan(worldCupPlan);
		return worldCupPlan.getId();
	}

	/**
	 * 获取投注列表
	 * @param userId
	 * @return
	 */
	public List<WCPlanDTO> worldCupPlanList(Integer userId){
		List<WCPlanDTO> dtos = new ArrayList<WCPlanDTO>(0);
		List<DlWorldCupPlan> worldCupPlanList = dlWorldCupPlanMapper.getWorldCupPlanList(userId);
		if(CollectionUtils.isNotEmpty(worldCupPlanList)) {
			Map<Integer, WCContryDTO> contryMap = dlWorldCupContryService.wcContryMap();
			dtos = new ArrayList<WCPlanDTO>(worldCupPlanList.size());
			for(DlWorldCupPlan plan: worldCupPlanList) {
				WCPlanDTO dto = new WCPlanDTO();
				Integer addTime = plan.getAddTime();
				LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(Long.valueOf(addTime), 0, ZoneOffset.ofHours(8));
				String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				dto.setAddTime(format);
				String planJson = plan.getPlanJson();
				this.parsePlanJson(planJson, dto, contryMap);
				Integer isOpen = plan.getIsOpen();
				if(1==isOpen) {
					dto.setRst1(plan.getStatusGj());
					dto.setRst16(plan.getStatus16());
					dto.setRst2(plan.getStatusGyj());
					dto.setRst4(plan.getStatus4());
					dto.setRst8(plan.getStatus8());
				}
				dtos.add(dto);
			}
		}
		return dtos;
	}
	//16|A1:396,A2:543,B1:424,B2:1044,C1:375,C2:409,D1:413,D2:366,E1:417,E2:420,F1:377,F2:403,G1:363,G2:425,H1:392,H2:410;8|A1:396,A2:424,B1:375,B2:413,C1:417,C2:377,D1:363,D2:392;4|A1:396,A2:375,B1:417,B2:363;2|A1:396,A2:417;1|A1:396
	private void parsePlanJson(String planJson, WCPlanDTO planDto, Map<Integer, WCContryDTO> contryMap) {
		String[] cells = planJson.split(";");
		for(String cell: cells) {
			String[] split = cell.split("\\|");
			int parseInt = Integer.parseInt(split[0]);
			if(1 == parseInt) {
				String[] contrys = split[1].split(":");
				Integer contryId = Integer.parseInt(contrys[1]);
				WCContryDTO contryDto = contryMap.get(contryId);
				WCPlanCellDTO dto = new WCPlanCellDTO(contryId, contryDto.getContryName());
				dto.setIsGet(0);
				String is1 = contryDto.getIs1();
				if("A1".equals(is1)) {
					dto.setIsGet(1);
				}
				planDto.setPlan1(dto);
				continue;
			}
			List<WCPlanCellDTO> plans = null;
			if(16 == parseInt) {
				plans = new ArrayList<WCPlanCellDTO>(16);
				planDto.setPlan16(plans);
			}else if(8 == parseInt) {
				plans = new ArrayList<WCPlanCellDTO>(8);
				planDto.setPlan8(plans);
			}else if(4 == parseInt) {
				plans = new ArrayList<WCPlanCellDTO>(4);
				planDto.setPlan4(plans);
			}else if(2 == parseInt) {
				plans = new ArrayList<WCPlanCellDTO>(2);
				planDto.setPlan2(plans);
			}
			String[] contrys = split[1].split(",");
			for(String contry: contrys) {
				String[] split2 = contry.split(":");
				Integer contryId = Integer.parseInt(split2[1]);
				WCContryDTO contryDto = contryMap.get(contryId);
				if(contryDto == null) {
					System.out.println(contryId);
				}
				WCPlanCellDTO dto = new WCPlanCellDTO(contryId, contryDto.getContryName());
				Integer isGet = this.getIsGet(contryDto, parseInt);
				dto.setIsGet(isGet);
				plans.add(dto);
			}
		}
	}

	/**
	 * 获取猜中的值 
	 */
	private Integer getIsGet(WCContryDTO contryDto, int parseInt) {
		Integer isGet = 0;
		if(16 == parseInt) {
			String is16 = contryDto.getIs16();
			if(StringUtils.isNotBlank(is16) && !is16.equals("0")) {
				isGet = 1;
			}
		}else if(8 == parseInt) {
			String is8 = contryDto.getIs8();
			if(StringUtils.isNotBlank(is8) && !is8.equals("0")) {
				isGet = 1;
			}
		}else if(4 == parseInt) {
			String is4 = contryDto.getIs4();
			if(StringUtils.isNotBlank(is4) && !is4.equals("0")) {
				isGet = 1;
			}
		}else if(2 == parseInt) {
			String is2 = contryDto.getIs2();
			if(StringUtils.isNotBlank(is2) && !is2.equals("0")) {
				isGet = 1;
			}
		}
		return isGet;
	}
	public BigDecimal findAllOrderAmount(Integer userId, Integer startTime) {
		return dlWorldCupPlanMapper.findAllOrderAmount(userId, startTime);
	}

	public List<DlWorldCupPlan> findByUserId(Integer userId) {
		Condition condition = new Condition(DlWorldCupPlan.class);
		condition.createCriteria().andEqualTo("userId", userId);
		return this.findByCondition(condition);
	}
}
