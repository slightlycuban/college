package teamstats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

/**
 *
 * @author Michael Tracy
 */
public class TeamTest {

    /**
     * The main driver to test TeamTree and TeamInfo.
     * @param args
     * @throws java.io.FileNotFoundException
     * @throws java.lang.NullPointerException
     */
    public static void main(String[] args) throws FileNotFoundException,
            NullPointerException {

        JFileChooser select = new JFileChooser(".");

        if(select.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File inFile;
            Scanner fileScan = null;
            try {
                inFile = select.getSelectedFile();
                fileScan = new Scanner(inFile);

                TeamTree tree = new TeamTree();

                while (fileScan.hasNextLine()) {
                    StringTokenizer line = new StringTokenizer(fileScan.nextLine());

                    String date = line.nextToken(":");
                    String winner = line.nextToken();
                    line.nextToken();
                    String loser = line.nextToken();

                    int year = Integer.parseInt(date.substring(6, 8));
                    if (year <= 10) year += 2000;
                    else year += 1900;

                    TeamInfo winTeam = new TeamInfo(winner);
                    TeamInfo loseTeam = new TeamInfo(loser);
                    winTeam.addYear(year + "");
                    loseTeam.addYear(year + "");
                    winTeam.incrWin();
                    loseTeam.incrLoss();

                    // System.out.println("Winning " + winner + " to the tree!");
                    tree.add(winTeam);
                    // System.out.println("Losing " + loser + " to the tree.");
                    tree.add(loseTeam);
                } // end while

                Collection teams = tree.getTree().values();
                StringBuilder out = new StringBuilder();
                Iterator<TeamInfo> it = teams.iterator();
                while (it.hasNext()) {
                    out.append(it.next().toString());
                }
                System.out.println(out.toString());
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