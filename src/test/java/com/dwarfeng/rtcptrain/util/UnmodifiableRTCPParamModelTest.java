package com.dwarfeng.rtcptrain.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dwarfeng.rtcptrain.model.DefaultRTCPParamModel;
import com.dwarfeng.rtcptrain.model.RTCPParamModel;
import com.dwarfeng.rtcptrain.model.TestRTCPParamObverser;

public class UnmodifiableRTCPParamModelTest {

	private static RTCPParamModel model;
	private static TestRTCPParamObverser obv;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		model = new DefaultRTCPParamModel(0, 1, 2, 10, 11, 12, 20, 21, 22, 30, 31, 32, 150);
		obv = new TestRTCPParamObverser();
		model.addObverser(obv);
		model = ModelUtil.unmodifiableRTCPParamModel(model);
	}

	@After
	public void tearDown() throws Exception {
		model = null;
		obv = null;
	}

	@Test
	public final void testGetV00() {
		assertEquals(0, model.getV00(), 0.001);
	}

	@Test
	public final void testGetV01() {
		assertEquals(1, model.getV01(), 0.001);
	}

	@Test
	public final void testGetV02() {
		assertEquals(2, model.getV02(), 0.001);
	}

	@Test
	public final void testGetV10() {
		assertEquals(10, model.getV10(), 0.001);
	}

	@Test
	public final void testGetV11() {
		assertEquals(11, model.getV11(), 0.001);
	}

	@Test
	public final void testGetV12() {
		assertEquals(12, model.getV12(), 0.001);
	}

	@Test
	public final void testGetV20() {
		assertEquals(20, model.getV20(), 0.001);
	}

	@Test
	public final void testGetV21() {
		assertEquals(21, model.getV21(), 0.001);
	}

	@Test
	public final void testGetV22() {
		assertEquals(22, model.getV22(), 0.001);
	}

	@Test
	public final void testGetV30() {
		assertEquals(30, model.getV30(), 0.001);
	}

	@Test
	public final void testGetV31() {
		assertEquals(31, model.getV31(), 0.001);
	}

	@Test
	public final void testGetV32() {
		assertEquals(32, model.getV32(), 0.001);
	}

	@Test
	public final void testGetToolLength() {
		assertEquals(150, model.getToolLength(), 0.001);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV00() {
		assertTrue(model.setV00(-0));
		assertEquals(1, obv.getV00ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV01() {
		assertTrue(model.setV01(-1));
		assertEquals(1, obv.getV01ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV02() {
		assertTrue(model.setV02(-2));
		assertEquals(1, obv.getV02ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV10() {
		assertTrue(model.setV10(-10));
		assertEquals(1, obv.getV10ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV11() {
		assertTrue(model.setV11(-11));
		assertEquals(1, obv.getV11ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV12() {
		assertTrue(model.setV12(-12));
		assertEquals(1, obv.getV12ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV20() {
		assertTrue(model.setV20(-20));
		assertEquals(1, obv.getV20ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV21() {
		assertTrue(model.setV21(-21));
		assertEquals(1, obv.getV21ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV22() {
		assertTrue(model.setV22(-22));
		assertEquals(1, obv.getV22ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV30() {
		assertTrue(model.setV30(-30));
		assertEquals(1, obv.getV30ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV31() {
		assertTrue(model.setV31(-31));
		assertEquals(1, obv.getV31ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetV32() {
		assertTrue(model.setV32(-32));
		assertEquals(1, obv.getV32ChangedCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetToolLength() {
		assertTrue(model.setToolLength(-150));
		assertEquals(1, obv.getToolLengthChangedCount());
		fail("没有抛出异常");
	}

}
