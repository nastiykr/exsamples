import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.File;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

public class Application {
    public static void main(String[] args) throws IOException {
        //Create a buffred reader so that you can read in the file
        BufferedReader reader = new BufferedReader(new FileReader(new File("input_values.txt")));

        //The StringBuffer will be used to create a string if your file has multiple lines
        StringBuffer sb = new StringBuffer();
        String line;

        while((line = reader.readLine())!= null)
        {
            sb.append(line);
        }

        //We now split the line on the "," to get a string array of the values
        String [] store = sb.toString().split(";");

        //Show values from file
        for (int i = 7; i < 14; i++) {
            System.out.println(store[i]);
        }

        Integer Contact_value_start = Integer.parseInt(store[7]);
        Integer Contact_value_end = Integer.parseInt(store[8]);
        Integer Numbers_of_calls = Integer.parseInt(store[9]);
        Integer Frequency = Integer.parseInt(store[10]);
        String Scenario = new String(store[11].getBytes(), "UTF-8");
        String Message = new String(store[12].getBytes(), "UTF-8");
        String Input_datetime = store[13];
        ZonedDateTime dateTime = ZonedDateTime.parse(Input_datetime);

        Integer k = 1;
        Integer Priority = 0;
        String Contact_type = "phone";
        Integer Contact_order = 1;
        Integer Contact_value = Contact_value_start;
        String Confirmation_PIN = "";

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("export_generateForRupor.csv"),"UTF-8");

             BufferedWriter bw = new BufferedWriter(writer)) {

                bw.write("Record number;Full name;Priority;Contact type;Contact order;Contact value;Scenario;Message;Confirmation PIN;Start datetime\n");
                for (int i = 0; i < (86400/Frequency) * Numbers_of_calls;i++) {

                    bw.write(k + ";" + k + ";" + Priority + ";" + Contact_type + ";" + Contact_order + ";" + Contact_value + ";" + Scenario + ";" + Message + ";" + Confirmation_PIN + ";" + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")) +"\n");

                    if (k % Numbers_of_calls == 0) {
                        dateTime = dateTime.plusSeconds(1*Frequency);
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
        System.out.println("\nFile generated - export_generateForRupor.csv");
    }
}