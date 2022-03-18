package com.jiarui.base.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
//        String response = value.string();
//        BaseBean re = gson.fromJson(response, BaseBean.class);
        //关注的重点，自定义响应码中非0000的情况，一律抛出ApiException异常。
        //这样，我们就成功的将该异常交给onError()去处理了。
//        if (!re.getStatus().getCode().equals(ConstantUtil.SUCCESS)) {
//            Log.e("apierror==", re.getStatus().getMessage() + "==");
//            value.close();
//            throw new ApiException(re.getStatus().getMessage());
//        } else {
//        MediaType mediaType = value.contentType();
//        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
//        ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
//        InputStreamReader reader = null;
//        if (charset != null) {
//            reader = new InputStreamReader(bis, charset);
//        }
//        JsonReader jsonReader = null;
//        if (reader != null) {
//            jsonReader = gson.newJsonReader(reader);
//        }
//        try {
//            return adapter.read(jsonReader);
//        } finally {
//            value.close();
//        }
//        }
    }
}