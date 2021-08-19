package org.myddd.utils.serializer;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author cifei.huang
 * @date 2021/8/19 10:03
 */
public class HessianSerialization {

    public static <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream bos = null;
        Hessian2Output out = null;
        try{
            bos = new ByteArrayOutputStream();
            out = new Hessian2Output(bos);
            out.startMessage();
            out.writeObject(object);
            out.completeMessage();
        }catch (IOException e){
            throw e;
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                throw e;
            }
        }
        return bos.toByteArray();
    }

    public static <T> T deSerialize(byte[] bytes) throws IOException {
        T t;
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        Hessian2Input in = new Hessian2Input(bin);
        try {
            in.startMessage();
            t = (T) in.readObject();
            in.completeMessage();
        } catch (IOException e) {
            throw e;
        }finally {
            try {
                in.close();
                bin.close();
            } catch (IOException e) {
                throw e;
            }
        }
        return t;
    }


}
