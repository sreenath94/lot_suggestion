package com.bed;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Using the class to read data from PDF
 */
public class ReadDataPdf {
    PDDocument pdDocument;
    Pattern pattern = Pattern.compile("(\\d{4})");
    Map<String,Integer> finalResult = new HashMap<>();
    List<String> pdfFiles = Arrays.asList("src/KLTest.pdf","src/KLTest1.pdf");

    {

            try (Stream<Path> paths = Files.walk(Paths.get("src/files")))  {
                paths.filter(Files::isRegularFile).forEach(e->{
                    try {
                        pdDocument = PDDocument.load(e.toFile());
                        if (!pdDocument.isEncrypted()) {
                            PDFTextStripper stripper = new PDFTextStripper();
                            String text = stripper.getText(pdDocument);
                            //  System.out.println("Text:" + text);
                            find4Digit(text);
                        }
                        pdDocument.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                });

            } catch (IOException e) {
                System.out.println("Error Loading " + e);
                throw new RuntimeException(e);
            }
        finalResult.entrySet().stream().filter(e->e.getValue() >4).forEach(System.out::println);

    }

    public void find4Digit(String input){
        String[] part = input.split(" ");
      // Arrays.stream(part).forEach(System.out::println);
        Arrays.stream(part).filter(e->e.trim().length() == 4 && isNumeric(e.trim())).forEach(e->{
            if(!finalResult.containsKey(e)){
                finalResult.put(e,1);
            }else {
                finalResult.put(e, finalResult.get(e) + 1);
            }
        });

    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public void find4DigitCodes(String input) {

        char[] inputByte = input.toCharArray();
        int i = 0;
        while (i < inputByte.length) {
            System.out.println(inputByte[i]);
            if ((i + 5) < inputByte.length && " ".equals(String.valueOf(inputByte[i])) && " ".equals(String.valueOf(inputByte[i + 5]))) {
              //  System.out.println("Inside");
                System.out.println(inputByte[i] + inputByte[i + 1] + inputByte[i + 2] + inputByte[i + 3] + inputByte[i + 4] + inputByte[i + 5]);
                i = i + 4;
            }
            i++;
        }
    }

}
