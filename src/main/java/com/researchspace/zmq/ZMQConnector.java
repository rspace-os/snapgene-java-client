package com.researchspace.zmq;

import java.net.URI;
import java.net.URISyntaxException;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import com.researchspace.zmq.snapgene.internalrequests.SnapgeneRequest;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * Sends a request to a ZMQ message queue
 */
@Value
@Slf4j
public class ZMQConnector {

	URI uri;
	SocketChecker socketChecker = new SocketChecker();

	/**
	 * 
	 * @param host A host
	 * @param port A port
	 * @throws IllegalArgumentException if a URI cannot be created from arguments
	 */
	public ZMQConnector(String host, int port) {
		try {
			this.uri = new URI("tcp", null, host, port, null, null, null);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(
					String.format("Could not create valid URI from host '%s' and port '%d' ", host, port));
		}
	}

	/**
	 * Sends and waits for response for a SnapGene request.
	 * 
	 * @param req
	 * @return A JSON string of the response from the Snapgene server
	 */
	public String performRequest(SnapgeneRequest req) {
		socketChecker.check(uri, 2000);
		try (ZContext context = new ZContext()) {
			context.setLinger(5);
			// Socket to talk to server

			Socket socket = createConnection(context);
			String request = req.toJson();
			log.info("Request: {}", request);
			socket.send(request.getBytes(ZMQ.CHARSET), 0);
			return receiveReply(socket);
		}
	}

	private Socket createConnection(ZContext context) {
		Socket socket = context.createSocket(SocketType.REQ);
		socket.connect(uri.toString());
		return socket;
	}

	private String receiveReply(Socket socket) {
		byte[] reply = socket.recv(0);
		return new String(reply, ZMQ.CHARSET);
	}

}
