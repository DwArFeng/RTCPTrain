package com.dwarfeng.rtcptrain.launcher;

import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.rtcptrain.control.RTCPTrain;

/**
 * 启动任务。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
class StartTask extends AbstractTask {

	private final RTCPTrain rtcpTrain;
	private final String[] args;

	public StartTask(RTCPTrain rtcpTrain, String[] args) {
		this.rtcpTrain = rtcpTrain;
		this.args = args;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void todo() throws Exception {
		rtcpTrain.getActionManager().start(args);
	}

}
