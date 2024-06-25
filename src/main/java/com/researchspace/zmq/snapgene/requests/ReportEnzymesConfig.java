package com.researchspace.zmq.snapgene.requests;

import lombok.Data;

/**
 * Gets enzyme sites. Default enzyme set is EnzymeSet.UNIQUE_SIX_PLUS
 */

@Data
public class ReportEnzymesConfig {
	public ReportEnzymesConfig() {

	}

	private EnzymeSet enzymeSet = EnzymeSet.UNIQUE_SIX_PLUS;

	public ReportEnzymesConfig(EnzymeSet enzymeSet) {
		this.enzymeSet = enzymeSet;
	}

}