package CBTree;

/**
 * <code>CBNode</code> is used to store a pair of data points representing
 * a Cartesian coordinate. This class is used by <code>CBTree</code>
 * as the implementation of the nodes in that tree.
 * 
 * @author Michael Tracy
 */

public class CBNode {
    /**
     * References to the left subtree, <code>lst</code>, and
     * to the right subtree, <code>rst</code>.
     */
    CBNode lst;
    CBNode rst;

    /**
     * The two values that represent the coordindate.
     */
    int xdata;
    int ydata;

    /**
     * Default Constructor. Creates a new node.
     * <p>
     * This takes in two values representing the x and y
     * of a point on a Cartesian plane, and assigns the
     * values to xdata and ydata respectively.
     *
     * <code>lst</code> and <code>rst</code> are set to <code>null</code>,
     * because this creates a new node, not a new tree.
     *
     * @param xdata the x coordinate
     * @param ydata the y coordinate
     */
    public CBNode(int xdata, int ydata) {
        this.xdata = xdata;
        this.ydata = ydata;
        this.lst = this.rst = null;
    }

    /**
     * Create a string of the two data values,
     * xdata and ydata. The data is formatted
     * as a pair of Cartesian coordinates.
     *
     * @return  the formatted string of the class data
     */
    @Override
    public String toString() {
        return "(" + xdata + "," + ydata +")";
    }
}
