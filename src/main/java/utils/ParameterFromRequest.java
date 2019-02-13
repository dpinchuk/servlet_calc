package utils;

import javax.servlet.http.HttpServletRequest;

public class ParameterFromRequest {
    private final HttpServletRequest req;

    public ParameterFromRequest(HttpServletRequest req) {
        this.req = req;
    }

    public int getInt(String name) {
        if(req.getParameter(name) == null){
            throw new IllegalStateException(String.format("Parameter %s missing",name));
        }
        return Integer.parseInt(req.getParameter(name));
    }

    public String getStr(String name) {
        return req.getParameter(name);
    }
}
