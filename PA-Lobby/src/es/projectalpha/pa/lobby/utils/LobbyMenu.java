package es.projectalpha.pa.lobby.utils;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.lobby.PALobby;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LobbyMenu {

    private static Inventory servers;
    private PALobby plugin = PALobby.getInstance();

    public LobbyMenu(PALobby instance) {
        this.plugin = instance;
        servers = plugin.getServer().createInventory(null, 54, "Servidores");

        servers.addItem(new ItemStack(Material.APPLE));
    }

    public static void openMenu(PAUser u, MenuType menuType) {
        Inventory clon = null;

        switch (menuType) {
            case SERVERS:
                clon = servers;
                break;
        }
        if (clon != null) {
            u.getPlayer().closeInventory();
            u.getPlayer().openInventory(clon);
            u.sendSound(Sound.CLICK);
        }
    }

    public enum MenuType {
        SERVERS
    }
}
