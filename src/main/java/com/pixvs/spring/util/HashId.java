package com.pixvs.spring.util;

import org.hashids.Hashids;
import org.springframework.stereotype.Service;

@Service
public class HashId {

    Hashids hashids = new Hashids("", 5, "ABCDEFGHIJKLMPQRSTVWXYZ123456789");

    public String encode(int id) {
        return hashids.encode(id);
    }

    public Integer decode(String id) {
        return hashids.decode(id).length == 0 ? null : (int) hashids.decode(id)[0];
    }
}
