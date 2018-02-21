package app.repository.dto;

import app.service.DataType;

public class FieldCheckDTO {

    public DataType userField;
    public String value;

    public FieldCheckDTO(DataType userField, String value) {
        this.userField = userField;
        this.value = value;
    }
}
