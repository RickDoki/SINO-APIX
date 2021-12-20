package com.sinosdx.service.authentication.service;

import com.sinosdx.service.authentication.dao.entity.LoginHistory;
import com.sinosdx.service.authentication.result.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wendy
 * @date 2021/1/12
 */
@RestController
@RequestMapping("/auth/history/login")
public interface LoginHistoryService {

    /**
     * 查询用户的登录历史
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    R<List<LoginHistory>> getLoginHistoryByUserId(@PathVariable("userId") Integer userId);

    /**
     * 插入新的登录记录
     *
     * @param loginHistory
     * @return
     */
    @PostMapping("/insert")
    R<Object> insertNewLoginHistory(@RequestBody LoginHistory loginHistory);
}
