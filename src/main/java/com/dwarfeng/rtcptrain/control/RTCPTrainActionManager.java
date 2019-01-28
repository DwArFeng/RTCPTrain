package com.dwarfeng.rtcptrain.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.random.RandomDataImpl;

import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.SyncReferenceModel;
import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.num.unit.Angle;
import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.develop.backgr.Background;
import com.dwarfeng.dutil.develop.backgr.Task;
import com.dwarfeng.dutil.develop.i18n.PropUrlI18nInfo;
import com.dwarfeng.dutil.develop.i18n.SyncI18nHandler;
import com.dwarfeng.dutil.develop.i18n.io.XmlPropFileI18nLoader;
import com.dwarfeng.dutil.develop.logger.SyncLoggerHandler;
import com.dwarfeng.dutil.develop.logger.SysOutLoggerInfo;
import com.dwarfeng.dutil.develop.logger.io.Log4jLoggerLoader;
import com.dwarfeng.dutil.develop.resource.Resource;
import com.dwarfeng.dutil.develop.resource.SyncResourceHandler;
import com.dwarfeng.dutil.develop.resource.io.ResourceResetPolicy;
import com.dwarfeng.dutil.develop.resource.io.XmlJar2FileResourceLoader;
import com.dwarfeng.dutil.develop.setting.SettingUtil;
import com.dwarfeng.dutil.develop.setting.SyncSettingHandler;
import com.dwarfeng.dutil.develop.setting.io.PropSettingValueLoader;
import com.dwarfeng.dutil.develop.setting.io.PropSettingValueSaver;
import com.dwarfeng.rtcptrain.model.SyncRTCPParamModel;
import com.dwarfeng.rtcptrain.model.SyncRotateAxisModel;
import com.dwarfeng.rtcptrain.model.enumeration.CliSettingItem;
import com.dwarfeng.rtcptrain.model.enumeration.CoreSettingItem;
import com.dwarfeng.rtcptrain.model.enumeration.I18nKey;
import com.dwarfeng.rtcptrain.model.enumeration.MeasureDirection;
import com.dwarfeng.rtcptrain.model.enumeration.ModalSettingItem;
import com.dwarfeng.rtcptrain.model.enumeration.ResourceKey;
import com.dwarfeng.rtcptrain.util.Constants;
import com.dwarfeng.rtcptrain.util.TransformUtil;
import com.dwarfeng.rtcptrain.view.MainFrame;

class RTCPTrainActionManager implements ActionManager {

	private final RTCPTrain rtcpTrain;

