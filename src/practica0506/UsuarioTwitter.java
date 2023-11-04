package practica0506;

import java.net.URL;
import java.util.ArrayList;

public class UsuarioTwitter implements Comparable{
    protected String id;
    protected String screenName;
    protected ArrayList<String> tags;
    protected URL avatar;
    protected Long followersCount;
    protected Long FriendsCount;
    protected String lang;
    protected Long lastSeen;
    protected String tweetId;
    protected ArrayList<String> friends;
    protected int amigosDentro;

    public UsuarioTwitter(String id, String screenName, ArrayList<String> tags, URL avatar, Long followersCount,
                          Long friendsCount, String lang, Long lastSeen, String tweetId, ArrayList<String> friends) {
        this.id = id;
        this.screenName = screenName;
        this.tags = tags;
        this.avatar = avatar;
        this.followersCount = followersCount;
        FriendsCount = friendsCount;
        this.lang = lang;
        this.lastSeen = lastSeen;
        this.tweetId = tweetId;
        this.friends = friends;
        this.amigosDentro = 0;
    }

    public int getAmigosDentro() {
        return amigosDentro;
    }

    public void setAmigosDentro(int amigosDentro) {
        this.amigosDentro = amigosDentro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public URL getAvatar() {
        return avatar;
    }

    public void setAvatar(URL avatar) {
        this.avatar = avatar;
    }

    public Long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Long followersCount) {
        this.followersCount = followersCount;
    }

    public Long getFriendsCount() {
        return FriendsCount;
    }

    public void setFriendsCount(Long friendsCount) {
        FriendsCount = friendsCount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return (screenName+" - "+amigosDentro+" amigos.");
    }

    @Override
    public int compareTo(Object o) {
        UsuarioTwitter d = (UsuarioTwitter) o;
        return d.amigosDentro-this.amigosDentro;
    }
}

