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

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;

public class ClientBoilerplate {
    private static final Wait whichWait = stream(Thread.currentThread().getStackTrace())
            .anyMatch(st->st.getClassName().startsWith("org.junit")) ? new FakeWait() : new RealWait();

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

        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        webSocketContainer.setDefaultMaxTextMessageBufferSize(
                webSocketContainer.getDefaultMaxBinaryMessageBufferSize() * 32);
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient(webSocketContainer);
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

    static interface Wait {
        void sleep(int ms);
    }

    static class RealWait implements Wait {
        public void sleep(int ms){
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class FakeWait implements Wait {
        public void sleep(int ms){}
    }

    public static void waitRandomTime() {
        final Random random = new Random();
        float waitDuration = random.nextFloat() * 5000;
        waitMs((int) waitDuration);
    }

    public static void waitMs(int num){
        whichWait.sleep(num);
    }

    public static Car randomCar(){
        return new Car(UUID.randomUUID(),
                Side.randomBetween4Sides(),
                DrivingBehavior.values()[new Random().nextInt(DrivingBehavior.values().length)]);
    }

    public synchronized static void sendMessage(StompSession session, String url, Object payload) {
        session.send(url, payload);
    }
}
