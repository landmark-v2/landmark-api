package com.landmark.app.tourAPI.dto.request;

import lombok.Data;

@Data
public class DefaultInfo {

    private int numOfRows;
    private int pageNo;
    private String ServiceKey = "W%2FT9D1psibqDChSxnQ47aiSZzB4ePW5mp4WZiZZeSEFyiB5PyrOjuQMTiLyNMIuELuAHWSWZMfdEb2Qr07ZrNA%3D%3D";
    private String MobileOS = "ETC";
    private String MobileApp = "LandMark";
    private String type = "json";

    @Override
    public String toString() {
        return "numOfRows=" + numOfRows +
                "&pageNo=" + pageNo +
                "&ServiceKey=" + ServiceKey +
                "&MobileOS=" + MobileOS +
                "&MobileApp=" + MobileApp +
                "&_type=" + type;
    }
}
