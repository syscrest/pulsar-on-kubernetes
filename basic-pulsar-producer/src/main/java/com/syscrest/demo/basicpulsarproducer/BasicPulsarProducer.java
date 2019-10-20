package com.syscrest.demo.basicpulsarproducer;

import java.util.concurrent.TimeUnit;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class BasicPulsarProducer {

	static Logger logger = LoggerFactory.getLogger(BasicPulsarProducer.class);

	@Parameter(names = { "-t", "--topic" }, description = "topic-name")
	private String topic;

	@Parameter(names = { "--brokerServiceURL" }, description = "broker address")
	private String brokerServiceURL = "pulsar://localhost:6650";

	@Parameter(names = { "--waitMs" }, description = "wait time in milliseconds between messages")
	private long waitMs = 1000l;

	public static void main(String[] argscli) {

		BasicPulsarProducer main = new BasicPulsarProducer();

		JCommander.newBuilder().addObject(main).build().parse(argscli);
		main.run();
	}

	private void run() {
		try {

			PulsarClient client = PulsarClient.builder().serviceUrl(this.brokerServiceURL)
					.statsInterval(1, TimeUnit.SECONDS).build();

			Producer<BasicMessage> producer = client.newProducer(JSONSchema.of(BasicMessage.class)).topic(this.topic)
					.create();

			long i = 0;
			while (true) {
				i++;
				logger.info("sending message # " + i);
				producer.newMessage().key("" + i).value(new BasicMessage("this is message # " + i)).send();
				Thread.sleep(this.waitMs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}
}
