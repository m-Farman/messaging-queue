package com.farman.mqueue.service;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class TopicSubscriber {
	
	private final AtomicInteger offset;
	private final Subscriber subscriber;
	
	public TopicSubscriber(@NonNull Subscriber subscriber) {
		this(new AtomicInteger(0), subscriber);
	}

}
