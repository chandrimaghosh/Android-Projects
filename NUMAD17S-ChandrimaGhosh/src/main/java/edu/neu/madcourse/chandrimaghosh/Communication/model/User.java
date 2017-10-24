package edu.neu.madcourse.chandrimaghosh.Communication.model;

/**
 * Created by chandrimaghosh on 3/3/17.
 */

public class User {
    public String email;
    public String  udId;
    public String gameData;
    public String status;
    public int totalScore;
    public String country;




    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String udId, String gameData,String status,int totalScore,String country){
        this.email = email;
        this.udId = udId;
        this.gameData=gameData;
        this.status=status;
        this.country=country;
        this.totalScore=totalScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getGameData() {
        return gameData;
    }

    public void setGameData(String gameData) {
        this.gameData = gameData;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUdId() {
        return udId;
    }

    public void setUdId(String udId) {
        this.udId = udId;
    }


    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
