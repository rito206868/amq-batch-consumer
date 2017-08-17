package com.springboot.camel;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;




public class CamelServiceRoute extends RouteBuilder{


	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure method");
		CamelContext camelContext = getContext();
		
		camelContext.addComponent("activemq", jmsComponentAutoAcknowledge(connectionFactory));
		
		from("timer:foo?fixedRate=true&period=5000&delay=3000")
		.routeId("amq-batch-consumer")
		.pollEnrich("activemq:queue:TEST.GOEP??asyncConsumer=true&concurrentConsumers=10")
		.log(LoggingLevel.INFO, "${body}");
		
	}
	
	/**
	 * 
	 * @param connectionFactory
	 * @return
	 */
	public static JmsComponent jmsComponentAutoAcknowledge(ConnectionFactory connectionFactory) {
		        JmsConfiguration template = new JmsConfiguration(connectionFactory);
		        template.setAcknowledgementMode(Session.AUTO_ACKNOWLEDGE);
		        return new JmsComponent(template);
		    }
}
