package com.dwarfeng.rtcptrain.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.EventObject;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import com.dwarfeng.dutil.basic.cna.model.SyncReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.obv.ReferenceAdapter;
import com.dwarfeng.dutil.basic.cna.model.obv.ReferenceObverser;
import com.dwarfeng.dutil.basic.gui.swing.JAdjustableBorderPanel;
import com.dwarfeng.dutil.basic.gui.swing.JExconsole;
import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.dwarfeng.dutil.develop.i18n.SyncI18nHandler;
import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;
import com.dwarfeng.rtcptrain.control.RTCPTrain;
import com.dwarfeng.rtcptrain.model.SyncRTCPParamModel;
import com.dwarfeng.rtcptrain.model.SyncRotateAxisModel;
import com.dwarfeng.rtcptrain.model.enumeration.I18nKey;
import com.dwarfeng.rtcptrain.model.enumeration.MeasureDirection;
import com.dwarfeng.rtcptrain.model.obverser.RTCPParamAdapter;
import com.dwarfeng.rtcptrain.model.obverser.RTCPParamObverser;
import com.dwarfeng.rtcptrain.model.obverser.RotateAxisAdapter;
import com.dwarfeng.rtcptrain.model.obverser.RotateAxisObverser;
import com.dwarfeng.rtcptrain.util.Constants;
import com.dwarfeng.rtcptrain.view.task.ExitTask;
import com.dwarfeng.rtcptrain.view.task.MeasureTask;
import com.dwarfeng.rtcptrain.view.task.RandomAcTask;
import com.dwarfeng.rtcptrain.view.task.SetAcTask;
import com.dwarfeng.rtcptrain.view.task.SetCrTask;
import com.dwarfeng.rtcptrain.view.task.SetDatumRaTask;
import com.dwarfeng.rtcptrain.view.task.SetMeaDirTask;
import com.dwarfeng.rtcptrain.view.task.SetMeaRaTask;
import com.dwarfeng.rtcptrain.view.task.UseExperienceTask;

