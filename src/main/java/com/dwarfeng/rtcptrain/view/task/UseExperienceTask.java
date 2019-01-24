package com.dwarfeng.rtcptrain.view.task;

import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;

public class UseExperienceTask extends AbstractViewTask {

	public UseExperienceTask(ModelManager modelManager, ActionManager actionManager) {
		super(modelManager, actionManager);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void todo() throws Exception {
		actionManager.useExperience();
	}

}
