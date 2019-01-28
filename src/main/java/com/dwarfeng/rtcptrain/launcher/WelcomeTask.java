package com.dwarfeng.rtcptrain.launcher;

import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.rtcptrain.control.RTCPTrain;

/**
 * 启动任务。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
class WelcomeTask extends AbstractTask {

	private final RTCPTrain rtcpTrain;

	public WelcomeTask(RTCPTrain rtcpTrain) {
		this.rtcpTrain = rtcpTrain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void todo() throws Exception {
		SwingUtil.invokeInEventQueue(() -> {
			rtcpTrain.getActionManager().info("本程序开源！程序作者：丰沛");
			rtcpTrain.getActionManager().info("源代码地址（github）：https://github.com/DwArFeng/RTCPTrain");
			rtcpTrain.getActionManager().info("源代码地址（开源中国）：https://gitee.com/dwarfeng/RTCPTrain");
		});
	}

}