/**
 * 主界面。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -4690777196561214691L;

	private static final String ACTION_KEY_1 = "ak1";

	private final JPanel contentPane;
	private final JLabel lblVersionIndicator;
	private final JLabel lblBanner1;
	private final JLabel lblBanner2;
	private final JLabel lblBanner3;
	private final JLabel lblBanner4;
	private final JLabel lblBanner5;
	private final JLabel lblBanner6;
	private final JLabel lblBanner7;
	private final JLabel lblBanner8;
	private final JLabel lblBanner9;
	private final JLabel lblBanner10;
	private final JLabel lblBanner11;
	private final JLabel lblBanner12;
	private final JLabel lblBanner13;
	private final JLabel lblBanner14;
	private final JTable crTable;
	private final JTable acTable;
	private final JSpinner crToolLengthSpinner;
	private final JSpinner acToolLengthSpinner;
	private final JSpinner datumASpinner;
	private final JSpinner datumCSpinner;
	private final JSpinner meaASpinner;
	private final JSpinner meaCSpinner;
	private final JRadioButton meaDirectionX;
	private final JRadioButton meaDirectionY;
	private final JRadioButton meaDirectionZ;
	private final ButtonGroup meaDirectionGroup;
	private final JButton btnRandomAc;
	private final JButton btnMeasure;
	private final JButton btnUseExperience;
	private final JToggleButton btnShowAc;
	private final JExconsole exconsole;
	private final JTextField meaErrTextField;

	private ModelManager modelManager;
	private ActionManager actionManager;

	private final TableModel crTableModel = new JamTableModel();
	private final TableModel acTableModel = new JamTableModel();
	private final TableCellEditor rtcpParamCellEditor = new JSpinnerCellEditor();

	private final RTCPParamObverser crRTCPParamObverser = new RTCPParamAdapter() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV00Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 0, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 0, 1);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV01Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 1, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 0, 2);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV02Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 2, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 0, 3);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV10Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 10, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 1, 1);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV11Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 11, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 1, 2);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV12Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 12, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 1, 3);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV20Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 20, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 2, 1);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV21Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 21, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 2, 2);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV22Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 22, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 2, 3);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV30Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 30, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 3, 1);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV31Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 31, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 3, 2);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV32Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 32, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crTableModel.setValueAt(newValue, 3, 3);
				crAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireToolLengthChanged(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(crDuplexingForecast, new Object[] { 150, newValue })) {
					return;
				}

				crAdjustFlag = true;
				crToolLengthSpinner.setValue(newValue);
				crAdjustFlag = false;
			});
		}

	};
	private final RTCPParamObverser acRTCPParamObverser = new RTCPParamAdapter() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV00Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 0, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 0, 1);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV01Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 1, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 0, 2);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV02Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 2, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 0, 3);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV10Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 10, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 1, 1);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV11Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 11, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 1, 2);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV12Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 12, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 1, 3);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV20Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 20, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 2, 1);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV21Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 21, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 2, 2);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV22Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 22, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 2, 3);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV30Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 30, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 3, 1);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV31Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 31, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 3, 2);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireV32Changed(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 32, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acTableModel.setValueAt(newValue, 3, 3);
				acAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireToolLengthChanged(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(acDuplexingForecast, new Object[] { 150, newValue })) {
					return;
				}

				acAdjustFlag = true;
				acToolLengthSpinner.setValue(newValue);
				acAdjustFlag = false;
			});
		}

	};
	private final RotateAxisObverser datumRotateAxisObverser = new RotateAxisAdapter() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireAChanged(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(datumRaDuplexingForecast,
						new Object[] { Constants.ACTION_INDEX_DATAUM_ROTATE_AXIS_A, newValue })) {
					return;
				}

				datumRaAdjustFlag = true;
				datumASpinner.setValue(newValue);
				datumRaAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireCChanged(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(datumRaDuplexingForecast,
						new Object[] { Constants.ACTION_INDEX_DATAUM_ROTATE_AXIS_C, newValue })) {
					return;
				}

				datumRaAdjustFlag = true;
				datumCSpinner.setValue(newValue);
				datumRaAdjustFlag = false;
			});
		}
	};
	private final RotateAxisObverser measureRotateAxisObverser = new RotateAxisAdapter() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireAChanged(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(datumRaDuplexingForecast,
						new Object[] { Constants.ACTION_INDEX_MEASURE_ROTATE_AXIS_A, newValue })) {
					return;
				}

				meaRaAdjustFlag = true;
				meaASpinner.setValue(newValue);
				meaRaAdjustFlag = false;
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireCChanged(double oldValue, double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				if (checkDuplexingForecast(datumRaDuplexingForecast,
						new Object[] { Constants.ACTION_INDEX_MEASURE_ROTATE_AXIS_C, newValue })) {
					return;
				}

				meaRaAdjustFlag = true;
				meaCSpinner.setValue(newValue);
				meaRaAdjustFlag = false;
			});
		}
	};
	private final ReferenceObverser<MeasureDirection> measureDirectionObverser = new ReferenceAdapter<MeasureDirection>() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireSet(MeasureDirection oldValue, MeasureDirection newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				int index;
				switch (newValue) {
				case X:
					index = Constants.ACTION_INDEX_MEASURE_DIRECTION_X;
					break;
				case Y:
					index = Constants.ACTION_INDEX_MEASURE_DIRECTION_Y;
					break;
				case Z:
					index = Constants.ACTION_INDEX_MEASURE_DIRECTION_Z;
					break;
				default:
					index = Constants.ACTION_INDEX_MEASURE_DIRECTION_X;
					break;
				}

				if (checkDuplexingForecast(datumRaDuplexingForecast, new Object[] { index, newValue })) {
					return;
				}

				meaDirAdjustFlag = true;
				switch (newValue) {
				case X:
					meaDirectionGroup.setSelected(meaDirectionX.getModel(), true);
					break;
				case Y:
					meaDirectionGroup.setSelected(meaDirectionY.getModel(), true);
					break;
				case Z:
					meaDirectionGroup.setSelected(meaDirectionZ.getModel(), true);
					break;
				default:
					meaDirectionGroup.setSelected(meaDirectionX.getModel(), true);
					break;
				}
				meaDirAdjustFlag = false;
			});
		}

	};
	private final ReferenceObverser<Double> measureErrorObverser = new ReferenceAdapter<Double>() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fireSet(Double oldValue, Double newValue) {
			SwingUtil.invokeInEventQueue(() -> {
				meaErrTextField.setText(Double.toString(newValue));
			});
		}

	};

	/** 双工通信预测。 */
	private final Queue<Object[]> crDuplexingForecast = new ArrayDeque<>();
	/** 双工通信预测。 */
	private final Queue<Object[]> acDuplexingForecast = new ArrayDeque<>();
	/** 双工通信预测。 */
	private final Queue<Object[]> datumRaDuplexingForecast = new ArrayDeque<>();
	/** 双工通信预测。 */
	private final Queue<Object[]> meaRaDuplexingForecast = new ArrayDeque<>();
	/** 双工通信预测。 */
	private final Queue<Object[]> meaDirDuplexingForecast = new ArrayDeque<>();

	/** 准备标识。 */
	private boolean crAdjustFlag = false;
	/** 准备标识。 */
	private boolean acAdjustFlag = false;
	/** 准备标识。 */
	private boolean datumRaAdjustFlag = false;
	/** 准备标识。 */
	private boolean meaRaAdjustFlag = false;
	/** 准备标识。 */
	private boolean meaDirAdjustFlag = false;

	private InputStream sysIn;
	private PrintStream sysOut;
	private PrintStream sysErr;

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
		setBounds(100, 100, 600, 800);
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
		contentPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.requestFocus();
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		lblVersionIndicator = new JLabel(RTCPTrain.VERSION.getLongName());
		lblVersionIndicator.setHorizontalAlignment(SwingConstants.TRAILING);
		contentPane.add(lblVersionIndicator, BorderLayout.SOUTH);

		JAdjustableBorderPanel adjustableBorderPanel = new JAdjustableBorderPanel();
		adjustableBorderPanel.setWestSeparatorEnabled(false);
		adjustableBorderPanel.setWestEnabled(true);
		adjustableBorderPanel.setSeperatorThickness(5);
		adjustableBorderPanel.setEastEnabled(true);
		contentPane.add(adjustableBorderPanel, BorderLayout.CENTER);

		meaDirectionGroup = new ButtonGroup();
		sysIn = System.in;
		sysOut = System.out;
		sysErr = System.err;

		JPanel panel_2 = new JPanel();
		adjustableBorderPanel.add(panel_2, BorderLayout.EAST);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		lblBanner10 = new JLabel();
		GridBagConstraints gbc_lblBanner10 = new GridBagConstraints();
		gbc_lblBanner10.gridwidth = 4;
		gbc_lblBanner10.anchor = GridBagConstraints.WEST;
		gbc_lblBanner10.insets = new Insets(0, 0, 5, 0);
		gbc_lblBanner10.gridx = 0;
		gbc_lblBanner10.gridy = 0;
		panel_2.add(lblBanner10, gbc_lblBanner10);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 4;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);

		acTable = new JTable();
		acTable.putClientProperty("terminateEditOnFocusLost", true);
		acTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		acTable.getTableHeader().setReorderingAllowed(false);
		acTable.setFillsViewportHeight(true);
		acTable.setModel(acTableModel);
		acTable.getColumnModel().getColumn(1).setCellEditor(rtcpParamCellEditor);
		acTable.getColumnModel().getColumn(2).setCellEditor(rtcpParamCellEditor);
		acTable.getColumnModel().getColumn(3).setCellEditor(rtcpParamCellEditor);
		acTable.setRowHeight(32);
		acTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == 0 || acAdjustFlag)
					return;
				int index = e.getFirstRow() * 10 + (e.getColumn() - 1);
				double newValue = (double) acTableModel.getValueAt(e.getFirstRow(), e.getColumn());
				acDuplexingForecast.add(new Object[] { index, newValue });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(
							new SetAcTask(MainFrame.this.modelManager, MainFrame.this.actionManager, index, newValue));
				});
			}
		});
		scrollPane_1.setViewportView(acTable);

		lblBanner13 = new JLabel();
		GridBagConstraints gbc_lblBanner13 = new GridBagConstraints();
		gbc_lblBanner13.insets = new Insets(0, 0, 0, 5);
		gbc_lblBanner13.gridx = 0;
		gbc_lblBanner13.gridy = 2;
		panel_2.add(lblBanner13, gbc_lblBanner13);

		acToolLengthSpinner = new JPreciseSpinner();
		acToolLengthSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		acToolLengthSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (acAdjustFlag)
					return;
				double newValue = (double) acToolLengthSpinner.getValue();
				acDuplexingForecast.add(new Object[] { 150, newValue });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(
							new SetAcTask(MainFrame.this.modelManager, MainFrame.this.actionManager, 150, newValue));
				});
			}
		});
		GridBagConstraints gbc_acToolLengthSpinner = new GridBagConstraints();
		gbc_acToolLengthSpinner.fill = GridBagConstraints.BOTH;
		gbc_acToolLengthSpinner.gridwidth = 3;
		gbc_acToolLengthSpinner.gridx = 1;
		gbc_acToolLengthSpinner.gridy = 2;
		panel_2.add(acToolLengthSpinner, gbc_acToolLengthSpinner);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		adjustableBorderPanel.add(panel_3, BorderLayout.WEST);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		lblBanner14 = new JLabel();
		GridBagConstraints gbc_lblBanner14 = new GridBagConstraints();
		gbc_lblBanner14.insets = new Insets(0, 0, 5, 0);
		gbc_lblBanner14.anchor = GridBagConstraints.WEST;
		gbc_lblBanner14.gridx = 0;
		gbc_lblBanner14.gridy = 0;
		panel_3.add(lblBanner14, gbc_lblBanner14);

		btnRandomAc = new JButton();
		btnRandomAc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager
							.submit(new RandomAcTask(MainFrame.this.modelManager, MainFrame.this.actionManager));
				});
			}
		});
		GridBagConstraints gbc_btnRandomAc = new GridBagConstraints();
		gbc_btnRandomAc.insets = new Insets(0, 0, 5, 0);
		gbc_btnRandomAc.fill = GridBagConstraints.BOTH;
		gbc_btnRandomAc.gridx = 0;
		gbc_btnRandomAc.gridy = 2;
		panel_3.add(btnRandomAc, gbc_btnRandomAc);

		btnMeasure = new JButton();
		btnMeasure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager
							.submit(new MeasureTask(MainFrame.this.modelManager, MainFrame.this.actionManager));
				});
			}
		});
		GridBagConstraints gbc_btnMeasure = new GridBagConstraints();
		gbc_btnMeasure.fill = GridBagConstraints.BOTH;
		gbc_btnMeasure.insets = new Insets(0, 0, 5, 0);
		gbc_btnMeasure.gridx = 0;
		gbc_btnMeasure.gridy = 3;
		panel_3.add(btnMeasure, gbc_btnMeasure);

		btnShowAc = new JToggleButton();
		adjustableBorderPanel.setEastVisible(btnShowAc.isSelected());
		btnShowAc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				adjustableBorderPanel.setEastVisible(btnShowAc.isSelected());
			}
		});

		btnUseExperience = new JButton();
		btnUseExperience.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager
							.submit(new UseExperienceTask(MainFrame.this.modelManager, MainFrame.this.actionManager));
				});
			}
		});
		GridBagConstraints gbc_btnUseExperience = new GridBagConstraints();
		gbc_btnUseExperience.fill = GridBagConstraints.BOTH;
		gbc_btnUseExperience.insets = new Insets(0, 0, 5, 0);
		gbc_btnUseExperience.gridx = 0;
		gbc_btnUseExperience.gridy = 4;
		panel_3.add(btnUseExperience, gbc_btnUseExperience);
		GridBagConstraints gbc_btnShowAc = new GridBagConstraints();
		gbc_btnShowAc.insets = new Insets(0, 0, 5, 0);
		gbc_btnShowAc.fill = GridBagConstraints.BOTH;
		gbc_btnShowAc.gridx = 0;
		gbc_btnShowAc.gridy = 5;
		panel_3.add(btnShowAc, gbc_btnShowAc);

		JAdjustableBorderPanel adjustableBorderPanel_1 = new JAdjustableBorderPanel();
		adjustableBorderPanel_1.setSouthPreferredValue(300);
		adjustableBorderPanel_1.setSeperatorThickness(5);
		adjustableBorderPanel_1.setSouthVisible(true);
		adjustableBorderPanel_1.setSouthEnabled(true);
		adjustableBorderPanel.add(adjustableBorderPanel_1, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		adjustableBorderPanel_1.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblBanner9 = new JLabel();
		GridBagConstraints gbc_lblBanner9 = new GridBagConstraints();
		gbc_lblBanner9.gridwidth = 4;
		gbc_lblBanner9.anchor = GridBagConstraints.WEST;
		gbc_lblBanner9.insets = new Insets(0, 0, 5, 0);
		gbc_lblBanner9.gridx = 0;
		gbc_lblBanner9.gridy = 0;
		panel.add(lblBanner9, gbc_lblBanner9);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);

		crTable = new JTable();
		crTable.putClientProperty("terminateEditOnFocusLost", true);
		crTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		crTable.getTableHeader().setReorderingAllowed(false);
		crTable.setFillsViewportHeight(true);
		crTable.setModel(crTableModel);
		crTable.getColumnModel().getColumn(1).setCellEditor(rtcpParamCellEditor);
		crTable.getColumnModel().getColumn(2).setCellEditor(rtcpParamCellEditor);
		crTable.getColumnModel().getColumn(3).setCellEditor(rtcpParamCellEditor);
		crTable.setRowHeight(32);
		crTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == 0 || crAdjustFlag)
					return;
				int index = e.getFirstRow() * 10 + (e.getColumn() - 1);
				double newValue = (double) crTableModel.getValueAt(e.getFirstRow(), e.getColumn());
				crDuplexingForecast.add(new Object[] { index, newValue });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(
							new SetCrTask(MainFrame.this.modelManager, MainFrame.this.actionManager, index, newValue));
				});
			}
		});

		scrollPane.setViewportView(crTable);

		lblBanner12 = new JLabel();
		GridBagConstraints gbc_lblBanner12 = new GridBagConstraints();
		gbc_lblBanner12.insets = new Insets(0, 0, 5, 5);
		gbc_lblBanner12.gridx = 0;
		gbc_lblBanner12.gridy = 2;
		panel.add(lblBanner12, gbc_lblBanner12);

		crToolLengthSpinner = new JPreciseSpinner();
		crToolLengthSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		crToolLengthSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (crAdjustFlag)
					return;
				double newValue = (double) crToolLengthSpinner.getValue();
				crDuplexingForecast.add(new Object[] { 150, newValue });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(
							new SetCrTask(MainFrame.this.modelManager, MainFrame.this.actionManager, 150, newValue));
				});
			}
		});
		GridBagConstraints gbc_crToolLengthSpinner = new GridBagConstraints();
		gbc_crToolLengthSpinner.fill = GridBagConstraints.BOTH;
		gbc_crToolLengthSpinner.gridwidth = 3;
		gbc_crToolLengthSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_crToolLengthSpinner.gridx = 1;
		gbc_crToolLengthSpinner.gridy = 2;
		panel.add(crToolLengthSpinner, gbc_crToolLengthSpinner);

		lblBanner4 = new JLabel();
		GridBagConstraints gbc_lblBanner4 = new GridBagConstraints();
		gbc_lblBanner4.anchor = GridBagConstraints.WEST;
		gbc_lblBanner4.insets = new Insets(0, 0, 5, 5);
		gbc_lblBanner4.gridx = 0;
		gbc_lblBanner4.gridy = 3;
		panel.add(lblBanner4, gbc_lblBanner4);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 4;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));

		meaDirectionX = new JRadioButton();
		meaDirectionX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (meaDirAdjustFlag)
					return;
				meaDirDuplexingForecast.add(new Object[] { Constants.ACTION_INDEX_MEASURE_DIRECTION_X });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(new SetMeaDirTask(MainFrame.this.modelManager,
							MainFrame.this.actionManager, Constants.ACTION_INDEX_MEASURE_DIRECTION_X));
				});
			}
		});
		panel_1.add(meaDirectionX);

		meaDirectionY = new JRadioButton();
		meaDirectionY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (meaDirAdjustFlag)
					return;
				meaDirDuplexingForecast.add(new Object[] { Constants.ACTION_INDEX_MEASURE_DIRECTION_Y });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(new SetMeaDirTask(MainFrame.this.modelManager,
							MainFrame.this.actionManager, Constants.ACTION_INDEX_MEASURE_DIRECTION_Y));
				});
			}
		});
		panel_1.add(meaDirectionY);

		meaDirectionZ = new JRadioButton();
		meaDirectionZ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (meaDirAdjustFlag)
					return;
				meaDirDuplexingForecast.add(new Object[] { Constants.ACTION_INDEX_MEASURE_DIRECTION_Z });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(new SetMeaDirTask(MainFrame.this.modelManager,
							MainFrame.this.actionManager, Constants.ACTION_INDEX_MEASURE_DIRECTION_Z));
				});
			}
		});
		panel_1.add(meaDirectionZ);
		meaDirectionGroup.add(meaDirectionX);
		meaDirectionGroup.add(meaDirectionY);
		meaDirectionGroup.add(meaDirectionZ);

		lblBanner1 = new JLabel();
		GridBagConstraints gbc_lblBanner1 = new GridBagConstraints();
		gbc_lblBanner1.anchor = GridBagConstraints.WEST;
		gbc_lblBanner1.insets = new Insets(0, 0, 5, 0);
		gbc_lblBanner1.gridwidth = 4;
		gbc_lblBanner1.gridx = 0;
		gbc_lblBanner1.gridy = 5;
		panel.add(lblBanner1, gbc_lblBanner1);

		lblBanner2 = new JLabel();
		GridBagConstraints gbc_lblBanner2 = new GridBagConstraints();
		gbc_lblBanner2.anchor = GridBagConstraints.WEST;
		gbc_lblBanner2.insets = new Insets(0, 0, 5, 5);
		gbc_lblBanner2.gridx = 0;
		gbc_lblBanner2.gridy = 6;
		panel.add(lblBanner2, gbc_lblBanner2);

		datumASpinner = new JPreciseSpinner();
		datumASpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (datumRaAdjustFlag)
					return;
				double newValue = (double) datumASpinner.getValue();
				datumRaDuplexingForecast.add(new Object[] { Constants.ACTION_INDEX_DATAUM_ROTATE_AXIS_A, newValue });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(new SetDatumRaTask(MainFrame.this.modelManager,
							MainFrame.this.actionManager, Constants.ACTION_INDEX_DATAUM_ROTATE_AXIS_A, newValue));
				});
			}
		});
		datumASpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(90)));
		GridBagConstraints gbc_datumASpinner = new GridBagConstraints();
		gbc_datumASpinner.fill = GridBagConstraints.BOTH;
		gbc_datumASpinner.insets = new Insets(0, 0, 5, 5);
		gbc_datumASpinner.gridx = 1;
		gbc_datumASpinner.gridy = 6;
		panel.add(datumASpinner, gbc_datumASpinner);

		lblBanner3 = new JLabel();
		GridBagConstraints gbc_lblBanner3 = new GridBagConstraints();
		gbc_lblBanner3.anchor = GridBagConstraints.WEST;
		gbc_lblBanner3.insets = new Insets(0, 0, 5, 5);
		gbc_lblBanner3.gridx = 2;
		gbc_lblBanner3.gridy = 6;
		panel.add(lblBanner3, gbc_lblBanner3);

		datumCSpinner = new JPreciseSpinner();
		datumCSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (datumRaAdjustFlag)
					return;
				double newValue = (double) datumCSpinner.getValue();
				datumRaDuplexingForecast.add(new Object[] { Constants.ACTION_INDEX_DATAUM_ROTATE_AXIS_C, newValue });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(new SetDatumRaTask(MainFrame.this.modelManager,
							MainFrame.this.actionManager, Constants.ACTION_INDEX_DATAUM_ROTATE_AXIS_C, newValue));
				});
			}
		});
		datumCSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(90)));
		GridBagConstraints gbc_datumCSpinner = new GridBagConstraints();
		gbc_datumCSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_datumCSpinner.fill = GridBagConstraints.BOTH;
		gbc_datumCSpinner.gridx = 3;
		gbc_datumCSpinner.gridy = 6;
		panel.add(datumCSpinner, gbc_datumCSpinner);

		lblBanner5 = new JLabel();
		GridBagConstraints gbc_lblBanner5 = new GridBagConstraints();
		gbc_lblBanner5.gridwidth = 4;
		gbc_lblBanner5.anchor = GridBagConstraints.WEST;
		gbc_lblBanner5.insets = new Insets(0, 0, 5, 0);
		gbc_lblBanner5.gridx = 0;
		gbc_lblBanner5.gridy = 7;
		panel.add(lblBanner5, gbc_lblBanner5);

		lblBanner6 = new JLabel();
		GridBagConstraints gbc_lblBanner6 = new GridBagConstraints();
		gbc_lblBanner6.anchor = GridBagConstraints.WEST;
		gbc_lblBanner6.insets = new Insets(0, 0, 5, 5);
		gbc_lblBanner6.gridx = 0;
		gbc_lblBanner6.gridy = 8;
		panel.add(lblBanner6, gbc_lblBanner6);

		meaASpinner = new JPreciseSpinner();
		meaASpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (meaRaAdjustFlag)
					return;
				double newValue = (double) meaASpinner.getValue();
				meaRaDuplexingForecast.add(new Object[] { Constants.ACTION_INDEX_MEASURE_ROTATE_AXIS_A, newValue });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(new SetMeaRaTask(MainFrame.this.modelManager,
							MainFrame.this.actionManager, Constants.ACTION_INDEX_MEASURE_ROTATE_AXIS_A, newValue));
				});
			}
		});
		meaASpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(90)));
		GridBagConstraints gbc_meaASpinner = new GridBagConstraints();
		gbc_meaASpinner.fill = GridBagConstraints.BOTH;
		gbc_meaASpinner.insets = new Insets(0, 0, 5, 5);
		gbc_meaASpinner.gridx = 1;
		gbc_meaASpinner.gridy = 8;
		panel.add(meaASpinner, gbc_meaASpinner);

		lblBanner7 = new JLabel();
		GridBagConstraints gbc_lblBanner7 = new GridBagConstraints();
		gbc_lblBanner7.anchor = GridBagConstraints.WEST;
		gbc_lblBanner7.insets = new Insets(0, 0, 5, 5);
		gbc_lblBanner7.gridx = 2;
		gbc_lblBanner7.gridy = 8;
		panel.add(lblBanner7, gbc_lblBanner7);

		meaCSpinner = new JPreciseSpinner();
		meaCSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (meaRaAdjustFlag)
					return;
				double newValue = (double) meaCSpinner.getValue();
				meaRaDuplexingForecast.add(new Object[] { Constants.ACTION_INDEX_MEASURE_ROTATE_AXIS_C, newValue });
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager.submit(new SetMeaRaTask(MainFrame.this.modelManager,
							MainFrame.this.actionManager, Constants.ACTION_INDEX_MEASURE_ROTATE_AXIS_C, newValue));
				});
			}
		});
		meaCSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(90)));
		GridBagConstraints gbc_meaCSpinner = new GridBagConstraints();
		gbc_meaCSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_meaCSpinner.fill = GridBagConstraints.BOTH;
		gbc_meaCSpinner.gridx = 3;
		gbc_meaCSpinner.gridy = 8;
		panel.add(meaCSpinner, gbc_meaCSpinner);

		lblBanner8 = new JLabel();
		GridBagConstraints gbc_lblBanner8 = new GridBagConstraints();
		gbc_lblBanner8.anchor = GridBagConstraints.WEST;
		gbc_lblBanner8.insets = new Insets(0, 0, 5, 5);
		gbc_lblBanner8.gridx = 0;
		gbc_lblBanner8.gridy = 9;
		panel.add(lblBanner8, gbc_lblBanner8);

		meaErrTextField = new JTextField();
		meaErrTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		meaErrTextField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridwidth = 3;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 9;
		panel.add(meaErrTextField, gbc_textField);
		meaErrTextField.setColumns(10);

		lblBanner11 = new JLabel();
		GridBagConstraints gbc_lblBanner11 = new GridBagConstraints();
		gbc_lblBanner11.anchor = GridBagConstraints.WEST;
		gbc_lblBanner11.gridwidth = 4;
		gbc_lblBanner11.gridx = 0;
		gbc_lblBanner11.gridy = 10;
		panel.add(lblBanner11, gbc_lblBanner11);

		JPanel panel_4 = new JPanel();
		adjustableBorderPanel_1.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_4.add(scrollPane_2, BorderLayout.CENTER);

		exconsole = new JExconsole();
		System.setIn(exconsole.in);
		System.setOut(exconsole.out);
		System.setErr(exconsole.out);
		scrollPane_2.setViewportView(exconsole);

		contentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK), ACTION_KEY_1);
		contentPane.getActionMap().put(ACTION_KEY_1, new AbstractAction() {

			private static final long serialVersionUID = 4334932440780255423L;

			@Override
			public void actionPerformed(ActionEvent e) {
				checkManagerAndDo(() -> {
					MainFrame.this.actionManager
							.submit(new MeasureTask(MainFrame.this.modelManager, MainFrame.this.actionManager));
				});
			}
		});

		this.modelManager = modelManager;
		this.actionManager = actionManager;

		// 添加观察器。
		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
			manager.getCurrentRTCPParamModel().addObverser(crRTCPParamObverser);
			manager.getActualRTCPParamModel().addObverser(acRTCPParamObverser);
			manager.getDatumRotateAxisModel().addObverser(datumRotateAxisObverser);
			manager.getMeasureRotateAxisModel().addObverser(measureRotateAxisObverser);
			manager.getMeasureDirectionModel().addObverser(measureDirectionObverser);
			manager.getMeasureErrorModel().addObverser(measureErrorObverser);
		});

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
	 * @param modelManager
	 *            the modelManager to set
	 */
	public void setModelManager(ModelManager modelManager) {
		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
			manager.getCurrentRTCPParamModel().removeObverser(crRTCPParamObverser);
			manager.getActualRTCPParamModel().removeObverser(acRTCPParamObverser);
			manager.getDatumRotateAxisModel().removeObverser(datumRotateAxisObverser);
			manager.getMeasureRotateAxisModel().removeObverser(measureRotateAxisObverser);
			manager.getMeasureDirectionModel().removeObverser(measureDirectionObverser);
			manager.getMeasureErrorModel().removeObverser(measureErrorObverser);
		});
		this.modelManager = modelManager;
		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
			manager.getCurrentRTCPParamModel().addObverser(crRTCPParamObverser);
			manager.getActualRTCPParamModel().addObverser(acRTCPParamObverser);
			manager.getDatumRotateAxisModel().addObverser(datumRotateAxisObverser);
			manager.getMeasureRotateAxisModel().addObverser(measureRotateAxisObverser);
			manager.getMeasureDirectionModel().addObverser(measureDirectionObverser);
			manager.getMeasureErrorModel().addObverser(measureErrorObverser);
		});
		syncModel();
	}

	/**
	 * @return the actionManager
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}

	/**
	 * @param actionManager
	 *            the actionManager to set
	 */
	public void setActionManager(ActionManager actionManager) {
		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
			manager.getCurrentRTCPParamModel().removeObverser(crRTCPParamObverser);
			manager.getActualRTCPParamModel().removeObverser(acRTCPParamObverser);
			manager.getDatumRotateAxisModel().removeObverser(datumRotateAxisObverser);
			manager.getMeasureRotateAxisModel().removeObverser(measureRotateAxisObverser);
			manager.getMeasureDirectionModel().removeObverser(measureDirectionObverser);
			manager.getMeasureErrorModel().removeObverser(measureErrorObverser);
		});
		this.actionManager = actionManager;
		Optional.ofNullable(this.modelManager).ifPresent(manager -> {
			manager.getCurrentRTCPParamModel().addObverser(crRTCPParamObverser);
			manager.getActualRTCPParamModel().addObverser(acRTCPParamObverser);
			manager.getDatumRotateAxisModel().addObverser(datumRotateAxisObverser);
			manager.getMeasureRotateAxisModel().addObverser(measureRotateAxisObverser);
			manager.getMeasureDirectionModel().addObverser(measureDirectionObverser);
			manager.getMeasureErrorModel().addObverser(measureErrorObverser);
		});
		syncModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		System.setIn(sysIn);
		System.setOut(sysOut);
		System.setErr(sysErr);
		exconsole.dispose();
		super.dispose();
	}

	private void syncModel() {
		SyncI18nHandler i18nHandler = modelManager.getI18nHandler();
		SyncRTCPParamModel currentRTCPParamModel = modelManager.getCurrentRTCPParamModel();
		SyncRTCPParamModel actualRTCPParamModel = modelManager.getActualRTCPParamModel();
		SyncRotateAxisModel datumRotateAxisModel = modelManager.getDatumRotateAxisModel();
		SyncRotateAxisModel measureRotateAxisModel = modelManager.getMeasureRotateAxisModel();
		SyncReferenceModel<MeasureDirection> measureDirectionModel = modelManager.getMeasureDirectionModel();
		SyncReferenceModel<Double> measureErrorModel = modelManager.getMeasureErrorModel();

		this.setTitle(Constants.MISSING_LABEL);
		lblBanner1.setText(Constants.MISSING_LABEL);
		lblBanner2.setText(Constants.MISSING_LABEL);
		lblBanner3.setText(Constants.MISSING_LABEL);
		lblBanner4.setText(Constants.MISSING_LABEL);
		lblBanner5.setText(Constants.MISSING_LABEL);
		lblBanner6.setText(Constants.MISSING_LABEL);
		lblBanner7.setText(Constants.MISSING_LABEL);
		lblBanner8.setText(Constants.MISSING_LABEL);
		lblBanner9.setText(Constants.MISSING_LABEL);
		lblBanner10.setText(Constants.MISSING_LABEL);
		lblBanner11.setText(Constants.MISSING_LABEL);
		lblBanner12.setText(Constants.MISSING_LABEL);
		lblBanner13.setText(Constants.MISSING_LABEL);
		lblBanner14.setText(Constants.MISSING_LABEL);
		btnRandomAc.setText(Constants.MISSING_LABEL);
		btnMeasure.setText(Constants.MISSING_LABEL);
		btnUseExperience.setText(Constants.MISSING_LABEL);
		btnShowAc.setText(Constants.MISSING_LABEL);
		meaDirectionX.setText(Constants.MISSING_LABEL);
		meaDirectionY.setText(Constants.MISSING_LABEL);
		meaDirectionZ.setText(Constants.MISSING_LABEL);
		crTable.getColumnModel().getColumn(0).setHeaderValue(Constants.MISSING_LABEL);
		crTable.getColumnModel().getColumn(1).setHeaderValue(Constants.MISSING_LABEL);
		crTable.getColumnModel().getColumn(2).setHeaderValue(Constants.MISSING_LABEL);
		crTable.getColumnModel().getColumn(3).setHeaderValue(Constants.MISSING_LABEL);
		crAdjustFlag = true;
		crTableModel.setValueAt(Constants.MISSING_LABEL, 0, 0);
		crTableModel.setValueAt(Constants.MISSING_LABEL, 1, 0);
		crTableModel.setValueAt(Constants.MISSING_LABEL, 2, 0);
		crTableModel.setValueAt(Constants.MISSING_LABEL, 3, 0);
		crTableModel.setValueAt(0.0, 0, 1);
		crTableModel.setValueAt(0.0, 0, 2);
		crTableModel.setValueAt(0.0, 0, 3);
		crTableModel.setValueAt(0.0, 1, 1);
		crTableModel.setValueAt(0.0, 1, 2);
		crTableModel.setValueAt(0.0, 1, 3);
		crTableModel.setValueAt(0.0, 2, 1);
		crTableModel.setValueAt(0.0, 2, 2);
		crTableModel.setValueAt(0.0, 2, 3);
		crTableModel.setValueAt(0.0, 3, 1);
		crTableModel.setValueAt(0.0, 3, 2);
		crTableModel.setValueAt(0.0, 3, 3);
		crToolLengthSpinner.setValue(0);
		crAdjustFlag = false;
		acAdjustFlag = true;
		acTable.getColumnModel().getColumn(0).setHeaderValue(Constants.MISSING_LABEL);
		acTable.getColumnModel().getColumn(1).setHeaderValue(Constants.MISSING_LABEL);
		acTable.getColumnModel().getColumn(2).setHeaderValue(Constants.MISSING_LABEL);
		acTable.getColumnModel().getColumn(3).setHeaderValue(Constants.MISSING_LABEL);
		acTableModel.setValueAt(Constants.MISSING_LABEL, 0, 0);
		acTableModel.setValueAt(Constants.MISSING_LABEL, 1, 0);
		acTableModel.setValueAt(Constants.MISSING_LABEL, 2, 0);
		acTableModel.setValueAt(Constants.MISSING_LABEL, 3, 0);
		acTableModel.setValueAt(0.0, 0, 1);
		acTableModel.setValueAt(0.0, 0, 2);
		acTableModel.setValueAt(0.0, 0, 3);
		acTableModel.setValueAt(0.0, 1, 1);
		acTableModel.setValueAt(0.0, 1, 2);
		acTableModel.setValueAt(0.0, 1, 3);
		acTableModel.setValueAt(0.0, 2, 1);
		acTableModel.setValueAt(0.0, 2, 2);
		acTableModel.setValueAt(0.0, 2, 3);
		acTableModel.setValueAt(0.0, 3, 1);
		acTableModel.setValueAt(0.0, 3, 2);
		acTableModel.setValueAt(0.0, 3, 3);
		acToolLengthSpinner.setValue(0);
		acAdjustFlag = false;
		datumRaAdjustFlag = true;
		datumASpinner.setValue(0);
		datumCSpinner.setValue(0);
		datumRaAdjustFlag = false;
		meaRaAdjustFlag = true;
		meaASpinner.setValue(0);
		meaCSpinner.setValue(0);
		meaRaAdjustFlag = false;
		meaDirAdjustFlag = true;
		meaDirectionGroup.setSelected(meaDirectionX.getModel(), true);
		meaDirAdjustFlag = false;
		meaErrTextField.setText(Double.toString(0));

		if (Objects.isNull(modelManager)) {
			return;
		}

		i18nHandler.getLock().readLock().lock();
		try {
			this.setTitle(i18nString(I18nKey.LABEL_1));
			lblBanner1.setText(i18nString(I18nKey.LABEL_11));
			lblBanner2.setText(i18nString(I18nKey.LABEL_12));
			lblBanner3.setText(i18nString(I18nKey.LABEL_13));
			lblBanner4.setText(i18nString(I18nKey.LABEL_14));
			lblBanner5.setText(i18nString(I18nKey.LABEL_15));
			lblBanner6.setText(i18nString(I18nKey.LABEL_12));
			lblBanner7.setText(i18nString(I18nKey.LABEL_13));
			lblBanner8.setText(i18nString(I18nKey.LABEL_16));
			lblBanner9.setText(i18nString(I18nKey.LABEL_17));
			lblBanner10.setText(i18nString(I18nKey.LABEL_18));
			lblBanner11.setText(i18nString(I18nKey.LABEL_19));
			lblBanner12.setText(i18nString(I18nKey.LABEL_10));
			lblBanner13.setText(i18nString(I18nKey.LABEL_10));
			lblBanner14.setText(i18nString(I18nKey.LABEL_20));
			btnRandomAc.setText(i18nString(I18nKey.LABEL_22));
			btnMeasure.setText(i18nString(I18nKey.LABEL_23));
			btnUseExperience.setText(i18nString(I18nKey.LABEL_24));
			btnShowAc.setText(i18nString(I18nKey.LABEL_21));
			meaDirectionX.setText(i18nString(I18nKey.LABEL_3));
			meaDirectionY.setText(i18nString(I18nKey.LABEL_4));
			meaDirectionZ.setText(i18nString(I18nKey.LABEL_5));
			crTable.getColumnModel().getColumn(0).setHeaderValue(i18nString(I18nKey.LABEL_2));
			crTable.getColumnModel().getColumn(1).setHeaderValue(i18nString(I18nKey.LABEL_3));
			crTable.getColumnModel().getColumn(2).setHeaderValue(i18nString(I18nKey.LABEL_4));
			crTable.getColumnModel().getColumn(3).setHeaderValue(i18nString(I18nKey.LABEL_5));
			crTableModel.setValueAt(i18nString(I18nKey.LABEL_6), 0, 0);
			crTableModel.setValueAt(i18nString(I18nKey.LABEL_7), 1, 0);
			crTableModel.setValueAt(i18nString(I18nKey.LABEL_8), 2, 0);
			crTableModel.setValueAt(i18nString(I18nKey.LABEL_9), 3, 0);
			acTable.getColumnModel().getColumn(0).setHeaderValue(i18nString(I18nKey.LABEL_2));
			acTable.getColumnModel().getColumn(1).setHeaderValue(i18nString(I18nKey.LABEL_3));
			acTable.getColumnModel().getColumn(2).setHeaderValue(i18nString(I18nKey.LABEL_4));
			acTable.getColumnModel().getColumn(3).setHeaderValue(i18nString(I18nKey.LABEL_5));
			acTableModel.setValueAt(i18nString(I18nKey.LABEL_6), 0, 0);
			acTableModel.setValueAt(i18nString(I18nKey.LABEL_7), 1, 0);
			acTableModel.setValueAt(i18nString(I18nKey.LABEL_8), 2, 0);
			acTableModel.setValueAt(i18nString(I18nKey.LABEL_9), 3, 0);
		} finally {
			i18nHandler.getLock().readLock().unlock();
		}

		currentRTCPParamModel.getLock().readLock().lock();
		try {
			crAdjustFlag = true;
			crTableModel.setValueAt(currentRTCPParamModel.getV00(), 0, 1);
			crTableModel.setValueAt(currentRTCPParamModel.getV01(), 0, 2);
			crTableModel.setValueAt(currentRTCPParamModel.getV02(), 0, 3);
			crTableModel.setValueAt(currentRTCPParamModel.getV10(), 1, 1);
			crTableModel.setValueAt(currentRTCPParamModel.getV11(), 1, 2);
			crTableModel.setValueAt(currentRTCPParamModel.getV12(), 1, 3);
			crTableModel.setValueAt(currentRTCPParamModel.getV20(), 2, 1);
			crTableModel.setValueAt(currentRTCPParamModel.getV21(), 2, 2);
			crTableModel.setValueAt(currentRTCPParamModel.getV22(), 2, 3);
			crTableModel.setValueAt(currentRTCPParamModel.getV30(), 3, 1);
			crTableModel.setValueAt(currentRTCPParamModel.getV31(), 3, 2);
			crTableModel.setValueAt(currentRTCPParamModel.getV32(), 3, 3);
			crToolLengthSpinner.setValue(currentRTCPParamModel.getToolLength());
			crAdjustFlag = false;
		} finally {
			currentRTCPParamModel.getLock().readLock().unlock();
		}

		actualRTCPParamModel.getLock().readLock().lock();
		try {
			acAdjustFlag = true;
			acTableModel.setValueAt(actualRTCPParamModel.getV00(), 0, 1);
			acTableModel.setValueAt(actualRTCPParamModel.getV01(), 0, 2);
			acTableModel.setValueAt(actualRTCPParamModel.getV02(), 0, 3);
			acTableModel.setValueAt(actualRTCPParamModel.getV10(), 1, 1);
			acTableModel.setValueAt(actualRTCPParamModel.getV11(), 1, 2);
			acTableModel.setValueAt(actualRTCPParamModel.getV12(), 1, 3);
			acTableModel.setValueAt(actualRTCPParamModel.getV20(), 2, 1);
			acTableModel.setValueAt(actualRTCPParamModel.getV21(), 2, 2);
			acTableModel.setValueAt(actualRTCPParamModel.getV22(), 2, 3);
			acTableModel.setValueAt(actualRTCPParamModel.getV30(), 3, 1);
			acTableModel.setValueAt(actualRTCPParamModel.getV31(), 3, 2);
			acTableModel.setValueAt(actualRTCPParamModel.getV32(), 3, 3);
			acToolLengthSpinner.setValue(actualRTCPParamModel.getToolLength());
			acAdjustFlag = false;
		} finally {
			actualRTCPParamModel.getLock().readLock().unlock();
		}

		datumRotateAxisModel.getLock().readLock().lock();
		try {
			datumRaAdjustFlag = true;
			datumASpinner.setValue(datumRotateAxisModel.getA());
			datumCSpinner.setValue(datumRotateAxisModel.getC());
			datumRaAdjustFlag = false;
		} finally {
			datumRotateAxisModel.getLock().readLock().unlock();
		}

		measureRotateAxisModel.getLock().readLock().lock();
		try {
			meaRaAdjustFlag = true;
			meaASpinner.setValue(measureRotateAxisModel.getA());
			meaCSpinner.setValue(measureRotateAxisModel.getC());
			meaRaAdjustFlag = false;
		} finally {
			measureRotateAxisModel.getLock().readLock().unlock();
		}

		measureDirectionModel.getLock().readLock().lock();
		try {
			switch (measureDirectionModel.get()) {
			case X:
				meaDirectionGroup.setSelected(meaDirectionX.getModel(), true);
				break;
			case Y:
				meaDirectionGroup.setSelected(meaDirectionY.getModel(), true);
				break;
			case Z:
				meaDirectionGroup.setSelected(meaDirectionZ.getModel(), true);
				break;
			default:
				meaDirectionGroup.setSelected(meaDirectionX.getModel(), true);
				break;
			}
		} finally {
			measureDirectionModel.getLock().readLock().unlock();
		}

		measureErrorModel.getLock().readLock().lock();
		try {
			meaErrTextField.setText(Double.toString(measureErrorModel.get()));
		} finally {
			measureErrorModel.getLock().readLock().unlock();
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

	private boolean checkDuplexingForecast(Queue<Object[]> duplexingForecast, Object[] objs) {
		if (duplexingForecast.isEmpty()) {
			return false;
		}

		if (Arrays.equals(duplexingForecast.peek(), objs)) {
			duplexingForecast.poll();
			return true;
		} else {
			syncModel();
			duplexingForecast.clear();
			return false;
		}
	}

	private static final class JSpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = 5255950337111713064L;

		private final JSpinner spinner;

		public JSpinnerCellEditor() {
			super();
			spinner = new JPreciseSpinner(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getCellEditorValue() {
			try {
				spinner.commitEdit();
			} catch (ParseException ignore) {
				// Do nothing.
			}
			return spinner.getValue();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			spinner.setValue(value);
			return spinner;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isCellEditable(EventObject e) {
			if (e instanceof MouseEvent) {
				MouseEvent mouseEvent = (MouseEvent) e;
				return mouseEvent.getClickCount() == 2;
			}
			return true;
		}

	}

	private static final class JamTableModel extends DefaultTableModel implements TableModel {

		private static final long serialVersionUID = -410778778536337054L;

		private final String[] descArr = new String[4];
		private final double[][] rtcpArr = new double[3][4];

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getRowCount() {
			return 4;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getColumnCount() {
			return 4;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return column > 0;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getValueAt(int row, int column) {
			if (column == 0) {
				return descArr[row];
			} else {
				return rtcpArr[column - 1][row];
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setValueAt(Object aValue, int row, int column) throws IllegalArgumentException {
			if (column == 0) {
				if (!(aValue instanceof String))
					throw new IllegalArgumentException("参数类型应该为 String，当前:" + aValue.getClass().getName());
				descArr[row] = (String) aValue;
			} else {
				if (!(aValue instanceof Double))
					throw new IllegalArgumentException("参数类型应该为 Double，当前:" + aValue.getClass().getName());
				rtcpArr[column - 1][row] = (double) aValue;
			}
			fireTableCellUpdated(row, column);
		}
	}

}
