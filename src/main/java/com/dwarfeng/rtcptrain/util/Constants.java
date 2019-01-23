package com.dwarfeng.rtcptrain.util;

/**
 * 程序常量。
 * 
 * <p>
 * 禁止继承以及外部实例化。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class Constants {

	/** 默认的丢失文本字段。 */
	public static final String MISSING_LABEL = "！文本丢失";

	/** 资源列表所在的路径。 */
	public static final String JPATH_RESOURCE_LIST = "/com/dwarfeng/rtcptrain/resources/resource-list.xml";
	/** Jar包内默认的国际化属性文件路径。 */
	public static final String JPATH_DEFAULT_I18N_PROP_FILE = "/com/dwarfeng/rtcptrain/resources/i18n/zh_CN.properties";

	/** 代表强制重置配置文件的命令行参数。 */
	public static final String CLI_OPT_FLAG_CONFIG_FORCE_RESET = "r";

	private Constants() {
		throw new IllegalStateException("禁止外部实例化。");
	}

}
