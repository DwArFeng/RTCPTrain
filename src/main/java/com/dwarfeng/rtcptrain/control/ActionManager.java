package com.dwarfeng.rtcptrain.control;

import com.dwarfeng.dutil.develop.backgr.Task;
import com.dwarfeng.rtcptrain.model.enumeration.MeasureDirection;

/**
 * 程序的动作处理器。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ActionManager {

	// --------------------------------------------程序控制--------------------------------------------
	/**
	 * 启动程序。
	 * 
	 * <p>
	 * 通过该方法使用指定的参数启动程序。该方法只允许调用一次，多次调用会抛出异常。
	 * 
	 * @param args 指定的参数。
	 * @throws IllegalStateException 程序已经启动。
	 * @throws NullPointerException  指定的入口参数为 <code> null </code>。
	 */
	public void start(String[] args) throws IllegalStateException, NullPointerException;

	/**
	 * 退出程序。
	 * 
	 * @throws IllegalStateException 程序尚未启动或者已经退出。
	 */
	public void exit() throws IllegalStateException;

	// --------------------------------------------程序动作--------------------------------------------
	/**
	 * 
	 * @param task
	 * @throws NullPointerException
	 */
	public void submit(Task task) throws NullPointerException;

	public void setCurrentRTCPParamV00(double newValue);

	public void setCurrentRTCPParamV01(double newValue);

	public void setCurrentRTCPParamV02(double newValue);

	public void setCurrentRTCPParamV10(double newValue);

	public void setCurrentRTCPParamV11(double newValue);

	public void setCurrentRTCPParamV12(double newValue);

	public void setCurrentRTCPParamV20(double newValue);

	public void setCurrentRTCPParamV21(double newValue);

	public void setCurrentRTCPParamV22(double newValue);

	public void setCurrentRTCPParamV30(double newValue);

	public void setCurrentRTCPParamV31(double newValue);

	public void setCurrentRTCPParamV32(double newValue);

	public void setCurrentRTCPParamToolLength(double newValue);

	public void setActualRTCPParamV00(double newValue);

	public void setActualRTCPParamV01(double newValue);

	public void setActualRTCPParamV02(double newValue);

	public void setActualRTCPParamV10(double newValue);

	public void setActualRTCPParamV11(double newValue);

	public void setActualRTCPParamV12(double newValue);

	public void setActualRTCPParamV20(double newValue);

	public void setActualRTCPParamV21(double newValue);

	public void setActualRTCPParamV22(double newValue);

	public void setActualRTCPParamV30(double newValue);

	public void setActualRTCPParamV31(double newValue);

	public void setActualRTCPParamV32(double newValue);

	public void setActualRTCPParamToolLength(double newValue);

	public void setDatumRotateAxisA(double newValue);

	public void setDatumRotateAxisC(double newValue);

	public void setMeasureRotateAxisA(double newValue);

	public void setMeasureRotateAxisC(double newValue);

	public void setMeasureDirection(MeasureDirection direction);

	public void randomActualRTCPParam();

	public void useExperience();

	public void measureError();

	// --------------------------------------------日志输出--------------------------------------------
	/**
	 * 
	 * @param message
	 * @throws NullPointerException
	 */
	public void trace(String message) throws NullPointerException;

	/**
	 * 
	 * @param message
	 * @throws NullPointerException
	 */
	public void debug(String message) throws NullPointerException;

	/**
	 * 
	 * @param message
	 * @throws NullPointerException
	 */
	public void info(String message) throws NullPointerException;

	/**
	 * 
	 * @param message
	 * @throws NullPointerException
	 */
	public void warn(String message) throws NullPointerException;

	/**
	 * 
	 * @param message
	 * @param t
	 * @throws NullPointerException
	 */
	public void warn(String message, Throwable t) throws NullPointerException;

	/**
	 * 
	 * @param message
	 * @param t
	 * @throws NullPointerException
	 */
	public void error(String message, Throwable t) throws NullPointerException;

	/**
	 * 
	 * @param message
	 * @param t
	 * @throws NullPointerException
	 */
	public void fatal(String message, Throwable t) throws NullPointerException;

}
