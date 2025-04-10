import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import javax.sound.sampled.Clip;

public class Modify {
	/*public static int[] ft (int y) {
		int[] x = new int[44100/2];
		//double volume = -2 * i * Math.PI / x.length;
		double volumePart = -2 * Math.PI / x.length;
		for (int i = 0; i < 44100/2; i++) {
			x[i] = (int) Math.cos(volumePart * i);
		}
		return x;
	}
	public static int ift(int[] y) {
		double sumPartOne = 0;
		//double angle = 44100/1000 * i * 2.0 * Math.PI;
		double anglePart = 44100/1000 * 2.0 * Math.PI;
		for (int i = 0; i < y.length; i++) {
			sumPartOne += Math.sin(anglePart * i) * y[i];
		}
		int sumPartTwo = (int) sumPartOne;
		return sumPartTwo;
	}
	*/
	
	static File file1;
	static File file2;
	static FileInputStream input;
	static FileOutputStream output;
	static byte[] bytes;
	static Complex[] filtered;
	static Complex[] waveformH;
	static byte[] result;
	static boolean processed = false;
	static byte[] processedAudio;
	static boolean play = false;
	static SourceDataLine sourceDataLine;
	static int increment = 8;
	//static int index = 0;
	volatile static int index = 0;
	
	public Modify(File importFile) {
		file1 = importFile;
		importSound();
	}
	
	//This method is based on code written by Dr. Craig Reinhart for Tone.java
	public static byte[] addNoise(byte[] wave) {
		byte[] noisywave = new byte[wave.length];

		for (int i = 0; i < wave.length; ++i) {
			//double noise = wave[i] + (Math.random() - 0.5) * 50;
			//double noise = wave[i] + (Math.random() - 0.5) * 15;
			double noise = wave[i] + (Math.random() - 0.5) * 4;
			//double noise = wave[i] + (Math.random() - 0.5) * 2;
			noisywave[i] = (byte) ((noise < 127) ? ((noise > -128) ? noise : -128) : 127);
			//noisywave[i] = (byte) noise;
		}
		return noisywave;
	}
	
