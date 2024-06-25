package com.researchspace.zmq.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.researchspace.zmq.ZMQConnector;

@Configuration
@PropertySource("classpath:/application-test.properties")
class SpringConfig {

	private @Autowired Environment env;

	@Bean
	ZMQConnector ZMQConnector() {
		return new ZMQConnector(env.getProperty("snapgene.host"),
				env.getProperty("snapgene.port", Integer.class, 5556));
	}

}
