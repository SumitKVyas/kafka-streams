package learn.mishudi.kafkastreams.join2streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author svyas
 */
public class KafkaStreamsCustomValueDeserializer implements Deserializer {

	private Class typeToDeserialize;
	public KafkaStreamsCustomValueDeserializer(Class typeToDeserialize) {
		this.typeToDeserialize = typeToDeserialize;
	}

	@Override
	public Object deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		Object obj = null;
		try {
			obj = mapper.readValue(data, this.typeToDeserialize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
