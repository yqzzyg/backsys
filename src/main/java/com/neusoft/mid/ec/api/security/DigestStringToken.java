package com.neusoft.mid.ec.api.security;

import org.apache.shiro.authc.AuthenticationToken;

import com.neusoft.mid.ec.api.util.TokenHelper;

/**
 * Created by puras on 10/11/16.
 */
public class DigestStringToken implements AuthenticationToken {

    /**
     * <p>Discription:[XXXX]</p>
     */
    private static final long serialVersionUID = 5400828175295528246L;

    private String principal;

    private String credential;

    private Long timestamp;

    public DigestStringToken(String digestString) {
        String[] tokens = TokenHelper.parseToken(digestString);
        principal = tokens[0];
        timestamp = Long.valueOf(tokens[1]);
        credential = tokens[2];
    }

    @Override
    public String toString() {
        return "DigestStringToken{" + "principal='" + principal + '\'' + ", credential='"
                + credential + '\'' + ", timestamp=" + timestamp + '}';
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credential;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
