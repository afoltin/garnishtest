package com.mobiquityinc.mobit.modules.generic.httpclient;

import com.mobiquityinc.mobit.modules.generic.httpclient.model.HttpResponse;
import lombok.NonNull;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import javax.annotation.Nullable;
import java.util.List;

public interface HttpRequestBuilder {

    @NonNull HttpRequestBuilder setHeader(@NonNull String name, @NonNull String value);
    @NonNull HttpRequestBuilder addHeader(@NonNull String name, @NonNull String value);
    @NonNull HttpRequestBuilder addHeader(@NonNull String name, @Nullable String... values);
    @NonNull HttpRequestBuilder addHeader(@NonNull String key, @NonNull List<String> values);

    @NonNull HttpRequestBuilder body(@NonNull String body);
    @NonNull HttpRequestBuilder body(@NonNull String body, @NonNull final ContentType contentType);
    @NonNull HttpRequestBuilder body(@NonNull MultipartEntityBuilder body);

    @NonNull HttpResponse execute();
}
