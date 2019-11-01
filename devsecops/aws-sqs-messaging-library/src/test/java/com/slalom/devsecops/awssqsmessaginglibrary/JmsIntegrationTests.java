package com.slalom.devsecops.awssqsmessaginglibrary;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Session;

import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableJms
public class JmsIntegrationTests {
    private String eventType = "testEventType";
    private String entityType = "testEntityType";
    private String retryCount = "testRetryCount";
    private String messageBody = "testMessageBody";
    private static SpringApp app = new SpringApp();

    private static SQSRestServer sqsMockServer;
    private static AmazonSQS sqsClient;
    private static String testQueueUrl;

    private QueueMessageSender messageSender;

    @BeforeAll
    static void setupClass() {

        sqsMockServer = SQSRestServerBuilder.start();
        sqsMockServer.waitUntilStarted();
        sqsClient = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("x", "x")))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:9324", "elasticmq"))
                .build();
        testQueueUrl = sqsClient.createQueue(SpringApp.TEST_QUEUE_NAME).getQueueUrl();

    }

    @AfterAll
    static void teardownClass() {
        sqsMockServer.stopAndWait();
    }

    @BeforeEach
    void setupTest() throws Exception {
        messageSender =
                new QueueMessageSender(app.getQueueConnectionFactory()
                        .createConnection().createSession(false, Session.CLIENT_ACKNOWLEDGE));
    }

    /*
     * This test currently¡ uses an actual SQS queue that is designated for this test only.
     *
     * In the near future, we should replace the actual SQS instance with ElasticMQ that would allow
     * us to have an actual test environment that we have more control over.
     *
     */
    @Test
    public void sendMessageFromSpringAppWithBodyAndAttributesTests() throws JMSException {
        messageSender.sendMessage(SpringApp.TEST_QUEUE_NAME, messageBody, eventType, entityType, retryCount);

        /*
         * The following checks to see that there are no more messages left in the SQS Queue.
         * The JmsListener in the SpringApp should catch and remove all messages from the queue.
         */
        ReceiveMessageRequest emptyCheckMsgReq =
                new ReceiveMessageRequest(testQueueUrl)
                        .withMaxNumberOfMessages(10)
                        .withWaitTimeSeconds(5)
                        .withMessageAttributeNames("Attributes");

        List<Message> receivedEmptyCheckMsgs = sqsClient.receiveMessage(emptyCheckMsgReq).getMessages();

        assertTrue(receivedEmptyCheckMsgs.isEmpty());
    }

    private Map<String, MessageAttributeValue> getTestMessageAttributes() {
        Map<String, MessageAttributeValue> expectedAttributes = new HashMap<>();
        expectedAttributes.put("EventType", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(eventType));

        expectedAttributes.put("EntityType", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(entityType));

        expectedAttributes.put("RetryCount", new MessageAttributeValue()
                .withDataType("Number")
                .withStringValue(retryCount));

        return expectedAttributes;
    }
}
