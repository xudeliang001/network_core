package com.uca.network.common.dataSource;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.collect.Lists;
import com.uca.network.common.config.DBConfig;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DBConfig dbConfig;

    public DruidDataSource initDataSource(String dataBase) {
        try {
            logger.info("DynamicRoutingDataSource initDataSources begin :{}",dataBase);
            Map<Object, Object> sourceMap = new HashMap<>();
            sourceMap.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, dbConfig.getDRIVER_CLASS_NAME());
            sourceMap.put(DruidDataSourceFactory.PROP_URL, String.format(dbConfig.getURL(), dataBase));
            sourceMap.put(DruidDataSourceFactory.PROP_USERNAME, dbConfig.getDB_USER_NAME());
            sourceMap.put(DruidDataSourceFactory.PROP_PASSWORD, dbConfig.getDB_USER_PASSWORD());
            sourceMap.put(DruidDataSourceFactory.PROP_INITIALSIZE, dbConfig.getDB_INITIALSIZE());
            sourceMap.put(DruidDataSourceFactory.PROP_MINIDLE, dbConfig.getDB_MINIDLE());
            sourceMap.put(DruidDataSourceFactory.PROP_MAXACTIVE, dbConfig.getDB_MAXACTIVE());
            sourceMap.put(DruidDataSourceFactory.PROP_MAXWAIT, dbConfig.getDB_MAXWAIT());
            sourceMap.put(DruidDataSourceFactory.PROP_TIMEBETWEENEVICTIONRUNSMILLIS, dbConfig.getDB_TIMEBETWEENEVICTIONRUNSMILLIS());
            sourceMap.put(DruidDataSourceFactory.PROP_MINEVICTABLEIDLETIMEMILLIS, dbConfig.getDB_MINEVICTABLEIDLETIMEMILLIS());
            sourceMap.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, dbConfig.getDB_VALIDATIONQUERY());
            sourceMap.put(DruidDataSourceFactory.PROP_TESTWHILEIDLE, dbConfig.getDB_TESTWHILEIDLE());
            sourceMap.put(DruidDataSourceFactory.PROP_TESTONBORROW, dbConfig.getDB_TESTONBORROW());
            sourceMap.put(DruidDataSourceFactory.PROP_TESTONRETURN, dbConfig.getDB_TESTONRETURN());
            sourceMap.put(DruidDataSourceFactory.PROP_POOLPREPAREDSTATEMENTS, dbConfig.getDB_POOLPREPAREDSTATEMENTS());
            sourceMap.put(DruidDataSourceFactory.PROP_FILTERS, dbConfig.getDB_FILTERS());
            StatFilter filter = new StatFilter();
            filter.setSlowSqlMillis(dbConfig.getDB_SLOW_SQL__MILLIS());
            filter.setLogSlowSql(dbConfig.getDB_LOG_SLOWSQL());
            DruidDataSource druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(sourceMap);
            druidDataSource.setFilters(dbConfig.getDB_FILTERS());
//            druidDataSource.setKeepAlive(true);
            druidDataSource.setProxyFilters(Lists.newArrayList(filter));
            druidDataSource.init();
            logger.info("DynamicRoutingDataSource initDataSource over");
            return druidDataSource;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("DynamicRoutingDataSource initDataSource error");
            return null;
        }
    }

}
