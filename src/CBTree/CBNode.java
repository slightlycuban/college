/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CBTree;

/**
 *
 * @author mtracy
 */

public class CBNode {
    CBNode lst;
    CBNode rst;
    int xdata;
    int ydata;

    public CBNode(int xdata, int ydata) {
        this.xdata = xdata;
        this.ydata = ydata;
        this.lst = this.rst = null;
    }

    @Override
    public String toString() {
        return "(" + xdata + "," + ydata +")";
    }
}
