package com.dwarfeng.rtcptrain.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import com.dwarfeng.rtcptrain.model.obverser.RTCPParamObverser;

/**
 * 抽象RTCP参数模型。
 * 
 * <p>
 * RTCP参数模型的抽象实现。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractRTCPParamModel implements RTCPParamModel {

	/** 抽象RTCP参数模型的侦听器集合。 */
	protected final Set<RTCPParamObverser> obversers;

	/**
	 * 新实例。
	 */
	public AbstractRTCPParamModel() {
		this(Collections.newSetFromMap(new WeakHashMap<>()));
	}

	/**
	 * 新实例。
	 * 
	 * @param obversers 指定的观察器集合。
	 * @throws NullPointerException 指定的入口参数为 <code> null </code>。
	 */
	public AbstractRTCPParamModel(Set<RTCPParamObverser> obversers) throws NullPointerException {
		Objects.requireNonNull(obversers, "入口参数 obversers 不能为 null。");
		this.obversers = obversers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<RTCPParamObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addObverser(RTCPParamObverser obverser) {
		return obversers.add(obverser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeObverser(RTCPParamObverser obverser) {
		return obversers.remove(obverser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearObverser() {
		obversers.clear();
	}

	protected void fireV00Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV00Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV01Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV01Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV02Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV02Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV10Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV10Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV11Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV11Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV12Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV12Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV20Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV20Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV21Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV21Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV22Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV22Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV30Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV30Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV31Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV31Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireV32Changed(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireV32Changed(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	protected void fireToolLengthChanged(double oldValue, double newValue) {
		for (RTCPParamObverser obverser : obversers) {
			if (Objects.nonNull(obverser))
				try {
					obverser.fireToolLengthChanged(oldValue, newValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

}
