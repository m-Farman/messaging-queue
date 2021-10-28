package com.farman.mqueue.service;

import com.farman.mqueue.model.Message;

public interface Subscriber {

	public String getID();

	void consume(Message message) throws InterruptedException;

}
