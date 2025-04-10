import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;

//Written by my capstone professor based on code by Andrew Thompson (2007/12/6) https://forums.oracle.com/ords/apexds/post/example-code-to-generate-audio-tone-8529
public class Tone extends JFrame {
	static AudioFormat af;
	static SourceDataLine sdl;
	public Tone(){
	}
	public static byte[] generateTone(int hz, int msecs, int volume, boolean addHarmonic) {
		
		float frequency = 44100;
		int N = (int) (msecs * frequency / 1000);
		byte[] wave = new byte[N];
		
		byte[] buf;
		
		if (addHarmonic) {
			buf = new byte[2];
		} else {
			buf = new byte[1];
		}

		for (int i = 0; i < N; i++) {
			double angle = i / (frequency / hz) * 2.0 * Math.PI;
			buf[0] = (byte) (Math.sin(angle) * volume);

			if (addHarmonic) {
				double angle2 = (i) / (frequency / hz) * 2.0 * Math.PI;
				buf[1] = (byte) (Math.sin(2 * angle2) * volume * 0.6);
			} else {
				wave[i] = buf[0];
			}
		}
		return wave;
	}

	public static byte[] addNoise(byte[] wave) {
		byte[] noisywave = new byte[wave.length];

		for (int i = 0; i < wave.length; ++i) {
			double noise = wave[i] + (Math.random() - 0.5) * 50;
			noisywave[i] = (byte) ((noise < 127) ? ((noise > -128) ? noise : -128) : 127);

		}
		return noisywave;
	}

	public static void playTone(byte[] wave) throws LineUnavailableException {
		AudioFormat af;
		float frequency = 44100;
		af = new AudioFormat(frequency, 8, 1, true, false);
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		for (byte b : wave) {
			byte[] buf = { b };
			sdl.write(buf, 0, 1);
		}

		sdl.drain();
		sdl.stop();
		sdl.close();

	}

	public static void writeTone(byte[] wave, String csvname) throws FileNotFoundException {

		try {
			PrintWriter pw = new PrintWriter(new File(csvname));
			for (int i = 0; i < wave.length; i++) {
				pw.println(wave[i]);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	public static byte[] processWave(byte[] wave) {

		// -- make sure length is a power of 2
		int e = 1;
		while (Math.pow(2, e) < wave.length) {
			++e;
		}
		--e;

		// -- move to a Complex Number array
		Complex[] waveformc = new Complex[(int) Math.pow(2, e)];
		for (int i = 0; i < waveformc.length; ++i) {
			waveformc[i] = new Complex(wave[i], 0);
		}

		Complex[] waveformf = FFT2.fft(waveformc);

		Complex[] conjugate = new Complex[waveformf.length];
		for (int i = 0; i < conjugate.length; ++i) {
			conjugate[i] = waveformf[i].conjugate();
		}
		double[] PSD = new double[waveformf.length];
		for (int i = 0; i < conjugate.length; ++i) {
			PSD[i] = waveformf[i].times(conjugate[i]).re() / conjugate.length;
		}

		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("fft.csv"));
			for (int i = 0; i < PSD.length; ++i) {
				pw.println(PSD[i]);
			}
			pw.close();
		}
		catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}


		// -- filter the FFT with a simple threshold
		Complex[] filtered = new Complex[waveformf.length];
		for (int i = 0; i < waveformf.length; ++i) {
			if (PSD[i] < 1000) {
				filtered[i] = new Complex(0, 0);
			}
			else {
				filtered[i] = new Complex(waveformf[i].re(), waveformf[i].im());
			}
		}

		// -- compute the IFFT of the filtered FFT
		Complex[] waveformi = FFT2.ifft(filtered);

		// -- copy the real part to a waveform (byte) array
		byte[] result = new byte[waveformi.length];
		for (int i = 0; i < waveformi.length; ++i) {
			result[i] = (byte) (waveformi[i].re());
		}
		return result;

	}

	public static void mainConsole(String[] args) {
		try {
			// -- generate the clean tone
			byte[] wave = generateTone(1000, 2000, 50, false);
			writeTone(wave, "clean.csv");
			playTone(wave);
			wave = addNoise(wave);
			writeTone(wave, "noisy.csv");
			playTone(wave);
			byte[] filtered = processWave(wave);
			playTone(filtered);
			writeTone(filtered, "filtered.csv");

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	public static void main (String[] args) {
		mainConsole(args);
	}
}