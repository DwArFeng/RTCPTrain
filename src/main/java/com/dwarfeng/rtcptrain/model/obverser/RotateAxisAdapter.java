package com.dwarfeng.rtcptrain.model.obverser;

/**
 * 回转轴观察器适配器。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class RotateAxisAdapter implements RotateAxisObverser {

	@Override
	public void fireAChanged(double oldValue, double newValue) {
	}

	@Override
	public void fireCChanged(double oldValue, double newValue) {
	}

}
