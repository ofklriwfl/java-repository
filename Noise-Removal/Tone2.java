
public class Tone2 {
	//methods written by my capstone professor
	public static byte[] addNoise(byte[] wave) {
		byte[] noisywave = new byte[wave.length];
		for (int i = 0; i < wave.length; ++i) {
			double noise = wave[i] + (Math.random() - 0.5) * 50;
			noisywave[i] = (byte) ((noise < 127) ? ((noise > -128) ? noise : -128) : 127);
		}
		return noisywave;
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
		
		// -- perform the FFT and the power spectral density
		Complex[] waveformf = FFT.fft(waveformc);
		
		// -- calculate the power spectral density of the FFT
		Complex[] conjugate = new Complex[waveformf.length];
		for (int i = 0; i < conjugate.length; ++i) {
			conjugate[i] = waveformf[i].conjugate();
		}
		double[] PSD = new double[waveformf.length];
		for (int i = 0; i < conjugate.length; ++i) {
			PSD[i] = waveformf[i].times(conjugate[i]).re() / conjugate.length;
		}

		// -- filter FFT
		Complex[] filtered = new Complex[waveformf.length];
		for (int i = 0; i < waveform.length; ++i) {
			if (PSD[i] < 1000) {
				filtered[i] = new Complex(0, 0);
			}
			else {
				filtered[i] = new Complex(waveformf[i].re(), waveformf[i].im());		
			}
		}

		// -- compute IFFT
		Complex[] waveformH = FFT.ifft(filtered);
		
		// -- copy the real part
		byte[] result = new byte[waveformH.length];
		for (int i = 0; i < waveformH.length; ++i) {
			result[i] = (byte)(waveform[i].re());
		}
		return result;
	}
	public static void (generateAndPlayTone(int hz, int meses, int volume) throws LineUnavailableException {
		try {
			PrintWriter pw = new PrintWriter(new File("tone.txt"));
			for (int i = 0; i < msecs * frequency / 1000; i++) {
				double angle = i / (frequency / hz) * 2.0 * Math.PI;
				buf[0] = (byte) (Math.sin(angle) * volume);
				sdl.write(buf, 0, 1);
				pw.println(buf[0]);
			}
		}
	}
	public static void main(String[] args) {
	}
}