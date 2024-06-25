package com.researchspace.zmq;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;

import org.apache.commons.lang.Validate;

import lombok.extern.slf4j.Slf4j;

/**
 * Connects to a socket to test it's available within a timeout
 */
@Slf4j
public class SocketChecker {
	/**
	 * Attempts to connect
	 * 
	 * @param uri           URI of a tcp connection e.g. tcp::123.2234.3:5556
	 * @param timeoutMillis > 0
	 * @throws IllegalStateException if connection cannot be made before timeout
	 *                               expires
	 */
	public void check(URI uri, int timeoutMillis) {
		Validate.isTrue(timeoutMillis > 0, "timeout must be positive integer");

		SocketAddress address = new InetSocketAddress(uri.getHost(), uri.getPort());

		try (java.net.Socket socket = new java.net.Socket()) {
			socket.connect(address, timeoutMillis);
		} catch (IOException e) {
			throw new IllegalStateException("Snapgene server is not available");
		}

	}

}
