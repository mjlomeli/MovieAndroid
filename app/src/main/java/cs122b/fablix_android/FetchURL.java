package cs122b.fablix_android;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FetchURL {
    public static String LOGIN_URL = "https://ec2-13-57-13-91.us-west-1.compute.amazonaws.com:8443/WebApp/api/login";
    public static String SINGLE_MOVIE = "https://ec2-13-57-13-91.us-west-1.compute.amazonaws.com:8443/WebApp/api/singlemovie";
    public static String MOVIES_List = "https://ec2-13-57-13-91.us-west-1.compute.amazonaws.com:8443/WebApp/api/search?";
    public static final String DEFAULT_IMAGE_JPG = "https://www.imgworlds.com/wp-content/uploads/2015/11/novocinema1.jpg";
    public static final String IMAGE_SERVLET = "http://ec2-3-16-216-233.us-east-2.compute.amazonaws.com:8080/Fablix/api/image/";
    public static final String SEARCH_IMAGE_SERVLET = "http://ec2-3-16-216-233.us-east-2.compute.amazonaws.com:8080/Fablix/api/search_image/";
    public static final String DEFAULT_MOVIE_SERVLET = "https://ec2-13-57-13-91.us-west-1.compute.amazonaws.com:8443/WebApp/android?title=&year=0&dir=&star=&page=1&len=20";
    //`http://ec2-13-57-13-91.us-west-1.compute.amazonaws.com:8080/WebApp/api/search?title=&year=0&dir=&star=&page=1`;


    public static String cookSearch(String title,String pg_number){
        String charset = "UTF-8";
        String query;
        String pg_len = "20";
        String finish = "https://ec2-13-57-13-91.us-west-1.compute.amazonaws.com:8443/WebApp/android?";
        try {
            query = String.format("title=%s&year=%s&dir=%s&star=%s&page=%s&len=%s",
                    URLEncoder.encode(title, charset),
                    URLEncoder.encode("0", charset),
                    URLEncoder.encode("",charset),
                    URLEncoder.encode("",charset),
                    URLEncoder.encode(pg_number, charset),
                    URLEncoder.encode(pg_len, charset));

            finish += query;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return finish;
    }
}