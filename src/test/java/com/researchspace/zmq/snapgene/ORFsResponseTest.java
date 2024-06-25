package com.researchspace.zmq.snapgene;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.researchspace.core.util.JacksonUtil;
import com.researchspace.zmq.snapgene.responses.ORFsResponse;

class ORFsResponseTest {

	@Test
	void parseORFsResponse() throws IOException {
		String json = FileUtils.readFileToString(new File("src/test/resources/orfsResponse.json"), "UTF-8");
		ORFsResponse orfs = JacksonUtil.fromJson(json, ORFsResponse.class);
		assertNotNull(orfs);
		assertEquals(8, orfs.getOrfs().size());

	}

}
