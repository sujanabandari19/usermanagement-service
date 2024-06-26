package com.usermanagement.sqs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SQSConsumer {

    private final AmazonSQS amazonSQS;
    private final String queueUrl;
    
    
    public SQSConsumer(@Value("${aws.sqs.queue-url}") String queueUrl) {
        this.amazonSQS = AmazonSQSAsyncClientBuilder.defaultClient();
        this.queueUrl = queueUrl;
    }

    @Scheduled(fixedRate = 5000) // Poll SQS every 5 seconds
    public void pollSQSQueue() {
        amazonSQS.receiveMessage(queueUrl).getMessages().forEach(message -> {
           log.info("Message received: " + message.getBody());
            amazonSQS.deleteMessage(queueUrl, message.getReceiptHandle());
        });
    }
}
