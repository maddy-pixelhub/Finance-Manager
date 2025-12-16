package com.finance;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvWriter {
    public static void write(String path, List<Report> reports)
            throws IOException {

        BufferedWriter bw = Files.newBufferedWriter(Path.of(path));
        bw.write("month,income,expense,savings_ratio\n");
        for (Report r : reports) {
            bw.write(r.toCsv());
            bw.newLine();
        }
        bw.close();
    }
}

