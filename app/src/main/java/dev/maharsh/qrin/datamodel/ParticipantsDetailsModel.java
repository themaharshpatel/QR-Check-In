package dev.maharsh.qrin.datamodel;

public class ParticipantsDetailsModel {

    private String Email;
    private String Name;
    private String SeatNo;
    private boolean Present;
    private long RegId;



    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSeatNo() {
        return SeatNo;
    }

    public void setSeatNo(String seatNo) {
        SeatNo = seatNo;
    }

    public boolean isPresent() {
        return Present;
    }

    public void setPresent(boolean present) {
        this.Present = present;
    }

    public long getRegId() {
        return RegId;
    }

    public void setRegId(long regId) {
        RegId = regId;
    }
}
