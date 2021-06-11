package com.fans.bravegirls.vo.code;


public enum OneSignalSegment {
    Instagram("Instagram"), Twitter("Twitter"), GalleryNotice("GalleryNotice"),
    GalleryFund("GalleryFund"), GalleryEvent("GalleryEvent"), All("Subscribed Users");

    private String segmentName;


    public String getSegmentName() {
        return segmentName;
    }


    OneSignalSegment(String segmentName) {
        this.segmentName = segmentName;
    }
}
