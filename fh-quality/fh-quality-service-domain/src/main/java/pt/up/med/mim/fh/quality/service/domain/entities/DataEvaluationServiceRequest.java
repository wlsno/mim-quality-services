package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class DataEvaluationServiceRequest implements Serializable, IServiceObject {

	private static final long serialVersionUID = 1L;

	private RequestHeader header;
	private RequestBody body;

	public DataEvaluationServiceRequest() {
	}

	public RequestHeader getHeader() {
		return header;
	}

	public void setHeader(RequestHeader header) {
		this.header = header;
	}

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

}
