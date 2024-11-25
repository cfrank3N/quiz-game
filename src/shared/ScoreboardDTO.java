package shared;

import java.io.Serializable;

public record ScoreboardDTO(int you, int opponent) implements Serializable {
}
