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
                    node.lst = insert(xdata, ydata, node.lst, level++);
                }
                else if (node.xdata < xdata) {
                    node.rst = insert(xdata, ydata, node.rst, level++);
                }
                break;
            // we compare by the y value
            case 1:
                if (node.ydata >= ydata) {
                    node.lst = insert(xdata, ydata, node.lst, level++);
                }
                else if (node.ydata < ydata) {
                    node.rst = insert(xdata, ydata, node.rst, level++);
                }
                break;
            default:
                System.out.println("You found a value " + level + " that is neither odd nor even!");
                return null;
        }
        return node;
    }

    public static String inOrderTraversal (CBNode node) {
        if (node == null) return null;

        else return CBTree.inOrderTraversal(node.lst) + node.toString() + ";" + CBTree.inOrderTraversal(node.rst);
    }

    @Override
    public String toString() {
        return CBTree.inOrderTraversal(root);
    }

}
