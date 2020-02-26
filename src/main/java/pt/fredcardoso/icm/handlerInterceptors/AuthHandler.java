package pt.fredcardoso.icm.handlerInterceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import pt.fredcardoso.icm.model.User;

@Component
public class AuthHandler implements HandlerInterceptor {
	public boolean preHandle(
	  HttpServletRequest request,
	  HttpServletResponse response, 
	  Object handler) throws Exception {
	     
		String path = request.getRequestURI();
		
		//DEBUG
		User user = new User();
		user.setEmail("fredecardoso@hotmail.com");
		user.setId(14);
		user.setName("Frederico Cardoso");
		user.setAdmin(true);
		request.getSession().setAttribute("user", user);
		
		if(request.getSession(false) != null && request.getSession(false).getAttribute("user") != null) {
			return true;
		} else {
			request.getSession().setAttribute("user", new User());
		}
	    
		if(!path.equals("/login") && !path.equals("/register")) {
			response.sendRedirect("/login");
		}
		
		return true;
	}
}
