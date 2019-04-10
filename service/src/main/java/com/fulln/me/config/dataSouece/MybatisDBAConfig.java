package com.fulln.me.config.dataSouece;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源1的配置
 */
@Configuration
@MapperScan(basePackages = {"com.fulln.me.dao.log","com.fulln.me.dao.es"},sqlSessionFactoryRef = "sqlSessionFactory1")
public class MybatisDBAConfig {

    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.second")
    public DataSource dataSource1() {
        return new HikariDataSource();
    }

    @Bean(name = "sqlSessionFactory1")
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("db1") DataSource dataSource) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 使用db1数据源
        factoryBean.setDataSource(dataSource);
//        factoryBean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources(Arrays.toString(url)));
        return factoryBean.getObject();
    }

//    @Bean(name = "sqlSessionTemplate1")
//    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("sqlSessionFactory1") SqlSessionFactory sqlSessionFactory1) throws Exception {
//        // 使用上面配置的Factory
//        return new SqlSessionTemplate(sqlSessionFactory1);
//    }

    @Bean(name = "TransactionManager1")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("db1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}