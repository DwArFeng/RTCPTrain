package com.dwarfeng.rtcptrain.util;

import static org.junit.Assert.assertEquals;

import org.apache.commons.math.linear.RealMatrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransformUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testShiftMatrix() {
		RealMatrix matrix = TransformUtil.shiftMatrix(1.7, 1.2, 1.5);
		assertEquals(1, matrix.getEntry(0, 0), 0.00001);
		assertEquals(0, matrix.getEntry(0, 1), 0.00001);
		assertEquals(0, matrix.getEntry(0, 2), 0.00001);
		assertEquals(1.7, matrix.getEntry(0, 3), 0.00001);
		assertEquals(0, matrix.getEntry(1, 0), 0.00001);
		assertEquals(1, matrix.getEntry(1, 1), 0.00001);
		assertEquals(0, matrix.getEntry(1, 2), 0.00001);
		assertEquals(1.2, matrix.getEntry(1, 3), 0.00001);
		assertEquals(0, matrix.getEntry(2, 0), 0.00001);
		assertEquals(0, matrix.getEntry(2, 1), 0.00001);
		assertEquals(1, matrix.getEntry(2, 2), 0.00001);
		assertEquals(1.5, matrix.getEntry(2, 3), 0.00001);
	}

	@Test
	public final void testRotateMatrix() {
		RealMatrix matrix = TransformUtil.rotateMatrix(1.7, 1.2, 1.5, 1.8);
		assertEquals(0.1057206, matrix.getEntry(0, 0), 0.00001);
		assertEquals(-0.3848578, matrix.getEntry(0, 1), 0.00001);
		assertEquals(0.9169011, matrix.getEntry(0, 2), 0.00001);
		assertEquals(0.9712705, matrix.getEntry(1, 0), 0.00001);
		assertEquals(0.2376635, matrix.getEntry(1, 1), 0.00001);
		assertEquals(-0.0122332, matrix.getEntry(1, 2), 0.00001);
		assertEquals(-0.2132058, matrix.getEntry(2, 0), 0.00001);
		assertEquals(0.8918523, matrix.getEntry(2, 1), 0.00001);
		assertEquals(0.398927, matrix.getEntry(2, 2), 0.00001);
	}

	@Test
	public final void testNorm() {
		assertEquals(5, TransformUtil.norm(3, 4), 0.001);
	}

	@Test
	public final void testRtcpMachine2Tool() {
		RealMatrix matrix = TransformUtil.rtcpMachine2Tool(10, 9, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0.5, 1.5,
				110);
		assertEquals(-88.135865, matrix.getEntry(0, 0), 0.00001);
		assertEquals(-50.266424, matrix.getEntry(1, 0), 0.00001);
		assertEquals(-52.506456, matrix.getEntry(2, 0), 0.00001);
		assertEquals(1, matrix.getEntry(3, 0), 0.00001);
	}

	@Test
	public final void testRtcpTool2Machine() {
		RealMatrix matrix = TransformUtil.rtcpTool2Machine(-88.135865, -50.266424, -52.506456, 1, 2, 3, 4, 5, 6, 7, 8,
				9, 10, 11, 12, 0.5, 1.5, 110);
		assertEquals(10, matrix.getEntry(0, 0), 0.00001);
		assertEquals(9, matrix.getEntry(1, 0), 0.00001);
		assertEquals(8, matrix.getEntry(2, 0), 0.00001);
		assertEquals(1, matrix.getEntry(3, 0), 0.00001);
	}

	@Test
	public final void testRtcpError() {
		RealMatrix matrix = TransformUtil.rtcpError(0.1, -0.1, 1, 0.2, 0.05, 0, 1, 0.01, 0.02, 0, -0.3, 450.2, 0, 0, 1,
				0, 0, 0, 1, 0, 0, 0, 0, 450, 0, 0, 1.7, 1.8, 150, 155);
		assertEquals(9.8354134, matrix.getEntry(0, 0), 0.00001);
		assertEquals(-27.449834, matrix.getEntry(1, 0), 0.00001);
		assertEquals(-25.73196, matrix.getEntry(2, 0), 0.00001);
	}

}
