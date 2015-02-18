
/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * This class is a part of version four to calculate the population inside
 * each grid.
 */
public class ProcessGridPop extends RecursiveTask<int[][]> {
	private static final long serialVersionUID = 1L;
	public static ForkJoinPool fjPool = new ForkJoinPool();
	static final int SEQUENTIAL_THRESHOLD=100;
	int hi;
	int lo;
	Info info;
    /**
     * Constructs a new ProcessGridPop with given data
     * @param lo lower bound for parallel analyze
     * @param hi higher bound for parallel analyze
     * @param info includes data to analyze, and overall boundaries
     * 		  and how many grids to divide the map
     */
	public ProcessGridPop( int lo, int hi,Info info){
		this.hi = hi;
		this.lo = lo;
		this.info = info;
	}
	
	/**
	 * Identifies each population data's position in the grid and store it separate 
	 * population grids, using single thread method when it's less than cutoff, otherwise using
	 * multiple thread calculating method. 
	 */
	@Override
	protected int[][] compute() {
		if(hi - lo <= SEQUENTIAL_THRESHOLD){
			int[][] result = new int[info.col][info.row];
			for (int i = lo; i < hi; i++) {
				CensusGroup grid = info.file.data[i];
				float lon = grid.longitude;
				float lag = grid.latitude;
				double col = result.length * (lon - info.bound.left)
						/ (info.bound.right - info.bound.left);
				int posCol = (int) col;
				double row = result[0].length * (lag - info.bound.bottom)
						/ (info.bound.top - info.bound.bottom);
				int posRow = (int) row;
				if (posCol == result.length) {
					posCol = posCol - 1;
				}
				if (posRow == result[0].length) {
					posRow = posRow - 1;
				}
				result[posCol][posRow] += grid.population;
			}
			return result;
		}else {
			ProcessGridPop left = new ProcessGridPop(lo,(lo+hi)/2,info);
			ProcessGridPop right = new ProcessGridPop((lo+hi)/2,hi,info);

			left.fork();
			int[][] rightside = right.compute();
			int[][] leftside = left.join();
			fjPool.invoke(new SumGrid(leftside,rightside,0,rightside.length,rightside[0].length,0));
			return rightside;
		}

	}
	
	class SumGrid extends RecursiveAction{
		
		private static final long serialVersionUID = 1L;
		int[][] lGrid;
		int[][] rGrid;
		int left;
		int right;
		int top;
		int bottom;
		static final int SEQUENTIAL_CUTOFF=10;
		/**
		 * Constructs a new sumGrid with given data
		 * 
		 * @param lGrid leftside grid to added
		 * @param rGrid rightside grid to added
		 * @param left left bound for parallel analyze
		 * @param right right bound for parallel analyze
		 * @param top top bound for parallel analyze
		 * @param bottom bottom bound for parallel analyze
		 */
		public SumGrid(int[][] lGrid,int[][] rGrid, int left, int right,int top,int bottom){
			this.lGrid=lGrid;
			this.rGrid = rGrid;
			this.left=left;
			this.right=right;
			this.top=top;
			this.bottom=bottom;
		}
		/**
		 * merge the population in each grid, so the grid population 
		 * will include total population in grid. Using single thread 
		 * calculate method when it's less than cutoff, otherwise use 
		 * four threads calculate method
		 */
		@Override
		protected void compute(){
			if(right-left<SEQUENTIAL_CUTOFF && top - bottom < SEQUENTIAL_CUTOFF){
				for(int i=lo;i<hi;i++){
					for(int j=bottom;j<top;j++){
						rGrid[i][j]+=lGrid[i][j];
					}
				}
			}else{
				SumGrid topLeft = new SumGrid(lGrid,rGrid,left,(left+right)/2,top,(bottom+top)/2);
				SumGrid topRight = new SumGrid(lGrid,rGrid,(left+right)/2,right,top,(bottom+top)/2);
				SumGrid bottomLeft = new SumGrid(lGrid,rGrid,left,(left+right)/2,(bottom+top)/2,bottom);
				SumGrid bottomRight = new SumGrid(lGrid,rGrid,(left+right)/2,right,(bottom+top)/2,bottom);
				
				topLeft.fork();
				topRight.fork();
				bottomLeft.fork();
				bottomRight.compute();
				topLeft.join();
				topRight.join();
				bottomLeft.join();
			}
		}
	}
}
