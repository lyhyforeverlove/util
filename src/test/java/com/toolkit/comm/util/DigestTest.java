package com.toolkit.comm.util;


import com.toolkit.comm.util.base.encrypt.Digest;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class DigestTest {

    @Test
    public void test(){
        try {
            String s = Digest.getSignature("abced".getBytes(), "bbad".getBytes());
            System.out.println("s--------------:"+s);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
