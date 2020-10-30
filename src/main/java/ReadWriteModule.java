import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadWriteModule extends BaseWindow{

    public static String readFile(){
        String masterpass = "";

        try (BufferedReader br = new BufferedReader(new FileReader("storpass.txt"))) {
            /// read 1 line
            masterpass = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (masterpass);
    }

    ///  разбивает строку на 3 части
    public static String[] parsString(String stringConfig) {
        String[] parsStr = stringConfig.split(" ", 3);
        return parsStr;
    }
}