package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordResult {
    private final String result;
    private final int memberID;
    
    @JsonCreator
    public PasswordResult(@JsonProperty("result") String result,
                          @JsonProperty("memberID") int memberID) {
        this.result = result;
        this.memberID = memberID;
    }
    
    public String getResult() {
        return result;
    }
    
    public int getMemberID() {
        return memberID;
    }
}
