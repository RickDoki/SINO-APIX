package com.sinosdx.service.management.controller;//package com.sinosdx.middle.application.controller;


import com.sinosdx.service.management.dao.entity.Door;
import com.sinosdx.service.management.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * @author wendy
 * @date 2020/12/24
 */
@RestController
@RequestMapping("/app/door")
public class DoorController {
//
    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String TITLE = "door:title";
    private static final String DESCRIPTION = "door:description";

    /**
     * 创建平台配置
     *
     * @param door
     * @return
     */
    @PostMapping()
    public R<Object> createDoor(@RequestBody Door door) {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set(TITLE,door.getTitle());
        stringStringValueOperations.set(DESCRIPTION,door.getDescription());
        return R.success(door);
    }


    /**
     * 更新平台配置
     *
     * @param door
     * @return
     */
    @PutMapping()
    public R<Object> updateDoor(@RequestBody Door door) {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set(TITLE,door.getTitle());
        stringStringValueOperations.set(DESCRIPTION,door.getDescription());
        return R.success(door);
    }


    /**
     * 更新平台配置
     *
     * @return
     */
    @GetMapping()
    public R<Object> getDoor() {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        Door door = new Door().setTitle(stringStringValueOperations.get(TITLE)).setDescription(stringStringValueOperations.get(DESCRIPTION));
        return R.success(door);
    }

}
