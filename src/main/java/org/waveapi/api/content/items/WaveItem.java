package org.waveapi.api.content.items;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.models.ItemModel;
import org.waveapi.api.misc.Side;
import org.waveapi.api.world.entity.living.EntityPlayer;
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.api.world.inventory.UseHand;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;

import java.util.LinkedList;

import static org.waveapi.Main.bake;

@Mod.EventBusSubscriber(modid = "waveapi", bus = Mod.EventBusSubscriber.Bus.MOD)

public class WaveItem {
    private final String id;
    private final WaveMod mod;

    private Item item;

    private static LinkedList<WaveItem> toRegister = new LinkedList<>();

    private final Item.Settings settings;
    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        for (WaveItem item : toRegister) {
            item.item = new CustomItemWrap(item.settings, item).setRegistryName(new Identifier(item.mod.name, item.id));
            event.getRegistry().register(item.item);
        }
        toRegister = null;
    }

    public WaveItem(String id, WaveMod mod) {
        this.id = id;
        this.mod = mod;
        this.settings = new Item.Settings();

        toRegister.add(this);
    }

    public String getId() {
        return id;
    }

    public WaveItem setModel(ItemModel model) {
        if (Side.isClient() && bake) {
            model.build(ResourcePackManager.getInstance().getPackDir(), this);
        }
        return this;
    }

    public WaveMod getMod() {
        return mod;
    }

    public Item getItem() {
        return item;
    }

    public ItemUseResult onUse(org.waveapi.api.world.inventory.ItemStack item, UseHand hand, EntityPlayer player) {
        return null;
    }

    public WaveItem setTab(WaveTab tab) {
        settings.group(tab.group);
        return this;
    }

    public WaveItem setMaxStackSize(int size) {
        settings.maxCount(size);
        return this;
    }

    public WaveItem addTranslation(String language, String name) {
        if (Side.isClient() && bake) {
            LangManager.addTranslation(mod.name, language, "item." + mod.name + "." + id, name);
        }
        return this;
    }
}
