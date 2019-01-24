package com.dwarfeng.rtcptrain.model;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

/**
 * 回转轴模型。
 * 
 * <p>
 * 该模型是外部线程安全的。
 * 
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface SyncRotateAxisModel extends RotateAxisModel, ExternalReadWriteThreadSafe {

}
