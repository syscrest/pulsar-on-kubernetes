package com.syscrest.demo.basicpulsarconsumer;

import java.util.concurrent.TimeUnit;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class BasicPulsarConsumer {

	static Logger logger = LoggerFactory.getLogger(BasicPulsarConsumer.class);

	@Parameter(names = "--topic", description = "topic-name")
	private String topic;

	@Parameter(names = "--subscription", description = "subscription name")
	private String subscription;

	@Parameter(names = { "--brokerServiceURL" }, description = "broker address")
	private String brokerServiceURL = "pulsar://localhost:6650";

	public static void main(String[] argscli) {

		BasicPulsarConsumer main = new BasicPulsarConsumer();

		JCommander.newBuilder().addObject(main).build().parse(argscli);
		main.run();
	}

	private void run() {
		try {

			PulsarClient client = PulsarClient.builder().serviceUrl(this.brokerServiceURL)
					.statsInterval(1, TimeUnit.SECONDS).build();

			Consumer<BasicMessage> consumer = client.newConsumer(JSONSchema.of(BasicMessage.class)).topic(this.topic)
					.subscriptionName(this.subscription).subscribe();

			while (true) {
				Message<BasicMessage> msg = consumer.receive();
				logger.info("recieved message = " + msg.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}
}
