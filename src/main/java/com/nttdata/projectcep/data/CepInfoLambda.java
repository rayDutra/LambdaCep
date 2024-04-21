package com.nttdata.projectcep.data;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class CepInfoLambda implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        String apiUrl = "https://viacep.com.br/ws/" + input + "/json/";

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiUrl);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null) {
                String responseString = EntityUtils.toString(httpEntity);
                JSONObject jsonResponse = new JSONObject(responseString);

                return jsonResponse.toString();
            } else {
                return "Erro ao obter informações do CEP";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
}
