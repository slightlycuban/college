/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CBTree;

/**
 *
 * @author mtrac002
 */
public class CBTree {

    CBNode root;

    public CBTree() {
        root = null;
    }

    public CBNode findMin() {
        CBNode t = root;
        if (t == null) return null;
        while (t.lst != null) {
            t = t.lst;
        }
        return t;
    }

    public CBNode findMax() {
        CBNode t = root;
        if (t == null) return null;
        while (t.rst != null) t = t.rst;
        return t;
    }

    public void insert(int xdata, int ydata) {
        this.root = insert(xdata, ydata, root, 0);
    }

    private CBNode insert(int xdata, int ydata, CBNode node, int level) {
        if ( node == null ) return new CBNode(xdata, ydata);
        switch (level % 2) {
            // we compare by the x value
            case 0:
                if (node.xdata >= xdata) {
                    node.lst = insert(xdata, ydata, node.lst, level + 1);
                }
                else if (node.xdata < xdata) {
                    node.rst = insert(xdata, ydata, node.rst, level + 1);
                }
                break;
            // we compare by the y value
            case 1:
                if (node.ydata >= ydata) {
                    node.lst = insert(xdata, ydata, node.lst, level + 1);
                }
                else if (node.ydata < ydata) {
                    node.rst = insert(xdata, ydata, node.rst, level + 1);
                }
                break;
            default:
                System.out.println("You found a value " + level + " that is neither odd nor even!");
                return null;
        }
        return node;
    }

    private static String toString (CBNode node) {
        if (node == null) return "";

        else return CBTree.toString(node.lst) + node.toString() + ";" + CBTree.toString(node.rst);
    }

    @Override
    public String toString() {
        return CBTree.toString(root);
    }

    public String search (int x1, int y1, int x2, int y2) {

        int xmin = Math.min(x1, x2);
        int xmax = Math.max(x1, x2);
        int ymin = Math.min(y1, y2);
        int ymax = Math.max(y1, y2);

        return CBTree.search(xmin, xmax, ymin, ymax, root, 0);

    }

    private static String search (int xmin, int xmax, int ymin, int ymax, CBNode node, int level) {
        if (node == null) return "";
        switch (level % 2) {
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
