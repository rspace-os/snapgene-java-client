package com.researchspace.zmq.snapgene.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ORFsResponse extends SnapgeneResponse {

	/**
	 * for requestEnzyme
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonAlias({ "ORFs" })
	private List<Object> orfs;

}
