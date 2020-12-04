package core.basesyntax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class WorkWithFile {

    private static final String[] FILE_FIELDS
            = new String[] {"supply", "buy", "result"};

    private static List<String> readFile(String filePath) {
        File fileToRead = new File(filePath);
        List<String> readData = null;
        try {
            readData = Files.readAllLines(fileToRead.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file "
                    + fileToRead.getName(), e);
        }
        return readData;
    }

    private static void writeFile(String filePath, String data) {
        File fileToWrite = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWrite))) {
            bw.write(data);
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException("Wow... something went wrong,"
                    + "couldn't write the " + fileToWrite.getName(), e);
        }
    }

    private static String reportStatistics(List<String> list) {
        int supply = 0;
        int buy = 0;

        for (String line : list) {
            if (line.contains(FILE_FIELDS[0])) {
                supply += Integer.parseInt(line.split(",")[1]);
            }
            if (line.contains(FILE_FIELDS[1])) {
                buy += Integer.parseInt(line.split(",")[1]);
            }
        }

        return reportConstructor(supply, buy);
    }

    private static String reportConstructor(int supply, int buy) {
        StringBuilder sb = new StringBuilder();
        sb.append(FILE_FIELDS[0]).append(",").append(supply)
                .append(System.lineSeparator())
                .append(FILE_FIELDS[1]).append(",").append(buy)
                .append(System.lineSeparator())
                .append(FILE_FIELDS[2]).append(",").append(supply - buy)
                .append(System.lineSeparator());
        return sb.toString();
    }

    public void getStatistic(String fromFileName, String toFileName) {
        List<String> list = readFile(fromFileName);
        writeFile(toFileName, reportStatistics(list));
    }
}
