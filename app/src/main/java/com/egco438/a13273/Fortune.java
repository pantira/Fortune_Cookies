package com.egco438.a13273;

/**
 * Created by Benz on 10/31/2016.
 */
public class Fortune {
    private long id;
    private String picture;
    private String message;
    private String timestamp;

    public Fortune(Long id,String message, String pic_name , String timestamp){
        this.id = id;
        this.picture = pic_name;
        this.message = message;
        this.timestamp = timestamp;
    }
    public String getPicName(){ return picture ; }
    public String getMessage(){ return message; }
    public String getTimestamp(){ return timestamp; }
    public long getId() {
        return id;
    }
}
