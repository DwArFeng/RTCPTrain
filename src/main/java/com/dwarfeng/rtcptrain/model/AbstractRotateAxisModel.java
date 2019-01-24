package com.dwarfeng.rtcptrain.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import com.dwarfeng.rtcptrain.model.obverser.RotateAxisObverser;

/**
 * 抽象回转轴模型。
 * 
 * <p>
 * 回转轴模型的抽象实现。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractRotateAxisModel implements RotateAxisModel {

	/** 抽象回转轴模型的侦听器集合。 */
	protected final Set<RotateAxisObverser> obversers;

	/**
	 * 新实例。
	 */
	public AbstractRotateAxisModel() {
		this(Collections.newSetFromMap(new WeakHashMap<>()));
	}

	/**
	 * 新实例。
	 * 
	 * @param obversers 指定的观察器集合。
	 * @throws NullPointerException 指定的入口参数为 <code> null </code>。
	 */
	public AbstractRotateAxisModel(Set<RotateAxisObverser> obversers) throws NullPointerException {
		Objects.requireNonNull(obversers, "入口参数 obversers 不能为 null。");
		this.obversers = obversers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<RotateAxisObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addObverser(RotateAxisObverser obverser) {
		return obversers.add(obverser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeObverser(RotateAxisObverser obverser) {
		return obversers.remove(obverser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearObverser() {
		obversers.clear();
	}

	protected void fireAChanged(double oldValue, double newValue) {
		for (RotateAxisObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireAChanged(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireCChanged(double oldValue, double newValue) {
		for (RotateAxisObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireCChanged(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

}
