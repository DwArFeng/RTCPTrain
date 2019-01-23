package com.dwarfeng.rtcptrain.model;

public class DefaultRTCPParamModel extends AbstractRTCPParamModel {

	private double v00;
	private double v01;
	private double v02;

	private double v10;
	private double v11;
	private double v12;

	private double v20;
	private double v21;
	private double v22;

	private double v30;
	private double v31;
	private double v32;

	private double toolLength;

	public DefaultRTCPParamModel() {
		this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public DefaultRTCPParamModel(double v00, double v01, double v02, double v10, double v11, double v12, double v20,
			double v21, double v22, double v30, double v31, double v32, double toolLength) {
		this.v00 = v00;
		this.v01 = v01;
		this.v02 = v02;
		this.v10 = v10;
		this.v11 = v11;
		this.v12 = v12;
		this.v20 = v20;
		this.v21 = v21;
		this.v22 = v22;
		this.v30 = v30;
		this.v31 = v31;
		this.v32 = v32;
		this.toolLength = toolLength;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV00() {
		return v00;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV01() {
		return v01;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV02() {
		return v02;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV10() {
		return v10;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV11() {
		return v11;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV12() {
		return v12;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV20() {
		return v20;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV21() {
		return v21;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV22() {
		return v22;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV30() {
		return v30;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV31() {
		return v31;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getV32() {
		return v32;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getToolLength() {
		return toolLength;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV00(double value) throws UnsupportedOperationException {
		double oldValue = v00;
		v00 = value;
		fireV00Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV01(double value) throws UnsupportedOperationException {
		double oldValue = v01;
		v01 = value;
		fireV01Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV02(double value) throws UnsupportedOperationException {
		double oldValue = v02;
		v02 = value;
		fireV02Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV10(double value) throws UnsupportedOperationException {
		double oldValue = v10;
		v10 = value;
		fireV10Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV11(double value) throws UnsupportedOperationException {
		double oldValue = v11;
		v11 = value;
		fireV11Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV12(double value) throws UnsupportedOperationException {
		double oldValue = v12;
		v12 = value;
		fireV12Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV20(double value) throws UnsupportedOperationException {
		double oldValue = v20;
		v20 = value;
		fireV20Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV21(double value) throws UnsupportedOperationException {
		double oldValue = v21;
		v21 = value;
		fireV21Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV22(double value) throws UnsupportedOperationException {
		double oldValue = v22;
		v22 = value;
		fireV22Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV30(double value) throws UnsupportedOperationException {
		double oldValue = v30;
		v30 = value;
		fireV30Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV31(double value) throws UnsupportedOperationException {
		double oldValue = v31;
		v31 = value;
		fireV31Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setV32(double value) throws UnsupportedOperationException {
		double oldValue = v32;
		v32 = value;
		fireV32Changed(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setToolLength(double value) throws UnsupportedOperationException {
		double oldValue = toolLength;
		toolLength = value;
		fireToolLengthChanged(oldValue, value);
		return true;
	}

}
