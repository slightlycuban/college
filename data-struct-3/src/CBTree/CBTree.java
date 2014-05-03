package CBTree;

/**
 * <code>CBTree</code> implements a binary search tree using
 * <code>CBNode</code> as nodes in the tree. Since the nodes hole two
 * data values, even levels (including zero) compare only the first data
 * value, and odd levels compare only the second.
 * <p>
 * Duplicates are not allowed.
 *
 * @author Michael Tracy
 */
public class CBTree {

    /**
     * The reference to the root of the tree.
     */
    CBNode root;

    /**
     * Default constructor. Sets <code>root</code> to <code>null</code>,
     * thus creating an empty tree.
     */
    public CBTree() {
        root = null;
    }

    /**
     * Uses the wonderful properties of a binary search tree
     * to quickly navigate to the smallest value.
     *
     * @return  minimum value in the tree
     */
    public CBNode findMin() {
        CBNode t = root;
        if (t == null) return null;
        while (t.lst != null) {
            t = t.lst;
        }
        return t;
    }

    /**
     * Uses the wonderful properties of a binary search tree
     * to quickly navigate to the largest value.
     *
     * @return  maximum value in the tree
     */
    public CBNode findMax() {
        CBNode t = root;
        if (t == null) return null;
        while (t.rst != null) t = t.rst;
        return t;
    }

    /**
     * Insert a new node created from the given data points.
     * This function uses the recursive <code>insert</code>
     * to traverse the tree and find a suitable place to
     * inset the new node
     *
     * @param xdata int x value of the node to insert
     * @param ydata int y value of the node to insert
     */
    public void insert(int xdata, int ydata) {
        this.root = CBTree.insert(xdata, ydata, root, 0);
    }

    /**
     * Recursively traverse the tree to find the proper place to insert
     * a new node. Each call must return the reference to the new tree containing
     * the new node.
     * <p>
     * The comparitive property of this two-value tree is maintained by
     * <code>level</code>, which passes the level of the tree that is currently
     * being examined by this.
     * 
     * @param xdata int of x value to add to the new node
     * @param ydata int of y value to add to the new node
     * @param node  CBNode of where we're attempting to insert
     * @param level int of which level we are currently on
     * @return  reference to the tree with the new node
     */
    private static CBNode insert(int xdata, int ydata, CBNode node, int level) {
        if ( node == null ) return new CBNode(xdata, ydata);
        switch (level % 2) {
            // even levels; we compare by the x value
            case 0:
                if (node.xdata > xdata) {
                    node.lst = insert(xdata, ydata, node.lst, level + 1);
                }
                else if (node.xdata < xdata) {
                    node.rst = insert(xdata, ydata, node.rst, level + 1);
                }
                else if (node.ydata != ydata) {
                    node.lst = insert(xdata, ydata, node.lst, level + 1);
                }
                else return node;
                break;
            // odd levels; we compare by the y value
            case 1:
                if (node.ydata > ydata) {
                    node.lst = insert(xdata, ydata, node.lst, level + 1);
                }
                else if (node.ydata < ydata) {
                    node.rst = insert(xdata, ydata, node.rst, level + 1);
                }
                else if (node.xdata != xdata) {
                    node.lst = insert(xdata, ydata, node.lst, level + 1);
                }
                else return node;
                break;
            default:
                System.out.println("You found a value " + level + " that is neither odd nor even!");
                return null;
        }
        return node;
    }

    /**
     * This performs creates a string of the data stored in the tree.
     * This uses the recursive <code>toString</code> helper function to
     * recursively perform an InOrder traversal of the tree.
     *
     * @return  string created from the InOrder traversal of the tree
     */
    @Override
    public String toString() {
        return CBTree.toString(root);
    }

