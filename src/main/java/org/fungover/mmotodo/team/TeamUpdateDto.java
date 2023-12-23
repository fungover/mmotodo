package org.fungover.mmotodo.team;

import java.io.Serializable;

public record TeamUpdateDto(int id, String name) implements Serializable {
}
