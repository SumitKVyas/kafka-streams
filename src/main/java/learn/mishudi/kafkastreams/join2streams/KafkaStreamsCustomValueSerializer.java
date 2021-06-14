package learn.mishudi.kafkastreams.join2streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author svyas
 */
public class KafkaStreamsCustomValueSerializer implements Serializer {

	@Override
	public byte[] serialize(String topic, Object data) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(data).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
