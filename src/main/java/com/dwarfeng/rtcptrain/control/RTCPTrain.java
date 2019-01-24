package com.dwarfeng.rtcptrain.control;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dwarfeng.dutil.basic.cna.model.DefaultReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.ModelUtil;
import com.dwarfeng.dutil.basic.cna.model.SyncReferenceModel;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.backgr.Background;
import com.dwarfeng.dutil.develop.backgr.ExecutorServiceBackground;
import com.dwarfeng.dutil.develop.i18n.DelegateI18nHandler;
import com.dwarfeng.dutil.develop.i18n.I18nUtil;
import com.dwarfeng.dutil.develop.i18n.SyncI18nHandler;
import com.dwarfeng.dutil.develop.logger.DelegateLoggerHandler;
import com.dwarfeng.dutil.develop.logger.LoggerUtil;
import com.dwarfeng.dutil.develop.logger.SyncLoggerHandler;
import com.dwarfeng.dutil.develop.resource.DelegateResourceHandler;
import com.dwarfeng.dutil.develop.resource.ResourceUtil;
import com.dwarfeng.dutil.develop.resource.SyncResourceHandler;
import com.dwarfeng.dutil.develop.setting.DefaultSettingHandler;
import com.dwarfeng.dutil.develop.setting.SettingUtil;
import com.dwarfeng.dutil.develop.setting.SyncSettingHandler;
import com.dwarfeng.rtcptrain.model.DefaultRTCPParamModel;
import com.dwarfeng.rtcptrain.model.DefaultRotateAxisModel;
import com.dwarfeng.rtcptrain.model.SyncRTCPParamModel;
import com.dwarfeng.rtcptrain.model.SyncRotateAxisModel;
import com.dwarfeng.rtcptrain.model.enumeration.MeasureDirection;
import com.dwarfeng.rtcptrain.view.MainFrame;

