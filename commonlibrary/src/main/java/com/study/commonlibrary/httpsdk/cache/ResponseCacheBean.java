package com.study.commonlibrary.httpsdk.cache;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 网络响应缓存Bean基础类
 */
public class ResponseCacheBean implements Parcelable {
    String key;

    /*实体内容，本来用泛型T保存更合适，但是那样每个实体类得继承Parcelable*/
    public String content;
    /*保存时间*/
    public long saveTime;

    public ResponseCacheBean(String key, String content, long saveTime) {
        this.key = key;
        this.content = content;
        this.saveTime = saveTime;
    }

    protected ResponseCacheBean(Parcel in) {
        key = in.readString();
        content = in.readString();
        saveTime = in.readLong();
    }

    public static final Creator<ResponseCacheBean> CREATOR = new Creator<ResponseCacheBean>() {
        @Override
        public ResponseCacheBean createFromParcel(Parcel in) {
            return new ResponseCacheBean(in);
        }

        @Override
        public ResponseCacheBean[] newArray(int size) {
            return new ResponseCacheBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(key);
        parcel.writeString(content);
        parcel.writeLong(saveTime);
    }
}
