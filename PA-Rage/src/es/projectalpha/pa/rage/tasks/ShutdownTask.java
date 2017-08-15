package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class ShutdownTask extends BukkitRunnable {

    private final RageGames plugin;
    private int count = 10;

    public ShutdownTask(RageGames instance) {
        this.plugin = instance;
    }

    public void run() {
        switch (count) {
            case 10:
                RageGames.players.forEach(r -> r.sendMessage(Messages.getMessage(Messages.SEND, PAData.BUNGEE, "%t%", count + "")));
                break;
            case 2:
                removeAll();
                break;
            case 0:
                plugin.getServer().shutdown();
                cancel();
                break;
        }
        count--;
    }

    private void removeAll() {
        plugin.getGm().getPlaying().forEach(p -> plugin.getGm().removePlayerFromGame(p));
        RageGames.players.forEach(RagePlayer::sendToLobby);
    }
}
