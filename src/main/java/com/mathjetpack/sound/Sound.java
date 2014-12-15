package mathjetpack.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;


public class Sound {
    
    private HashMap<String, Clip[]> mSounds;

    public Sound() {
       
	mSounds = new HashMap<String, Clip[]>();

	Clip clips[];
	
	int clipNum;
	String clipInfo[] = null;

	BufferedReader reader = null;
	try {
	    //	    reader = new BufferedReader(new FileReader(new File(Sound.class.getResource("/sound/sounds.txt").toURI())));
	    reader = new BufferedReader(new FileReader("target/classes/sound/sounds.txt"));
	    
	    String line;
	    while((line = reader.readLine()) != null) {
	    
		clipInfo = line.split(" ");
		clipNum = Integer.parseInt(clipInfo[2]);

		clips = new Clip[clipNum];
		for(int i = 0; i < clipNum; i++) {
		    clips[i] = AudioSystem.getClip();
		    clips[i].open(AudioSystem.getAudioInputStream(Sound.class.getResource(clipInfo[1])));
		}
		mSounds.put(clipInfo[0], clips);
	    }
	}
	catch(Exception e) {
	    System.out.println("Error: Failed to load the sounds." + e);
	}
	finally {
	    try {
		reader.close();
	    }
	    catch(Exception e) {
		System.out.println("Error: Failed to load the sounds.");
	    }
	}
    }

    public void play(String sound) {
	play(sound, false);
    }    

    public void play(String sound, final boolean loop) {
	
	final Clip clips[] = mSounds.get(sound);

	for(Clip clip : clips) {
	    if(!clip.isRunning()) {
		clip.setFramePosition(0);
		if(loop)
		    clip.loop(Clip.LOOP_CONTINUOUSLY);
		else
		    clip.start();
		    
		break;
	    }
	}
    }
    
    public void stop(String sound) {
	mSounds.get(sound)[0].stop();
    }
}
