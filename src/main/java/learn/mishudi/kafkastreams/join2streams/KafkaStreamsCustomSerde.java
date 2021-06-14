package learn.mishudi.kafkastreams.join2streams;

import learn.mishudi.kafkastreams.join2streams.dto.VehicleDto;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author svyas
 */
public class KafkaStreamsCustomSerde implements Serde {

	private Class typeToWorkWith;

	public KafkaStreamsCustomSerde(Class typeToWorkWith) {
		this.typeToWorkWith = typeToWorkWith;
	}

	@Override
	public Deserializer deserializer() {
		return new KafkaStreamsCustomValueDeserializer(this.typeToWorkWith);
	}

	@Override
	public Serializer serializer() {
		return new KafkaStreamsCustomValueSerializer();
	}
}
