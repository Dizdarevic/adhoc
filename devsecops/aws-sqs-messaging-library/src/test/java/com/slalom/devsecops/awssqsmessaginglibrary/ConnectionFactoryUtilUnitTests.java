package com.slalom.devsecops.awssqsmessaginglibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.util.HashMap;
import java.util.Map;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.jupiter.api.Test;

public class ConnectionFactoryUtilUnitTests {

    private static final String testQueueName = "test_queue";
    private String eventType = "testEventType";
    private String messageBody = "testMessageBody";
    private String testRegion = "us-east-1";
    private String elasticMqRegion = "elasticmq";
    private String elasticMqEndpoint = "http://localhost:9324";
    private Session testSession = mock(Session.class);
    private MessageProducer producer = mock(MessageProducer.class);

    private Map<String, MessageAttributeValue> getTestMessageAttributes() {
        Map<String, MessageAttributeValue> expectedAttributes = new HashMap<>();
        expectedAttributes.put("EventType", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(eventType));

        return expectedAttributes;
    }

    @Test
    public void getConnectionFactoryShouldReturnANewConnectionFactory() {
        assertEquals(ConnectionFactoryUtil.getConnectionFactory(testRegion).getClass(), SQSConnectionFactory.class);
    }

    @Test
    public void getElasticMqConnectionFactoryShouldReturnANewElasticMqConnectionFactory() {
        assertEquals(ConnectionFactoryUtil.getConnectionFactory(elasticMqEndpoint, elasticMqRegion).getClass(),
                SQSConnectionFactory.class);
    }

    @Test
    public void getDestinationShouldReturnADestination() throws JMSException {
        Destination expectedDest = testSession.createQueue(testQueueName);
        Destination actualDest = ConnectionFactoryUtil.getDestination(testQueueName, testSession);

        assertEquals(expectedDest, actualDest);
    }

    @Test
    public void createMessageAttributesShouldReturnExpectedMessageAttributes() {
        Map<String, MessageAttributeValue> actualAttributes =
                ConnectionFactoryUtil.createMessageAttributes(eventType);

        assertEquals(getTestMessageAttributes(), actualAttributes);
    }

    @Test
    public void sendMessageWithAttributesShouldSendTheMessageWithAttachedAttributes() throws JMSException {
        TextMessage msg = mock(TextMessage.class);
        when(testSession.createTextMessage()).thenReturn(msg);

        msg.setText(messageBody);
        msg.setObjectProperty("Attributes", getTestMessageAttributes().toString());

        ConnectionFactoryUtil.sendMessageWithAttributes(producer, messageBody, getTestMessageAttributes(), testSession);

        verify(producer, times(1)).send(eq(msg));
    }
}
