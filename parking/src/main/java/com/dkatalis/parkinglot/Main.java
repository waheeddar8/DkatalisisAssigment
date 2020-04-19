package com.dkatalis.parkinglot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.dkatalis.parkinglot.exception.LogicError;
import com.dkatalis.parkinglot.service.IParkingService;
import com.dkatalis.parkinglot.service.ParkingService;
import com.dkatalis.parkinglot.validator.CommandValidator;

public class Main {
	public static void main(String[] args){
		String terminate = "stop";
		IParkingService parkingService = new ParkingService();
		BufferedReader bufferReader = null;
		String input = null;

		try {
			System.out.println("______________ #### DKatalis PARKING #### ______________\n");
			displayCommands();
			System.out.println("\n");

			int argLength = args.length;
			if (argLength == 0) {
				System.out.println("Enter << exit >> to exit further process..!!");
				System.out.println("--> ");
				while (true) {
					try {
						bufferReader = new BufferedReader(new InputStreamReader(System.in));
						input = bufferReader.readLine().trim();

						if (input.equalsIgnoreCase(terminate)){
							break;
						}
						CommandValidator.validate(input);
						parkingService.process(input);

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}else if (argLength == 1) {
				try {
					bufferReader = new BufferedReader(new FileReader( new File(args[0])));
					int lineNo = 1;
					
					while ((input = bufferReader.readLine()) != null) {
						try {
							CommandValidator.validate(input.trim());
						} catch (LogicError e) {
							System.out.println("Invalid Command Passed at line no --> " + lineNo);	
						}
						try{
							parkingService.process(input.trim());
						}catch (Exception e){
							System.out.println(e.getMessage());
						}
					}
				} catch (Exception e) {
					System.out.println("Invalid File loaded");
				}
			}else {
				System.out.println("Invalid Input Provided...!!!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try{
				if (bufferReader != null)
					bufferReader.close();
			}catch (IOException e){}
		}


	}

	private static void displayCommands() {
		System.out.println("<======= Use the Below Commands for your reference =======>\n");
		System.out.println("Create parking lot of size n   \t--> \t" + "create_parking_lot {capacity}");
		System.out.println("Park a car   \t\t\t--> \t" + "park {car_number}");
		System.out.println("Remove(Unpark) car from   \t--> \t" + "leave {car_number} {hours}");
		System.out.println("Print status of parking slot   \t--> \t" + "status");
		
	}
}
