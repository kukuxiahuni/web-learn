package me.learn.web.architecture.component;

/**
 * web
 * me.learn.web.architecture.component
 * Created by kukuxiahuni on 2018/3/16.
 */
public class Request {

    //请求方法:post, get, delete
    private String requestMethod;

    //请求路径: /web/create
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (!requestMethod.equals(request.requestMethod)) return false;
        return requestPath.equals(request.requestPath);
    }

    @Override
    public int hashCode() {
        int result = requestMethod.hashCode();
        result = 31 * result + requestPath.hashCode();
        return result;
    }
}
