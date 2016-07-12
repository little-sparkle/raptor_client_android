package com.littlesparkle.growler.raptor.entity;

/**
 * Created by dell on 2016/7/7.
 */
public class JourneyEntity {
    private String JourneyFrom;
    private String JourneyTO;
    private String JourneyState;
    private String JourneyTime;

    public String getJourneyFrom() {
        return JourneyFrom;
    }

    public void setJourneyFrom(String journeyFrom) {
        JourneyFrom = journeyFrom;
    }

    public String getJourneyTO() {
        return JourneyTO;
    }

    public void setJourneyTO(String journeyTO) {
        JourneyTO = journeyTO;
    }

    public String getJourneyState() {
        return JourneyState;
    }

    public void setJourneyState(String journeyState) {
        JourneyState = journeyState;
    }

    public String getJourneyTime() {
        return JourneyTime;
    }

    public void setJourneyTime(String journeyTime) {
        JourneyTime = journeyTime;
    }

    public JourneyEntity(String journeyFrom, String journeyTO, String journeyState, String journeyTime) {
        JourneyFrom = journeyFrom;
        JourneyTO = journeyTO;
        JourneyState = journeyState;
        JourneyTime = journeyTime;
    }

    @Override
    public String toString() {
        return "JourneyEntity{" +
                "JourneyFrom='" + JourneyFrom + '\'' +
                ", JourneyTO='" + JourneyTO + '\'' +
                ", JourneyState='" + JourneyState + '\'' +
                ", JourneyTime='" + JourneyTime + '\'' +
                '}';
    }
}
