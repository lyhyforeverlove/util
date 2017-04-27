package com.eeduspace.uuims.comm.util.xml;

import org.apache.commons.io.IOUtils;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:验证工具类
 */
public class XmlMarshaller {

    public String o2x(Object o) throws IOException {
        StringWriter writer = new StringWriter();
        marshaller.marshal(o, new StreamResult(writer));
        return writer.toString();
    }

    public Object x2o(String xml) throws IOException {
        return unmarshaller.unmarshal(new StreamSource(IOUtils.toInputStream(xml)));
    }

    private Unmarshaller unmarshaller;
    private Marshaller marshaller;


    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }
}
