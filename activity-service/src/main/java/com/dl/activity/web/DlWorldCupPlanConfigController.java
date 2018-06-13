package com.dl.activity.web;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.activity.model.DlWorldCupPlanConfig;
import com.dl.activity.service.DlWorldCupPlanConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2018/06/13.
*/
@RestController
@RequestMapping("/dl/world/cup/plan/config")
public class DlWorldCupPlanConfigController {
    @Resource
    private DlWorldCupPlanConfigService dlWorldCupPlanConfigService;

    @PostMapping("/add")
    public BaseResult add(DlWorldCupPlanConfig dlWorldCupPlanConfig) {
        dlWorldCupPlanConfigService.save(dlWorldCupPlanConfig);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public BaseResult delete(@RequestParam Integer id) {
        dlWorldCupPlanConfigService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public BaseResult update(DlWorldCupPlanConfig dlWorldCupPlanConfig) {
        dlWorldCupPlanConfigService.update(dlWorldCupPlanConfig);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public BaseResult detail(@RequestParam Integer id) {
        DlWorldCupPlanConfig dlWorldCupPlanConfig = dlWorldCupPlanConfigService.findById(id);
        return ResultGenerator.genSuccessResult(null,dlWorldCupPlanConfig);
    }

    @PostMapping("/list")
    public BaseResult list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<DlWorldCupPlanConfig> list = dlWorldCupPlanConfigService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(null,pageInfo);
    }
}
