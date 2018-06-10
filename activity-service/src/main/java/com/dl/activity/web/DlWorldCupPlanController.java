package com.dl.activity.web;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.activity.service.DlWorldCupPlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2018/06/10.
*/
@RestController
@RequestMapping("/dl/world/cup/plan")
public class DlWorldCupPlanController {
    @Resource
    private DlWorldCupPlanService dlWorldCupPlanService;

    @PostMapping("/add")
    public BaseResult add(DlWorldCupPlan dlWorldCupPlan) {
        dlWorldCupPlanService.save(dlWorldCupPlan);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public BaseResult delete(@RequestParam Integer id) {
        dlWorldCupPlanService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public BaseResult update(DlWorldCupPlan dlWorldCupPlan) {
        dlWorldCupPlanService.update(dlWorldCupPlan);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public BaseResult detail(@RequestParam Integer id) {
        DlWorldCupPlan dlWorldCupPlan = dlWorldCupPlanService.findById(id);
        return ResultGenerator.genSuccessResult(null,dlWorldCupPlan);
    }

    @PostMapping("/list")
    public BaseResult list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<DlWorldCupPlan> list = dlWorldCupPlanService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(null,pageInfo);
    }
}
