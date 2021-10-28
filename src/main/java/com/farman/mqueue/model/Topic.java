package com.farman.mqueue.model;

import java.util.ArrayList;
import java.util.List;

import com.farman.mqueue.service.Subscriber;
import com.farman.mqueue.service.TopicSubscriber;

import lombok.Getter;
import lombok.Singular;

@Getter
public class Topic {

	private final String ID;
	private final String name;
	

	private final List<Message> messages;
	private final List<TopicSubscriber> subscribers;

	public Topic(String iD, String name) {
		super();
		ID = iD;
		this.name = name;
		messages = new ArrayList<>();
		subscribers = new ArrayList<>();
	}

	public synchronized void addMessage(Message message) {
		messages.add(message);
	}

	public void addSubscriber(TopicSubscriber subscriber) {
		subscribers.add(subscriber);
	}
	
	public int getMessageSize() {
		return messages.size();
	}
	
	public Message getMessage(int index) {
		return messages.get(index);
	}

}