	public RTCPTrainActionManager(RTCPTrain rtcpTrain) {
		this.rtcpTrain = rtcpTrain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(String[] args) throws IllegalStateException, NullPointerException {
		Objects.requireNonNull(args, "入口参数 args 不能为 null。");

		// 要求程序的运行状态为未启动。
		requireRuntimeState(RuntimeState.NOT_START);
		try {
			// 在置被读取之前，首先使用内置的功能模块。
			applyBuiltinFunctionBeforeApplyConfig();
			// 通知应用程序正在启动。
			info(I18nKey.LOGGER_1);
			// 解析命令行参数。
			parseCliOption(args);
			// 应用程序配置。
			applyConfig();
			// 启动GUI。
			runGUI();
			// 设置程序的运行状态为正在运行。
			setRuntimeState(RuntimeState.RUNNING);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO 紧急退出机制。
		}
	}

	/**
	 * 在配置被读取之前，首先使用内置的功能模块。
	 */
	private void applyBuiltinFunctionBeforeApplyConfig() {
		SyncLoggerHandler loggerHandler = rtcpTrain.getLoggerHandler();
		SyncI18nHandler i18nHandler = rtcpTrain.getI18nHandler();

		SysOutLoggerInfo defaultLoggerInfo = new SysOutLoggerInfo(null, false);
		PropUrlI18nInfo defaultI18nInfo = new PropUrlI18nInfo(null, "初始化用国际化配置",
				RTCPTrain.class.getResource(Constants.JPATH_DEFAULT_I18N_PROP_FILE));

		loggerHandler.getLock().writeLock().lock();
		try {
			loggerHandler.clear();
			loggerHandler.add(defaultLoggerInfo);
			loggerHandler.use(defaultLoggerInfo);
		} finally {
			loggerHandler.getLock().writeLock().unlock();
		}

		i18nHandler.getLock().writeLock().lock();
		try {
			i18nHandler.clear();
			i18nHandler.add(defaultI18nInfo);
			i18nHandler.setCurrentLocale(null);
		} finally {
			i18nHandler.getLock().writeLock().unlock();
		}
	}

	private void parseCliOption(String[] args) throws NullPointerException {
		SyncSettingHandler cliSettingHandler = rtcpTrain.getCliSettingHandler();

		info(I18nKey.LOGGER_2);

		// 生成程序的命令行选项。
		Options options = new Options();
		options.addOption(Option.builder(Constants.CLI_OPT_FLAG_CONFIG_FORCE_RESET).hasArg(false).build());

		cliSettingHandler.getLock().writeLock().lock();
		try {
			cliSettingHandler.clear();
			SettingUtil.putEnumItems(CliSettingItem.class, cliSettingHandler);
			// 解析命令行。
			CommandLine commandLine = new DefaultParser().parse(options, args);
			// 判断是否需要强制复位配置文件。
			if (commandLine.hasOption(Constants.CLI_OPT_FLAG_CONFIG_FORCE_RESET)) {
				cliSettingHandler.setParsedValue(CliSettingItem.FLAG_CONFIG_FORCE_RESET, true);
			} else {
				cliSettingHandler.setParsedValue(CliSettingItem.FLAG_CONFIG_FORCE_RESET, false);
			}
		} catch (ParseException e) {
			warn(I18nKey.LOGGER_3, e);
		} finally {
			cliSettingHandler.getLock().writeLock().unlock();
		}
	}

	private void applyConfig() throws Exception {
		// 定义命令行变量。
		SyncSettingHandler cliSettingHandler = rtcpTrain.getCliSettingHandler();
		final Boolean flag_forceReset = cliSettingHandler.getParsedValidValue(CliSettingItem.FLAG_CONFIG_FORCE_RESET,
				Boolean.class);

		// 加载配置信息。
		loadResource(flag_forceReset);
		// 加载记录器配置。
		loadLoggerHandler();
		// 加器国际化配置。
		loadI18nHandler();
		// 加载核心配置。
		loadCoreSettingHandler();
		// 应用核心配置
		applyCoreSetting();
		// 加载模态配置。
		loadModalSettingHandler();
		// 应用模态配置
		applyModalSetting();
	}

	private InputStream openResourceInputStream(ResourceKey resourceKey) throws IOException {
		Resource resource = rtcpTrain.getResourceHandler().get(resourceKey.getName());
		try {
			return resource.openInputStream();
		} catch (IOException e) {
			formatWarn(I18nKey.LOGGER_10, e, resourceKey.getName());
			resource.reset();
			return resource.openInputStream();
		}
	}

	private void loadResource(Boolean flag_forceReset) {
		SyncResourceHandler resourceHandler = rtcpTrain.getResourceHandler();

		info(I18nKey.LOGGER_4);
		resourceHandler.getLock().writeLock().lock();
		try {
			resourceHandler.clear();
			// 如果在此处出现异常，则程序无法加载最基本的配置列表，但程序仍然可以继续运行。
			InputStream in;
			try {
				in = RTCPTrain.class.getResource(Constants.JPATH_RESOURCE_LIST).openStream();
			} catch (IOException e) {
				formatError(I18nKey.LOGGER_5, e);
				return;
			}
			Set<LoadFailedException> eptSet = new LinkedHashSet<>();
			try (XmlJar2FileResourceLoader loader = new XmlJar2FileResourceLoader(in,
					flag_forceReset ? ResourceResetPolicy.ALWAYS : ResourceResetPolicy.AUTO)) {
				eptSet.addAll(loader.countinuousLoad(resourceHandler));
			} catch (IOException e) {
				formatWarn(I18nKey.LOGGER_6, e, in.toString());
			}
			for (LoadFailedException e : eptSet) {
				warn(I18nKey.LOGGER_7, e);
			}
		} finally {
			resourceHandler.getLock().writeLock().unlock();
		}
	}

	private void loadLoggerHandler() {
		SyncLoggerHandler loggerHandler = rtcpTrain.getLoggerHandler();

		info(I18nKey.LOGGER_8);
		loggerHandler.getLock().writeLock().lock();
		try {
			loggerHandler.clear();
			InputStream in;
			try {
				in = openResourceInputStream(ResourceKey.LOGGER_SETTING);
			} catch (IOException e) {
				error(I18nKey.LOGGER_9, e);
				return;
			}
			Set<LoadFailedException> eptSet = new LinkedHashSet<>();
			try (Log4jLoggerLoader loader = new Log4jLoggerLoader(in)) {
				eptSet.addAll(loader.countinuousLoad(loggerHandler));
			} catch (IOException e) {
				formatWarn(I18nKey.LOGGER_6, e, in.toString());
			}
			for (LoadFailedException e : eptSet) {
				warn(I18nKey.LOGGER_11, e);
			}
			loggerHandler.useAll();
		} finally {
			loggerHandler.getLock().writeLock().unlock();
		}
	}

	private void loadI18nHandler() {
		SyncI18nHandler i18nHandler = rtcpTrain.getI18nHandler();

		info(I18nKey.LOGGER_12);
		i18nHandler.getLock().writeLock().lock();
		try {
			i18nHandler.clear();
			InputStream in;
			try {
				in = openResourceInputStream(ResourceKey.I18N_SETTING);
			} catch (IOException e) {
				error(I18nKey.LOGGER_13, e);
				return;
			}
			Set<LoadFailedException> eptSet = new LinkedHashSet<>();
			try (XmlPropFileI18nLoader loader = new XmlPropFileI18nLoader(in)) {
				eptSet.addAll(loader.countinuousLoad(i18nHandler));
			} catch (IOException e) {
				formatWarn(I18nKey.LOGGER_6, e, in.toString());
			}
			for (LoadFailedException e : eptSet) {
				warn(I18nKey.LOGGER_14, e);
			}
			i18nHandler.setCurrentLocale(null);
		} finally {
			i18nHandler.getLock().writeLock().unlock();
		}
	}

	private void loadCoreSettingHandler() {
		SyncSettingHandler coreSettingHandler = rtcpTrain.getCoreSettingHandler();

		info(I18nKey.LOGGER_15);
		coreSettingHandler.getLock().writeLock().lock();
		try {
			coreSettingHandler.clear();
			SettingUtil.putEnumItems(CoreSettingItem.class, coreSettingHandler);
			InputStream in;
			try {
				in = openResourceInputStream(ResourceKey.CONFIG);
			} catch (IOException e) {
				error(I18nKey.LOGGER_16, e);
				return;
			}
			Set<LoadFailedException> eptSet = new LinkedHashSet<>();
			try (PropSettingValueLoader loader = new PropSettingValueLoader(in, true)) {
				eptSet.addAll(loader.countinuousLoad(coreSettingHandler));
			} catch (IOException e) {
				formatWarn(I18nKey.LOGGER_6, e, in.toString());
			}
			for (LoadFailedException e : eptSet) {
				warn(I18nKey.LOGGER_17, e);
			}
		} finally {
			coreSettingHandler.getLock().writeLock().unlock();
		}
	}

	private void applyCoreSetting() {
		SyncSettingHandler coreSettingHandler = rtcpTrain.getCoreSettingHandler();
		SyncI18nHandler i18nHandler = rtcpTrain.getI18nHandler();

		Locale i18nLocale;

		coreSettingHandler.getLock().readLock().lock();
		try {
			i18nLocale = coreSettingHandler.getParsedValidValue(CoreSettingItem.I18N_LOCALE, Locale.class);
		} finally {
			coreSettingHandler.getLock().readLock().unlock();
		}

		i18nHandler.getLock().writeLock().lock();
		try {
			i18nHandler.setCurrentLocale(i18nLocale);
		} finally {
			i18nHandler.getLock().writeLock().unlock();
		}
	}

	private void loadModalSettingHandler() {
		SyncSettingHandler modalSettingHandler = rtcpTrain.getModalSettingHandler();

		info(I18nKey.LOGGER_18);
		modalSettingHandler.getLock().writeLock().lock();
		try {
			modalSettingHandler.clear();
			SettingUtil.putEnumItems(ModalSettingItem.class, modalSettingHandler);
			InputStream in;
			try {
				in = openResourceInputStream(ResourceKey.MODAL);
			} catch (IOException e) {
				error(I18nKey.LOGGER_19, e);
				return;
			}
			Set<LoadFailedException> eptSet = new LinkedHashSet<>();
			try (PropSettingValueLoader loader = new PropSettingValueLoader(in, true)) {
				eptSet.addAll(loader.countinuousLoad(modalSettingHandler));
			} catch (IOException e) {
				formatWarn(I18nKey.LOGGER_6, e, in.toString());
			}
			for (LoadFailedException e : eptSet) {
				warn(I18nKey.LOGGER_20, e);
			}
		} finally {
			modalSettingHandler.getLock().writeLock().unlock();
		}
	}

	private void applyModalSetting() {
		info(I18nKey.LOGGER_27);

		SyncSettingHandler modalSettingHandler = rtcpTrain.getModalSettingHandler();
		SyncRTCPParamModel currentRTCPParamModel = rtcpTrain.getCurrentRTCPParamModel();
		SyncRTCPParamModel actualRTCPParamModel = rtcpTrain.getActualRTCPParamModel();
		SyncRotateAxisModel datumRotateAxisModel = rtcpTrain.getDatumRotateAxisModel();
		SyncRotateAxisModel measureRotateAxisModel = rtcpTrain.getMeasureRotateAxisModel();
		SyncReferenceModel<MeasureDirection> measureDirectionModel = rtcpTrain.getMeasureDirectionModel();

		double currentV00, currentV01, currentV02;
		double currentV10, currentV11, currentV12;
		double currentV20, currentV21, currentV22;
		double currentV30, currentV31, currentV32;
		double currentToolLength;

		double actualV00, actualV01, actualV02;
		double actualV10, actualV11, actualV12;
		double actualV20, actualV21, actualV22;
		double actualV30, actualV31, actualV32;
		double actualToolLength;

		double datumRotateAxisA, datumRotateAxisC;
		double measureRotateAxisA, measureRotateAxisC;

		String measureDirection;

		modalSettingHandler.getLock().readLock().lock();
		try {
			currentV00 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V00,
					Double.class);
			currentV01 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V01,
					Double.class);
			currentV02 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V02,
					Double.class);
			currentV10 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V10,
					Double.class);
			currentV11 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V11,
					Double.class);
			currentV12 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V12,
					Double.class);
			currentV20 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V20,
					Double.class);
			currentV21 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V21,
					Double.class);
			currentV22 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V22,
					Double.class);
			currentV30 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V30,
					Double.class);
			currentV31 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V31,
					Double.class);
			currentV32 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V32,
					Double.class);
			currentToolLength = modalSettingHandler
					.getParsedValidValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_TOOL_LENGTH, Double.class);
			actualV00 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V00,
					Double.class);
			actualV01 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V01,
					Double.class);
			actualV02 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V02,
					Double.class);
			actualV10 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V10,
					Double.class);
			actualV11 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V11,
					Double.class);
			actualV12 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V12,
					Double.class);
			actualV20 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V20,
					Double.class);
			actualV21 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V21,
					Double.class);
			actualV22 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V22,
					Double.class);
			actualV30 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V30,
					Double.class);
			actualV31 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V31,
					Double.class);
			actualV32 = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V32,
					Double.class);
			actualToolLength = modalSettingHandler
					.getParsedValidValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_TOOL_LENGTH, Double.class);
			datumRotateAxisA = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_DATUM_ROTATE_AXIS_A,
					Double.class);
			datumRotateAxisC = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_DATUM_ROTATE_AXIS_C,
					Double.class);
			measureRotateAxisA = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_MEASURE_ROTATE_AXIS_A,
					Double.class);
			measureRotateAxisC = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_MEASURE_ROTATE_AXIS_C,
					Double.class);
			measureDirection = modalSettingHandler.getParsedValidValue(ModalSettingItem.VALUE_MEASURE_DIRECTION,
					String.class);
		} finally {
			modalSettingHandler.getLock().readLock().unlock();
		}

		currentRTCPParamModel.getLock().writeLock().lock();
		try {
			currentRTCPParamModel.setV00(currentV00);
			currentRTCPParamModel.setV01(currentV01);
			currentRTCPParamModel.setV02(currentV02);
			currentRTCPParamModel.setV10(currentV10);
			currentRTCPParamModel.setV11(currentV11);
			currentRTCPParamModel.setV12(currentV12);
			currentRTCPParamModel.setV20(currentV20);
			currentRTCPParamModel.setV21(currentV21);
			currentRTCPParamModel.setV22(currentV22);
			currentRTCPParamModel.setV30(currentV30);
			currentRTCPParamModel.setV31(currentV31);
			currentRTCPParamModel.setV32(currentV32);
			currentRTCPParamModel.setToolLength(currentToolLength);
		} finally {
			currentRTCPParamModel.getLock().writeLock().unlock();
		}

		actualRTCPParamModel.getLock().writeLock().lock();
		try {
			actualRTCPParamModel.setV00(actualV00);
			actualRTCPParamModel.setV01(actualV01);
			actualRTCPParamModel.setV02(actualV02);
			actualRTCPParamModel.setV10(actualV10);
			actualRTCPParamModel.setV11(actualV11);
			actualRTCPParamModel.setV12(actualV12);
			actualRTCPParamModel.setV20(actualV20);
			actualRTCPParamModel.setV21(actualV21);
			actualRTCPParamModel.setV22(actualV22);
			actualRTCPParamModel.setV30(actualV30);
			actualRTCPParamModel.setV31(actualV31);
			actualRTCPParamModel.setV32(actualV32);
			actualRTCPParamModel.setToolLength(actualToolLength);
		} finally {
			actualRTCPParamModel.getLock().writeLock().unlock();
		}

		datumRotateAxisModel.getLock().writeLock().lock();
		try {
			datumRotateAxisModel.setA(datumRotateAxisA);
			datumRotateAxisModel.setC(datumRotateAxisC);
		} finally {
			datumRotateAxisModel.getLock().writeLock().unlock();
		}

		measureRotateAxisModel.getLock().writeLock().lock();
		try {
			measureRotateAxisModel.setA(measureRotateAxisA);
			measureRotateAxisModel.setC(measureRotateAxisC);
		} finally {
			measureRotateAxisModel.getLock().writeLock().unlock();
		}

		measureDirectionModel.getLock().writeLock().lock();
		try {
			try {
				measureDirectionModel.set(MeasureDirection.valueOf(measureDirection));
			} catch (Exception e) {
				measureDirectionModel.set(MeasureDirection.X);
			}
		} finally {
			measureDirectionModel.getLock().writeLock().unlock();
		}

	}

	private void runGUI() {
		info(I18nKey.LOGGER_21);

		SyncReferenceModel<MainFrame> mainFrameRef = rtcpTrain.getMainFrameRef();

		SwingUtil.invokeInEventQueue(() -> {
			try {
				UIManager.setLookAndFeel(new NimbusLookAndFeel());
			} catch (UnsupportedLookAndFeelException ignore) {
			}

			MainFrame mainFrame = new MainFrame(rtcpTrain.getModelManager(), rtcpTrain.getActionManager());
			mainFrame.setVisible(true);
			mainFrameRef.set(mainFrame);
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exit() throws IllegalStateException {
		// 检查并设置程序的运行状态未正在运行。
		requireRuntimeState(RuntimeState.RUNNING);
		try {
			// 通知应用程序正在退出。
			info(I18nKey.LOGGER_26);
			// 保存程序配置。
			saveConfig();
			// 关闭GUI。
			disposeGUI();
			// 停止后台。
			stopBackground();
			// 设置退出代码。
			setExitCode(0);
			// 将程序的状态设置为已经结束。
			setRuntimeState(RuntimeState.ENDED);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO 紧急退出机制。
		}
	}

	private void saveConfig() {
		// 设置模态配置
		setModalSettingHandler();
		// 保存模态配置。
		saveModalSettingHandler();
	}

	private OutputStream openResourceOutputStream(ResourceKey resourceKey) throws IOException {
		Resource resource = rtcpTrain.getResourceHandler().get(resourceKey.getName());
		try {
			return resource.openOutputStream();
		} catch (IOException e) {
			formatWarn(I18nKey.LOGGER_22, e, resourceKey.getName());
			resource.reset();
			return resource.openOutputStream();
		}
	}

	private void setModalSettingHandler() {
		info(I18nKey.LOGGER_28);

		SyncSettingHandler modalSettingHandler = rtcpTrain.getModalSettingHandler();
		SyncRTCPParamModel currentRTCPParamModel = rtcpTrain.getCurrentRTCPParamModel();
		SyncRTCPParamModel actualRTCPParamModel = rtcpTrain.getActualRTCPParamModel();
		SyncRotateAxisModel datumRotateAxisModel = rtcpTrain.getDatumRotateAxisModel();
		SyncRotateAxisModel measureRotateAxisModel = rtcpTrain.getMeasureRotateAxisModel();
		SyncReferenceModel<MeasureDirection> measureDirectionModel = rtcpTrain.getMeasureDirectionModel();

		double currentV00, currentV01, currentV02;
		double currentV10, currentV11, currentV12;
		double currentV20, currentV21, currentV22;
		double currentV30, currentV31, currentV32;
		double currentToolLength;

		double actualV00, actualV01, actualV02;
		double actualV10, actualV11, actualV12;
		double actualV20, actualV21, actualV22;
		double actualV30, actualV31, actualV32;
		double actualToolLength;

		double datumRotateAxisA, datumRotateAxisC;
		double measureRotateAxisA, measureRotateAxisC;

		String measureDirection;

		currentRTCPParamModel.getLock().readLock().lock();
		try {
			currentV00 = currentRTCPParamModel.getV00();
			currentV01 = currentRTCPParamModel.getV01();
			currentV02 = currentRTCPParamModel.getV02();
			currentV10 = currentRTCPParamModel.getV10();
			currentV11 = currentRTCPParamModel.getV11();
			currentV12 = currentRTCPParamModel.getV12();
			currentV20 = currentRTCPParamModel.getV20();
			currentV21 = currentRTCPParamModel.getV21();
			currentV22 = currentRTCPParamModel.getV22();
			currentV30 = currentRTCPParamModel.getV30();
			currentV31 = currentRTCPParamModel.getV31();
			currentV32 = currentRTCPParamModel.getV32();
			currentToolLength = currentRTCPParamModel.getToolLength();
		} finally {
			currentRTCPParamModel.getLock().readLock().unlock();
		}

		actualRTCPParamModel.getLock().readLock().lock();
		try {
			actualV00 = actualRTCPParamModel.getV00();
			actualV01 = actualRTCPParamModel.getV01();
			actualV02 = actualRTCPParamModel.getV02();
			actualV10 = actualRTCPParamModel.getV10();
			actualV11 = actualRTCPParamModel.getV11();
			actualV12 = actualRTCPParamModel.getV12();
			actualV20 = actualRTCPParamModel.getV20();
			actualV21 = actualRTCPParamModel.getV21();
			actualV22 = actualRTCPParamModel.getV22();
			actualV30 = actualRTCPParamModel.getV30();
			actualV31 = actualRTCPParamModel.getV31();
			actualV32 = actualRTCPParamModel.getV32();
			actualToolLength = actualRTCPParamModel.getToolLength();
		} finally {
			actualRTCPParamModel.getLock().readLock().unlock();
		}

		datumRotateAxisModel.getLock().readLock().lock();
		try {
			datumRotateAxisA = datumRotateAxisModel.getA();
			datumRotateAxisC = datumRotateAxisModel.getC();
		} finally {
			datumRotateAxisModel.getLock().readLock().unlock();
		}

		measureRotateAxisModel.getLock().readLock().lock();
		try {
			measureRotateAxisA = measureRotateAxisModel.getA();
			measureRotateAxisC = measureRotateAxisModel.getC();
		} finally {
			measureRotateAxisModel.getLock().readLock().unlock();
		}

		measureDirectionModel.getLock().readLock().lock();
		try {
			measureDirection = measureDirectionModel.get().name();
		} finally {
			measureDirectionModel.getLock().readLock().unlock();
		}

		modalSettingHandler.getLock().writeLock().lock();
		try {
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V00, currentV00);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V01, currentV01);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V02, currentV02);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V10, currentV10);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V11, currentV11);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V12, currentV12);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V20, currentV20);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V21, currentV21);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V22, currentV22);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V30, currentV30);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V31, currentV31);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_V32, currentV32);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_CURRENT_RTCP_PARAM_TOOL_LENGTH,
					currentToolLength);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V00, actualV00);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V01, actualV01);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V02, actualV02);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V10, actualV10);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V11, actualV11);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V12, actualV12);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V20, actualV20);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V21, actualV21);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V22, actualV22);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V30, actualV30);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V31, actualV31);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_V32, actualV32);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_ACTUAL_RTCP_PARAM_TOOL_LENGTH, actualToolLength);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_DATUM_ROTATE_AXIS_A, datumRotateAxisA);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_DATUM_ROTATE_AXIS_C, datumRotateAxisC);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_MEASURE_ROTATE_AXIS_A, measureRotateAxisA);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_MEASURE_ROTATE_AXIS_C, measureRotateAxisC);
			modalSettingHandler.setParsedValue(ModalSettingItem.VALUE_MEASURE_DIRECTION, measureDirection);
		} finally {
			modalSettingHandler.getLock().writeLock().unlock();
		}
	}

	private void saveModalSettingHandler() {
		SyncSettingHandler modalSettingHandler = rtcpTrain.getModalSettingHandler();

		info(I18nKey.LOGGER_23);
		modalSettingHandler.getLock().readLock().lock();
		try {
			OutputStream out;
			try {
				out = openResourceOutputStream(ResourceKey.MODAL);
			} catch (IOException e) {
				error(I18nKey.LOGGER_24, e);
				return;
			}
			Set<SaveFailedException> eptSet = new LinkedHashSet<>();
			try (PropSettingValueSaver saver = new PropSettingValueSaver(out, true)) {
				eptSet.addAll(saver.countinuousSave(modalSettingHandler));
			} catch (IOException e) {
				formatWarn(I18nKey.LOGGER_6, e, out.toString());
			}
			for (SaveFailedException e : eptSet) {
				warn(I18nKey.LOGGER_25, e);
			}
		} finally {
			modalSettingHandler.getLock().readLock().unlock();
		}
	}

	private void disposeGUI() {
		SyncReferenceModel<MainFrame> mainFrameRef = rtcpTrain.getMainFrameRef();

		SwingUtil.invokeInEventQueue(() -> {
			mainFrameRef.get().dispose();
		});
	}

	private void stopBackground() {
		Background background = rtcpTrain.getBackground();

		background.shutdown();
	}

	private void setExitCode(int code) {
		SyncReferenceModel<Integer> exitCodeRef = rtcpTrain.getExitCodeRef();
		exitCodeRef.set(code);
	}

	private void requireRuntimeState(RuntimeState state) {
		ReferenceModel<RuntimeState> runtimeStateRef = rtcpTrain.getRuntimeStateRef();
		RuntimeState currentState = runtimeStateRef.get();

		if (currentState != state) {
			throw new IllegalStateException(String.format("非法的运行状态 %s，应该为 %s", currentState, state));
		}
	}

	private void setRuntimeState(RuntimeState state) {
		ReferenceModel<RuntimeState> runtimeStateRef = rtcpTrain.getRuntimeStateRef();
		Lock runtimeStateLock = rtcpTrain.getRuntimeStateLock();
		Condition runtimeStateCondition = rtcpTrain.getRuntimeStateCondition();

		runtimeStateLock.lock();
		try {
			runtimeStateRef.set(state);
			runtimeStateCondition.signalAll();
		} finally {
			runtimeStateLock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void submit(Task task) throws NullPointerException {
		Objects.requireNonNull(task, "入口参数 task 不能为 null。");
		rtcpTrain.getBackground().submit(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV00(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV00(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV01(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV01(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV02(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV02(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV10(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV10(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV11(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV11(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV12(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV12(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV20(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV20(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV21(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV21(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV22(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV22(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV30(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV30(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV31(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV31(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamV32(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setV32(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentRTCPParamToolLength(double newValue) {
		rtcpTrain.getCurrentRTCPParamModel().setToolLength(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV00(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV00(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV01(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV01(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV02(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV02(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV10(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV10(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV11(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV11(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV12(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV12(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV20(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV20(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV21(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV21(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV22(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV22(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV30(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV30(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV31(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV31(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamV32(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setV32(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActualRTCPParamToolLength(double newValue) {
		rtcpTrain.getActualRTCPParamModel().setToolLength(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDatumRotateAxisA(double newValue) {
		rtcpTrain.getDatumRotateAxisModel().setA(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDatumRotateAxisC(double newValue) {
		rtcpTrain.getDatumRotateAxisModel().setC(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMeasureRotateAxisA(double newValue) {
		rtcpTrain.getMeasureRotateAxisModel().setA(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMeasureRotateAxisC(double newValue) {
		rtcpTrain.getMeasureRotateAxisModel().setC(newValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMeasureDirection(MeasureDirection direction) {
		Objects.requireNonNull(direction, "入口参数 direction 不能为 null。");
		rtcpTrain.getMeasureDirectionModel().set(direction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void randomActualRTCPParam() {
		info(I18nKey.LOGGER_31);

		SyncSettingHandler coreSettingHandler = rtcpTrain.getCoreSettingHandler();
		SyncRTCPParamModel actualRTCPParamModel = rtcpTrain.getActualRTCPParamModel();

		boolean v00Flag, v01Flag, v02Flag;
		boolean v10Flag, v11Flag, v12Flag;
		boolean v20Flag, v21Flag, v22Flag;
		boolean v30Flag, v31Flag, v32Flag;
		boolean toolLengthFlag;

		double v00, v00Mean, v00Sigma;
		double v01, v01Mean, v01Sigma;
		double v02, v02Mean, v02Sigma;
		double v10, v10Mean, v10Sigma;
		double v11, v11Mean, v11Sigma;
		double v12, v12Mean, v12Sigma;
		double v20, v20Mean, v20Sigma;
		double v21, v21Mean, v21Sigma;
		double v22, v22Mean, v22Sigma;
		double v30, v30Mean, v30Sigma;
		double v31, v31Mean, v31Sigma;
		double v32, v32Mean, v32Sigma;
		double toolLength, toolLengthMean, toolLengthSigma;

		coreSettingHandler.getLock().readLock().lock();
		try {
			v00Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V00, Boolean.class);
			v01Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V01, Boolean.class);
			v02Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V02, Boolean.class);
			v10Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V10, Boolean.class);
			v11Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V11, Boolean.class);
			v12Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V12, Boolean.class);
			v20Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V20, Boolean.class);
			v21Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V21, Boolean.class);
			v22Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V22, Boolean.class);
			v30Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V30, Boolean.class);
			v31Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V31, Boolean.class);
			v32Flag = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_V32, Boolean.class);
			toolLengthFlag = coreSettingHandler.getParsedValidValue(
					CoreSettingItem.POLICY_NORMAL_DISTRIBUTION_ENABLED_ACTUAL_TOOL_LENGTH, Boolean.class);

			v00Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V00,
					Double.class);
			v01Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V01,
					Double.class);
			v02Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V02,
					Double.class);
			v10Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V10,
					Double.class);
			v11Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V11,
					Double.class);
			v12Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V12,
					Double.class);
			v20Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V20,
					Double.class);
			v21Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V21,
					Double.class);
			v22Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V22,
					Double.class);
			v30Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V30,
					Double.class);
			v31Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V31,
					Double.class);
			v32Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V32,
					Double.class);
			toolLengthMean = coreSettingHandler.getParsedValidValue(
					CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_TOOL_LENGTH, Double.class);
			v00Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V00, Double.class);
			v01Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V01, Double.class);
			v02Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V02, Double.class);
			v10Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V10, Double.class);
			v11Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V11, Double.class);
			v12Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V12, Double.class);
			v20Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V20, Double.class);
			v21Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V21, Double.class);
			v22Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V22, Double.class);
			v30Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V30, Double.class);
			v31Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V31, Double.class);
			v32Sigma = coreSettingHandler
					.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_V32, Double.class);
			toolLengthSigma = coreSettingHandler.getParsedValidValue(
					CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_SIGMA_ACTUAL_TOOL_LENGTH, Double.class);
		} finally {
			coreSettingHandler.getLock().readLock().unlock();
		}

		try {
			v00 = getRandomRTCPParam(v00Mean, v00Sigma, v00Flag);
			v01 = getRandomRTCPParam(v01Mean, v01Sigma, v01Flag);
			v02 = getRandomRTCPParam(v02Mean, v02Sigma, v02Flag);
			v10 = getRandomRTCPParam(v10Mean, v10Sigma, v10Flag);
			v11 = getRandomRTCPParam(v11Mean, v11Sigma, v11Flag);
			v12 = getRandomRTCPParam(v12Mean, v12Sigma, v12Flag);
			v20 = getRandomRTCPParam(v20Mean, v20Sigma, v20Flag);
			v21 = getRandomRTCPParam(v21Mean, v21Sigma, v21Flag);
			v22 = getRandomRTCPParam(v22Mean, v22Sigma, v22Flag);
			v30 = getRandomRTCPParam(v30Mean, v30Sigma, v30Flag);
			v31 = getRandomRTCPParam(v31Mean, v31Sigma, v31Flag);
			v32 = getRandomRTCPParam(v32Mean, v32Sigma, v32Flag);
			toolLength = getRandomRTCPParam(toolLengthMean, toolLengthSigma, toolLengthFlag);
		} catch (Exception e) {
			warn(I18nKey.LOGGER_32, e);
			return;
		}

		actualRTCPParamModel.getLock().writeLock().lock();
		try {
			actualRTCPParamModel.setV00(v00);
			actualRTCPParamModel.setV01(v01);
			actualRTCPParamModel.setV02(v02);
			actualRTCPParamModel.setV10(v10);
			actualRTCPParamModel.setV11(v11);
			actualRTCPParamModel.setV12(v12);
			actualRTCPParamModel.setV20(v20);
			actualRTCPParamModel.setV21(v21);
			actualRTCPParamModel.setV22(v22);
			actualRTCPParamModel.setV30(v30);
			actualRTCPParamModel.setV31(v31);
			actualRTCPParamModel.setV32(v32);
			actualRTCPParamModel.setToolLength(toolLength);
		} finally {
			actualRTCPParamModel.getLock().writeLock().unlock();
		}
	}

	private final RandomDataImpl randomDataImpl = new RandomDataImpl();

	private double getRandomRTCPParam(double mean, double sigma, boolean flag) throws MathException {
		return flag ? randomDataImpl.nextGaussian(mean, sigma) : mean;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void useExperience() {
		info(I18nKey.LOGGER_33);

		SyncSettingHandler coreSettingHandler = rtcpTrain.getCoreSettingHandler();
		SyncRTCPParamModel currentRTCPParamModel = rtcpTrain.getCurrentRTCPParamModel();

		double v00Mean, v01Mean, v02Mean;
		double v10Mean, v11Mean, v12Mean;
		double v20Mean, v21Mean, v22Mean;
		double v30Mean, v31Mean, v32Mean;
		double toolLengthMean;

		coreSettingHandler.getLock().readLock().lock();
		try {
			v00Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V00,
					Double.class);
			v01Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V01,
					Double.class);
			v02Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V02,
					Double.class);
			v10Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V10,
					Double.class);
			v11Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V11,
					Double.class);
			v12Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V12,
					Double.class);
			v20Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V20,
					Double.class);
			v21Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V21,
					Double.class);
			v22Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V22,
					Double.class);
			v30Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V30,
					Double.class);
			v31Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V31,
					Double.class);
			v32Mean = coreSettingHandler.getParsedValidValue(CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_V32,
					Double.class);
			toolLengthMean = coreSettingHandler.getParsedValidValue(
					CoreSettingItem.PARAM_NORMAL_DISTRIBUTION_MEAN_ACTUAL_TOOL_LENGTH, Double.class);
		} finally {
			coreSettingHandler.getLock().readLock().unlock();
		}

		currentRTCPParamModel.getLock().writeLock().lock();
		try {
			currentRTCPParamModel.setV00(v00Mean);
			currentRTCPParamModel.setV01(v01Mean);
			currentRTCPParamModel.setV02(v02Mean);
			currentRTCPParamModel.setV10(v10Mean);
			currentRTCPParamModel.setV11(v11Mean);
			currentRTCPParamModel.setV12(v12Mean);
			currentRTCPParamModel.setV20(v20Mean);
			currentRTCPParamModel.setV21(v21Mean);
			currentRTCPParamModel.setV22(v22Mean);
			currentRTCPParamModel.setV30(v30Mean);
			currentRTCPParamModel.setV31(v31Mean);
			currentRTCPParamModel.setV32(v32Mean);
			currentRTCPParamModel.setToolLength(toolLengthMean);
		} finally {
			currentRTCPParamModel.getLock().writeLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void measureError() {
		SyncRTCPParamModel actualRTCPParamModel = rtcpTrain.getActualRTCPParamModel();
		SyncRTCPParamModel currentRTCPParamModel = rtcpTrain.getCurrentRTCPParamModel();
		SyncRotateAxisModel datumRotateAxisModel = rtcpTrain.getDatumRotateAxisModel();
		SyncRotateAxisModel measureRotateAxisModel = rtcpTrain.getMeasureRotateAxisModel();
		SyncReferenceModel<MeasureDirection> measureDirectionModel = rtcpTrain.getMeasureDirectionModel();
		SyncReferenceModel<Double> measureErrorModel = rtcpTrain.getMeasureErrorModel();

		double acV00, acV01, acV02;
		double acV10, acV11, acV12;
		double acV20, acV21, acV22;
		double acV30, acV31, acV32;
		double cuV00, cuV01, cuV02;
		double cuV10, cuV11, cuV12;
		double cuV20, cuV21, cuV22;
		double cuV30, cuV31, cuV32;
		double datumA, datumC, a, c;
		double acL, cuL;
		MeasureDirection measureDirection;

		actualRTCPParamModel.getLock().readLock().lock();
		try {
			acV00 = actualRTCPParamModel.getV00();
			acV01 = actualRTCPParamModel.getV01();
			acV02 = actualRTCPParamModel.getV02();
			acV10 = actualRTCPParamModel.getV10();
			acV11 = actualRTCPParamModel.getV11();
			acV12 = actualRTCPParamModel.getV12();
			acV20 = actualRTCPParamModel.getV20();
			acV21 = actualRTCPParamModel.getV21();
			acV22 = actualRTCPParamModel.getV22();
			acV30 = actualRTCPParamModel.getV30();
			acV31 = actualRTCPParamModel.getV31();
			acV32 = actualRTCPParamModel.getV32();
			acL = actualRTCPParamModel.getToolLength();
		} finally {
			actualRTCPParamModel.getLock().readLock().unlock();
		}

		currentRTCPParamModel.getLock().readLock().lock();
		try {
			cuV00 = currentRTCPParamModel.getV00();
			cuV01 = currentRTCPParamModel.getV01();
			cuV02 = currentRTCPParamModel.getV02();
			cuV10 = currentRTCPParamModel.getV10();
			cuV11 = currentRTCPParamModel.getV11();
			cuV12 = currentRTCPParamModel.getV12();
			cuV20 = currentRTCPParamModel.getV20();
			cuV21 = currentRTCPParamModel.getV21();
			cuV22 = currentRTCPParamModel.getV22();
			cuV30 = currentRTCPParamModel.getV30();
			cuV31 = currentRTCPParamModel.getV31();
			cuV32 = currentRTCPParamModel.getV32();
			cuL = currentRTCPParamModel.getToolLength();
		} finally {
			currentRTCPParamModel.getLock().readLock().unlock();
		}

		datumRotateAxisModel.getLock().readLock().lock();
		try {
			datumA = datumRotateAxisModel.getA();
			datumC = datumRotateAxisModel.getC();
		} finally {
			datumRotateAxisModel.getLock().readLock().unlock();
		}

		measureRotateAxisModel.getLock().readLock().lock();
		try {
			a = measureRotateAxisModel.getA();
			c = measureRotateAxisModel.getC();
		} finally {
			measureRotateAxisModel.getLock().readLock().unlock();
		}

		measureDirectionModel.getLock().readLock().lock();
		try {
			measureDirection = Optional.ofNullable(measureDirectionModel.get()).orElse(MeasureDirection.X);
		} finally {
			measureDirectionModel.getLock().readLock().unlock();
		}

		if (TransformUtil.norm(acV00, acV01, acV02) == 0 || TransformUtil.norm(acV20, acV21, acV22) == 0
				|| TransformUtil.norm(cuV00, cuV01, cuV02) == 0 || TransformUtil.norm(cuV20, cuV21, cuV22) == 0) {
			warn(I18nKey.LOGGER_30);
			return;
		}

		double measureError = TransformUtil
				.rtcpError(acV00, acV01, acV02, acV10, acV11, acV12, acV20, acV21, acV22, acV30, acV31, acV32, cuV00,
						cuV01, cuV02, cuV10, cuV11, cuV12, cuV20, cuV21, cuV22, cuV30, cuV31, cuV32,
						NumberUtil.unitTrans(datumA, Angle.DEG, Angle.RAD).doubleValue(),
						NumberUtil.unitTrans(datumC, Angle.DEG, Angle.RAD).doubleValue(),
						NumberUtil.unitTrans(a, Angle.DEG, Angle.RAD).doubleValue(),
						NumberUtil.unitTrans(c, Angle.DEG, Angle.RAD).doubleValue(), acL, cuL)
				.getEntry(measureDirection.getIndex(), 0);
		formatInfo(I18nKey.LOGGER_29, datumA, datumC, a, c, measureDirection.name(), measureError);

		measureErrorModel.getLock().writeLock().lock();
		try {
			measureErrorModel.set(measureError);
		} finally {
			measureErrorModel.getLock().writeLock().unlock();
		}
	}

	// --------------------------------------------日志输出--------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void trace(String message) throws NullPointerException {
		Objects.requireNonNull(message, "入口参数 message 不能为 null。");
		rtcpTrain.getLoggerHandler().trace(message);
	}

	@SuppressWarnings("unused")
	private void trace(I18nKey key) throws NullPointerException {
		trace(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void debug(String message) throws NullPointerException {
		Objects.requireNonNull(message, "入口参数 message 不能为 null。");
		rtcpTrain.getLoggerHandler().debug(message);
	}

	@SuppressWarnings("unused")
	private void debug(I18nKey key) throws NullPointerException {
		debug(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void info(String message) throws NullPointerException {
		Objects.requireNonNull(message, "入口参数 message 不能为 null。");
		rtcpTrain.getLoggerHandler().info(message);
	}

	private void info(I18nKey key) throws NullPointerException {
		info(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL));
	}

	private void formatInfo(I18nKey key, Object... args) {
		info(String.format(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL), args));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void warn(String message) throws NullPointerException {
		Objects.requireNonNull(message, "入口参数 message 不能为 null。");
		rtcpTrain.getLoggerHandler().warn(message);
	}

	private void warn(I18nKey key) throws NullPointerException {
		warn(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL));
	}

	@SuppressWarnings("unused")
	private void formatWarn(I18nKey key, Object... args) {
		warn(String.format(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL), args));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void warn(String message, Throwable t) throws NullPointerException {
		Objects.requireNonNull(message, "入口参数 message 不能为 null。");
		Objects.requireNonNull(t, "入口参数 t 不能为 null。");
		rtcpTrain.getLoggerHandler().warn(message, t);
	}

	private void warn(I18nKey key, Throwable t) throws NullPointerException {
		warn(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL), t);
	}

	private void formatWarn(I18nKey key, Throwable t, Object... args) {
		warn(String.format(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL), args), t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void error(String message, Throwable t) throws NullPointerException {
		Objects.requireNonNull(message, "入口参数 message 不能为 null。");
		Objects.requireNonNull(t, "入口参数 t 不能为 null。");
		rtcpTrain.getLoggerHandler().error(message, t);
	}

	private void error(I18nKey key, Throwable t) throws NullPointerException {
		error(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL), t);
	}

	private void formatError(I18nKey key, Throwable t, Object... args) {
		error(String.format(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL), args), t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fatal(String message, Throwable t) throws NullPointerException {
		Objects.requireNonNull(message, "入口参数 message 不能为 null。");
		Objects.requireNonNull(t, "入口参数 t 不能为 null。");
		rtcpTrain.getLoggerHandler().fatal(message, t);
	}

	@SuppressWarnings("unused")
	private void fatal(I18nKey key, Throwable t) throws NullPointerException {
		fatal(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL), t);
	}

	@SuppressWarnings("unused")
	private String i18nString(I18nKey key) {
		return rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL);
	}

	@SuppressWarnings("unused")
	private String formatI18nString(I18nKey key, Object... args) {
		return String.format(rtcpTrain.getI18nHandler().getStringOrDefault(key, Constants.MISSING_LABEL), args);
	}

}
