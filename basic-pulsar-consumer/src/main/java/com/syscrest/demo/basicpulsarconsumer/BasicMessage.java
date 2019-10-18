package com.syscrest.demo.basicpulsarconsumer;

public class BasicMessage {

	public BasicMessage() {

	}

	public BasicMessage(String body) {
		this.body = body;
	}

	public String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "BasicMessage [body=" + body + "]";
	}

}
