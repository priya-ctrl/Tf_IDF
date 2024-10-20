import model.DocumentData;
import search.TFIDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

public class SequentialSearch {
    public static final String BOOKS_DIRECTORY = "./resources/books";
    public static final String SEARCH_QUERY1 = "The best detective who catches many criminals using his deductive methods";
    public static final String SEARCH_QUERY2 = "The girl that falls through a rabbit hole into a fantasy wonderland";
    public static final String SEARCH_QUERY3 = "A was between Russia and France in the cold winter";
    private static Map<String, DocumentData> documentsResults;
    private static Object score;


    public static void main(String[] args) throws FileNotFoundException {

    File documentsDirectory = new File(BOOKS_DIRECTORY);

    List<String> documents = Arrays.asList(documentsDirectory.list())
            .stream()
            .map(documentName -> BOOKS_DIRECTORY + "/" + documentName)
            .collect(Collectors.toList());
    List<String> terms = TFIDF.getWordsFromLine(SEARCH_QUERY1);

    findMostRelevantDocuments(documents, terms);
}

        private static void findMostRelevantDocuments(List < String > documents, List < String > terms) throws
        FileNotFoundException {
            Map<String, DocumentData> documentDataMap = new HashMap<>();

            for (String document : documents) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(document));
                List<String> lines = bufferedReader.lines().collect(Collectors.toList());
                List<String> words = TFIDF.getWordsFromLines(lines);
                DocumentData documentData = TFIDF.createDocumentData(words, terms);
                documentDataMap.put(document, documentData);
            }

            Map<Double, List<String>> documentsByScore;
            documentsByScore = TFIDF.getDocumentsSortedByScore(terms, documentsResults);
            printResults(documentsByScore);
        }
        private static void printResults(Map<Double, List<String>> documentByScore){
        for(Map.Entry<Double, List<String>> docScorePair : documentByScore.entrySet()){
            for(String document : docScorePair.getValue()){
                System.out.println(String.format("Book : %s - score :%f",document.split("/")[3],score));
            }
        }
        }
    }

