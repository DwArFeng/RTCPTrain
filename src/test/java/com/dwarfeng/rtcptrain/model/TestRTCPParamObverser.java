package com.dwarfeng.rtcptrain.model;

import com.dwarfeng.rtcptrain.model.obverser.RTCPParamAdapter;
import com.dwarfeng.rtcptrain.model.obverser.RTCPParamObverser;

public class TestRTCPParamObverser extends RTCPParamAdapter implements RTCPParamObverser {

	private int v00ChangedCount = 0;
	private int v01ChangedCount = 0;
	private int v02ChangedCount = 0;

	private int v10ChangedCount = 0;
	private int v11ChangedCount = 0;
	private int v12ChangedCount = 0;

	private int v20ChangedCount = 0;
	private int v21ChangedCount = 0;
	private int v22ChangedCount = 0;

	private int v30ChangedCount = 0;
	private int v31ChangedCount = 0;
	private int v32ChangedCount = 0;

	private int toolLengthChangedCount = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV00Changed(double oldValue, double newValue) {
		v00ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV01Changed(double oldValue, double newValue) {
		v01ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV02Changed(double oldValue, double newValue) {
		v02ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV10Changed(double oldValue, double newValue) {
		v10ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV11Changed(double oldValue, double newValue) {
		v11ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV12Changed(double oldValue, double newValue) {
		v12ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV20Changed(double oldValue, double newValue) {
		v20ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV21Changed(double oldValue, double newValue) {
		v21ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV22Changed(double oldValue, double newValue) {
		v22ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV30Changed(double oldValue, double newValue) {
		v30ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV31Changed(double oldValue, double newValue) {
		v31ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireV32Changed(double oldValue, double newValue) {
		v32ChangedCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireToolLengthChanged(double oldValue, double newValue) {
		toolLengthChangedCount++;
	}

	/**
	 * @return the v00ChangedCount
	 */
	public int getV00ChangedCount() {
		return v00ChangedCount;
	}

	/**
	 * @return the v01ChangedCount
	 */
	public int getV01ChangedCount() {
		return v01ChangedCount;
	}

	/**
	 * @return the v02ChangedCount
	 */
	public int getV02ChangedCount() {
		return v02ChangedCount;
	}

	/**
	 * @return the v10ChangedCount
	 */
	public int getV10ChangedCount() {
		return v10ChangedCount;
	}

	/**
	 * @return the v11ChangedCount
	 */
	public int getV11ChangedCount() {
		return v11ChangedCount;
	}

	/**
	 * @return the v12ChangedCount
	 */
	public int getV12ChangedCount() {
		return v12ChangedCount;
	}

	/**
	 * @return the v20ChangedCount
	 */
	public int getV20ChangedCount() {
		return v20ChangedCount;
	}

	/**
	 * @return the v21ChangedCount
	 */
	public int getV21ChangedCount() {
		return v21ChangedCount;
	}

	/**
	 * @return the v22ChangedCount
	 */
	public int getV22ChangedCount() {
		return v22ChangedCount;
	}

	/**
	 * @return the v30ChangedCount
	 */
	public int getV30ChangedCount() {
		return v30ChangedCount;
	}

	/**
	 * @return the v31ChangedCount
	 */
	public int getV31ChangedCount() {
		return v31ChangedCount;
	}

	/**
	 * @return the v32ChangedCount
	 */
	public int getV32ChangedCount() {
		return v32ChangedCount;
	}

	/**
	 * @return the toolLengthChangedCount
	 */
	public int getToolLengthChangedCount() {
		return toolLengthChangedCount;
	}

}
