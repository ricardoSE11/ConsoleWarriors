
package consolewarriors.Server.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("wins")
    @Expose
    private int wins;
    @SerializedName("loses")
    @Expose
    private int loses;
    @SerializedName("surrenders")
    @Expose
    private int surrenders;
    @SerializedName("kills")
    @Expose
    private int kills;
    @SerializedName("successful attacks")
    @Expose
    private int successfulAttacks;
    @SerializedName("failed attacks")
    @Expose
    private int failedAttacks;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Player() {
    }

    /**
     * 
     * @param loses
     * @param username
     * @param surrenders
     * @param kills
     * @param successfulAttacks
     * @param failedAttacks
     * @param wins
     */
    public Player(String username, int wins, int loses, int surrenders, int kills, int successfulAttacks, int failedAttacks) {
        super();
        this.username = username;
        this.wins = wins;
        this.loses = loses;
        this.surrenders = surrenders;
        this.kills = kills;
        this.successfulAttacks = successfulAttacks;
        this.failedAttacks = failedAttacks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getSurrenders() {
        return surrenders;
    }

    public void setSurrenders(int surrenders) {
        this.surrenders = surrenders;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getSuccessfulAttacks() {
        return successfulAttacks;
    }

    public void setSuccessfulAttacks(int successfulAttacks) {
        this.successfulAttacks = successfulAttacks;
    }

    public int getFailedAttacks() {
        return failedAttacks;
    }

    public void setFailedAttacks(int failedAttacks) {
        this.failedAttacks = failedAttacks;
    }

}
