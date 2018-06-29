package managers;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;

import utils.Utils;

public class WebSocketServer extends Thread {
	private ServerSocket serverSocket;
	private int port;
	private boolean running = false;

	private String rawWebpage;
	private String rawWebpageRanking;
	private String formattedWebpage;
	private String formattedWebpageRanking;
	private String css;

	public WebSocketServer(byte[] ipAddress, int port) {
		this.port = port;
		try {
			rawWebpage = new String(Utils.readFile("site/index.html"));
			rawWebpageRanking = new String(Utils.readFile("site/rankings.html"));
			css = new String(Utils.readFile("site/StyleSheet.css"));
			reformatWebpage();
			reformatWebpageRankings();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void startServer() {
		try {
			serverSocket = new ServerSocket(port);
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopServer() {
		running = false;
		this.interrupt();
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			try {

				// Call accept() to receive the next connection
				Socket socket = serverSocket.accept();

				// Pass the socket to the RequestHandler thread for processing
				RequestHandler requestHandler = new RequestHandler(socket);
				requestHandler.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class RequestHandler extends Thread {
		private Socket socket;

		RequestHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				// Get input and output streams
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				String line = in.readLine();
				if (line.contains("GET") && line != null) {
					if (line.contains(" / ")) {
						// redirect
						out.println("HTTP/1.1 301 Moved Permanently");
						out.println("Location: /score");
					} else if (line.contains(" /score ")) {
						// webpage
						out.println("HTTP/1.1 200 OK");
						out.println("Content-Type: text/html");
						out.println("\r\n");
						out.println(formattedWebpage);
					} else if (line.contains(" /ranking ")) {
						// webpage
						out.println("HTTP/1.1 200 OK");
						out.println("Content-Type: text/html");
						out.println("\r\n");
						out.println(formattedWebpageRanking);
					} else if (line.contains(" /StyleSheet.css ")) {
						// send file
						out.println("HTTP/1.1 200 OK");
						out.println("Content-Type: text/css");
						out.println("\r\n");
						out.println(css);
					}
				}

				if (line.contains("POST") && line != null) {
					if (line.contains(" / ")) {
						// redirect
						out.println("HTTP/1.1 301 Moved Permanently");
						out.println("Location: /score");
					} else if (line.contains(" /score ")) {
						// webpage
						out.println("HTTP/1.1 200 OK");
						out.println("Content-Type: text/html");
						out.println("\r\n");
						out.println(formattedWebpage);
					}
					StringBuilder raw = new StringBuilder();
					int contentLength = 0;
					while (!(line = in.readLine()).equals("")) {
						raw.append('\n' + line);
						if (line.startsWith("Content-Length: ")) {
							contentLength = Integer.parseInt(line.substring(16));
						}
					}
					StringBuilder body = new StringBuilder();
					int c = 0;
					for (int i = 0; i < contentLength; i++) {
						c = in.read();
						body.append((char) c);
					}
					processScoringSubmit(body.toString());
				}

				// Write out our header to the client
				out.flush();

				// Close our connection
				in.close();
				out.close();
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void processScoringSubmit(String body) {
		String eventBody = "";
		if (body.contains("true")) {
			eventBody = "+";
		} else if (body.contains("false")) {
			eventBody = "-";
		} else {
			eventBody = String.valueOf(body.charAt(body.length() - 1));
		}
		ActionEvent e = new ActionEvent(this, 0, eventBody);
		if (body.contains("red_high_duckling")) {
			Tournement.getMainDisplay().addScoringModuleEvent(0, e);
		} else if (body.contains("red_low_duckling")) {
			Tournement.getMainDisplay().addScoringModuleEvent(1, e);
		} else if (body.contains("red_pen_duckling")) {
			Tournement.getMainDisplay().addScoringModuleEvent(2, e);
		} else if (body.contains("red_crate_mallard")) {
			Tournement.getMainDisplay().addScoringModuleEvent(3, e);
		} else if (body.contains("red_pen_mallard")) {
			Tournement.getMainDisplay().addScoringModuleEvent(4, e);
		} else if (body.contains("red_minor")) {
			Tournement.getMainDisplay().addScoringModuleEvent(5, e);
		} else if (body.contains("red_major")) {
			Tournement.getMainDisplay().addScoringModuleEvent(6, e);
		} else if (body.contains("blue_high_duckling")) {
			Tournement.getMainDisplay().addScoringModuleEvent(7, e);
		} else if (body.contains("blue_low_duckling")) {
			Tournement.getMainDisplay().addScoringModuleEvent(8, e);
		} else if (body.contains("blue_pen_duckling")) {
			Tournement.getMainDisplay().addScoringModuleEvent(9, e);
		} else if (body.contains("blue_crate_mallard")) {
			Tournement.getMainDisplay().addScoringModuleEvent(10, e);
		} else if (body.contains("blue_pen_mallard")) {
			Tournement.getMainDisplay().addScoringModuleEvent(11, e);
		} else if (body.contains("blue_minor")) {
			Tournement.getMainDisplay().addScoringModuleEvent(12, e);
		} else if (body.contains("blue_major")) {
			Tournement.getMainDisplay().addScoringModuleEvent(13, e);
		}
		Tournement.getMainDisplay().repaintAll();
		reformatWebpage();
	}

	public void reformatWebpage() {
		int[] counts = ScoringManager.getInstance().getCounts();
		String formattedWebpage = rawWebpage;
		formattedWebpage = formattedWebpage.replace("RHD_VALUE", String.valueOf(counts[0]));
		formattedWebpage = formattedWebpage.replace("RLD_VALUE", String.valueOf(counts[1]));
		formattedWebpage = formattedWebpage.replace("RPD_VALUE", String.valueOf(counts[2]));
		formattedWebpage = formattedWebpage.replace("RCM_VALUE", String.valueOf(counts[3]));
		formattedWebpage = formattedWebpage.replace("RPM_VALUE", String.valueOf(counts[4]));
		formattedWebpage = formattedWebpage.replace("RMIN_VALUE", String.valueOf(counts[5]));
		formattedWebpage = formattedWebpage.replace("RMAJ_VALUE", String.valueOf(counts[6]));
		formattedWebpage = formattedWebpage.replace("BHD_VALUE", String.valueOf(counts[7]));
		formattedWebpage = formattedWebpage.replace("BLD_VALUE", String.valueOf(counts[8]));
		formattedWebpage = formattedWebpage.replace("BPD_VALUE", String.valueOf(counts[9]));
		formattedWebpage = formattedWebpage.replace("BCM_VALUE", String.valueOf(counts[10]));
		formattedWebpage = formattedWebpage.replace("BPM_VALUE", String.valueOf(counts[11]));
		formattedWebpage = formattedWebpage.replace("BMIN_VALUE", String.valueOf(counts[12]));
		formattedWebpage = formattedWebpage.replace("BMAJ_VALUE", String.valueOf(counts[13]));
		this.formattedWebpage = formattedWebpage;
	}

	public void reformatWebpageRankings() {
		String formattedWebpage = rawWebpageRanking;
		LocalTime now = LocalTime.now();
		formattedWebpage = formattedWebpage.replace("TIME", String.format("%02d:%02d", now.getHour(), now.getMinute()));
		ArrayList<Team> teams = TeamManager.getInstance().getTeams();
		for (Team team : teams) {
			formattedWebpage = formattedWebpage.replaceFirst("NUMBER", String.valueOf(team.getNumber()));
			formattedWebpage = formattedWebpage.replaceFirst("NAME", team.getName());
			formattedWebpage = formattedWebpage.replaceFirst("SCORE", String.format("%5.1f", team.getAverageScore()));
			formattedWebpage = formattedWebpage.replaceFirst("DUCKLING", String.format("%5.1f", team.getAverageDuckling()));
			formattedWebpage = formattedWebpage.replaceFirst("MALLARD", String.format("%5.1f", team.getAverageMallard()));
			formattedWebpage = formattedWebpage.replaceFirst("FOUL", String.format("%5.1f", team.getAverageFouls()));
			formattedWebpage = formattedWebpage.replaceFirst("MAX", String.format("%3d", team.getMaxScore()));
		}
		// System.out.println(formattedWebpage);
		this.formattedWebpageRanking = formattedWebpage;
	}
}
