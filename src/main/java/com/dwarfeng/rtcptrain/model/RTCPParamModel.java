package com.dwarfeng.rtcptrain.model;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.rtcptrain.model.obverser.RTCPParamObverser;

/**
 * RTCP参数模型。
 * 
 * <pre>
 * V0: C轴回转矢量
 * V1: C轴偏移矢量
 * V2: A轴回转矢量
 * V3: A轴偏移矢量
 * </pre>
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface RTCPParamModel extends ObverserSet<RTCPParamObverser> {

	public double getV00();

	public double getV01();

	public double getV02();

	public double getV10();

	public double getV11();

	public double getV12();

	public double getV20();

	public double getV21();

	public double getV22();

	public double getV30();

	public double getV31();

	public double getV32();

	public double getToolLength();

	public boolean setV00(double value) throws UnsupportedOperationException;

	public boolean setV01(double value) throws UnsupportedOperationException;

	public boolean setV02(double value) throws UnsupportedOperationException;

	public boolean setV10(double value) throws UnsupportedOperationException;

	public boolean setV11(double value) throws UnsupportedOperationException;

	public boolean setV12(double value) throws UnsupportedOperationException;

	public boolean setV20(double value) throws UnsupportedOperationException;

	public boolean setV21(double value) throws UnsupportedOperationException;

	public boolean setV22(double value) throws UnsupportedOperationException;

	public boolean setV30(double value) throws UnsupportedOperationException;

	public boolean setV31(double value) throws UnsupportedOperationException;

	public boolean setV32(double value) throws UnsupportedOperationException;

	public boolean setToolLength(double value) throws UnsupportedOperationException;

}
