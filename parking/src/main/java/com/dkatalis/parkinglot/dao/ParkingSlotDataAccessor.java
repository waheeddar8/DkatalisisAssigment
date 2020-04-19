package com.dkatalis.parkinglot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.dkatalis.parkinglot.exception.LogicError;

public class ParkingSlotDataAccessor implements IParkingSlotDataAccessor{
	
	private Set<Integer> availableSlots;
	private Map<Integer, String> reservedParkingDataMap;
	
	private ParkingSlotDataAccessor(int capacity) {
		reservedParkingDataMap = new HashMap<>();
		availableSlots = new TreeSet<>();
		for (int i = 0; i < capacity; i++) {
			availableSlots.add(i+1);
		}
	}
	
	private static ParkingSlotDataAccessor instance = null;
	
	public static ParkingSlotDataAccessor getinstannce(int capacity) {
		if (instance == null){
			synchronized (ParkingSlotDataAccessor.class){
				if (instance == null){
					instance = new ParkingSlotDataAccessor(capacity);
				}
			}
		}
		return instance;
	}
	
	public void add(int i){
		availableSlots.add(i);
	}
	
	public int getSlot(){
		Optional<Integer> s = availableSlots.stream().findFirst();
		if (s.isPresent())
			return s.get();
		return 0;
	}
	
	public void removeSlot(int availableSlot){
		availableSlots.remove(availableSlot);
	}

	@Override
	public void parkVehical(String vehicalNo) {
		int slotNo = getSlot();
		if (slotNo == 0)
			throw new LogicError("Sorry, parking lot is full");
		
		if (reservedParkingDataMap.containsValue(vehicalNo))
			throw new LogicError("Vehical with Registration number "+ vehicalNo +" is already parked...!!!");
		
		reservedParkingDataMap.put(slotNo, vehicalNo);
		removeSlot(slotNo);
		System.out.println("Allocated slot number: " + slotNo);
	}

	@Override
	public void releaseParking(String vehicalNo, int duration) {
		if (!reservedParkingDataMap.containsValue(vehicalNo))
			throw new LogicError("Registration number " + vehicalNo + " not found");
		int slotNo = getSlotNumberByRegdNo(vehicalNo);
		reservedParkingDataMap.remove(slotNo);
		availableSlots.add(slotNo);
		int cost = calculateCost(duration);
		System.out.println("Registration number " + vehicalNo + " with Slot Number " + slotNo + " is free with Charge " + cost) ;
	}

	private int getSlotNumberByRegdNo(String vehicalNo) {
		int slotNo = 0;
		for (Entry<Integer, String> entry : reservedParkingDataMap.entrySet()) {
            if (entry.getValue().equals(vehicalNo)) {
                slotNo = entry.getKey();
            	break;
            }
        }
		return slotNo;
	}

	@Override
	public void getStatus() {
		if (reservedParkingDataMap.isEmpty()) {
			throw new LogicError("Whole Parking is Empty ....!!!");
		}
		System.out.println("Slot No.\tRegistration No.");
		
		List<String> list = reservedParkingDataMap.entrySet().stream().map(e -> e.getKey() + "\t\t" + e.getValue()).collect(Collectors.toList());
		list.forEach(s -> System.out.println(s));
		
	}

	private int calculateCost(int duration) {
		int basePrice = 20;
		int extraHrs = 0;
		if (duration > 2) {
			extraHrs = duration-2;
		}
		return basePrice + extraHrs*10;
	}
	
}
