package learn.mishudi.kafkastreams.join2streams.dto;

/**
 * @author svyas
 */
public class VehicleMaintenanceDto {
	private String vin;
	private Boolean maintenanceDue;
	private String maintenanceDetails;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Boolean isMaintenanceDue() {
		return maintenanceDue;
	}

	public void setMaintenanceDue(Boolean maintenanceDue) {
		this.maintenanceDue = maintenanceDue;
	}

	public String getMaintenanceDetails() {
		return maintenanceDetails;
	}

	public void setMaintenanceDetails(String maintenanceDetails) {
		this.maintenanceDetails = maintenanceDetails;
	}

	@Override
	public String toString() {
		return "VehicleMaintenanceDto{" +
				"vin='" + vin + '\'' +
				", maintenanceDue=" + maintenanceDue +
				", maintenanceDetails='" + maintenanceDetails + '\'' +
				'}';
	}
}
