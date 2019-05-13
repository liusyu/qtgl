package qtgl.config.ibaties;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qtgl.config.ibaties.base.BaseConnect;
import qtgl.config.ibaties.base.ConnectCommon;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "qtgl.dao.rscMapper",sqlSessionTemplateRef = "rscSqlSessionTemplate")
public class RSCConfig {

    @ConfigurationProperties(prefix = "mysql.datasource.rsc")
    public class DBConfig2  extends BaseConnect { }

    @Bean
    public DBConfig2 createDB2(){
        return new DBConfig2();
    }
    //配置数据源
    @Bean(name = "rscDataSource")
    public DataSource testDataSource(DBConfig2 testConfig,ConnectCommon connectCommon) throws SQLException{
        return  connectCommon.createDataSource(testConfig,"rscDataSource");
    }

    @Bean(name = "rscSqlSessionFactory")
    public SqlSessionFactory test2SqlSessionFactory(@Qualifier("rscDataSource") DataSource dataSource)
            throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "rscSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("rscSqlSessionFactory") SqlSessionFactory sqlSessionFactory)throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
