package com.komunikatorinternetowy.utils;

import java.io.Closeable;
import java.io.IOException;

//TODO Change to jUnit5
class CloseSafeTest {
    public void testClose_noParams() {
        String wantedMessage = "It should be like it.";
        Closeable closeable = new Closeable() {
            @Override
            public void close() throws IOException {
                throw new IOException(wantedMessage);
            }
        };

        CloseSafe.close(closeable);
        assert true;
    }
}