    /**
     * Recursively get the data from each node. Output is presented InOrder
     * 
     * @param node  CBNode of the tree to output
     * @return  String of data within the node/tree
     */
    private static String toString (CBNode node) {
        if (node == null) return "";

        else return CBTree.toString(node.lst) + node.toString() + ";" + CBTree.toString(node.rst);
    }

    /**
     * Search through the tree, and return a string of nodes
     * that fall within a rectangle outlined by two pairs of
     * Cartesian coordinates.
     *
     * This makes use of the recursive <code>search</code> to traverse
     * through the tree.
     *
     * @param x1    int x value of the first coordinate
     * @param y1    int y value of the first coordinate
     * @param x2    int x value of the second coordinate
     * @param y2    int y value of the second coordinate
     * @return  string of data from all nodes that fall within the four bounds
     */
    public String search(int x1, int y1, int x2, int y2) {

        int xmin = Math.min(x1, x2);
        int xmax = Math.max(x1, x2);
        int ymin = Math.min(y1, y2);
        int ymax = Math.max(y1, y2);

        // call the recursive search function on root
        return CBTree.search(xmin, xmax, ymin, ymax, root, 0);

    }

    /**
     * Recursively search through the tree, and return a string of nodes
     * that fall within a rectangle outlined by two pairs of
     * Cartesian coordinates.
     * <p>
     * This uses the comparitive property of the tree to eliminate certain
     * subtrees. If a node falls below the currently checked minimum, then
     * that node's left subtree is excluded from the search. Likewise, if a node
     * is over the currently checked max, its right subtree is ignored.
     * <p>
     * The comparitive property of this two-value tree is checked by
     * <code>level</code>, which passes the level of the tree that is currently
     * being examined by this.
     * 
     * @param xmin  int of minimum x value for the rectangle
     * @param xmax  int of maximum x value for the rectangle
     * @param ymin  int of minimum y value for the rectangle
     * @param ymax  int of maximum y value for the rectangle
     * @param node  CBNode of the node/tree to perform the search on
     * @param level int of the level of the tree the search is currently at
     * @return      String containing all nodes found thus far that satisfy the condition
     */
    private static String search (int xmin, int xmax, int ymin, int ymax, CBNode node, int level) {
        if (node == null) return "";
        switch (level % 2) {
            // we are on the even levels, and must use
            // the x values for comparison
            case 0:
                if (node.xdata > xmax) {
                    return CBTree.search(xmin, xmax, ymin, ymax, node.lst, level + 1);
                } else if (node.xdata < xmin) {
                    return CBTree.search(xmin, xmax, ymin, ymax, node.rst, level + 1);
                } else {
                    if (ymin <= node.ydata && node.ydata <= ymax)
                        return CBTree.search(xmin, xmax, ymin, ymax, node.lst, level + 1) + node.toString() + ";" + CBTree.search(xmin, xmax, ymin, ymax, node.rst, level + 1);
                    else
                        return CBTree.search(xmin, xmax, ymin, ymax, node.lst, level + 1) + CBTree.search(xmin, xmax, ymin, ymax, node.rst, level + 1);
                }
            // we are on the odd levels, and must use
            // the y values for comparison
            case 1:
                if (node.ydata > ymax) {
                    return CBTree.search(xmin, xmax, ymin, ymax, node.lst, level + 1);
                } else if (node.ydata < ymin) {
                    return CBTree.search(xmin, xmax, ymin, ymax, node.rst, level + 1);
                } else {
                    if (xmin <= node.xdata && node.xdata <= xmax)
                        return CBTree.search(xmin, xmax, ymin, ymax, node.lst, level + 1) + node.toString() + ";" + CBTree.search(xmin, xmax, ymin, ymax, node.rst, level + 1);
                    else
                        return CBTree.search(xmin, xmax, ymin, ymax, node.lst, level + 1) + CBTree.search(xmin, xmax, ymin, ymax, node.rst, level + 1);
                }
            default:
                System.out.println("Um, " + level + " doesn't seem to be odd or even...");
                return null;
        }
    }

}
