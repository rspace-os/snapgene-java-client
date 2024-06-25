package com.researchspace.zmq.snapgene.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

/**
 * Configuration for PNG image maps. Client code should use builder() to
 * populate the object. <br/>
 * By default, configuration will include all features and set linear to be
 * <code>true</code>
 */
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class GeneratePngMapConfig {

	GeneratePngMapConfig() {

	}

	GeneratePngMapConfig(boolean linear, boolean showEnzymes, boolean showPrimers, boolean showFeatures,
			boolean showORFs, Integer designWidth, Integer designHeight) {

		this.linear = linear;
		this.showEnzymes = showEnzymes;
		this.showPrimers = showPrimers;
		this.showFeatures = showFeatures;
		this.showORFs = showORFs;
		this.designWidth = designWidth;
		this.designHeight = designHeight;
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

}