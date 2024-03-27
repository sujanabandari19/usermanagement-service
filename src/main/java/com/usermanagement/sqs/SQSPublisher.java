package com.usermanagement.sqs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

@Component
public class SQSPublisher {

    private final AmazonSQSAsync amazonSQS;
    private final String queueUrl;

    public SQSPublisher(@Value("${aws.sqs.queue-url}") String queueUrl) {
        this.amazonSQS = AmazonSQSAsyncClientBuilder.defaultClient();
        this.queueUrl = queueUrl;
    }

    public SendMessageResult publishMessage(String message) {
        SendMessageRequest request = new SendMessageRequest(queueUrl, message);
        return amazonSQS.sendMessage(request);
    }
}
