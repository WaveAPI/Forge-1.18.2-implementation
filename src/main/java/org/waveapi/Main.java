package org.waveapi;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.waveapi.api.WaveLoader;
import org.waveapi.api.content.items.WaveShapedRecipe;
import org.waveapi.api.misc.Side;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;
import org.waveapi.utils.FileUtil;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod("waveapi")
public class Main {
	public static final Logger LOGGER = LogManager.getLogger("WaveAPI");

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

		new ResourcePackManager();

		WaveLoader.init();

		Set<String> loaded = new HashSet<>();


		for (Map.Entry<String, WaveLoader.WrappedWaveMod> mod : WaveLoader.getMods().entrySet()) {
			bake = mod.getValue().changed;
			if (bake) {
				FileUtil.recursivelyDelete(new File(ResourcePackManager.getInstance().getPackDir(), "data/" + mod.getValue().mod.name));
				FileUtil.recursivelyDelete(new File(ResourcePackManager.getInstance().getPackDir(), "assets/" + mod.getValue().mod.name));
			}
			loaded.add(mod.getValue().mod.name);
			mod.getValue().mod.init();
		}

		File[] files = new File(ResourcePackManager.getInstance().getPackDir(), "data").listFiles();
		if (files != null) {
			for (File f : files) {
				if (!loaded.contains(f.getName())) {
					FileUtil.recursivelyDelete(f);
				}
			}
		}

		files = new File(ResourcePackManager.getInstance().getPackDir(), "assets").listFiles();
		if (files != null) {
			for (File f : files) {
				if (!loaded.contains(f.getName())) {
					FileUtil.recursivelyDelete(f);
				}
			}
		}

		WaveShapedRecipe.build(new File(mainFolder, "resource_pack/data"));

		if (Side.isClient()) {
			LangManager.write();
		}
	}
}