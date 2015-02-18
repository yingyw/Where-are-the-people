/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
/**
 * This class extends VersionFour and implements VersionBase. A version of the
 * population queries that use 2D array and multiple threads to find population
 * in each grid and calculate sub_population of query.
 * 
 */
public class VersionFive extends VersionFour implements VersionBase {

	private Rectangle target;
	private int[][] grids;
	private Object[][] lock;
	private int column;

	/**
	 * Construct a new Version Five process with given file,columns, rows and
	 * target query.
	 */
	public VersionFive(CensusData file, int x, int y, Rectangle target) {
		super(file, x, y, target);
		this.target = target;
		column = x;
		row = y;
		this.grids = new int[x][y];
		lock = new Object[x][y];
		ProcessGridPopThread pThread = new ProcessGridPopThread(0, column,
				grids, lock, file);
		try {
			grids = pThread.gridPop(grids);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Obtains the second grid which each grid contains the total population
	 * from the leftmost grid to the current grid.
	 */
	@Override
	public int getTargetPopulation() {

		grids = v3.gridSum(grids);
		int total = v3.getTargetPopulation(target);
		return total;
	}

	/**
	 * Return a pair includes population in target query and percent of total
	 * population
	 * 
	 * @return pair includes population in target query and percent of total
	 *         population
	 */
	@Override
	public Pair<Integer, Float> getPair() {
		int total = getTargetPopulation();
		float percentage = (float) total / (float) scaleBoundary().population
				* 100;
		return new Pair<Integer, Float>(total, percentage);

	}

}
