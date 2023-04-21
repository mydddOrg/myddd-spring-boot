package org.myddd.commons.gateway;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import jakarta.inject.Named;
import org.myddd.commons.sms.SMSConfigErrorException;
import org.myddd.commons.sms.SMSGatewayServiceErrorException;
import org.myddd.commons.sms.application.SMSGateway;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;

@Named
public class SMSGatewayEmay implements SMSGateway {

    @Value("${emay.key}")
    private String emayKey;

    @Value("${emay.secret}")
    private String emaySecret;

    @Value("${emay.api}")
    private String emayApi;

    @Value("${sms.content}")
    private String smsContent;

    private HttpClient httpClient;

    private HttpClient getHttpClient(){
        if(Objects.isNull(httpClient)){
            httpClient = HttpClient.newBuilder().build();
        }
        return httpClient;
    }

    @Override
    public void sendSmsToMobile(String mobile, String code) {
        var jsonData = new JsonObject();
        jsonData.addProperty("mobile",mobile);
        jsonData.addProperty("content",String.format(smsContent,code));
        jsonData.addProperty("requestTime",System.currentTimeMillis());
        jsonData.addProperty("requestValidPeriod",60);

        var bytes = jsonData.toString().getBytes(StandardCharsets.UTF_8);
        String ALGORITHM = "AES/ECB/PKCS5Padding";
        var encryptData = AES.encrypt(bytes,emaySecret.getBytes(), ALGORITHM);
        sendCodeByEmay(encryptData);
    }

    private void sendCodeByEmay(byte[] data){
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(emayApi))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .header("appId",emayKey)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                    .build();

            var response =
                    getHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());

            if(response.statusCode() != 200)throw new SMSGatewayServiceErrorException();
            var responseResult = response.headers().firstValue("result");
            if(responseResult.isPresent()){
                var value = responseResult.get();
                if(!"SUCCESS".equals(value))throw new SMSGatewayServiceErrorException();
            }else{
                throw new SMSGatewayServiceErrorException();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new SMSGatewayServiceErrorException();
        }
    }


    private void validEmayConfig(){
        if(Strings.isNullOrEmpty(emayApi) || Strings.isNullOrEmpty(emayKey) || Strings.isNullOrEmpty(emaySecret)) throw new SMSConfigErrorException();
    }
}
