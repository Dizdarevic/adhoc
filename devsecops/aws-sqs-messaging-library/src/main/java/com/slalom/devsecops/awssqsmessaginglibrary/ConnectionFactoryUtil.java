package com.slalom.devsecops.awssqsmessaginglibrary;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.util.HashMap;
import java.util.Map;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class ConnectionFactoryUtil {

    public static void sendMessageWithAttributes(
            MessageProducer producer,
            String messageBody,
            Map<String, MessageAttributeValue> messageAttributes,
            Session session
    ) throws JMSException {

        TextMessage msg = session.createTextMessage();
        msg.setText(messageBody);
        msg.setObjectProperty("EventType", messageAttributes.get("EventType").getStringValue());

        producer.send(msg);
    }

    public static Map<String, MessageAttributeValue> createMessageAttributes(String eventType) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("EventType", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(eventType));

        return messageAttributes;
    }

    public static ConnectionFactory getConnectionFactory(String region) {
        AmazonSQSClientBuilder builder = AmazonSQSClientBuilder.standard()
                .withRegion(region)
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance());

        return new SQSConnectionFactory(new ProviderConfiguration(), builder);
    }

    public static ConnectionFactory getConnectionFactory(String endpoint, String region) {
        AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("x", "x")))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .build();
        return new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);
    }

    public static Destination getDestination(String queueName, Session session) throws JMSException {
        return session.createQueue(queueName);
    }
}
