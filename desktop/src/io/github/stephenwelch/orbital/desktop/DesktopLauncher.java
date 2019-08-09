package io.github.stephenwelch.orbital.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.stephenwelch.orbital.game.OrbitalGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.samples = 16;
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new OrbitalGame(), config);
	}
}
