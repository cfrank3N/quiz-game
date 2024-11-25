package shared;

import javax.swing.*;
import java.io.Serializable;

public record PlayerDTO(String name, String avatarPath) implements Serializable {
}
