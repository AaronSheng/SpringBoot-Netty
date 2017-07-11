package com.aaron.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Aaron Sheng on 9/12/16.
 */
@Configuration
public class RdsConfiguration {
    @Value("${spring.datasource.serverurl}")
    private String serverurl;
    @Value("${spring.datasource.communityurl}")
    private String communityurl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.filters}")
    private String filters;

    @Value("${spring.hibernate.dialect}")
    private String dialect;
    @Value("${spring.hibernate.show_sql}")
    private String showSql;
    @Value("${spring.hibernate.autoReconnect}")
    private String autoReconnect;
    @Value("${spring.hibernate.transaction.auto_close_session}")
    private String transactionAutoClostSession;
    @Value("${spring.hibernate.connection.autocommit}")
    private String connectionAutoCommit;
    @Value("${spring.statement_cache.size}")
    private String statementCacheSize;

    @Bean(name = "serverDataSource")
    @Qualifier(value = "serverDataSource")
    @Primary
    public DataSource serverDataSource() {
        return dataSource(serverurl);
    }

    @Bean(name = "communityDataSource")
    @Qualifier(value = "communityDataSource")
    public DataSource communityDataSource() {
        return dataSource(communityurl);
    }

    private DataSource dataSource(String url) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setValidationQuery(validationQuery);
        //druidDataSource.setFilters(filters);
        return druidDataSource;
    }


    @Bean(name = "serverSessionFactory")
    @Qualifier(value = "serverSessionFactory")
    @Primary
    public LocalSessionFactoryBean serverSessionFactory(@Qualifier("serverDataSource") DataSource dataSource) {
        return sessionFactoryBean(dataSource);
    }

    @Bean(name = "communitySessionFactory")
    @Qualifier(value = "communitySessionFactory")
    public LocalSessionFactoryBean communitySessionFactory(@Qualifier("communityDataSource") DataSource dataSource) {
        return sessionFactoryBean(dataSource);
    }

    private LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.autoReconnect", autoReconnect);
        properties.setProperty("hibernate.transaction.auto_close_session", transactionAutoClostSession);
        properties.setProperty("hibernate.transaction.connection.autocommit", connectionAutoCommit);
        properties.setProperty("statement_cache.size", statementCacheSize);
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.setPackagesToScan("com.aaron.entity");
        return localSessionFactoryBean;
    }


    @Bean(name = "serverTransactionManager")
    @Qualifier(value = "serverTransactionManager")
    public HibernateTransactionManager serverTransactionManager(@Qualifier("serverSessionFactory") SessionFactory sessionFactory) {
        return hibernateTransactionManager(sessionFactory);
    }

    @Bean(name = "communityTransactionManager")
    @Qualifier(value = "communityTransactionManager")
    public HibernateTransactionManager communityTransactionManager(@Qualifier("communitySessionFactory") SessionFactory sessionFactory) {
        return hibernateTransactionManager(sessionFactory);
    }

    private HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }
}
