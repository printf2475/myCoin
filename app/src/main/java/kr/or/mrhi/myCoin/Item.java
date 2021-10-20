package kr.or.mrhi.myCoin;

public class Item {
    //보통은 객체지향은 정보의 은닉을 해야하기 때문에, private으로 만든다.
    String title;
    String link;
    String desc;
    String imgUrl;
    String date;

    public Item() { //혹시 몰라서 매개변수가 없는 것도 만들었다.
    }

    public Item(String title, String link, String desc, String imgUrl, String date) {
        this.title = title;
        this.link = link;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    //Getter & Setter Method..
    //멤버 변수가 private이면

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}