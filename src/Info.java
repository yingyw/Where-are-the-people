
/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
/**
 * A class called info includes some unchanged information
 * to reduce the number of parameter passed in parallel method
 *
 */
public class Info {
	int col;
	int row;
	Rectangle bound;
	CensusData file;
	Rectangle target;
	/**
	 * Construct a Info with given number of columns, rows,overall boundaries
	 * and parsed data
	 */
	public Info(int col, int row, Rectangle boundary,CensusData file){
		this.col=col;
		this.row=row;
		bound=boundary;
		this.file = file;
	}
	/**
	 * Construct a Info with given number of columns, rows,overall boundaries
	 * and parsed data, and target grid
	 */
	public Info(int col, int row, Rectangle boundary,CensusData file,Rectangle target){
		this.col=col;
		this.row=row;
		bound=boundary;
		this.file = file;
		this.target=target;
	}
}
