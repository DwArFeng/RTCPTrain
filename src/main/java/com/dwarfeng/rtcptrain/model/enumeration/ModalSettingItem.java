package com.dwarfeng.rtcptrain.model.enumeration;

import com.dwarfeng.dutil.develop.setting.SettingEnumItem;
import com.dwarfeng.dutil.develop.setting.SettingInfo;
import com.dwarfeng.dutil.develop.setting.info.DoubleSettingInfo;
import com.dwarfeng.dutil.develop.setting.info.StringSettingInfo;

/**
 * 模态设置条目。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum ModalSettingItem implements SettingEnumItem {

	VALUE_CURRENT_RTCP_PARAM_V00("value.current.rtcp-param.v00", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V01("value.current.rtcp-param.v01", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V02("value.current.rtcp-param.v02", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V10("value.current.rtcp-param.v10", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V11("value.current.rtcp-param.v11", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V12("value.current.rtcp-param.v12", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V20("value.current.rtcp-param.v20", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V21("value.current.rtcp-param.v21", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V22("value.current.rtcp-param.v22", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V30("value.current.rtcp-param.v30", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V31("value.current.rtcp-param.v31", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_V32("value.current.rtcp-param.v32", new DoubleSettingInfo("0")), //
	VALUE_CURRENT_RTCP_PARAM_TOOL_LENGTH("value.current.rtcp-param.tool-length", new DoubleSettingInfo("0")), //

	VALUE_ACTUAL_RTCP_PARAM_V00("value.actual.rtcp-param.v00", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V01("value.actual.rtcp-param.v01", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V02("value.actual.rtcp-param.v02", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V10("value.actual.rtcp-param.v10", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V11("value.actual.rtcp-param.v11", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V12("value.actual.rtcp-param.v12", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V20("value.actual.rtcp-param.v20", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V21("value.actual.rtcp-param.v21", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V22("value.actual.rtcp-param.v22", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V30("value.actual.rtcp-param.v30", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V31("value.actual.rtcp-param.v31", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_V32("value.actual.rtcp-param.v32", new DoubleSettingInfo("0")), //
	VALUE_ACTUAL_RTCP_PARAM_TOOL_LENGTH("value.actual.rtcp-param.tool-length", new DoubleSettingInfo("0")), //

	VALUE_DATUM_ROTATE_AXIS_A("value.datum.rotate-axis.a", new DoubleSettingInfo("0")), //
	VALUE_DATUM_ROTATE_AXIS_C("value.datum.rotate-axis.c", new DoubleSettingInfo("0")), //

	VALUE_MEASURE_ROTATE_AXIS_A("value.measure.rotate-axis.a", new DoubleSettingInfo("0")), //
	VALUE_MEASURE_ROTATE_AXIS_C("value.measure.rotate-axis.c", new DoubleSettingInfo("0")), //

	VALUE_MEASURE_DIRECTION("value.measure-direction", new StringSettingInfo("X")),//

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
