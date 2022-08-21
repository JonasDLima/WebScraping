import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;

import com.opencsv.CSVWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class ColorsDataBase {
    private String cor;
    private String codigoRGB;
    private String codigoHexadecimal;

    public ColorsDataBase() {
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setCodigoRGB(String codigoRGB) {
        this.codigoRGB = codigoRGB;
    }

    public void setCodigoHexadecimal(String codigoHexadecimal) {
        this.codigoHexadecimal = codigoHexadecimal;
    }

    public String getCor() {
        return cor;
    }

    public String getCodigoRGB() {
        return codigoRGB;
    }

    public String getCodigoHexadecimal() {
        return codigoHexadecimal;
    }

    public static List persistColorsData() {
        List colorsDataList = new List();
        try {
            Document webPage = Jsoup.connect(
                    "https://ayltoninacio.com.br/blog/tabela-cores-html-css-hexadecimal-nome-rgb").get();
            //System.out.println(webPage.html());
            //Elements row = webPage.select("body > article > div > div > div > div > table > tbody > tr:nth-child(3) > td:nth-child(1)");
            //System.out.println(row);
            Elements cabecalho = webPage.select("body > article > div > div > div > div > table > tbody > tr:nth-child(1)");
            String nomeCor = cabecalho.select("th:nth-child(1)").text();
            String rgbCor = cabecalho.select("th:nth-child(2)").text();
            String hexCor = cabecalho.select("th:nth-child(3)").text();
            //System.out.println(nomeCor);
            String colorsDataBase = (nomeCor + rgbCor + hexCor);
            //System.out.println(colorsDataBase);
            colorsDataList.add(colorsDataBase);
            //System.out.println(colorsDataBase);

            for (int i = 2; i < 130; i++) {
                Elements row = webPage.select("body > article > div > div > div > div > table > tbody > tr:nth-child("+ i +")");

                nomeCor = row.select("td:nth-child(1)").text();
                rgbCor = row.select("td:nth-child(2)").text();
                hexCor = row.select("td:nth-child(3)").text();

                colorsDataBase = (nomeCor + " " + rgbCor + " " + hexCor);
                colorsDataList.add(colorsDataBase);
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        return colorsDataList;
    }

    public static void CriarCsvComStringsOpenCsv() throws IOException {

            List colorsDataList = ColorsDataBase.persistColorsData();
            System.out.println(colorsDataList);
            Writer writer = Files.newBufferedWriter(Paths.get("/home/jonasdlima/Documentos/cores.txt"));
            CSVWriter csvWriter = new CSVWriter(writer);

            csvWriter.writeNext(new String[]{colorsDataList.getSelectedItem()});
            csvWriter.writeAll(Collections.singletonList(colorsDataList.getItems()));


            csvWriter.flush();
            writer.close();
    }
}
