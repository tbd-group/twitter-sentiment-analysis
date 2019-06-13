// Generated from ./src/antlr/CSVStream.g4 by ANTLR 4.7.2
package sentient;

    import org.zeromq.ZMQ;

    import org.apache.commons.text.StringEscapeUtils;

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
public class CSVStreamParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, TEXT=4, STRING=5;
	public static final int
		RULE_parse = 0, RULE_csvFile = 1, RULE_hdr = 2, RULE_row = 3, RULE_field = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "csvFile", "hdr", "row", "field"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "','", "'\r'", "'\n'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "TEXT", "STRING"
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
	public String getGrammarFileName() { return "CSVStream.g4"; }

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

	    private ParseProtos.CsvRow.Builder builder = ParseProtos.CsvRow.newBuilder();

	public CSVStreamParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ParseContext extends ParserRuleContext {
		public CsvFileContext csvFile() {
			return getRuleContext(CsvFileContext.class,0);
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
			setState(10);
			csvFile();
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

	public static class CsvFileContext extends ParserRuleContext {
		public HdrContext hdr() {
			return getRuleContext(HdrContext.class,0);
		}
		public TerminalNode EOF() { return getToken(CSVStreamParser.EOF, 0); }
		public List<RowContext> row() {
			return getRuleContexts(RowContext.class);
		}
		public RowContext row(int i) {
			return getRuleContext(RowContext.class,i);
		}
		public CsvFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csvFile; }
	}

	public final CsvFileContext csvFile() throws RecognitionException {
		CsvFileContext _localctx = new CsvFileContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_csvFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			hdr();
			setState(14); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(13);
				row();
				}
				}
				setState(16); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << TEXT) | (1L << STRING))) != 0) );
			setState(18);
			match(EOF);

			    outputSocket.send(new byte[0], 0);  // send EOF
			    inputSocket.recv(0);  // receive acknowledgment

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

	public static class HdrContext extends ParserRuleContext {
		public RowContext row() {
			return getRuleContext(RowContext.class,0);
		}
		public HdrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hdr; }
	}

	public final HdrContext hdr() throws RecognitionException {
		HdrContext _localctx = new HdrContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_hdr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			row();
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

	public static class RowContext extends ParserRuleContext {
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public RowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_row; }
	}

	public final RowContext row() throws RecognitionException {
		RowContext _localctx = new RowContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_row);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			field();
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(24);
				match(T__0);
				setState(25);
				field();
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(31);
				match(T__1);
				}
			}

			setState(34);
			match(T__2);

			    ParseProtos.CsvRow row = builder.build();
			    outputSocket.send(row.toByteArray(), 0);
			    builder.clear();
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

	public static class FieldContext extends ParserRuleContext {
		public String value;
		public Token unquoted;
		public Token quoted;
		public TerminalNode TEXT() { return getToken(CSVStreamParser.TEXT, 0); }
		public TerminalNode STRING() { return getToken(CSVStreamParser.STRING, 0); }
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TEXT:
				{
				setState(37);
				((FieldContext)_localctx).unquoted = match(TEXT);
				((FieldContext)_localctx).value =  (((FieldContext)_localctx).unquoted!=null?((FieldContext)_localctx).unquoted.getText():null);
				}
				break;
			case STRING:
				{
				setState(39);
				((FieldContext)_localctx).quoted = match(STRING);
				((FieldContext)_localctx).value =  StringEscapeUtils.unescapeCsv((((FieldContext)_localctx).quoted!=null?((FieldContext)_localctx).quoted.getText():null));
				}
				break;
			case T__0:
			case T__1:
			case T__2:
				{
				((FieldContext)_localctx).value =  "";
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			builder.addField(_localctx.value);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\7\61\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\3\3\3\6\3\21\n\3\r\3\16\3\22\3"+
		"\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\7\5\35\n\5\f\5\16\5 \13\5\3\5\5\5#\n\5"+
		"\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\5\6-\n\6\3\6\3\6\3\6\2\2\7\2\4\6\b\n"+
		"\2\2\2\60\2\f\3\2\2\2\4\16\3\2\2\2\6\27\3\2\2\2\b\31\3\2\2\2\n,\3\2\2"+
		"\2\f\r\5\4\3\2\r\3\3\2\2\2\16\20\5\6\4\2\17\21\5\b\5\2\20\17\3\2\2\2\21"+
		"\22\3\2\2\2\22\20\3\2\2\2\22\23\3\2\2\2\23\24\3\2\2\2\24\25\7\2\2\3\25"+
		"\26\b\3\1\2\26\5\3\2\2\2\27\30\5\b\5\2\30\7\3\2\2\2\31\36\5\n\6\2\32\33"+
		"\7\3\2\2\33\35\5\n\6\2\34\32\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36\37\3"+
		"\2\2\2\37\"\3\2\2\2 \36\3\2\2\2!#\7\4\2\2\"!\3\2\2\2\"#\3\2\2\2#$\3\2"+
		"\2\2$%\7\5\2\2%&\b\5\1\2&\t\3\2\2\2\'(\7\6\2\2(-\b\6\1\2)*\7\7\2\2*-\b"+
		"\6\1\2+-\b\6\1\2,\'\3\2\2\2,)\3\2\2\2,+\3\2\2\2-.\3\2\2\2./\b\6\1\2/\13"+
		"\3\2\2\2\6\22\36\",";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}