package com.study.commonlibrary.httpsdk.cookie;

import android.os.Parcel;
import android.os.Parcelable;

import okhttp3.Cookie;

/**
 *
 */
public class ParcelCookie implements Parcelable {
    private transient Cookie cookie;
    private transient Cookie clientCookie;

    private ParcelCookie() {
    }

    public ParcelCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        Cookie result = cookie;
        if (clientCookie != null) {
            result = clientCookie;
        }
        return result;
    }

    public static final Creator<ParcelCookie> CREATOR = new Creator<ParcelCookie>() {
        @Override
        public ParcelCookie createFromParcel(Parcel in) {
            return new ParcelCookie(in);
        }

        @Override
        public ParcelCookie[] newArray(int size) {
            return new ParcelCookie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cookie.name());
        dest.writeString(cookie.value());
        dest.writeLong(cookie.expiresAt());
        dest.writeString(cookie.domain());
        dest.writeString(cookie.path());
        dest.writeBooleanArray(new boolean[]{cookie.secure(), cookie.httpOnly(), cookie.hostOnly(), cookie.persistent()});
    }

    protected ParcelCookie(Parcel in) {
        String name = in.readString();
        String value = in.readString();
        long expiresAt = in.readLong();
        String domain = in.readString();
        String path = in.readString();
        boolean[] values = new boolean[4];
        in.readBooleanArray(values);
        boolean secure = values[0];
        boolean httpOnly = values[1];
        boolean hostOnly = values[2];
        boolean persistent = values[3];
        Cookie.Builder builder = new Cookie.Builder();
        builder = builder.name(name);
        builder = builder.value(value);
        builder = builder.expiresAt(expiresAt);
        builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
        builder = builder.path(path);
        builder = secure ? builder.secure() : builder;
        builder = httpOnly ? builder.httpOnly() : builder;
        clientCookie = builder.build();
    }
}

