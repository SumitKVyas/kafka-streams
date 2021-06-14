package learn.mishudi.kafkastreams.join2streams.joiner;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.mishudi.kafkastreams.join2streams.dto.DecoratedVehicleDto;
import learn.mishudi.kafkastreams.join2streams.dto.VehicleDto;
import learn.mishudi.kafkastreams.join2streams.dto.VehicleMaintenanceDto;
import org.apache.kafka.streams.kstream.ValueJoiner;

/**
 * @author svyas
 */
public class VehicleMaintenanceJoiner implements ValueJoiner<VehicleDto, VehicleMaintenanceDto, String> {

	@Override
	public String apply(VehicleDto v1, VehicleMaintenanceDto v2) {
		DecoratedVehicleDto decoratedVehicleDto = new DecoratedVehicleDto();
		decoratedVehicleDto.setVin(v1.getVin());
		decoratedVehicleDto.setMake(v1.getMake());
		decoratedVehicleDto.setModel(v1.getModel());
		decoratedVehicleDto.setYear(v1.getYear());
		if (v2.isMaintenanceDue() && v2.getMaintenanceDetails() != null) {
			String cost = v2.getMaintenanceDetails().split(" - ")[1];
			decoratedVehicleDto.setEstimatedCost(Double.valueOf(cost));
		}
		String response = null;
		try {
			response = new ObjectMapper().writeValueAsString(decoratedVehicleDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
