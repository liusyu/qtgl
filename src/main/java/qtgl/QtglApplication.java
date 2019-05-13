package qtgl;

import com.quantangle.infoplus.sdk.SubscriberServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class QtglApplication extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(QtglApplication.class);


	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		//sdk入口
		return new ServletRegistrationBean(new SubscriberServlet(), "/infoplus_subscriber");
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(QtglApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(QtglApplication.class, args);
	}

}