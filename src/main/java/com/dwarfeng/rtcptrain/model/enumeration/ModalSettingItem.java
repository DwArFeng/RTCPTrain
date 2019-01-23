package com.dwarfeng.rtcptrain.model.enumeration;

import com.dwarfeng.dutil.develop.setting.SettingEnumItem;
import com.dwarfeng.dutil.develop.setting.SettingInfo;
import com.dwarfeng.dutil.develop.setting.info.BooleanSettingInfo;
import com.dwarfeng.dutil.develop.setting.info.FileSettingInfo;

/**
 * 模态设置条目。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum ModalSettingItem implements SettingEnumItem {

	/** 是否有上次打开的文件。 */
	FLAG_LAST_IMPORTED_FILE_EXISTS("flag.last-imported-file-exists", new BooleanSettingInfo("false")),
	/** 上次打开的文件位置。 */
	FILE_LAST_IMPORTED_FILE("path.last-imported-file", new FileSettingInfo("attribute")),
	/** 是否有上次打开的文件。 */
	FLAG_LAST_EXPORTED_FILE_EXISTS("flag.last-exported-file-exists", new BooleanSettingInfo("false")),
	/** 上次打开的文件位置。 */
	FILE_LAST_EXPORTED_FILE("path.last-exported-file", new FileSettingInfo("attribute")),

	;

	private final String name;
	private final SettingInfo settingInfo;

	private ModalSettingItem(String name, SettingInfo settingInfo) {
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