	public static void importSound() {
		//public static void main(String[] args) {
		// TODO Auto-generated method stub
		//File file1 = new File("Untitled 8.wav");
		//File file2 = new File("Dual 17" + ".wav");
		//File sample = new File("Sample.wav");
		Random random = new Random();
		
		try {
			//FileInputStream input = new FileInputStream(file1); 
			//FileInputStream input = new FileInputStream(file1);
			input = new FileInputStream(file1);
			//FileOutputStream output = new FileOutputStream(file2);
			//FileWriter writer = new FileWriter(file2);
			
			
			//byte[] bytes = input.readAllBytes();
			bytes = input.readAllBytes();
			/*File original = new File("original.txt");
			FileOutputStream originalOutput = new FileOutputStream(original);
			//for (int i = 0; i < bytes.length; i++) {
			//for (int i = 0; i < bytes.length; i += 4) {
			//for (int i = 49; i < bytes.length; i += 4) {
			for (int i = 49; i < bytes.length - 4; i += 4) {
				//originalOutput.write(bytes[i]);
				int sample = ((int) (bytes[i] << 24) + (int) (bytes[i + 1] << 16) + (int) (bytes[i + 2] << 8) + (int) (bytes[i + 3]));
				//char[] number = (bytes[i] + " ").toCharArray();
				char[] number = (sample + "").toCharArray();
				for (char num : number) {
					originalOutput.write(num);
				}
				originalOutput.write('\n');
			
			}
			*/
			processed = false;
			//NoiseGUI.playSound.setMaximum(bytes.length - 48);
			//NoiseGUI.playSound.setMaximum((int) (bytes.length / 2.0) - 48);
			/*if (increment == 4) {
				NoiseGUI.playSound.setMaximum(bytes.length - 48);
			} else {
				NoiseGUI.playSound.setMaximum((int) (bytes.length / 2.0) - 48);
			}
			*/
			if (increment == 4) {
				NoiseGUI.playSound.setMaximum(bytes.length - 49);
			} else {
				NoiseGUI.playSound.setMaximum((int) (bytes.length / 2.0) - 49);
			}
			int byteLength = NoiseGUI.playSound.getMaximum();
			//int seconds = (int) (byteLength / 44100.0) % 60;
			//int totalSeconds = (int) (byteLength / 44100.0);
			int totalSeconds = (int) (byteLength / 176400.0);
			int seconds = totalSeconds % 60;
			//int hours = (int) (seconds / 60.0) % 60;
			//int hours = (int) (seconds / (60 * 60));
			int minutes = (int) (totalSeconds / 60.0) % 60;
			//int minutes = hours % 60;
			//int minutes = (int) (seconds / 60.0) - (hours * 60);
			int hours = (int) (totalSeconds / 60.0 / 60.0);
			String lengthText = "";
			if (hours < 10) {
				lengthText += ("0" + hours + ":");
			} else {
				lengthText += (hours + ":");
			}
			if (minutes < 10) {
				lengthText += ("0" + minutes + ":");
			} else {
				lengthText += (minutes + ":");
			}
			if (seconds < 10) {
				if (seconds < 0) {
					seconds = 0;
				}
				lengthText += ("0" + seconds);
			} else {
				lengthText += seconds;
			}
			NoiseGUI.playMoment.setText("00:00:00/" + lengthText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void oldProcessSound(int limitnum) {
		processed = true;
		//public static void main(String[] args) {
			try {
			//byte[] integers = input.readAllBytes();
			/*for (int i = 0; i < 48; i++) {
				output.write(bytes[i]);
			}
			*/
			//FileInputStream sampleStream = new FileInputStream(sample);
			//byte[] sampleBytes = sampleStream.readAllBytes();
			/*for (int out = 0; out < 50; out++) {
				//for (int number = -48000; number < 48000; number += random.nextInt(1200)) {
					for (int i = 0; i < 8048; i++) {
						int number = random.nextInt(200) * (int) (Math.pow(-1, 2));
						byte[] parts = new byte[]{(byte) (number << 24), (byte) (number 
						<< 16), (byte) (number << 8), (byte)(number)};
						for (byte part : parts) {
							output.write(part);
						}
					}
				//}
			}
			*/
			/*for (int number = 36000; number < 12000; number -= 2) {
				byte[] parts = new byte[]{(byte) (number << 24), (byte) (number << 16), (byte) (number << 8), (byte)(number)};
				for (byte part : parts) {
					output.write(part);
				}
			}
			*/
			//int addition = -12;
			//int[] integers = new int[((bytes.length - 48) / 4) + ((bytes.length - 48) % 4)];
			//int[] integers = new int[bytes.length];
			//for (int i = 48; i < bytes.length - (bytes.length % 4); i += 4) {
			/*for (int i = 48; i < integers.length; i += 4) {
				int number = ((int) (bytes[i] << 24) + (int) (bytes[i + 1] << 16) + (int) (bytes[i + 2] << 8) + (int) (bytes[i + 3]));
				number *= 0.00036;
				byte[] parts = new byte[]{(byte) (number << 24), (byte) (number << 16), (byte) (number << 8), (byte)(number)};
				integers[i] = parts[0];
				integers[i + 1] = parts[1];
				integers[i + 2] = parts[2];
				integers[i + 3] = parts[3];
				
			}
			*/
			//for (int i = 48; i < bytes.length; i += 4) {
			int i = 48;
			//int sampleIndex = 48;
			//int sampleIndex2 = 0;
			/*int[][] samples = new int[sampleBytes.length / 4][44100/2];
			while (sampleIndex < sampleBytes.length - 8) {
				int sampleNum = ((int) (sampleBytes[sampleIndex] << 24) + (int) (sampleBytes[sampleIndex + 1] << 16) + (int) (sampleBytes[sampleIndex + 2] << 8) + (int) (sampleBytes[sampleIndex + 3]));
				//int[] sampleLevel = new int[44100/2];
				for (int f = 0; f < 44100/2; f++) {
					samples[sampleIndex2][f] = (int) (sampleNum * Math.pow(Math.E, (2 * Math.PI * f * i)));
				}
				sampleIndex2++;
			}
			sampleIndex2 = 0;
			*/
			//int[] sampleLevel = new int[44100/2];
			//Complex[] complexNums = new Complex[bytes.length / 4];
			//LAST: while (i < bytes.length - 8) {
				//int number = ((int) (bytes[i] << 3) + (int) (bytes[i + 1] << 2) + (int) (bytes[i + 2] << 1) + (int) (bytes[i + 3]));
				//LAST: int number = ((int) (bytes[i] << 24) + (int) (bytes[i + 1] << 16) + (int) (bytes[i + 2] << 8) + (int) (bytes[i + 3]));
				/*Complex[] complexNums = new Complex[4];
				complexNums[0] = new Complex(bytes[i], 0);
				complexNums[1] = new Complex(bytes[i + 1], 0);
				complexNums[2] = new Complex(bytes[i + 2], 0);
				complexNums[3] = new Complex(bytes[i + 3], 0);
				*/
				//Begin new
				/*Complex[] complexNums = new Complex[2];
				complexNums[0] = new Complex(number, 0);
				number = ((int) (bytes[i + 4] << 24) + (int) (bytes[i + 5] << 16) + (int) (bytes[i + 6] << 8) + (int) (bytes[i + 7] << 8) + (int) (bytes[i + 4]));
				complexNums[1] = new Complex(number, 0);
				*/
				//End new
				//Complex[] complexNums = new Complex[number];
				//int sampleNum = ((int) (sampleBytes[sampleIndex] << 24) + (int) (sampleBytes[sampleIndex + 1] << 16) + (int) (sampleBytes[sampleIndex + 2] << 8) + (int) (sampleBytes[sampleIndex + 3]));
				/*if (number == addition) {
					number = 0;
				}
				*/
				//int number = ((int) (bytes[i]) + (int) (bytes[i + 1] << 8) + (int) (bytes[i + 2] << 16) + (int) (bytes[i + 3] << 24));
				//System.out.println(number);
				//byte[] parts = new byte[]{(byte) (number >> 3), (byte) (number >> 2), (byte) (number >> 1), (byte)(number)};
				//byte[] parts = new byte[]{(byte) (number), (byte) (number >> 8), (byte) (number >> 16), (byte)(number >> 24)};
				/*if (number > -15500 && number < 15500) {
					number = 0;
				} else {
					number /= 2;
				}
				*/
				/*if (number > 24000 && number < 46000) {
					number = 0;
				}
				if (number > -46000 && number < -24000) {
					number = 0;
				}
				if (number < -100000) {
					number = 0;
				}
				if (number > 100000) {
					number = 0;
				}
				*/
				/*if (number < -9000) {
					number *= 8;
				} else if (number > 9000) {
					number /= 8;
				} else {
					number = 0;
				}
				*/
				/*if (number > -500 && number < 500) {
					number = 0;
				}
				*/
				/*if (number < -20000) {
					number = 0;
				}
				if (number > 20000) {
					number = 0;
				}
				if (number > -15000 && number < -500) {
					number = 0;
				} else if (number > 500 && number < 15000) {
					number = 0;
				}
				*/
				/*if (number > -8500 && number < 8500) {
					number = 0;
				}
				*/
				//byte[] parts = new byte[]{(byte) (number >> 24), (byte) (number >> 16), (byte) (number >> 8), (byte)(number)};
				//LAST: int x = 4;
				/*if (number < 0 && (number + x) < 0) {
					if ((number + x) < 0) {
						number += x;
					}
					number += x;
				} else {
					if ((number + x) > 0) {
						number = 0;
					}
				}
				if (number > 0 && (number - x) > 0) {
					if ((number - x) < 0) {
						number -= x;
					}
					number -= x;
				} else {
					if ((number - x) < 0) {
						number = 0;
					}
				}
				*/
				/*if (number + x < 0) {
					number += x;
				} else if (number - x > 0) {
					number -= x;
				} else {
					number = 0;
				}
				*/
				/*if (number - x > 0) {
					number += (int) Math.pow(number, 2);
				} else if (number + x < 0) {
					number -= (int) Math.pow(number, 2);
				} else {
					number = 0;
				}
				*/
				//number *= 0.0000002;
				//number *= 0.00064;
				/*if ((number * 0.00004) > -2 && (number * 0.00004 < 2)) {
					number = 0;
				}
				*/
				/*if (number * 0.00001 == 0) {
					number = 0;
				}
				*/
				//*/int otherNumber = ((int) (integers[i] << 24) + (int) (integers[i + 1] << 16) + (int) (integers[i + 2] << 8) + (int) (integers[i + 3]));
				/*if (otherNumber == 0) {
					number = 0;
				} else {
					number *= 0.004;
				}
				*/
				/*/if (otherNumber == 0) {
					number = 0;
				} else {
					if (number < -31) {
						number += 32;
					} else if (number > 31) {
						number -= 32;
					}
				}
				/*/
				
				//number *= 0.00044;
				//number -= sampleNum / 2.0;
				
				/*if ((number < 0) && (sampleNum > 0)) {
					number += sampleNum;
				} else if ((number < 0) && (sampleNum < 0)){
					number -= sampleNum;
				} else if ((number > 0) && (sampleNum < 0)) {
					number += sampleNum;
				} else if ((number > 0) && (sampleNum > 0)) {
					number -= sampleNum;
				}
				*/
				/*int[] level = new int[32768/2];
				for (int f = 0; f < 32768/2; f++) {
					System.out.print(number * Math.pow(Math.E, (2 * Math.PI * f * i)) + " ");
					level[f] = (int) (number * Math.pow(Math.E, (2 * Math.PI * f * i)));
					//sampleLevel[f] = (int) (sampleNum * Math.pow(Math.E, (2 * Math.PI * f * i)));
				}
				*/
				//complexNums[(i - 48) / 4] = new Complex(number, 0);
				/*for (int num : level) {
					if (num < 95) {
						num = 0;
					}
				}
				*/
				//System.out.println();
				//Other recent
				/*int sum = 0;
				//int sampleSum = 0;
				for (int num = 0; num < 44100/2; num++) {
					sum += (int) (level[num] * Math.pow(Math.E, (2 * Math.PI * (44100/1000) * num)));
					//sum -= (int) (samples[sampleIndex2][num] * Math.pow(Math.E, (2 * Math.PI * (44100/1000) * num)));
					sum -= (int) (sampleLevel[num] * Math.pow(Math.E, (2 * Math.PI * (44100/1000) * num)));
				}
				//number = sum;
				if (sum < 0) {
					sum = 0;
				}
				number = sum;
				*/ 
				/*for (int y : level) {
					complexNums[y] = new Complex(y, 0);
				}
				*/
				//for (int y = 0; y < 44100/2; y++) {
				/*for (int y = 0; y < 32768/2; y++) {
					complexNums[y] = new Complex(level[y], 0);
				}
				complexNums = FFT.fft(complexNums);
				complexNums = FFT.ifft(complexNums);
				int sum = 0;
				for (Complex y : complexNums) {
					sum += (int) y.abs();
				}
				*/
				/*Complex[] complexNums = new Complex[44100];
				for (int index = 0; index < complexNums.length; index++) {
					complexNums[index] = new Complex(0, 0);
				}
				*/
				//LAST: i += 4;
				//i += 8;
			//}
				//complexNums = FFT.fft(complexNums);
				//complexNums = FFT.fft(complexNums, 44100);
				//End new
				//LAST
				/*int[] results = ft(number);
				for (int result : results) {
					if (result < 5) {
						result = 0;
					}
				}
				*/
				//END LAST
			//Following code for this method is based on Dr. Craig Reinhart's code from Tone.java
			int pow = 1;
			while (Math.pow(2, pow) < bytes.length) {
				
				++pow;
			}
			//--e;
			int more = (int) Math.pow(2, pow) - bytes.length;
			Complex[] waveformc = new Complex[(int) Math.pow(2, pow)];
			/*for (int index = 0; index < waveformc.length; index++) {
				waveformc[index] = new Complex(0, 0);
			}
			*/
			//for (int index = 0; index < waveformc.length; ++index) {
			for (int index = 0; index < bytes.length - 1604; ++index) {
				//waveformc[index] = new Complex(bytes[index + 48], 0);
				waveformc[index] = new Complex(bytes[index + 49], 0);
			}
			
			Complex noNoise = new Complex(0, 0);
			for (int index = bytes.length - 1604; index < waveformc.length; index++) {
				//waveformc[index] = new Complex(0, 0);
				waveformc[index] = noNoise;
			}

			// -- perform the FFT and the power spectral density
			Complex[] waveformf = FFT2.fft(waveformc);
			//Complex[] waveformf = new Complex[waveformc.length];
			/*for (int index = 0; index < waveformf.length; index++) {
				waveformf[index] = new Complex(0, 0);
			}
			*/

			// -- calculate the power spectral density of the FFT
			Complex[] conjugate = new Complex[waveformf.length];
			for (int index = 0; index < conjugate.length; ++index) {
				conjugate[index] = waveformf[index].conjugate();
			}
			double[] PSD = new double[waveformf.length];
			for (int index = 0; index < conjugate.length; ++index) {
				PSD[index] = waveformf[index].times(conjugate[index]).re() / conjugate.length;
			}

			// -- filter FFT
			//boolean lessThanZero = false;
			int maximum = 0;
			//Complex[] filtered = new Complex[waveformf.length];
			//filtered = new Complex0[waveformf.length];
			filtered = new Complex[waveformf.length];
			/*File volume = new File("volume.txt");
			FileOutputStream volumeOutput = new FileOutputStream(volume);
			byte[] PSD2 = new byte[PSD.length];
			for (int index2 = 0; index2 < PSD.length; index2++) {
				PSD2[index] = (byte) PSD2[index];
			}
			//PSD2 = addNoise(PSD2);
			//for (int index2 = 0; index2 < PSD.length; index2++) {
				////PSD[index2] = PSD2[index2];
				//PSD[index2] = (PSD2[index2] >= 0) ? PSD2[index2] : 0;
			//}
			char[] number = ((int) PSD[index] + "").toCharArray();
			for (char num : number) {
				volumeOutput.write(num);
			}
			volumeOutput.write('\n');
			*/
			for (int index = 0; index < waveformf.length; ++index) {
				/*if (PSD[index] > maximum) {
					maximum = ((int) PSD[index]) + 1;
				}
				*/
				
				/*System.out.print(PSD[index] + " ");
				if (index % 80 == 0) {
					System.out.println();
				}
				*/
				//if (PSD[index] < 100 || PSD[index] > 126) {
				//if (PSD[index] < 100) {
				//volumeOutput.write((byte) PSD[index]);
				//char[] number = (PSD[index] + "").toCharArray();
				/*byte[] PSD2 = new byte[PSD.length];
				for (int index2 = 0; index2 < PSD.length; index2++) {
					PSD2[index] = (byte) PSD2[index];
				}
				PSD2 = addNoise(PSD2);
				for (int index2 = 0; index2 < PSD.length; index2++) {
					PSD[index2] = PSD2[index2];
				}
				char[] number = ((int) PSD[index] + "").toCharArray();
				for (char num : number) {
					volumeOutput.write(num);
				}
				volumeOutput.write('\n');
				*/
				if (PSD[index] < limitnum) {
					//filtered[index] = new Complex(0, 0);
					filtered[index] = noNoise;
				}
				
				//else {
				
				/*else if (PSD[index] > 126) {
					filtered[index] = new Complex(waveformf[index].re() * 8, waveformf[index].im() * 8);
					//System.out.println(filtered[index]);
				/*if (PSD[index] < 0) {
					lessThanZero = true;
				}
				*/ 
				
				
				
				//System.out.print(PSD[index] + " ");
				//} 
				else {
					filtered[index] = new Complex(waveformf[index].re() / PSD[index] * 14, waveformf[index].im() / PSD[index] * 14);
				//}
				}
			}
			//System.out.println(lessThanZero);
			//System.out.println(maximum);
			//processedAudio = new byte[filtered.length];
			/*processedAudio = new byte[(int) (filtered.length / 2.0)];
			//for (int index = 0; index < processedAudio.length; i++) {
			for (int index = 0; index < (int) (processedAudio.length); index++) {
				//processedAudio[i] = (byte) filtered[i].re();
				processedAudio[index] = (byte) filtered[index].re();
			}
			*/
			

			// -- compute IFFT
			//Complex[] waveformH = FFT2.ifft(filtered);
			
			waveformH = FFT2.ifft(filtered);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//New
			//byte[] result = new byte[waveformH.length];
			//result = new byte[waveformH.length];
			//result = new byte[(int)(waveformH.length / 2.0)];
			result = new byte[(int) bytes.length];
			//for (int index = 0; index < waveformH.length; index++) {
			//try {
				/*File after  = new File("after.txt");
				FileOutputStream afterStream = new FileOutputStream(after);
				*/
				for (int index = 0; index < result.length; index++) {
					result[index] = (byte) (waveformH[index].re());
					/*try {
						//afterStream.write(result[index]);
						int sample = ((int) (result[index] << 24) + (int) (result[index + 1] << 16) + (int) (result[index + 2] << 8) + (int) (result[index + 3]));;
						//char[] number = ((int) result[index] + "").toCharArray();
						char[] number = (sample + "").toCharArray();
						for (char num : number) {
							afterStream.write(num);
						}
						afterStream.write('\n');
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
				}
			/*} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			//NoiseGUI.playSound.setMaximum(result.length - 48);
			//NoiseGUI.playSound.setMaximum((int) (result.length / 8.0) - 48);
			/*if (increment == 4) {
				NoiseGUI.playSound.setMaximum(bytes.length - 48);
			} else {
				NoiseGUI.playSound.setMaximum((int) (bytes.length / 2.0) - 48);
			}
			*/
			//
				// -- make sure length is a power of 2
				int e = 1;
				while (Math.pow(2, e) < bytes.length) {
					++e;
				}
				--e;

				// -- move to a Complex Number array
				Complex[] waveformc = new Complex[(int) Math.pow(2, e)];
				for (int i = 48; i < waveformc.length; ++i) {
					waveformc[i] = new Complex(bytes[i], 0);
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
					if (PSD[i] < limitnum) {
						filtered[i] = new Complex(0, 0);
					}
					else {
						filtered[i] = new Complex(waveformf[i].re(), waveformf[i].im());
					}
				}

				// -- compute the IFFT of the filtered FFT
				Complex[] waveformi = FFT2.ifft(filtered);

				// -- copy the real part to a waveform (byte) array
				//byte[] result = new byte[waveformi.length];
				//for (int i = 0; i < waveformi.length; ++i) {
				for (int i = 0; i < result.length; ++i) {
					result[i] = (byte) (waveformi[i].re());
				}
			if (increment == 4) {
				//NoiseGUI.playSound.setMaximum(result.length - 48);
				NoiseGUI.playSound.setMaximum(result.length - 49);
			} else {
				//NoiseGUI.playSound.setMaximum((int) (result.length / 2.0) - 48);
				NoiseGUI.playSound.setMaximum((int) (result.length / 2.0) - 49);
			}
			int byteLength = NoiseGUI.playSound.getMaximum();
			//int seconds = (int) (byteLength / 44100.0) % 60;
			//int totalSeconds = (int) (byteLength / 44100.0) % 60;
			//int totalSeconds = (int) (byteLength / 44100.0);
			int totalSeconds = (int) (byteLength / 176400.0);
			int seconds = totalSeconds % 60;
			//int hours = (int) (seconds / 60.0) % 60;
			//int hours = (int) (seconds / (60 * 60));
			int minutes = (int) (totalSeconds / 60.0) % 60;
			//int minutes = hours % 60;
			//int minutes = (int) (seconds / 60.0) - (hours * 60);
			int hours = (int) (totalSeconds / 60.0 / 60.0);
			String lengthText = "";
			if (hours < 10) {
				lengthText += ("0" + hours + ":");
			} else {
				lengthText += (hours + ":");
			}
			if (minutes < 10) {
				lengthText += ("0" + minutes + ":");
			} else {
				lengthText += (minutes + ":");
			}
			if (seconds < 10) {
				if (seconds < 0) {
					seconds = 0;
				}
				lengthText += ("0" + seconds);
			} else {
				lengthText += seconds;
			}
			NoiseGUI.playMoment.setText("00:00:00/" + lengthText);
			NoiseGUI.playSound.setValue(0);
			//if (limitnum == 0) {
				//result = addNoise(result);
			//}
	}
			
			
	public static void processSound(int limitnum) {
		processed = true;
		//The following code for this method is based on code written by Dr. Craig Reinhart for Tone.java
		// -- make sure length is a power of 2
				int e = 1;
				while (Math.pow(2, e) < bytes.length) {
					++e;
				}
				//--e;

				// -- move to a Complex Number array
				Complex[] waveformc = new Complex[(int) Math.pow(2, e)];
				for (int i = 0; i < bytes.length; ++i) {
				//for (int i = 48; i < bytes.length; ++i) {
					waveformc[i] = new Complex(bytes[i], 0);
				}
				for (int i = bytes.length; i < waveformc.length; i++) {
					waveformc[i] = new Complex(0, 0);
				}

				Complex[] waveformf = new Complex[waveformc.length];
				waveformf = FFT2.fft(waveformc);

				Complex[] conjugate = new Complex[waveformf.length];
				for (int i = 0; i < conjugate.length; ++i) {
					conjugate[i] = waveformf[i].conjugate();
				}
				double[] PSD = new double[waveformf.length];
				for (int i = 0; i < conjugate.length; ++i) {
					PSD[i] = waveformf[i].times(conjugate[i]).re() / conjugate.length;
				}

				/*PrintWriter pw;
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
				*/
				/*PrintWriter pw;
				try {
					pw = new PrintWriter(new File("volume.csv"));
					for (double num : PSD) {
						pw.println((int) num);
					}
					pw.close();
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
				*/
				/*try {
				File volume = new File("volume.txt");
				FileOutputStream volumeOutput = new FileOutputStream(volume);
				
				for (int i = 0; i < PSD.length; i += 4) {
					char[] number = (PSD[i] + "").toCharArray();
					System.out.println(number);
					for (char num : number) {
						volumeOutput.write(num);
					}
					volumeOutput.write('\n');
				
				}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				*/

				// -- filter the FFT with a simple threshold
				Complex[] filtered = new Complex[waveformf.length];
				double sample = PSD[0];
				for (int i = 0; i < waveformf.length; ++i) {
					//System.out.print(PSD[i] + " ");
					if (PSD[i] > sample) {
						sample = PSD[i];
					}
					if (PSD[i] < limitnum) {
						filtered[i] = new Complex(0, 0);
					}
					else {
						filtered[i] = new Complex(waveformf[i].re(), waveformf[i].im());
					}
				}
				System.out.println(sample);
				// -- compute the IFFT of the filtered FFT
				Complex[] waveformi = FFT2.ifft(filtered);

				// -- copy the real part to a waveform (byte) array
				result = new byte[waveformi.length];
				for (int i = 0; i < waveformi.length; ++i) {
					result[i] = (byte) (waveformi[i].re());
				}
				if (increment == 4) {
					//NoiseGUI.playSound.setMaximum(result.length - 48);
					NoiseGUI.playSound.setMaximum(result.length - 49);
				} else {
					//NoiseGUI.playSound.setMaximum((int) (result.length / 2.0) - 48);
					NoiseGUI.playSound.setMaximum((int) (result.length / 2.0) - 49);
				}
				int byteLength = NoiseGUI.playSound.getMaximum();
				//int seconds = (int) (byteLength / 44100.0) % 60;
				//int totalSeconds = (int) (byteLength / 44100.0) % 60;
				//int totalSeconds = (int) (byteLength / 44100.0);
				int totalSeconds = (int) (byteLength / 176400.0);
				int seconds = totalSeconds % 60;
				//int hours = (int) (seconds / 60.0) % 60;
				//int hours = (int) (seconds / (60 * 60));
				int minutes = (int) (totalSeconds / 60.0) % 60;
				//int minutes = hours % 60;
				//int minutes = (int) (seconds / 60.0) - (hours * 60);
				int hours = (int) (totalSeconds / 60.0 / 60.0);
				String lengthText = "";
				if (hours < 10) {
					lengthText += ("0" + hours + ":");
				} else {
					lengthText += (hours + ":");
				}
				if (minutes < 10) {
					lengthText += ("0" + minutes + ":");
				} else {
					lengthText += (minutes + ":");
				}
				if (seconds < 10) {
					if (seconds < 0) {
						seconds = 0;
					}
					lengthText += ("0" + seconds);
				} else {
					lengthText += seconds;
				}
				NoiseGUI.playMoment.setText("00:00:00/" + lengthText);
				NoiseGUI.playSound.setValue(0);
				//if (limitnum == 0) {
					//result = addNoise(result);
				//}
	}
	
	public static void exportSound(String fileName) {
		//result = addNoise(result);
			try {
				//file2 = exportFile;
				file2 = new File(fileName);
				output = new FileOutputStream(file2);
				for (int i = 0; i < 48; i++) {
					output.write(bytes[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		//public static static void main(String[] args) {
			try {
			// -- copy the real part
			if (processed == true) {
			//byte[] result = new byte[waveformH.length];
			//for (int index = 0; index < waveformH.length; ++index) {
			//for (int index = 0; index < waveformH.length - 32; ++index) {
			//result = bytes;
			for (int index = 0; index < result.length - 8; ++index) {
			//for (int index = 0; (index + 49) < result.length - 8; ++index) {
			//for (int index = 0; (index + 49) < bytes.length - 48; ++index) {
				//result[index] = (byte)(waveformH[index].re());
				//output.write((byte)(waveformH[index].re()));
				//if (processed == true) {
					output.write(result[index]);
				//} else {
					//bytes = addNoise(bytes);
					//output.write(bytes[index + 49]);
				//}
			}
			} else {
				/*bytes = addNoise(bytes);
				 */
				//for (int index = 0; (index + 49) < bytes.length - 48; ++index) {
				for (int index = 0; (index + 48) < bytes.length - 48; ++index) {
					output.write(bytes[index]);
				}
			}
				/*float frequency = 44100;
				byte[] buf;
				AudioFormat af;
				buf = new byte[2];
				af = new AudioFormat(frequency,8,2,true,false);
				SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
				sdl = AudioSystem.getSourceDataLine(af);
				sdl.open(af);
				sdl.start();
				for(int i=0; i<8000*frequency/1000; i++){
					double angle = i/(frequency/400)*2.0*Math.PI;
					buf[0]=(byte)(Math.sin(angle)*50);
					double angle2 = (i)/(frequency/400)*2.0*Math.PI;
					buf[1]=(byte)(Math.sin(2*angle2)*60*0.6);
					sdl.write(buf,0,2);
					for (byte num : buf) {
						output.write(buf[0] + buf[1]
								);
					}
				}
				sdl.drain();
				sdl.stop();
				sdl.close();
				*/
		//}
				/*for (Complex y : complexNums) {
					if (y.re() < 20) {
						y = new Complex(0, y.im());
					}
				}
				*/
				/*for (Complex y : complexNums) {
					y = y.conjugate();
					y = y.scale(1.0 / 44100);
					if (y.re() < 5) {
						y = new Complex(0, y.im());
					}
				}
				*/
				//complexNums = FFT.ifft(complexNums);
				//LAST: number = ift(results);
				//int[] combine = new int[(bytes.length - 48) / 4];
				//byte[] combine = new byte[4];
				//Begin new
				/*int[] combine = new int[2];
				*/
				//End new
				/*for (int y = 0; y < (bytes.length - 48)/4; y++) {
					combine[y] = (int) complexNums[y].abs();
				}
				*/
				/*for (int y = 0; y < 4; y++) {
					//combine[y] = (int) complexNums[y].abs();
					combine[y] = (byte) complexNums[y].re();
				}
				*/
				//Begin new
				/*for (int y = 0; y < 2; y++) {
					combine[y] = (int) complexNums[y].re();
				}
				*/
				//End new
				//Most recent
				//byte[] parts = new byte[]{(byte) (number << 24), (byte) (number << 16), (byte) (number << 8), (byte)(number)};
				//Begin new
				/*for (int y : combine) {
				byte[] parts = new byte[] {(byte) (y << 24), (byte) (y << 16), (byte) (y << 8), (byte) (y)};
					for (byte part : parts) {
						//writer.write(part);
						output.write(part);
					}
				}
				*/
				//double sum = 0;
				/*for (Complex y : complexNums) {
					sum += (int) y.re();
				}
				*/
				/*for (int y = 0; y < complexNums.length / 2; y++) {
					sum += (int) complexNums[y].re();
				}
				number = (int) sum;
				*/
				//LAST
				/*byte[] parts = new byte[] {(byte) (number << 24), (byte) (number << 16), (byte) (number << 8), (byte) (number)};
				for (byte part : parts) {
					output.write(part);
				}
				*/
				//END LAST
				/*for (byte part : result) {
					output.write(part);
				}
				*/
				/*for (int index = 0; index < more; index++) {
					output.write(bytes[index]);
				}
				*/
				/*for (int index = 0; index < result.length - bytes.length - 48; index++) {
					output.write(result[index]);
				}
				*/
				/*for (int index = 0; index < result.length; index++) {
					output.write(result[index]);
				}
				*/
				/*for (byte part : combine) {
					output.write(part);
				}
				*/
				/*if (addition == 12) {
					addition = -12;
				} else {
					addition += 8;
				}
				*/
				//integers[i - 48] = number;
				//integers[i] = number;
				//Most recent
				/*i += 4;
				 */
				//if (sampleIndex == sampleBytes.length) {
				/*if (sampleIndex > sampleBytes.length * (4.0 / 5.0)) {
					sampleIndex = 48;
				} else {
					//sampleIndex++;
					sampleIndex += 4;
				}
				*/
				/*if (sampleIndex2 < samples.length - 8) {
					sampleIndex2 = 0;
				} else {
					sampleIndex2++;
				}
				*/
			//LAST: }
			
			/*for (int x : integers) {
				byte[] parts = new byte[]{(byte) (x << 24), (byte) (x << 16), (byte) (x << 8), (byte)(x)};
				for (byte part : parts) {
					//writer.write(part);
					output.write(part);
				}
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void playSound(int index) {
		int num = index;
		if (index > 4) {
			while (index % 4 != 0) {
				num--;
			}
		} else {
			num = 0;
		}
		//System.out.println(index);
		System.out.println(num);
		if (play == true) {
			/*sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();
			*/
			/*NoiseGUI.thread.sourceDataLine.drain();
			NoiseGUI.thread.sourceDataLine.stop();
			NoiseGUI.thread.sourceDataLine.close();
			NoiseGUI.thread.bool(false);
			play = false;
			*/
			//NoiseGUI.thread.index = index;
			//NoiseGUI.thread.index = num;
			index = num;
		}
		
		play = true;
		try {
			byte[] byteArray;
			int startIndex;
			if (processed == true) {
				byteArray = result;
				//byteArray = processedAudio;
				//startIndex = index + 0;
				startIndex = num + 0;
			} else {
				byteArray = bytes;
				//startIndex = index + 48;
				//startIndex = index + 49;
				startIndex = num + 49;
				while (startIndex % 4 != 0) {
					startIndex--;
				}
			}
			//NoiseGUI.playSound.setValue(index);
			NoiseGUI.playSound.setValue(num);
			//byte[] buffer = new byte[1];
			//byte[] buffer = new byte[4];
			//AudioFormat audioFormat = new AudioFormat(44100, 8, 1, true, false);
			//AudioFormat audioFormat = new AudioFormat(44100, 32, 2, true, false);
			//AudioFormat audioFormat = new AudioFormat(44100, 32, 1, true, false);
			//AudioFormat audioFormat = new AudioFormat(176400, 8, 1, true, false);
			//AudioFormat audioFormat = new AudioFormat(22050, 32, 1, true, false);
			//AudioFormat audioFormat = new AudioFormat(44100, 4, 1, true, false);
			//AudioFormat audioFormat = new AudioFormat(264400, 8, 1, true, false);
			/*sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			*/
			//double angle = -2.0 * Math.PI / byteArray.length / 2;
			//double angle = -2.0 * Math.PI / (byteArray.length / 8);
			//for (int i = startIndex; i < byteArray.length; i++) {
			//for (int i = startIndex; i < byteArray.length - 48; i += 8){
			//for (int i = startIndex; i < 44100; i += 8) {
			//for (int i = startIndex; i < 44100 * 32; i += 2) {
			//for (int i = startIndex; i < byteArray.length - 48; i += 2) {
			//for (int i = startIndex; i < byteArray.length - 24; i += increment) {
				//double angle = i / 2.0 * Math.PI;
				//double angle = i / 2.0 * Math.PI / byteArray.length / 2;
				//double angle = -2.0 * Math.PI / byteArray.length / 4;
				//double angle = -2.0 * Math.PI / byteArray.length / 2;
				//double angle = i / (44100.0/5000) * 2.0 * Math.PI;
				/*if (byteArray[i] == 0) {
					byteArray[i] = 1;
				}
				if (byteArray[i + 2] == 0) {
					byteArray[i + 2] = 1;
				}
				if (byteArray[i + 4] == 0) {
					byteArray[i + 4] = 1;
				}
				if (byteArray[i + 6] == 0) {
					byteArray[i + 6] = 1;
				}
				*/
				//buffer[0] = (byte) (byteArray[i] * Math.sin(i / angle));
				//buffer[0] = (byte) (byteArray[i] * Math.sin(angle));
				/*buffer[0] = (byte) (byteArray[i]);
				buffer[1] = (byte) (byteArray[i + 2]);
				buffer[2] = (byte) (byteArray[i + 4]);
				buffer[3] = (byte) (byteArray[i + 6]);
				*/
				/*buffer[0] = (byte) (byteArray[i]);
				buffer[1] = (byte) (byteArray[i + 1]);
				buffer[2] = (byte) (byteArray[i + 2]);
				buffer[3] = (byte) (byteArray[i + 3]);
				*/
				/*buffer[1] = (byte) (byteArray[i + 2] * Math.sin((i + 2) / angle));
				buffer[2] = (byte) (byteArray[i + 4] * Math.sin((i + 4) / angle));
				buffer[3] = (byte) (byteArray[i + 6] * Math.sin((i + 6) / angle));
				*/
				/*buffer[0] = (byte) (Math.sin(i / (byteArray[i] + 1) * angle) * 50);
				buffer[1] = (byte) (Math.sin(i / (byteArray[i + 2] + 1) * angle) * 50);
				buffer[2] = (byte) (Math.sin(i / (byteArray[i + 4] + 1) * angle) * 50);
				buffer[3] = (byte) (Math.sin(i / (byteArray[i + 6] + 1) * angle) * 50);
				*/
				//double angle = i / byteArray[i] * 2.0 * Math.PI;
				//double angle = byteArray[i] * -2.0 * Math.PI / byteArray.length;
				//buffer[0] = (byte) (Math.sin(angle) * 50);
				//sourceDataLine.write(buffer, 0, 1);
				//sourceDataLine.write(buffer, 0, 4);
				//sourceDataLine.write(buffer, 0, 2);
				//System.out.println(i + "/" + byteArray.length);
				//NoiseGUI.playSound.setValue(NoiseGUI.playSound.getValue() + 1);
				/*NoiseGUI.playSound.setValue(NoiseGUI.playSound.getValue() + 4);
				NoiseGUI.updateTime();
				*/
			//}
			/*sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();
			*/
			//}
			//play = false;
			System.out.println("Starting");
			NoiseGUI.thread.newArray(byteArray);
			//NoiseGUI.thread.selectIndex(startIndex);
			NoiseGUI.thread.selectIndex(num);
			NoiseGUI.thread.chooseIncrement(increment);
			NoiseGUI.thread.bool(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
