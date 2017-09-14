package com.jashburn;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

	public static final int PORT = 8888;

	public static final String PATH_APP_V1 = "/interview/v1";
	public static final String PATH_HELLO = "/hello";

	@Override
	public void start(Future<Void> future) {
		InterviewHandlers interviewHandlers = new InterviewHandlers();

		Router apiRouter = Router.router(vertx);
		apiRouter.get(PATH_HELLO)
			.produces(HttpHeaderValues.APPLICATION_JSON.toString())
			.handler(interviewHandlers::getHello);

		Router mainRouter = Router.router(vertx);
		mainRouter.mountSubRouter(PATH_APP_V1, apiRouter);

		vertx.createHttpServer().requestHandler(mainRouter::accept).listen(PORT, result -> {
			if (result.succeeded()) {
				future.complete();
			} else {
				future.fail(result.cause());
			}
		});
	}
}
