package com.researchspace.zmq.snapgene.internalrequests;

import com.researchspace.zmq.snapgene.requests.ExportDnaFileConfig;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ExportDnaFileRequest extends SnapgeneRequest {

	private String inputFile, outputFile;
	private String exportFilter;

	/**
	 * Essential arguments
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @param exportConfig
	 */
	public ExportDnaFileRequest(String inputFile, String outputFile, ExportDnaFileConfig exportConfig) {
		super("exportDNAFile");
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		if (exportConfig != null) {
			this.exportFilter = exportConfig.getExportFilter().getId();
		}
	}

}