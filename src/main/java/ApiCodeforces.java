import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ApiCodeforces {
    public static Set<String> getSet(String name) throws IOException, JSONException {
        final String template = "https://codeforces.com/api/user.status?handle=%s";
        Scanner sc = new Scanner(System.in);


            URL url = new URL(String.format(template, name));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            } catch (IOException e) {
                System.out.println("Неправильный ник, введите еще раз!");

            }
            JSONArray obj = new JSONObject(result.toString()).getJSONArray("result");
            Set<String> cntNotSolved = new HashSet<>();
            Set<String> cntSolved = new HashSet<>();
            for (int i = 0; i < obj.length(); ++i) {
                try {
                    String id = obj.getJSONObject(i).getJSONObject("problem").getInt("contestId") + obj.getJSONObject(i).getJSONObject("problem").getString("index");
                    String status = obj.getJSONObject(i).getString("verdict");
                    if (status.equals("OK"))
                        cntSolved.add(id);
                    else
                        cntNotSolved.add(id);
                }
                catch (JSONException e) {
                    continue;
                }

            }
            for (String s : cntSolved)
                cntNotSolved.remove(s);
            return cntNotSolved;

    }
}
