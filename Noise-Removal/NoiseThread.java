import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class NoiseThread extends Thread {
	static boolean playing = false;
	static byte[] byteArray;
	static int startIndex;
	static int increment;
	static SourceDataLine sourceDataLine;
	//static int index;
	volatile static int index;
	
	public NoiseThread() {
		
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (playing == true) {
				try {
				byte[] buffer = new byte[4];
				AudioFormat audioFormat = new AudioFormat(176400, 8, 1, true, false);
				//AudioFormat audioFormat = new AudioFormat(88200, 8, 1, true, false);
				//SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
				sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
				//for (int index = startIndex; index < byteArray.length - 24; index += increment) {
				//for (index = startIndex; index < byteArray.length - 24; index += increment) {
				index = startIndex;
				System.out.println("Starting");
				//Exit condition not reached?
				//while (index < byteArray.length - 24) {
				//while (index < NoiseGUI.playSound.getMaximum() - 4) {
				while ((index < NoiseGUI.playSound.getMaximum() - 4) && (NoiseGUI.sound.play != false)) {
					//if (playing != false) {
					//Thread.onSpinWait();
					//System.out.println("Starting");
					//if (NoiseGUI.sound.play != false) {
					if (NoiseGUI.playNow != false) {
						buffer[0] = (byte) (byteArray[index]);
						buffer[1] = (byte) (byteArray[index + 1]);
						buffer[2] = (byte) (byteArray[index + 2]);
						buffer[3] = (byte) (byteArray[index + 3]);
						sourceDataLine.write(buffer, 0, 4);
						NoiseGUI.playSound.setValue(NoiseGUI.playSound.getValue() + 4);
						NoiseGUI.updateTime();
						//index += increment;
						//index = NoiseGUI.sound.index + increment;
						index = NoiseGUI.playSound.getValue();
						if (index > 4) {
							while (index % 4 != 0) {
								index--;
							}
						} else {
							System.out.println("Restarting");
							index = 0;
						}
						//NoiseGUI.sound.index = index;
					}
					//System.out.println("Still playing");
				}
				//Not reached
				System.out.println("Stopping");
				sourceDataLine.drain();
				sourceDataLine.stop();
				sourceDataLine.close();
				playing = false;
				Modify.play = false;
				NoiseGUI.sound.play = false;
				NoiseGUI.playNow = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void newArray(byte[] array) {
		byteArray = array;
	}
	
	public void selectIndex(int index) {
		startIndex = index;
	}
	
	public void chooseIncrement(int num) {
		increment = num;
	}
	
	public void bool (boolean b) {
		playing = b;
		System.out.println("Boolean changed");
	}
}
