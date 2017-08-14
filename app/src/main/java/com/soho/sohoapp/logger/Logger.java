package com.soho.sohoapp.logger;

public interface Logger {
    /**
     * Developers stuff.
     */
    void d(String message);

    /**
     * The process might be continued, but take extra caution.
     */
    void w(String message);

    /**
     * The process might be continued, but take extra caution.
     */
    void w(String message, Throwable cause);

    /**
     * Something terribly wrong had happened, that must be investigated immediately.
     */
    void e(String message);

    /**
     * Something terribly wrong had happened, that must be investigated immediately.
     */
    void e(String message, Throwable cause);
}
