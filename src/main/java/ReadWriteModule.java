import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.util.Arrays;

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

        BaseWindow.decryption(parsStr[0].getBytes());
        BaseWindow.decryption(parsStr[1].getBytes());
        BaseWindow.decryption(parsStr[2].getBytes());

        /*BaseWindow.decryption(new BigInteger(parsStr[0], 16).toByteArray());
        BaseWindow.decryption(new BigInteger(parsStr[1], 16).toByteArray());
        BaseWindow.decryption(new BigInteger(parsStr[2], 16).toByteArray()); */

        return parsStr;
    }
}