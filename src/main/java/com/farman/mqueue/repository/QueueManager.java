package com.farman.mqueue.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.farman.mqueue.exception.NoTopicFoundException;
import com.farman.mqueue.exception.SubscriberNotFoundException;
import com.farman.mqueue.exception.TopicAlreadyExistsException;
import com.farman.mqueue.model.Message;
import com.farman.mqueue.model.Topic;
import com.farman.mqueue.service.Subscriber;
import com.farman.mqueue.service.TopicHandler;
import com.farman.mqueue.service.TopicSubscriber;

import lombok.NonNull;

@Service
public class QueueManager {

	private final Map<String, TopicHandler> topicRegistry;

	public QueueManager() {
		topicRegistry = new HashMap<>();
	}

	public void addTopic(@NonNull String topicName) {
		if (topicRegistry.containsKey(topicName)) {
			throw new TopicAlreadyExistsException();
		}
		topicRegistry.put(topicName, new TopicHandler(new Topic(UUID.randomUUID().toString(), topicName)));
	}

	public void publishMsg(@NonNull String topicName, @NonNull Message message) throws NoTopicFoundException {
		if (!topicRegistry.containsKey(topicName)) {
			throw new NoTopicFoundException();
		}
		TopicHandler topicHandler = topicRegistry.get(topicName);
		topicHandler.addMessage(message);
		new Thread(() -> topicHandler.publish()).start();
	}

	public void addSubscriber(@NonNull String topicName, @NonNull Subscriber subscriber) throws NoTopicFoundException {
		if (!topicRegistry.containsKey(topicName)) {
			throw new NoTopicFoundException();
		}
		topicRegistry.get(topicName).addSubscriber(subscriber);
	}

	public void resetOffset(@NonNull String topicName, @NonNull String subscriberID, @NonNull Integer offset) {
		if (!topicRegistry.containsKey(topicName)) {
			throw new NoTopicFoundException();
		}
		TopicHandler topicHandler = topicRegistry.get(topicName);
		Optional<TopicSubscriber> subscriber = topicHandler.getTopic().getSubscribers().stream()
				.filter(s -> s.getSubscriber().getID().equals(subscriberID)).findFirst();
		if (!subscriber.isPresent()) {
			throw new SubscriberNotFoundException();
		}
		subscriber.get().getOffset().set(offset);
		new Thread(() -> topicHandler.startSubscriberWorker(subscriber.get())).start();
	}
}
