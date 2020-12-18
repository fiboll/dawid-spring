package dawid.spring;

import com.lingocoder.springfox.api.doc.DocketTyper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import static com.lingocoder.springfox.enums.DocketType.SWAGGER2;

/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */
@Configuration
public class Configuraton {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Petstore")
                .description("This is a sample server Petstore server.  You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, you can use the api key `special-key` to test the authorization filters.")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.5")
                .contact(new Contact("","", "apiteam@swagger.io"))
                .build();
    }

    @Bean
    public Docket customImplementation(){
        return DocketTyper.of(SWAGGER2).orElseThrow(IllegalAccessError::new)
                .select()
                .apis(RequestHandlerSelectors.basePackage("dawid.spring.rest"))
                .build()
                .apiInfo(apiInfo());
    }



}