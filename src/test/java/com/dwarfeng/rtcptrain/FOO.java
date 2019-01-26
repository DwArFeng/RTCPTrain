package com.dwarfeng.rtcptrain;

import java.text.DecimalFormat;

import com.dwarfeng.dutil.basic.io.CT;

public class FOO {

	// Upper limit on integer and fraction digits for a Java double
	static final int DOUBLE_INTEGER_DIGITS = 309;
	static final int DOUBLE_FRACTION_DIGITS = 340;
	
	public static void main(String[] args) {
		DecimalFormat format = new DecimalFormat();
		// format.setMinimumFractionDigits(0);
		 format.setMaximumFractionDigits(DOUBLE_FRACTION_DIGITS);
		// format.setMinimumIntegerDigits(0);
		 format.setMaximumIntegerDigits(DOUBLE_INTEGER_DIGITS);
		// format.setDecimalSeparatorAlwaysShown(false);
		CT.trace(format.format(0));
	}

}
