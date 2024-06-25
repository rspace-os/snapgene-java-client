package com.researchspace.zmq.snapgene.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnzymesResponse extends SnapgeneResponse {

	/**
	 * requestEnzyme
	 */
	@JsonInclude(Include.NON_NULL)
	private Integer count;

	/**
	 * for requestEnzyme
	 */
	@JsonInclude(Include.NON_NULL)
	private List<Object> enzymes;

	@JsonInclude(Include.NON_NULL)
	private String setName;

}
