package com.dkatalis.parkinglot.service;

import com.dkatalis.parkinglot.constants.Constants;
import com.dkatalis.parkinglot.dao.ParkingSlotDataAccessor;
import com.dkatalis.parkinglot.exception.LogicError;

public class ParkingService implements IParkingService{

	ParkingSlotDataAccessor parkingDataAccessor = null;

	@Override
	public void process(String inputCommand) {
		String[] commands = inputCommand.split(" ");
		String baseCommand = commands[0];

		if (baseCommand.equals(Constants.CREATE_PARKING_LOT)) {
			try {
				int size = Integer.parseInt(commands[1]);
				initParkingLot(size);


			} catch (NumberFormatException e) {
				throw new LogicError("Second Arguement is Invalid. Should be a valid number", e);
			}
		}
		if (baseCommand.equals(Constants.PARK)) {
			park(commands[1]);
		}
		if (baseCommand.equals(Constants.LEAVE)) {
			try {
				releaseParking(commands[1], Integer.parseInt(commands[2]));
			} catch (NumberFormatException e) {
				throw new LogicError("Second / Third Arguement is Invalid. Should be a valid number", e);
			}
		}
		if (baseCommand.equals(Constants.STATUS)) {
			getStatus();

		}
	}

	private void getStatus() {
		if (parkingDataAccessor == null)
			throw new LogicError("Parking Slots are not created...!!!");
		parkingDataAccessor.getStatus();
		
	}

	private void initParkingLot(int size) {
		if (parkingDataAccessor != null)
			throw new LogicError("Parking Slots are already created...!!!");

		parkingDataAccessor = ParkingSlotDataAccessor.getinstannce(size);
		System.out.println("Created parking lot with " + size + " slots");
	}

	private void park(String vehicalNo) {
		if (parkingDataAccessor == null)
			throw new LogicError("Parking Slots are not created...!!!");
		parkingDataAccessor.parkVehical(vehicalNo);
	}

	private void releaseParking(String vehicalNo, int duration) {
		if (parkingDataAccessor == null)
			throw new LogicError("Parking Slots are not created...!!!");

		parkingDataAccessor.releaseParking(vehicalNo, duration);
	}





}
