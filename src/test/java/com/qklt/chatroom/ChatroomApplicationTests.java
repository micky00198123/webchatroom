package com.qklt.chatroom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.Socket;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatroomApplicationTests {

    @Test
    void contextLoads() throws IOException {

        Socket socket = new Socket("",8080);

    }

}
