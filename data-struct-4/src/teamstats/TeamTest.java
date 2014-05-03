package teamstats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

/**
 * The main driver to test TeamTree and TeamInfo.
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

                // Read in each line of the file
                while (fileScan.hasNextLine()) {
                    StringTokenizer line = new StringTokenizer(fileScan.nextLine());

                    String date = line.nextToken(":");
                    String winner = line.nextToken();
                    line.nextToken();
                    String loser = line.nextToken();

                    // This little block breaks out the year
                    int year = Integer.parseInt(date.substring(6, 8));
                    if (year <= 10) year += 2000;
                    else year += 1900;

                    // Construct the two TeamInfo objects
                    TeamInfo winTeam = new TeamInfo(winner);
                    TeamInfo loseTeam = new TeamInfo(loser);
                    winTeam.addYear(year + "");
                    loseTeam.addYear(year + "");
                    winTeam.incrWin();
                    loseTeam.incrLoss();

                    // Add the new TeamInfo objects to the tree
                    tree.add(winTeam);
                    tree.add(loseTeam);
                } // end while

                // Setup to output the data
                Collection<TeamInfo> teams = tree.getTree().values();
                StringBuilder out = new StringBuilder();
                for (TeamInfo team : teams) out.append(team.toString());
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