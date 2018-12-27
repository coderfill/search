package com.project.search.ansj;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.nlpcn.commons.lang.util.logging.Log;
import org.nlpcn.commons.lang.util.logging.LogFactory;

import java.util.Map;

public class AnsjTokenizerFactory extends TokenizerFactory {

    private final Log logger = LogFactory.getLog();

    private Map<String, String> args;


    protected AnsjTokenizerFactory(Map<String, String> args) {
        super(args);
        this.args = args;
    }

    @Override
    public Tokenizer create(AttributeFactory attributeFactory) {
        return null;
    }
}
