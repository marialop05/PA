package es.projectalpha.pa.rage.events;

import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    private RageGames plugin;

    public PlayerEvents(RageGames instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(null);
            System.out.println(RageGames.getPlayer(player.getPlayer()));
        plugin.getGm().getPlaying().forEach(p -> { System.out.println(p.getPlayer() + " " + p.getPlayer().getName()); });
        if (plugin.getGm().isInLobby()) {
            player.getInventory().clear();
            plugin.getGm().addPlayerToGame(RageGames.getPlayer(player));
            plugin.getServer().getOnlinePlayers().forEach(p -> player.showPlayer(p));
            plugin.getServer().getOnlinePlayers().forEach(p -> p.showPlayer(player));
            player.teleport(plugin.getAm().getRandomSpawn());
            RageGames.getPlayer(player).setLobby();
            Utils.broadcastMsg("&7Ha entrado al juego &e" + player.getDisplayName() + " &3(&b" + plugin.getGm().getPlaying().size() + "&d/&b" + plugin.getAm().getMaxPlayers() + "&3)");
            plugin.getGm().checkStart();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        plugin.getGm().removePlayerFromGame(RageGames.getPlayer(p));
        Utils.broadcastMsg("&7Ha salido del juego &e" + p.getDisplayName() + " &3(&b" + plugin.getGm().getPlaying().size() + "&d/&b" + plugin.getAm().getMaxPlayers() + "&3)");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!RageGames.getPlayer(e.getPlayer()).isOnRank(PACmd.Grupo.Admin)) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!RageGames.getPlayer(e.getPlayer()).isOnRank(PACmd.Grupo.Admin)) e.setCancelled(true);
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();

        p.teleport(plugin.getAm().getRandomSpawn());
        RageGames.getPlayer(p).resetPlayer();

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }
}
