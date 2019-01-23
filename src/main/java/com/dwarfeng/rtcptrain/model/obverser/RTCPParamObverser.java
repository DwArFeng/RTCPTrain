package com.dwarfeng.rtcptrain.model.obverser;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * RTCP参数模型观察器。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface RTCPParamObverser extends Obverser {

	public void fireV00Changed(double oldValue, double newValue);

	public void fireV01Changed(double oldValue, double newValue);

	public void fireV02Changed(double oldValue, double newValue);

	public void fireV10Changed(double oldValue, double newValue);

	public void fireV11Changed(double oldValue, double newValue);

	public void fireV12Changed(double oldValue, double newValue);

	public void fireV20Changed(double oldValue, double newValue);

	public void fireV21Changed(double oldValue, double newValue);

	public void fireV22Changed(double oldValue, double newValue);

	public void fireV30Changed(double oldValue, double newValue);

	public void fireV31Changed(double oldValue, double newValue);

	public void fireV32Changed(double oldValue, double newValue);

	public void fireToolLengthChanged(double oldValue, double newValue);

}
