package utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Fonts {
	private static Fonts instance;

	public Font[] scienceFair = new Font[6];
	public Font[] terminal = new Font[6];
	public Font[] codeNewRoman = new Font[6];

	public final byte SMALL = 0;
	public final byte MEDIUM = 1;
	public final byte LARGE = 2;
	public final byte X_LARGE = 3;
	public final byte XX_LARGE = 4;
	public final byte XXX_LARGE = 5;

	public static Fonts getInstance() {
		if (instance == null) {
			instance = new Fonts();
		}
		return instance;
	}

	/**
	 * This registers custom fonts
	 */
	private Fonts() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TYPE1_FONT, new File("Science Fair.otf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("DECTerminalModern.ttf")));
			ge.registerFont(Font.createFont(Font.TYPE1_FONT, new File("Code New Roman.otf")));
			System.out.println(Arrays.toString(ge.getAvailableFontFamilyNames()));
		} catch (IOException | FontFormatException e) {
		}

		terminal[SMALL] = new Font("DEC Terminal Modern", Font.PLAIN, 16);
		terminal[MEDIUM] = new Font("DEC Terminal Modern", Font.PLAIN, 32);
		terminal[LARGE] = new Font("DEC Terminal Modern", Font.PLAIN, 48);
		terminal[X_LARGE] = new Font("DEC Terminal Modern", Font.PLAIN, 64);
		terminal[XX_LARGE] = new Font("DEC Terminal Modern", Font.PLAIN, 96);
		terminal[XXX_LARGE] = new Font("DEC Terminal Modern", Font.PLAIN, 128);

		scienceFair[SMALL] = new Font("Science Fair", Font.PLAIN, 16);
		scienceFair[MEDIUM] = new Font("Science Fair", Font.PLAIN, 32);
		scienceFair[LARGE] = new Font("Science Fair", Font.PLAIN, 48);
		scienceFair[X_LARGE] = new Font("Science Fair", Font.PLAIN, 64);
		scienceFair[XX_LARGE] = new Font("Science Fair", Font.PLAIN, 96);
		scienceFair[XXX_LARGE] = new Font("Science Fair", Font.PLAIN, 128);

		codeNewRoman[SMALL] = new Font("Code New Roman", Font.PLAIN, 16);
		codeNewRoman[MEDIUM] = new Font("Code New Roman", Font.PLAIN, 32);
		codeNewRoman[LARGE] = new Font("Code New Roman", Font.PLAIN, 48);
		codeNewRoman[X_LARGE] = new Font("Code New Roman", Font.PLAIN, 64);
		codeNewRoman[XX_LARGE] = new Font("Code New Roman", Font.PLAIN, 96);
		codeNewRoman[XXX_LARGE] = new Font("Code New Roman", Font.PLAIN, 128);
	}
}
