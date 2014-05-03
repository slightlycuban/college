package CBTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

/**
 * This is the main driver for to test the functionality of <code>CBTree</code>.
 * 
 * This takes in an input file, <code>input.txt</code> and performs insertion,
 * output, and search on the tree according to the input. JFileChooser is used
 * to select this file.
 *
 * @author Michael Tracy
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException,
            NullPointerException
    {

        JFileChooser select = new JFileChooser(".");

        if(select.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File inFile;
            Scanner fileScan = null;
            try {
                inFile = select.getSelectedFile();
                fileScan = new Scanner(inFile);
                
                CBTree tree = new CBTree();

                while (fileScan.hasNextLine()) {
                    StringTokenizer line = new StringTokenizer(fileScan.nextLine());

                    char action = line.nextToken().charAt(0);

                    switch (action) {
                        case 'I': // Insert node into tree
                            int x = Integer.parseInt(line.nextToken());
                            int y = Integer.parseInt(line.nextToken());
                            System.out.println("Inserting (" + x + "," + y + ") " +
                                    "into the tree.");
                            tree.insert(x, y);
                            break;
                        case 'O': // Output the status of the tree
                            System.out.println("Nodes that are currently in the tree:");
                            System.out.println(tree.toString() + "\n");
                            break;
                        case 'S': // Search for nodes that are in the rectangle.
                            int x1 = Integer.parseInt(line.nextToken());
                            int y1 = Integer.parseInt(line.nextToken());
                            int x2 = Integer.parseInt(line.nextToken());
                            int y2 = Integer.parseInt(line.nextToken());
                            System.out.println("These are all the nodes that fall within " +
                                    "(" + x1 + "," + y1 + ") and " +
                                    "(" + x2 + "," + y2 + "):");
                            System.out.println(tree.search(x1, y1, x2, y2) + "\n");
                            break;
                        default:
                            System.out.println("Unknown option: " + action);
                    } // end switch
                } // end while
            } //end try
            catch( IOException e) {
                System.out.println("wrong input");
                e.printStackTrace();
            } // end catch
            finally {
                fileScan.close();
            } // end finally
        } // end if
    } //end main
}
