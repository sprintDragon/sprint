package com.sprint.trace.aspect.ano;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * User: wangdi
 * Date: 16/6/15
 * Time: 上午11:07
 * Email: yfwangdi@jd.com
 */
@Aspect
@Component
public class TraceableAspect {

    /**
     * 暂时 @After 应该比较合适,但是未来不一定,所以暂时使用熟练的 @Around
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "(execution(public * ((@Traceable *)+).*(..)) && within(@Traceable *)) || @annotation(Traceable)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            pushKafka(pjp, e);
            throw e;
        } catch (Throwable e) {
            throw e;
        } finally {
            pushKafka(pjp);
        }
    }

    private void pushKafka(ProceedingJoinPoint pjp) {
        pushKafka(pjp, null);
    }

    private void pushKafka(ProceedingJoinPoint pjp, Exception e) {
        String key = getTrackId();
        if (key == null) {
            return;
        }
        Object[] args = pjp.getArgs();
        //todo and then
    }

    /**
     * trackId 会首先检查 JSF 的上下文中是否存在,如果存在会继续使用同一个 TRAID
     *
     * @return
     */
    private String getTrackId() {
        return "";
    }
}
