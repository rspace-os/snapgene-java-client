package com.researchspace.zmq.snapgene.requests;

import com.researchspace.zmq.snapgene.internalrequests.SnapgeneRequest;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ReportFeatures extends SnapgeneRequest {

	private String inputFile;

	public ReportFeatures(String inputFile) {
		super("reportFeatures");
		this.inputFile = inputFile;
	}

}