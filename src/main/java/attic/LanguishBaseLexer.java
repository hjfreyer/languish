package attic;
//package languish;
//// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 LanguishBase.g 2009-05-15 18:18:15
//
//import org.antlr.runtime.*;
//import java.util.Stack;
//import java.util.List;
//import java.util.ArrayList;
//
//public class LanguishBaseLexer extends Lexer {
//    public static final int T__7=7;
//    public static final int INT=5;
//    public static final int T__8=8;
//    public static final int EOF=-1;
//    public static final int WS=6;
//    public static final int T__10=10;
//    public static final int T__9=9;
//    public static final int T__11=11;
//    public static final int NEWLINE=4;
//
//    // delegates
//    // delegators
//
//    public LanguishBaseLexer() {;} 
//    public LanguishBaseLexer(CharStream input) {
//        this(input, new RecognizerSharedState());
//    }
//    public LanguishBaseLexer(CharStream input, RecognizerSharedState state) {
//        super(input,state);
//
//    }
//    public String getGrammarFileName() { return "LanguishBase.g"; }
//
//    // $ANTLR start "T__7"
//    public final void mT__7() throws RecognitionException {
//        try {
//            int _type = T__7;
//            int _channel = DEFAULT_TOKEN_CHANNEL;
//            // LanguishBase.g:3:6: ( '+' )
//            // LanguishBase.g:3:8: '+'
//            {
//            match('+'); 
//
//            }
//
//            state.type = _type;
//            state.channel = _channel;
//        }
//        finally {
//        }
//    }
//    // $ANTLR end "T__7"
//
//    // $ANTLR start "T__8"
//    public final void mT__8() throws RecognitionException {
//        try {
//            int _type = T__8;
//            int _channel = DEFAULT_TOKEN_CHANNEL;
//            // LanguishBase.g:4:6: ( '-' )
//            // LanguishBase.g:4:8: '-'
//            {
//            match('-'); 
//
//            }
//
//            state.type = _type;
//            state.channel = _channel;
//        }
//        finally {
//        }
//    }
//    // $ANTLR end "T__8"
//
//    // $ANTLR start "T__9"
//    public final void mT__9() throws RecognitionException {
//        try {
//            int _type = T__9;
//            int _channel = DEFAULT_TOKEN_CHANNEL;
//            // LanguishBase.g:5:6: ( '*' )
//            // LanguishBase.g:5:8: '*'
//            {
//            match('*'); 
//
//            }
//
//            state.type = _type;
//            state.channel = _channel;
//        }
//        finally {
//        }
//    }
//    // $ANTLR end "T__9"
//
//    // $ANTLR start "T__10"
//    public final void mT__10() throws RecognitionException {
//        try {
//            int _type = T__10;
//            int _channel = DEFAULT_TOKEN_CHANNEL;
//            // LanguishBase.g:6:7: ( '(' )
//            // LanguishBase.g:6:9: '('
//            {
//            match('('); 
//
//            }
//
//            state.type = _type;
//            state.channel = _channel;
//        }
//        finally {
//        }
//    }
//    // $ANTLR end "T__10"
//
//    // $ANTLR start "T__11"
//    public final void mT__11() throws RecognitionException {
//        try {
//            int _type = T__11;
//            int _channel = DEFAULT_TOKEN_CHANNEL;
//            // LanguishBase.g:7:7: ( ')' )
//            // LanguishBase.g:7:9: ')'
//            {
//            match(')'); 
//
//            }
//
//            state.type = _type;
//            state.channel = _channel;
//        }
//        finally {
//        }
//    }
//    // $ANTLR end "T__11"
//
//    // $ANTLR start "INT"
//    public final void mINT() throws RecognitionException {
//        try {
//            int _type = INT;
//            int _channel = DEFAULT_TOKEN_CHANNEL;
//            // LanguishBase.g:31:5: ( ( '0' .. '9' )+ )
//            // LanguishBase.g:31:9: ( '0' .. '9' )+
//            {
//            // LanguishBase.g:31:9: ( '0' .. '9' )+
//            int cnt1=0;
//            loop1:
//            do {
//                int alt1=2;
//                int LA1_0 = input.LA(1);
//
//                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
//                    alt1=1;
//                }
//
//
//                switch (alt1) {
//            	case 1 :
//            	    // LanguishBase.g:31:9: '0' .. '9'
//            	    {
//            	    matchRange('0','9'); 
//
//            	    }
//            	    break;
//
//            	default :
//            	    if ( cnt1 >= 1 ) break loop1;
//                        EarlyExitException eee =
//                            new EarlyExitException(1, input);
//                        throw eee;
//                }
//                cnt1++;
//            } while (true);
//
//
//            }
//
//            state.type = _type;
//            state.channel = _channel;
//        }
//        finally {
//        }
//    }
//    // $ANTLR end "INT"
//
//    // $ANTLR start "NEWLINE"
//    public final void mNEWLINE() throws RecognitionException {
//        try {
//            int _type = NEWLINE;
//            int _channel = DEFAULT_TOKEN_CHANNEL;
//            // LanguishBase.g:32:8: ( ( '\\r' )? '\\n' )
//            // LanguishBase.g:32:9: ( '\\r' )? '\\n'
//            {
//            // LanguishBase.g:32:9: ( '\\r' )?
//            int alt2=2;
//            int LA2_0 = input.LA(1);
//
//            if ( (LA2_0=='\r') ) {
//                alt2=1;
//            }
//            switch (alt2) {
//                case 1 :
//                    // LanguishBase.g:32:9: '\\r'
//                    {
//                    match('\r'); 
//
//                    }
//                    break;
//
//            }
//
//            match('\n'); 
//
//            }
//
//            state.type = _type;
//            state.channel = _channel;
//        }
//        finally {
//        }
//    }
//    // $ANTLR end "NEWLINE"
//
//    // $ANTLR start "WS"
//    public final void mWS() throws RecognitionException {
//        try {
//            int _type = WS;
//            int _channel = DEFAULT_TOKEN_CHANNEL;
//            // LanguishBase.g:33:5: ( ( ' ' | '\\t' )+ )
//            // LanguishBase.g:33:9: ( ' ' | '\\t' )+
//            {
//            // LanguishBase.g:33:9: ( ' ' | '\\t' )+
//            int cnt3=0;
//            loop3:
//            do {
//                int alt3=2;
//                int LA3_0 = input.LA(1);
//
//                if ( (LA3_0=='\t'||LA3_0==' ') ) {
//                    alt3=1;
//                }
//
//
//                switch (alt3) {
//            	case 1 :
//            	    // LanguishBase.g:
//            	    {
//            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
//            	        input.consume();
//
//            	    }
//            	    else {
//            	        MismatchedSetException mse = new MismatchedSetException(null,input);
//            	        recover(mse);
//            	        throw mse;}
//
//
//            	    }
//            	    break;
//
//            	default :
//            	    if ( cnt3 >= 1 ) break loop3;
//                        EarlyExitException eee =
//                            new EarlyExitException(3, input);
//                        throw eee;
//                }
//                cnt3++;
//            } while (true);
//
//            _channel=HIDDEN;
//
//            }
//
//            state.type = _type;
//            state.channel = _channel;
//        }
//        finally {
//        }
//    }
//    // $ANTLR end "WS"
//
//    public void mTokens() throws RecognitionException {
//        // LanguishBase.g:1:8: ( T__7 | T__8 | T__9 | T__10 | T__11 | INT | NEWLINE | WS )
//        int alt4=8;
//        switch ( input.LA(1) ) {
//        case '+':
//            {
//            alt4=1;
//            }
//            break;
//        case '-':
//            {
//            alt4=2;
//            }
//            break;
//        case '*':
//            {
//            alt4=3;
//            }
//            break;
//        case '(':
//            {
//            alt4=4;
//            }
//            break;
//        case ')':
//            {
//            alt4=5;
//            }
//            break;
//        case '0':
//        case '1':
//        case '2':
//        case '3':
//        case '4':
//        case '5':
//        case '6':
//        case '7':
//        case '8':
//        case '9':
//            {
//            alt4=6;
//            }
//            break;
//        case '\n':
//        case '\r':
//            {
//            alt4=7;
//            }
//            break;
//        case '\t':
//        case ' ':
//            {
//            alt4=8;
//            }
//            break;
//        default:
//            NoViableAltException nvae =
//                new NoViableAltException("", 4, 0, input);
//
//            throw nvae;
//        }
//
//        switch (alt4) {
//            case 1 :
//                // LanguishBase.g:1:10: T__7
//                {
//                mT__7(); 
//
//                }
//                break;
//            case 2 :
//                // LanguishBase.g:1:15: T__8
//                {
//                mT__8(); 
//
//                }
//                break;
//            case 3 :
//                // LanguishBase.g:1:20: T__9
//                {
//                mT__9(); 
//
//                }
//                break;
//            case 4 :
//                // LanguishBase.g:1:25: T__10
//                {
//                mT__10(); 
//
//                }
//                break;
//            case 5 :
//                // LanguishBase.g:1:31: T__11
//                {
//                mT__11(); 
//
//                }
//                break;
//            case 6 :
//                // LanguishBase.g:1:37: INT
//                {
//                mINT(); 
//
//                }
//                break;
//            case 7 :
//                // LanguishBase.g:1:41: NEWLINE
//                {
//                mNEWLINE(); 
//
//                }
//                break;
//            case 8 :
//                // LanguishBase.g:1:49: WS
//                {
//                mWS(); 
//
//                }
//                break;
//
//        }
//
//    }
// 
//
// 
//
//}