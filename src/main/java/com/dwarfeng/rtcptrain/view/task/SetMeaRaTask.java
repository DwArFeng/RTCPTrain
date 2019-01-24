package com.dwarfeng.rtcptrain.view.task;

import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;
import com.dwarfeng.rtcptrain.util.Constants;

public class SetMeaRaTask extends AbstractViewTask {

	private final int index;
	private final double newValue;

	public SetMeaRaTask(ModelManager modelManager, ActionManager actionManager, int index, double newValue) {
		super(modelManager, actionManager);
		this.index = index;
		this.newValue = newValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void todo() throws Exception {
		switch (index) {
		case Constants.ACTION_INDEX_MEASURE_ROTATE_AXIS_A:
			actionManager.setMeasureRotateAxisA(newValue);
			break;
		case Constants.ACTION_INDEX_MEASURE_ROTATE_AXIS_C:
			actionManager.setMeasureRotateAxisC(newValue);
			break;
		}
	}

}
