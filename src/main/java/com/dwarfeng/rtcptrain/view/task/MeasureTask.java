package com.dwarfeng.rtcptrain.view.task;

import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;

public class MeasureTask extends AbstractViewTask {

	public MeasureTask(ModelManager modelManager, ActionManager actionManager) {
		super(modelManager, actionManager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void todo() throws Exception {
		actionManager.measureError();
	}

}
