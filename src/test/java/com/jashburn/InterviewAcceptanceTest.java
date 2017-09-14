package com.jashburn;

import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.vertx.core.http.HttpHeaders.ACCEPT;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jashburn.model.Message;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class InterviewAcceptanceTest {

	private static Vertx vertx;
	private static HttpClient client;

	@BeforeClass
	public static void setUp(TestContext context) throws Exception {
		vertx = Vertx.vertx();
		vertx.deployVerticle(MainVerticle.class.getName(), context.asyncAssertSuccess());

		client = vertx.createHttpClient();
	}

	@AfterClass
	public static void tearDown(TestContext context) throws Exception {
		vertx.close(context.asyncAssertSuccess());
	}

	@Test
	public void shouldGetMessage(TestContext context) {
		final Async async = context.async();

		client.get(MainVerticle.PORT, "localhost", MainVerticle.PATH_APP_V1 + MainVerticle.PATH_HELLO)
			.putHeader(ACCEPT, APPLICATION_JSON)
			.handler(response -> {
				context.assertEquals(OK.code(), response.statusCode());

				response.bodyHandler(body -> {
					Message message = Json.decodeValue(body, Message.class);
					context.assertEquals("Hello, world!", message.getMessage());

					async.complete();
				});
			}).end();
	}
}
