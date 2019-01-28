package com.dwarfeng.rtcptrain.model.enumeration;

import com.dwarfeng.dutil.develop.setting.SettingEnumItem;
import com.dwarfeng.dutil.develop.setting.SettingInfo;
import com.dwarfeng.dutil.develop.setting.info.BooleanSettingInfo;
import com.dwarfeng.dutil.develop.setting.info.DoubleSettingInfo;
import com.dwarfeng.dutil.develop.setting.info.LocaleSettingInfo;

/**
 * 核心设置条目。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum CoreSettingItem implements SettingEnumItem {

	/** 国际化地区。 */
	I18N_LOCALE("i18n.locale", new LocaleSettingInfo("zh_CN")),

	/** 实际值的V00是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V00("policy.normal-distribution.enabled.actual-v00",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V01是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V01("policy.normal-distribution.enabled.actual-v01",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V02是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V02("policy.normal-distribution.enabled.actual-v02",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V10是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V10("policy.normal-distribution.enabled.actual-v10",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V11是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V11("policy.normal-distribution.enabled.actual-v11",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V12是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V12("policy.normal-distribution.enabled.actual-v12",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V20是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V20("policy.normal-distribution.enabled.actual-v20",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V21是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V21("policy.normal-distribution.enabled.actual-v21",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V22是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V22("policy.normal-distribution.enabled.actual-v22",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V30是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V30("policy.normal-distribution.enabled.actual-v30",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V31是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V31("policy.normal-distribution.enabled.actual-v31",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的V32是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V32("policy.normal-distribution.enabled.actual-v32",
			new BooleanSettingInfo("FALSE")),
	/** 实际值的刀具长度是否启用正态分布。 */
	POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_TOOL_LENGTH("policy.normal-distribution.enabled.actual-tool-length",
			new BooleanSettingInfo("FALSE")),

	/** 实际值的V00对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V00("param.normal-distribution.mean.actual-v00", new DoubleSettingInfo("0")),
	/** 实际值的V01对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V01("param.normal-distribution.mean.actual-v01", new DoubleSettingInfo("0")),
	/** 实际值的V02对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V02("param.normal-distribution.mean.actual-v02", new DoubleSettingInfo("1")),
	/** 实际值的V10对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V10("param.normal-distribution.mean.actual-v10", new DoubleSettingInfo("0")),
	/** 实际值的V11对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V11("param.normal-distribution.mean.actual-v11", new DoubleSettingInfo("0")),
	/** 实际值的V12对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V12("param.normal-distribution.mean.actual-v12", new DoubleSettingInfo("0")),
	/** 实际值的V20对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V20("param.normal-distribution.mean.actual-v20", new DoubleSettingInfo("1")),
	/** 实际值的V21对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V21("param.normal-distribution.mean.actual-v21", new DoubleSettingInfo("0")),
	/** 实际值的V22对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V22("param.normal-distribution.mean.actual-v22", new DoubleSettingInfo("0")),
	/** 实际值的V30对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V30("param.normal-distribution.mean.actual-v30", new DoubleSettingInfo("0")),
	/** 实际值的V31对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V31("param.normal-distribution.mean.actual-v31", new DoubleSettingInfo("0")),
	/** 实际值的V32对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V32("param.normal-distribution.mean.actual-v32",
			new DoubleSettingInfo("450")),
	/** 实际值的刀具长度对应的正态分布的期望值。 */
	PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_TOOL_LENGTH("param.normal-distribution.mean.actual-tool-length",
			new DoubleSettingInfo("150")),

	/** 实际值的V00对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V00("param.normal-distribution.sigma.actual-v00",
			new DoubleSettingInfo("0")),
	/** 实际值的V01对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V01("param.normal-distribution.sigma.actual-v01",
			new DoubleSettingInfo("0")),
	/** 实际值的V02对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V02("param.normal-distribution.sigma.actual-v02",
			new DoubleSettingInfo("0")),
	/** 实际值的V10对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V10("param.normal-distribution.sigma.actual-v10",
			new DoubleSettingInfo("0")),
	/** 实际值的V11对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V11("param.normal-distribution.sigma.actual-v11",
			new DoubleSettingInfo("0")),
	/** 实际值的V12对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V12("param.normal-distribution.sigma.actual-v12",
			new DoubleSettingInfo("0")),
	/** 实际值的V20对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V20("param.normal-distribution.sigma.actual-v20",
			new DoubleSettingInfo("0")),
	/** 实际值的V21对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V21("param.normal-distribution.sigma.actual-v21",
			new DoubleSettingInfo("0")),
	/** 实际值的V22对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V22("param.normal-distribution.sigma.actual-v22",
			new DoubleSettingInfo("0")),
	/** 实际值的V30对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V30("param.normal-distribution.sigma.actual-v30",
			new DoubleSettingInfo("0")),
	/** 实际值的V31对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V31("param.normal-distribution.sigma.actual-v31",
			new DoubleSettingInfo("0")),
	/** 实际值的V32对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V32("param.normal-distribution.sigma.actual-v32",
			new DoubleSettingInfo("0")),
	/** 实际值的刀具长度对应的正态分布的标准差值。 */
	PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_TOOL_LENGTH("param.normal-distribution.sigma.actual-tool-length",
			new DoubleSettingInfo("0")),

	;

	private final String name;
	private final SettingInfo settingInfo;

	private CoreSettingItem(String name, SettingInfo settingInfo) {
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
