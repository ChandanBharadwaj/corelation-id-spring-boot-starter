package com.chandan;

import org.apache.catalina.Context;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorrelationIdAutoConfiguartion implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>{

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CorrelationIdAutoConfiguartion.class);


    @Bean
    public CorrelationIdValve correlationIdValve() {
        return new CorrelationIdValve();
    }

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		// TODO Auto-generated method stub
		factory.addContextCustomizers( new TomcatContextCustomizer() {

			@Override
			public void customize(Context context) {
				context.getPipeline().addValve(correlationIdValve());
				
			}
			
		});

	}

}