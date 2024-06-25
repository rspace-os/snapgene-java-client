package com.researchspace.zmq.snapgene.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Encapsulates general response information from Snapgene daemon
 */
@Data
public class SnapgeneResponse {

	private Integer code;
	private String response;
	private Integer serverIndex;

	/**
	 * Optional filename for operations that generate an output file
	 */
	@JsonInclude(Include.NON_NULL)
	private String outputFileName;

}
