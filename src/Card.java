import lombok.AllArgsConstructor;
import lombok.Data;

/*
@author Andrei Gorevoi
*/
@Data
@AllArgsConstructor
public class Card {
    private String question;
    private String answer;
}
