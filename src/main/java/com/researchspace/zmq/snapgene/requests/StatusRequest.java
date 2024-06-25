package com.researchspace.zmq.snapgene.requests;

import com.researchspace.core.util.JacksonUtil;
import com.researchspace.zmq.snapgene.internalrequests.SnapgeneRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatusRequest extends SnapgeneRequest {

	public StatusRequest() {
		super("status");
	}

	public String toJson() {
		return JacksonUtil.toJson(this);
	}

}