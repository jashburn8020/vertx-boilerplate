package com.jashburn;

import io.vertx.core.Vertx;

public class InterviewApplication {

	public static void main(String[] args) {
		Vertx.vertx().deployVerticle(MainVerticle.class.getName());
	}
}
