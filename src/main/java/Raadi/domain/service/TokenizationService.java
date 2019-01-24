package Raadi.domain.service;

import Raadi.domain.entity.TokenDataEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class TokenizationService {

    /**
     * Tokenize a string to return an HashMap of String and TokenDataEntity.
     * @param text String text to tokenize.
     * @return HashMap of String and TokenDataEntity.
     */
    HashMap<String, TokenDataEntity> tokenization(String text)
    {
        HashMap<String, TokenDataEntity> vector = new HashMap<>();
        String[] arrWords = text.split(" ");

        Double unitFrequency = 1.0/arrWords.length;

        for (int i = 0; i < arrWords.length; i++)
        {

            final int position = i;
            String token = arrWords[i];

            // The word already exists.
            if (vector.containsKey(token))
            {
                // Push new position in array positions.
                ArrayList<Integer> positions = vector.get(token).getPositions();
                positions.add(position);
                vector.get(token).setPositions(positions);

                // Update the weight.
                Double weight = vector.get(token).getWeight();
                weight += unitFrequency;
                vector.get(token).setWeight(weight);
            }
            else // Create the token.
            {
                // If it is not a stop word.
                if (!isStopWord(token))
                {
                    // Transform for stemming algorithm.
                    token = stemmingTransform(token);

                    // Transform for synonym.
                    token = synonymTransform(token);

                    TokenDataEntity tokenData = new TokenDataEntity(unitFrequency, new ArrayList<Integer>() {{
                        add(position);
                    }});
                    vector.put(token, tokenData);
                }
            }

        }

        return vector;
    }

    /**
     * Check if a word is categorized as a stop word.
     * @param word String word to check.
     * @return True if it is a stop word, else false.
     */
    private Boolean isStopWord(String word)
    {
        return IndexerManagerService.getInstance().getStopWords().contains(word);
    }

    /**
     * Stemming transformation process on a word.
     * @param word String to transform.
     * @return String transformed as stemming algorithm.
     */
    private String stemmingTransform(String word)
    {
        if (Pattern.matches(".+sses", word) || Pattern.matches(".+ies", word))
            return word.substring(0, word.length()-2);
        else if (Pattern.matches(".+ss", word))
            return word;
        else if (Pattern.matches(".+s", word))
            return word.substring(0, word.length() - 1);

        return word;
    }

    /**
     * Synonym transformation process on a word.
     * @param word String to transform.
     * @return String transformed as synonym.
     */
    private String synonymTransform(String word) {
        return IndexerManagerService.getInstance().getSynonyms().getOrDefault(word, word);
    }
}
