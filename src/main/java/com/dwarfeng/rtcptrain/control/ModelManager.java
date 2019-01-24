package com.dwarfeng.rtcptrain.control;

import com.dwarfeng.dutil.develop.backgr.Background;
import com.dwarfeng.dutil.develop.i18n.SyncI18nHandler;
import com.dwarfeng.dutil.develop.logger.SyncLoggerHandler;
import com.dwarfeng.dutil.develop.resource.SyncResourceHandler;
import com.dwarfeng.dutil.develop.setting.SyncSettingHandler;
import com.dwarfeng.rtcptrain.model.SyncRTCPParamModel;
import com.dwarfeng.rtcptrain.model.SyncRotateAxisModel;

public interface ModelManager {

	/**
	 * @return the actualRTCPParamModel
	 */
	public SyncRTCPParamModel getActualRTCPParamModel();

	/**
	 * @return the background
	 */
	public Background getBackground();

	/**
	 * @return the coreSettingHandler
	 */
	public SyncSettingHandler getCoreSettingHandler();

	/**
	 * @return the currentRTCPParamModel
	 */
	public SyncRTCPParamModel getCurrentRTCPParamModel();

	/**
	 * @return the datumRotateAxisModel
	 */
	public SyncRotateAxisModel getDatumRotateAxisModel();

	/**
	 * @return the i18nHandler
	 */
	public SyncI18nHandler getI18nHandler();

	/**
	 * @return the loggerHandler
	 */
	public SyncLoggerHandler getLoggerHandler();

	/**
	 * @return the measureRotateModel
	 */
	public SyncRotateAxisModel getMeasureRotateModel();

	/**
	 * @return the modalSettingHandler
	 */
	public SyncSettingHandler getModalSettingHandler();

	/**
	 * @return the resourceHandler
	 */
	public SyncResourceHandler getResourceHandler();

}
