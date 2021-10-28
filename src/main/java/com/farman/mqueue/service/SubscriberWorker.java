package com.farman.mqueue.service;

import com.farman.mqueue.model.Message;
import com.farman.mqueue.model.Topic;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class SubscriberWorker implements Runnable {

	private final Topic topic;
	private final TopicSubscriber subscriber;

	@SneakyThrows
	@Override
	public void run() {
		do {
			synchronized (subscriber) {
				int offset = subscriber.getOffset().get();
				if (offset >= topic.getMessageSize()) {
					subscriber.wait();
				}
				
				Message msg = topic.getMessage(offset);
				subscriber.getSubscriber().consume(msg);
				subscriber.getOffset().compareAndSet(offset, offset + 1);
			}
		} while (true);
	}

	public synchronized void resume() {
		synchronized (subscriber) {
			subscriber.notify();
		}
	}

}
