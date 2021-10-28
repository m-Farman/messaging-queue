package com.farman.mqueue.service;

import java.util.HashMap;
import java.util.Map;

import com.farman.mqueue.model.Message;
import com.farman.mqueue.model.Topic;

import lombok.Getter;

@Getter
public class TopicHandler {

	private final Topic topic;
	private final Map<String, SubscriberWorker> workers;

	public TopicHandler(Topic topic) {
		this.topic = topic;
		this.workers = new HashMap<>();
	}
	
	
	public void addMessage(Message message) {
		topic.addMessage(message);
	}
	
	public void addSubscriber(Subscriber subscriber) {
		topic.addSubscriber(new TopicSubscriber(subscriber));
	}
	
	public void publish() {
		topic.getSubscribers().forEach(s-> startSubscriberWorker(s));
	}
	
	
	public void startSubscriberWorker(TopicSubscriber subscriber) {
		String subscriberID = subscriber.getSubscriber().getID();
		if (!workers.containsKey(subscriberID)) {
			final SubscriberWorker subscriberWorker= new SubscriberWorker(topic, subscriber);
			workers.put(subscriberID, subscriberWorker);
			new Thread(subscriberWorker).start();
		}
		workers.get(subscriberID).resume();
		
	}
}
