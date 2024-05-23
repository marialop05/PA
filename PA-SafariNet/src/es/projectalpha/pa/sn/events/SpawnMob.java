package es.projectalpha.pa.sn.events;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import es.projectalpha.pa.sn.SNMob;
import es.projectalpha.pa.sn.SafariNet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by cadox on 13/12/2016.
 */
public class SpawnMob implements Listener {

    private final SafariNet plugin;

    public SpawnMob(SafariNet plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onSpawn(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (isRightClickAction(event.getAction()) && event.getHand() == EquipmentSlot.HAND) {
            ItemStack item = event.getItem();

            if (isValidSpawnItem(item)) {
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();
                
                int id = Integer.parseInt(lore.get(0));
                String mobType = lore.get(1);

                if (canSpawnMob(player)) {
                    SNMob mob = new SNMob(player);

                    if (mob.isOwner(id)) {
                        mob.spawnMob(id, mobType);
                        item.setAmount(item.getAmount() - 1); // Decrease item amount by 1
                    } else {
                        player.sendMessage(plugin.getPrefix() + ChatColor.RED + "No eres el dueño de este huevo");
                    }
                } else {
                    player.sendMessage(plugin.getPrefix() + ChatColor.RED + "No puedes spawnear un mob en parcelas ajenas");
                }
            }
        }
    }

    private boolean isRightClickAction(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    private boolean isValidSpawnItem(ItemStack item) {
        if (item == null || item.getType() != Material.SNOWBALL) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.hasLore()) {
            return false;
        }

        String displayName = ChatColor.stripColor(meta.getDisplayName());
        return displ
