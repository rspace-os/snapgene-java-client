package com.researchspace.zmq.snapgene;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.researchspace.zmq.snapgene.internalrequests.GenerateSVGMapRequest;

class GenerateSVGMapTest {

	@Test
	void inputAndOutputFilesMustBeSetInBuilder() {
		assertThrows(NullPointerException.class, () -> new GenerateSVGMapRequest(null, null, null));
		assertThrows(NullPointerException.class, () -> new GenerateSVGMapRequest("in.dna", null, null));
		assertDoesNotThrow(() -> new GenerateSVGMapRequest("in.dna", "out.svg", null));
	}

}
