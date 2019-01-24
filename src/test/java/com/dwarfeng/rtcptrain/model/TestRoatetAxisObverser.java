package com.dwarfeng.rtcptrain.model;

import com.dwarfeng.rtcptrain.model.obverser.RotateAxisAdapter;
import com.dwarfeng.rtcptrain.model.obverser.RotateAxisObverser;

public class TestRoatetAxisObverser extends RotateAxisAdapter implements RotateAxisObverser {

	private int aChangeCount = 0;
	private int cChangeCount = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireAChanged(double oldValue, double newValue) {
		aChangeCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireCChanged(double oldValue, double newValue) {
		cChangeCount++;
	}

	/**
	 * @return the aChangeCount
	 */
	public int getAChangeCount() {
		return aChangeCount;
	}

	/**
	 * @return the cChangeCount
	 */
	public int getCChangeCount() {
		return cChangeCount;
	}

}
