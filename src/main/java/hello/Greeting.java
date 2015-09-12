package hello;

public class Greeting {

    private final long id;
    private final String content;
    private final String profession;

    public Greeting(long id, String content, String profession) {
        this.id = id;
        this.content = content;
        this.profession = profession;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getProfession() {
        return profession;
    }
}
