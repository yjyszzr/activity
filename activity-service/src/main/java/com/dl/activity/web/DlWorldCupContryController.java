package com.dl.activity.web;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.activity.dto.WCContryDTO;
import com.dl.activity.model.DlWorldCupContry;
import com.dl.activity.param.EmptyParam;
import com.dl.activity.service.DlWorldCupContryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2018/06/10.
*/
@RestController
@RequestMapping("/dl/activity/contry")
public class DlWorldCupContryController {
    @Resource
    private DlWorldCupContryService dlWorldCupContryService;


    @PostMapping("/list")
    public BaseResult<List<WCContryDTO>> list(@RequestBody EmptyParam param) {
       List<WCContryDTO> wcContryList = dlWorldCupContryService.wcContryList();
        return ResultGenerator.genSuccessResult("success",wcContryList);
    }
}
