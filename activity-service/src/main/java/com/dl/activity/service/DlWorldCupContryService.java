package com.dl.activity.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
}
