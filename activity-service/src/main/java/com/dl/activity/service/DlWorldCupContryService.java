package com.dl.activity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.dao2.DlWorldCupContryMapper;
import com.dl.activity.dto.WCContryDTO;
import com.dl.activity.model.DlWorldCupContry;
import com.dl.base.service.AbstractService;

@Service
public class DlWorldCupContryService extends AbstractService<DlWorldCupContry> {
	@Resource
	private DlWorldCupContryMapper dlWorldCupContryMapper;

	@Transactional(value="transactionManager2",readOnly=true)
	public List<WCContryDTO> wcContryList() {
		List<WCContryDTO> dtos = new ArrayList<WCContryDTO>(0);
		List<DlWorldCupContry> wcContrys = dlWorldCupContryMapper.selectAll();
		if(CollectionUtils.isNotEmpty(wcContrys)) {
			dtos = new ArrayList<WCContryDTO>(wcContrys.size());
			for(DlWorldCupContry contry: wcContrys) {
				WCContryDTO dto = new WCContryDTO();
				dto.setContryName(contry.getContryName());
				dto.setContryPic(contry.getContryPic());
				dto.setCountryId(contry.getCountryId());
				dto.setIs1(contry.getIs1());
				dto.setIs16(contry.getIs16());
				dto.setIs2(contry.getIs2());
				dto.setIs4(contry.getIs4());
				dto.setIs8(contry.getIs8());
				dtos.add(dto);
			}
		}
		return dtos;
	}
	private Integer isGetValue(String str) {
		if(StringUtils.isNotBlank(str) && !str.equals("0")) {
			return 1;
		}
		return 0;
	}
	@Transactional(value="transactionManager2",readOnly=true)
	public Map<Integer, WCContryDTO> wcContryMap() {
		Map<Integer, WCContryDTO> map = new HashMap<Integer, WCContryDTO>(0);
		List<DlWorldCupContry> wcContrys = dlWorldCupContryMapper.selectAll();
		if(CollectionUtils.isNotEmpty(wcContrys)) {
			map = new HashMap<Integer, WCContryDTO>(wcContrys.size());
			for(DlWorldCupContry contry: wcContrys) {
				WCContryDTO dto = new WCContryDTO();
				dto.setContryName(contry.getContryName());
				dto.setContryPic(contry.getContryPic());
				dto.setCountryId(contry.getCountryId());
				dto.setIs1(contry.getIs1());
				dto.setIs16(contry.getIs16());
				dto.setIs2(contry.getIs2());
				dto.setIs4(contry.getIs4());
				dto.setIs8(contry.getIs8());
				map.put(contry.getCountryId(), dto);
			}
		}
		return map;
	}
}
