// ~*~ antlr ~*~

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

grammar CSVDatasetSplitter;

@parser::header {
    import java.io.BufferedWriter;
    import java.util.Random;

    import lombok.Getter;
    import lombok.Setter;
    import lombok.SneakyThrows;
}

@parser::members {
    @Setter
    private BufferedWriter trainingOutput;

    @Setter
    private BufferedWriter testingOutput;

    @Setter
    private double testingProbability = 0.30;

    @Getter
    private final Random random = new Random();

    @SneakyThrows
    public void write(BufferedWriter output, String sentiment, String tweetId, String tweet) {
        output.write(sentiment);
        output.write(',');
        output.write(tweetId);
        output.write(',');
        output.write(tweet);
        output.newLine();
    }
}

csvFile: hdr row+ ;
hdr : row ;

row : fields+=field (',' fields+=field)* '\r'? '\n' {
    String sentiment = $fields.get(0).value;
    String tweetId = $fields.get(1).value;
    String tweet = $fields.get(2).value;
    double probability = random.nextDouble();
    if (probability < testingProbability) {
        write(testingOutput, sentiment, tweetId, tweet);
    }
    else {
        write(trainingOutput, sentiment, tweetId, tweet);
    }
}
;

field returns [String value]
    : unquoted=TEXT {$value = $unquoted.text;}
    | quoted=STRING {$value = $quoted.text;}
    | {$value = "";}
    ;

TEXT   : ~[,\n\r"]+ ;
STRING : '"' ('""'|~'"')* '"' ; // quote-quote is an escaped quote
