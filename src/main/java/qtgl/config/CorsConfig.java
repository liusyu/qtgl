package qtgl.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import qtgl.config.ibaties.base.ConnectCommon;

@Configuration
@ComponentScan
public class CorsConfig {
    @Bean public ConnectCommon createConnection(){
        return new ConnectCommon();
    }
//
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//            corsConfiguration.addAllowedOrigin("http://localhost:8088"); // 允许任何域名使用
//            corsConfiguration.addAllowedHeader("*"); // 允许任何头
//            corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
//            corsConfiguration.setAllowCredentials(true);
//
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//            source.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置
//
//        return new CorsFilter(source);
//    }


}
