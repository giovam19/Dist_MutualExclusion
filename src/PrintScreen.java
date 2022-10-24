public class PrintScreen {
    private static boolean print = false;
    private static String message = "0";

    public static void printMessage(String s) {
        message = s;
        print = true;
        System.out.println("hola");
    }

    public static void main(String[] args) {
        System.out.println("Start");
        while (true) {
            System.out.println(message);
            if (print) {
                System.out.println(message);
                print = false;
            }
        }
    }
}
