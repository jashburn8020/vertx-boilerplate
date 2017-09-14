package com.jashburn;

import static io.netty.handler.codec.http.HttpHeaderValues.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.vertx.core.http.HttpHeaders.*;

import com.jashburn.model.Message;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

class InterviewHandlers {

	void getHello(RoutingContext context) {
		Message message = new Message();
		message.setMessage("Hello, world!");

		context.response().setStatusCode(OK.code()).putHeader(CONTENT_TYPE, APPLICATION_JSON)
			.end(Json.encodePrettily(message));
	}
}
