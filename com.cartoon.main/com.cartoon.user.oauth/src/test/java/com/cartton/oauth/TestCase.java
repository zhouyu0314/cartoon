package com.cartton.oauth;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCase {
    @Test
    public void test() throws Exception{
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiaWQiOiIxIiwiZXhwIjoxNTk2MDIxNDkyLCJhdXRob3JpdGllcyI6WyJzZWNraWxsX2xpc3QiLCJnb29kc19saXN0Il0sImp0aSI6ImE2ODAxMjMyLWUzMWQtNGFiZC04MDliLThiY2ZjMTQxY2Y0ZiIsImNsaWVudF9pZCI6ImRvY3RvciIsInVzZXJuYW1lIjoiMTg2MjYwMDAzMDkifQ.pg3owVxHUFNOGigEFQwmaWVRnxmqyoQvrAS2Ga6SL436SbK2n-GbDKcZ96S123i2UgkCE42TXVP1ioaL_zZWuLIXT7n1osb8Ns30_7OXBrGuE3jx0jZ798D5zNuuL9lco2JA7VvXBbfIMAMGxoSTnpsFwKeioTJQcxS8n4tlzX0RQAOFoT3v2AUxOTvRPO1F6DldrzgQHNep9xifEPi9C0t8yUsvQDPpnXfztzux8dLdcaI8EUjLKY-q-T52dxPnWWFhAiQjHLpkEmV2HjWAmGQgzb1J8RXGKxRfnhwgidgMC_t98pEqdpLLOGvPbdOzwVpuf21PEnt5XVRPHQfeLw";
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAss4XPJ67TwUujrnLn85t8TBkB9oUPcUNAdxVw1TBlLWPIIocHi3v8/TNEnS/Zc+9dFx2NBb8Zqzk0729y+5gN9U/qbhyeo2P7wbkfXATznBKp3Gcj//blHl5iOK4n3za3ELLoOhNyZFYNLGfw1dSDTUF9Mpkk7JeUOXP+l0yn6rxRAGb+Gis8ISb+5VVwoci3yDpCpH3Kt94f4cyLCoj6edEwBLWdGIl7BHIpnxoAVr23zDyizomXuFjPkGRH9N8kRd83N+z6NTcgI41flSbBRVT0jFpr6XpX8yMZOyCNHR+ZRGBSVR0ogfZkrEp8z7ej4+IjiTFPEug0Oeau2auPQIDAQAB-----END PUBLIC KEY-----";
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
        //获取Jwt原始内容
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        //System.out.println(encoded);


    }
}
