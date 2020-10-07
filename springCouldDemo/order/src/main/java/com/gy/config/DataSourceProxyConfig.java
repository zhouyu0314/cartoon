package com.gy.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HelloWoodes
 */
@Configuration
public class DataSourceProxyConfig {
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Bean("originXuzhou")
    @ConfigurationProperties(prefix = "spring.datasource.xuzhou")
    public DataSource dataSourceMaster() {
        return new DruidDataSource();
    }

    @Bean("originNanjing")
    @ConfigurationProperties(prefix = "spring.datasource.nanjing")
    public DataSource dataSourceStorage() {
        return new DruidDataSource();
    }

    @Bean("originBeijing")
    @ConfigurationProperties(prefix = "spring.datasource.beijing")
    public DataSource dataSourcePay() {
        return new DruidDataSource();
    }

    @Bean(name = "xuzhou")
    public DataSourceProxy masterDataSourceProxy(@Qualifier("originXuzhou") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "nanjing")
    public DataSourceProxy storageDataSourceProxy(@Qualifier("originNanjing") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "beijing")
    public DataSourceProxy payDataSourceProxy(@Qualifier("originBeijing") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("xuzhou") DataSource dataSourceOrder,
                                        @Qualifier("nanjing") DataSource dataSourceStorage,
                                        @Qualifier("beijing") DataSource dataSourcePay) {

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceKey.XUZHOU, dataSourceOrder);
        dataSourceMap.put(DataSourceKey.NANJING, dataSourceStorage);
        dataSourceMap.put(DataSourceKey.BEIJING, dataSourcePay);

        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceOrder);
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        DynamicDataSourceContextHolder.getDataSourceKeys().addAll(dataSourceMap.keySet());

        return dynamicRoutingDataSource;
    }

    @Bean
    //@ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());





        return sqlSessionFactoryBean;
    }

}