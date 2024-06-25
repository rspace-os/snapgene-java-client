package com.researchspace.zmq.snapgene.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configurer for ORFs
 */

@Data
@NoArgsConstructor
public class ReportORFsConfig {

	private ReadingFrame readingFrame = ReadingFrame.ORFS_ONLY;

	public ReportORFsConfig(ReadingFrame readingFrame) {
		this.readingFrame = readingFrame;
	}

}