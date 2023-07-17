package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();
	
			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

			System.out.println("Enter '@login', or hit 'Enter' to send to All: ");
			String desiredRecipient = scanner.nextLine();
			if (desiredRecipient.isEmpty()) {
				desiredRecipient = "@All";
			}

            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				Message m;
				if (desiredRecipient != null){
					m = new Message(login,desiredRecipient,text);
				}else {
					m = new Message(login,text);
				}
				int res = m.send(Utils.getURL() + "/add",desiredRecipient);
				System.out.println("Message sent. Response code: " + res);

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occurred: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
