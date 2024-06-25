package com.researchspace.zmq.snapgene.requests;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configure export. Default is FASTA format
 */
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ExportDnaFileConfig {

	private ExportFilter exportFilter = ExportFilter.FASTA;

	/**
	 * Specify the exact export type required
	 * 
	 * @param exportFilter
	 */
	public ExportDnaFileConfig(ExportFilter exportFilter) {
		this.exportFilter = exportFilter;
	}

}