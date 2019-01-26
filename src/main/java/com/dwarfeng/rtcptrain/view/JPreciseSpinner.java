package com.dwarfeng.rtcptrain.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class JPreciseSpinner extends JSpinner {

	private static final long serialVersionUID = -9018685638942412973L;

	public JPreciseSpinner() {
		super();
	}

	public JPreciseSpinner(SpinnerModel model) {
		super(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected JComponent createEditor(SpinnerModel model) {
		if (model instanceof SpinnerDateModel) {
			return new DateEditor(this);
		} else if (model instanceof SpinnerListModel) {
			return new ListEditor(this);
		} else if (model instanceof SpinnerNumberModel) {
			return new PreciseNumberEditor(this);
		} else {
			return new DefaultEditor(this);
		}
	}

	public static class PreciseNumberEditor extends NumberEditor {

		private static final long serialVersionUID = -2905165200386400194L;

		// Upper limit on integer and fraction digits for a Java double
		static final int DOUBLE_INTEGER_DIGITS = 309;
		static final int DOUBLE_FRACTION_DIGITS = 340;

		public PreciseNumberEditor(JSpinner spinner) {
			super(spinner);
			DecimalFormat format = new DecimalFormat();
			// format.setMinimumFractionDigits(0);
			format.setMaximumFractionDigits(DOUBLE_FRACTION_DIGITS);
			// format.setMinimumIntegerDigits(0);
			format.setMaximumIntegerDigits(DOUBLE_INTEGER_DIGITS);
			format.setDecimalSeparatorAlwaysShown(false);
			SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
			NumberFormatter formatter = new NumberEditorFormatter(model, format);
			DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
			JFormattedTextField ftf = getTextField();
			ftf.setFormatterFactory(factory);
		}

	}

	/**
	 * This subclass of javax.swing.NumberFormatter maps the minimum/maximum
	 * properties to a SpinnerNumberModel and initializes the valueClass of the
	 * NumberFormatter to match the type of the initial models value.
	 */
	private static class NumberEditorFormatter extends NumberFormatter {

		private static final long serialVersionUID = -7367382641730099603L;

		private final SpinnerNumberModel model;

		NumberEditorFormatter(SpinnerNumberModel model, NumberFormat format) {
			super(format);
			this.model = model;
			setValueClass(model.getValue().getClass());
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("rawtypes")
		@Override
		public void setMinimum(Comparable min) {
			model.setMinimum(min);
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("rawtypes")
		@Override
		public Comparable getMinimum() {
			return model.getMinimum();
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("rawtypes")
		@Override
		public void setMaximum(Comparable max) {
			model.setMaximum(max);
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("rawtypes")
		@Override
		public Comparable getMaximum() {
			return model.getMaximum();
		}
	}

}
