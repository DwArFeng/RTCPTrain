package com.dwarfeng.rtcptrain.util;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.rtcptrain.model.RTCPParamModel;
import com.dwarfeng.rtcptrain.model.RotateAxisModel;
import com.dwarfeng.rtcptrain.model.SyncRTCPParamModel;
import com.dwarfeng.rtcptrain.model.SyncRotateAxisModel;
import com.dwarfeng.rtcptrain.model.obverser.RTCPParamObverser;
import com.dwarfeng.rtcptrain.model.obverser.RotateAxisObverser;

/**
 * 模型工具类。
 * 
 * <p>
 * 禁止继承以及外部实例化。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class ModelUtil {

	/**
	 * 根据指定的RTCP参数模型生成一个不可编辑的RTCP参数模型。
	 * 
	 * @param model 指定的RTCP参数模型。
	 * @return 根据指定的RTCP参数模型生成的不可编辑的RTCP参数模型。
	 * @throws NullPointerException 指定的入口参数为 <code> null </code>。
	 */
	public static RTCPParamModel unmodifiableRTCPParamModel(RTCPParamModel model) throws NullPointerException {
		Objects.requireNonNull(model, "入口参数 model 不能为 null。");
		return new UnmodifiableRTCPParamModel(model);
	}

	private static final class UnmodifiableRTCPParamModel implements RTCPParamModel {

		private final RTCPParamModel delegate;

		public UnmodifiableRTCPParamModel(RTCPParamModel delegate) {
			this.delegate = delegate;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Set<RTCPParamObverser> getObversers() {
			return delegate.getObversers();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean addObverser(RTCPParamObverser obverser) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("addObverser");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean removeObverser(RTCPParamObverser obverser) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("removeObverser");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clearObverser() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("clearObverser");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV00() {
			return delegate.getV00();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV01() {
			return delegate.getV01();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV02() {
			return delegate.getV02();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV10() {
			return delegate.getV10();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV11() {
			return delegate.getV11();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV12() {
			return delegate.getV12();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV20() {
			return delegate.getV20();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV21() {
			return delegate.getV21();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV22() {
			return delegate.getV22();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV30() {
			return delegate.getV30();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV31() {
			return delegate.getV31();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV32() {
			return delegate.getV32();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getToolLength() {
			return delegate.getToolLength();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV00(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV00");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV01(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV01");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV02(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV02");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV10(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV10");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV11(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV11");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV12(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV12");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV20(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV20");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV21(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV21");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV22(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV22");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV30(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV30");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV31(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV31");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV32(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setV32");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setToolLength(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setToolLength");
		}

	}

	/**
	 * 根据指定的RTCP参数模型生成线程安全的RTCP参数模型。
	 * 
	 * @param model 指定的RTCP参数模型。
	 * @return 根据指定的RTCP参数模型生成的线程安全的RTCP参数模型。
	 * @throws NullPointerException 指定的入口参数为 <code> null </code>。
	 */
	public static SyncRTCPParamModel syncRTCPParamModel(RTCPParamModel model) throws NullPointerException {
		Objects.requireNonNull(model, "入口参数 model 不能为 null。");
		return new SyncRTCPParamModelImpl(model);
	}

	private static final class SyncRTCPParamModelImpl implements SyncRTCPParamModel {

		private final RTCPParamModel delegate;
		private final ReadWriteLock lock = new ReentrantReadWriteLock();

		public SyncRTCPParamModelImpl(RTCPParamModel delegate) {
			this.delegate = delegate;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public ReadWriteLock getLock() {
			return lock;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Set<RTCPParamObverser> getObversers() {
			lock.readLock().lock();
			try {
				return delegate.getObversers();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean addObverser(RTCPParamObverser obverser) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.addObverser(obverser);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean removeObverser(RTCPParamObverser obverser) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.removeObverser(obverser);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clearObverser() throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				delegate.clearObverser();
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV00() {
			lock.readLock().lock();
			try {
				return delegate.getV00();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV01() {
			lock.readLock().lock();
			try {
				return delegate.getV01();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV02() {
			lock.readLock().lock();
			try {
				return delegate.getV02();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV10() {
			lock.readLock().lock();
			try {
				return delegate.getV10();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV11() {
			lock.readLock().lock();
			try {
				return delegate.getV11();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV12() {
			lock.readLock().lock();
			try {
				return delegate.getV12();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV20() {
			lock.readLock().lock();
			try {
				return delegate.getV20();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV21() {
			lock.readLock().lock();
			try {
				return delegate.getV21();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV22() {
			lock.readLock().lock();
			try {
				return delegate.getV22();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV30() {
			lock.readLock().lock();
			try {
				return delegate.getV30();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV31() {
			lock.readLock().lock();
			try {
				return delegate.getV31();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getV32() {
			lock.readLock().lock();
			try {
				return delegate.getV32();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getToolLength() {
			lock.readLock().lock();
			try {
				return delegate.getToolLength();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV00(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV00(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV01(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV01(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV02(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV02(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV10(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV10(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV11(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV11(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV12(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV12(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV20(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV20(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV21(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV21(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV22(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV22(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV30(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV30(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV31(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV31(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setV32(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setV32(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setToolLength(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setToolLength(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

	}

	/**
	 * 根据指定的回转轴模型生成一个不可编辑的回转轴模型。
	 * 
	 * @param model 指定的模型。
	 * @return 根据指定的模型生成的不可编辑的回转轴模型。
	 * @throws NullPointerException 指定的入口参数为 <code> null </code>。
	 */
	public static RotateAxisModel unmodifiableRotateAxisModel(RotateAxisModel model) throws NullPointerException {
		Objects.requireNonNull(model, "入口参数 model 不能为 null。");
		return new UnmodifiableRotateAxisModel(model);
	}

	static final class UnmodifiableRotateAxisModel implements RotateAxisModel {

		private final RotateAxisModel delegate;

		public UnmodifiableRotateAxisModel(RotateAxisModel delegate) {
			this.delegate = delegate;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Set<RotateAxisObverser> getObversers() {
			return delegate.getObversers();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean addObverser(RotateAxisObverser obverser) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("addObverser");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean removeObverser(RotateAxisObverser obverser) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("removeObverser");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clearObverser() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("clearObverser");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getA() {
			return delegate.getA();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getC() {
			return delegate.getC();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setA(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setA");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setC(double value) throws UnsupportedOperationException {
			throw new UnsupportedOperationException("setC");
		}

	}

	/**
	 * 根据指定的回转轴模型生成线程安全的回转轴模型。
	 * 
	 * @param model 指定的模型。
	 * @return 通过指定的回转轴模型生成的线程安全的回转轴模型。
	 * @throws NullPointerException 指定的入口参数为 <code> null </code>。
	 */
	public static SyncRotateAxisModel syncRotateAxisModel(RotateAxisModel model) throws NullPointerException {
		Objects.requireNonNull(model, "入口参数 model 不能为 null。");
		return new SyncRotateAxisModelImpl(model);
	}

	private static final class SyncRotateAxisModelImpl implements SyncRotateAxisModel {

		private final RotateAxisModel delegate;
		private final ReadWriteLock lock = new ReentrantReadWriteLock();

		public SyncRotateAxisModelImpl(RotateAxisModel delegate) {
			this.delegate = delegate;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public ReadWriteLock getLock() {
			return lock;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Set<RotateAxisObverser> getObversers() {
			lock.readLock().lock();
			try {
				return delegate.getObversers();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean addObverser(RotateAxisObverser obverser) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.addObverser(obverser);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean removeObverser(RotateAxisObverser obverser) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.removeObverser(obverser);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clearObverser() throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				delegate.clearObverser();
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getA() {
			lock.readLock().lock();
			try {
				return delegate.getA();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getC() {
			lock.readLock().lock();
			try {
				return delegate.getC();
			} finally {
				lock.readLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setA(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setA(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean setC(double value) throws UnsupportedOperationException {
			lock.writeLock().lock();
			try {
				return delegate.setC(value);
			} finally {
				lock.writeLock().unlock();
			}
		}

	}

	private ModelUtil() {
		throw new IllegalStateException("禁止外部实例化");
	}

}
