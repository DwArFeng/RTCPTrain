package com.dwarfeng.rtcptrain.launcher;

import com.dwarfeng.dutil.develop.backgr.BackgroundUtil;
import com.dwarfeng.dutil.develop.backgr.Task;
import com.dwarfeng.rtcptrain.control.RTCPTrain;

public class RTCPTrainLLauncher {

	public static void main(String[] args) throws InterruptedException {
		RTCPTrain rtcpTrain = new RTCPTrain();
		StartTask startTask = new StartTask(rtcpTrain, args);
		rtcpTrain.getActionManager().submit(startTask);
		rtcpTrain.getActionManager()
				.submit(BackgroundUtil.blockedTask(new WelcomeTask(rtcpTrain), new Task[] { startTask }));
		rtcpTrain.awaitFinish();
		System.exit(rtcpTrain.getExitCode());
	}

}
