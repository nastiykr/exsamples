import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Contact_value_start");
        Integer Contact_value_start = scanner.nextInt();

        System.out.println("Input Contact_value_end");
        Integer Contact_value_end = scanner.nextInt();

        System.out.println("Input number of calls per second");
        Integer Count_repeat = scanner.nextInt();

        System.out.println("Input Scenario, example: tests");
        String Scenario = scanner.next();

        scanner.nextLine();
        System.out.println("Input Message, example: Старт. %pause=3000% Тестовое оповещение");
        String Message = scanner.next();

        scanner.nextLine();
        System.out.println("Input start date, format: 2019-08-15T00:00:00+02:00");
        String Input_datetime = scanner.next();
        ZonedDateTime dateTime = ZonedDateTime.parse(Input_datetime);

        Integer k = 1;
        Integer Priority = 0;
        String Contact_type = "phone";
        Integer Contact_order = 1;
        Integer Contact_value = Contact_value_start;
        String Confirmation_PIN = "";

        try (FileWriter writer = new FileWriter("a_generate_rup.csv");
             BufferedWriter bw = new BufferedWriter(writer)) {

                bw.write("Record number;Full name;Priority;Contact type;Contact order;Contact value;Scenario;Message;Confirmation PIN;Start datetime\n");
                for (int i = 0; i < 86400*Count_repeat;i++) {

                    bw.write(k + ";" + k + ";" + Priority + ";" + Contact_type + ";" + Contact_order + ";" + Contact_value + ";" + Scenario + ";" + Message + ";" + Confirmation_PIN + ";" + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")) +"\n");

                    if (k % Count_repeat == 0) {
                        dateTime = dateTime.plusSeconds(1);
                    }

                    k++;
                    Contact_value++;

                    if (Contact_value > Contact_value_end) {
                       Contact_value = Contact_value_start;
                    }
                }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        System.out.println("\nFile generated - a_generate_rup.csv");
    }
}