package org.waveapi;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.waveapi.api.WaveLoader;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.mics.Side;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;

import java.io.File;
import java.util.Map;

@Mod("waveapi")
public class Main {
	public static final Logger LOGGER = LogManager.getLogger();

	public static final File mainFolder = new File("./waveAPI");

	public static boolean bake;

	public Main() {
		LOGGER.info("Initializing");

		try {
			Class.forName("net.minecraft.client.MinecraftClient");
			Side.isServer = false;
		} catch (ClassNotFoundException e) {
			Side.isServer = true;
		}

		if (Side.isClient()) {
			new ResourcePackManager();
		}

		WaveLoader.init();

		for (Map.Entry<String, WaveLoader.WrappedWaveMod> mod : WaveLoader.getMods().entrySet()) {
			bake = mod.getValue().changed;
			mod.getValue().mod.init();
		}

		if (Side.isClient()) {
			LangManager.write();
		}
	}
}