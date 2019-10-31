package com.uca.network.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
    @Value("${db.url}")
    private String URL;
    @Value("${db.driverClassName}")
    private String DRIVER_CLASS_NAME;
    @Value("${db.username}")
    private String DB_USER_NAME ;
    @Value("${db.password}")
    private String DB_USER_PASSWORD;
    @Value("${db.initialSize}")
    private String DB_INITIALSIZE;
    @Value("${db.minIdle}")
    private String DB_MINIDLE  ;
    @Value("${db.maxActive}")
    private String DB_MAXACTIVE ;
    @Value("${db.maxWait}")
    private String DB_MAXWAIT;
    @Value("${db.timeBetweenEvictionRunsMillis}")
    private String DB_TIMEBETWEENEVICTIONRUNSMILLIS ;
    @Value("${db.minEvictableIdleTimeMillis}")
    private String DB_MINEVICTABLEIDLETIMEMILLIS;
    @Value("${db.validationQuery}")
    private String DB_VALIDATIONQUERY;
    @Value("${db.testWhileIdle}")
    private String DB_TESTWHILEIDLE;
    @Value("${db.testOnBorrow}")
    private String DB_TESTONBORROW ;
    @Value("${db.testOnReturn}")
    private String DB_TESTONRETURN ;
    @Value("${db.poolPreparedStatements}")
    private String DB_POOLPREPAREDSTATEMENTS;
    @Value("${db.filters}")
    private String DB_FILTERS ;

    @Value("${db.logSlowSql}")
    private Boolean DB_LOG_SLOWSQL = true;

    @Value("${db.slowSqlMillis}")
    private Long DB_SLOW_SQL__MILLIS;

    public String getDRIVER_CLASS_NAME() {
        return DRIVER_CLASS_NAME;
    }

    public void setDRIVER_CLASS_NAME(String DRIVER_CLASS_NAME) {
        this.DRIVER_CLASS_NAME = DRIVER_CLASS_NAME;
    }

    public String getDB_USER_NAME() {
        return DB_USER_NAME;
    }

    public void setDB_USER_NAME(String DB_USER_NAME) {
        this.DB_USER_NAME = DB_USER_NAME;
    }

    public String getDB_USER_PASSWORD() {
        return DB_USER_PASSWORD;
    }

    public void setDB_USER_PASSWORD(String DB_USER_PASSWORD) {
        this.DB_USER_PASSWORD = DB_USER_PASSWORD;
    }

    public String getDB_INITIALSIZE() {
        return DB_INITIALSIZE;
    }

    public void setDB_INITIALSIZE(String DB_INITIALSIZE) {
        this.DB_INITIALSIZE = DB_INITIALSIZE;
    }

    public String getDB_MINIDLE() {
        return DB_MINIDLE;
    }

    public void setDB_MINIDLE(String DB_MINIDLE) {
        this.DB_MINIDLE = DB_MINIDLE;
    }

    public String getDB_MAXACTIVE() {
        return DB_MAXACTIVE;
    }

    public void setDB_MAXACTIVE(String DB_MAXACTIVE) {
        this.DB_MAXACTIVE = DB_MAXACTIVE;
    }

    public String getDB_MAXWAIT() {
        return DB_MAXWAIT;
    }

    public void setDB_MAXWAIT(String DB_MAXWAIT) {
        this.DB_MAXWAIT = DB_MAXWAIT;
    }

    public String getDB_TIMEBETWEENEVICTIONRUNSMILLIS() {
        return DB_TIMEBETWEENEVICTIONRUNSMILLIS;
    }

    public void setDB_TIMEBETWEENEVICTIONRUNSMILLIS(String DB_TIMEBETWEENEVICTIONRUNSMILLIS) {
        this.DB_TIMEBETWEENEVICTIONRUNSMILLIS = DB_TIMEBETWEENEVICTIONRUNSMILLIS;
    }

    public String getDB_MINEVICTABLEIDLETIMEMILLIS() {
        return DB_MINEVICTABLEIDLETIMEMILLIS;
    }

    public void setDB_MINEVICTABLEIDLETIMEMILLIS(String DB_MINEVICTABLEIDLETIMEMILLIS) {
        this.DB_MINEVICTABLEIDLETIMEMILLIS = DB_MINEVICTABLEIDLETIMEMILLIS;
    }

    public String getDB_VALIDATIONQUERY() {
        return DB_VALIDATIONQUERY;
    }

    public void setDB_VALIDATIONQUERY(String DB_VALIDATIONQUERY) {
        this.DB_VALIDATIONQUERY = DB_VALIDATIONQUERY;
    }

    public String getDB_TESTWHILEIDLE() {
        return DB_TESTWHILEIDLE;
    }

    public void setDB_TESTWHILEIDLE(String DB_TESTWHILEIDLE) {
        this.DB_TESTWHILEIDLE = DB_TESTWHILEIDLE;
    }

    public String getDB_TESTONBORROW() {
        return DB_TESTONBORROW;
    }

    public void setDB_TESTONBORROW(String DB_TESTONBORROW) {
        this.DB_TESTONBORROW = DB_TESTONBORROW;
    }

    public String getDB_TESTONRETURN() {
        return DB_TESTONRETURN;
    }

    public void setDB_TESTONRETURN(String DB_TESTONRETURN) {
        this.DB_TESTONRETURN = DB_TESTONRETURN;
    }

    public String getDB_POOLPREPAREDSTATEMENTS() {
        return DB_POOLPREPAREDSTATEMENTS;
    }

    public void setDB_POOLPREPAREDSTATEMENTS(String DB_POOLPREPAREDSTATEMENTS) {
        this.DB_POOLPREPAREDSTATEMENTS = DB_POOLPREPAREDSTATEMENTS;
    }

    public String getDB_FILTERS() {
        return DB_FILTERS;
    }

    public void setDB_FILTERS(String DB_FILTERS) {
        this.DB_FILTERS = DB_FILTERS;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Boolean getDB_LOG_SLOWSQL() {
        return DB_LOG_SLOWSQL;
    }

    public void setDB_LOG_SLOWSQL(Boolean DB_LOG_SLOWSQL) {
        this.DB_LOG_SLOWSQL = DB_LOG_SLOWSQL;
    }

    public Long getDB_SLOW_SQL__MILLIS() {
        return DB_SLOW_SQL__MILLIS;
    }

    public void setDB_SLOW_SQL__MILLIS(Long DB_SLOW_SQL__MILLIS) {
        this.DB_SLOW_SQL__MILLIS = DB_SLOW_SQL__MILLIS;
    }
}
