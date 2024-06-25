package com.researchspace.zmq.snapgene.internalrequests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.researchspace.zmq.snapgene.requests.EnzymeSet;
import com.researchspace.zmq.snapgene.requests.GenerateSVGMapConfig;
import com.researchspace.zmq.snapgene.requests.ReadingFrame;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode(callSuper = true)

@JsonInclude(Include.NON_NULL)
public class GenerateSVGMapRequest extends SnapgeneRequest {

	public GenerateSVGMapRequest(@NonNull String inputFile, @NonNull String outputFile, GenerateSVGMapConfig config) {
		super("generateSVGMap");
		this.inputFile = inputFile;
		this.outputSvgDom = outputFile;
		if (config != null) {
			this.linear = config.isLinear();
			this.showEnzymes = config.isShowEnzymes();
			this.showPrimers = config.isShowPrimers();
			this.showFeatures = config.isShowFeatures();
			this.showORFs = config.isShowORFs();
			this.designWidth = config.getDesignWidth() != null ? config.getDesignWidth() : 0;
			this.designHeight = config.getDesignHeight() != null ? config.getDesignHeight() : 0;

			this.title = config.getTitle();
			this.subtitle = config.getSubtitle();
			this.enzymeSet = config.getEnzymeSet();
			this.minORFLen = config.getMinORFLen();
			this.readingFrame = config.getReadingFrame();
		}
	}

	private String inputFile;
	private String outputSvgDom;

	private boolean linear = true;

	private boolean showEnzymes = true;

	private boolean showPrimers = true;

	private boolean showFeatures = true;

	private boolean showORFs = true;

	@JsonIgnore
	private Integer designWidth = 0;

	@JsonIgnore
	private Integer designHeight = 0;

	private String title, subtitle;
	private EnzymeSet enzymeSet;
	private Integer minORFLen;
	private ReadingFrame readingFrame;

	public String getDesignSize() {
		if (designWidth > 0 && designHeight > 0) {
			return designWidth + " x " + designHeight;
		} else {
			return null;
		}
	}

	@JsonIgnore
	public Integer getDesignHeight() {
		return designHeight;
	}

	@JsonIgnore
	public Integer getDesignWidth() {
		return designWidth;
	}

}