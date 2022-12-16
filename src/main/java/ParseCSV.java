import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseCSV {
    public static void main(String[] args) {
        String path = "data/movementList.csv";
        ArrayList<String> lines = new ArrayList<>();
        try {
            lines = (ArrayList<String>) Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, Double> expence2sum = new HashMap<>();

        String firstLine = null;
        for(String line : lines) {
            if(firstLine == null) {
                firstLine = line;
                continue;
            }
            String[] tokens = line.split(",");
            double expense = Double.parseDouble(tokens[7]);
            if(expense == 0) {
                continue;
            }
            String paymentType = getPaymentType(tokens[5]);
            if(!expence2sum.containsKey(paymentType)) {
                expence2sum.put(paymentType, 0.);
            }
            expence2sum.put(
                    paymentType,
                    expence2sum.get(paymentType) + expense
            );
        }

        for(String paymentType : expence2sum.keySet()) {
            double sum = expence2sum.get(paymentType);
            System.out.println(paymentType + "\t" + sum);
        }
    }

    private static String getPaymentType(String info) {
        String regex = "[^a-zA-Z0-9]([a-zA-Z0-9\s]+)[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}\s[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
}


