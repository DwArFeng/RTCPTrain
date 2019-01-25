package com.dwarfeng.rtcptrain.model.enumeration;

/**
 * 测量方向枚举。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum MeasureDirection {

	X(0), Y(1), Z(2),

	;

	private final int index;

	private MeasureDirection(int index) {
		this.index = index;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	};

}
