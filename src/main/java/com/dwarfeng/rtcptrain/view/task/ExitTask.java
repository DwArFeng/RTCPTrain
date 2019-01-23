package com.dwarfeng.rtcptrain.view.task;

import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;

public class ExitTask extends AbstractViewTask {

	public ExitTask(ModelManager modelManager, ActionManager actionManager) {
		super(modelManager, actionManager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void todo() throws Exception {
		actionManager.exit();
	}

}
