package packettosend;

import enums.States;

import java.io.Serializable;

public record Pack(States header, Object object) implements Serializable {

}
