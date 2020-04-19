package com.dkatalis.parkinglot.dao;

public interface IParkingSlotDataAccessor {
	public void getStatus();
	public void parkVehical(String vehicalNo);
	public void releaseParking(String vehicalNo, int duration);
}
