/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * This class is a part of version two to calculate total population in map and
 * boundaries of whole map using parallel implementation.
 * 
 */
public class ScaleBoundary extends RecursiveTask<Rectangle> {
	public static ForkJoinPool fjPool = new ForkJoinPool();
	static final long serialVersionUID = 1L;
	static final int SEQUENTIAL_THRESHOLD = 100;
	CensusData grid;
	int hi;
	int lo;
	Rectangle result;

	/**
	 * Constructs a new ScaleBoundary with given data
	 * 
	 * @param grid
	 *            data to analyze
	 * @param lo
	 *            lower bound for parallel analyze
	 * @param hi
	 *            higher bound for parallel analyze
	 */
	public ScaleBoundary(CensusData grid, int lo, int hi) {
		this.grid = grid;
		this.hi = hi;
		this.lo = lo;
		result = new Rectangle(grid.data[0].longitude, grid.data[0].longitude,
				grid.data[0].latitude, grid.data[0].latitude);
	}

	/**
	 * compute boundaries of whole map and total population of whole map use
	 * single thread calculate method when it's less than cutoff, otherwise use
	 * multiple threads calculate method
	 */
	@Override
	protected Rectangle compute() {
		if (hi - lo <= SEQUENTIAL_THRESHOLD) {
			CensusGroup[] data = grid.data;
			for (int i = lo; i < hi; i++) {
				result.population += data[i].population;
				result.left = Math.min(data[i].longitude, result.left);
				result.right = Math.max(data[i].longitude, result.right);
				result.top = Math.max(data[i].latitude, result.top);
				result.bottom = Math.min(data[i].latitude, result.bottom);
			}

			return result;

		} else {
			ScaleBoundary left = new ScaleBoundary(grid, lo, (lo + hi) / 2);
			ScaleBoundary right = new ScaleBoundary(grid, (lo + hi) / 2, hi);
			left.fork();
			Rectangle rightside = right.compute();
			Rectangle leftside = left.join();
			result = new Rectangle(Math.min(rightside.left, leftside.left),
					Math.max(leftside.right, rightside.right), Math.max(
							leftside.top, rightside.top), Math.min(
							leftside.bottom, rightside.bottom),
					rightside.population + leftside.population);
			return result;
		}
	}

	/**
	 * Process the given data to get boundaries of whole map and total
	 * population of whole map
	 * 
	 * @return a Rectangle includes boundaries of the whole map and total
	 *         population of whole map.
	 */
	public Rectangle fourCorners() {
		return fjPool.invoke(new ScaleBoundary(grid, 0, grid.data_size));
	}

}