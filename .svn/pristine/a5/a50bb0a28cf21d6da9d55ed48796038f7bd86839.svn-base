/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * This class is a part of version two used to calculate population in required
 * query using parallel implementation traversal for each query .
 */
public class TargetPopulation extends RecursiveTask<Integer> {
	static final long serialVersionUID = 1L;
	public static ForkJoinPool fjPool = new ForkJoinPool();
	static final int SEQUENTIAL_THRESHOLD = 100;
	CensusData grid;
	int hi;
	int lo;
	Info info;

	/**
	 * Construct a new TargetPopulation
	 * 
	 * @param grid
	 *            data to analyze
	 * @param lo
	 *            lower bound for parallel calculating
	 * @param hi
	 *            higher bound for parallel calculating
	 * @param info object Info includes unchanged parameter
	 */
	public TargetPopulation(CensusData grid, int lo, int hi, Info info) {
		this.grid = grid;
		this.hi = hi;
		this.lo = lo;
		this.info = info;
	}

	/**
	 * Compute population in target query, using single thread method when it's
	 * less than cutoff, otherwise use multiple calculate method
	 */
	@Override
	protected Integer compute() {
		if (hi - lo < SEQUENTIAL_THRESHOLD) {
			int targetPopulation = 0;
			for (int i = lo; i < hi; i++) {

				CensusGroup file = grid.data[i];
				float lon = file.longitude;
				float lag = file.latitude;
				float west = ((info.target.left - 1) / info.col)
						* (info.bound.right - info.bound.left) + info.bound.left;
				float east = (info.target.right / info.col) * (info.bound.right - info.bound.left)
						+ info.bound.left;
				float south = ((info.target.bottom - 1) / info.row)
						* (info.bound.top - info.bound.bottom) + info.bound.bottom;
				float north = (info.target.top / info.row) * (info.bound.top - info.bound.bottom)
						+ info.bound.bottom;
				if (lag <= north && lag >= south && lon <= east && lon >= west) {
					targetPopulation += file.population;
				}
			}
			return targetPopulation;
		} else {
			int mid = (hi + lo) / 2;
			TargetPopulation left = new TargetPopulation(grid, lo, mid, info);
			TargetPopulation right = new TargetPopulation(grid, mid, hi,info);
			left.fork();
			int rightside = right.compute();
			int leftside = left.join();
			return rightside + leftside;

		}
	}

	/**
	 * Gets the population of the required query rectangle
	 * 
	 * @param target
	 *            target query to calculate population in
	 * @return the population in target query
	 */
	public Integer targetPoplulation(Rectangle target) {
		int result = fjPool.invoke(new TargetPopulation(grid, 0,
				grid.data_size, info));
		return result;
	}
}
