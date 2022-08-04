package ca.bc.gov.educ.api.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EducGradProgramApiApplication {

    private static Logger logger = LoggerFactory.getLogger(EducGradProgramApiApplication.class);

    public static void main(String[] args) {
        logger.debug("########Starting API");
        SpringApplication.run(EducGradProgramApiApplication.class, args);
        logger.debug("########Started API");
    }

}