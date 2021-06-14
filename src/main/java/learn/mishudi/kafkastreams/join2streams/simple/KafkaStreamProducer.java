package learn.mishudi.kafkastreams.join2streams.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author svyas
 */

/**
 * Produces records for two topics
 */
public class KafkaStreamProducer {

	private static final String KEY = "FIXED-KEY";

	private static final Map<Integer, String> LEFT;
	static {
		LEFT = new HashMap<>();
		LEFT.put(1, null);
		LEFT.put(3, "A");
		LEFT.put(5, "B");
		LEFT.put(7, null);
		LEFT.put(9, "C");
		LEFT.put(12, null);
		LEFT.put(15, "D");
	}

	private static final Map<Integer, String> RIGHT;
	static {
		RIGHT = new HashMap<>();
		RIGHT.put(2, null);
		RIGHT.put(4, "a");
		RIGHT.put(6, "b");
		RIGHT.put(8, null);
		RIGHT.put(10, "c");
		RIGHT.put(11, null);
		RIGHT.put(13, null);
		RIGHT.put(14, "d");
	}

	static void sendMessages() {

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9095");
		props.put("acks", "all");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);

		try {
			for (int i = 0; i < 15; i++) {
				// Every 10 seconds send a message
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {}

				if (LEFT.containsKey(i + 1)) {
					producer.send(new ProducerRecord<String, String>("my-kafka-left-stream-topic", KEY, LEFT.get(i + 1)));
				}
				if (RIGHT.containsKey(i + 1)) {
					producer.send(new ProducerRecord<String, String>("my-kafka-right-stream-topic", KEY, RIGHT.get(i + 1)));
				}

			}
		} finally {
			producer.close();
		}
	}

	public static void main(String... args) {
		sendMessages();
	}
}