/**
 * RTCP训练软件的主程序。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class RTCPTrain {

	/** 程序的版本。 */
	public static final Version VERSION = new DefaultVersion.Builder().setType(VersionType.ALPHA)
			.setFirstVersion((byte) 0).setSecondVersion((byte) 0).setThirdVersion((byte) 0).setBuildDate("20190103")
			.setBuildVersion('A').build();
	/** 程序的实例列表，用于持有引用 */
	private static final Set<RTCPTrain> INSTANCES = Collections.synchronizedSet(new HashSet<>());

	// --------------------------------------------管理器--------------------------------------------
	/** 模型管理器。 */
	private final ModelManager modelManager = new RTCPTrainModelManager(this);
	/** 动作管理器。 */
	private final ActionManager actionManager = new RTCPTrainActionManager(this);

	// --------------------------------------------模型--------------------------------------------
	/** 后台。 */
	private final Background background = new ExecutorServiceBackground(
			Executors.newFixedThreadPool(4, ExecutorServiceBackground.THREAD_FACTORY),
			Collections.newSetFromMap(new WeakHashMap<>()));
	/** 核心配置模型。 */
	private final SyncSettingHandler coreSettingHandler = SettingUtil.syncSettingHandler(new DefaultSettingHandler(
			new LinkedHashMap<>(), new LinkedHashMap<>(), Collections.newSetFromMap(new WeakHashMap<>())));
	/** 模态配置模型。 */
	private final SyncSettingHandler modalSettingHandler = SettingUtil.syncSettingHandler(new DefaultSettingHandler(
			new LinkedHashMap<>(), new LinkedHashMap<>(), Collections.newSetFromMap(new WeakHashMap<>())));
	/** 命令行配置模型。 */
	private final SyncSettingHandler cliSettingHandler = SettingUtil.syncSettingHandler(new DefaultSettingHandler());
	/** 记录器接口。 */
	private final SyncLoggerHandler loggerHandler = LoggerUtil.syncLoggerHandler(new DelegateLoggerHandler());
	/** 国际化处理器。 */
	private final SyncI18nHandler i18nHandler = I18nUtil.syncI18nHandler(new DelegateI18nHandler());
	/** 配置处理器。 */
	private final SyncResourceHandler resourceHandler = ResourceUtil.syncResourceHandler(new DelegateResourceHandler());

	/** 当前使用的RTCP参数。 */
	private final SyncRTCPParamModel currentRTCPParamModel = com.dwarfeng.rtcptrain.util.ModelUtil
			.syncRTCPParamModel(new DefaultRTCPParamModel());
	/** 实际的RTCP参数。 */
	private final SyncRTCPParamModel actualRTCPParamModel = com.dwarfeng.rtcptrain.util.ModelUtil
			.syncRTCPParamModel(new DefaultRTCPParamModel());
	/** 基准回转轴。 */
	private final SyncRotateAxisModel datumRotateAxisModel = com.dwarfeng.rtcptrain.util.ModelUtil
			.syncRotateAxisModel(new DefaultRotateAxisModel());
	/** 测量回转轴。 */
	private final SyncRotateAxisModel measureRotateAxisModel = com.dwarfeng.rtcptrain.util.ModelUtil
			.syncRotateAxisModel(new DefaultRotateAxisModel());
	/** 测量方向。 */
	private final SyncReferenceModel<MeasureDirection> measureDirectionModel = ModelUtil
			.syncReferenceModel(new DefaultReferenceModel<>(MeasureDirection.X));
	/** 测量误差。 */
	private final SyncReferenceModel<Double> measureErrorModel = ModelUtil
			.syncReferenceModel(new DefaultReferenceModel<>(new Double(0)));

	// --------------------------------------------控制--------------------------------------------
	/** 程序的退出代码。 */
	private final SyncReferenceModel<Integer> exitCodeRef = com.dwarfeng.dutil.basic.cna.model.ModelUtil
			.syncReferenceModel(new DefaultReferenceModel<>(Integer.MIN_VALUE));
	/** 程序的状态。 */
	private final SyncReferenceModel<RuntimeState> runtimeStateRef = com.dwarfeng.dutil.basic.cna.model.ModelUtil
			.syncReferenceModel(new DefaultReferenceModel<>(RuntimeState.NOT_START));
	/** 程序的状态锁。 */
	private final Lock runtimeStateLock = new ReentrantLock();
	/** 程序的状态条件 */
	private final Condition runtimeStateCondition = runtimeStateLock.newCondition();

	// --------------------------------------------视图--------------------------------------------
	private final SyncReferenceModel<MainFrame> mainFrameRef = ModelUtil
			.syncReferenceModel(new DefaultReferenceModel<>());

	/**
	 * 新实例。
	 */
	public RTCPTrain() {
		// 为自己保留引用。
		INSTANCES.add(this);
	}

	/**
	 * 
	 * @throws InterruptedException
	 */
	public void awaitFinish() throws InterruptedException {
		runtimeStateLock.lock();
		try {
			while (runtimeStateRef.get() != RuntimeState.ENDED) {
				runtimeStateCondition.await();
			}
		} finally {
			runtimeStateLock.unlock();
		}
	}

	/**
	 * 
	 * @param timeout
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	public boolean awaitFinish(long timeout, TimeUnit unit) throws InterruptedException {
		runtimeStateLock.lock();
		try {
			long nanosTimeout = unit.toNanos(timeout);
			while (runtimeStateRef.get() != RuntimeState.ENDED) {
				if (nanosTimeout > 0)
					nanosTimeout = runtimeStateCondition.awaitNanos(nanosTimeout);
				else
					return false;
			}
			return true;
		} finally {
			runtimeStateLock.unlock();
		}
	}

	/**
	 * @return the actionManager
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}

	/**
	 * 
	 * @return
	 */
	public int getExitCode() {
		return exitCodeRef.get();
	}

	/**
	 * @return the modelManager
	 */
	public ModelManager getModelManager() {
		return modelManager;
	}

	/**
	 * @return the actualRTCPParamModel
	 */
	SyncRTCPParamModel getActualRTCPParamModel() {
		return actualRTCPParamModel;
	}

	/**
	 * @return the background
	 */
	Background getBackground() {
		return background;
	}

	/**
	 * @return the cliSettingHandler
	 */
	SyncSettingHandler getCliSettingHandler() {
		return cliSettingHandler;
	}

	/**
	 * @return the coreSettingHandler
	 */
	SyncSettingHandler getCoreSettingHandler() {
		return coreSettingHandler;
	}

	/**
	 * @return the currentRTCPParamModel
	 */
	SyncRTCPParamModel getCurrentRTCPParamModel() {
		return currentRTCPParamModel;
	}

	/**
	 * @return the datumRotateAxisModel
	 */
	SyncRotateAxisModel getDatumRotateAxisModel() {
		return datumRotateAxisModel;
	}

	/**
	 * @return the exitCodeRef
	 */
	SyncReferenceModel<Integer> getExitCodeRef() {
		return exitCodeRef;
	}

	/**
	 * @return the i18nHandler
	 */
	SyncI18nHandler getI18nHandler() {
		return i18nHandler;
	}

	/**
	 * @return the loggerHandler
	 */
	SyncLoggerHandler getLoggerHandler() {
		return loggerHandler;
	}

	/**
	 * @return the mainFrameRef
	 */
	SyncReferenceModel<MainFrame> getMainFrameRef() {
		return mainFrameRef;
	}

	/**
	 * @return the measureDirectionModel
	 */
	SyncReferenceModel<MeasureDirection> getMeasureDirectionModel() {
		return measureDirectionModel;
	}

	/**
	 * @return the measureErrorModel
	 */
	SyncReferenceModel<Double> getMeasureErrorModel() {
		return measureErrorModel;
	}

	/**
	 * @return the measureRotateAxisModel
	 */
	SyncRotateAxisModel getMeasureRotateAxisModel() {
		return measureRotateAxisModel;
	}

	/**
	 * @return the modalSettingHandler
	 */
	SyncSettingHandler getModalSettingHandler() {
		return modalSettingHandler;
	}

	/**
	 * @return the resourceHandler
	 */
	SyncResourceHandler getResourceHandler() {
		return resourceHandler;
	}

	/**
	 * @return the runtimeStateCondition
	 */
	Condition getRuntimeStateCondition() {
		return runtimeStateCondition;
	}

	/**
	 * @return the runtimeStateLock
	 */
	Lock getRuntimeStateLock() {
		return runtimeStateLock;
	}

	/**
	 * @return the runtimeStateRef
	 */
	SyncReferenceModel<RuntimeState> getRuntimeStateRef() {
		return runtimeStateRef;
	}

}
