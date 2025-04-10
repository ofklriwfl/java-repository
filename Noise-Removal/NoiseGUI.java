

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;

public class NoiseGUI extends JFrame {
	File selected;
	static Runnable gui;
	//static JPanel options;
	static JFileChooser select;
	static JLabel sliderName;
	
	//static JSlider limit;
	static JTextField limit;
	static JButton play;
	static JButton process;
	static JButton save;
	static JButton newButton;
	static JLabel playMoment;
	static JSlider playSound;
	static JFileChooser select2;
	//static JTextField fileName;
	static JLabel status;
	static int originalWidth;
	static int originalHeight;
	static Modify sound;
	//static boolean playNow = false;
	volatile static boolean playNow = false;
	static NoiseThread thread;
	
	
	public void chooseFile(File f) {
		this.selected = f;
		//limit.setMaximum(128);
		limit.setText("0");
		try {
			/*FileInputStream inputStream = new FileInputStream(this.selected);
			byte[] samples = inputStream.readAllBytes();
			int e = 1;
					while (Math.pow(2, e) < samples.length) {
						++e;
					}
					Complex[] waveformc = new Complex[(int) Math.pow(2, e)];
					for (int i = 0; i < samples.length; ++i) {
						waveformc[i] = new Complex(samples[i], 0);
					}
					for (int i = samples.length; i < waveformc.length; i++) {
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
					double maximum = PSD[0];
					for (double num : PSD) {
						if (num > maximum) {
							maximum = num;
						}
					}
					samples = new byte[1];
					waveformc = null;
					waveformf = null;
					conjugate = null;
					PSD = null;
					limit.setMaximum((int) maximum);
					*/
					//limit.setMaximum(2000000);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		sound = new Modify(this.selected);
	}
	
	public void changeSize (int x, int y) {
		
		this.setSize(x, y);
		
	}
	
	public static void updateTime() {
		int byteLength = NoiseGUI.playSound.getValue();
		//int totalSeconds = (int) (byteLength / 44100.0);
		int totalSeconds = (int) (byteLength / 176400.0);
		//int totalSeconds = (int) (byteLength / 88200.0);
		int seconds = totalSeconds % 60;
		int minutes = (int) (totalSeconds / 60.0) % 60;
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
		NoiseGUI.playMoment.setText(lengthText + "/" + NoiseGUI.playMoment.getText().substring(NoiseGUI.playMoment.getText().length() - 8, NoiseGUI.playMoment.getText().length()));
	}
	
	public NoiseGUI() {
		super("Noise removal");
		//this.setBounds(0, 0, 600, 200);
		//this.setSize(900, 800);
		this.originalWidth = this.getWidth();
		this.originalHeight = this.getHeight();
		/*this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Closing");
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}});
		*/
		FlowLayout layout = new FlowLayout();
		changeSize(560, 450);
		//GridLayout layout2 = new GridLayout(10, 5);
		GridLayout layout2 = new GridLayout(9, 0);
		//layout2.setHgap(50);
		//this.setSize(200, 400);
		//GridBagLayout layout = new GridBagLayout();
		//JPanel mainPanel = new JPanel(layout);
		//layout.setHgap(20);
		//layout2.setHgap(20);
		this.setResizable(false);
		JPanel choice = new JPanel(layout);
		//JFileChooser select = new JFileChooser() {
		NoiseGUI.select = new JFileChooser() {
			
			
			
			
			public void approveSelection() {
				if (this.getSelectedFile().getName().substring(this.getSelectedFile().getName().length() - 4, this.getSelectedFile().getName().length()).equals(".wav")) {
					chooseFile(this.getSelectedFile());
					this.setVisible(false);
					
					//options = new JPanel(layout);
					
					//JLabel sliderName = new JLabel(" Noise floor: 0  ");
					//options.add(sliderName);
					//choice.add(sliderName);
					//JSlider limit = new JSlider();
					/*limit.setMinimum(0);
					//limit.setMaximum(128);
					//limit.setMaximum(255000);
					limit.setMajorTickSpacing(20);
					limit.setMinorTickSpacing(10);
					//limit.setPaintTicks(true);
					limit.setPaintTicks(false);
					//limit.setPaintLabels(true);
					limit.setPaintLabels(false);
					limit.setSnapToTicks(false);
					limit.setValue(0);
					*/
					//limit.setSize(800, limit.getHeight());
					//options.add(limit);
					//choice.add(limit);
					choice.setLayout(layout2);
					//choice.setSize(200, 400);
					//changeSize(400, 600);
					//changeSize(400, 500);
					changeSize(400, 405);
					NoiseGUI.sliderName.setVisible(true);
					NoiseGUI.limit.setVisible(true);
					NoiseGUI.play.setVisible(true);
					
					/*int length = 44100;
					int seconds = (int) (length / 44100.0);
					*/
					int byteLength = 44100;
					//int seconds = (int) (byteLength / 44100.0);
					//int totalSeconds = (int) (byteLength / 44100.0);
					int totalSeconds = (int) (byteLength / 176400.0);
					int seconds = totalSeconds % 60;
					//int hours = (int) (seconds / 60.0) % 60;
					int minutes = (int) (totalSeconds / 60.0) % 60;
					//int minutes = hours % 60;
					//int hours = (int) (minutes / 60.0) % 60;
					int hours = (int) (totalSeconds / 60.0 / 60.0);
					String lengthText = "00:00:00/";
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
						lengthText += ("0" + seconds);
					} else {
						lengthText += seconds;
					}
					//NoiseGUI.playMoment.setText("00:00:00/00:00:01");
					//NoiseGUI.playMoment.setText("00:00:00/" + hours + ":" + minutes + ":" + seconds);
					
				
					NoiseGUI.process.setVisible(true);
					NoiseGUI.save.setVisible(true);
					NoiseGUI.status.setText(" Ready");
					NoiseGUI.newButton.setVisible(true);
					NoiseGUI.status.setVisible(true);
					NoiseGUI.playMoment.setVisible(true);
					NoiseGUI.playSound.setVisible(true);
					NoiseGUI.playSound.setValue(0);
					//getContentPane().remove(choice);
					//getContentPane().add(options);
					//options.setVisible(true);
					//choice.remove(this);
				}
			}
			public void cancelSelection() {
				//this.setVisible(false);
				//System.exit(EXIT_ON_CLOSE);
				//this.removeAll();
				if (selected == null) {
					System.exit(EXIT_ON_CLOSE);
				} else {
					this.setVisible(false);
					choice.setLayout(layout2);
					//choice.setSize(200, 400);
					//changeSize(400, 600);
					//changeSize(400, 500);
					changeSize(400, 405);
					NoiseGUI.sliderName.setVisible(true);
					NoiseGUI.limit.setVisible(true);
					NoiseGUI.play.setVisible(true);
					NoiseGUI.process.setVisible(true);
					NoiseGUI.save.setVisible(true);
					NoiseGUI.newButton.setVisible(true);
					NoiseGUI.status.setVisible(true);
					NoiseGUI.playMoment.setVisible(true);
					NoiseGUI.playSound.setVisible(true);
				}
			}
		};
		select.setMultiSelectionEnabled(false);
		
		select.setVisible(true);
		//File selected;
		select.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println(e.getComponent().getName());
				if (e.getComponent().getName() == JFileChooser.APPROVE_SELECTION) {
					selected = select.getSelectedFile();
					choice.setVisible(false);
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		/*for (Component component : select.getComponents()) {
			System.out.println(component.getName() + " " + component.toString());
			System.out.println();
		}
		*/
		NoiseGUI.select2 = new JFileChooser() {
			public void approveSelection() {
				/*if (!NoiseGUI.fileName.getText().equals("") && !NoiseGUI.fileName.getText().equals(null)) {
					this.setSelectedFile(new File(NoiseGUI.fileName.getText() + ".wav"));
				*/
					if (this.getSelectedFile().exists() == false) {
						System.out.println(this.getSelectedFile().getName() + ".wav");
						sound.exportSound(this.getSelectedFile().getName() + ".wav");
						this.setVisible(false);
						choice.setLayout(layout2);
						//choice.setSize(200, 400);
						
						//changeSize(400, 600);
						//changeSize(400, 500);
						changeSize(400, 405);
						NoiseGUI.sliderName.setVisible(true);
						NoiseGUI.limit.setVisible(true);
						NoiseGUI.playSound.setVisible(true);
						NoiseGUI.process.setVisible(true);
						NoiseGUI.save.setVisible(true);
						NoiseGUI.newButton.setVisible(true);
						NoiseGUI.status.setText(" Saved");
						NoiseGUI.status.setVisible(true);
						NoiseGUI.play.setVisible(true);
						NoiseGUI.playMoment.setVisible(true);
						
					}
				/*}
				*/
			}
			
			public void cancelSelection() {
				this.setVisible(false);
				choice.setLayout(layout2);
				//choice.setSize(200, 400);
				//changeSize(400, 600);
				//changeSize(400, 500);
				changeSize(400, 405);
				NoiseGUI.sliderName.setVisible(true);
				NoiseGUI.limit.setVisible(true);
				NoiseGUI.play.setVisible(true);
				NoiseGUI.process.setVisible(true);
				NoiseGUI.save.setVisible(true);
				NoiseGUI.newButton.setVisible(true);
				NoiseGUI.status.setVisible(true);
				NoiseGUI.playMoment.setVisible(true);
				NoiseGUI.playSound.setVisible(true);
			}
		};
		select2.setApproveButtonText("Save");
		select2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		select2.setVisible(false);
		/*fileName = new JTextField();
		select2.add(fileName);
		//select2.addKeyListener(new KeyListener() {
		fileName.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				/*for (Component component : select2.getComponents()) {
					component.setEnabled(true);
				}
				*/
				//select2.setSelectedFile(new File("noiseTemp.txt"));
			/*}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}});
		*/
		//getContentPane().add(mainPanel);
		getContentPane().add(choice);
		choice.setVisible(true);
		//options = new JPanel(layout);
		//JLabel sliderName = new JLabel("Noise floor");
		//this.sliderName = new JLabel("Noise floor: " + this.limit.getValue());
		//this.sliderName = new JLabel(" Noise floor: 0");
		this.sliderName = new JLabel(" Noise floor: ");
		//options.add(sliderName);
		//choice.add(sliderName);
		choice.add(sliderName, 0);
		this.sliderName.setVisible(false);
		//JSlider limit = new JSlider();
		/*this.limit = new JSlider();
		limit.setMinimum(0);
		limit.setMaximum(128);
		*/
		this.limit = new JTextField("0");
		limit.setColumns(8);
		/*limit.setMajorTickSpacing(20);
		limit.setMinorTickSpacing(10);
		limit.setPaintTicks(true);
		limit.setPaintLabels(true);
		limit.setSnapToTicks(false);
		*/
		/*limit.setValue(0);
		 */
		//options.add(limit);
		//choice.add(limit);
		choice.add(limit, 1);
		this.limit.setVisible(false);
		/*this.limit.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (NoiseGUI.limit.getValue() < 100) {
					NoiseGUI.sliderName.setText(" Noise floor: " + NoiseGUI.limit.getValue() + "  ");
				} else {
					NoiseGUI.sliderName.setText(" Noise floor: " + NoiseGUI.limit.getValue() + " ");
				}
				NoiseGUI.status.setText(" Changes not processed");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (NoiseGUI.limit.getValue() < 100) {
					NoiseGUI.sliderName.setText(" Noise floor: " + NoiseGUI.limit.getValue() + "  ");
				} else {
					NoiseGUI.sliderName.setText(" Noise floor: " + NoiseGUI.limit.getValue() + " ");
				}
				//NoiseGUI.status.setText("Changes not processed");
			}});
		*/
		//mainPanel.add(select);
		//choice.add(select);
		//getContentPane().add(options);
		//options.setVisible(false);
		this.play = new JButton("Play/Pause");
		this.play.addMouseListener(new MouseListener() {

			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				//sound.playSound(0);
				if (playNow == false) {
				//if (NoiseGUI.sound.play == false) {
					System.out.println("Play");
					System.out.println(playNow);
					//NoiseGUI.thread.notify();
					NoiseGUI.thread.bool(true);
					playNow = true;
					NoiseGUI.sound.play = true;
					sound.playSound(NoiseGUI.playSound.getValue());
					//playNow = false;
					//NoiseGUI.sound.play = false;
					System.out.println("variable: " + playNow);
					System.out.println("End of mouse event");
					} else {
					try {
						System.out.println("Pause");
						System.out.println(playNow);
						//NoiseGUI.thread.wait();
						NoiseGUI.thread.bool(false);
						playNow = false;
						//NoiseGUI.sound.play = false;
					//} catch (InterruptedException ex) {
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		//choice.add(this.play);
		choice.add(this.play, 2);
		this.play.setVisible(false);
		//this.process = new JButton("Generate");
		this.process = new JButton("Process");
		//choice.add(this.process);
		choice.add(this.process, 3);
		this.process.setVisible(false);
		this.process.addMouseListener(new MouseListener(
				) {

			@Override
			public void mouseClicked(MouseEvent e) {
				//NoiseGUI.status.setText("Processing...");
				/*try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
				//sound.processSound(NoiseGUI.limit.getValue());
				try {
					sound.processSound(Math.abs(Integer.parseInt(NoiseGUI.limit.getText())));
				} catch (Exception ex) {
					ex.printStackTrace();
					//NoiseGUI.status.setText("Invalid number format");
				}
				NoiseGUI.status.setText(" Processing complete");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		this.save = new JButton("Save");
		//choice.add(this.save);
		choice.add(this.save, 4);
		this.save.setVisible(false);
		this.save.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//choice.setSize(NoiseGUI.originalWidth, NoiseGUI.originalHeight);
				//changeSize(NoiseGUI.originalWidth, NoiseGUI.originalHeight);
				changeSize(560, 500);
				choice.setLayout(layout);
				NoiseGUI.sliderName.setVisible(false);
				NoiseGUI.limit.setVisible(false);
				NoiseGUI.play.setVisible(false);
				NoiseGUI.process.setVisible(false);
				NoiseGUI.save.setVisible(false);
				NoiseGUI.newButton.setVisible(false);
				NoiseGUI.status.setVisible(false);
				NoiseGUI.playMoment.setVisible(false);
				NoiseGUI.playSound.setVisible(false);
				NoiseGUI.select2.setVisible(true);
				NoiseGUI.select2.setDialogType(JFileChooser.SAVE_DIALOG);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		this.newButton = new JButton("New");
		this.newButton.setVisible(false);
		this.newButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//choice.setSize(NoiseGUI.originalWidth, NoiseGUI.originalHeight);
				//changeSize(NoiseGUI.originalWidth, NoiseGUI.originalHeight);
				
				
				
				
				changeSize(560, 500);
				choice.setLayout(layout);
				//NoiseGUI.sliderName.setText(" Noise floor: 0");
				NoiseGUI.sliderName.setText(" Noise floor: ");
				NoiseGUI.sliderName.setVisible(false);
				//NoiseGUI.limit.setValue(0);
				NoiseGUI.limit.setText("0");
				NoiseGUI.limit.setVisible(false);
				NoiseGUI.play.setVisible(false);
				NoiseGUI.process.setVisible(false);
				NoiseGUI.save.setVisible(false);
				NoiseGUI.newButton.setVisible(false);
				NoiseGUI.status.setVisible(false);
				NoiseGUI.playMoment.setVisible(false);
				NoiseGUI.playSound.setVisible(false);
				NoiseGUI.select.setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//choice.add(this.newButton);
		choice.add(this.newButton, 5);
		status = new JLabel("");
		//choice.add(status);
		choice.add(this.status, 6);
		status.setVisible(false);
		this.playMoment = new JLabel("00:00:00/00:00:01");
		//choice.add(this.playMoment);
		choice.add(this.playMoment, 7);
		this.playMoment.setVisible(false);
		this.playSound = new JSlider();
		this.playSound.setValue(0);
		this.playSound.setPaintLabels(false);
		this.playSound.setPaintTicks(false);
		this.playSound.setMinimum(0);
		//this.playSound.setMaximum(sound.bytes.length - 48);
		this.playSound.setMaximum(44100);
		//choice.add(this.playSound);
		choice.add(this.playSound, 8);
		this.playSound.setVisible(false);
		this.playSound.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//int byteLength = NoiseGUI.playSound.getValue() - 44100;
				
				/*int byteLength = NoiseGUI.playSound.getValue();
				if (playNow == true) {
					sound.playSound(byteLength);
				}
				//int seconds = (int) (byteLength / 44100.0);
				int totalSeconds = (int) (byteLength / 44100.0);
				int seconds = totalSeconds % 60;
				//int hours = (int) (seconds / 60.0) % 60;
				int minutes = (int) (totalSeconds / 60.0) % 60;
				//int minutes = hours % 60;
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
				//NoiseGUI.playMoment.setText(lengthText + "/" + NoiseGUI.playMoment.getText().substring(NoiseGUI.playMoment.getText().length() - 9, NoiseGUI.playMoment.getText().length()));
				NoiseGUI.playMoment.setText(lengthText + "/" + NoiseGUI.playMoment.getText().substring(NoiseGUI.playMoment.getText().length() - 8, NoiseGUI.playMoment.getText().length()));
				*/
				updateTime();
				//if (playNow == true) {
				if (NoiseGUI.sound.play == true) {
					while (NoiseGUI.playSound.getValue() % 4 != 0) {
						NoiseGUI.playSound.setValue(NoiseGUI.playSound.getValue() - 1);
					}
					sound.playSound(NoiseGUI.playSound.getValue());
					
				}
			}
			

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				/*
				//int byteLength = NoiseGUI.playSound.getValue() - 44100;
				//int byteLength = NoiseGUI.playSound.getValue();
				int byteLength = NoiseGUI.playSound.getMaximum() - NoiseGUI.playSound.getValue();
				//int seconds = (int) (byteLength / 44100.0);
				int totalSeconds = (int) (byteLength / 44100.0);
				int seconds = totalSeconds % 60;
				//int hours = (int) (seconds / 60.0) % 60;
				int minutes = (int) (seconds / 60.0) % 60;
				//int minutes = hours % 60;
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
					lengthText += ("0" + seconds);
					if (seconds < 0) {
						seconds = 0;
					}
				} else {
					lengthText += seconds;
				}z
				//NoiseGUI.playMoment.setText(lengthText + "/" + NoiseGUI.playMoment.getText().substring(NoiseGUI.playMoment.getText().length() - 9, NoiseGUI.playMoment.getText().length()));
				NoiseGUI.playMoment.setText(lengthText + "/" + NoiseGUI.playMoment.getText().substring(NoiseGUI.playMoment.getText().length() - 8, NoiseGUI.playMoment.getText().length()));
				*/
			}});
		
		
		choice.add(select);
		choice.add(select2);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		//Runnable gui = new Runnable() {
		gui = new Runnable() {
			public void run() {
				NoiseGUI window = new NoiseGUI();
				window.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(gui);
		thread = new NoiseThread();
		thread.run();
	}
	

	
}
