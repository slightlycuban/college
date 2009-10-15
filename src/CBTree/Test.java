/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CBTree;

/**
 *
 * @author mtracy
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CBTree tree = new CBTree();
        tree.insert(5, 7);
        tree.insert(3, 2);
        tree.insert(6, 2);

        System.out.println(CBTree.inOrderTraversal(tree.root));
    }

}
