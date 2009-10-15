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
        tree.insert(6, 8);
        tree.insert(1, 10);
        tree.insert( 5, 12);
        tree.insert(4, 1);
        tree.insert( 9, 10);
        tree.insert(15, 12);
        tree.insert(9, 9);

        System.out.println(tree.toString());
        
        System.out.println(tree.search(4, 6, 10, 13));
    }

}
