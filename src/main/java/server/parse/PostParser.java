package server.parse;

import client.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import cracker.PasswordCracker;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import model.CrackPasswordMessage;
import util.NodesFile;

public class PostParser implements Parser {

    private final URI uri;

    public PostParser(URI rUri) {
        this.uri = rUri;
    }

    @Override
    public String composeResponse(final HttpExchange req) {
        final String ret;
        switch (this.uri.getPath()) {
            case "/nodes":
                NodesFile.writeContent(readReqBody(req));
                ret = req.getRequestURI().toString();
                break;
            case "/process":
                ret = "Received command: " + readReqBody(req);
                break;
            case "/file":
                ret = readFile(req);
                break;
            default:
                ret = "=====\nUnknown request!\n====\n" + req.getRequestURI().toString();
                break;
        }
        return ret;
    }
    
    private String  readFile(HttpExchange req) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(req.getRequestBody(), StandardCharsets.UTF_8));
             FileWriter writer = new FileWriter("currentHash")) {
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            writer.write(buffer.toString());
            CrackPasswordMessage message = new ObjectMapper().readValue(buffer.toString(), CrackPasswordMessage.class);
            writer.flush();
            new Thread(
                    () -> this.crackFile(message)
            )
                    .start();
            return "Everything is okay";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Something went wrong!";
    }
    
    private void crackFile(CrackPasswordMessage message) {
        Client.crackFile(message);
    }
    
    private String readReqBody(final HttpExchange exchange) {
        try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            int b;
            StringBuilder buf = new StringBuilder();
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }
            return buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Something went wrong!";
    }

}
