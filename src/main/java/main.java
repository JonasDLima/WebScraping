import java.io.IOException;
import java.util.Arrays;

public class main {
    public static void main (String[] args) throws IOException {
        System.out.println(Arrays.toString(ColorsDataBase.persistColorsData().getItems()));
        ColorsDataBase.CriarCsvComStringsOpenCsv();
    }
}
