/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
/**
 * This class implements VersionBase. A version of the population queries that
 * uses a single thread to find boundaries of population map and find
 * sub_population of a query
 * 
 */
public class VersionOne implements VersionBase {
	private CensusData file;
	private int column;
	private int row;
	private Rectangle target;
	private int totalpopulation;

	/**
	 * Construct a new Version One process with given file,columns, rows and
	 * target query
	 * 
	 * @param file
	 *            data to go analyze
	 * @param x
	 *            number of columns to divide map
	 * @param y
	 *            number of rows to divide map
	 * @param target
	 *            target query to find population in
	 */
	public VersionOne(CensusData file, int x, int y, Rectangle target) {
		this.file = file;
		column = x;
		row = y;
		this.target = target;
		totalpopulation = 0;
	}

	/**
	 * Find and return four boundaries of whole map
	 */
	@ Override
	public Rectangle scaleBoundary() {
		CensusGroup[] grid = file.data;
		totalpopulation = grid[0].population;
		float maxLatitude = grid[0].latitude;
		float maxLongitude = grid[0].longitude;
		float minLatitude = grid[0].latitude;
		float minLongitude = grid[0].longitude;
		for (int i = 1; i <= file.data_size - 1; i++) {
			totalpopulation += grid[i].population;
			maxLatitude = Math.max(grid[i].latitude, maxLatitude);
			maxLongitude = Math.max(grid[i].longitude, maxLongitude);
			minLatitude = Math.min(grid[i].latitude, minLatitude);
			minLongitude = Math.min(grid[i].longitude, minLongitude);
		}

		return new Rectangle(minLongitude, maxLongitude, maxLatitude,
				minLatitude, totalpopulation);
	}

	/**
	 * Calculate population inside target query and returns this population
	 */
	@ Override
	public int getTargetPopulation() {
		Rectangle bound = scaleBoundary();
		int targetPopulation = 0;
		for (int i = 0; i <= file.data_size - 1; i++) {
			CensusGroup grid = file.data[i];
			float lon = grid.longitude;
			float lag = grid.latitude;
			float west = ((target.left - 1) / column)
					* (bound.right - bound.left) + bound.left;
			float east = (target.right / column) * (bound.right - bound.left)
					+ bound.left;
			float south = ((target.bottom - 1) / row)
					* (bound.top - bound.bottom) + bound.bottom;
			float north = (target.top / row) * (bound.top - bound.bottom)
					+ bound.bottom;
			if (lag <= north && lag >= south && lon <= east && lon >= west) {
				targetPopulation += grid.population;
			}
		}
		return targetPopulation;
	}

	/**
	 * Return a pair includes population in target query and percent of total
	 * population
	 * 
	 * @return pair includes population in target query and percent of total
	 *         population
	 */
	public Pair<Integer, Float> getPair() {
		float percentage = (float) getTargetPopulation()
				/ (float) scaleBoundary().population * 100;
		return new Pair<Integer, Float>((int) getTargetPopulation(), percentage);

	}
}