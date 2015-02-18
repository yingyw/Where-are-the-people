/**
 * Yingying Wang (yingyw)
 * Xiaoyan Chen(h13579)
 * CSE332 Projects 3
 */
/**
 * This interface describes all basic implementations of different version
 * 
 */
public interface VersionBase {

	/**
	 * scale up the given west, east, south, and north according to given data's
	 * latitude and longitude.
	 */
	public Rectangle scaleBoundary();

	/**
	 * sums up the total population of given area
	 * 
	 * @return the total population of the given area
	 */
	public int getTargetPopulation();

}