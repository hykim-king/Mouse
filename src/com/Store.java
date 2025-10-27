package com;

import com.google.gson.annotations.SerializedName;

public class Store {
    @SerializedName("매장이름")
    public String name;

    @SerializedName("위도주소")
    public String latitude;

    @SerializedName("경도주소")
    public String longitude;

    @SerializedName("매장타입")
    public String type;

    @SerializedName("매장주소")
    public String address;

    @SerializedName("매장전화")
    public String phone;

    @SerializedName("매장설명")
    public String description;
}
