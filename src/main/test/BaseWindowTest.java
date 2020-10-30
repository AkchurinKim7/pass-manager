import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BaseWindowTest {

    @Test
    void writeString() {
        String testString = "vk admin admin1";
        String stringSS = null;
        BaseWindow.writeString(testString);

        try (BufferedReader br = new BufferedReader(new FileReader(BaseWindow.filePath))) {
            /// чтение построчно
            String stringS;
            while ((stringS = br.readLine()) != null) {
                if (stringS.equals(testString)){
                    stringSS = testString;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(stringSS, testString);


    }
}