package com.sprint.trace.aspect;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * User: wangdi
 * Date: 16/6/15
 * Time: 上午11:07
 * Email: yfwangdi@jd.com
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(2)
public @interface Traceable {


}
