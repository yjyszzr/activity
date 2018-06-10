package com.dl.activity.web;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.activity.model.DlWorldCupContry;
import com.dl.activity.service.DlWorldCupContryService;
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
@RequestMapping("/dl/world/cup/contry")
public class DlWorldCupContryController {
    @Resource
    private DlWorldCupContryService dlWorldCupContryService;

    @PostMapping("/add")
    public BaseResult add(DlWorldCupContry dlWorldCupContry) {
        dlWorldCupContryService.save(dlWorldCupContry);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public BaseResult delete(@RequestParam Integer id) {
        dlWorldCupContryService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public BaseResult update(DlWorldCupContry dlWorldCupContry) {
        dlWorldCupContryService.update(dlWorldCupContry);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public BaseResult detail(@RequestParam Integer id) {
        DlWorldCupContry dlWorldCupContry = dlWorldCupContryService.findById(id);
        return ResultGenerator.genSuccessResult(null,dlWorldCupContry);
    }

    @PostMapping("/list")
    public BaseResult list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<DlWorldCupContry> list = dlWorldCupContryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(null,pageInfo);
    }
}
