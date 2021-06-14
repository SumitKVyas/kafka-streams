package learn.mishudi.kafkastreams.join2streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giladam.kafka.jacksonserde.Jackson2Serde;
import learn.mishudi.kafkastreams.join2streams.dto.VehicleDto;
import learn.mishudi.kafkastreams.join2streams.dto.VehicleMaintenanceDto;
import learn.mishudi.kafkastreams.join2streams.joiner.VehicleMaintenanceJoiner;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;

import java.time.Duration;
import java.util.Properties;

/**
 * @author svyas
 */

/**
 * Inner join two streams and produce result to a new output topic
 */
public class KafkaStreamsInnerJoinDemo {

	public KafkaStreamsInnerJoinDemo() {
		startStreamStreamInnerJoin();
	}

	private VehicleMaintenanceJoiner vehicleMaintenanceJoiner = new VehicleMaintenanceJoiner();
	protected ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private void startStreamStreamInnerJoin() {
		Serde<VehicleDto> vehicleDtoSerde = new Jackson2Serde<>(OBJECT_MAPPER, VehicleDto.class);
		Serde<VehicleMaintenanceDto> vehicleMaintenanceDtoSerde = new Jackson2Serde<>(OBJECT_MAPPER, VehicleMaintenanceDto.class);

		KafkaStreams streamsInnerJoin;

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-stream-inner-join");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9095");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		final StreamsBuilder builder = new StreamsBuilder();

		KStream<String, VehicleDto> leftSource = builder.stream("my-kafka-left-stream-topic", Consumed.with(Serdes.String(), new KafkaStreamsCustomSerde(VehicleDto.class)));
		KStream<String, VehicleMaintenanceDto> rightSource = builder.stream("my-kafka-right-stream-topic", Consumed.with(Serdes.String(), new KafkaStreamsCustomSerde(VehicleMaintenanceDto.class)));

		KStream<String, String> joined = leftSource.join(rightSource, vehicleMaintenanceJoiner, JoinWindows.of(Duration.ofMillis(45000)), Joined.with(Serdes.String(), vehicleDtoSerde, vehicleMaintenanceDtoSerde));

		joined.to("my-kafka-stream-stream-inner-join-out");

		final Topology topology = builder.build();
		streamsInnerJoin = new KafkaStreams(topology, props);
		streamsInnerJoin.start();
		System.out.println("Started Inner Join Demo!!");
	}

	public static void main(String... args) {
		new KafkaStreamsInnerJoinDemo();
	}
}
