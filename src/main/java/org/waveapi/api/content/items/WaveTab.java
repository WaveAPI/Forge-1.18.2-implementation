package org.waveapi.api.content.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.models.SimpleItemModel;
import org.waveapi.api.misc.Side;
import org.waveapi.content.resources.LangManager;

import static org.waveapi.Main.bake;

public class WaveTab {

    private final String id;
    private final WaveMod mod;
    public final ItemGroup group;


    public WaveTab(String id, WaveItem item, WaveMod mod) {
        this.mod = mod;
        this.id = id;
        group = new ItemGroup(mod.name + ":" + id) {
            @Override
            public ItemStack createIcon() {
                return new ItemStack(item.getItem());
            }
        };
    }

    public WaveTab(String id, SimpleItemModel model, WaveMod mod) {
        this.mod = mod;
        this.id = id;
        WaveItem logo = new WaveItem("tab_" + id + "_logo_item", mod).setModel(model);

        group = new ItemGroup(mod.name + ":" + id) {
            @Override
            public ItemStack createIcon() {
                return new ItemStack(logo.getItem());
            }
        };
    }

    public WaveTab(String id, String modelPath, WaveMod mod) {
        this.mod = mod;
        this.id = id;
        WaveItem logo = new WaveItem("tab_" + id + "_logo_item", mod).setModel(new SimpleItemModel(modelPath));

        group = new ItemGroup(mod.name + ":" + id) {
            @Override
            public ItemStack createIcon() {
                return new ItemStack(logo.getItem());
            }
        };
    }

    public WaveTab addTranslation(String language, String name) {
        if (Side.isClient() && bake) {
            LangManager.addTranslation(mod.name, language, "itemGroup." + mod.name + ":" + id, name);
        }
        return this;
    }

}
