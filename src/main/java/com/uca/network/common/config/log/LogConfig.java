package com.uca.network.common.config.log;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "logging")
public class LogConfig {

    public static ExceptionLogConfig exception;

    public static AspectLogConfig aspect;


    public void setAspect(AspectLogConfig aspect) {
        LogConfig.aspect = aspect;
    }

    public AspectLogConfig getAspect() {
        return aspect;
    }

    public void setException(ExceptionLogConfig exception) {
        LogConfig.exception = exception;
    }

    public ExceptionLogConfig getException() {
        return exception;
    }

    public static class ExceptionLogConfig {

        public static boolean debug;

        public static String tag;

        public static boolean fullMessage;

        public static void setDebug(boolean debug) {
            ExceptionLogConfig.debug = debug;
        }

        public static void setTag(String tag) {
            ExceptionLogConfig.tag = tag;
        }

        public static void setFullMessage(boolean fullMessage) {
            ExceptionLogConfig.fullMessage = fullMessage;
        }
    }

    public static class AspectLogConfig {

        public static boolean debug;

        public static boolean turnOn;

        public static void setDebug(boolean debug) {
            AspectLogConfig.debug = debug;
        }

        public static void setTurnOn(boolean turnOn) {
            AspectLogConfig.turnOn = turnOn;
        }
    }
}
