package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter your login: ");
            String login = scanner.nextLine();

            Thread th = new Thread(new GetThread(login));
            th.setDaemon(true);
            th.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }

            System.out.println("Enter your message with receiver like: @'login' message ");
            while (true) {
                String tempText = scanner.nextLine();
                if (tempText.isEmpty()) break;
                String text = "";
                String to = "";
                if (tempText.equals("/users") || tempText.equals("/online")) {
                    new GetUsersOnline().getPresentUsers();
                } else if (tempText.startsWith("@")) {
                    if (tempText.contains(" ")) {
                        to = tempText.substring(tempText.indexOf("@") + 1, tempText.indexOf(" "));
                        text = tempText.substring(tempText.indexOf(" ") + 1);
                    } else {
                        text = tempText;
                    }
                } else {
                    text = tempText;
                }


                Message m = new Message(login, to, text);
                int res = m.send(Utils.getURL() + "/add");

                if (res != 200) { // 200 OK
                    System.out.println("HTTP error occurred: " + res);
                    return;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}