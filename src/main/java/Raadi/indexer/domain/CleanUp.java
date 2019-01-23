package Raadi.indexer.domain;

import Raadi.Manager;
import Raadi.entity.DocumentClean;
import Raadi.entity.DocumentRaw;
import Raadi.entity.TokenData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CleanUp
{

    /**
     * cleanup
     * @param documentRaw
     * @return
     */
    public static DocumentClean cleanup(DocumentRaw documentRaw)
    {
        DocumentClean documentClean = new DocumentClean();

        documentClean.setContent(documentRaw.getContent());
        documentClean.setChildrenURL(documentRaw.getChildrenURL());
        documentClean.setURL(documentRaw.getURL());
        documentClean.setVector(tokenisation(documentClean.getContent()));

        return documentClean;
    }

    /**
     * tokenisation
     * @param text
     * @return HashMap<String, TokenData>
     */
    public static HashMap<String, TokenData> tokenisation(String text)
    {
        HashMap<String, TokenData> vector = new HashMap<>();
        String[] arrWords = text.split(" ");

        Double unitFrequence = 1.0/arrWords.length;

        for (int i = 0; i < arrWords.length; i++)
        {
            final int position = i;
            String token = arrWords[i];

            // The word already exists
            if (vector.containsKey(arrWords[i]))
            {
                // Push new position in array positions
                ArrayList<Integer> positions = vector.get(token).getPositions();
                positions.add(position);
                vector.get(token).setPositions(positions);

                // Update the weight
                Double weight = vector.get(token).getWeight();
                weight += unitFrequence;
                vector.get(token).setWeight(weight);
            }
            else // create the token
            {
                // if is not a stop word
                if (!isStopWord(token))
                {
                    // Transform for stemming algo
                    token = stemmingTransform(token);

                    // Transform for synonym
                    token = synonymTransform(token);

                    TokenData tokenData = new TokenData(unitFrequence, new ArrayList<Integer>() {{
                        add(position);
                    }});
                    vector.put(token, tokenData);
                }
            }

        }

        return vector;
    }


    /**
     * isStopWord
     * @param word
     * @return
     */
    private static Boolean isStopWord(String word)
    {
        return Manager.getInstance().getStopWords().contains(word);
    }

    /**
     * stemmingTransform
     * @param word
     * @return String : Transformed stemming
     */
    private static String stemmingTransform(String word)
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
     * synonymTransform
     * @param word
     * @return String : Transform synonym
     */
    private static String synonymTransform(String word)
    {
        return Manager.getInstance().getSynonyms().getOrDefault(word, word);
    }
}
