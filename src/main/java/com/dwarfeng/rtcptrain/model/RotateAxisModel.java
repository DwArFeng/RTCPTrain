package com.dwarfeng.rtcptrain.model;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.rtcptrain.model.obverser.RotateAxisObverser;

/**
 * 回转轴模型。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface RotateAxisModel extends ObverserSet<RotateAxisObverser> {

	public double getA();

	public double getC();

	public boolean setA(double value) throws UnsupportedOperationException;

	public boolean setC(double value) throws UnsupportedOperationException;

}
