package com.uca.network.common.config.log;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
class LogAspect {

    @Pointcut("execution(public * com.uca.network.*.controller.*.*(..))")
    void logPointControllerCut() {
    }

    @Pointcut("execution(public * com.uca.network.*.service.*.*(..))")
    void logPointServiceCut() {
    }

    @Pointcut("execution(public * com.uca.network.common.*.RestClient.*(..))")
    void logPointClientCut() {
    }

    @Around("logPointControllerCut()")
    Object logPointControllerCut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return LogConfig.AspectLogConfig.turnOn ? doAround(proceedingJoinPoint, 1) : proceedingJoinPoint.proceed();
    }

    @Around("logPointServiceCut()")
    Object logPointServiceCut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return LogConfig.AspectLogConfig.turnOn ? doAround(proceedingJoinPoint, 2) : proceedingJoinPoint.proceed();
    }

    @Around("logPointClientCut()")
    Object logPointClientCut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return LogConfig.AspectLogConfig.turnOn ? doAround(proceedingJoinPoint, 3) : proceedingJoinPoint.proceed();
    }

    Object doAround(ProceedingJoinPoint proceedingJoinPoint, int flag) throws Throwable {
        String beforePerfix = "", afterPerfix = "";
        switch (flag) {
            case 1:
                beforePerfix = "=>";
                afterPerfix = "<=";
                break;
            case 2:
                beforePerfix = "==>";
                afterPerfix = "<==";
                break;
            case 3:
                beforePerfix = "===>";
                afterPerfix = "<===";
                break;
        }
        Class methodClass = proceedingJoinPoint.getTarget().getClass();
        Logger log = LoggerFactory.getLogger(methodClass);
        long startTime = System.currentTimeMillis();
        String method = methodClass.getName() + "." + proceedingJoinPoint.getSignature().getName() + "()";
        StringBuffer stringBuffer = new StringBuffer();
        if (LogConfig.AspectLogConfig.debug) {
            stringBuffer.append("|-");
            for (Object o : proceedingJoinPoint.getArgs()) {
                try {
                    stringBuffer.append(JSON.toJSONString(o));
                } catch (Exception e) {
                    stringBuffer.append("该参数不支持解析！");
                }
                stringBuffer.append("-");
            }
            stringBuffer.append("-|");
            log.info(beforePerfix + "> Params: " + stringBuffer.toString());
        }
        log.info(beforePerfix + "    S:0    " + method);
        Object ob = proceedingJoinPoint.proceed();
        log.info(afterPerfix + "    S:" + (System.currentTimeMillis() - startTime) + "  " + method);
        String s;
        try {
            s = JSON.toJSONString(ob);
        } catch (Exception e) {
            s = "该返回解析失败！";
        }
        if (LogConfig.AspectLogConfig.debug) {
            log.info("<" + afterPerfix + " Result: " + s);
        }
        return ob;
    }
}
