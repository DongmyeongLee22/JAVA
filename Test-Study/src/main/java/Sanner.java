import java.io.*;

public class Sanner {
    public static void main(String[] args) throws Exception {
        final String path = "/Users/LeeHyeEun/Downloads/DongProject/Blog/Test";
        String oriPath = path + "/Temp.md";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(oriPath)));

        String writhPath = path + "/Temp2.md";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(writhPath), true));
        String line = "";
        boolean isCode = false;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("```")) {
                isCode = !isCode;
                if (isCode) {
                    line += "java";
                }
            }
            System.out.println(line);
            bufferedWriter.write(line + "\n");
        }
        bufferedReader.close();
        bufferedWriter.close();
    }

}
