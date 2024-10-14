package com.example.law.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.law.dao.UserRepository;
import com.example.law.pojo.entity.User;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.common.ResultResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
@Slf4j
public class StaffHeartBeatController {
    @Autowired
    private UserRepository userRepository;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Builder
    public static class StaffHeartBeat {
        private String staffId;
        private Date lastHeartBeatTime;
    }

    //定义一个全局计数器，每次调用累加
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    //定义服务器列表
    private static final ConcurrentLinkedQueue<StaffHeartBeat> staffList = new ConcurrentLinkedQueue<StaffHeartBeat>();
    private static List<User> allStaff = new ArrayList<>();

    @SaCheckRole(value = {"staff"})
    @GetMapping("/staff/heartbeat")
    public ResultResponse heartBeat() {
        String userId = (String) StpUtil.getLoginId();
        for (StaffHeartBeat staffHeartBeat : staffList) {
            if (staffHeartBeat.getStaffId().equals(userId)) {
                staffHeartBeat.setLastHeartBeatTime(new Date());
                return ResultResponse.success("心跳成功");
            }
        }
        staffList.add(StaffHeartBeat.builder().staffId(userId).lastHeartBeatTime(new Date()).build());
        return ResultResponse.success("心跳成功");
    }

    // 轮询的方式获得一位在线的工作人员，如果没有工作人员在线就轮询返回一个工作人员
    @GetMapping("/staff")
    public ResultResponse getStaff() {
        if (staffList.isEmpty()) {
            if (allStaff == null || allStaff.isEmpty()) {
                allStaff = userRepository.findByRole(User.Role.STAFF);
            }
            return ResultResponse.success(allStaff.get(RandomUtil.randomInt(0, allStaff.size())).getUserId());
        }
        synchronized (StaffHeartBeatController.class) {
            Iterator<StaffHeartBeat> iterator = staffList.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                StaffHeartBeat staffHeartBeat = iterator.next();
                if (i == atomicInteger.get()) {
                    atomicInteger.getAndUpdate(x -> (x + 1) % staffList.size());
                    return ResultResponse.success(staffHeartBeat.getStaffId());
                }
                i++;
            }
            throw new BizException(ExceptionEnum.STAFF_POLL_ERROR);
        }
    }

    // 每5分钟清除一次不在线的工作人员
    @Scheduled(fixedRate = 300000)
    public void clearNotOnlineStaff() {
        log.info("clear not online staff");
        if (staffList.isEmpty()) {
            return;
        }
        Date now = new Date();
        staffList.removeIf(staffHeartBeat -> now.getTime() - staffHeartBeat.getLastHeartBeatTime().getTime() > 300000);
    }

    //每一小时删除工作人员，考虑新增工作人员的情况
    @Scheduled(cron = "0 0 */1 * * ?")
    public void updateAllStaff() {
        log.info("clear all staff");
        allStaff.clear();
    }

}
