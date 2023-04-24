package ca.bc.gov.educ.api.program.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

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

	@Bean
	ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		return builder.createXmlMapper(false)
				// Set timezone for JSON serialization as system timezone
				.timeZone(TimeZone.getDefault())
				.build();
	}

	@Bean
	Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		return new Jackson2ObjectMapperBuilder();
	}
}
