package teamstats;

import java.util.TreeMap;

/**
 * This implements the interface for the tree that holds the teamstat data.
 * 
 * @author Michael Tracy
 */
public class TeamTree {

    /**
     * <code>TreeMap</code> holding all the data for the different teams.
     */
    private TreeMap<String,TeamInfo> tree;

    /**
     * Default constructor. Creates an empty <code>TreeMap</code>
     * of type <code>String,TeamInfo</code>
     */
    public TeamTree() {
        this.tree = new TreeMap<String,TeamInfo>();
    }

    /**
     * This takes a TeamInfo <code>team</code> and adds it to the tree.
     * If the team does not exist in the tree, a new team will be added.
     * If the team exists in the tree, then the data for wins, losses, and
     * years played will be added to the TeamInfo currently in the tree.
     * 
     * @param team <code>TeamInfo</code> of the team to add.
     */
    public void add(TeamInfo team) {
        TeamInfo newTeam = this.getTree().get(team.getName());
        // Team does not exist in tree
        if (newTeam == null) {
            this.getTree().put(team.getName(), team);
        }
        // Its in there, now we just have to add it properly
        else {
            newTeam.addLosses(team.getLosses());
            newTeam.addWins(team.getWins());
            newTeam.addYears(team.getYears());
            this.getTree().put(newTeam.getName(), newTeam);
        }
    }

    /**
     * Simply returns the <code>toString()</code> from <code>TreeMap</code>.
     * @return unformatted String of whatever is in <code>tree</code>.
     */
    @Override
    public String toString() {
        return this.getTree().toString();
    }

    /**
     * @return the tree
     */
    public TreeMap<String, TeamInfo> getTree() {
        return tree;
    }

}
