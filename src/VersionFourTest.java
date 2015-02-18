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

public class VersionFourTest {

	private Pair<Integer, Float> v4Pair;
	private VersionFour v4;
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

	/** Test scaleBoundary, getTargetPopulation, and getPair in VersionFour */

	@Test
	public void testWholeMapVersionFour() {
		target = new Rectangle(1, 100, 500, 1);
		v4 = new VersionFour(file, 100, 500, target);
		v4Pair = v4.getPair();
		int targetPop = v4Pair.getElementA();
		float percent = v4Pair.getElementB();
		float expected = 100;
		assertEquals(312471327, targetPop);
		assertTrue(expected == percent);
	}

	@Test
	public void testHalfMapVersionFour() {
		target = new Rectangle(1, 50, 500, 1);
		v4 = new VersionFour(file, 100, 500, target);
		v4Pair = v4.getPair();
		int targetPop = v4Pair.getElementA();
		float percent = v4Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(8.90);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(27820072, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void generalCase1VersionFour() {
		target = new Rectangle(1, 5, 4, 1);
		v4 = new VersionFour(file, 20, 25, target);
		v4Pair = v4.getPair();
		int targetPop = v4Pair.getElementA();
		float percent = v4Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(0.44);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(1360301, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void generalCase2VersionFour() {
		target = new Rectangle(1, 9, 25, 12);
		v4 = new VersionFour(file, 20, 25, target);
		v4Pair = v4.getPair();
		int targetPop = v4Pair.getElementA();
		float percent = v4Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(0.23);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(710231, targetPop);
		assertEquals(expected, percentage);
	}

	@Test
	public void generalCase3VersionFour() {
		target = new Rectangle(9, 20, 13, 1);
		v4 = new VersionFour(file, 20, 25, target);
		v4Pair = v4.getPair();
		int targetPop = v4Pair.getElementA();
		float percent = v4Pair.getElementB();
		BigDecimal a = new BigDecimal(percent);
		BigDecimal percentage = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal b = new BigDecimal(99.34);
		BigDecimal expected = b.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		assertEquals(310400795, targetPop);
		assertEquals(expected, percentage);
	}
}