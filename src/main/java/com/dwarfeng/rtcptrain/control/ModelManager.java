package com.dwarfeng.rtcptrain.control;

import com.dwarfeng.dutil.develop.backgr.Background;
import com.dwarfeng.dutil.develop.i18n.SyncI18nHandler;
import com.dwarfeng.dutil.develop.logger.SyncLoggerHandler;
import com.dwarfeng.dutil.develop.resource.SyncResourceHandler;
import com.dwarfeng.dutil.develop.setting.SyncSettingHandler;

public interface ModelManager {

	/**
	 * @return the background
	 */
	public Background getBackground();

	/**
	 * @return the coreSettingHandler
	 */
	public SyncSettingHandler getCoreSettingHandler();

	/**
	 * @return the i18nHandler
	 */
	public SyncI18nHandler getI18nHandler();

	/**
	 * @return the loggerHandler
	 */
	public SyncLoggerHandler getLoggerHandler();

	/**
	 * @return the modalSettingHandler
	 */
	public SyncSettingHandler getModalSettingHandler();

	/**
	 * @return the resourceHandler
	 */
	public SyncResourceHandler getResourceHandler();

}
