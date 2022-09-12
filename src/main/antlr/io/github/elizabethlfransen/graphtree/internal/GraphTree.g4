grammar GraphTree;

tokens { INDENT, DEDENT }

@lexer::header {
    package io.github.elizabethlfransen.graphtree.internal;
    import com.yuvalshavit.antlr4.DenterHelper;
}

@lexer::members {
    private final DenterHelper denter = new DenterHelper(
        NL,
        GraphTreeParser.INDENT,
        GraphTreeParser.DEDENT
    ) {
        @Override
        public Token pullToken() {
        return GraphTreeLexer.super.nextToken();
        }
    };

    @Override
    public Token nextToken() {
        return denter.nextToken();
    }
}

@parser::header {
  package io.github.elizabethlfransen.graphtree.internal;
  import java.util.Collections;
}

document returns [DocumentNode ast]:
    e=emptyDocument {$ast = $e.ast;}
    |n=nonEmptyDocument {$ast = $n.ast;};
emptyDocument returns [DocumentNode ast]:
    NL { $ast = new DocumentNode(Collections.emptyList()); };
nonEmptyDocument
    returns [DocumentNode ast]
    locals [ArrayList<LabelNode> list]
    @init {
        $list = new ArrayList<LabelNode>();
    }:
     (n=node { $list.add($n.ast); } )+
     { $ast = new DocumentNode($list); };
node returns [LabelNode ast]:
    l=leafNode { $ast = $l.ast; }
    | p=parentNode { $ast=$p.ast; } ;
leafNode returns [LabelNode ast]:
    id=Identifier NL { $ast = new LabelNode($id.text, Collections.emptyList()); };
parentNode
    returns [LabelNode ast]
    locals [ArrayList<LabelNode> list]
    @init {
           $list = new ArrayList<LabelNode>();
     }:
    id=Identifier
       INDENT
           ( n=node { $list.add($n.ast); } )+
       DEDENT
       { $ast = new LabelNode($id.text, $list); };

NL: ('\r'? '\n' ' '*);

Identifier: (~[ \t\r\n\u000C])(~[\r\n])*;