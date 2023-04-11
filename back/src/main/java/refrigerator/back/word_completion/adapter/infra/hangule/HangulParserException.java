package refrigerator.back.word_completion.adapter.infra.hangule;

public class HangulParserException extends Exception{
    public HangulParserException() {
        super();
    }

    public HangulParserException(String message) {
        super(message);
    }
}