package com.crowdriff.styx;

import org.joda.time.Instant;

import java.util.UUID;

public class StyxMessage {

	public final String uuid;
	public final Instant timestamp;
	public final String message;

	public StyxMessage(String message) {
		this.uuid = UUID.randomUUID().toString();
		this.timestamp = new Instant();
		this.message = message;
	}

}
