/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
import java.util.concurrent.ForkJoinPool;

/**
 * This class implements VersionBase. A version of the population queries
 * that use 2D array and multiple threads to find population in each grid and
 * calculate sub_population of query.
 *
 */
public class VersionFour implements VersionBase {
	ForkJoinPool fPool = new ForkJoinPool();
	private CensusData file;
	int[][] result;
	Rectangle target;
	int col;
	int row;
	VersionThree v3;
	/**
	 * Constructs a new version four object with given file, number of columns
	 * and rows and target boundaries.
	 */
	public VersionFour(CensusData file, int col, int row, Rectangle target) {
		this.file=file;
		result = new int[col][row];
		this.target = target;
		this.col = col;
		this.row = row;
		v3 = new VersionThree(file,col,row,target);
	
	}
	
	/**
	 * get overall boundaries of the map, returns the boundaries represented by 
	 * a rectangle
	 */
	@Override
	public Rectangle scaleBoundary(){
		if(file.data_size==0){
			return null;
		}
		ScaleBoundary boundaries = new ScaleBoundary(file,0,file.data_size);
		Rectangle boundary = boundaries.fourCorners();
		return boundary;
	}
	/**
	 * Calculate the population inside each grid of query return a 2D array
	 * to represent the query with grids
	 */
	public int[][] gridPopulation(){
		Info info = new Info(col,row,scaleBoundary(),file);
		ProcessGridPop gridPop = new ProcessGridPop(0,result.length,info);
		return fPool.invoke(gridPop);
	}
	/**
	 * Get population inside target grid, return the number of population 
	 * inside target grid
	 */
	public int getTargetPopulation(){
		result = v3.gridSum(gridPopulation());
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
	public Pair<Integer, Float> getPair() {
		int total = getTargetPopulation();
		float percentage = (float) total
				/ (float) scaleBoundary().population * 100;
		return new Pair<Integer, Float>(total, percentage);

	}
}

