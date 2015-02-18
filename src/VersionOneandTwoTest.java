/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

/**
 * This is a test for both version one and version two.
 */
public class VersionOneandTwoTest {

	private Pair<Integer, Float> v1Pair;
	private Pair<Integer, Float> v2Pair;
	private VersionOne v1;
	private VersionTwo v2;
	private CensusData file;
	private Rectangle target;

	@Before
	public void setup() throws NumberFormatException, IOException {
		CensusData result = new CensusData();
		BufferedReader fileIn = new BufferedReader(new FileReader(
				"src/CenPop2010.txt"));
		String oneLine = fileIn.readLine(); // skip the first line

		// read each subsequent line and add relevant data to a big array
		while ((oneLine = fileIn.readLine()) != null) {
			String[] tokens = oneLine.split(",");
			if (tokens.length != PopulationQuery.TOKENS_PER_LINE)
				throw new NumberFormatException();
			int population = Integer
					.parseInt(tokens[PopulationQuery.POPULATION_INDEX]);
			if (population != 0)
				result.add(
						population,
						Float.parseFloat(tokens[PopulationQuery.LATITUDE_INDEX]),
						Float.parseFloat(tokens[PopulationQuery.LONGITUDE_INDEX]));
		}

		fileIn.close();

		file = result;

	}

	/** Test scaleBoundary, getTargetPopulation, and getPair in VersionOne */
	@Test
	public void testWholeMapVersionOne() {
		target = new Rectangle(1, 100, 500, 1);
		v1 = new VersionOne(file, 100, 500, target);
		v1Pair = v1.getPair();
		int targetPop = v1Pair.getElementA();
		float percent = v1Pair.getElementB();
		float expected = 100;
		assertEquals(312471327, targetPop);
		assertTrue(expected == percent);
	}

	@Test
	public void testHalfMapVersionOne() {
		target = new Rectangle(1, 50, 500, 1);
		v1 = new VersionOne(file, 100, 500, target);
		v1Pair = v1.getPair();
		int targetPop = v1Pair.getElementA();
		float percent = v1Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(8.90);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(27820072, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void generalCase1VersionOne() {
		target = new Rectangle(1, 5, 4, 1);
		v1 = new VersionOne(file, 20, 25, target);
		v1Pair = v1.getPair();
		int targetPop = v1Pair.getElementA();
		float percent = v1Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(0.44);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(1360301, targetPop);
		assertEquals(expected, percentage);
	}

	/** Test scaleBoundary, getTargetPopulation, and getPair in VersionTwo */
	@Test
	public void generalCase2VersionOne() {
		target = new Rectangle(1, 9, 25, 12);
		v1 = new VersionOne(file, 20, 25, target);
		v1Pair = v1.getPair();
		int targetPop = v1Pair.getElementA();
		float percent = v1Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(0.23);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(710231, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void generalCase3VersionOne() {
		target = new Rectangle(9, 20, 13, 1);
		v1 = new VersionOne(file, 20, 25, target);
		v1Pair = v1.getPair();
		int targetPop = v1Pair.getElementA();
		float percent = v1Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(99.34);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(310400795, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void testWholeMapVersionTwo() {
		target = new Rectangle(1, 100, 500, 1);
		v2 = new VersionTwo(file, 100, 500, target);
		v2Pair = v2.getPair();
		int targetPop = v2Pair.getElementA();
		float percent = v2Pair.getElementB();
		float expected = 100;
		assertEquals(312471327, targetPop);
		assertTrue(expected == percent);
	}

	@Test
	public void testHalfMapVersionTwo() {
		target = new Rectangle(1, 50, 500, 1);
		v2 = new VersionTwo(file, 100, 500, target);
		v2Pair = v2.getPair();
		int targetPop = v2Pair.getElementA();
		float percent = v2Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(8.90);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(27820072, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void generalCase1VersionTwo() {
		target = new Rectangle(1, 5, 4, 1);
		v2 = new VersionTwo(file, 20, 25, target);
		v2Pair = v2.getPair();
		int targetPop = v2Pair.getElementA();
		float percent = v2Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(0.44);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(1360301, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void generalCase2VersionTwo() {
		target = new Rectangle(1, 9, 25, 12);
		v2 = new VersionTwo(file, 20, 25, target);
		v2Pair = v2.getPair();
		int targetPop = v2Pair.getElementA();
		float percent = v2Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(0.23);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(710231, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void generalCase3VersionTwo() {
		target = new Rectangle(9, 20, 13, 1);
		v2 = new VersionTwo(file, 20, 25, target);
		v2Pair = v2.getPair();
		int targetPop = v2Pair.getElementA();
		float percent = v2Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(99.34);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(310400795, targetPop);
		assertEquals(expected, percentage);
	}

}