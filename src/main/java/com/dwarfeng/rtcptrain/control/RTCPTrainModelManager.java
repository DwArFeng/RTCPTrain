package com.dwarfeng.rtcptrain.control;

import com.dwarfeng.dutil.basic.cna.model.SyncReferenceModel;
import com.dwarfeng.dutil.develop.backgr.Background;
import com.dwarfeng.dutil.develop.i18n.SyncI18nHandler;
import com.dwarfeng.dutil.develop.logger.SyncLoggerHandler;
import com.dwarfeng.dutil.develop.resource.SyncResourceHandler;
import com.dwarfeng.dutil.develop.setting.SyncSettingHandler;
import com.dwarfeng.rtcptrain.model.SyncRTCPParamModel;
import com.dwarfeng.rtcptrain.model.SyncRotateAxisModel;
import com.dwarfeng.rtcptrain.model.enumeration.MeasureDirection;

class RTCPTrainModelManager implements ModelManager {

	private final RTCPTrain rtcpTrain;

	public RTCPTrainModelManager(RTCPTrain rtcpTrain) {
		this.rtcpTrain = rtcpTrain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncRTCPParamModel getActualRTCPParamModel() {
		return rtcpTrain.getActualRTCPParamModel();
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
	public SyncRTCPParamModel getCurrentRTCPParamModel() {
		return rtcpTrain.getCurrentRTCPParamModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncRotateAxisModel getDatumRotateAxisModel() {
		return rtcpTrain.getDatumRotateAxisModel();
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
	public SyncReferenceModel<MeasureDirection> getMeasureDirectionModel() {
		return rtcpTrain.getMeasureDirectionModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncReferenceModel<Double> getMeasureErrorModel() {
		return rtcpTrain.getMeasureErrorModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyncRotateAxisModel getMeasureRotateAxisModel() {
		return rtcpTrain.getMeasureRotateAxisModel();
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
