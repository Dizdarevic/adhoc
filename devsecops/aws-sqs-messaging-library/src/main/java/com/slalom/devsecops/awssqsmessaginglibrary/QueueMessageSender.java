package com.slalom.devsecops.awssqsmessaginglibrary;

import static com.slalom.devsecops.awssqsmessaginglibrary.ConnectionFactoryUtil.createMessageAttributes;
import static com.slalom.devsecops.awssqsmessaginglibrary.ConnectionFactoryUtil.sendMessageWithAttributes;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.util.Map;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;



public class QueueMessageSender {

    private Session session;

    public QueueMessageSender(Session session) {
        this.session = session;
    }

    public void sendMessage(String queueName,
                            String messageBody,
                            String eventType,
                            String entityType,
                            String retryCount) throws JMSException {

        Queue queue = session.createQueue(queueName);

        MessageProducer msgProducer = session.createProducer(queue);

        Map<String, MessageAttributeValue> messageAttributes =
                createMessageAttributes(eventType, entityType, retryCount);

        sendMessageWithAttributes(msgProducer, messageBody, messageAttributes, session);
    }
}
