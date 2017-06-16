package me.ianhe.model.wx;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Button {
    private String type;
    private String name;
    @JsonProperty("sub_button")
    private List<Button> subButton;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Button> getSubButton() {
        return subButton;
    }

    public void setSubButton(List<Button> subButton) {
        this.subButton = subButton;
    }
}
