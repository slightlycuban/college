package teamstats;

import java.util.TreeMap;

/**
 * This implements the interface for the tree that holds the teamstat data.
 * 
 * @author Michael Tracy
 */
public class TeamTree {

    private TreeMap tree;

    public TeamTree() {
        this.tree = new TreeMap();
    }

    public void add(TeamInfo team) {
        Object newTeam = this.tree.get(team.getName());
        if (newTeam == null) {
            this.tree.put(team.getName(), team);
        } else {
            TeamInfo found = (TeamInfo)newTeam;
            found.addLosses(team.getLosses());
            found.addWins(team.getWins());
            found.addYear(team.getYears().getFirst());
        }
    }

}
