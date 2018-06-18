package cls;

import io.netty.buffer.ByteBuf;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author Jiazhi
 * @since 2018/6/18
 */
public class ByteBufTest {

    /**
     * JDK ByteBuffer
     * https://blog.csdn.net/z69183787/article/details/77102198
     * mark <= position <= limit <= capacity
     */
    @Test
    public void testJdkByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put("a".getBytes());
        byteBuffer.flip();
    }

//    /**
//     * 顺序读
//     */
//    @Test
//    public void testRead(){
//        ByteBuf
//    }


}
