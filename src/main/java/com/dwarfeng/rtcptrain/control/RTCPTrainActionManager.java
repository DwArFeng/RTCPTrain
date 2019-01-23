package com.dwarfeng.rtcptrain.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
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

import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.SyncReferenceModel;
import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.SaveFailedException;
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
import com.dwarfeng.rtcptrain.model.enumeration.CliSettingItem;
import com.dwarfeng.rtcptrain.model.enumeration.CoreSettingItem;
import com.dwarfeng.rtcptrain.model.enumeration.I18nKey;
import com.dwarfeng.rtcptrain.model.enumeration.ModalSettingItem;
import com.dwarfeng.rtcptrain.model.enumeration.ResourceKey;
import com.dwarfeng.rtcptrain.util.Constants;
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
		SyncSettingHandler modalSettingHandler = rtcpTrain.getModalSettingHandler();

		modalSettingHandler.getLock().readLock().lock();
		try {
			// Something to do.
		} finally {
			modalSettingHandler.getLock().readLock().unlock();
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
		// 加载模态配置。
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

	@SuppressWarnings("unused")
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
