package com.farman.mqueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.farman.mqueue.model.Message;
import com.farman.mqueue.repository.QueueManager;
import com.farman.mqueue.service.SleepySubscriber;

@SpringBootApplication
public class MessagingQueueApplication implements CommandLineRunner{

	@Autowired
	private QueueManager queueManager;
	
	public static void main(String[] args) {
		SpringApplication.run(MessagingQueueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		queueManager.addTopic("topic1");
		queueManager.addTopic("topic2");
		
		queueManager.addSubscriber("topic1", new SleepySubscriber("S1",1000));
		queueManager.addSubscriber("topic1", new SleepySubscriber("S2",1000));
		queueManager.addSubscriber("topic1", new SleepySubscriber("S3",1000));
		
		queueManager.addSubscriber("topic2", new SleepySubscriber("T2S1",1000));
		
		queueManager.publishMsg("topic1", new Message("1", "msg 1"));
		queueManager.publishMsg("topic1", new Message("2", "msg 2"));
		queueManager.publishMsg("topic1", new Message("3", "msg 3"));
		queueManager.publishMsg("topic1", new Message("4", "msg 4"));
		queueManager.publishMsg("topic1", new Message("5", "msg 5"));

		queueManager.publishMsg("topic2", new Message("1", "msg 1"));
		queueManager.publishMsg("topic2", new Message("2", "msg 2"));
		queueManager.publishMsg("topic2", new Message("3", "msg 3"));
		queueManager.publishMsg("topic2", new Message("4", "msg 4"));
		
		
		
		//queueManager.publishMsg("topic3", new Message("5", "msg 5"));
		
	}

}
