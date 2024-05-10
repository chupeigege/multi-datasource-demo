package vip.aquan.multidatasourcedemo.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@MapperScan(basePackages = {"vip.aquan.multidatasourcedemo.mapper.one.**"},sqlSessionTemplateRef = "sqlSessionTemplateOne")
@Configuration
@Slf4j
public class MybatisConfigSourceOne {

    @Value("${spring.datasource.one.url}")
    private String url;
    @Value("${spring.datasource.one.username}")
    private String username;
    @Value("${spring.datasource.one.password}")
    private String password;
    @Value("${spring.datasource.one.driverClassName}")
    private String driverClassName;
    @Bean
    public DataSource dataSourceOne() {
        log.info("开始初始化One数据源");
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        return datasource;
    }
    @Bean("sqlSessionFactoryOne")
    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("dataSourceOne") DataSource dataSource) throws Exception {
        // 创建 SqlSessionFactoryBean 对象
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        // 设置数据源
        factoryBean.setDataSource(dataSource);
        // 设置 mapper 映射文件路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/one/*.xml"));
        factoryBean.setPlugins(mybatisPlusInterceptorOne());
        // 返回 SqlSessionFactory 对象
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateOne(@Qualifier("sqlSessionFactoryOne") SqlSessionFactory  sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManagerOne(@Qualifier("dataSourceOne") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptorOne() {
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