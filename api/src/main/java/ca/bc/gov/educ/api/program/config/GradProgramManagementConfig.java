package ca.bc.gov.educ.api.program.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:messages.properties")
public class GradProgramManagementConfig implements WebMvcConfigurer {

	@Autowired
	RequestInterceptor requestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
