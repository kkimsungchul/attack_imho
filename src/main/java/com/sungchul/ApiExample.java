package com.sungchul;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class ApiExample {
    public static void main(String[] args) {

        // 현재 시간의 Unix Timestamp (밀리초 단위)
        long currentTimestamp = Instant.now().toEpochMilli();

        // 1분(60초 * 1000ms) 전의 Unix Timestamp 계산
        long oneMinuteAgoTimestamp = currentTimestamp - (60 * 1000);

        // 변환된 날짜/시간 확인
        printFormattedTime("현재 시간", currentTimestamp);
        printFormattedTime("1분 전", oneMinuteAgoTimestamp);

//        String getApiUrl = "http://221.163.166.53/api/todo"; // GET 예제 API URL
        String postApiUrl = "http://221.163.166.53/api/todo"; // POST 예제 API URL
        String deleteApiUrl = "http://221.163.166.53/api/todo"; // DELETE 예제 API URL

//        sendDeleteRequest(deleteApiUrl);

//        sendGetRequest(getApiUrl);
//        sendPostRequest(postApiUrl);

//        String name = "}ll";
//        String ip = "555.555.555.555";
//        sendPostRequest(postApiUrl, name, ip);

//        for(int i= 1; i <= 254; i++) {
//            UUID uuid = UUID.randomUUID();
//            String ipTemp = ip+i;
//            String nameTemp = name+uuid.toString();
//            sendPostRequest(postApiUrl, nameTemp, ipTemp);
//        }
    }

    public static void sendGetRequest(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            System.out.println("GET Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("GET Response: " + response.toString());
            } else {
                System.out.println("GET request failed.");
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sendDeleteRequest(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);


            long id = 1743600218892L;
            String jsonInputString = String.format("{\"id\":%d}",id);
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            // OutputStream을 사용해 데이터 전송
            try (OutputStream os = connection.getOutputStream()) {

                os.write(input);
                System.out.println(os.toString());

                os.flush(); // 데이터 전송 보장
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

            // 서버 응답 읽기
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                System.out.println("POST Response: " + response.toString());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void sendPostRequest(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // 전송할 JSON 데이터
            String jsonInputString = "{\"name\": \"ㅋㅋㅋ\" , \"ip\": \"221.163.166.53\"}";
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            // OutputStream을 사용해 데이터 전송
            try (OutputStream os = connection.getOutputStream()) {

                os.write(input);
                System.out.println(os.toString());

                os.flush(); // 데이터 전송 보장
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

            // 서버 응답 읽기
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                System.out.println("POST Response: " + response.toString());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPostRequest(String apiUrl, String name, String ip) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // 동적으로 JSON 생성
            String jsonInputString = String.format("{\"name\": \"%s\", \"ip\": \"%s\"}", name, ip);
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            // OutputStream을 사용해 데이터 전송
            try (OutputStream os = connection.getOutputStream()) {
                os.write(input);
                os.flush(); // 데이터 전송 보장
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

            // 서버 응답 읽기
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                System.out.println("POST Response: " + response.toString());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printFormattedTime(String label, long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZonedDateTime dateTime = instant.atZone(ZoneId.of("Asia/Seoul")); // 한국 시간 기준 변환

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTime.format(formatter);

        System.out.println(label + " Unix Timestamp: " + timestamp);
        System.out.println(label + " (한국 시간): " + formattedDate);
        System.out.println();
    }
}
