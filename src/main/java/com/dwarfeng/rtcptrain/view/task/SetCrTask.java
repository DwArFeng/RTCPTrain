package com.dwarfeng.rtcptrain.view.task;

import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;

public class SetCrTask extends AbstractViewTask {

	private final int index;
	private final double newValue;

	public SetCrTask(ModelManager modelManager, ActionManager actionManager, int index, double newValue) {
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
		// V0
		case 0:
			actionManager.setCurrentRTCPParamV00(newValue);
			break;
		case 1:
			actionManager.setCurrentRTCPParamV01(newValue);
			break;
		case 2:
			actionManager.setCurrentRTCPParamV02(newValue);
			break;
		// V1
		case 10:
			actionManager.setCurrentRTCPParamV10(newValue);
			break;
		case 11:
			actionManager.setCurrentRTCPParamV11(newValue);
			break;
		case 12:
			actionManager.setCurrentRTCPParamV12(newValue);
			break;
		// V2
		case 20:
			actionManager.setCurrentRTCPParamV20(newValue);
			break;
		case 21:
			actionManager.setCurrentRTCPParamV21(newValue);
			break;
		case 22:
			actionManager.setCurrentRTCPParamV22(newValue);
			break;
		// V3
		case 30:
			actionManager.setCurrentRTCPParamV30(newValue);
			break;
		case 31:
			actionManager.setCurrentRTCPParamV31(newValue);
			break;
		case 32:
			actionManager.setCurrentRTCPParamV32(newValue);
			break;
		// Tool length
		case 150:
			actionManager.setCurrentRTCPParamToolLength(newValue);
			break;
		}
	}

}
