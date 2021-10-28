package com.farman.mqueue.service;

import com.farman.mqueue.model.Message;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SleepySubscriber implements Subscriber {

	private final String ID;
	private final int sleepTime;

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void consume(Message message) throws InterruptedException {
		System.out.println("Starting consuming on" + getID() + " with message " + message);
		Thread.sleep(sleepTime);
		System.out.println("Completed consuming on" + getID() + " with message " + message);

	}

}
