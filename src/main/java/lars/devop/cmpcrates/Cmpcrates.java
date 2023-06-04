package lars.devop.cmpcrates;

import io.papermc.paper.event.inventory.PrepareResultEvent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Cmpcrates extends JavaPlugin implements Listener {
    private Map<Player, CrateData> editingCrates;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        editingCrates = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("createcrate")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
                return true;
            }
            Player player = (Player) sender;
            openCrateCreationGUI(player);
            return true;
        } else if (command.getName().equalsIgnoreCase("editcrate")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage("Usage: /editcrate <crate-name>");
                return true;
            }
            Player player = (Player) sender;
            String crateName = args[0];
            openCrateEditingGUI(player, crateName);
            return true;
        } else if (command.getName().equalsIgnoreCase("givekey")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage("Usage: /givekey <crate-name>");
                return true;
            }
            Player player = (Player) sender;
            String crateName = args[0];
            giveCrateKey(player, crateName);
            return true;
        }
        return false;
    }

    private void openCrateCreationGUI(Player player) {
        Inventory gui = player.getServer().createInventory(null, 27, "Crate Creation");

        // Crate name input
        ItemStack nameInput = new ItemStack(Material.NAME_TAG);
        ItemMeta nameInputMeta = nameInput.getItemMeta();
        nameInputMeta.setDisplayName("Crate Name");
        nameInput.setItemMeta(nameInputMeta);
        gui.setItem(10, nameInput);

        // Crate block selection
        ItemStack blockSelection = new ItemStack(Material.CHEST);
        ItemMeta blockSelectionMeta = blockSelection.getItemMeta();
        blockSelectionMeta.setDisplayName("Crate Block");
        blockSelection.setItemMeta(blockSelectionMeta);
        gui.setItem(13, blockSelection);

        // Crate content selection
        ItemStack contentSelection = new ItemStack(Material.BOOK);
        ItemMeta contentSelectionMeta = contentSelection.getItemMeta();
        contentSelectionMeta.setDisplayName("Crate Content");
        contentSelection.setItemMeta(contentSelectionMeta);
        gui.setItem(16, contentSelection);

        player.openInventory(gui);
    }

    private void openCrateEditingGUI(Player player, String crateName) {
        CrateData crateData = getCrateData(crateName);
        if (crateData == null) {
            player.sendMessage("Crate not found.");
            return;
        }

        Inventory gui = player.getServer().createInventory(null, 27, "Crate Editing - " + crateName);

        // Crate name display
        ItemStack nameDisplay = new ItemStack(Material.NAME_TAG);
        ItemMeta nameDisplayMeta = nameDisplay.getItemMeta();
        nameDisplayMeta.setDisplayName("Crate Name");
        nameDisplayMeta.setLore(Collections.singletonList(crateName));
        nameDisplay.setItemMeta(nameDisplayMeta);
        gui.setItem(10, nameDisplay);

        // Crate block selection
        ItemStack blockSelection = new ItemStack(crateData.getCrateBlock());
        ItemMeta blockSelectionMeta = blockSelection.getItemMeta();
        blockSelectionMeta.setDisplayName("Crate Block");
        blockSelection.setItemMeta(blockSelectionMeta);
        gui.setItem(13, blockSelection);

        // Crate content selection
        ItemStack contentSelection = new ItemStack(Material.BOOK);
        ItemMeta contentSelectionMeta = contentSelection.getItemMeta();
        contentSelectionMeta.setDisplayName("Crate Content");
        contentSelection.setItemMeta(contentSelectionMeta);
        gui.setItem(16, contentSelection);

        player.openInventory(gui);
    }

    private void giveCrateKey(Player player, String crateName) {
        // Assuming the crate key is an item, you can create and give it to the player
        ItemStack crateKey = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta crateKeyMeta = crateKey.getItemMeta();
        crateKeyMeta.setDisplayName(crateName + " Key");
        crateKey.setItemMeta(crateKeyMeta);

        player.getInventory().addItem(crateKey);

        player.sendMessage("You have received a " + crateName + " key!");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();

        // Handle crate creation GUI clicks
        if (inventory != null && inventory.getTitle().equals("Crate Creation")) {
            event.setCancelled(true);

            // Crate name input
            if (event.getSlot() == 10) {
                if (item != null && item.getType() == Material.NAME_TAG) {
                    // Code to handle crate name input
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        String crateName = meta.getDisplayName();
                        // Process crate name
                    }
                }
            }

            // Crate block selection
            if (event.getSlot() == 13) {
                if (item != null) {
                    // Code to handle crate block selection
                    Material crateBlock = item.getType();
                    // Process crate block
                }
            }

            // Crate content selection
            if (event.getSlot() == 16) {
                if (item != null && item.getType() == Material.BOOK) {
                    // Code to handle crate content selection
                    // Open content selection GUI or perform other actions
                }
            }
        }

        // Handle crate editing GUI clicks
        if (inventory != null && inventory.getTitle().startsWith("Crate Editing - ")) {
            event.setCancelled(true);

            String crateName = inventory.getTitle().replace("Crate Editing - ", "");

            // Crate name display
            if (event.getSlot() == 10) {
                if (item != null && item.getType() == Material.NAME_TAG) {
                    // Code to handle crate name display
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        String displayName = meta.getDisplayName();
                        // Check if the displayed crate name matches the current crate name
                        if (displayName.equals(crateName)) {
                            // Perform actions for crate name display
                        }
                    }
                }
            }

            // Crate block selection
            if (event.getSlot() == 13) {
                if (item != null) {
                    // Code to handle crate block selection
                    Material crateBlock = item.getType();
                    // Process crate block
                }
            }

            // Crate content selection
            if (event.getSlot() == 16) {
                if (item != null && item.getType() == Material.BOOK) {
                    // Code to handle crate content selection
                    // Open content selection GUI or perform other actions
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Handle crate opening with keys
        if (item != null && item.getType() == Material.TRIPWIRE_HOOK) {
            // Check if the item has the desired display name or lore to identify it as a crate key
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null) {
                String displayName = itemMeta.getDisplayName();
                List<String> lore = itemMeta.getLore();

                // Check if the item is a crate key
                if (displayName != null && displayName.endsWith(" Key") && lore != null && lore.contains("Crate Key")) {
                    // Extract the crate name from the key's display name
                    String crateName = displayName.replace(" Key", "");

                    // Code to handle crate opening
                    // Open the crate, generate rewards, etc.
                    openCrate(player, crateName);

                    // Reduce the key's quantity by 1
                    item.setAmount(item.getAmount() - 1);
                    player.getInventory().setItemInMainHand(item);
                }
            }
        }
    }

    class CrateData {
        private String crateName;
        private Material crateBlock;
        // Other crate data fields and methods

        public CrateData(String crateName, Material crateBlock) {
            this.crateName = crateName;
            this.crateBlock = crateBlock;
        }

        public String getCrateName() {
            return crateName;
        }

        public Material getCrateBlock() {
            return crateBlock;
        }

        // Other getter and setter methods for additional crate data fields

        // Example method to add other crate data
        public void setOtherCrateData(String otherData) {
            // Set the value of otherData
        }

        // Example method to retrieve other crate data
        public String getOtherCrateData() {
            // Return the value of otherData
            return null;
        }
    }
}

