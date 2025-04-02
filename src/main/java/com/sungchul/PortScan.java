package com.sungchul;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortScan {
    public static void main(String[] args) {
        String host = "221.163.166.53"; // 스캔할 서버의 IP 또는 도메인
        int startPort = 1; // 시작 포트
        int endPort = 65535; // 끝 포트
        int threadCount = 10; // 스레드 개수

        System.out.println("Scanning ports on host: " + host);

        // 스레드 풀 생성
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // 포트 범위를 스캔하는 작업을 스레드 풀에 제출
        for (int port = startPort; port <= endPort; port++) {
            int currentPort = port; // 람다식에서 사용하려면 final 또는 effectively final이어야 함
            executor.submit(() -> {
                if (isPortOpen(host, currentPort, 100)) { // 타임아웃 100ms 설정
                    System.out.println("Port " + currentPort + " is open.");
                }
            });
        }

        // 작업 완료 후 스레드 풀 종료
        executor.shutdown();
    }
    /**
     * 주어진 호스트와 포트가 열려 있는지 확인
     *
     * @param host       스캔할 호스트
     * @param port       스캔할 포트
     * @param timeout    연결 시도 제한 시간(ms)
     * @return 포트가 열려 있으면 true, 아니면 false
     */
    private static boolean isPortOpen(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // 연결 실패 -> 포트가 닫혀 있음
        }
    }
}