package com.researchspace.zmq.snapgene.internalrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.researchspace.zmq.snapgene.requests.EnzymeSet;
import com.researchspace.zmq.snapgene.requests.ImportDnaFileConfig;
import com.researchspace.zmq.snapgene.requests.ReadingFrame;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class ImportDnaFileRequest extends SnapgeneRequest {

	private String inputFile, outputFile;

	private int minORFLen = 75;

	private ReadingFrame readingFrames = ReadingFrame.ORFS_ONLY;

	private EnzymeSet enzymeSet = EnzymeSet.UNIQUE_SIX_PLUS;

	/**
	 * Essential arguments
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @param config     ImportDnaFileConfig can be <code>null</code>
	 */
	public ImportDnaFileRequest(String inputFile, String outputFile, ImportDnaFileConfig config) {
		super("importDNAFile");
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		if (config != null) {
			this.minORFLen = config.getMinORFLen();
			this.readingFrames = config.getReadingFrames();
			this.enzymeSet = config.getEnzymeSet();
		}
	}

}