package com.slalom.devsecops.awssqsmessaginglibrary;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.messaging.handler.annotation.Header;


@SpringBootApplication
@EnableJms
public class SpringApp {
    private Map<String, String> receivedMessages = new HashMap<>();

    @Bean
    public Map<String, String> fetchReceivedMessages() {
        return receivedMessages;
    }

    static final String TEST_QUEUE_NAME = "test";

    @Bean
    public ConnectionFactory getQueueConnectionFactory() {
        AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("x", "x")))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:9324", "elasticmq"))
                .build();
        return new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);

        return factory;
    }

    @JmsListener(destination = TEST_QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receive(String message, @Header("Attributes") String attrs) {
        receivedMessages.put(message, attrs);
        System.out.println(receivedMessages);
    }

    public static void main(String[] args) throws JMSException {
        SpringApplication.run(SpringApp.class, args);
    }
}