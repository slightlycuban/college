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

    /**
     * The name of the team
     */
    private String name;
    /**
     * The number of losses the team has suffered
     */
    private int losses;
    /**
     * The number of games the team has won
     */
    private int wins;
    /**
     * <code>LinkedList&lt;String&gt;</code> of the years the team has played
     */
    private LinkedList<String> years;

    /**
     * Default constructor. Creates a <code>TeamInfo</code>
     * with a name given by the string <code>name</code>
     * @param name String to name the TeamInfo object
     */
    public TeamInfo(String name) {
        this.name = name;
        this.losses = this.wins = 0;
        this.years = new LinkedList<String>();
    }

    /**
     * Add a particular year to the list <code>year</code>.
     * Reject any duplicates.
     *
     * @param year String of the year to add
     */
    public void addYear(String year) {
        if (!this.years.contains(year)) this.years.add(year);
    }

    /**
     * Add a list of years to the list <code>year</code>.
     * @param years LinkedList&lt;String&gt; of years to add
     */
    public void addYears(LinkedList<String> years) {
        ListIterator<String> it = years.listIterator();
        while(it.hasNext()) this.addYear(it.next());
    }

    /**
     * Increment the number of wins by 1
     */
    public void incrWin() {
        this.wins++;
    }

    /**
     * Add a number of wins to the total
     * @param wins integer of number of wins to add
     */
    public void addWins(int wins) {
        this.wins += wins;
    }

    /**
     * Increment the number of losses by 1
     */
    public void incrLoss() {
        this.losses++;
    }

    /**
     * Add a number of losses to the total
     * @param losses integer of number of losses to add
     */
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

    /**
     * I&rsquo;ve gone over this quite a bit, and I cannot see how sorting
     * the years when I receive them would be any faster than just sorting
     * the entire list when I want to call <code>toString()</code>. Plus, this
     * is much easier than sorting it on <code>addYear</code>. So, before
     * the years are printed, they are sorted.
     *
     * @return formatted string of the team name, number of wins,
     * number of losses, and list of years the team played
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Name: " + this.getName() +
                " Wins: " + this.getWins() + " Losses: " + this.getLosses() +
                "\nYears: ");
        Collections.sort(this.years);
        ListIterator<String> it = this.years.listIterator();
        while (it.hasNext()) out.append(it.next() + " ");
        out.append("\n\n");
        return out.toString();
    }

}
