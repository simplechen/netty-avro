package prototype.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;

import LocalTimeProtocol.Locations;
import static org.jboss.netty.channel.Channels.pipeline;

public class AvroServerPipelineFactory implements ChannelPipelineFactory {

    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();

        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
//        pipeline.addLast("protobufDecoder", new ProtobufDecoder(Locations.getDefaultInstance()));
        pipeline.addLast("avroDecoder", new AvroDecoder(Locations.getDefaultInstance()));
        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
//        pipeline.addLast("protobufEncoder", new ProtobufEncoder());
        pipeline.addLast("avroEncoder", new AvroEncoder());
        pipeline.addLast("handler", new AvroServerHandler());

        return pipeline;
    }
}