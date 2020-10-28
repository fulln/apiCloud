package com.fulln.me.web.service.msg.board;

import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.model.msg.board.MessageBoard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 留言板业务接口
 * @date 2019/5/22 23:14
 **/
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface MessageBoardService {

    @PostMapping("/msg/board/findAll")
    GlobalResult allList();

    @PostMapping("/msg/board/add")
    GlobalResult insert(@RequestBody MessageBoard info);

    @PostMapping("/msg/board/findByCondition")
    GlobalResult findByCondition(@RequestBody MessageBoard board);
}
