package com.dwarfeng.rtcptrain.model.obverser;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 回转轴观察器。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface RotateAxisObverser extends Obverser {

	public void fireAChanged(double oldValue, double newValue);

	public void fireCChanged(double oldValue, double newValue);

}
