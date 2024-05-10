package vip.aquan.multidatasourcedemo.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@MapperScan(basePackages = {"vip.aquan.multidatasourcedemo.mapper.two.**"},sqlSessionTemplateRef = "sqlSessionTemplateTwo")
@Configuration
@Slf4j
public class MybatisConfigSourceTwo {
    @Value("${spring.datasource.two.url}")
    private String url;
    @Value("${spring.datasource.two.username}")
    private String username;
    @Value("${spring.datasource.two.password}")
    private String password;
    @Value("${spring.datasource.two.driverClassName}")
    private String driverClassName;
    @Bean
    public DataSource dataSourceTwo() {
        log.info("开始初始化Two数据源");
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        return datasource;
    }
    @Bean("sqlSessionFactoryTwo")
    public SqlSessionFactory sqlSessionFactoryTwo(@Qualifier("dataSourceTwo") DataSource dataSource) throws Exception {
        // 创建 SqlSessionFactoryBean 对象
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        // 设置数据源
        factoryBean.setDataSource(dataSource);
        // 设置 mapper 映射文件路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/two/*.xml"));
        factoryBean.setPlugins(mybatisPlusInterceptorTwo());
        // 返回 SqlSessionFactory 对象
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateTwo(@Qualifier("sqlSessionFactoryTwo") SqlSessionFactory  sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManagerTwo(@Qualifier("dataSourceTwo") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptorTwo() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 乐观锁配置
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
//    @Bean(name = "plugins1")
//    public Interceptor[] plugins() {
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("helperDialect", "oracle"); // 数据库方言
//        properties.setProperty("reasonable", "true"); // 启用合理化查询
//        properties.setProperty("supportMethodsArguments", "true"); // 支持通过Mapper接口参数来传递分页参数
//        properties.setProperty("params", "count=countSql"); // 使用默认的count查询
//        pageHelper.setProperties(properties);
//        return new Interceptor[]{pageHelper};
//    }
}