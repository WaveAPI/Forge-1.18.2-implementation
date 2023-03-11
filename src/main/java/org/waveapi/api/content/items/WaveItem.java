package org.waveapi.api.content.items;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.models.ItemModel;
import org.waveapi.api.mics.Side;
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

    private static Registry<Item> registry;
    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        for (WaveItem item : toRegister) {
            event.getRegistry().register(new Item(new Item.Settings()).setRegistryName(new Identifier(item.mod.name, item.id)));
        }
        toRegister = null;
    }

    public WaveItem(String id, WaveMod mod) {
        this.id = id;
        this.mod = mod;

        toRegister.add(this);
    }

    public String getId() {
        return id;
    }

    public void setModel(ItemModel model) {
        if (Side.isClient() && bake) {
            model.build(ResourcePackManager.getInstance().getPackDir(), this);
        }
    }

    public WaveMod getMod() {
        return mod;
    }

    public Item getItem() {
        return item;
    }

    public void addTranslation(String language, String name) {
        if (Side.isClient() && bake) {
            LangManager.addTranslation(mod.name, language, "item." + mod.name + "." + id, name);
        }
    }
}
