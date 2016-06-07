package com.lpoo.project.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lpoo.project.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "AnimalFlip";
		//config.fullscreen = true;
		//config.resizable = false;
		config.addIcon( "Icons\\Icon_Large.png", Files.FileType.Internal);
		config.addIcon( "Icons\\Icon_Medium.png", Files.FileType.Internal);
		config.addIcon( "Icons\\Icon_Small.png", Files.FileType.Internal);
		new LwjglApplication(MyGame.getInstance(), config);
	}
}
