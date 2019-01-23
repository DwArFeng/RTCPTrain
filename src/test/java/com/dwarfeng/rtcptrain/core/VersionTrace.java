package com.dwarfeng.rtcptrain.core;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.rtcptrain.control.RTCPTrain;

/**
 * 用于输出版本号的工具。
 * 
 * @author DwArFeng
 * @since 1.1.0
 */
public class VersionTrace {

	public static void main(String[] args) {
		CT.trace(RTCPTrain.VERSION.getLongName());
	}

}
