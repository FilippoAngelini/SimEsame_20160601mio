package it.polito.tdp.flight.model;

public class AirportAndDistance implements Comparable<AirportAndDistance>{
	
	Airport airport;
	double distance;
	
	public AirportAndDistance(Airport airport, double distance) {
		super();
		this.airport = airport;
		this.distance = distance;
	}
	/**
	 * @return the airport
	 */
	public Airport getAirport() {
		return airport;
	}
	/**
	 * @param airport the airport to set
	 */
	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airport == null) ? 0 : airport.hashCode());
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirportAndDistance other = (AirportAndDistance) obj;
		if (airport == null) {
			if (other.airport != null)
				return false;
		} else if (!airport.equals(other.airport))
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		return true;
	}
	@Override
	public int compareTo(AirportAndDistance altro) {
		
		return Double.compare(this.distance, altro.getDistance());
	}
	
	

}
