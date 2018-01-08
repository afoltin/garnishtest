package org.garnishtest.demo.rest_complex.web.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public final class TodoListApi {

    private final long id;
    @NonNull private final String title;

    @JsonCreator
    public TodoListApi(@JsonProperty ("id") final long id,
                       @JsonProperty ("title") @NonNull final String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return this.id;
    }

    @NonNull
    public String getTitle() {
        return this.title;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
