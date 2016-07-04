package com.sprint.trace.biz;

import com.sprint.trace.api.IDemoBiz;
import com.sprint.trace.aspect.Traceable;
import org.springframework.stereotype.Service;

/**
 * Created by wangdi on 16-7-4.
 */
@Service
public class DemoBiz implements IDemoBiz {

    @Traceable
    public String queryOrder(Long orderId, Long vendorId) {
        return orderId + ",time:" + System.currentTimeMillis();
    }

}
