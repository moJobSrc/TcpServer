package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final String address = ctx.channel().remoteAddress().toString().split(":")[0];
        System.out.println("Client connected from " + address);
        byte[] patchdata = hexStringToByteArray("7c00000080052b288a1301001a002300000068007400740070003a002f002f006b006100720074002e0064006e002e006e00650078006f006e00630064006e002e0063006f002e006b0072002f007000610074006300680000000000000000007603000903000003000000000083a3e547000015000d00000003000000000065");
        ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
        buffer.writeBytes(patchdata);
        ctx.writeAndFlush(buffer);

        System.out.println("SEND PATCHDATA PACKET TO CLIENT");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bytes = (ByteBuf) msg;
        byte[] data = new byte[bytes.readableBytes()];
        bytes.readBytes(data);
        short header = (short)((data[0] << 8) + data[1]); // mayne
        System.out.println("Received message: " + byteArrayToHex(data));
        System.out.println("Received message: " + header);

        if (header == 26371) { // login
            byte[] patchdata = hexStringToByteArray("65000000bb02890a000000009da31a0001000000070000006d006f006f00730061006e00740002000000ffffff7f00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002d2a2dd9e90c000000");
            ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
            buffer.writeBytes(patchdata);
            ctx.writeAndFlush(buffer);
        } else if (header == 2560) { //userdata 몰라 maybe lucci, rp etc..
            //old? 25010000d8037b1401070000006d006f006f00730061006e00740000000000010f003f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000000000000000000550700000000000000000000000000000000070000006d006f006f00730061006e0074000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000040000005400650073007400000000000000
            //new? 25010000d8037b1401070000006d006f006f00730061006e00740000000000010f003f000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001c0000000000000000000000000000000000000000000000000000000000000000000000000000006cf7ffff0000000000000000000000000000070000006d006f006f00730061006e0074000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000040000005400650073007400000000000000
            //test 25010000d8037b1401070000006d006f006f00730061006e00740000000000010f003f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000055000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000006d006f006f00730061006e0074000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000040000005400650073007400000000000000
            byte[] patchdata = hexStringToByteArray("25010000d8037b1401070000006d006f006f00730061006e00740000000000010f003f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000055550000000000000000000000000000000000000000000000000000000000000000000000000000550700000000000000000000000000000000000000006d006f006f00730061006e0074000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000040000005400650073007400000000000000");
            ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
            buffer.writeBytes(patchdata);
            ctx.writeAndFlush(buffer);
        } else if (header == 5120) { //userdata 몰라 maybe inventory?
            //old? 340000007a08715f0100000001000000030000000100030001000002ffff00000200070001000002ffff00000300100001000002ffff0000
            //new? 580000007a08715f0100000001000000060000000100030001000002ffff00000200070001000002ffff00000300100001000002ffff00000200050001000002ffff000003000b0001000002ffff00000200050001000002ffff0000
            //plus 580000007a08715f0100000001000000060000000100030001000002ffff00000200070001000002ffff00000300100001000002ffff00000200050001000002ffff000003000b0001000002ffff00000200050001000002ffff0000
            //plus 580000007a08715f0100000001000000060000000100030001000002ffff00000200070001000002ffff00000300100001000002ffff00000200050001000002ffff000003000b0001000002ffff00000200050001000002ffff0000
            byte[] patchdata = hexStringToByteArray("340000007a08715f0100000001000000030000000100030001000002ffff00000200070001000002ffff00000300100001000002ffff0000");
            ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
            buffer.writeBytes(patchdata);
            ctx.writeAndFlush(buffer);
        } else if (header == 3072) { // timeattack //0c 00 00 00 b6 06 e8 3b 01 00 00 74 04 b6 3f 00 //0c 00 00 00 b6 06 e8 3b 01 00 00 3b 02 26 14 00
            //63000000b706f83b01707c70897cbaba7cc74dba7cb3867de24db3ba9cdaceba7cd1d7ba0929ceba7c55d7ba7cf0b165ab8ab1ba7c8f86ba7c8f861b1b35091b1b35091b1b3509ba0959ce1b1b35091efa5dc9a2fc3ff1ba09f3ceba62b7ceba9c90ce1efa5fb3
                                                     //63000000b706f83b01707c70897cbaba7cc74dba7cb3867de24db3ba9cdaceba7cd1d7ba0929ceba7c55d7ba7cf0b165ab8ab1ba7c8f86ba7c8f861b1b35091b1b35091b1b3509ba0959ce1b1b35091efa5dc9a2fc3ff1ba09f3ceba62b7ceba9c90ce1efa5fb3
                                                     //63000000b706f83b01707c70897cbaba7cc74dba7cb3867de24db3ba9cdaceba7cd1d7ba0929ceba7c55d7ba7cf0b165ab8ab1ba7c8f86ba7c8f861b1b35091b1b35091b1b3509ba0959ce1b1b35091efa5dc9a2fc3ff1ba09f3ceba62b7ceba9c90ce1efa5fb3
            byte[] patchdata = hexStringToByteArray("63000000b706f83b01707c70897cbaba7cc74dba7cb3867de24db3ba9cdaceba7cd1d7ba0929ceba7c55d7ba7cf0b165ab8ab1ba7c8f86ba7c8f861b1b35091b1b35091b1b3509ba0959ce1b1b35091efa5dc9a2fc3ff1ba09f3ceba62b7ceba9c90ce1efa5fb3");
            ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
            buffer.writeBytes(patchdata);
            ctx.writeAndFlush(buffer);
        } else if (header == 1024) {
            // 080000006d07fd4800000000
            //04 00 00 00 6c 07 eb 48
            byte[] scenario = {0x04, 0x00, 0x00, 0x00, 0x6c, 0x07, (byte) 0xeb, 0x48};
            if (Arrays.equals(scenario, data)) {
                byte[] patchdata = hexStringToByteArray("080000006d07fd4800000000");
                ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
                buffer.writeBytes(patchdata);
                ctx.writeAndFlush(buffer);
            }
        } else if (header == 2048) {
            //scenario chapter (start-end) request packet
            //request 08 00 00 00 03 06 24 2f 01 00 00 01
            if (data[4] == 0x03) { //scenario
                ByteBuf buffer = ctx.alloc().buffer(1);
                byte chapter = data[data.length - 1];
                if (data[4] == 0x03) { //start packet
                    //response 09 00 00 00 04 06 32 2f 01 00 00 {episode} 00
                    buffer.writeBytes(new byte[]{0x09, 0x00, 0x00, 0x00, 0x04, 0x06, 0x32, 0x2f, 0x01, 0x00, 0x00, chapter, 0x00});
                } else if (data[4] == (byte) 0x90) { //end packet
                    //response 09 00 00 00 91 09 34 76 01 00 00 {episode} 00
                    buffer.writeBytes(new byte[]{0x09, 0x00, 0x00, 0x00, (byte) 0x91, 0x09, 0x34, 0x76, 0x01, 0x00, 0x00, chapter, 0x00});
                }
                ctx.writeAndFlush(buffer);
            } else if (data[4] == (byte) 0xe8) { //multiplayer
                //f70000000b0bb89d01ee00000053003b32b9ef0600000002060000006e006f007600690063006500030b00000072006f006f006b006900650049006e00740072006f00040600000072006f006f006b006900650005030000006c007600330006030000006c007600320007030000006c0076003100060000000100060000006e006f00760069006300650002ffffffff02000b00000072006f006f006b006900650049006e00740072006f0003ffffffff03000600000072006f006f006b006900650004ffffffff0400030000006c007600330005ffffffff0500030000006c007600320006ffffffff0600030000006c007600310007ffffffff
                byte[] patchdata = hexStringToByteArray("f70000000b0bb89d01ee00000053003b32b9ef0600000002060000006e006f007600690063006500030b00000072006f006f006b006900650049006e00740072006f00040600000072006f006f006b006900650005030000006c007600330006030000006c007600320007030000006c0076003100060000000100060000006e006f00760069006300650002ffffffff02000b00000072006f006f006b006900650049006e00740072006f0003ffffffff03000600000072006f006f006b006900650004ffffffff0400030000006c007600330005ffffffff0500030000006c007600320006ffffffff0600030000006c007600310007ffffffff");
                ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
                buffer.writeBytes(patchdata);
                ctx.writeAndFlush(buffer);
            }
        } else if (header == 2816) { //scenario chapter maybe kart?
            //0b 00 00 00 de 03 d1 14 01 00 00 15 ac 70 c8
            //0b000000de03d11401000015ac70c8
            // 63000000df03da1401707c70897cbaba7cc74dba7cb3867de24db3ba9cdaceba7cd1d7ba0929ceba7c55d7ba7cf0b165ab8ab1ba7c8f86ba7c8f861b1b35091b1b35091b1b3509ba0959ce1b1b35091efa5dc9a2fc3ff1ba09f3ceba62b7ceba9c90ce1efa5fb3
            byte[] patchdata = hexStringToByteArray("63000000df03da1401707c70897cbaba7cc74dba7cb3867de24db3ba9cdaceba7cd1d7ba0929ceba7c55d7ba7cf0b165ab8ab1ba7c8f86ba7c8f861b1b35091b1b35091b1b3509ba0959ce1b1b35091efa5dc9a2fc3ff1ba09f3ceba62b7ceba9c90ce1efa5fb3");
            ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
            buffer.writeBytes(patchdata);
            ctx.writeAndFlush(buffer);
        } else if (header == 1280) { //license packet
            // 05 00 00 00 52 06 fe 34 ac
            //6300000053060d35d4707c70897cbaba7cc74dba7cb3867de24db3ba9cdaceba7cd1d7ba0929ceba7c55d7ba7cf0b165ab8ab1ba7c8f86ba7c8f861b1b35091b1b35091b1b3509ba0959ce1b1b35091efa5dc9a2fc3ff1ba09f3ceba62b7ceba9c90ce1efa5fb3
            byte licenseEpisode = data[data.length-1]; // last byte
            byte[] patchdata = hexStringToByteArray("6300000053060d35"+String.format("%02x",licenseEpisode)+"707c70897cbaba7cc74dba7cb3867de24db3ba9cdaceba7cd1d7ba0929ceba7c55d7ba7cf0b165ab8ab1ba7c8f86ba7c8f861b1b35091b1b35091b1b3509ba0959ce1b1b35091efa5dc9a2fc3ff1ba09f3ceba62b7ceba9c90ce1efa5fb3");
            ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
            buffer.writeBytes(patchdata);
            ctx.writeAndFlush(buffer);
        } else if (header == 1792) {
            //12000000ed05172e00000000020000002d2a2dd9e90c
            byte[] patchdata = hexStringToByteArray("12000000ed05172e00000000020000002d2a2dd9e90c");
            ByteBuf buffer = ctx.alloc().buffer(patchdata.length);
            buffer.writeBytes(patchdata);
            ctx.writeAndFlush(buffer);
        }
    }

    //    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("Received message: Hello, client!");
//        System.out.println(((LittleEndianAccessor) msg).readShort());
////        byte[] data = new byte[bytes.readableBytes()];
////        bytes.readBytes(data);
////        System.out.println("Received message: " + byteArrayToHex(data));
//
////        Channel channel = ctx.channel();
////        channel.writeAndFlush("Response : '" + message + "' received\n");
//
////        String responseMessage = "Hello, client!";
////        ByteBuf buffer = ctx.alloc().buffer(responseMessage.length());
////        buffer.writeBytes(responseMessage.getBytes());
////        ctx.writeAndFlush(buffer);
////        String message = (String) msg;
////        System.out.println("Received message: " + message);
//
//        // Send a response back to the client
//
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a)
            sb.append(String.format("%02x ", b&0xff));
        return sb.toString();
    }
}
