package com.dwarfeng.rtcptrain.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.develop.i18n.SyncI18nHandler;
import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;
import com.dwarfeng.rtcptrain.model.enumeration.I18nKey;
import com.dwarfeng.rtcptrain.util.Constants;
import com.dwarfeng.rtcptrain.view.task.ExitTask;

/**
 * 主界面。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class MainFrame extends JFrame {

	private final JPanel contentPane;

	private ModelManager modelManager;
	private ActionManager actionManager;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		this(null, null);
	}

	/**
	 * Create the frame.
	 */
	public MainFrame(ModelManager modelManager, ActionManager actionManager) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager
							.submit(new ExitTask(MainFrame.this.modelManager, MainFrame.this.actionManager));
				});
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/**
		 * 
		 * 
		 * 
		 * TODO here.
		 * 
		 * 
		 * 
		 * 
		 */

		this.modelManager = modelManager;
		this.actionManager = actionManager;

//		// 添加观察器。
//		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
//			manager.getFiles2ImportModel().addObverser(listObverser);
//		});

		// 同步模型。
		syncModel();
	}

	/**
	 * @return the modelManager
	 */
	public ModelManager getModelManager() {
		return modelManager;
	}

	/**
	 * @param modelManager the modelManager to set
	 */
	public void setModelManager(ModelManager modelManager) {
//		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
//		manager.getFiles2ImportModel().removeObverser(listObverser);
//		});
		this.modelManager = modelManager;
//		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
//		manager.getFiles2ImportModel().addObverser(listObverser);
//		});
		syncModel();
	}

	/**
	 * @return the actionManager
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}

	/**
	 * @param actionManager the actionManager to set
	 */
	public void setActionManager(ActionManager actionManager) {
//		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
//			manager.getFiles2ImportModel().removeObverser(listObverser);
//		});
		this.actionManager = actionManager;
//		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
//			manager.getFiles2ImportModel().addObverser(listObverser);
//		});
		syncModel();
	}

	private void syncModel() {
		SyncI18nHandler i18nHandler = modelManager.getI18nHandler();

//		this.setTitle(Constants.MISSING_LABEL);
//		btnImport.setText(Constants.MISSING_LABEL);
//		btnExport.setText(Constants.MISSING_LABEL);
//		lblBanner.setText(Constants.MISSING_LABEL);
//
//		listModel.clear();

		if (Objects.isNull(modelManager)) {
			return;
		}

		i18nHandler.getLock().readLock().lock();
		try {
//			this.setTitle(i18nString(I18nKey.LABEL_1));
//			btnImport.setText(i18nString(I18nKey.LABEL_2));
//			btnExport.setText(i18nString(I18nKey.LABEL_3));
//			lblBanner.setText(i18nString(I18nKey.LABEL_4));
		} finally {
			i18nHandler.getLock().readLock().unlock();
		}
	}

	private String i18nString(I18nKey i18nKey) {
		return Optional.ofNullable(modelManager)
				.map(manager -> manager.getI18nHandler().getStringOrDefault(i18nKey, Constants.MISSING_LABEL))
				.orElse("null");
	}

	private void checkManagerAndDo(Runnable runnable) {
		if (Objects.isNull(modelManager) || Objects.isNull(actionManager)) {
			return;
		}
		runnable.run();
	}

}
