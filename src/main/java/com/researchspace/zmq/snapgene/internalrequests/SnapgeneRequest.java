package com.researchspace.zmq.snapgene.internalrequests;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Validate;

import com.researchspace.core.util.JacksonUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Base class for all requests
 */

@AllArgsConstructor
@Getter
public abstract class SnapgeneRequest {

	private String request;

	/**
	 * Converts the request to JSON for sending to SnapGene daemon
	 * 
	 * @return
	 */
	public String toJson() {
		return JacksonUtil.toJson(this);
	}

	/*
	 * Requires input file to be a .dna file
	 */
	void validInputFileIsNativeSnapgeneDNA(String inputFile) {
		Validate.isTrue(FilenameUtils.getExtension(inputFile).equalsIgnoreCase("dna"),
				"Input path must be a valid .dna file");
	}

}
