package com.dwarfeng.rtcptrain.view.task;

import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;
import com.dwarfeng.rtcptrain.util.Constants;

public class SetDatumRaTask extends AbstractViewTask {

	private final int index;
	private final double newValue;

	public SetDatumRaTask(ModelManager modelManager, ActionManager actionManager, int index, double newValue) {
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
		case Constants.ACTION_INDEX_DATAUM_ROTATE_AXIS_A:
			actionManager.setDatumRotateAxisA(newValue);
			break;
		case Constants.ACTION_INDEX_DATAUM_ROTATE_AXIS_C:
			actionManager.setDatumRotateAxisC(newValue);
			break;
		}
	}

}
