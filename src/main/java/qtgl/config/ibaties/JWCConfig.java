package qtgl.config.ibaties;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import qtgl.config.ibaties.base.BaseConnect;
import qtgl.config.ibaties.base.ConnectCommon;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "qtgl.dao.jwcMapper",sqlSessionTemplateRef = "jwcSqlSessionTemplate")
public class JWCConfig {


    @ConfigurationProperties(prefix = "mysql.datasource.jwc")
   public class DBConfig1 extends BaseConnect {

    }
    @Bean
    public DBConfig1 createDB1(){
        return new DBConfig1();
    }
    //配置数据源


    @Bean(name = "jwcDataSource")
    public DataSource testDataSource(DBConfig1 testConfig,ConnectCommon connectCommon) throws SQLException{
        return connectCommon.createDataSource(testConfig,"jwcDataSource");
    }
    @Primary
    @Bean(name = "jwcSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("jwcDataSource") DataSource dataSource)
            throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "jwcSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("jwcSqlSessionFactory") SqlSessionFactory sqlSessionFactory)throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
