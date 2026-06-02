import java.util.ArrayList;
import java.util.List;

public class ScoreManager {

    private List<Score> scores = new ArrayList<>();

    public void addScore(Score score) {
        scores.add(score);
    }

    public List<Score> getScores() {
        return scores;
    }
}