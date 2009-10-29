package teamstats;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * This class is designed to keep all the info of a team.
 * 
 * @author Michael Tracy
 */
public class TeamInfo {

    private String name;
    private int losses;
    private int wins;
    private LinkedList<String> years;

    public TeamInfo(String name) {
        this.name = name;
        this.losses = this.wins = 0;
        this.years = new LinkedList<String>();
    }

    public void addYear(String year) {
        if (years.isEmpty()) years.add(year);
        ListIterator<String> it = years.listIterator();
        for (String here = it.next(); here != null; here = it.next()) {
            if (year.equals(here)) {
                throw new DuplicateElementException("Year " + year + " is already in the list.");
            }
            if (year.compareTo(here) < 0) break;
        }
        it.previous();
        it.add(year);
    }

    public void incrWin() {
        this.wins++;
    }

    public void addWins(int wins) {
        this.wins += wins;
    }

    public void incrLoss() {
        this.losses++;
    }

    public void addLosses(int losses) {
        this.losses += losses;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * @return the wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return the years
     */
    public LinkedList<String> getYears() {
        return years;
    }

}
