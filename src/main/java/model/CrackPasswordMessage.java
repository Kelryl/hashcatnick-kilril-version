package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class CrackPasswordMessage {
    private final String hash;
    private final String algorithm;
    private final int numberOfMembers;
    private final int memberNumber;
    
    
    @JsonCreator
    public CrackPasswordMessage(@JsonProperty("hash") String hash,
                                @JsonProperty("algorithm") String algorithm,
                                @JsonProperty("numberOfMembers") int numberOfMembers,
                                @JsonProperty("memberNumber") int memberNumber) {
        this.hash = hash;
        this.algorithm = algorithm;
        this.numberOfMembers = numberOfMembers;
        this.memberNumber = memberNumber;
    }
    
    public int getNumberOfMembers() {
        return numberOfMembers;
    }
    
    public String getHash() {
        return hash;
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
    
    public int getMemberNumber() {
        return memberNumber;
    }
}
