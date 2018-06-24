package utils;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Utils {

	public static void fillZeros(int[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
	}

	public static String generateString(Random rng, String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static int sumOfArray(int[]... arrays) {
		int sum = 0;
		for (int i = 0; i < arrays.length; i++) {
			for (int ii = 0; ii < arrays[i].length; ii++) {
				sum += arrays[i][ii];
			}
		}
		return sum;
	}

	public static int[] productOfArrays(int[]... arrays) {
		int numArrays = arrays.length;
		int length = arrays[0].length;
		for (int i = 0; i < arrays.length; i++) {
			length = Math.min(length, arrays[i].length);
		}
		int[] returnArray = new int[length];
		for (int i = 0; i < length; i++) {
			returnArray[i] = arrays[0][i];
			for (int ii = 1; ii < numArrays; ii++) {
				returnArray[i] *= arrays[ii][i];
			}
		}
		return returnArray;
	}

	public static File selectFile(String fileExtension, Component parent, String defaultPath) {
		JFileChooser chooser = null;
		LookAndFeel previousLF = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			chooser = new JFileChooser();
			UIManager.setLookAndFeel(previousLF);
		} catch (IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException
				| ClassNotFoundException e) {
		}

		FileNameExtensionFilter filter = new FileNameExtensionFilter(fileExtension, fileExtension);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (chooser.getSelectedFile().getAbsolutePath().endsWith(fileExtension)) {
				return chooser.getSelectedFile().getAbsoluteFile();
			}
			return new File(chooser.getSelectedFile().getAbsolutePath() + "." + fileExtension);
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			return new File(defaultPath);
		}
		return null;
	}

	public static String arrayToCSV(int[] array) {
		return Arrays.toString(array).replace("[", "").replace("]", "");
	}

	public static int[] CSVToIntArray(String CSV) {
		String[] arrayString = CSV.split(",");
		int[] array = new int[arrayString.length];
		for (int i = 0; i < array.length; i++) {
			arrayString[i] = arrayString[i].replaceAll(" ", "");
			//if (arrayString[i].matches("T(-?[0-9]+)")) {
				array[i] = Integer.parseInt(arrayString[i]);
			//}
		}
		return array;
	}

	public static byte[] readFile(String path) throws IOException {
		File file = new File(path);
		byte[] bytes = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(bytes, 0, bytes.length);
		bis.close();
		fis.close();
		return bytes;
	}

	public static String inputStreamToString(InputStream inputStream) throws IOException {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
			return buffer.lines().collect(Collectors.joining("\n"));
		}
	}
	
	public static void appendToFile(File file, String line){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
			bw.write(line);
			bw.newLine();
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
