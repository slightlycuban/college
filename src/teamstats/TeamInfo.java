package teamstats;

import java.util.Collections;
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
        years.add(year);
    }

    public void addYears(LinkedList<String> years) {
        ListIterator<String> it = years.listIterator();
        for (String here = it.next(); here != null; here = it.next()) this.addYear(here);
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

    @Override
    /**
     *
     */
    public String toString() {
        StringBuilder out = new StringBuilder("Name: " + this.getName() + " Wins: " + this.getWins() + " Losses: " + this.getLosses() +
                "\nYears: ");
        Collections.sort(years);
        ListIterator<String> it = years.listIterator();
        for (String here = it.next(); here != null; here = it.next()) {
            out.append(here + " ");
        }
        return out.toString();
    }

}
