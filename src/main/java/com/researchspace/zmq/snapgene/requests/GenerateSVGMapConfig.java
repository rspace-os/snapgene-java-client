package com.researchspace.zmq.snapgene.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

/**
 * Configuration for SVG image maps. Client code should use builder to populate
 * the object. <br/>
 * By default, configuration will include all features and set linear to be
 * <code>true</code>
 */
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class GenerateSVGMapConfig {

	GenerateSVGMapConfig() {

	}

	GenerateSVGMapConfig(boolean linear, boolean showEnzymes, boolean showPrimers, boolean showFeatures,
			boolean showORFs, Integer designWidth, Integer designHeight, String title, String subtitle,
			EnzymeSet enzymeSet, Integer orfLen, ReadingFrame readingFrame) {

		this.linear = linear;
		this.showEnzymes = showEnzymes;
		this.showPrimers = showPrimers;
		this.showFeatures = showFeatures;
		this.showORFs = showORFs;
		this.designWidth = designWidth;
		this.designHeight = designHeight;

		this.title = title;
		this.subtitle = subtitle;
		this.enzymeSet = enzymeSet;
		this.minORFLen = orfLen;
		this.readingFrame = readingFrame;
	}

	@Builder.Default
	private boolean linear = true;
	@Builder.Default
	private boolean showEnzymes = true;
	@Builder.Default
	private boolean showPrimers = true;
	@Builder.Default
	private boolean showFeatures = true;
	@Builder.Default
	private boolean showORFs = true;

	@JsonIgnore
	@Builder.Default
	private Integer designWidth = 0;

	@JsonIgnore
	@Builder.Default
	private Integer designHeight = 0;

	private String title, subtitle;
	private EnzymeSet enzymeSet;
	private Integer minORFLen;
	private ReadingFrame readingFrame;

}