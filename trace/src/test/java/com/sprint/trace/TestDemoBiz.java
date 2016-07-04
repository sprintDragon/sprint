package com.sprint.trace;

import com.sprint.trace.api.IDemoBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wangdi on 16-7-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class TestDemoBiz {

    @Resource
    IDemoBiz iDemoBiz;

    @Test
    public void testDemo() throws Exception {
        iDemoBiz.queryOrder(22l, 11l);
    }
}
