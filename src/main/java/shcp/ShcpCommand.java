package shcp;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elias
 */
public class ShcpCommand {

    private final String asciiDatagram;
    private String method = "";
    private String token = "";
    private String body = "";

    public ShcpCommand(String asciiDatagram) {
        this.asciiDatagram = asciiDatagram;
        int i = 0;
        for (char c : asciiDatagram.toCharArray()) {
            if (i == 0) { // Methoden-Phase
                if (c == '!') {
                    i++; // Wechsel in Token-Phase
                    continue;
                }
                if(c == ':') {
                    i += 2; // Wechsel in Body-Phase
                    this.token = null;
                    continue;
                }
                this.method += c;
            }
            if(i == 1) { // Token-Phase
                if(c == ':'){
                    i++;
                    continue;
                }
                this.token += c;
            }
            if(i == 2) { // Body-Phase
                this.body += c;
            }
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getToken() {
        return this.token;
    }
    
    public String getBody() {
        return this.body;
    }
    
    /* public String getParam(String key) {
        try {
            return this.paramValues.get(key);
        } catch (Exception e) {
            return null;
        }
    } */
}
