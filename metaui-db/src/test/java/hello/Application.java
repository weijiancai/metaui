package hello;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/3.
 */
@Configuration
@ComponentScan
public class Application {
    @Bean
    MessageService mockMessageService() {
        return new MessageService() {
            @Override
            public String getMessage() {
                return "Hello World";
            }
        };
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    DruidDataSource dataSource() throws SQLException {
        DruidDataSource dataSource =  new DruidDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://weiyi1998.com:18888;databaseName=yhbis_wy");
        dataSource.setUsername("sa");
        dataSource.setPassword("123!@#qwe");
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        dataSource.setInitialSize(5);
        dataSource.setFilters("stat"); // 启用监控统计功能
        return dataSource;
    }

    /*@Bean
    JdbcTemplate mockJdbcTemplate() {
        return new JdbcTemplate();
    }*/

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        MessagePrinter printer = context.getBean(MessagePrinter.class);
        printer.printMessage();
        ShippingDao dao = context.getBean(ShippingDao.class);
        System.out.println(dao.getShippingCount());
    }
}
