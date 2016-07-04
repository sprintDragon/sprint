package com.sprint.trace.aspect;

import com.lmax.disruptor.dsl.Disruptor;
import com.sprint.trace.biz.TraceCellEventHandler;
import com.sprint.trace.biz.TraceCellEventTranslator;
import com.sprint.trace.domain.TraceCell;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * User: wangdi
 * Date: 16/6/15
 * Time: 上午11:07
 * Email: yfwangdi@jd.com
 */
@Aspect
@Component
public class TraceableAspect {

    @Resource
    Disruptor disruptor;

    /**
     * 暂时 @After 应该比较合适,但是未来不一定,所以暂时使用熟练的 @Around
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "(execution(public * ((@Traceable *)+).*(..)) && within(@Traceable *)) || @annotation(Traceable)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object ret = null;
        Throwable ree = null;
        try {
            ret = pjp.proceed();
            return ret;
        } catch (Exception e) {
            ree = e;
            throw e;
        } catch (Throwable e) {
            ree = e;
            throw e;
        } finally {
            pushKafka(pjp, ret, ree);
        }
    }

    private void pushKafka(ProceedingJoinPoint pjp, Object ret, Throwable ree) {
        String key = getTrackId();
        if (key == null) {
            return;
        }
        disruptor.publishEvent(new TraceCellEventTranslator(fixTraceInfo(key, pjp.getArgs(), ret, ree)));
    }

    private TraceCell fixTraceInfo(String traceId, Object[] args, Object ret, Throwable ree) {
        TraceCell traceCell = new TraceCell();
        traceCell.setTraceId(traceId);
        traceCell.setApp("demo");
        traceCell.setIp("127.0.0.1");
        StringBuilder sb = new StringBuilder();
        for (Object obj : args) {
            sb.append(String.valueOf(obj)).append(",");
        }
        sb.append(ret).append(",").append(ree);
        traceCell.setContext(sb.toString());
        return traceCell;
    }

    /**
     * trackId 会首先检查 JSF 的上下文中是否存在,如果存在会继续使用同一个 TRAID
     *
     * @return
     */
    private String getTrackId() {
        return UUID.randomUUID().toString();
    }
}
