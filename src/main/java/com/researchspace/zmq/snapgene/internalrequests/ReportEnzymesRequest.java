package com.researchspace.zmq.snapgene.internalrequests;

import com.researchspace.zmq.snapgene.requests.EnzymeSet;
import com.researchspace.zmq.snapgene.requests.ReportEnzymesConfig;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Gets enzyme sites. Default enzyme set is EnzymeSet.UNIQUE_SIX_PLUS
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class ReportEnzymesRequest extends SnapgeneRequest {

	private String inputFile;
	private EnzymeSet enzymeSet = EnzymeSet.UNIQUE_SIX_PLUS;

	/**
	 * Path to input .dna file for
	 * 
	 * @param inputFile
	 * @throws IllegalArgumentException if inputFile not a .dna file
	 */
	public ReportEnzymesRequest(String inputFile) {
		super("reportEnzymes");
		validInputFileIsNativeSnapgeneDNA(inputFile);
		this.inputFile = inputFile;
	}

	/**
	 * Set an alternative EnzymeSet
	 * 
	 * @param inputFile
	 * @param cfg
	 */
	public ReportEnzymesRequest(String inputFile, ReportEnzymesConfig cfg) {
		this(inputFile);
		if (cfg != null) {
			this.enzymeSet = cfg.getEnzymeSet();
		}
	}

}