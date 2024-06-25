package com.researchspace.zmq.acceptance;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.researchspace.zmq.SocketChecker;
import com.researchspace.zmq.ZMQConnector;

@SpringJUnitConfig(classes = SpringConfig.class)
class SocketCheckerTest {

	private @Autowired ZMQConnector connector;

	@Test
	void socketSuccess() throws UnknownHostException, IOException {
		SocketChecker checker = new SocketChecker();
		checker.check(connector.getUri(), 1000);
	}

	@Test
	void socketFail() throws UnknownHostException, IOException {
		SocketChecker checker = new SocketChecker();
		Assertions.assertThrows(IllegalStateException.class, () -> checker.check(invalidConnection().getUri(), 1000));
	}

	private ZMQConnector invalidConnection() {
		// is invalid port
		return new ZMQConnector(connector.getUri().getHost(), 5557);
	}

}
