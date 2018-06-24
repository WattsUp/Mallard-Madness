package managers;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {

	private static synchronized void playSound(final String url) {
		Runnable play = new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				Clip clip = null;
				try {
					File f = new File("sound/" + url);
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
					clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		play.run();
	}

	public static void playSoundStart() {
		playSound("CHARGE.wav");
	}

	public static void playSoundWarning() {
		playSound("WARNEOM.wav");
	}

	public static void playSoundSoftStop() {
		playSound("ENDMATCH.wav");
	}

	public static void playSoundHardStop() {
		playSound("FOGHORN.wav");
	}
}
