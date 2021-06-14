package learn.mishudi.kafkastreams.join2streams.dto;

/**
 * @author svyas
 */
public class VehicleDto {
	private String vin;
	private String make;
	private String model;
	private Long year;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "VehicleDto{" +
				"vin='" + vin + '\'' +
				", make='" + make + '\'' +
				", model='" + model + '\'' +
				", year=" + year +
				'}';
	}
}