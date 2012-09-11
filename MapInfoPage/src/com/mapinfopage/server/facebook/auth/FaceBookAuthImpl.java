package com.mapinfopage.server.facebook.auth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class FaceBookAuthImpl extends HttpServlet{
	
	private static Logger log = Logger.getLogger(FaceBookAuthImpl.class);
	
	private static final String client_id = "356405047717538";
    private static final String secret = "1fbb237c011991c71dabafa109499847";
    
    // set this to your servlet URL for the authentication servlet/filter
    private static final String redirect_uri = "http://127.0.0.1:8888/mapinfopage/facebookAuthentication";
    /// set this to the list of extended permissions you want
    private static final String[] perms = new String[] {"publish_stream", "email"};
    
    
    
    /* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req, resp);
	}
	
	
	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
        String code = req.getParameter("code");
        if (code != null && !code.isEmpty()) {
            String authURL = getAuthURL(code);
            URL url = new URL(authURL);
            try {
                String result = readURL(url);
                String accessToken = null;
                Integer expires = null;
                String[] pairs = result.split("&");
                for (String pair : pairs) {
                    String[] kv = pair.split("=");
                    if (kv.length != 2) {
                        throw new RuntimeException("Unexpected auth response");
                    } else {
                        if (kv[0].equals("access_token")) {
                            accessToken = kv[1];
                        }
                        if (kv[0].equals("expires")) {
                            expires = Integer.valueOf(kv[1]);
                        }
                    }
                }
                if (accessToken != null && expires != null) {
                    log.info("ACESS TOKEN = " + accessToken);
                    log.info("Expires = " + expires);
                } else {
                    throw new RuntimeException("Access token and expires not found");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String readURL(URL url) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = url.openStream();
        int r;
        while ((r = is.read()) != -1) {
            baos.write(r);
        }
        return new String(baos.toByteArray());
    }
		
	
    
    private 	 String getLoginRedirectURL() {
        return "https://graph.facebook.com/oauth/authorize?client_id=" +
            client_id + "&display=page&redirect_uri=" +
            redirect_uri+"&scope="+"publish_stream,email,read_stream";
    }
    
    
    private  String getAuthURL(String authCode){
    	return "https://graph.facebook.com/oauth/access_token?client_id=" +
    	client_id+"&redirect_uri=" +
    	redirect_uri+"&client_secret="+secret+"&code="+encode(authCode);
    }

	private	  String encode(String authCode) {
		String encodedAuthCode = null;
		try {
			encodedAuthCode = URLEncoder.encode(authCode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		// This should never happen, we have specified UTF-8 correctly, Log error
			log.error(e);
		}
		return encodedAuthCode;
	}
    

}
