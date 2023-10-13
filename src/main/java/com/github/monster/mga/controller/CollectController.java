package com.github.monster.mga.controller;

import cn.hutool.core.date.DateUtil;
import com.github.monster.mga.core.pool.DynamicTaskPool;
import com.github.monster.mga.core.task.Task;
import com.github.monster.mga.core.util.SpringContextUtil;
import com.github.monster.mga.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Monster
 */
@RestController
@RequestMapping()
public class CollectController {

    @Resource
    private DynamicTaskPool dynamicTaskPool;

    @GetMapping("/test")
    public void startAttackCityStatistics() {

        Date startTime = DateUtil.offsetSecond(new Date(), 1).toCalendar().getTime();
        Task task = new Task("test", startTime, 0, 0, () -> {
            DemoService demoService = SpringContextUtil.getBean(DemoService.class);
            demoService.demo();
        });

        dynamicTaskPool.addOnce(task);
    }

    @GetMapping("/test/view")
    public ModelAndView test1() {
        return new ModelAndView("test");
    }

}
