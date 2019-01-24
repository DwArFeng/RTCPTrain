package com.dwarfeng.rtcptrain.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dwarfeng.rtcptrain.model.DefaultRotateAxisModel;
import com.dwarfeng.rtcptrain.model.RotateAxisModel;
import com.dwarfeng.rtcptrain.model.TestRoatetAxisObverser;

public class UnmodifiableRotateAxisModelTest {

	private static RotateAxisModel model;
	private static TestRoatetAxisObverser obv;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		model = new DefaultRotateAxisModel(90, 180);
		obv = new TestRoatetAxisObverser();
		model.addObverser(obv);
		model = ModelUtil.unmodifiableRotateAxisModel(model);
	}

	@After
	public void tearDown() throws Exception {
		model = null;
		obv = null;
	}

	@Test
	public final void testGetA() {
		assertEquals(90, model.getA(), 0.001);
	}

	@Test
	public final void testGetC() {
		assertEquals(180, model.getC(), 0.001);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetA() {
		assertTrue(model.setA(-90));
		assertEquals(1, obv.getAChangeCount());
		fail("没有抛出异常");
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testSetC() {
		assertTrue(model.setC(-180));
		assertEquals(1, obv.getCChangeCount());
		fail("没有抛出异常");
	}

}
