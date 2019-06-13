grammar DictionaryList;

@parser::header {
    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.util.zip.GZIPOutputStream;

    import org.zeromq.ZMQ;

    import lombok.Getter;
    import lombok.Setter;

    import sentient.ParseProtos;
}

@parser::members {
    @Setter
    private ZMQ.Socket outputSocket;

    @Setter
    private ZMQ.Socket inputSocket;

    @Getter
    private int numRows = 0;

    private ParseProtos.DictionaryList.Builder builder =
        ParseProtos.DictionaryList.newBuilder();
}

parse: dictionaryList;

dictionaryList: term* {
    ParseProtos.DictionaryList dict = builder.build();
    byte[] bytes = dict.toByteArray();
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bytes.length);
    GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
    zipStream.write(bytes);
    zipStream.close();
    byteStream.close();
    bytes = byteStream.toByteArray();
    outputSocket.send(bytes, 0);
    builder.clear();
    inputSocket.recv(0);  // receive acknowledgment
};
catch [IOException exception] {
    exception.printStackTrace();
}

term: TEXT ('\r'? '\n' | EOF) {
    builder.addTerm($TEXT.text);
    numRows += 1;
};

TEXT: ~[\n\r]+;
