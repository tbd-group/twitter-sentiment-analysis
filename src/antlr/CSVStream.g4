/*
 [The "BSD licence"]
 Copyright (c) 2013 Terence Parr
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

grammar CSVStream;

@parser::header {
    import org.zeromq.ZMQ;

    import org.apache.commons.text.StringEscapeUtils;

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

    private ParseProtos.CsvRow.Builder builder = ParseProtos.CsvRow.newBuilder();
}

parse: csvFile;

csvFile: hdr row+ EOF {
    outputSocket.send(new byte[0], 0);  // send EOF
    inputSocket.recv(0);  // receive acknowledgment
}
;

hdr : row ;

row : field (',' field)* '\r'? '\n' {
    ParseProtos.CsvRow row = builder.build();
    outputSocket.send(row.toByteArray(), 0);
    builder.clear();
    numRows += 1;
};

field locals [String value]:
  ( unquoted=TEXT {$value = $unquoted.text;}
  | quoted=STRING {$value = StringEscapeUtils.unescapeCsv($quoted.text);}
  | /* ϵ */ {$value = "";}
  ) {builder.addField($value);};

TEXT   : ~[,\n\r"]+ ;
STRING : '"' ('""'|~'"')* '"' ; // quote-quote is an escaped quote
