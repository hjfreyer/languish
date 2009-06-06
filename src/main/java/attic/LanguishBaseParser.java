package attic;
//package languish;
//// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 LanguishBase.g 2009-05-15 18:18:15
//
//import org.antlr.runtime.*;
//import java.util.Stack;
//import java.util.List;
//import java.util.ArrayList;
//
//
//import org.antlr.runtime.tree.*;
//
//public class LanguishBaseParser extends Parser {
//    public static final String[] tokenNames = new String[] {
//        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEWLINE", "INT", "WS", "'+'", "'-'", "'*'", "'('", "')'"
//    };
//    public static final int T__7=7;
//    public static final int INT=5;
//    public static final int T__8=8;
//    public static final int WS=6;
//    public static final int EOF=-1;
//    public static final int T__10=10;
//    public static final int T__9=9;
//    public static final int T__11=11;
//    public static final int NEWLINE=4;
//
//    // delegates
//    // delegators
//
//
//        public LanguishBaseParser(TokenStream input) {
//            this(input, new RecognizerSharedState());
//        }
//        public LanguishBaseParser(TokenStream input, RecognizerSharedState state) {
//            super(input, state);
//             
//        }
//        
//    protected TreeAdaptor adaptor = new CommonTreeAdaptor();
//
//    public void setTreeAdaptor(TreeAdaptor adaptor) {
//        this.adaptor = adaptor;
//    }
//    public TreeAdaptor getTreeAdaptor() {
//        return adaptor;
//    }
//
//    public String[] getTokenNames() { return LanguishBaseParser.tokenNames; }
//    public String getGrammarFileName() { return "LanguishBase.g"; }
//
//
//    public static class prog_return extends ParserRuleReturnScope {
//        CommonTree tree;
//        public Object getTree() { return tree; }
//    };
//
//    // $ANTLR start "prog"
//    // LanguishBase.g:14:1: prog : ( stat )+ ;
//    public final LanguishBaseParser.prog_return prog() throws RecognitionException {
//        LanguishBaseParser.prog_return retval = new LanguishBaseParser.prog_return();
//        retval.start = input.LT(1);
//
//        CommonTree root_0 = null;
//
//        LanguishBaseParser.stat_return stat1 = null;
//
//
//
//        try {
//            // LanguishBase.g:14:5: ( ( stat )+ )
//            // LanguishBase.g:14:9: ( stat )+
//            {
//            root_0 = (CommonTree)adaptor.nil();
//
//            // LanguishBase.g:14:9: ( stat )+
//            int cnt1=0;
//            loop1:
//            do {
//                int alt1=2;
//                int LA1_0 = input.LA(1);
//
//                if ( ((LA1_0>=NEWLINE && LA1_0<=INT)||LA1_0==10) ) {
//                    alt1=1;
//                }
//
//
//                switch (alt1) {
//            	case 1 :
//            	    // LanguishBase.g:14:11: stat
//            	    {
//            	    pushFollow(FOLLOW_stat_in_prog43);
//            	    stat1=stat();
//
//            	    state._fsp--;
//
//            	    adaptor.addChild(root_0, stat1.getTree());
//            	    System.out.println((stat1!=null?((CommonTree)stat1.tree):null).toStringTree());
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
//            retval.stop = input.LT(-1);
//
//            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
//            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
//
//        }
//        catch (RecognitionException re) {
//            reportError(re);
//            recover(input,re);
//    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
//
//        }
//        finally {
//        }
//        return retval;
//    }
//    // $ANTLR end "prog"
//
//    public static class stat_return extends ParserRuleReturnScope {
//        CommonTree tree;
//        public Object getTree() { return tree; }
//    };
//
//    // $ANTLR start "stat"
//    // LanguishBase.g:16:1: stat : ( expr NEWLINE -> expr | NEWLINE ->);
//    public final LanguishBaseParser.stat_return stat() throws RecognitionException {
//        LanguishBaseParser.stat_return retval = new LanguishBaseParser.stat_return();
//        retval.start = input.LT(1);
//
//        CommonTree root_0 = null;
//
//        Token NEWLINE3=null;
//        Token NEWLINE4=null;
//        LanguishBaseParser.expr_return expr2 = null;
//
//
//        CommonTree NEWLINE3_tree=null;
//        CommonTree NEWLINE4_tree=null;
//        RewriteRuleTokenStream stream_NEWLINE=new RewriteRuleTokenStream(adaptor,"token NEWLINE");
//        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
//        try {
//            // LanguishBase.g:16:5: ( expr NEWLINE -> expr | NEWLINE ->)
//            int alt2=2;
//            int LA2_0 = input.LA(1);
//
//            if ( (LA2_0==INT||LA2_0==10) ) {
//                alt2=1;
//            }
//            else if ( (LA2_0==NEWLINE) ) {
//                alt2=2;
//            }
//            else {
//                NoViableAltException nvae =
//                    new NoViableAltException("", 2, 0, input);
//
//                throw nvae;
//            }
//            switch (alt2) {
//                case 1 :
//                    // LanguishBase.g:16:9: expr NEWLINE
//                    {
//                    pushFollow(FOLLOW_expr_in_stat58);
//                    expr2=expr();
//
//                    state._fsp--;
//
//                    stream_expr.add(expr2.getTree());
//                    NEWLINE3=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat60);  
//                    stream_NEWLINE.add(NEWLINE3);
//
//
//
//                    // AST REWRITE
//                    // elements: expr
//                    // token labels: 
//                    // rule labels: retval
//                    // token list labels: 
//                    // rule list labels: 
//                    // wildcard labels: 
//                    retval.tree = root_0;
//                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
//
//                    root_0 = (CommonTree)adaptor.nil();
//                    // 16:29: -> expr
//                    {
//                        adaptor.addChild(root_0, stream_expr.nextTree());
//
//                    }
//
//                    retval.tree = root_0;
//                    }
//                    break;
//                case 2 :
//                    // LanguishBase.g:17:9: NEWLINE
//                    {
//                    NEWLINE4=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat81);  
//                    stream_NEWLINE.add(NEWLINE4);
//
//
//
//                    // AST REWRITE
//                    // elements: 
//                    // token labels: 
//                    // rule labels: retval
//                    // token list labels: 
//                    // rule list labels: 
//                    // wildcard labels: 
//                    retval.tree = root_0;
//                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
//
//                    root_0 = (CommonTree)adaptor.nil();
//                    // 17:29: ->
//                    {
//                        root_0 = null;
//                    }
//
//                    retval.tree = root_0;
//                    }
//                    break;
//
//            }
//            retval.stop = input.LT(-1);
//
//            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
//            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
//
//        }
//        catch (RecognitionException re) {
//            reportError(re);
//            recover(input,re);
//    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
//
//        }
//        finally {
//        }
//        return retval;
//    }
//    // $ANTLR end "stat"
//
//    public static class expr_return extends ParserRuleReturnScope {
//        CommonTree tree;
//        public Object getTree() { return tree; }
//    };
//
//    // $ANTLR start "expr"
//    // LanguishBase.g:20:1: expr : multExpr ( ( '+' | '-' ) multExpr )* ;
//    public final LanguishBaseParser.expr_return expr() throws RecognitionException {
//        LanguishBaseParser.expr_return retval = new LanguishBaseParser.expr_return();
//        retval.start = input.LT(1);
//
//        CommonTree root_0 = null;
//
//        Token char_literal6=null;
//        Token char_literal7=null;
//        LanguishBaseParser.multExpr_return multExpr5 = null;
//
//        LanguishBaseParser.multExpr_return multExpr8 = null;
//
//
//        CommonTree char_literal6_tree=null;
//        CommonTree char_literal7_tree=null;
//
//        try {
//            // LanguishBase.g:20:5: ( multExpr ( ( '+' | '-' ) multExpr )* )
//            // LanguishBase.g:20:9: multExpr ( ( '+' | '-' ) multExpr )*
//            {
//            root_0 = (CommonTree)adaptor.nil();
//
//            pushFollow(FOLLOW_multExpr_in_expr109);
//            multExpr5=multExpr();
//
//            state._fsp--;
//
//            adaptor.addChild(root_0, multExpr5.getTree());
//            // LanguishBase.g:20:18: ( ( '+' | '-' ) multExpr )*
//            loop4:
//            do {
//                int alt4=2;
//                int LA4_0 = input.LA(1);
//
//                if ( ((LA4_0>=7 && LA4_0<=8)) ) {
//                    alt4=1;
//                }
//
//
//                switch (alt4) {
//            	case 1 :
//            	    // LanguishBase.g:20:19: ( '+' | '-' ) multExpr
//            	    {
//            	    // LanguishBase.g:20:19: ( '+' | '-' )
//            	    int alt3=2;
//            	    int LA3_0 = input.LA(1);
//
//            	    if ( (LA3_0==7) ) {
//            	        alt3=1;
//            	    }
//            	    else if ( (LA3_0==8) ) {
//            	        alt3=2;
//            	    }
//            	    else {
//            	        NoViableAltException nvae =
//            	            new NoViableAltException("", 3, 0, input);
//
//            	        throw nvae;
//            	    }
//            	    switch (alt3) {
//            	        case 1 :
//            	            // LanguishBase.g:20:20: '+'
//            	            {
//            	            char_literal6=(Token)match(input,7,FOLLOW_7_in_expr113); 
//            	            char_literal6_tree = (CommonTree)adaptor.create(char_literal6);
//            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal6_tree, root_0);
//
//
//            	            }
//            	            break;
//            	        case 2 :
//            	            // LanguishBase.g:20:25: '-'
//            	            {
//            	            char_literal7=(Token)match(input,8,FOLLOW_8_in_expr116); 
//            	            char_literal7_tree = (CommonTree)adaptor.create(char_literal7);
//            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal7_tree, root_0);
//
//
//            	            }
//            	            break;
//
//            	    }
//
//            	    pushFollow(FOLLOW_multExpr_in_expr120);
//            	    multExpr8=multExpr();
//
//            	    state._fsp--;
//
//            	    adaptor.addChild(root_0, multExpr8.getTree());
//
//            	    }
//            	    break;
//
//            	default :
//            	    break loop4;
//                }
//            } while (true);
//
//
//            }
//
//            retval.stop = input.LT(-1);
//
//            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
//            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
//
//        }
//        catch (RecognitionException re) {
//            reportError(re);
//            recover(input,re);
//    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
//
//        }
//        finally {
//        }
//        return retval;
//    }
//    // $ANTLR end "expr"
//
//    public static class multExpr_return extends ParserRuleReturnScope {
//        CommonTree tree;
//        public Object getTree() { return tree; }
//    };
//
//    // $ANTLR start "multExpr"
//    // LanguishBase.g:23:1: multExpr : atom ( '*' atom )* ;
//    public final LanguishBaseParser.multExpr_return multExpr() throws RecognitionException {
//        LanguishBaseParser.multExpr_return retval = new LanguishBaseParser.multExpr_return();
//        retval.start = input.LT(1);
//
//        CommonTree root_0 = null;
//
//        Token char_literal10=null;
//        LanguishBaseParser.atom_return atom9 = null;
//
//        LanguishBaseParser.atom_return atom11 = null;
//
//
//        CommonTree char_literal10_tree=null;
//
//        try {
//            // LanguishBase.g:24:5: ( atom ( '*' atom )* )
//            // LanguishBase.g:24:9: atom ( '*' atom )*
//            {
//            root_0 = (CommonTree)adaptor.nil();
//
//            pushFollow(FOLLOW_atom_in_multExpr142);
//            atom9=atom();
//
//            state._fsp--;
//
//            adaptor.addChild(root_0, atom9.getTree());
//            // LanguishBase.g:24:14: ( '*' atom )*
//            loop5:
//            do {
//                int alt5=2;
//                int LA5_0 = input.LA(1);
//
//                if ( (LA5_0==9) ) {
//                    alt5=1;
//                }
//
//
//                switch (alt5) {
//            	case 1 :
//            	    // LanguishBase.g:24:15: '*' atom
//            	    {
//            	    char_literal10=(Token)match(input,9,FOLLOW_9_in_multExpr145); 
//            	    char_literal10_tree = (CommonTree)adaptor.create(char_literal10);
//            	    root_0 = (CommonTree)adaptor.becomeRoot(char_literal10_tree, root_0);
//
//            	    pushFollow(FOLLOW_atom_in_multExpr148);
//            	    atom11=atom();
//
//            	    state._fsp--;
//
//            	    adaptor.addChild(root_0, atom11.getTree());
//
//            	    }
//            	    break;
//
//            	default :
//            	    break loop5;
//                }
//            } while (true);
//
//
//            }
//
//            retval.stop = input.LT(-1);
//
//            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
//            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
//
//        }
//        catch (RecognitionException re) {
//            reportError(re);
//            recover(input,re);
//    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
//
//        }
//        finally {
//        }
//        return retval;
//    }
//    // $ANTLR end "multExpr"
//
//    public static class atom_return extends ParserRuleReturnScope {
//        CommonTree tree;
//        public Object getTree() { return tree; }
//    };
//
//    // $ANTLR start "atom"
//    // LanguishBase.g:27:1: atom : ( INT | '(' expr ')' );
//    public final LanguishBaseParser.atom_return atom() throws RecognitionException {
//        LanguishBaseParser.atom_return retval = new LanguishBaseParser.atom_return();
//        retval.start = input.LT(1);
//
//        CommonTree root_0 = null;
//
//        Token INT12=null;
//        Token char_literal13=null;
//        Token char_literal15=null;
//        LanguishBaseParser.expr_return expr14 = null;
//
//
//        CommonTree INT12_tree=null;
//        CommonTree char_literal13_tree=null;
//        CommonTree char_literal15_tree=null;
//
//        try {
//            // LanguishBase.g:27:5: ( INT | '(' expr ')' )
//            int alt6=2;
//            int LA6_0 = input.LA(1);
//
//            if ( (LA6_0==INT) ) {
//                alt6=1;
//            }
//            else if ( (LA6_0==10) ) {
//                alt6=2;
//            }
//            else {
//                NoViableAltException nvae =
//                    new NoViableAltException("", 6, 0, input);
//
//                throw nvae;
//            }
//            switch (alt6) {
//                case 1 :
//                    // LanguishBase.g:27:9: INT
//                    {
//                    root_0 = (CommonTree)adaptor.nil();
//
//                    INT12=(Token)match(input,INT,FOLLOW_INT_in_atom165); 
//                    INT12_tree = (CommonTree)adaptor.create(INT12);
//                    adaptor.addChild(root_0, INT12_tree);
//
//
//                    }
//                    break;
//                case 2 :
//                    // LanguishBase.g:28:9: '(' expr ')'
//                    {
//                    root_0 = (CommonTree)adaptor.nil();
//
//                    char_literal13=(Token)match(input,10,FOLLOW_10_in_atom176); 
//                    pushFollow(FOLLOW_expr_in_atom179);
//                    expr14=expr();
//
//                    state._fsp--;
//
//                    adaptor.addChild(root_0, expr14.getTree());
//                    char_literal15=(Token)match(input,11,FOLLOW_11_in_atom181); 
//
//                    }
//                    break;
//
//            }
//            retval.stop = input.LT(-1);
//
//            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
//            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
//
//        }
//        catch (RecognitionException re) {
//            reportError(re);
//            recover(input,re);
//    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
//
//        }
//        finally {
//        }
//        return retval;
//    }
//    // $ANTLR end "atom"
//
//    // Delegated rules
//
//
// 
//
//    public static final BitSet FOLLOW_stat_in_prog43 = new BitSet(new long[]{0x0000000000000432L});
//    public static final BitSet FOLLOW_expr_in_stat58 = new BitSet(new long[]{0x0000000000000010L});
//    public static final BitSet FOLLOW_NEWLINE_in_stat60 = new BitSet(new long[]{0x0000000000000002L});
//    public static final BitSet FOLLOW_NEWLINE_in_stat81 = new BitSet(new long[]{0x0000000000000002L});
//    public static final BitSet FOLLOW_multExpr_in_expr109 = new BitSet(new long[]{0x0000000000000182L});
//    public static final BitSet FOLLOW_7_in_expr113 = new BitSet(new long[]{0x0000000000000420L});
//    public static final BitSet FOLLOW_8_in_expr116 = new BitSet(new long[]{0x0000000000000420L});
//    public static final BitSet FOLLOW_multExpr_in_expr120 = new BitSet(new long[]{0x0000000000000182L});
//    public static final BitSet FOLLOW_atom_in_multExpr142 = new BitSet(new long[]{0x0000000000000202L});
//    public static final BitSet FOLLOW_9_in_multExpr145 = new BitSet(new long[]{0x0000000000000420L});
//    public static final BitSet FOLLOW_atom_in_multExpr148 = new BitSet(new long[]{0x0000000000000202L});
//    public static final BitSet FOLLOW_INT_in_atom165 = new BitSet(new long[]{0x0000000000000002L});
//    public static final BitSet FOLLOW_10_in_atom176 = new BitSet(new long[]{0x0000000000000420L});
//    public static final BitSet FOLLOW_expr_in_atom179 = new BitSet(new long[]{0x0000000000000800L});
//    public static final BitSet FOLLOW_11_in_atom181 = new BitSet(new long[]{0x0000000000000002L});
//
//}