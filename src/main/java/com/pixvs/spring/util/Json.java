package com.pixvs.spring.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Json {

    public static List<ObjectNode> tupleToJson(List<Tuple> results) {

        List<ObjectNode> json = new ArrayList<ObjectNode>();

        ObjectMapper mapper = new ObjectMapper();

        for (Tuple t : results)
        {
            List<TupleElement<?>> cols = t.getElements();

            ObjectNode one = mapper.createObjectNode();

            for (TupleElement col : cols)
            {
                if(t.get(col.getAlias()) != null && Integer.class.equals(t.get(col.getAlias()).getClass())){
                    one.put(col.getAlias(), t.get(col.getAlias()) != null ? (Integer) t.get(col.getAlias()) : null);
                }else if(t.get(col.getAlias()) != null && BigDecimal.class.equals(t.get(col.getAlias()).getClass())){
                    one.put(col.getAlias(), t.get(col.getAlias()) != null ? (BigDecimal) t.get(col.getAlias()) : null);
                }else if(t.get(col.getAlias()) != null && Boolean.class.equals(t.get(col.getAlias()).getClass())){
                    one.put(col.getAlias(), t.get(col.getAlias()) != null ? (Boolean) t.get(col.getAlias()) : null);
                }else{
                    one.put(col.getAlias(), t.get(col.getAlias()) != null ? t.get(col.getAlias()).toString() : null);
                }

            }

            json.add(one);
        }

        return json;
    }
}
