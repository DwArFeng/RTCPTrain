package com.dwarfeng.rtcptrain.model.enumeration;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 国际化文本键。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum I18nKey implements Name {

	LOGGER_1("logger.1"), //
	LOGGER_2("logger.2"), //
	LOGGER_3("logger.3"), //
	LOGGER_4("logger.4"), //
	LOGGER_5("logger.5"), //
	LOGGER_6("logger.6"), //
	LOGGER_7("logger.7"), //
	LOGGER_8("logger.8"), //
	LOGGER_9("logger.9"), //
	LOGGER_10("logger.10"), //
	LOGGER_11("logger.11"), //
	LOGGER_12("logger.12"), //
	LOGGER_13("logger.13"), //
	LOGGER_14("logger.14"), //
	LOGGER_15("logger.15"), //
	LOGGER_16("logger.16"), //
	LOGGER_17("logger.17"), //
	LOGGER_18("logger.18"), //
	LOGGER_19("logger.19"), //
	LOGGER_20("logger.20"), //
	LOGGER_21("logger.21"), //
	LOGGER_22("logger.22"), //
	LOGGER_23("logger.23"), //
	LOGGER_24("logger.24"), //
	LOGGER_25("logger.25"), //
	LOGGER_26("logger.26"), //
	LOGGER_27("logger.27"), //
	LOGGER_28("logger.28"), //

	;

	private String name;

	private I18nKey(String name) {
		this.name = name;
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
	public String toString() {
		return name;
	}
}
