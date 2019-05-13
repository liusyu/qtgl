package qtgl.config.ibaties.base;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;



import java.sql.SQLException;
import java.util.Properties;


@ConfigurationProperties(prefix = "mysql.datasource.common")
public class ConnectCommon {

    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;


    public AtomikosDataSourceBean createDataSource(BaseConnect connect, String name) throws SQLException {


        DruidXADataSource mysqlXADataSource = new DruidXADataSource();
        mysqlXADataSource.setUrl(connect.getUrl());
        mysqlXADataSource.setPassword(connect.getPassword());
        mysqlXADataSource.setUsername(connect.getUsername());
        mysqlXADataSource.setDriverClassName(connect.getDriver());
//        Properties properties = new Properties();
//        properties.put("url", connect.getUrl());
//        properties.put("username", connect.getUsername());
//        properties.put("password", connect.getPassword());
//        properties.put("driverClassName", "com.mysql.jdbc.Driver" );
        //创建atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();

//        xaDataSource.setXaProperties(properties);
        xaDataSource.setXaDataSource(mysqlXADataSource);

        xaDataSource.setUniqueResourceName(name);//这个是数据连接池注入的名称，最好唯一，如果相同，请参考spring @Qualifier @Primary

        xaDataSource.setMinPoolSize(getMinPoolSize());
        xaDataSource.setMaxPoolSize(getMaxPoolSize());
        xaDataSource.setMaxLifetime(getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(getLoginTimeout());
        xaDataSource.setMaintenanceInterval(getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(getMaxIdleTime());
        xaDataSource.setTestQuery(getTestQuery());
        xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");//阿里连接池
        return xaDataSource;
    }



    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(int maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public int getBorrowConnectionTimeout() {
        return borrowConnectionTimeout;
    }

    public void setBorrowConnectionTimeout(int borrowConnectionTimeout) {
        this.borrowConnectionTimeout = borrowConnectionTimeout;
    }

    public int getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(int loginTimeout) {
        this.loginTimeout = loginTimeout;
    }

    public int getMaintenanceInterval() {
        return maintenanceInterval;
    }

    public void setMaintenanceInterval(int maintenanceInterval) {
        this.maintenanceInterval = maintenanceInterval;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public String getTestQuery() {
        return testQuery;
    }

    public void setTestQuery(String testQuery) {
        this.testQuery = testQuery;
    }
}

