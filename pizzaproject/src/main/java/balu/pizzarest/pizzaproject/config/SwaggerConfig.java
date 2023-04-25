package balu.pizzarest.pizzaproject.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;


/**
 * @author Sergii Bugaienko
 */

//@SecurityScheme(
//        name = "JWT",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("pizzaRest.controllers.rest"))
//                .paths(PathSelectors.any())
//                .build();
//    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                .paths(regex("/controllers/rest/.*"))
                .apis(RequestHandlerSelectors.basePackage("pizzaRest.controllers.rest"))
                .build()
                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Pizza project API")
                .description("Pizza API allows you to remotely manage the pizzeria. ### Check out its awesome features: * Authorization is required to work with the API. Authorization is done by the bearer-token 😎 ")
                .license("")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("","", ""))
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pizza project API")
                        .description("Pizza API allows you to remotely manage the pizzeria. ### Check out its awesome features: * Authorization is required to work with the API. Authorization is done by the bearer-token 😎 ")
                        .termsOfService("")
                        .version("1.0.0")
                        .license(new License()
                                .name("")
                                .url("http://unlicense.org"))
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .email("")));
    }


}
