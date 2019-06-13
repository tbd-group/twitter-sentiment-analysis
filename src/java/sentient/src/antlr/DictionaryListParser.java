// Generated from ./src/antlr/DictionaryList.g4 by ANTLR 4.7.2
package sentient;

    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.util.zip.GZIPOutputStream;

    import org.zeromq.ZMQ;

    import lombok.Getter;
    import lombok.Setter;

    import sentient.ParseProtos;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DictionaryListParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, TEXT=3;
	public static final int
		RULE_parse = 0, RULE_dictionaryList = 1, RULE_term = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "dictionaryList", "term"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'\r'", "'\n'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "TEXT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DictionaryList.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    @Setter
	    private ZMQ.Socket outputSocket;

	    @Setter
	    private ZMQ.Socket inputSocket;

	    @Getter
	    private int numRows = 0;

	    private ParseProtos.DictionaryList.Builder builder =
	        ParseProtos.DictionaryList.newBuilder();

	public DictionaryListParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ParseContext extends ParserRuleContext {
		public DictionaryListContext dictionaryList() {
			return getRuleContext(DictionaryListContext.class,0);
		}
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(6);
			dictionaryList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DictionaryListContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public DictionaryListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictionaryList; }
	}

	public final DictionaryListContext dictionaryList() throws RecognitionException {
		DictionaryListContext _localctx = new DictionaryListContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_dictionaryList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(11);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TEXT) {
				{
				{
				setState(8);
				term();
				}
				}
				setState(13);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

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

			}
		}
		catch (IOException exception) {

			    exception.printStackTrace();

		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public Token TEXT;
		public TerminalNode TEXT() { return getToken(DictionaryListParser.TEXT, 0); }
		public TerminalNode EOF() { return getToken(DictionaryListParser.EOF, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			((TermContext)_localctx).TEXT = match(TEXT);
			setState(22);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
				{
				setState(18);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(17);
					match(T__0);
					}
				}

				setState(20);
				match(T__1);
				}
				break;
			case EOF:
				{
				setState(21);
				match(EOF);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			    builder.addTerm((((TermContext)_localctx).TEXT!=null?((TermContext)_localctx).TEXT.getText():null));
			    numRows += 1;

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\5\35\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\3\2\3\2\3\3\7\3\f\n\3\f\3\16\3\17\13\3\3\3\3\3\3\4\3\4\5"+
		"\4\25\n\4\3\4\3\4\5\4\31\n\4\3\4\3\4\3\4\2\2\5\2\4\6\2\2\2\34\2\b\3\2"+
		"\2\2\4\r\3\2\2\2\6\22\3\2\2\2\b\t\5\4\3\2\t\3\3\2\2\2\n\f\5\6\4\2\13\n"+
		"\3\2\2\2\f\17\3\2\2\2\r\13\3\2\2\2\r\16\3\2\2\2\16\20\3\2\2\2\17\r\3\2"+
		"\2\2\20\21\b\3\1\2\21\5\3\2\2\2\22\30\7\5\2\2\23\25\7\3\2\2\24\23\3\2"+
		"\2\2\24\25\3\2\2\2\25\26\3\2\2\2\26\31\7\4\2\2\27\31\7\2\2\3\30\24\3\2"+
		"\2\2\30\27\3\2\2\2\31\32\3\2\2\2\32\33\b\4\1\2\33\7\3\2\2\2\5\r\24\30";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}