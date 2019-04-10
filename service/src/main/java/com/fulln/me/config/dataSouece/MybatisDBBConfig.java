package com.fulln.me.config.dataSouece;


import com.github.pagehelper.PageInterceptor;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源2的配置
 */
@Configuration
@MapperScan(basePackages = {"com.fulln.me.dao.system","com.fulln.me.dao.search"},sqlSessionFactoryRef = "sqlSessionFactory2")
public class MybatisDBBConfig {

    @Autowired
    private MybatisProperties mybatisProperties;

    @Autowired
    private PageHelperProperties pageHelperProperties;

    /**
     * application.properteis中对应属性的前缀
     */
    @Bean(name = "db2")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.primary")
    public DataSource dataSource2() {
        return new HikariDataSource();
    }


    @Bean(name = "sqlSessionFactory2")
    @Primary
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("db2")DataSource db2) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 使用db2数据源
        factoryBean.setDataSource(db2);

        factoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        factoryBean.setTypeHandlersPackage(mybatisProperties.getTypeHandlersPackage());
       //设置mybatis的config地址
        factoryBean.setConfigurationProperties(mybatisProperties.getConfigurationProperties());
        //设置mapper文件地址
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisProperties.getMapperLocations()[0]));
        //设置pagehelper的插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(pageHelperProperties.getProperties());
        factoryBean.setPlugins(new Interceptor[]{pageInterceptor});

        return factoryBean.getObject();
    }

//    @Bean(name = "sqlSessionTemplate2")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate2(@Qualifier("sqlSessionFactory2")SqlSessionFactory sqlSessionFactory2) throws Exception {
//        // 使用上面配置的Factory
//        return new SqlSessionTemplate(sqlSessionFactory2);
//    }

    @Primary
    @Bean(name = "TransactionManager2")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("db2") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}