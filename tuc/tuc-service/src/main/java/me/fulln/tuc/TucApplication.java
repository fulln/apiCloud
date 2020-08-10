package me.fulln.tuc;

import com.fulln.proxys.annotation.EnableDynamicSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author fulln
 * @description 用户中心
 * @date Created in  11:41  2020-07-20.
 **/
@EnableDynamicSource
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan(basePackages = "me.fulln.tuc")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TucApplication {

	public static void main(String[] args) {
		SpringApplication.run(TucApplication.class, args);
	}

}
