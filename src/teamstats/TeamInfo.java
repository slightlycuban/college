package teamstats;

import java.util.TreeSet;

/**
 * This class is designed to keep all the info of a team. It will hold
 * the name, number of losses, number of wins, and a set of all the years
 * the team has played.
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
     * <code>TreeSet&lt;String&gt;</code> of the years the team has played
     */
    private TreeSet<String> years;

    /**
     * Default constructor. Creates a <code>TeamInfo</code>
     * with a name given by the string <code>name</code>
     * @param name String to name the TeamInfo object
     */
    public TeamInfo(String name) {
        this.name = name;
        this.losses = this.wins = 0;
        this.years = new TreeSet<String>();
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
     * @param years <code>TreeSet&lt;String&gt;</code> of years to add
     */
    public void addYears(TreeSet<String> years) {
        this.years.addAll(years);
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
     * @return the name as a <code>String</code>
     */
    public String getName() {
        return name;
    }

    /**
     * @return the losses as an <code>int</code>
     */
    public int getLosses() {
        return losses;
    }

    /**
     * @return the wins as an <code>int</code>
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return the years the team has played in a <code>TreeSet</code>
     */
    public TreeSet<String> getYears() {
        return years;
    }

    /**
     * Uses <code>StringBuilder</code> to create a formatted string
     * of all the data currently in the object about the team.
     *
     * @return formatted string of the team name, number of wins,
     * number of losses, and list of years the team played
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Name: " + this.getName() +
                " Wins: " + this.getWins() + " Losses: " + this.getLosses() +
                "\nYears: ");
        for (String year : this.years) out.append(year + " ");
        out.append("\n\n");
        return out.toString();
    }

}
