package tasks.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(regex("/api/.*"))//
				.build()
				.consumes(Collections.singleton("application/json"))
				.produces(Collections.singleton("application/json"))
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Trello task management service")
				.description("Service prividing task management via trello")
				.contact(new Contact(" Mateusz Pilarczyk", " https://github.com/mateusz58/CRUD.git", "matp321@gmail.com"))
				.version("1.0")
				.build();
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		// Required by Swagger UI configuration
		registry.addResourceHandler("/lib/**").addResourceLocations("/lib/").setCachePeriod(0);
		registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(0);
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(0);
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
