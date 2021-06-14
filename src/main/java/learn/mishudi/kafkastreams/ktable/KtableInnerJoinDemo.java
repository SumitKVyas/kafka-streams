package learn.mishudi.kafkastreams.ktable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giladam.kafka.jacksonserde.Jackson2Serde;
import learn.mishudi.kafkastreams.join2streams.KafkaStreamsCustomSerde;
import learn.mishudi.kafkastreams.join2streams.dto.VehicleDto;
import learn.mishudi.kafkastreams.join2streams.dto.VehicleMaintenanceDto;
import learn.mishudi.kafkastreams.join2streams.joiner.VehicleMaintenanceJoiner;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Properties;

/**
 * @author svyas
 */
public class KtableInnerJoinDemo {
	public KtableInnerJoinDemo() {
		startKTableInnerJoin();
	}

	private VehicleMaintenanceJoiner vehicleMaintenanceJoiner = new VehicleMaintenanceJoiner();
	protected ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private void startKTableInnerJoin() {

		KafkaStreams streamsInnerJoin;

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-table-inner-join");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9095");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		final StreamsBuilder builder = new StreamsBuilder();

		KTable<String, VehicleDto> leftSource = builder.table("my-kafka-left-table-topic", Consumed.with(Serdes.String(), new KafkaStreamsCustomSerde(VehicleDto.class)));
		KTable<String, VehicleMaintenanceDto> rightSource = builder.table("my-kafka-right-table-topic", Consumed.with(Serdes.String(), new KafkaStreamsCustomSerde(VehicleMaintenanceDto.class)));

		leftSource.toStream().print(Printed.toSysOut());
		rightSource.toStream().print(Printed.toSysOut());

		KStream<String, String> joined = leftSource.join(rightSource, vehicleMaintenanceJoiner).toStream();

		joined.to("my-kafka-table-inner-join-out");

		final Topology topology = builder.build();
		streamsInnerJoin = new KafkaStreams(topology, props);
		streamsInnerJoin.start();
		System.out.println("Started Inner Join Demo!!");
	}

	public static void main(String... args) {
		new KtableInnerJoinDemo();
	}
}
