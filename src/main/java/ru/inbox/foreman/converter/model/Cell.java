package ru.inbox.foreman.converter.model;

import java.awt.*;

public class Cell extends Component {
    private String value;
    private boolean error;

    public Cell(String value, boolean error) {
        this.value = value;
        this.error = error;
    }

    public Cell(String value) {
        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public boolean isError() {
        return error;
    }
}
