package com.dwarfeng.rtcptrain.util;

import java.util.Objects;

import org.la4j.LinearAlgebra.InverterFactory;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

public final class TransformUtil {

	/**
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 * @return
	 */
	public static Matrix shiftMatrix(double dx, double dy, double dz) {
		return new Basic2DMatrix(new double[][] { //
				{ 1, 0, 0, dx }, //
				{ 0, 1, 0, dy }, //
				{ 0, 0, 1, dz }, //
				{ 0, 0, 0, 1 },//
		});
	}

	/**
	 * 
	 * @param angle
	 * @param vx
	 * @param vy
	 * @param vz
	 * @return
	 */
	public static Matrix rotateMatrix(double angle, double vx, double vy, double vz) throws IllegalArgumentException {
		double vectorNorm = norm(vx, vy, vz);
		if (vectorNorm == 0) {
			throw new IllegalArgumentException("旋转矢量长度不能为0。");
		}
		double vxInUnit = vx / vectorNorm, vyInUnit = vy / vectorNorm, vzInUnit = vz / vectorNorm;
		return new Basic2DMatrix(new double[][] { //
				{ StrictMath.pow(vxInUnit, 2) + (1 - StrictMath.pow(vxInUnit, 2)) * StrictMath.cos(angle),
						vxInUnit * vyInUnit * (1 - StrictMath.cos(angle)) - vzInUnit * StrictMath.sin(angle),
						vxInUnit * vzInUnit * (1 - StrictMath.cos(angle)) + vyInUnit * StrictMath.sin(angle), 0 }, //
				{ vxInUnit * vyInUnit * (1 - StrictMath.cos(angle)) + vzInUnit * StrictMath.sin(angle),
						StrictMath.pow(vyInUnit, 2) + (1 - StrictMath.pow(vyInUnit, 2)) * StrictMath.cos(angle),
						vyInUnit * vzInUnit * (1 - StrictMath.cos(angle)) - vxInUnit * StrictMath.sin(angle), 0 }, //
				{ vxInUnit * vzInUnit * (1 - StrictMath.cos(angle)) - vyInUnit * StrictMath.sin(angle),
						vyInUnit * vzInUnit * (1 - StrictMath.cos(angle)) + vxInUnit * StrictMath.sin(angle),
						StrictMath.pow(vzInUnit, 2) + (1 - StrictMath.pow(vzInUnit, 2)) * StrictMath.cos(angle), 0 }, //
				{ 0, 0, 0, 1 },//
		});
	}

	/**
	 * 返回几个参数组成的向量的长度。
	 * 
	 * @param params
	 *            指定的参数组成的数组。
	 * @return 指定参数对应的向量的长度。
	 * @throws NullPointerException
	 *             指定的入口参数为 <code> null </code>。
	 */
	public static double norm(double... params) throws NullPointerException {
		Objects.requireNonNull(params, "入口参数 params 不能为 null。");

		double sum = 0;
		for (int i = 0; i < params.length; i++) {
			sum += StrictMath.pow(params[i], 2);
		}
		return StrictMath.sqrt(sum);
	}

	/**
	 * 
	 * @param px
	 * @param py
	 * @param pz
	 * @param v00
	 * @param v01
	 * @param v02
	 * @param v10
	 * @param v11
	 * @param v12
	 * @param v20
	 * @param v21
	 * @param v22
	 * @param v30
	 * @param v31
	 * @param v32
	 * @param a
	 * @param c
	 * @param l
	 * @return
	 */
	public static Matrix rtcpMachine2Tool(double px, double py, double pz, double v00, double v01, double v02,
			double v10, double v11, double v12, double v20, double v21, double v22, double v30, double v31, double v32,
			double a, double c, double l) throws IllegalArgumentException {
		if (norm(v00, v01, v02) == 0 || norm(v20, v21, v22) == 0) {
			throw new IllegalArgumentException("A，C回转轴矢量长度不能为0");
		}

		Matrix machinePoint = new Basic2DMatrix(new double[][] { { px }, { py }, { pz }, { 1 } });
		Matrix transformMatrix = //
				rotateMatrix(c, v00, v01, v02)//
						.multiply(shiftMatrix(-v10, -v11, -v12))//
						.multiply(rotateMatrix(a, v20, v21, v22))//
						.multiply(shiftMatrix(-v30, -v31, -v32))//
						.multiply(shiftMatrix(0, 0, -l))//
						.multiply(rotateMatrix(-a, v20, v21, v22))//
						.multiply(rotateMatrix(-c, v00, v01, v02));
		return transformMatrix.multiply(machinePoint);
	}

