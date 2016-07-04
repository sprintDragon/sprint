package com.sprint.trace.domain;

/**
 * Created by wangdi on 16-7-4.
 */
public class TraceCell {

    private String traceId;
    private String ip;
    private String app;
    private String context;

    @Override
    public String toString() {
        return "TraceCell{" +
                "traceId='" + traceId + '\'' +
                ", ip='" + ip + '\'' +
                ", app='" + app + '\'' +
                ", context='" + context + '\'' +
                '}';
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
