package org.garnishtest.demo.rest_complex.web.dao.model;

import lombok.NonNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class TodoList {

    private final long id;
    @NonNull private final String title;

    public TodoList(final long id,
                    @NonNull final String title) {
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
