package com.researchspace.zmq.snapgene.requests;

import lombok.ToString;

/**
 * Choices of output format for exportDNAFile request
 */
@ToString
public enum ExportFilter {

	SNAPGENE("SnapGene DNA Sequence", "com.gslbiotech.dna", "dna"),
	GENBANK_STANDARD("GenBank - Standard", "biosequence.gb", "gb"),
	GENBANK_SNAPGENE("GenBank - SnapGene", "biosequence.gbk", "gb"), FASTA("FASTA", "public.fasta", "fasta"),
	TEXT_FILE("Text File", "public.txt", "txt");

	private String displayName, id, fileExtension;

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * ID of an export filter
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	public String getFileExtensionString() {
		return fileExtension;
	}

	private ExportFilter(String displayName, String id, String fileExtension) {
		this.displayName = displayName;
		this.id = id;
		this.fileExtension = fileExtension;
	}

}