	/**
	 * 
	 * @param px
	 * @param py
	 * @param pz
	 * @param v00
	 * @param v01
	 * @param v02
	 * @param v10
	 * @param v11
	 * @param v12
	 * @param v20
	 * @param v21
	 * @param v22
	 * @param v30
	 * @param v31
	 * @param v32
	 * @param a
	 * @param c
	 * @param l
	 * @return
	 */
	public static Matrix rtcpTool2Machine(double px, double py, double pz, double v00, double v01, double v02,
			double v10, double v11, double v12, double v20, double v21, double v22, double v30, double v31, double v32,
			double a, double c, double l) throws IllegalArgumentException {
		if (norm(v00, v01, v02) == 0 || norm(v20, v21, v22) == 0) {
			throw new IllegalArgumentException("A，C回转轴矢量长度不能为0");
		}

		Matrix machinePoint = new Basic2DMatrix(new double[][] { { px }, { py }, { pz }, { 1 } });
		Matrix transformMatrix = //
				rotateMatrix(c, v00, v01, v02)//
						.multiply(shiftMatrix(-v10, -v11, -v12))//
						.multiply(rotateMatrix(a, v20, v21, v22))//
						.multiply(shiftMatrix(-v30, -v31, -v32))//
						.multiply(shiftMatrix(0, 0, -l))//
						.multiply(rotateMatrix(-a, v20, v21, v22))//
						.multiply(rotateMatrix(-c, v00, v01, v02));
		return transformMatrix.withInverter(InverterFactory.SMART).inverse().multiply(machinePoint);
	}

	/**
	 * 
	 * @param acV00
	 * @param acV01
	 * @param acV02
	 * @param acV10
	 * @param acV11
	 * @param acV12
	 * @param acV20
	 * @param acV21
	 * @param acV22
	 * @param acV30
	 * @param acV31
	 * @param acV32
	 * @param cuV00
	 * @param cuV01
	 * @param cuV02
	 * @param cuV10
	 * @param cuV11
	 * @param cuV12
	 * @param cuV20
	 * @param cuV21
	 * @param cuV22
	 * @param cuV30
	 * @param cuV31
	 * @param cuV32
	 * @param datumA
	 * @param datumC
	 * @param a
	 * @param c
	 * @param acL
	 * @param cuL
	 * @return
	 */
	public static Matrix rtcpError(double acV00, double acV01, double acV02, double acV10, double acV11, double acV12,
			double acV20, double acV21, double acV22, double acV30, double acV31, double acV32, double cuV00,
			double cuV01, double cuV02, double cuV10, double cuV11, double cuV12, double cuV20, double cuV21,
			double cuV22, double cuV30, double cuV31, double cuV32, double datumA, double datumC, double a, double c,
			double acL, double cuL) {
		Matrix datumPoint = rtcpMachine2Tool(0, 0, 0, acV00, acV01, acV02, acV10, acV11, acV12, acV20, acV21, acV22,
				acV30, acV31, acV32, datumA, datumC, acL);
		Matrix tempPoint = rtcpMachine2Tool(0, 0, 0, cuV00, cuV01, cuV02, cuV10, cuV11, cuV12, cuV20, cuV21, cuV22,
				cuV30, cuV31, cuV32, datumA, datumC, cuL);
		tempPoint = rtcpTool2Machine(tempPoint.get(0, 0), tempPoint.get(1, 0), tempPoint.get(2, 0), cuV00, cuV01, cuV02,
				cuV10, cuV11, cuV12, cuV20, cuV21, cuV22, cuV30, cuV31, cuV32, a, c, cuL);
		tempPoint = rtcpMachine2Tool(tempPoint.get(0, 0), tempPoint.get(1, 0), tempPoint.get(2, 0), acV00, acV01, acV02,
				acV10, acV11, acV12, acV20, acV21, acV22, acV30, acV31, acV32, a, c, acL);
		return tempPoint.subtract(datumPoint);
	}

	private TransformUtil() {
		throw new IllegalStateException("禁止外部实例化。");
	}

}
