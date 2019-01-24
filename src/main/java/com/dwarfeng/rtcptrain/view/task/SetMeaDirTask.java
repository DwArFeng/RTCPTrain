package com.dwarfeng.rtcptrain.view.task;

import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;
import com.dwarfeng.rtcptrain.model.enumeration.MeasureDirection;
import com.dwarfeng.rtcptrain.util.Constants;

public class SetMeaDirTask extends AbstractViewTask {

	private final int index;

	public SetMeaDirTask(ModelManager modelManager, ActionManager actionManager, int index) {
		super(modelManager, actionManager);
		this.index = index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void todo() throws Exception {
		switch (index) {
		case Constants.ACTION_INDEX_MEASURE_DIRECTION_X:
			actionManager.setMeasureDirection(MeasureDirection.X);
			break;
		case Constants.ACTION_INDEX_MEASURE_DIRECTION_Y:
			actionManager.setMeasureDirection(MeasureDirection.Y);
			break;
		case Constants.ACTION_INDEX_MEASURE_DIRECTION_Z:
			actionManager.setMeasureDirection(MeasureDirection.Z);
			break;
		}
	}

}
