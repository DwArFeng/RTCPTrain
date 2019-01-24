package com.dwarfeng.rtcptrain.view.task;

import com.dwarfeng.rtcptrain.control.ActionManager;
import com.dwarfeng.rtcptrain.control.ModelManager;

public class SetAcTask extends AbstractViewTask {

	private final int index;
	private final double newValue;

	public SetAcTask(ModelManager modelManager, ActionManager actionManager, int index, double newValue) {
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
			actionManager.setActualRTCPParamV00(newValue);
			break;
		case 1:
			actionManager.setActualRTCPParamV01(newValue);
			break;
		case 2:
			actionManager.setActualRTCPParamV02(newValue);
			break;
		// V1
		case 10:
			actionManager.setActualRTCPParamV10(newValue);
			break;
		case 11:
			actionManager.setActualRTCPParamV11(newValue);
			break;
		case 12:
			actionManager.setActualRTCPParamV12(newValue);
			break;
		// V2
		case 20:
			actionManager.setActualRTCPParamV20(newValue);
			break;
		case 21:
			actionManager.setActualRTCPParamV21(newValue);
			break;
		case 22:
			actionManager.setActualRTCPParamV22(newValue);
			break;
		// V3
		case 30:
			actionManager.setActualRTCPParamV30(newValue);
			break;
		case 31:
			actionManager.setActualRTCPParamV31(newValue);
			break;
		case 32:
			actionManager.setActualRTCPParamV32(newValue);
			break;
		// Tool length
		case 150:
			actionManager.setActualRTCPParamToolLength(newValue);
		}
	}

}
