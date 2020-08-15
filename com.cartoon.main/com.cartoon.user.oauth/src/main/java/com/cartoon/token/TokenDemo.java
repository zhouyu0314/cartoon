package com.cartoon.token;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class TokenDemo {
    public static void main(String[] args) {
        TokenDemo tokenDemo=new TokenDemo();
        tokenDemo.parseToken();
        //tokenDemo.createToken();
    }
    public void createToken(){
        //证书文件路径
        String key_location="cartoon.jks";
        //秘钥库密码
        String key_password="aa11cartoon11aa";
        //秘钥密码
        String keypwd = "aa11cartoon11aa";
        //秘钥别名
        String alias = "cartoon";
        ClassPathResource classPathResource=new ClassPathResource(key_location);
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, key_password.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keypwd.toCharArray());
        RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate();
        Map<String ,Object> data  =new HashMap<>();
//        data.put("id",1);
//        data.put("name","张三");
//        data.put("age",20);
        Jwt jwt= JwtHelper.encode(JSON.toJSONString(data),new RsaSigner(privateKey));
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }

    public void parseToken(){
        //String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOjEsImFnZSI6MjB9.T4-Z0XAVaccgBv2FUc051UA27dTLw9777XRbqlvPEb4iWEVvDxeqWyRSnDg6nkgt1GzQ3poGfINQNChk0DDzsMj4GS6KUIiYZpHmCzEzNJrQy3y2xsdUO9WF3suGjdfJTfO-GHz5FjXt1f4ZiApS0Kw_YtOmIL1wPgCSicFNpVa7fOd6MSkaDR49Ztn4_bL5dQt1ZIe0hONoLQqqNYSGJ3WBOmbi6qi5zhyWGOsa1d0mhQicLF4aUgMOOY-wu_3kSwop8oCJSGlnqfNu0GNQmKzcduxKVz_eubnRNGNbPmeYvF1OdYPyUtxtonCcu0QlPGW0vulTcaQnB0Lhx98R3A";
        String token =  "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiZXhwIjoxNTk2Nzk2MTIyLCJhdXRob3JpdGllcyI6WyJ7XCJ1c2VybmFtZVwiOlwiMTg2MjYwMDAzMDlcIn0iXSwianRpIjoiOGUwY2RmOTEtYTQyZi00M2QyLTkwNTUtZmI0Nzc2NjFmOGI3IiwiY2xpZW50X2lkIjoiZG9jdG9yIn0.hyeUtfBBydFFL4HbVzEX3W-l9Fc_plO54-mtkoGMWpNqIjZ78nAEyecuw2niL6DKzbhZ5tqESZMdEQbZMrA7V7xxfRna4UIi8CVoAPHz_cRSPPbRf28T44ojizzM_ErV3zg-_GbyENV3_j3KX-Khn4veEVN06MIFTyvZ25fRKGTCQ6Jb1yZHpv-9UKVhynyhzbDoHZ7Hlde-71iDsSL4a_T1PgyKum6NGaMoHd6D5etgpbXC0VjtOQdh4rFh0iWCvwzFBmS0KdlgEIvlJIyDBmkiJR6v2sBtBlLSC0QaPXgI4qaDxnl3tyIZBXqBl9je3WAQ6f6mPUulpfLBKtwUJQ";
        String pubKey="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAss4XPJ67TwUujrnLn85t8TBkB9oUPcUNAdxVw1TBlLWPIIocHi3v8/TNEnS/Zc+9dFx2NBb8Zqzk0729y+5gN9U/qbhyeo2P7wbkfXATznBKp3Gcj//blHl5iOK4n3za3ELLoOhNyZFYNLGfw1dSDTUF9Mpkk7JeUOXP+l0yn6rxRAGb+Gis8ISb+5VVwoci3yDpCpH3Kt94f4cyLCoj6edEwBLWdGIl7BHIpnxoAVr23zDyizomXuFjPkGRH9N8kRd83N+z6NTcgI41flSbBRVT0jFpr6XpX8yMZOyCNHR+ZRGBSVR0ogfZkrEp8z7ej4+IjiTFPEug0Oeau2auPQIDAQAB-----END PUBLIC KEY-----";

        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(pubKey));
        String claims = jwt.getClaims();
        System.out.println(claims);


    }
}
