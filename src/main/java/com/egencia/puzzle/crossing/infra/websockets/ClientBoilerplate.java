package com.egencia.puzzle.crossing.infra.websockets;

import com.egencia.puzzle.crossing.position.Side;
import com.egencia.puzzle.crossing.traffic.Car;
import com.egencia.puzzle.crossing.traffic.DrivingBehavior;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;

public class ClientBoilerplate {

    private static void subscribe(StompSession session, String route, Class<?> expectedClass, Consumer<?> consumer){
        Consumer<Object> castConsumer = (Consumer<Object>) consumer;
        session.subscribe(route, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return expectedClass;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                castConsumer.accept(payload);
            }
        });
    }

    public static void connectTo(String url, Consumer<StompSession> andAfter,
                                 Map<String, Consumer<?>> consumers,
                                 Map<String, Class<?>> expectedClasses) throws ExecutionException, InterruptedException {

        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        SockJsClient sockJsClient = new SockJsClient(singletonList(new WebSocketTransport(simpleWebSocketClient)));

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connect(url, new StompSessionHandler() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                andAfter.accept(session);
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                System.err.println(exception);
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return null;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
            }
        }).get();

        StompSession session2 = stompClient.connect(url, new StompSessionHandler() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                System.err.println(exception);
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return null;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
            }
        }).get();
        consumers.forEach((k,v) -> subscribe(session2, k, expectedClasses.get(k), v));

    }

    public static void waitRandomTime() {
        final Random random = new Random();
        float waitDuration = random.nextFloat() * 15000;
        waitMs((int) waitDuration);
    }

    public static void waitMs(int num){
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Car randomCar(Side from){
        Random random = new Random();
        return new Car(UUID.randomUUID(),
                from != null ? from : Side.values()[random.nextInt(Side.values().length)],
                DrivingBehavior.values()[random.nextInt(DrivingBehavior.values().length)]);
    }

    public synchronized static void sendMessage(StompSession session, String url, Object payload) {
        session.send(url, payload);
    }
}
