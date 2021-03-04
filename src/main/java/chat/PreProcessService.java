//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chat;

import com.turing.semantic.main.SemanticService;
import com.turing.semantic.preprocess.service.SynonymPhraseAhoCorasickService;
import com.turing.semantic.preprocess.service.Traditional2SimplifiedService;
import com.turing.semantic.rule.Result;
import com.turing.semantic.rule.SemanticRule;
import com.turing.semantic.service.SemanticRuleParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class PreProcessService {
    private static final Pattern ANY_WORD = Pattern.compile("[\\w\\pP ]");
    private static final Double RATE = 0.8D;
    static{
        try {
            SemanticService.init();
            Result result = SemanticService.analysis("谁爱谁是谁");
            if (result.isSuccess()) {
                String str = result.getCleanValue();
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String process(String sentence) throws Exception {
        if (StringUtils.isEmpty(sentence)) {
            return null;
        } else {
            return isEnglish(sentence) ? processEnglish(sentence) : processChinese(sentence);
        }
    }

    private static boolean isEnglish(String sentence) {
        Matcher matcher = ANY_WORD.matcher(sentence);

        int count;
        for (count = 0; matcher.find(); ++count) {
        }

        return (double) count > (double) sentence.length() * RATE;
    }

    private static String processEnglish(String sentence) throws Exception {
        return sentence;
    }

    private static String processChinese(String sentence) throws Exception {
        sentence = Traditional2SimplifiedService.process(sentence);
        SemanticRule semanticRule = SemanticRuleParserService.getSemanticRule();
        Result result = semanticRule.filter(sentence);
        if (result.isSuccess() && result.hasValue()) {
            sentence = removeWhiteSpace(result.getValue().trim());
//            SynonymPhraseAhoCorasickService.buildSynonymPhraseTrie();
            sentence = SynonymPhraseAhoCorasickService.process(sentence).trim();
            return sentence;
        } else {
            return null;
        }
    }

    private static String removeWhiteSpace(String sentence) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < sentence.length(); ++i) {
            if (!Character.isWhitespace(sentence.charAt(i))) {
                builder.append(sentence.charAt(i));
            }
        }

        return builder.toString();
    }
}
