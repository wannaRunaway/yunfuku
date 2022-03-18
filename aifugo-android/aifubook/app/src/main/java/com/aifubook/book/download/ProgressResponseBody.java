package com.aifubook.book.download;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final AbsFileProgressCallback progressListener;
    private BufferedSource bufferedSource;
    private Handler mUIHandler = new Handler(Looper.getMainLooper());

    public ProgressResponseBody(ResponseBody mResponseBody, AbsFileProgressCallback mProgressListener) {
        this.responseBody = mResponseBody;
        this.progressListener = mProgressListener;
    }

    public MediaType contentType() {
        return this.responseBody.contentType();
    }

    public long contentLength() {
        return this.responseBody.contentLength();
    }

    public BufferedSource source() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(this.source(this.responseBody.source()));
        }

        return this.bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            public long read(Buffer sink, long byteCount) throws IOException {
                final long bytesRead = super.read(sink, byteCount);
                this.totalBytesRead += bytesRead != -1L ? bytesRead : 0L;
                ProgressResponseBody.this.mUIHandler.post(new Runnable() {
                    public void run() {
                        ProgressResponseBody.this.progressListener.onProgress(totalBytesRead, ProgressResponseBody.this.responseBody.contentLength(), bytesRead == -1L);
                    }
                });
                return bytesRead;
            }
        };
    }

    public interface ProgressListener {
        void update(long var1, long var3, boolean var5);
    }
}
