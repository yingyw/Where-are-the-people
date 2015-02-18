
/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
/**
 * This class extends VersionOne and implements VersionBase. A version of the
 * population queries that uses multiple threads to find boundaries of
 * population map and find sub_population of a query
 * 
 */
public class VersionTwo extends VersionOne implements VersionBase {
	private CensusData file;
	protected Rectangle target;
	private Rectangle result;
	private int col;
	private int row;

	VersionTwo(CensusData file, int col, int row, Rectangle target) {
		super(file, row, col, target);
		this.file = file;
		this.target = target;
		this.col = col;
		this.row = row;
	}

	/**
	 * returns a Rectangle includes four boundaries of whole map and total
	 * population inside map
	 */
	@ Override
	public Rectangle scaleBoundary() {
		if (file.data_size == 0) {
			return null;
		}
		ScaleBoundary boundary = new ScaleBoundary(file, 0, file.data_size);
		result = boundary.fourCorners();
		return result;
	}

	/**
	 * Calculate population inside target query and returns this population
	 * Returns target population in target query
	 */
	@ Override
	public int getTargetPopulation() {
		if (file.data_size == 0) {
			return 0;
		}
		Rectangle bound = scaleBoundary();
		Info info = new Info(col,row,bound,file,target);
		TargetPopulation population = new TargetPopulation(file, 0,
				file.data_size, info);
		return population.targetPoplulation(target);

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
		float percentage = (float) getTargetPopulation()
				/ (float) scaleBoundary().population * 100;
		return new Pair<Integer, Float>((int) getTargetPopulation(), percentage);

	}

}

