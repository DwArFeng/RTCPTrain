package com.dwarfeng.rtcptrain.control;

import com.dwarfeng.dutil.develop.backgr.Background;
import com.dwarfeng.dutil.develop.i18n.SyncI18nHandler;
import com.dwarfeng.dutil.develop.logger.SyncLoggerHandler;
import com.dwarfeng.dutil.develop.resource.SyncResourceHandler;
import com.dwarfeng.dutil.develop.setting.SyncSettingHandler;

class RTCPTrainModelManager implements ModelManager {

	private final RTCPTrain rtcpTrain;

	public RTCPTrainModelManager(RTCPTrain rtcpTrain) {
		this.rtcpTrain = rtcpTrain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Background getBackground() {
		return rtcpTrain.getBackground();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncSettingHandler getCoreSettingHandler() {
		return rtcpTrain.getCoreSettingHandler();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncI18nHandler getI18nHandler() {
		return rtcpTrain.getI18nHandler();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncLoggerHandler getLoggerHandler() {
		return rtcpTrain.getLoggerHandler();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncSettingHandler getModalSettingHandler() {
		return rtcpTrain.getModalSettingHandler();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncResourceHandler getResourceHandler() {
		return rtcpTrain.getResourceHandler();
	}

}
