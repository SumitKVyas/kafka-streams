package learn.mishudi.kafkastreams.join2streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giladam.kafka.jacksonserde.Jackson2Serde;
import learn.mishudi.kafkastreams.join2streams.dto.VehicleDto;
import learn.mishudi.kafkastreams.join2streams.dto.VehicleMaintenanceDto;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;

import java.util.Properties;

/**
 * @author svyas
 */

/**
 * Produces records for two topics
 */
public class KafkaStreamProducer {

	public KafkaStreamProducer() {
		sendMessagesToLeftTopic();
		sendMessagesToRightTopic();
	}

	private static final String KEY = "abcd1234";
	protected ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private void sendMessagesToLeftTopic() {

		Serde<VehicleDto> vehicleDtoSerde = new Jackson2Serde<>(OBJECT_MAPPER, VehicleDto.class);

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9095");
		props.put("acks", "all");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "learn.mishudi.kafkastreams.join2streams.KafkaStreamsCustomValueSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);

		VehicleDto vehDto = new VehicleDto();
		vehDto.setVin("abcd1234");
		vehDto.setMake("BMW");
		vehDto.setModel("X7");
		vehDto.setYear(2019l);

		try {
			producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, vehDto));
		} finally {
			producer.close();
		}
	}

	private void sendMessagesToRightTopic() {

		Serde<VehicleMaintenanceDto> vehicleMaintenanceDtoSerde = new Jackson2Serde<>(OBJECT_MAPPER, VehicleMaintenanceDto.class);

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9095");
		props.put("acks", "all");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "learn.mishudi.kafkastreams.join2streams.KafkaStreamsCustomValueSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);

		VehicleMaintenanceDto vehMaintenanceDto = new VehicleMaintenanceDto();
		vehMaintenanceDto.setVin("abcd1234");
		vehMaintenanceDto.setMaintenanceDetails("Need to repair engine - 5000");
		vehMaintenanceDto.setMaintenanceDue(true);

		try {
			producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, vehMaintenanceDto));
		} finally {
			producer.close();
		}
	}

	public static void main(String... args) {
		new KafkaStreamProducer();
	}
}
