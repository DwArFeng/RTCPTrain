package com.dwarfeng.rtcptrain.model.enumeration;

import com.dwarfeng.dutil.develop.setting.SettingEnumItem;
import com.dwarfeng.dutil.develop.setting.SettingInfo;
import com.dwarfeng.dutil.develop.setting.info.BooleanSettingInfo;

/**
 * 命令行设置条目。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum CliSettingItem implements SettingEnumItem {

	/** 是否强制重置配置文件。 */
	FLAG_CONFIG_FORCE_RESET("flag.config_force_reset", new BooleanSettingInfo("false")),

	;

	private final String name;
	private final SettingInfo settingInfo;

	private CliSettingItem(String name, SettingInfo settingInfo) {
		this.name = name;
		this.settingInfo = settingInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SettingInfo getSettingInfo() {
		return settingInfo;
	}
}
