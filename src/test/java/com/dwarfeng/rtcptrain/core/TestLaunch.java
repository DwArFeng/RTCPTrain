package com.dwarfeng.rtcptrain.core;

import com.dwarfeng.rtcptrain.control.RTCPTrain;

public class TestLaunch {

	public static void main(String[] args) throws InterruptedException {
		RTCPTrain rtcpTrain = new RTCPTrain();
		rtcpTrain.getActionManager().start(new String[] { "-r" });
		rtcpTrain.awaitFinish();
	}

}
