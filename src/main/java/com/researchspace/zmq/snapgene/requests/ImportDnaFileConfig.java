package com.researchspace.zmq.snapgene.requests;

import org.apache.commons.lang3.Validate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder()
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ImportDnaFileConfig {

	@Builder.Default
	private int minORFLen = 75;
	@Builder.Default
	private ReadingFrame readingFrames = ReadingFrame.ORFS_ONLY;
	@Builder.Default
	private EnzymeSet enzymeSet = EnzymeSet.UNIQUE_SIX_PLUS;

	/**
	 * All arg constructor for all arguments; alternatively use the builder. The
	 * builder will call this method on calling build()
	 * 
	 * @param minORFLen,    > 0
	 * @param readingFrames
	 * @param enzymeSet
	 * @throws IllegalArgumentException if arguments invalid
	 */
	public ImportDnaFileConfig(int minORFLen, ReadingFrame readingFrames, EnzymeSet enzymeSet) {
		Validate.isTrue(minORFLen > 0, "minORF length must be > 0");
		Validate.noNullElements(new Object[] { readingFrames, enzymeSet }, "No null arguments!");
		this.minORFLen = minORFLen;
		this.readingFrames = readingFrames;
		this.enzymeSet = enzymeSet;
	}

}