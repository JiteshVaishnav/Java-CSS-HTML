package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class Main {
    public static void main(String[] args) throws IOException {
        // Load HTML file
        String charsetName = "UTF-8";
        String absolutePath = (new File("./").getAbsolutePath());
        absolutePath = removeLastChar(absolutePath);
        String htmlFilePath = absolutePath + "src/main/java/org/example/index.html";
        Document doc = Jsoup.parse(new File(htmlFilePath), charsetName);
        System.out.println("BEFORE:\n" + doc.outerHtml());

        // Replace each link nodes with its respective CSS file content
        for (Element link : doc.select("link[rel=stylesheet]")) {
            String cssFilename = link.attr("href");

            Element style = new Element(Tag.valueOf("style"), "");
            style.appendText("/* " + cssFilename + " */");
            style.appendText(loadCssFileContent(cssFilename, charsetName));

            link.replaceWith(style);
        }

        System.out.println("\nAFTER:\n" + doc.outerHtml());

        BufferedWriter bw = new BufferedWriter(new FileWriter(htmlFilePath));
        bw.write(doc.outerHtml());
        bw.close();
    }

    private static String loadCssFileContent(String path, String charsetName) throws IOException {
        String absolutePath = (new File("./").getAbsolutePath());
        absolutePath = removeLastChar(absolutePath);
        String cssFilePath = absolutePath + path;
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, charsetName);
    }

    //method to remove last character
    private static String removeLastChar(String s)
    {
        //returns the string after removing the last character
        return s.substring(0, s.length() - 1);
    }
}
