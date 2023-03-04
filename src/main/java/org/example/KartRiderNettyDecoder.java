package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class KartRiderNettyDecoder extends ByteToMessageDecoder {
  protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception {
    if (!buffer.hasArray())
      return;
    byte[] bytes = new byte[buffer.readableBytes()];
    buffer.duplicate().readBytes(bytes);

    list.add(new LittleEndianAccessor(new ByteArrayByteStream(bytes)));
  }
}
