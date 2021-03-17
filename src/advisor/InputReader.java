package advisor;

import java.util.Scanner;

public class InputReader {
    public static String[] readCommand() {
        Scanner scanner = new Scanner(System.in);
        String[] args = scanner.nextLine().split(" ");
        return args;
    }
}
