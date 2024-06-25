package com.researchspace.zmq.snapgene.internalrequests;

import com.researchspace.zmq.snapgene.requests.ReadingFrame;
import com.researchspace.zmq.snapgene.requests.ReportORFsConfig;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * By default will use ReadingFrame.ALL_FRAMES
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class ReportORFsRequest extends SnapgeneRequest {

	private String inputFile;
	private ReadingFrame readingFrame = ReadingFrame.ALL_FRAMES;

	public ReportORFsRequest(String inputFile) {
		super("reportORFs");
		this.inputFile = inputFile;
	}

	public ReportORFsRequest(String inputFile, ReportORFsConfig reportORFsConfig) {
		this(inputFile);
		this.readingFrame = reportORFsConfig.getReadingFrame();
	}

}