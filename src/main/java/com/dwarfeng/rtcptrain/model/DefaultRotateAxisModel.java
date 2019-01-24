package com.dwarfeng.rtcptrain.model;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import com.dwarfeng.rtcptrain.model.obverser.RotateAxisObverser;

public class DefaultRotateAxisModel extends AbstractRotateAxisModel {

	private double a;
	private double c;

	public DefaultRotateAxisModel() {
		this(0, 0, Collections.newSetFromMap(new WeakHashMap<>()));
	}

	public DefaultRotateAxisModel(double a, double c) {
		this(a, c, Collections.newSetFromMap(new WeakHashMap<>()));
	}

	public DefaultRotateAxisModel(double a, double c, Set<RotateAxisObverser> obversers) throws NullPointerException {
		super(obversers);
		this.a = a;
		this.c = c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getA() {
		return a;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getC() {
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setA(double value) throws UnsupportedOperationException {
		double oldValue = a;
		a = value;
		fireAChanged(oldValue, value);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setC(double value) throws UnsupportedOperationException {
		double oldValue = c;
		c = value;
		fireCChanged(oldValue, value);
		return true;
	}

}
