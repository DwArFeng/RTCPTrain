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

	LABEL_1("label.1"), //
	LABEL_2("label.2"), //
	LABEL_3("label.3"), //
	LABEL_4("label.4"), //
	LABEL_5("label.5"), //
	LABEL_6("label.6"), //
	LABEL_7("label.7"), //
	LABEL_8("label.8"), //
	LABEL_9("label.9"), //
	LABEL_10("label.10"), //
	LABEL_11("label.11"), //
	LABEL_12("label.12"), //
	LABEL_13("label.13"), //
	LABEL_14("label.14"), //
	LABEL_15("label.15"), //
	LABEL_16("label.16"), //
	LABEL_17("label.17"), //
	LABEL_18("label.18"), //
	LABEL_19("label.19"), //
	LABEL_20("label.20"), //
	LABEL_21("label.21"), //
	LABEL_22("label.22"), //
	LABEL_23("label.23"), //
	LABEL_24("label.24"), //

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
