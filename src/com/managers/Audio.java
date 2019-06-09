package com.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio {
	public void playSound(String filepath) {
		InputStream music;
		
		try {
			music = new FileInputStream(new File(filepath));
			AudioStream audio = new AudioStream(music);
			AudioPlayer.player.start(audio);
		}
		catch(Exception e) {
			System.out.println("error");
		}
	}
}
