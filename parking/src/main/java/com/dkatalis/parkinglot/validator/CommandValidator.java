package com.dkatalis.parkinglot.validator;

import java.util.Arrays;
import java.util.List;

import com.dkatalis.parkinglot.constants.Constants;
import com.dkatalis.parkinglot.exception.LogicError;

public class CommandValidator {
	private final static List<String> commandList = Arrays.asList(
			Constants.CREATE_PARKING_LOT,
			Constants.PARK,
			Constants.LEAVE,
			Constants.STATUS
			);
	
	public static void validate(String command) {
		String[] inputs = command.split(" ");
		if (!commandList.contains(inputs[0])) {
			throw new LogicError("INVALID COMMAND PASSED");
		}
		boolean checkValid = true;
		String initalCommand = inputs[0];
		switch (inputs.length)
		{
			case 1:
				if (!initalCommand.equals(Constants.STATUS))
					checkValid = false;
				break;
			case 2:
				if (!(initalCommand.equals(Constants.PARK) || initalCommand.equals(Constants.CREATE_PARKING_LOT)))
					checkValid = false;
				break;
			case 3:
				if (!initalCommand.equals(Constants.LEAVE))
					checkValid = false;
				break;
			default:
				checkValid = false;
		}
		
		if (!checkValid) {
			throw new LogicError("INVALID COMMAND PASSED");
		}
	}
}
