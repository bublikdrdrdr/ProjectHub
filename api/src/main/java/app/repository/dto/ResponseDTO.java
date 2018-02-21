package app.repository.dto;

import java.util.LinkedList;
import java.util.List;

public abstract class ResponseDTO<E> {

    public Long count;
    public List<E> items;

    public ResponseDTO() {
    }

    public ResponseDTO(Long count) {
        this(count, new LinkedList<>());
    }

    public ResponseDTO(Long count, List<E> items) {
        this.count = count;
        this.items = items;
    }
}
